package ru.ltst.u2020mvp.base.navigation.activity;

import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class NoParamsActivityScreen extends ActivityScreen {
    @Override
    protected final void configureIntent(@NonNull Intent intent) {
        // empty implementation
    }
}
