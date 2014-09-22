package ru.ltst.u2020mvp.ui.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.base.U2020Activity;
import ru.ltst.u2020mvp.ui.base.ViewPresenter;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class ImgurImageActivity extends U2020Activity {
    private static final String BF_IMAGE_ID = "ImgurImageActivity.imageId";

    public static Intent activityIntent(Context context, @NonNull String imageId) {
        Intent intent = new Intent(context, ImgurImageActivity.class);
        intent.putExtra(BF_IMAGE_ID, imageId);
        return intent;
    }

    @Inject Presenter presenter;

    @InjectView(R.id.imgur_image_view)
    ImgurImageView view;

    private @NonNull String imageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageId = getIntent().getStringExtra(BF_IMAGE_ID);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.takeView(view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dropView(view);
    }


    @Override
    protected Object module() {
        return new ImgurImageModule(imageId);
    }

    @Override
    protected int layoutId() {
        return R.layout.imgur_image_view;
    }

    @Override
    protected ViewPresenter<? extends View> presenter() {
        return presenter;
    }

    @Singleton
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
}
