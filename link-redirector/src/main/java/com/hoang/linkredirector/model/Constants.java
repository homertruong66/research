package com.hoang.linkredirector.model;

import java.util.List;

import jersey.repackaged.com.google.common.collect.Lists;

public class Constants {
    /* Validator */
    public static final String LOGIN_REGEX  = "^([a-zA-Z0-9_\\-])*$";
    public static final String DOMAIN_REGEX = "^([a-z0-9]([-a-z0-9]*[a-z0-9])?\\.)+((a[cdefgilmnoqrstuwxz]|aero|arpa)" +
                                              "|(b[abdefghijmnorstvwyz]|biz)|(c[acdfghiklmnorsuvxyz]|cat|com|coop)" +
                                              "|d[ejkmoz]|(e[ceghrstu]|edu)|f[ijkmor]|(g[abdefghilmnpqrstuwy]|gov)" +
                                              "|h[kmnrtu]|(i[delmnoqrst]|info|int)|(j[emop]|jobs)|k[eghimnprwyz]|" +
                                              "l[abcikrstuvy]|(m[acdeghklmnopqrstuvwxyz]|mil|mobi|museum)|" +
                                              "(n[acefgilopruz]|name|net)|(om|org)|(p[aefghklmnrstwy]|pro)|qa" +
                                              "|r[eosuw]|s[abcdeghijklmnortvyz]|(t[cdfghjklmnoprtvwz]|travel)" +
                                              "|u[agkmsyz]|v[aceginu]|w[fs]|xxx|y[etu]|z[amw])$";

    /* Project */
    public static final List<String> PROJECT_RESERVED_TITLES =
        Lists.newArrayList("account", "accounts", "add", "admin", "api", "config",
                           "create", "dashboard", "dashboards", "default", "defaults", "domains", "delete", "edit",
                           "explorer", "guiapi", "help", "home", "index", "invite", "link", "linkbuilder", "links",
                           "login", "module", "modules", "new", "null", "pattern", "patterns", "profile", "profiles",
                           "project", "projects", "publisher", "publishers", "report", "reports", "update", "user",
                           "users", "session", "setting", "settings", "share", "shares", "sharing", "signin",
                           "signout", "state", "stats", "subscription", "subscriptions", "support", "validate"
        );

    public static final String PROJECT_CONVERSION_DOMAINS_DEFAULT = "all";

    public static final String PROJECT_ID                      = "id";
    public static final String PROJECT_SUBSCRIPTION_ID         = "subscription_id";
    public static final String PROJECT_API_KEY                 = "api_key";
    public static final String PROJECT_TITLE                   = "title";
    public static final String PROJECT_DEFAULT_DOMAIN          = "default_domain";
    public static final String PROJECT_DEFAULT_DOMAIN_ID       = "default_domain_id";
    public static final String PROJECT_AWESM_APPEND            = "awesm_append";
    public static final String PROJECT_AWESM_APPEND_EXCEPTIONS = "awesm_append_exceptions";
    public static final String PROJECT_CREATED_AT              = "created_at";
    public static final String PROJECT_UPDATED_AT              = "updated_at";

    public static final String PROJECT_DOMAIN_ACCOUNT_ID = "account_id";
    public static final String PROJECT_DOMAIN_HOSTNAME   = "hostname";
    public static final String PROJECT_DOMAIN_CREATED_AT = "created_at";
    public static final String PROJECT_DOMAIN_UPDATED_AT = "updated_at";

    public static final String PROJECT_SETTING_ACCOUNT_ID = "account_id";
    public static final String PROJECT_SETTING_NAME       = "name";
    public static final String PROJECT_SETTING_VALUE      = "value";
    public static final String PROJECT_SETTING_UPDATED_AT = "updated_at";

    /* Domain Settings */
    public static final String DOMAIN_SETTING_ID                 = "id";
    public static final String DOMAIN_SETTING_SUBSCRIPTION_ID    = "subscription_id";
    public static final String DOMAIN_SETTING_HOST_NAME          = "hostname";
    public static final String DOMAIN_SETTING_ROOT_REDIRECT_NAME = "root_redirect";
    public static final String DOMAIN_SETTING_NOT_FOUND_REDIRECT = "not_found_redirect";
    public static final String DOMAIN_SETTING_CREATED_AT         = "created_at";
    public static final String DOMAIN_SETTING_UPDATED_AT         = "updated_at";

}
