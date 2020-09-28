package com.amit.connect4.common;

public class Desision {
    public static void validateColor(String color) {
        try {
            DiscColor.fromValue(color);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "valid values of color are " + DiscColor.RED + " and " + DiscColor.YELLOW);
        }
    }
}
