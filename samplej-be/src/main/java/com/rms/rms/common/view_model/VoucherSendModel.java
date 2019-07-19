package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public class VoucherSendModel {

    private Set<String> ids;
    private Set<String> customerIds;
    private Set<String> emails;

    public void escapeHtml() {}

    public String validate() {
        StringBuilder errors = new StringBuilder();

        if (CollectionUtils.isEmpty(ids)) {
            errors.append("'ids' can't be empty! && ");
        }

        for (String id : ids) {
            if (StringUtils.isBlank(id)) {
                errors.append("'ids' can't contains empty id! && ");
            }
        }

        if (CollectionUtils.isEmpty(customerIds) && CollectionUtils.isEmpty(emails)) {
            errors.append("'customer_ids' and 'emails' can't be empty together! && ");
        }

        if (!CollectionUtils.isEmpty(customerIds)) {
            for (String customerId : customerIds) {
                if (StringUtils.isBlank(customerId)) {
                    errors.append("'customer_ids' can't contains empty customerId! &&");
                }
            }
        }

        if (!CollectionUtils.isEmpty(emails)) {
            for (String email : emails) {
                if (StringUtils.isBlank(email)) {
                    errors.append("'emails' can't contains empty email! &&");
                }

                if (!MyStringUtil.isEmailFormatCorrect(email)) {
                    errors.append("'emails' has incorrect format email! &&");
                }
            }
        }

        return errors.toString().equals("") ? null : errors.toString();
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public Set<String> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(Set<String> customerIds) {
        this.customerIds = customerIds;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "VoucherSendModel{" +
                "ids=" + ids +
                ", customerIds=" + customerIds +
                ", emails=" + emails +
                '}';
    }
}
