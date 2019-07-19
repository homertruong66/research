package com.hoang.uma.controller.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoang.uma.common.dto.LoginDto;
import com.hoang.uma.common.dto.UserDto;
import com.hoang.uma.common.util.JwtUtils;
import com.hoang.uma.service.SecurityService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.StringUtils;
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

//@Component -> we have to instantiate this class manually to add to Spring Security filter chain to avoid duplicate
public class TokenAuthenticationFilter extends GenericFilterBean {

	private Logger logger = Logger.getLogger(TokenAuthenticationFilter.class);

	private static final String HEADER_SECURITY_TOKEN_KEY = "X-Security-Token";
    private static final String HEADER_USER_PROFILE_KEY = "X-User-Profile";
    private static final String URL_APP_PAGE = "/app";
    private static final String URL_CSS = "/css";
    private static final String URL_IMG = "/img";
    private static final String URL_JS = "/js";
    private static final String URL_HEALTH = "/health";
    private static final String URL_LOGIN_PAGE = "/";
    private static final String URL_FAVICON = "/favicon.ico";
    private static final String URL_LOGIN = "/login";
	private static final String URL_LOGOUT = "/logout";
    private static final String HEADER_USERNAME = "username";
    private static final String HEADER_PASSWORD = "password";

	private JwtUtils jwtUtils;
    private SecurityService securityService;
    private ModelMapper beanMapper;
    private ObjectMapper jsonMapper = new ObjectMapper();

    public TokenAuthenticationFilter (JwtUtils jwtUtils, SecurityService securityService, ModelMapper beanMapper) {
        this.jwtUtils = jwtUtils;
        this.securityService = securityService;
        this.beanMapper = beanMapper;
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
		throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

        // ignore no need token uri
		String uri = request.getRequestURI();
		if (noNeedToken(uri)) {
            logger.info("ignore allowed URI: '" + uri + "'");
			chain.doFilter(req, res);
			return;
		}

		// check login uri
        if (uri.equals(URL_LOGIN)) {
            String username = request.getHeader(HEADER_USERNAME);
            String password = request.getHeader(HEADER_PASSWORD);
            logger.info("log " + username + "/" + password + " in ");
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                logger.error("username or password is empty");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            UserDto userDto = securityService.authenticate(username, password);
            if (userDto == null) {
                logger.info("username '" + username + "' or password '" + password + "' is not correct" );
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            // set Authentication object for Spring Security
            List<GrantedAuthority> authorities = getAuthorities(userDto);
            Authentication authentication = new PreAuthenticatedAuthenticationToken(userDto.getEmail(), userDto.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // generate token and returns to client via response header
            String token = jwtUtils.generateToken(userDto, new Date());
            String headerSecurityToken = "Bearer " + token;
            response.setHeader(HEADER_SECURITY_TOKEN_KEY, headerSecurityToken);
            LoginDto loginDto = beanMapper.map(userDto, LoginDto.class);
            response.setHeader(HEADER_USER_PROFILE_KEY, jsonMapper.writeValueAsString(loginDto));

            // insert token for current User to db
            securityService.insertToken(userDto.getId(), token);

            return;
        }

		// get token
		String headerSecurityToken = request.getHeader(HEADER_SECURITY_TOKEN_KEY);
		if (headerSecurityToken == null || !headerSecurityToken.startsWith("Bearer ")) {
            logger.error("no security token header found for request: " + uri);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}

		String token = headerSecurityToken.substring(7);   // remove "Bearer "

        // get User
		String email = jwtUtils.getEmail(token);
        UserDto userDto = securityService.getUserByEmail(email);
        if (userDto == null) {
            logger.error("invalid token because email '" + email + "' not found !");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        // check logout
        if (uri.equals(URL_LOGOUT)) {
            logger.info("log current user out");
            securityService.deleteToken(userDto.getId());
            SecurityContextHolder.clearContext();
            return;
        }

        // validate token
		if (userDto.getToken() == null || !jwtUtils.validateToken(token, userDto)) {
            logger.info("token invalid");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}

		// token is valid, set an Authentication object to bypass Spring Security if not exist yet
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities = getAuthorities(userDto);
            Authentication authentication = new PreAuthenticatedAuthenticationToken(userDto.getEmail(), userDto.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }


    // Utilities
    private List<GrantedAuthority> getAuthorities (UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDto.getRoles().stream().forEach(roleStr -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleStr);
            authorities.add(grantedAuthority);
        });

        return authorities;
    }

    private boolean noNeedToken(String uri) {
        return  uri.equals(URL_LOGIN_PAGE) ||
                uri.equals(URL_APP_PAGE) ||
                uri.contains(URL_CSS) ||
                uri.contains(URL_IMG) ||
                uri.contains(URL_JS) ||
                uri.contains(URL_HEALTH) ||
                uri.contains(URL_FAVICON);
    }

}


