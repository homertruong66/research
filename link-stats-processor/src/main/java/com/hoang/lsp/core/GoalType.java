package com.hoang.lsp.core;

public enum GoalType {

    PAGE_VIEW("pageview"),
    GOAL_1("goal_1"),
    GOAL_2("goal_2"),
    GOAL_3("goal_3"),
    GOAL_4("goal_4"),
    GOAL_5("goal_5"),
    GOAL_6("goal_6"),
    GOAL_7("goal_7"),
    GOAL_8("goal_8"),
    GOAL_9("goal_9"),
    GOAL_10("goal_10");

    String value;

    private GoalType (String value) {
        this.value = value;
    }

    public String getValue () {
        return this.value;
    }

    public static GoalType parse (final String value) {
        if ( value == null ) {
            throw new IllegalArgumentException("value can not null");
        }

        switch (value) {
            case "pageview":
                return PAGE_VIEW;
            case "goal_1":
                return GOAL_1;
            case "goal_2":
                return GOAL_2;
            case "goal_3":
                return GOAL_3;
            case "goal_4":
                return GOAL_4;
            case "goal_5":
                return GOAL_5;
            case "goal_6":
                return GOAL_6;
            case "goal_7":
                return GOAL_7;
            case "goal_8":
                return GOAL_8;
            case "goal_9":
                return GOAL_9;
            case "goal_10":
                return GOAL_10;

            default:
                throw new IllegalArgumentException("Illegal value " + value);
        }
    }

}
