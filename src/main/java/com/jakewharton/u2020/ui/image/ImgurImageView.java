package com.jakewharton.u2020.ui.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jakewharton.u2020.Injector;
import com.jakewharton.u2020.data.api.model.response.Image;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ImgurImageView extends ImageView {

    @Inject Picasso picasso;
    public ImgurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Injector.inject(context, this);
    }

    public void bindTo(Image image) {
        picasso.load(image.link).into(this);
    }
}
