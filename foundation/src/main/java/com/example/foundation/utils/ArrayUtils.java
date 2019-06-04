package com.example.foundation.utils;

import java.util.List;

/**
 * Created by zhangzhiqin on 2017/6/22.
 */
public final class ArrayUtils {


    private ArrayUtils() {
    }


    /**
     * @param array
     * @param value
     * @return
     */
    public static boolean containsStringValue(String[] array, String value) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (value == null) {
            return false;
        }
        for (int i = 0, len = array.length; i < len; i++) {
            if (value.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStringValue(List<String> list, String value) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (int i = 0,len = list.size(); i < len; i++) {
            if(value.equals(list.get(i))){
                return true;
            }
        }
        return false;
    }

}
