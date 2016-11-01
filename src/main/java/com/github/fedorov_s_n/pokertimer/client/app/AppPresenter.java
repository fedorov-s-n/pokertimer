package com.github.fedorov_s_n.pokertimer.client.app;

import static com.github.fedorov_s_n.pokertimer.client.views.AppViews.newAppView;
import static com.github.fedorov_s_n.pokertimer.client.views.AppViews.newInfoView;
import static com.github.fedorov_s_n.pokertimer.client.views.AppViews.newHeaderView;

import org.tessell.gwt.dom.client.IsElement;
import org.tessell.presenter.BasicPresenter;
import org.tessell.presenter.Presenter;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.views.IsAppView;

public class AppPresenter extends BasicPresenter<IsAppView> {

    private final AppState state;

    public AppPresenter(AppState state) {
        super(newAppView());
        this.state = state;
    }

    @Override
    public void onBind() {
        view.root().addAndReplaceElement(newHeaderView(), view.headerPlaceholder());
        add(new MainPanelPresenter(state), view.leftPlaceholder());
        add(new BlindsPanelPresenter(state), view.rightPlaceholder());
        view.root().addAndReplaceElement(newInfoView(), view.infoPlaceholder());
        super.onBind();
    }

    private void add(Presenter p, IsElement placeholder) {
        view.root().addAndReplaceElement(addPresenter(p).getView(), placeholder);
    }

}
