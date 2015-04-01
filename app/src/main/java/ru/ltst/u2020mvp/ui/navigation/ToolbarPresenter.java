package ru.ltst.u2020mvp.ui.navigation;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;

import ru.ltst.u2020mvp.ui.navigation.activity.ActivityConnector;

public class ToolbarPresenter extends ActivityConnector<Toolbar> {

    public void setMenuItemVisibility(@IdRes int id, boolean visible) {
        Toolbar currentToolbar = getAttachedObject();
        if (currentToolbar == null) {
            return;
        }
        currentToolbar.getMenu().findItem(id).setVisible(visible);
    }

    public void setTitle(@StringRes int resId) {
        Toolbar currentToolbar = getAttachedObject();
        if (currentToolbar == null) {
            return;
        }
        currentToolbar.setTitle(resId);
    }

    public void setTitle(String title) {
        Toolbar currentToolbar = getAttachedObject();
        if (currentToolbar == null) {
            return;
        }
        currentToolbar.setTitle(title);
    }
}
