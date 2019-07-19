package com.rms.rms.service;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.service.model.Email;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * homertruong
 */

public interface EmailService {

    String getSystemEmailTemplate() throws IOException;

    String getActivateEmailContent()throws IOException;

    String getActivateEmailTitle () throws IOException;

    String getCreateFirstChannelEmailContent() throws IOException;

    String getCreateFirstChannelEmailTitle() throws IOException;

    String getCreateAffToReferrerEmailContent() throws IOException;

    String getCreateAffToReferrerEmailTitle() throws IOException;

    String getCreateAffToSubsAdminEmailContent() throws IOException;

    String getCreateAffToSubsAdminEmailTitle() throws IOException;

    String getInviteEmailContent ()throws IOException;

    String getInviteEmailTitle ()throws IOException;

    String getRemindEmailContent ()throws IOException;

    String getRemindEmailTitle ()throws IOException;

    String getRenewSubscriberEmailContent ()throws IOException;

    String getRenewSubscriberEmailTitle ()throws IOException;

    String getResetPasswordEmailContent ()throws IOException;

    String getResetPasswordEmailTitle ()throws IOException;

    String getSendVoucherEmailContent ()throws IOException;

    String getSendVoucherEmailTitle ()throws IOException;

    String getSubsAdminCreateEmailContent ()throws IOException;

    String getSubsAdminCreateEmailTitle ()throws IOException;

    String getUpgradeSubscriberEmailContent ()throws IOException;

    String getUpgradeSubscriberEmailTitle ()throws IOException;

    Map<String, String> getType2Titles() throws IOException;

    void send(Email email) throws BusinessException;

    void send(List<Email> emails) throws BusinessException;
}
