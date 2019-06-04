package com.example.foundation.utils;

/**
 * @author zhou.fan
 */
public enum DataFormat {
    /** 年月日时分秒 **/
    DatetimeFormat("yyyy-MM-dd HH:mm:ss"),
    /** 年月日 **/
    DateFormat("yyyy-MM-dd");

    DataFormat(String value) {
        EnumUtils.changeToName(this, value);
    }
}
