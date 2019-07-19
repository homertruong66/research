package com.rms.rms.service;

import com.rms.rms.common.dto.LoginDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;

/**
 * homertruong
 */

public interface AuthenService {

    LoginDto activate(String activateToken) throws BusinessException;

    LoginDto authenticate(String email, String password);

    String deleteToken(String userId);

    UserDto getLoggedUser()throws BusinessException;

    UserDto getUserByEmail(String email);

    String insertToken(String userId, String token);
}
