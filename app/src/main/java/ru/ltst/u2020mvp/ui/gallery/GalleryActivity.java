package ru.ltst.u2020mvp.ui.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.U2020Component;
import ru.ltst.u2020mvp.data.GalleryDatabase;
import ru.ltst.u2020mvp.data.api.model.request.Section;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.rx.EndlessObserver;
import ru.ltst.u2020mvp.ui.base.HasComponent;
import ru.ltst.u2020mvp.ui.base.U2020Activity;
import ru.ltst.u2020mvp.ui.base.ViewPresenter;
import ru.ltst.u2020mvp.ui.image.ImgurImageActivity;
import ru.ltst.u2020mvp.ui.navigation.activity.ActivityScreen;
import ru.ltst.u2020mvp.ui.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.ui.navigation.activity.NoParamsActivityScreen;
import ru.ltst.u2020mvp.ui.navigation.ScreenSwitcher;
import rx.Subscription;
import rx.functions.Action1;
import timber.log.Timber;

public class GalleryActivity extends U2020Activity implements HasComponent<GalleryComponent> {

    @Inject
    Presenter presenter;

    @InjectView(R.id.gallery_view)
    GalleryView view;

    private GalleryComponent galleryComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
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
        galleryComponent = null;
        super.onDestroy();
    }

    @Override
    protected int layoutId() {
        return R.layout.gallery_view;
    }

    @Override
    protected ViewPresenter<? extends View> presenter() {
        return presenter;
    }

    @Override
    public GalleryComponent getComponent() {
        return galleryComponent;
    }

    @GalleryScope
    public static class Presenter extends ViewPresenter<GalleryView> {

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
            request = galleryDatabase.loadGallery(section, new EndlessObserver<List<Image>>() {
                @Override
                public void onNext(List<Image> images) {
                    getView().getAdapter().replaceWith(images);
                    getView().setDisplayedChildId(R.id.gallery_grid);
                }
            });
            clicks = getView().observeImageClicks().subscribe(
                new Action1<Pair<Image, GalleryItemView>>() {
                    @Override
                    public void call(Pair<Image, GalleryItemView> image) {
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
    }

    public static class Screen extends NoParamsActivityScreen {
        @Override
        protected Class<? extends Activity> activityClass() {
            return GalleryActivity.class;
        }
    }
}
