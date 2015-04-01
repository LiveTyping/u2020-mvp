package ru.ltst.u2020mvp.ui.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.base.ComponentFinder;
import ru.ltst.u2020mvp.ui.base.HasComponent;

public class ImgurImageView extends ImageView {

    @Inject Picasso picasso;

    public ImgurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImgurImageComponent component = ComponentFinder.findActivityComponent(context);
        component.inject(this);
    }

    public void bindTo(Image image) {
        picasso.load(image.link).into(this);
    }

    public static interface Injector {
        void inject(ImgurImageView view);
    }
}
