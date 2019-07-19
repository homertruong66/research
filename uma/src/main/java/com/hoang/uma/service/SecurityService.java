package com.hoang.uma.service;

import com.hoang.uma.common.dto.UserDto;

/**
 * homertruong
 */

public interface SecurityService {

    UserDto authenticate(String email, String password);

    String deleteToken(String userId);

    UserDto getUserByEmail(String email);

    String insertToken(String userId, String token);

}
