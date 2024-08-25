package com.will.taskmanager.utils;

import org.springframework.util.ObjectUtils;

public class Utils {

    public static boolean isEmpty(Object object) {
        return object == null || ObjectUtils.isEmpty(object);
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static <T> T nvl(Object originalInstance, T returnIfNull) {
        if (originalInstance instanceof String) {
            if (((String) originalInstance).trim().isEmpty()) {
                return returnIfNull;
            }
        }
        return isEmpty(originalInstance) ? returnIfNull : (T)originalInstance;
    }
}
