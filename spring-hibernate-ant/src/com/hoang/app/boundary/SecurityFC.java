package com.hoang.app.boundary;

import com.hoang.app.dto.AuthenticationDTO;

public interface SecurityFC {

	public AuthenticationDTO authenticate(String username);
}