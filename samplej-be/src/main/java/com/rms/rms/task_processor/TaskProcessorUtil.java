package com.rms.rms.task_processor;

import com.rms.rms.common.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

public class TaskProcessorUtil {

    public static Authentication createAuthentication(UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDto.getRoles().stream().forEach(roleName -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(grantedAuthority);
        });

        return new PreAuthenticatedAuthenticationToken(userDto, userDto.getPassword(), authorities);
    }
}
