package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.mvp.BaseView;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.base.ComponentFinder;
import ru.ltst.u2020mvp.ui.gallery.GalleryComponent;
import ru.ltst.u2020mvp.ui.misc.BetterViewAnimator;
import ru.ltst.u2020mvp.ui.misc.GridInsetDecoration;
import rx.Observable;
import rx.Subscriber;
import rx.android.AndroidSubscriptions;
import rx.functions.Action0;

public class GalleryView extends BetterViewAnimator implements BaseView {
    public static final int COLUMNS_COUNT = 2;

    @InjectView(R.id.gallery_grid)
    RecyclerView galleryView;

    @Inject
    Picasso picasso;

    private final GalleryAdapter adapter;
    private final List<Subscriber<? super Pair<Image, GalleryItemView>>> clickSubscribers = new ArrayList<>();
    private final Observable.OnSubscribe<Pair<Image, GalleryItemView>> clickOnSubscribe = new Observable.OnSubscribe<Pair<Image, GalleryItemView>>() {
        @Override
        public void call(final Subscriber<? super Pair<Image, GalleryItemView>> subscriber) {
            clickSubscribers.add(subscriber);
            subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(new Action0() {
                @Override
                public void call() {
                    clickSubscribers.remove(subscriber);
                }
            }));
        }
    };

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        GalleryComponent component = ComponentFinder.findActivityComponent(context);
        component.inject(this);
        adapter = new GalleryAdapter(getContext(), picasso);
        adapter.setOnClickListener(new GalleryAdapter.OnClickListener() {
            @Override
            public void onImageClicked(Image image, GalleryItemView view) {
                Pair<Image, GalleryItemView> item = new Pair<>(image, view);
                for (Subscriber<? super Pair<Image, GalleryItemView>> subscriber : clickSubscribers) {
                    subscriber.onNext(item);
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        final StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(COLUMNS_COUNT, StaggeredGridLayoutManager.VERTICAL);
        galleryView.setLayoutManager(layout);
        galleryView.setItemAnimator(new DefaultItemAnimator());
        galleryView.addItemDecoration(new GridInsetDecoration(getContext(), R.dimen.grid_inset));
        galleryView.setAdapter(adapter);
    }

    public GalleryAdapter getAdapter() {
        return adapter;
    }

    public Observable<Pair<Image, GalleryItemView>> observeImageClicks() {
        return Observable.create(clickOnSubscribe);
    }

    @Override
    public void showLoading() {
        setDisplayedChildId(R.id.gallery_progress);
    }

    @Override
    public void showContent() {
        setDisplayedChildId(R.id.gallery_grid);
    }

    @Override
    public void showError(Throwable throwable) {
        setDisplayedChildId(R.id.gallery_error_view);
    }

    public interface Injector {
        void inject(GalleryView view);
    }
}
