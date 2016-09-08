package ru.ltst.u2020mvp.ui.misc;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Danil on 08.09.2016.
 */
public abstract class SimpleItemSelectedListener implements AdapterView.OnItemSelectedListener {
    @Override
    final public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        onItemSelected(position);
    }

    @Override
    final public void onNothingSelected(AdapterView<?> parent) {
    }

    public abstract void onItemSelected(int position);
}
