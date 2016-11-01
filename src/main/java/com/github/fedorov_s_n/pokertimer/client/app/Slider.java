package com.github.fedorov_s_n.pokertimer.client.app;

import com.google.gwt.user.client.ui.IsWidget;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.google.gwt.event.dom.client.HasMouseMoveHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.TakesValue;

public class Slider implements TakesValue<Integer> {

    private final SliderBarSimpleHorizontal panel;

    public Slider(final AppState state) {
        panel = new SliderBarSimpleHorizontal(state.timeTotal.get(), "700px", false);
        panel.getDragWidget().setSize("50px", "25px");
        ((HasMouseMoveHandlers) panel.getDragWidget()).addMouseMoveHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(final MouseMoveEvent event) {
                state.timeRemained.set(getValue());
            }
        });
    }

    public void setMaxValue(Integer value) {
        panel.setMaxValue(value);
    }

    public Integer getMaxValue() {
        return panel.getMaxValue();
    }

    @Override
    public void setValue(Integer value) {
        panel.setValue(getMaxValue() - value);
    }

    @Override
    public Integer getValue() {
        return getMaxValue() - panel.getValue();
    }

    public IsWidget asWidget() {
        return panel.asWidget();
    }
}
