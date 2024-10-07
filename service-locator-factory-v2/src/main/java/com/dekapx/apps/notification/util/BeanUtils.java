package com.dekapx.apps.notification.util;

import lombok.experimental.UtilityClass;

import java.beans.Introspector;

@UtilityClass
public class BeanUtils {
    public static String generateBeanName(final Class clazz) {
        return Introspector.decapitalize(clazz.getSimpleName());
    }
}
