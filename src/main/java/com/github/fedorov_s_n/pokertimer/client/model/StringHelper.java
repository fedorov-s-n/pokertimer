package com.github.fedorov_s_n.pokertimer.client.model;

public class StringHelper {

    public static String join(String delimiter, int[] array) {
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(i2s(array[0]));
        for (int i = 1; i < array.length; ++i) {
            builder.append(delimiter);
            builder.append(i2s(array[i]));
        }
        return builder.toString();
    }

    private static String i2s(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return Integer.toString(i);
        }
    }
}
