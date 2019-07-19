package com.rms.rms.common.view_model;

import org.apache.commons.lang3.StringUtils;

public class ChangePasswordModel {

    private String confirmedNewPassword;
    private String newPassword;
    private String oldPassword;

    public void escapeHtml() {}

    public String validate() {
        String errors = "";

        if (StringUtils.isEmpty(oldPassword)) {
            errors += "'old_password' can't be empty! && ";
        }

        if (StringUtils.isBlank(newPassword)) {
            errors += "'new_password' can't be empty! && ";
        }

        if (StringUtils.isBlank(confirmedNewPassword)) {
            errors += "'confirmed_new_password' can't be empty! && ";
        }

        if (confirmedNewPassword != null && !confirmedNewPassword.equals(newPassword)) {
            errors += "'confirmed_new_password' must same as 'new_password' && ";
        }

        return errors.equals("") ? null : errors;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String toString() {
        return "ResetPasswordModel{" +
                "confirmedNewPassword='" + confirmedNewPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                '}';
    }
}
