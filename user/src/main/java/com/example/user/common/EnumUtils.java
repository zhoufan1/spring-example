package com.example.user.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;

public final class EnumUtils {
    private static Logger LOG = LoggerFactory.getLogger(EnumUtils.class);

    public static <T extends Enum<T>> void changeToName(T enumInstance, String value) {
        try {

            Field fieldName = enumInstance.getDeclaringClass().getSuperclass().getDeclaredField("name");
            fieldName.setAccessible(true);
            fieldName.set(enumInstance, value);
            fieldName.setAccessible(false);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
