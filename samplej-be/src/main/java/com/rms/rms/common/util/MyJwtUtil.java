package com.rms.rms.common.util;

import com.rms.rms.common.config.properties.SecurityProperties;
import com.rms.rms.common.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * homertruong
 */

@Component
public class MyJwtUtil {

    private static final String JWT_CLAIMS_ROLES_KEY = "roles";

    private Logger logger = Logger.getLogger(MyJwtUtil.class);

    @Autowired
    private SecurityProperties securityProperties;

    public String generateToken(UserDto userDto, Date issuedAt) {
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();

        Claims claims = Jwts.claims();
        claims.put(JWT_CLAIMS_ROLES_KEY, userDto.getRoles());
        claims.setSubject(userDto.getEmail());
        claims.setIssuer(userDto.getEmail());
        claims.setIssuedAt(issuedAt);
        claims.setExpiration(new Date(issuedAt.getTime() + jwtProperties.getExpiration()));

        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                   .compact();
    }

    public String getEmail(String token) {
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        String email;
        try {
            email = Jwts.parser()
                        .setSigningKey(jwtProperties.getSecret())
                        .parseClaimsJws(token)
                        .getBody()
                        .getIssuer();
        }
        catch (Exception ex) {
            if (ex instanceof ExpiredJwtException) {
                throw ex;
            }

            logger.error(ex.getMessage());
            email = null;
        }

        return email;
    }

    public Date getExpirationDate(String token) {
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        Date expirationDate = null;
        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(jwtProperties.getSecret())
                                .parseClaimsJws(token)
                                .getBody();
            if (claims != null) {
                expirationDate = claims.getExpiration();
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return expirationDate;
    }

    public List<String> getRoles(String token) {
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception ex) {
            return null;
        }

        Object object = claims.get(JWT_CLAIMS_ROLES_KEY);
        if (object != null) {
            return (List<String>) object;
        }

        return null;
    }

    public boolean isTokenCorrect(String token, UserDto userDto) {
        String email = getEmail(token);
        if (email == null) {
            return false;
        }

        String userEmail = userDto.getEmail();
        if (userEmail == null || !email.equals(userEmail)) {
            return false;
        }

        String userToken = userDto.getToken();
        if (userToken == null || !token.equals(userToken)) {
            return false;
        }

        return true;
    }
}
