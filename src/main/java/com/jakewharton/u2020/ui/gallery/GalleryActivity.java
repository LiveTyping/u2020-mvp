package com.jakewharton.u2020.ui.gallery;

import android.app.Activity;
import android.os.Bundle;

import com.jakewharton.u2020.R;
import com.jakewharton.u2020.data.GalleryDatabase;
import com.jakewharton.u2020.data.api.Section;
import com.jakewharton.u2020.data.api.model.Image;
import com.jakewharton.u2020.data.rx.EndlessObserver;
import com.jakewharton.u2020.ui.base.ActivityPresenter;
import com.jakewharton.u2020.ui.base.U2020Activity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscription;

public class GalleryActivity extends U2020Activity {

    @Inject
    Presenter presenter;

    @InjectView(R.id.gallery_view)
    GalleryView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        presenter.takeView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.dropView(this);
    }

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

    @Override
    protected ActivityPresenter<? extends Activity> presenter() {
        return presenter;
    }

    @Singleton
    public static class Presenter extends ActivityPresenter<GalleryActivity> {
        @Inject
        GalleryDatabase galleryDatabase;

        private Section section = Section.HOT;
        private Subscription request;

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
                    getView().view.getAdapter().replaceWith(images);
                    getView().view.setDisplayedChildId(R.id.gallery_grid);
                }
            });
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            request.unsubscribe();
        }
    }
}
