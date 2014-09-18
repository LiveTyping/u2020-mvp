package com.jakewharton.u2020.ui.gallery;

import com.jakewharton.u2020.R;
import com.jakewharton.u2020.ui.base.U2020Activity;

public class GalleryActivity extends U2020Activity {

    @Override
    protected Object[] modules() {
        return new Object[]{
            new GalleryModule()
        };
    }

    @Override
    protected int layoutId() {
        return R.layout.gallery_view;
    }
}
