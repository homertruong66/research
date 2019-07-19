package com.rms.rms.controller.filter;

import com.rms.rms.common.dto.LoginDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.util.MyJsonUtil;
import com.rms.rms.common.util.MyJwtUtil;
import com.rms.rms.service.AuthenService;
import com.rms.rms.service.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * homertruong
 */

// @Component -> we have to instantiate this class manually to add to Spring
// Security filter chain to avoid duplication
public class SecurityTokenFilter extends GenericFilterBean {

    // General
    private static final String URL_HEALTH = "/health";
    private static final String URL_LOGIN = "/login";
    private static final String URL_APP_PAGE = "/app";
    private static final String URL_CSS = "/css";
    private static final String URL_IMG = "/img";
    private static final String URL_JS = "/js";
    private static final String URL_FAVICON = "/favicon.ico";
    private static final String URL_DOMAIN = "/domains";

    // Subscriber
    private static final String URL_SUBSCRIBER_SEARCH = "/subscribers/search";

    // Affiliate
    private static final String URL_AFFILIATE_SIGN_UP = "/affiliates/sign_up";
    private static final String URL_AFFILIATE_ACTIVATE = "/affiliates/activate";

    // Parameter name
    private static final String PARAM_ACTIVATION_TOKEN = "token";

    // System Security
    private static final String HEADER_SECURITY_TOKEN_KEY = "X-Security-Token";
    private static final String HEADER_USER_PROFILE_KEY = "X-User-Profile";
    private static final int TOKEN_EXPIRED_CODE = 440;
    private static final int USER_INACTIVE_CODE = 441;
    private static final String HEADER_USERNAME = "username";
    private static final String HEADER_PASSWORD = "password";
    private static final String URL_LOGIN_PAGE = "/";
    private static final String URL_LOGOUT = "/logout";
    private static final String URL_RESET_PASSWORD = "/users/reset_password";

    // AWS
    private static final String URL_AWS_S3_PRE_SIGNED_URL = "/v1/aws-s3-pre-signed-url";

    // Person
    private static final String URL_PERSON_RESET_PASSWORD = "/v1/reset_password";

    // Infusion
    private static final String URL_INFUSION_CODE = "/subs_infusion_configs/code";

    // Swagger
    private static final String URL_SWAGGER_DOCS = "/v2/api-docs";
    private static final String URL_SWAGGER_UI = "/swagger-ui.html";
    private static final String URL_SWAGGER_WEBJARS = "/webjars/springfox-swagger-ui/";
    private static final String URL_SWAGGER_RESOURCES = "/swagger-resources";
    private static final String URL_SWAGGER_CSRF = "/csrf";

    private Logger logger = Logger.getLogger(SecurityTokenFilter.class);
    private MyJwtUtil myJwtUtil;
    private AuthenService authenService;
    private ModelMapper beanMapper;

