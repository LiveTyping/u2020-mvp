package com.jakewharton.u2020.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jakewharton.u2020.R;
import com.jakewharton.u2020.data.GalleryDatabase;
import com.jakewharton.u2020.data.api.model.request.Section;
import com.jakewharton.u2020.data.api.model.response.Image;
import com.jakewharton.u2020.data.rx.EndlessObserver;
import com.jakewharton.u2020.ui.base.U2020Activity;
import com.jakewharton.u2020.ui.base.ViewPresenter;
import com.jakewharton.u2020.ui.image.ImgurImageActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class GalleryActivity extends U2020Activity {

    @Inject
    Presenter presenter;

    @InjectView(R.id.gallery_view)
    GalleryView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        return new GalleryModule();
    }

    @Override
    protected int layoutId() {
        return R.layout.gallery_view;
    }

    @Override
    protected ViewPresenter<? extends View> presenter() {
        return presenter;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<GalleryView> {

        @Inject
        GalleryDatabase galleryDatabase;

        private Section section = Section.HOT;
        private Subscription request;
        private Subscription clicks;

        @Inject
        public Presenter(GalleryDatabase galleryDatabase) {
            this.galleryDatabase = galleryDatabase;
        }

        @Override
        protected void onLoad() {
            super.onLoad();
            request = galleryDatabase.loadGallery(section, new EndlessObserver<List<Image>>() {
                @Override
                public void onNext(List<Image> images) {
                    getView().getAdapter().replaceWith(images);
                    getView().setDisplayedChildId(R.id.gallery_grid);
                }
            });
            clicks = getView().observeImageClicks().subscribe(
                new Action1<Image>() {
                    @Override
                    public void call(Image image) {
                        Timber.d("Image clicked with id = %s", image.id);
                        Context context = getView().getContext();
                        Intent intent = ImgurImageActivity.activityIntent(context, image.id);
                        context.startActivity(intent);
                    }
                }
            );
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            request.unsubscribe();
            clicks.unsubscribe();
        }
    }
}
