package com.hvn.spring.service;

import com.hvn.spring.dto.UserDTO;
import com.hvn.spring.model.ReturnValue;


public interface UserService {

	public ReturnValue<UserDTO> findByUserName(String username);

}
