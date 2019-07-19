package com.rms.rms.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * homertruong
 */

@ConfigurationProperties(prefix = "subs-email-templates")
public class SubsEmailTemplateProperties {

    private String affSignUpTitle;
    private String affRemindUpdateBankInfoTitle;
    private String affForgotPasswordTitle;
    private String customerOrderCreatedTitle;
    private String affOrderCreatedTitle;
    private String affOrderApprovedTitle;
    private String affPaymentApprovedTitle;

    private String headerFooterContent;
    private String affSignUpContent;
    private String affRemindUpdateBankInfoContent;
    private String affForgotPasswordContent;
    private String customerOrderCreatedContent;
    private String affOrderCreatedContent;
    private String affOrderApprovedContent;
    private String affPaymentApprovedContent;

    public String getAffSignUpTitle() {
        return affSignUpTitle;
    }

    public void setAffSignUpTitle(String affSignUpTitle) {
        this.affSignUpTitle = affSignUpTitle;
    }

    public String getAffRemindUpdateBankInfoTitle() {
        return affRemindUpdateBankInfoTitle;
    }

    public void setAffRemindUpdateBankInfoTitle(String affRemindUpdateBankInfoTitle) {
        this.affRemindUpdateBankInfoTitle = affRemindUpdateBankInfoTitle;
    }

    public String getAffForgotPasswordTitle() {
        return affForgotPasswordTitle;
    }

    public void setAffForgotPasswordTitle(String affForgotPasswordTitle) {
        this.affForgotPasswordTitle = affForgotPasswordTitle;
    }

    public String getCustomerOrderCreatedTitle() {
        return customerOrderCreatedTitle;
    }

    public void setCustomerOrderCreatedTitle(String customerOrderCreatedTitle) {
        this.customerOrderCreatedTitle = customerOrderCreatedTitle;
    }

    public String getAffOrderCreatedTitle() {
        return affOrderCreatedTitle;
    }

    public void setAffOrderCreatedTitle(String affOrderCreatedTitle) {
        this.affOrderCreatedTitle = affOrderCreatedTitle;
    }

    public String getAffOrderApprovedTitle() {
        return affOrderApprovedTitle;
    }

    public void setAffOrderApprovedTitle(String affOrderApprovedTitle) {
        this.affOrderApprovedTitle = affOrderApprovedTitle;
    }

    public String getAffPaymentApprovedTitle() {
        return affPaymentApprovedTitle;
    }

    public void setAffPaymentApprovedTitle(String affPaymentApprovedTitle) {
        this.affPaymentApprovedTitle = affPaymentApprovedTitle;
    }

    public String getHeaderFooterContent() {
        return headerFooterContent;
    }

    public void setHeaderFooterContent(String headerFooterContent) {
        this.headerFooterContent = headerFooterContent;
    }

    public String getAffSignUpContent() {
        return affSignUpContent;
    }

    public void setAffSignUpContent(String affSignUpContent) {
        this.affSignUpContent = affSignUpContent;
    }

    public String getAffRemindUpdateBankInfoContent() {
        return affRemindUpdateBankInfoContent;
    }

    public void setAffRemindUpdateBankInfoContent(String affRemindUpdateBankInfoContent) {
        this.affRemindUpdateBankInfoContent = affRemindUpdateBankInfoContent;
    }

    public String getAffForgotPasswordContent() {
        return affForgotPasswordContent;
    }

    public void setAffForgotPasswordContent(String affForgotPasswordContent) {
        this.affForgotPasswordContent = affForgotPasswordContent;
    }

    public String getCustomerOrderCreatedContent() {
        return customerOrderCreatedContent;
    }

    public void setCustomerOrderCreatedContent(String customerOrderCreatedContent) {
        this.customerOrderCreatedContent = customerOrderCreatedContent;
    }

    public String getAffOrderCreatedContent() {
        return affOrderCreatedContent;
    }

    public void setAffOrderCreatedContent(String affOrderCreatedContent) {
        this.affOrderCreatedContent = affOrderCreatedContent;
    }

    public String getAffOrderApprovedContent() {
        return affOrderApprovedContent;
    }

    public void setAffOrderApprovedContent(String affOrderApprovedContent) {
        this.affOrderApprovedContent = affOrderApprovedContent;
    }

    public String getAffPaymentApprovedContent() {
        return affPaymentApprovedContent;
    }

    public void setAffPaymentApprovedContent(String affPaymentApprovedContent) {
        this.affPaymentApprovedContent = affPaymentApprovedContent;
    }

}