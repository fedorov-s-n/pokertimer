package com.github.fedorov_s_n.pokertimer.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static org.tessell.model.dsl.TakesValues.innerTextOf;
import static org.tessell.model.properties.NewProperty.booleanProperty;
import org.tessell.model.properties.BooleanProperty;
import org.tessell.presenter.BasicPresenter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsFieldView;
import org.tessell.model.properties.Property;

public class FieldPresenter<T> extends BasicPresenter<IsFieldView> {

    private final BooleanProperty editing;
    private final Property<String> property;

    public FieldPresenter(Property<String> property) {
        super(AppViews.newFieldView());
        this.editing = booleanProperty("editing", false);
        this.property = property;
    }

    @Override
    public void onBind() {
        super.onBind();
        binder.bind(property).to(innerTextOf(view.label()));
        binder.onDoubleClick(view.label()).set(editing).to(true);
        binder.onDoubleClick(view.label()).focus(view.editBox());

        binder.when(editing).is(true).hide(view.label());
        binder.when(editing).is(true).show(view.editBox());
        binder.when(editing).is(false).hide(view.editBox());
        binder.when(editing).is(true).set(view.editBox()).to(property);

        binder.onKeyDown(view.editBox(), KEY_ESCAPE).set(editing).to(false);
        binder.onKeyDown(view.editBox(), KEY_ENTER).execute(saveNewValue);
        binder.onBlur(view.editBox()).execute(saveNewValue);
    }

    private final Runnable saveNewValue = new Runnable() {
        public void run() {
            if (editing.isTrue()) {
                property.set(view.editBox().getValue());
                editing.set(false);
            }
        }
    };

}
