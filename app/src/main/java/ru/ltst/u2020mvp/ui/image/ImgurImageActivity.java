package ru.ltst.u2020mvp.ui.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.base.HasComponent;
import ru.ltst.u2020mvp.base.mvp.BaseActivity;
import ru.ltst.u2020mvp.base.mvp.BasePresenter;
import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreen;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import rx.Observable;
import rx.Subscription;
import timber.log.Timber;

public class ImgurImageActivity extends BaseActivity implements HasComponent<ImgurImageComponent> {

    @Inject Presenter presenter;

    @BindView(R.id.imgur_image_view)
    ImgurImageView view;

    private String imageId;
    private ImgurImageComponent imgurImageComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ActivityScreen.setTransitionView(this.view);
    }

    @Override
    protected void onCreateComponent(U2020Component u2020Component) {
        imgurImageComponent = DaggerImgurImageComponent.builder().
                u2020Component(u2020Component).
                imgurImageModule(new ImgurImageModule(imageId)).build();
        imgurImageComponent.inject(this);
    }

    @Override
    protected void onExtractParams(@NonNull Bundle params) {
        super.onExtractParams(params);
        imageId = getIntent().getStringExtra(Screen.BF_IMAGE_ID);
    }

    @Override
    protected void onDestroy() {
        imgurImageComponent = null;
        super.onDestroy();
    }

    @Override
    protected int viewId() {
        return R.id.imgur_image_view;
    }

    @Override
    protected int layoutId() {
        return R.layout.imgur_image_view;
    }

    @Override
    protected BasePresenter<? extends View> presenter() {
        return presenter;
    }


    @Override
    public ImgurImageComponent getComponent() {
        return imgurImageComponent;
    }

    @ImgurImageScope
    public static class Presenter extends BasePresenter<ImgurImageView> {

        private final Observable<Image> imageObservable;
        private Subscription subscription;

        @Inject
        public Presenter(Observable<Image> imageObservable) {
            this.imageObservable = imageObservable;
        }

        @Override
        protected void onLoad() {
            super.onLoad();
            Timber.d("Loading image");
            getView().showLoading();
            subscription = imageObservable.
                subscribe(
                        image -> {
                            Timber.d("Image loaded with id %s", image.toString());
                            getView().bindTo(image);
                            getView().showContent();
                        },
                        throwable -> {
                            Timber.e(throwable, "Image loading error");
                        }
                );
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            subscription.unsubscribe();
        }
    }

    public static class Screen extends ActivityScreen {

        private static final String BF_IMAGE_ID = "ImgurImageActivity.imageId";

        private final String imageId;

        public Screen(String imageId) {
            this.imageId = imageId;
        }

        @Override
        protected void configureIntent(@NonNull Intent intent) {
            intent.putExtra(BF_IMAGE_ID, imageId);
        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return ImgurImageActivity.class;
        }
    }
}
