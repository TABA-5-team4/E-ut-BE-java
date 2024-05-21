package taba.team4.eut.common.utils;

import java.util.Map;

public class ParamUtil {

    public static String getString(Map<String, ?> param, String key) {
        return getProperty(param, key, String.class, null);
    }
    public static String getString(Map<String, ?> param, String key, String defaultValue) {
        return getProperty(param, key, String.class, defaultValue);
    }
    public static int getInt(Map<String, ?> param, String key) {
        return getProperty(param, key, Integer.class, null);
    }
    public static int getInt(Map<String, ?> param, String key, int defaultValue) {
        return getProperty(param, key, Integer.class, defaultValue);
    }

    public static <T> T getProperty(Map<String, ?> param, String key, Class<T> targetType, T defaultValue) {
        return (param.containsKey(key)) ? (T) param.get(key) : defaultValue;
    }


}
