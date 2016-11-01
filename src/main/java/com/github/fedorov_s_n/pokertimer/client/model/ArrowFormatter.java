package com.github.fedorov_s_n.pokertimer.client.model;

import org.tessell.model.properties.PropertyFormatter;

public class ArrowFormatter extends PropertyFormatter<Boolean, String> {

    @Override
    public String format(Boolean a) {
        return a ? AppState.RUN_SYMBOL : "";
    }

    @Override
    public Boolean parse(String b) throws Exception {
        return AppState.RUN_SYMBOL.equals(b);
    }
}
