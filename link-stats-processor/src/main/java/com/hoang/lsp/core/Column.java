package com.hoang.lsp.core;

public enum Column {
    CLICKS("clicks"),
    LAST_CLICKED_AT("last_clicked_at"),
    PAGE_VIEWS("pageviews"),
    GOAL_1("goal_1"),
    GOAL_1_VALUE("goal_1_value"),
    GOAL_2("goal_2"),
    GOAL_2_VALUE("goal_2_value"),
    GOAL_3("goal_3"),
    GOAL_3_VALUE("goal_3_value"),
    GOAL_4("goal_4"),
    GOAL_4_VALUE("goal_4_value"),
    GOAL_5("goal_5"),
    GOAL_5_VALUE("goal_5_value"),
    GOAL_6("goal_6"),
    GOAL_6_VALUE("goal_6_value"),
    GOAL_7("goal_7"),
    GOAL_7_VALUE("goal_7_value"),
    GOAL_8("goal_8"),
    GOAL_8_VALUE("goal_8_value"),
    GOAL_9("goal_9"),
    GOAL_9_VALUE("goal_9_value"),
    GOAL_10("goal_10"),
    GOAL_10_VALUE("goal_10_value");

    private String name;

    Column (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }
}