    public SecurityTokenFilter(MyJwtUtil myJwtUtil, AuthenService authenService, ModelMapper beanMapper) {
        this.myJwtUtil = myJwtUtil;
        this.authenService = authenService;
        this.beanMapper = beanMapper;
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // no need token?
        String uri = request.getRequestURI();
        if (noNeedToken(request)) {
            logger.debug("ignore allowed URI: '" + uri + "'");
            handleNoNeedTokenRequest(request);
            chain.doFilter(req, res);
            return;
        }

        // login?
        if (uri.equals(URL_LOGIN)) {
            doLogin(request, response);
            return;
        }

        // activate and login?
        if (uri.equals(URL_AFFILIATE_ACTIVATE)) {
            doActivate(request, response);
            return;
        }

        // no token found?
        String headerSecurityToken = request.getHeader(HEADER_SECURITY_TOKEN_KEY);
        if (headerSecurityToken == null || !headerSecurityToken.startsWith("Bearer ")) {
            logger.error("no security token header found for request: " + uri);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // token expired?
        String token = headerSecurityToken.substring(7); // remove "Bearer "
        String email;
        try {
            email = myJwtUtil.getEmail(token);
        }
        catch (ExpiredJwtException ex) {
            logger.info("token expired for request: " + uri + ", return token expired error to client");
            response.setStatus(TOKEN_EXPIRED_CODE);
            return;
        }

        // invalid token due to not found email?
        UserDto userDto = authenService.getUserByEmail(email);
        if (userDto == null) {
            logger.error("invalid token because email '" + email + "' not found, return bad request error to client");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        // invalid token?
        if (!myJwtUtil.isTokenCorrect(token, userDto)) {
            logger.info("token invalid for request: " + uri + ", return unauthorized access error to client");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // logout?
        if (uri.equals(URL_LOGOUT)) {
            doLogout(userDto);
            return;
        }

        // authentication ok -> set an Authentication object for Spring Security Context
        setAuthenticationForSpringSecurityContext(userDto);

        chain.doFilter(req, res);
    }


    // Utilities
    private void doActivate(HttpServletRequest request, HttpServletResponse response) {
        String activationToken = request.getParameter(PARAM_ACTIVATION_TOKEN);
        logger.info("activate an Affiliate with token: " + activationToken);

        // activate, generate token and return to client via response header
        LoginDto loginDto;
        try {
            loginDto = authenService.activate(activationToken);
        }
        catch (BusinessException e) {
            logger.error("activation failed!", e);
            response.setStatus(e.getCode());
            return;
        }

        UserDto userDto = new UserDto() {
            {
                this.setId(loginDto.getId());
                this.setEmail(loginDto.getEmail());
                this.setRoles(loginDto.getRoles());
                this.setStatus(loginDto.getStatus());
            }
        };
        String token = generateToken(userDto);
        String headerSecurityToken = "Bearer " + token;
        response.setHeader(HEADER_SECURITY_TOKEN_KEY, headerSecurityToken);
        response.setHeader(HEADER_USER_PROFILE_KEY, MyJsonUtil.gson.toJson(loginDto));

        // set Authentication object for Spring Security Context
        setAuthenticationForSpringSecurityContext(userDto);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) {
        // wrong http method?
        if (!request.getMethod().equals(RequestMethod.POST.toString())) {
            logger.error("wrong http method: " + request.getMethod() + " - only POST supported !");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String username = request.getHeader(HEADER_USERNAME);
        String password = request.getHeader(HEADER_PASSWORD);
        logger.info("log " + username + "/" + password + " in ");

        // empty username/password
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            logger.error("username or password is empty");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // username/password incorrect
        LoginDto loginDto = authenService.authenticate(username, password);
        if (loginDto == null) {
            logger.error("username '" + username + "' or password '" + password + "' is not correct");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // user is inactive
        UserDto userDto = new UserDto() {
            {
                this.setId(loginDto.getId());
                this.setEmail(loginDto.getEmail());
                this.setRoles(loginDto.getRoles());
                this.setStatus(loginDto.getStatus());
            }
        };
        if (userDto.getStatus().equals(User.STATUS_INACTIVE)) {
            logger.error("user is inactive");
            response.setStatus(USER_INACTIVE_CODE);
            return;
        }

        // authentication ok -> generate token and return to client via response header
        // if currently logged -> log out first
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            this.doLogout(userDto);
        }
        String token = generateToken(userDto);      // generate a new token and insert it into db
        String headerSecurityToken = "Bearer " + token;
        response.setHeader(HEADER_SECURITY_TOKEN_KEY, headerSecurityToken);
        beanMapper.map(userDto, loginDto);  // get email, roles
        response.setHeader(HEADER_USER_PROFILE_KEY, MyJsonUtil.gson.toJson(loginDto));

        // set Authentication object for Spring Security Context
        setAuthenticationForSpringSecurityContext(userDto);

        logger.info("log " + username + "/" + password + " in done!");
    }

    private void doLogout(UserDto userDto) {
        logger.info("log user " + userDto.getEmail() + " out");
        SecurityContextHolder.clearContext();
        authenService.deleteToken(userDto.getId());
    }

    private String generateToken(UserDto userDto) {
        // get current token
        String token = userDto.getToken();
        if (token != null) {
            try {
                myJwtUtil.isTokenCorrect(token, userDto);
                return token;
            }
            catch (ExpiredJwtException ex) {
                logger.info("token expired, generate new token");
            }
        }

        // generate new token
        token = myJwtUtil.generateToken(userDto, new Date());
        authenService.insertToken(userDto.getId(), token);

        return token;
    }

    private List<GrantedAuthority> getAuthorities(UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDto.getRoles().stream().forEach(roleName -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(grantedAuthority);
        });

        return authorities;
    }

    private void handleNoNeedTokenRequest (HttpServletRequest request) {
        String headerSecurityToken = request.getHeader(HEADER_SECURITY_TOKEN_KEY);
        if (headerSecurityToken != null && headerSecurityToken.startsWith("Bearer ")) {
            String token = headerSecurityToken.substring(7); // remove "Bearer "

            // token expired?
            String email;
            try {
                email = myJwtUtil.getEmail(token);
            }
            catch (ExpiredJwtException ex) {
                // no need to throw exception here
                return;
            }

            // user not found?
            UserDto userDto = authenService.getUserByEmail(email);
            if (userDto == null) {
                return;
            }

            // token invalid?
            if (!myJwtUtil.isTokenCorrect(token, userDto)) {
                return;
            }

            // token valid, set an Authentication object for Spring Security Context
            setAuthenticationForSpringSecurityContext(userDto);
        }
    }

    private boolean noNeedToken(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String httpMethod = request.getMethod();

        if (uri.contains(URL_DOMAIN)) {
            return true;
        }

        if (uri.contains(URL_RESET_PASSWORD) && httpMethod.equals(RequestMethod.POST.toString())) {
            return true;
        }

        if (uri.contains(URL_SUBSCRIBER_SEARCH) && httpMethod.equals(RequestMethod.POST.toString())) {
            return true;
        }

        if (uri.contains(URL_AFFILIATE_SIGN_UP) && httpMethod.equals(RequestMethod.POST.toString())) {
            return true;
        }

        if (uri.contains(URL_AWS_S3_PRE_SIGNED_URL) && httpMethod.equals(RequestMethod.POST.toString())) {
            return true;
        }

        if (uri.contains(URL_PERSON_RESET_PASSWORD) && httpMethod.equals(RequestMethod.POST.toString())) {
            return true;
        }

        if (uri.contains(URL_INFUSION_CODE)) {
            return true;
        }

        if (uri.contains(URL_SWAGGER_DOCS) || uri.contains(URL_SWAGGER_UI) || uri.contains(URL_SWAGGER_WEBJARS) ||
                uri.contains(URL_SWAGGER_RESOURCES) || uri.contains(URL_SWAGGER_CSRF)) {
            return true;
        }

        return uri.equals(URL_LOGIN_PAGE)
                || uri.equals(URL_APP_PAGE)
                || uri.contains(URL_CSS)
                || uri.contains(URL_IMG)
                || uri.contains(URL_JS)
                || uri.contains(URL_HEALTH)
                || uri.contains(URL_FAVICON);
    }

    private void setAuthenticationForSpringSecurityContext (UserDto userDto) {
        boolean needTakeAction = true;
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthentication != null) {
            UserDto loggedDto = (UserDto) currentAuthentication.getPrincipal();
            if (loggedDto.getEmail().equals(userDto.getEmail())) {       // returning User, take no action
                needTakeAction = false;
            }
        }

        if (needTakeAction) {
            List<GrantedAuthority> authorities = getAuthorities(userDto);
            Authentication authentication = new PreAuthenticatedAuthenticationToken(
                userDto, userDto.getPassword(), authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
