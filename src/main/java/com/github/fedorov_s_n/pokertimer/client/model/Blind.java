package com.github.fedorov_s_n.pokertimer.client.model;

import org.tessell.model.properties.BooleanProperty;
import org.tessell.model.properties.IntegerProperty;
import static org.tessell.model.properties.NewProperty.*;

public class Blind {

    public final BooleanProperty active = booleanProperty("active");
    public final IntegerProperty small = integerProperty("small");
    public final IntegerProperty big = integerProperty("big");
    public final IntegerProperty ante = integerProperty("ante");
    public final IntegerProperty time = integerProperty("time");

    public Blind(int small, int big, int ante, int time) {
        this.active.set(false);
        this.small.set(small);
        this.big.set(big);
        this.ante.set(ante);
        this.time.set(time);
    }

}
