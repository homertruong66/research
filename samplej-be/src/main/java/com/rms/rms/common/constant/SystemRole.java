package com.rms.rms.common.constant;

import java.util.Set;

/**
 * homertruong
 */

public class SystemRole {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_AFFILIATE = "ROLE_AFFILIATE";
    public static final String ROLE_CHANNEL = "ROLE_CHANNEL";
    public static final String ROLE_SUBS_ADMIN = "ROLE_SUBS_ADMIN";
    public static final String ROLE_ACCOUNTANT = "ROLE_ACCOUNTANT";

    public static boolean hasAdminRole(Set<String> roleStrings) {
        if (roleStrings == null || roleStrings.size() == 0) {
            return false;
        }

        for (String roleString : roleStrings) {
            if (roleString.equals(ROLE_ADMIN)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasAffiliateRole(Set<String> roleStrings) {
        if (roleStrings == null || roleStrings.size() == 0) {
            return false;
        }

        for (String roleString : roleStrings) {
            if (roleString.equals(ROLE_AFFILIATE)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasSubsAdminRole(Set<String> roleStrings) {
        if (roleStrings == null || roleStrings.size() == 0) {
            return false;
        }

        for (String roleString : roleStrings) {
            if (roleString.equals(ROLE_SUBS_ADMIN)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasAccountantRole(Set<String> roleStrings) {
        if (roleStrings == null || roleStrings.size() == 0) {
            return false;
        }

        for (String roleString : roleStrings) {
            if (roleString.equals(ROLE_ACCOUNTANT)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasChannelRole(Set<String> roleStrings) {
        if (roleStrings == null || roleStrings.size() == 0) {
            return false;
        }

        for (String roleString : roleStrings) {
            if (roleString.equals(ROLE_CHANNEL)) {
                return true;
            }
        }

        return false;
    }

}
