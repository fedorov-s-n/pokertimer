package com.github.fedorov_s_n.pokertimer.client.app;

import static com.google.gwt.event.dom.client.KeyCodes.KEY_ENTER;
import static com.google.gwt.event.dom.client.KeyCodes.KEY_ESCAPE;
import static org.tessell.model.dsl.TakesValues.innerTextOf;
import static org.tessell.model.properties.NewProperty.booleanProperty;
import org.tessell.model.properties.BooleanProperty;
import org.tessell.presenter.BasicPresenter;
import com.github.fedorov_s_n.pokertimer.client.views.AppViews;
import com.github.fedorov_s_n.pokertimer.client.views.IsFieldView;
import org.tessell.gwt.dom.client.IsElement;
import org.tessell.gwt.user.client.ui.IsHTMLPanel;
import org.tessell.gwt.user.client.ui.IsTextBox;
import org.tessell.model.properties.Property;

public class FieldPresenter extends BasicPresenter<IsFieldView> {

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
        binder.when(editing).is(true).hide(view.label());
        binder.when(editing).is(true).show(view.editBox());
        binder.when(editing).is(false).hide(view.editBox());
        binder.when(editing).is(true).run(new Runnable() {
            @Override
            public void run() {
                IsTextBox box = view.editBox();
                String text = property.get();
                box.setText(text);
                box.setSelectionRange(0, text.length());
                box.setFocus(true);
            }
        });

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

    public void edit() {
        editing.set(true);
    }

    public static FieldPresenter replace(
        BasicPresenter presenter,
        IsHTMLPanel panel,
        Property<String> property,
        IsElement element) {
        FieldPresenter field = new FieldPresenter(property);
        presenter.addPresenter(field);
        panel.addAndReplaceElement(field.getView(), element);
        return field;
    }
}
