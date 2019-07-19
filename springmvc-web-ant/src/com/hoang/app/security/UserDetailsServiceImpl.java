package com.hoang.app.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hoang.app.boundary.SecurityFC;
import com.hoang.app.dto.AuthenticationDTO;

/**
 * 
 * @author Hoang Truong
 */

public class UserDetailsServiceImpl implements UserDetailsService {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	private SecurityFC securityService;	
	public void setSecurityService(SecurityFC securityService) {
		this.securityService = securityService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {											
		logger.info("loadUserByUsername('" + username + "')");

		AuthenticationDTO authenticationDTO = null;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			authenticationDTO = securityService.authenticate(username);
			if (authenticationDTO == null) {
				logger.error("User with username '" + username + "' not found in database.");
				throw new UsernameNotFoundException("User not found in database");
			}			
			for (String authorityString : authenticationDTO.getAuthorities()) {
				authorities.add(new GrantedAuthorityImpl(authorityString));
			}	
		}
		catch (DataAccessException e) {
			throw e;
		}
		
		return new User(authenticationDTO.getUsername(), authenticationDTO.getPassword(), true, true, true, true, authorities);
	}
}
