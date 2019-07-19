package com.rms.rms.common.dto;

import java.io.Serializable;

/**
 * homertruong
 */

public class ChannelUserDto extends UserDto implements Serializable {

    private static final long serialVersionUID = -5209493530381414455L;

    private String password;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ChannelUserDto{" +
                "password='" + password + '\'' +
                '}' + super.toString();
    }
}
