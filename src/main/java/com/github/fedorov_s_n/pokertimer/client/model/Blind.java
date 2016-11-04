package com.github.fedorov_s_n.pokertimer.client.model;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import org.tessell.model.properties.BooleanProperty;
import org.tessell.model.properties.IntegerProperty;
import static org.tessell.model.properties.NewProperty.*;

public class Blind {

    public final AppState state;
    public final BooleanProperty active = booleanProperty("active");
    public final IntegerProperty small = integerProperty("small");
    public final IntegerProperty big = integerProperty("big");
    public final IntegerProperty ante = integerProperty("ante");
    public final IntegerProperty time = integerProperty("time");

    public Blind(AppState appState, int bet) {
        state = appState;
        big.addValueChangeHandler(new ValueChangeHandler<Integer>() {
            @Override
            public void onValueChange(ValueChangeEvent<Integer> event) {
                int sc = state.smallCoeff.get();
                int ac = state.anteCoeff.get();
                ante.set(ac == 0 ? 0 : event.getValue() / ac);
                small.set(sc == 0 ? 0 : event.getValue() / sc);
            }
        });
        active.set(false);
        big.set(bet);
        time.set(state.defaultTime.get());
    }

}
