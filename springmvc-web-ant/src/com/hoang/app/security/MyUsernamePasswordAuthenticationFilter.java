package com.hoang.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 
 * @author Hoang Truong
 */

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	 
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try {
			super.successfulAuthentication(request, response, authentication);
		} 
        catch (ServletException e) {
			e.printStackTrace();
		}
        request.getSession().setAttribute("authenticated", true);
        System.out.println("==successful login==");
    }
    
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException ,ServletException {
    	super.unsuccessfulAuthentication(request, response, failed);
    	System.out.println("==failed login==");
	}
}
