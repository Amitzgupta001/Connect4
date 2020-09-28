package com.amit.connect4.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscColor {
    YELLOW("Y"),
    RED("R"),
    NONE("0");
    private String value;

    DiscColor(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DiscColor fromValue(String text) {
        for (DiscColor b : DiscColor.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public static DiscColor fromValue(char text) {
        return fromValue(text + "");
    }
}
