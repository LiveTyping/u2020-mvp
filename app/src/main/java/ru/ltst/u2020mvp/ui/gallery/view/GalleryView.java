package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.ComponentFinder;
import ru.ltst.u2020mvp.base.mvp.BaseView;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.gallery.GalleryActivity;
import ru.ltst.u2020mvp.ui.gallery.GalleryComponent;
import ru.ltst.u2020mvp.ui.misc.AndroidSubscriptions;
import ru.ltst.u2020mvp.ui.misc.BetterViewAnimator;
import ru.ltst.u2020mvp.ui.misc.GridInsetDecoration;
import ru.ltst.u2020mvp.util.DrawableUtils;
import rx.Observable;
import rx.Subscriber;

public class GalleryView extends LinearLayout implements BaseView {
    public static final int COLUMNS_COUNT = 2;

    @BindView(R.id.gallery_grid) RecyclerView galleryView;
    @BindView(R.id.gallery_animator) BetterViewAnimator animator;
    @BindView(R.id.gallery_toolbar) Toolbar toolbar;
    @BindView(R.id.gallery_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.trending_loading_message) TextView loadingMessageView;

    @Inject Picasso picasso;
    @Inject GalleryActivity.Presenter presenter;

    private final GalleryAdapter adapter;
    private final List<Subscriber<? super Pair<Image, ImageView>>> clickSubscribers = new ArrayList<>();
    private final Observable.OnSubscribe<Pair<Image, ImageView>> clickOnSubscribe = subscriber -> {
        clickSubscribers.add(subscriber);
        subscriber.add(AndroidSubscriptions.unsubscribeInUiThread(() -> clickSubscribers.remove(subscriber)));
    };

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        GalleryComponent component = ComponentFinder.findActivityComponent(context);
        component.inject(this);
        adapter = new GalleryAdapter(getContext(), picasso);
        adapter.setOnClickListener(new GalleryAdapter.OnClickListener() {
            @Override
            public void onImageClicked(Image image, GalleryItemView view) {
                Pair<Image, ImageView> item = new Pair<Image, ImageView>(image, view.image);
                for (Subscriber<? super Pair<Image, ImageView>> subscriber : clickSubscribers) {
                    subscriber.onNext(item);
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        final StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(COLUMNS_COUNT, StaggeredGridLayoutManager.VERTICAL);
        galleryView.setLayoutManager(layout);
        galleryView.setItemAnimator(new DefaultItemAnimator());
        galleryView.addItemDecoration(new GridInsetDecoration(getContext(), R.dimen.grid_inset));
        galleryView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refresh());

        AnimationDrawable ellipsis =
                (AnimationDrawable) DrawableUtils.getDrawable(getContext(), R.drawable.dancing_ellipsis);
        loadingMessageView.setCompoundDrawablesWithIntrinsicBounds(null, null, ellipsis, null);
        ellipsis.start();

    }

    public GalleryAdapter getAdapter() {
        return adapter;
    }

    public Observable<Pair<Image, ImageView>> observeImageClicks() {
        return Observable.create(clickOnSubscribe);
    }

    public void setRefreshed() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        animator.setDisplayedChildId(R.id.trending_loading);
    }

    @Override
    public void showContent() {
        animator.setDisplayedChildId(R.id.gallery_swipe_refresh);
    }

    @Override
    public void showError(Throwable throwable) {
        animator.setDisplayedChildId(R.id.trending_error);
    }

    @Override
    public void showEmpty() {
        animator.setDisplayedChildId(R.id.trending_empty);
    }

    public interface Injector {
        void inject(GalleryView view);
    }

}
