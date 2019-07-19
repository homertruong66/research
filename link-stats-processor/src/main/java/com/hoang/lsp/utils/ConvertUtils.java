package com.hoang.lsp.utils;

import com.hoang.lsp.core.GoalType;
import com.hoang.lsp.core.MetricType;

public final class ConvertUtils {

    private ConvertUtils() {}

    public static MetricType convertToMetricType(GoalType goalType) {
        switch (goalType) {
            case PAGE_VIEW:
                return MetricType.PAGE_VIEW;
            case GOAL_1:
                return MetricType.GOAL_1;
            case GOAL_2:
                return MetricType.GOAL_2;
            case GOAL_3:
                return MetricType.GOAL_3;
            case GOAL_4:
                return MetricType.GOAL_4;
            case GOAL_5:
                return MetricType.GOAL_5;
            case GOAL_6:
                return MetricType.GOAL_6;
            case GOAL_7:
                return MetricType.GOAL_7;
            case GOAL_8:
                return MetricType.GOAL_8;
            case GOAL_9:
                return MetricType.GOAL_9;
            case GOAL_10:
                return MetricType.GOAL_10;

            default:
                throw new IllegalArgumentException("invalid goalType:" + goalType);
        }
    }

    public static GoalType convertToGoalType(MetricType metricType) {
        switch (metricType) {
            case PAGE_VIEW:
                return GoalType.PAGE_VIEW;
            case GOAL_1:
                return GoalType.GOAL_1;
            case GOAL_2:
                return GoalType.GOAL_2;
            case GOAL_3:
                return GoalType.GOAL_3;
            case GOAL_4:
                return GoalType.GOAL_4;
            case GOAL_5:
                return GoalType.GOAL_5;
            case GOAL_6:
                return GoalType.GOAL_6;
            case GOAL_7:
                return GoalType.GOAL_7;
            case GOAL_8:
                return GoalType.GOAL_8;
            case GOAL_9:
                return GoalType.GOAL_9;
            case GOAL_10:
                return GoalType.GOAL_10;

            default:
                throw new IllegalArgumentException("invalid metricType:" + metricType);
        }
    }

}
