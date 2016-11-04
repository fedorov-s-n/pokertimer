package com.github.fedorov_s_n.pokertimer.client.app;

import static com.github.fedorov_s_n.pokertimer.client.app.FieldPresenter.replace;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.model.ArrowFormatter;
import com.github.fedorov_s_n.pokertimer.client.model.Blind;
import com.github.fedorov_s_n.pokertimer.client.model.TimeFormatter;
import com.github.fedorov_s_n.pokertimer.client.model.IntegerFormatter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsBlindView;
import org.tessell.presenter.BasicPresenter;
import static org.tessell.model.dsl.TakesValues.textOf;

public class BlindPresenter extends BasicPresenter<IsBlindView> {

    private final AppState state;
    private final Blind blind;
    FieldPresenter small;
    FieldPresenter big;
    FieldPresenter ante;
    FieldPresenter time;

    public BlindPresenter(AppState state, Blind blind) {
        super(AppViews.newBlindView());
        this.state = state;
        this.blind = blind;
    }

    @Override
    public void onBind() {
        super.onBind();
        binder.bind(blind.active.formatted(new ArrowFormatter())).to(textOf(view.active()));
        small = replace(this, view.tr(), blind.small.formatted(new IntegerFormatter()), view.small());
        big = replace(this, view.tr(), blind.big.formatted(new IntegerFormatter()), view.big());
        ante = replace(this, view.tr(), blind.ante.formatted(new IntegerFormatter()), view.ante());
        time = replace(this, view.tr(), blind.time.formatted(new TimeFormatter()), view.time());
        view.buttonRun().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                state.setBlind(blind);
                state.runTimer();
            }
        });
        view.buttonRemove().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                state.blinds.remove(blind);
            }
        });
    }
}
