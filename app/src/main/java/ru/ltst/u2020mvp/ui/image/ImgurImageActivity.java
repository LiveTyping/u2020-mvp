package ru.ltst.u2020mvp.ui.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.base.HasComponent;
import ru.ltst.u2020mvp.ui.base.U2020Activity;
import ru.ltst.u2020mvp.ui.base.ViewPresenter;
import ru.ltst.u2020mvp.ui.navigation.activity.ActivityScreen;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class ImgurImageActivity extends U2020Activity implements HasComponent<ImgurImageComponent> {

    @Inject Presenter presenter;

    @InjectView(R.id.imgur_image_view)
    ImgurImageView view;

    private String imageId;
    private ImgurImageComponent imgurImageComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        ActivityScreen.setTransitionView(this.view);
    }

    @Override
    protected void onCreateComponent(U2020Component u2020Component) {
        imgurImageComponent = Dagger_ImgurImageComponent.builder().
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
    protected void onStart() {
        super.onStart();
        presenter.takeView(view);
    }

    @Override
    protected void onStop() {
        presenter.dropView(view);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        imgurImageComponent = null;
        super.onDestroy();
    }

    @Override
    protected int layoutId() {
        return R.layout.imgur_image_view;
    }

    @Override
    protected ViewPresenter<? extends View> presenter() {
        return presenter;
    }


    @Override
    public ImgurImageComponent getComponent() {
        return imgurImageComponent;
    }

    @ImgurImageScope
    public static class Presenter extends ViewPresenter<ImgurImageView> {

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
            subscription = imageObservable.
                subscribe(
                    new Action1<Image>() {
                        @Override
                        public void call(Image image) {
                            Timber.d("Image loaded with id %s", image.toString());
                            getView().bindTo(image);
                        }
                    },
                    new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Timber.e(throwable, "Image loading error");
                        }
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
