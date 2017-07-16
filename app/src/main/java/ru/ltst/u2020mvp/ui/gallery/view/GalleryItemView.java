package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.misc.AspectRatioImageView;

public class GalleryItemView extends CardView {
    @BindView(R.id.gallery_image_image)
    AspectRatioImageView image;
    @BindView(R.id.gallery_image_title)
    TextView title;

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(final Image item, final Picasso picasso) {
        float heightRatio = item.height / (float) item.width;
        image.setHeightRatio(heightRatio);
        image.requestLayout();
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                final int height = image.getMeasuredHeight();
                final int width = image.getMeasuredWidth();
                picasso.load(item.link).resize(width, height).into(image);
                return true;
            }
        });

        title.setText(item.title);
    }
}
