package com.github.fedorov_s_n.pokertimer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.github.fedorov_s_n.pokertimer.client.app.AppPresenter;
import com.github.fedorov_s_n.pokertimer.client.model.AppState;
import com.github.fedorov_s_n.pokertimer.client.resources.AppResources;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.GwtViewsProvider;

public class MainEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        AppResources r = GWT.create(AppResources.class);
        r.base().ensureInjected();
        AppViews.setProvider(new GwtViewsProvider(r.base()));
        AppState state = new AppState();
        AppPresenter p = new AppPresenter(state);
        p.bind();
        RootPanel.get().add(p.getView());
    }
}
