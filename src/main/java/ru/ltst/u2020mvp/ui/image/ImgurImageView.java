package ru.ltst.u2020mvp.ui.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import ru.ltst.u2020mvp.Injector;
import ru.ltst.u2020mvp.data.api.model.response.Image;
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
