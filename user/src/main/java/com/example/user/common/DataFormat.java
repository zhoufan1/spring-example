package com.example.user.common;

/**
 * @author zhou.fan
 */
public enum DataFormat {
    DatetimeFormat("yyyy-MM-dd HH:mm:ss"),
    DateFormat("yyyy-MM-dd");

    DataFormat(String value) {
        EnumUtils.changeToName(this, value);
    }
}
