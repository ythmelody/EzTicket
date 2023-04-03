package com.ezticket.web.activity.util;
import java.util.Map;
import java.util.Set;

public class CompositeQueryForAReport {
//
//    public static String get_aCondition_For_myDB(String columnName, String value) {
//
//        String aCondition = null;
//
//        if (!value.equals("all"))
//            aCondition = columnName + "=" + value;
//        return aCondition + " ";
//    }
//
//    public static String get_WhereCondition(Map<String, String> map) {
//        Set<String> keys = map.keySet();
//        StringBuffer whereCondition = new StringBuffer();
//        int count = 0;
//        for (String key : keys) {
//            String value = map.get(key);
//            if (value != null && value.trim().length() != 0) {
//                count++;
//                String aCondition = get_aCondition_For_myDB(key, value.trim());
//
//                if (count == 1)
//                    whereCondition.append(" where " + aCondition);
//                else
//                    whereCondition.append(" and " + aCondition);
//            }
//        }
//
//        return whereCondition.toString();
//    }
}
