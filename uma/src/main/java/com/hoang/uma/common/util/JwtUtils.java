package com.hoang.uma.common.util;

import com.hoang.uma.common.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * homertruong
 */

@Component
public class JwtUtils {

    private Logger logger = Logger.getLogger(JwtUtils.class);
    private static final String JWT_CLAIMS_ROLES_KEY = "roles";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UserDto userDto, Date issuedAt) {
        Claims claims = Jwts.claims();
        claims.put(JWT_CLAIMS_ROLES_KEY, userDto.getRoles());
        claims.setSubject(userDto.getEmail());
        claims.setIssuer(userDto.getEmail());
        claims.setIssuedAt(issuedAt);
        claims.setExpiration(new Date(issuedAt.getTime() + expiration));

        return Jwts.builder()
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.HS512, secretKey)
                   .compact();
    }

    public String getEmail(String token) {
        String email;
        try {
            email = Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token)
                        .getBody()
                        .getIssuer();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            email = null;
        }

        return email;
    }

    public Date getExpirationDate(String token) {
        Date expirationDate = null;
        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(secretKey)
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
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
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

    public boolean validateToken(String token, UserDto userDto) {
        String email = getEmail(token);
        Date expiration = getExpirationDate(token);

        if (email == null || expiration == null) {
            return false;
        }

        if (!email.equals(userDto.getEmail())) {
            return false;
        }

        Date now = new Date();
        if (expiration.before(now)) {
            return false;
        }

        if (!token.equals(userDto.getToken())) {
            return false;
        }

        return true;
    }
}
