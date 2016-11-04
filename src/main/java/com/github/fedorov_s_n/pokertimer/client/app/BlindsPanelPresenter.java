package com.github.fedorov_s_n.pokertimer.client.app;

import static com.github.fedorov_s_n.pokertimer.client.app.FieldPresenter.replace;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.model.Blind;
import com.github.fedorov_s_n.pokertimer.client.model.IntegerFormatter;
import com.github.fedorov_s_n.pokertimer.client.model.TimeFormatter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsBlindsPanelView;
import java.util.List;
import org.tessell.model.dsl.ListPropertyBinder.ListPresenterFactory;
import org.tessell.presenter.Presenter;
import org.tessell.presenter.BasicPresenter;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class BlindsPanelPresenter extends BasicPresenter<IsBlindsPanelView> {

    private final AppState state;

    public BlindsPanelPresenter(AppState state) {
        super(AppViews.newBlindsPanelView());
        this.state = state;
    }

    @Override
    public void onBind() {
        super.onBind();
        replace(this, view.panel(), state.defaultTime.formatted(new TimeFormatter()), view.defaltTime());
        replace(this, view.panel(), state.smallCoeff.formatted(new IntegerFormatter()), view.smallCoeff());
        replace(this, view.panel(), state.anteCoeff.formatted(new IntegerFormatter()), view.anteCoeff());
        binder.bind(state.blinds).to(this, view.blinds(), new ListPresenterFactory<Blind>() {
            public Presenter create(Blind message) {
                return new BlindPresenter(state, message);
            }
        });
        view.buttonAdd().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addBlind();
            }
        });
        view.buttonRemoveAll().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                state.blinds.clear();
            }
        });
        RootPanel.get().addDomHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                switch (event.getNativeKeyCode()) {
                    case KeyCodes.KEY_P:
                    case KeyCodes.KEY_NUM_PLUS:
                        event.stopPropagation();
                        event.preventDefault();
                        addBlind();
                        break;
                    case KeyCodes.KEY_X:
                    case KeyCodes.KEY_NUM_MINUS:
                        event.stopPropagation();
                        event.preventDefault();
                        state.blinds.clear();
                        break;
                }
            }
        }, KeyDownEvent.getType());
    }

    private void addBlind() {
        List<Blind> bl = state.blinds.get();
        state.blinds.add(new Blind(state, bl.isEmpty()
            ? 10
            : bl.get(bl.size() - 1).big.get() * 2));
        ((BlindPresenter) children().get(children().size() - 1)).big.edit();
    }
}
