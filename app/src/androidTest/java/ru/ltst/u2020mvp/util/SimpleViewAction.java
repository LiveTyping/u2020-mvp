package ru.ltst.u2020mvp.util;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

public abstract class SimpleViewAction <V extends View> implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return CoreMatchers.instanceOf(View.class);
    }

    @Override
    public String getDescription() {
        return "Simple view action";
    }

    @Override
    @SuppressWarnings("unchecked")
    public void perform(UiController uiController, View view) {
        call(uiController, (V) view);
    }

    protected abstract void call(UiController uiController, V view);
}
