package io.github.wishsummer.common.core.utils;

import org.apache.commons.lang3.ObjectUtils;

public class FormatUtils {

    public static String convert(Object o, String defaultValue) {
        if (ObjectUtils.isEmpty(o)) {
            return defaultValue;
        }
        if (o instanceof String) {
            return (String) o;
        }
        return o.toString();
    }

    public static String convert(Object o) {
        return convert(o, null);
    }
}
