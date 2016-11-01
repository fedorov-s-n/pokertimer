package com.github.fedorov_s_n.pokertimer.client.app;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.model.TimeFormatter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsMainPanelView;
import org.tessell.presenter.BasicPresenter;
import static org.tessell.model.dsl.TakesValues.*;

public class MainPanelPresenter extends BasicPresenter<IsMainPanelView> {

    private final AppState state;

    public MainPanelPresenter(AppState state) {
        super(AppViews.newMainPanelView());
        this.state = state;
    }

    @Override
    public void onBind() {
        super.onBind();
        binder.bind(state.timeRemained.formatted(new TimeFormatter())).to(textOf(view.remaining()));
        binder.bind(state.nextAction).to(textOf(view.buttonToggle()));
        binder.bind(state.blindCaption).to(textOf(view.blinds()));
        view.buttonToggle().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                state.toggleTimer();
            }
        });
        view.buttonReset().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                state.restart();
            }
        });
        final Slider slider = new Slider(state);
        view.panel().addAndReplaceElement(slider.asWidget(), view.slider());
        binder.bind(state.timeRemained).to(slider);
        binder.onChange(state.timeTotal).execute(new Runnable() {
            @Override
            public void run() {
                slider.setMaxValue(state.timeTotal.getValue());
            }
        });
    }
}
