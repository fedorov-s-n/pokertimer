package com.github.fedorov_s_n.pokertimer.client.app;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.model.Blind;
import com.github.fedorov_s_n.pokertimer.client.model.IntegerFormatter;
import com.github.fedorov_s_n.pokertimer.client.model.TimeFormatter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsBlindsPanelView;
import java.util.List;
import org.tessell.gwt.dom.client.IsElement;
import org.tessell.model.dsl.ListPropertyBinder.ListPresenterFactory;
import org.tessell.presenter.Presenter;
import org.tessell.presenter.BasicPresenter;
import org.tessell.model.properties.Property;

public class BlindsPanelPresenter extends BasicPresenter<IsBlindsPanelView> {

    private final AppState state;

    public BlindsPanelPresenter(AppState state) {
        super(AppViews.newBlindsPanelView());
        this.state = state;
    }

    @Override
    public void onBind() {
        super.onBind();
        add(state.defaultTime.formatted(new TimeFormatter()), view.defaltTime());
        add(state.smallCoeff.formatted(new IntegerFormatter()), view.smallCoeff());
        add(state.anteCoeff.formatted(new IntegerFormatter()), view.anteCoeff());
        binder.bind(state.blinds).to(this, view.blinds(), new ListPresenterFactory<Blind>() {
            public Presenter create(Blind message) {
                return new BlindPresenter(state, message);
            }
        });
        view.buttonAdd().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int sc = state.smallCoeff.get();
                int ac = state.anteCoeff.get();
                List<Blind> bl = state.blinds.get();
                int big = bl.isEmpty() ? 20 : bl.get(bl.size() - 1).big.get() * 2;
                int small = sc == 0 ? 0 : big / sc;
                int ante = ac == 0 ? 0 : big / ac;
                int time = state.defaultTime.get();
                state.blinds.add(new Blind(small, big, ante, time));
            }
        });
        view.buttonRemoveAll().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                state.blinds.clear();
            }
        });
    }

    public <T> void add(Property<String> property, IsElement element) {
        view.panel().addAndReplaceElement(
            addPresenter(new FieldPresenter(property)).getView(),
            element
        );
    }
}
