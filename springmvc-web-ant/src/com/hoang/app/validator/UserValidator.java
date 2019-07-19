package com.hoang.app.validator;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.hoang.app.boundary.UserFC;
import com.hoang.app.domain.User;
import com.hoang.app.dto.UserDTO;

/**
 * 
 * @author Hoang Truong
 *
 */

public class UserValidator implements Validator {
	
    private final Logger logger = Logger.getLogger(UserValidator.class);

    private UserFC userFC;

    public UserValidator(UserFC userFC) {
        this.userFC = userFC;
    }

    @SuppressWarnings("rawtypes")
	public boolean supports(Class c) {
        return UserDTO.class.isAssignableFrom(c);
    }

    public void validate(Object o, Errors errors) {
        logger.info("validate(" + o.toString() + ")");

        UserDTO userDTO = (UserDTO) o;
        if (userDTO.getCurrentPassword() == null ){     // Create
            User user = userFC.get(userDTO.getUsername());
            if (user != null) {
                errors.rejectValue("username", "errors.user.username.exist", 
                				   "Username is not available.");
                return;
            }            
        }
        else if (userDTO.getCurrentPassword() != null ){     // Update
            if (!userFC.isCurrentPasswordCorret(userDTO.getUsername(), userDTO.getCurrentPassword())) {
                errors.rejectValue("currentPassword", "errors.user.currentPassword.incorrect", 
                				   "Current password is not correct.");
                return;
            }            
        }

        String password = userDTO.getPassword();
        String confirmedPassword= userDTO.getConfirmedPassword();
        if (!password.equals(confirmedPassword)) {
            errors.rejectValue("password", "errors.user.password.mismatched",
                			   "Password must be matched with confirmed password.");
            return;
        }
    }
}
