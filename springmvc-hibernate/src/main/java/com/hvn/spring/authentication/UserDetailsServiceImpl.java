package com.hvn.spring.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.hvn.spring.dto.RoleDTO;
import com.hvn.spring.dto.UserDTO;
import com.hvn.spring.model.ReturnValue;
import com.hvn.spring.model.Role;
import com.hvn.spring.service.UserService;

/**
 * Created by vietnguyenq1 on 1/29/2015.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userAuthen = null;
        ReturnValue<UserDTO> returnValue = userService.findByUserName(username);
        UserDTO userDTO = returnValue.getData();
        if (userDTO != null) {
        	userAuthen = new UserDTO();
            userAuthen.setUsername(userDTO.getUsername());
            userAuthen.setPassword(userDTO.getPassword());
            // Set Role into to List For Author
            List<RoleDTO> roles = new ArrayList<RoleDTO>(0);
            Set<?> set = userDTO.getRoles();
            set.forEach(role -> {
                RoleDTO roleAuthor = new RoleDTO();
                roleAuthor.setName(((Role) role).getName());
                roles.add(roleAuthor);
            });
            userAuthen.setAuthorities(roles);
        }
        return userAuthen;

    }
}
