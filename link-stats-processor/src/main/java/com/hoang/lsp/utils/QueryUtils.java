package com.hoang.lsp.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoang.lsp.model.LinkStats;

public class QueryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryUtils.class);

    public static List<String> buildQueryForIncrement (String firstStatement, Map<BigInteger, List<LinkStats>> shares) {
        List<String> queries = new ArrayList<>();
        if ( MapUtils.isNotEmpty(shares) ) {
            for (BigInteger id : shares.keySet()) {
                queries.add(firstStatement.concat(buildMultiRows(shares.get(id))));
            }
        }
        return queries;
    }

    private static String buildMultiRows (List<LinkStats> shares) {
        StringBuilder builder = new StringBuilder();
        if ( CollectionUtils.isNotEmpty(shares) ) {
            for (LinkStats share : shares) {
                builder.
                    append("(").
                    append(share.getAccountId()).append(",").
                    append(share.getRedirectionId()).append(",").
                    append("'" + share.getAwesmId() + "'").append(",").
                    append("'" + share.getClickedDate() + "'").append(",").

                    append(share.getClicksIncrement()).append(",").
                    append(share.getMultiGenClicksIncrement()).append(",").

                    append(share.getGoal1Increment()).append(",").
                    append(share.getMultiGenGoal1Increment()).append(",").
                    append(share.getGoal1ValueIncrement()).append(",").
                    append(share.getMultiGenGoal1ValueIncrement()).append(",").

                    append(share.getGoal2Increment()).append(",").
                    append(share.getMultiGenGoal2Increment()).append(",").
                    append(share.getGoal2ValueIncrement()).append(",").
                    append(share.getMultiGenGoal2ValueIncrement()).append(",").

                    append(share.getGoal3Increment()).append(",").
                    append(share.getMultiGenGoal3Increment()).append(",").
                    append(share.getGoal3ValueIncrement()).append(",").
                    append(share.getMultiGenGoal3ValueIncrement()).append(",").

                    append(share.getGoal4Increment()).append(",").
                    append(share.getMultiGenGoal4Increment()).append(",").
                    append(share.getGoal4ValueIncrement()).append(",").
                    append(share.getMultiGenGoal4ValueIncrement()).append(",").

                    append(share.getGoal5Increment()).append(",").
                    append(share.getMultiGenGoal5Increment()).append(",").
                    append(share.getGoal5ValueIncrement()).append(",").
                    append(share.getMultiGenGoal5ValueIncrement()).append(",").

                    append(share.getGoal6Increment()).append(",").
                    append(share.getMultiGenGoal6Increment()).append(",").
                    append(share.getGoal6ValueIncrement()).append(",").
                    append(share.getMultiGenGoal6ValueIncrement()).append(",").

                    append(share.getGoal7Increment()).append(",").
                    append(share.getMultiGenGoal7Increment()).append(",").
                    append(share.getGoal7ValueIncrement()).append(",").
                    append(share.getMultiGenGoal7ValueIncrement()).append(",").

                    append(share.getGoal8Increment()).append(",").
                    append(share.getMultiGenGoal8Increment()).append(",").
                    append(share.getGoal8ValueIncrement()).append(",").
                    append(share.getMultiGenGoal8ValueIncrement()).append(",").

                    append(share.getGoal9Increment()).append(",").
                    append(share.getMultiGenGoal9Increment()).append(",").
                    append(share.getGoal9ValueIncrement()).append(",").
                    append(share.getMultiGenGoal9ValueIncrement()).append(",").

                    append(share.getGoal10Increment()).append(",").
                    append(share.getMultiGenGoal10Increment()).append(",").
                    append(share.getGoal10ValueIncrement()).append(",").
                    append(share.getMultiGenGoal10ValueIncrement()).append(",").

                    append(share.getPageViewsIncrement()).append(",").
                    append(share.getMultiGenPageviewsIncrement()).append(",").
                    append("now()").
                    append("),");
            }
        }
        return StringUtils.removeEnd(builder.toString(), ",");
    }

    /*public static Object[] convertObjectPropertyValuesToSqlValues (Object obj, CaseFormat caseFormat,
                                                                   final String... properties) {
        String[] convertedProperties = Arrays.copyOf(properties, properties.length);
        switch (caseFormat) {
            case LOWER_UNDERSCORE:
                // convert to LOWER_CAMEL
                for (int i = 0; i < properties.length; i++) {
                    convertedProperties[i] = LOWER_UNDERSCORE.to(LOWER_CAMEL, properties[i]);
                }
            default:
                break;
        }
        // get all the value of properties on object
        Object[] values = new Object[convertedProperties.length];
        for (int i = 0; i < convertedProperties.length; i++) {
                *//*apache common bean utils is used to get object's property value by its name
                Ex: instead of using record.awesm_id()
                    we can use PropertyUtils.getSimpleProperty(record, "awesm_id")
                That allow us to use for loop to get all the values we want
                without calling getX() method manually for each field*//*
            try {
                Object propertyValue = PropertyUtils.getSimpleProperty(obj, convertedProperties[i]);
                if ( !Number.class.isAssignableFrom(propertyValue.getClass()) ) {
                    propertyValue = "'" + propertyValue + "'";
                }
                values[i] = propertyValue;
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
                LOGGER.error("fail to extract sql value from object. Exception={}", e.getMessage());
            }
        }
        return values;
    }*/

}
