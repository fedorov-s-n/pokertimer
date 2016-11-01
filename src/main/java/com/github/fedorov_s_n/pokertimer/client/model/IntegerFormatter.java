package com.github.fedorov_s_n.pokertimer.client.model;

import org.tessell.model.properties.PropertyFormatter;

public class IntegerFormatter extends PropertyFormatter<Integer, String> {

    @Override
    public String format(Integer value) {
        return Integer.toString(value);
    }

    @Override
    public Integer parse(String string) {
        return Integer.parseInt(string);
    }
}
