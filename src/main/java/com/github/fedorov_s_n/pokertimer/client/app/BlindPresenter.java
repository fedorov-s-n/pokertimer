package com.github.fedorov_s_n.pokertimer.client.app;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.model.ArrowFormatter;
import com.github.fedorov_s_n.pokertimer.client.model.Blind;
import com.github.fedorov_s_n.pokertimer.client.model.TimeFormatter;
import com.github.fedorov_s_n.pokertimer.client.model.IntegerFormatter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsBlindView;
import org.tessell.gwt.dom.client.IsElement;
import org.tessell.model.properties.Property;
import org.tessell.presenter.BasicPresenter;
import static org.tessell.model.dsl.TakesValues.textOf;

public class BlindPresenter extends BasicPresenter<IsBlindView> {

    private final AppState state;
    private final Blind blind;

    public BlindPresenter(AppState state, Blind blind) {
        super(AppViews.newBlindView());
        this.state = state;
        this.blind = blind;
    }

    @Override
    public void onBind() {
        super.onBind();
        binder.bind(blind.active.formatted(new ArrowFormatter())).to(textOf(view.active()));
        add(blind.small.formatted(new IntegerFormatter()), view.small());
        add(blind.big.formatted(new IntegerFormatter()), view.big());
        add(blind.ante.formatted(new IntegerFormatter()), view.ante());
        add(blind.time.formatted(new TimeFormatter()), view.time());
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

    public <T> void add(Property<String> property, IsElement element) {
        view.tr().addAndReplaceElement(
            addPresenter(new FieldPresenter(property)).getView(),
            element
        );
    }
}
