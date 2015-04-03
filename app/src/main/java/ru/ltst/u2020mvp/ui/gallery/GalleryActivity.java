package ru.ltst.u2020mvp.ui.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.widget.ImageView;

import java.util.List;

import javax.inject.Inject;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.base.HasComponent;
import ru.ltst.u2020mvp.base.mvp.BaseActivity;
import ru.ltst.u2020mvp.base.mvp.BasePresenter;
import ru.ltst.u2020mvp.base.mvp.BaseView;
import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreen;
import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.base.navigation.activity.NoParamsActivityScreen;
import ru.ltst.u2020mvp.data.GalleryDatabase;
import ru.ltst.u2020mvp.data.api.model.request.Section;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.rx.EndlessObserver;
import ru.ltst.u2020mvp.ui.gallery.view.GalleryItemView;
import ru.ltst.u2020mvp.ui.gallery.view.GalleryView;
import ru.ltst.u2020mvp.ui.image.ImgurImageActivity;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class GalleryActivity extends BaseActivity implements HasComponent<GalleryComponent> {

    @Inject
    Presenter presenter;

    private GalleryComponent galleryComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gallery_activity_title);
    }

    @Override
    protected void onCreateComponent(U2020Component u2020Component) {
        galleryComponent = Dagger_GalleryComponent.builder().
                u2020Component(u2020Component).
                galleryModule(new GalleryModule()).build();
        galleryComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        galleryComponent = null;
        super.onDestroy();
    }

    @Override
    protected int viewId() {
        return R.id.gallery_view;
    }

    @Override
    protected int layoutId() {
        return R.layout.gallery_view;
    }

    @Override
    protected BasePresenter<? extends BaseView> presenter() {
        return presenter;
    }

    @Override
    public GalleryComponent getComponent() {
        return galleryComponent;
    }

    @GalleryScope
    public static class Presenter extends BasePresenter<GalleryView> {

        private final GalleryDatabase galleryDatabase;
        private final ActivityScreenSwitcher screenSwitcher;

        private Section section = Section.HOT;
        private Subscription request;
        private Subscription clicks;

        @Inject
        public Presenter(GalleryDatabase galleryDatabase, ActivityScreenSwitcher screenSwitcher) {
            this.galleryDatabase = galleryDatabase;
            this.screenSwitcher = screenSwitcher;
        }

        @Override
        protected void onLoad() {
            super.onLoad();
            getView().showLoading();
            request = galleryDatabase.loadGallery(section, new EndlessObserver<List<Image>>() {
                @Override
                public void onNext(List<Image> images) {
                    getView().getAdapter().replaceWith(images);
                    getView().showContent();
                }

                @Override
                public void onError(Throwable throwable) {
                    Timber.e(throwable, "Load gallery error");
                    getView().showError(throwable);
                }
            });
            clicks = getView().observeImageClicks().subscribe(
                new Action1<Pair<Image, ImageView>>() {
                    @Override
                    public void call(Pair<Image, ImageView> image) {
                        Timber.d("Image clicked with id = %s", image.first.id);
                        ActivityScreen screen = new ImgurImageActivity.Screen(image.first.id);
                        screen.attachTransitionView(image.second);
                        screenSwitcher.open(screen);
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

        public void refresh() {
            // TODO: implement refreshing
            final GalleryView view = getView();
            if (view != null) {
                view.setRefreshed();
            }
        }
    }

    public static class Screen extends NoParamsActivityScreen {
        @Override
        protected Class<? extends Activity> activityClass() {
            return GalleryActivity.class;
        }
    }
}
