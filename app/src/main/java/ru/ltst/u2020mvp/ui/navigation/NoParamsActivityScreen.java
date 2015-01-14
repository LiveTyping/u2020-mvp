package ru.ltst.u2020mvp.ui.navigation;

import android.content.Intent;
import android.support.annotation.NonNull;

public abstract class NoParamsActivityScreen extends ActivityScreen {
    @Override
    protected final void configureIntent(@NonNull Intent intent) {
        // empty implementation
    }
}
