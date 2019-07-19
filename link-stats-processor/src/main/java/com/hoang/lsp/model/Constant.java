package com.hoang.lsp.model;

public class Constant {

    public static final String DATA_PROCESSOR_APP_CONFIG_PATH     = "classpath:spring/data-processor-spring-config.xml";
    public static final String CLICKS_REPROCESSOR_APP_CONFIG_PATH = "classpath:spring/clicks-reprocessor-spring-config.xml";

    public static final String KEY_SEPARATOR = "::";

    public static final String HOURLY_PREFIX = "hourly";
    public static final String TOTALS_PREFIX = "totals";

    public static final String LOCK_PREFIX = "lock.";

    // Create Event Cache Constant
    public static final String CREATE_EVENT_PREFIX = "create";

    public static final String BRANCH_PREFIX = "branch";

    public static final String CREATE_EVENT_KEY_TEMPLATE = CREATE_EVENT_PREFIX + Constant.KEY_SEPARATOR
                                                           + "{0}" + Constant.KEY_SEPARATOR // accountId
                                                           + "{1}";                         // redirectionId

    public static final String CREATE_EVENT_BRANCH_KEY_TEMPLATE = CREATE_EVENT_PREFIX + Constant.KEY_SEPARATOR
                                                                  + BRANCH_PREFIX + Constant.KEY_SEPARATOR
                                                                  + "{0}" + Constant.KEY_SEPARATOR  // accountId
                                                                  + "{1}";                          // redirectionId

    public static final String CREATE_RECENTLY_MODIFIED_KEY = CREATE_EVENT_PREFIX + Constant.KEY_SEPARATOR
                                                              + "recently_modified";

    private Constant () {
    }

}
