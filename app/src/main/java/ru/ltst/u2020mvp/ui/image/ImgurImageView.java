package ru.ltst.u2020mvp.ui.image;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.mvp.BaseView;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.base.ComponentFinder;
import ru.ltst.u2020mvp.ui.misc.BetterViewAnimator;

public class ImgurImageView extends BetterViewAnimator implements BaseView {

    @Inject Picasso picasso;

    public ImgurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ImgurImageComponent component = ComponentFinder.findActivityComponent(context);
        component.inject(this);
    }

    @BindView(R.id.imgur_image_content)
    ImageView imageView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(Image image) {
        picasso.load(image.link).into(imageView);
    }

    @Override
    public void showLoading() {
        setDisplayedChildId(R.id.imgur_image_progress);
    }

    @Override
    public void showContent() {
        setDisplayedChildId(R.id.imgur_image_content);
    }

    @Override
    public void showEmpty() {
        setDisplayedChildId(R.id.imgur_image_empty_view);
    }

    @Override
    public void showError(Throwable throwable) {
        setDisplayedChildId(R.id.imgur_image_error_view);
    }

    public interface Injector {
        void inject(ImgurImageView view);
    }
}
