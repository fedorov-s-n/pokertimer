package com.github.fedorov_s_n.pokertimer.client.model;

import org.tessell.model.properties.PropertyFormatter;

public class TimeFormatter extends PropertyFormatter<Integer, String> {

    @Override
    public String format(Integer value) {
        if (value < 0) {
            throw new IllegalArgumentException("negative time");
        }
        int h = value / 3600;
        int m = (value / 60) % 60;
        int s = value % 60;
        return h == 0
            ? StringHelper.join(":", new int[]{m, s})
            : StringHelper.join(":", new int[]{h, m, s});
    }

    @Override
    public Integer parse(String string) {
        String[] parts = string.split(":");
        switch (parts.length) {
            case 1:
                return parsePart(parts[0]);
            case 2:
                return parsePart(parts[0]) * 60 + parsePart(parts[1]);
            case 3:
                return parsePart(parts[0]) * 3600 + parsePart(parts[1]) * 60 + parsePart(parts[2]);
            default:
                throw new NumberFormatException("not a time");
        }
    }

    private int parsePart(String part) {
        int r = Integer.parseInt(part);
        if (r < 0) {
            throw new NumberFormatException("negative time");
        }
        return r;
    }

    @Override
    public String nullValue() {
        return "00:00";
    }
}
