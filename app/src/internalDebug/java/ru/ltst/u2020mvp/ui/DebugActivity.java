package ru.ltst.u2020mvp.ui;

import android.app.Activity;
import android.os.Bundle;

import ru.ltst.u2020mvp.R;

public final class DebugActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity);
    }
}
