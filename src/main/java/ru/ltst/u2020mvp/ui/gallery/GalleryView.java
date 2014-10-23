package ru.ltst.u2020mvp.ui.gallery;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.ltst.u2020mvp.Injector;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.api.model.response.Gallery;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.misc.BetterViewAnimator;
import rx.Observable;
import rx.android.events.OnItemClickEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Func1;

public class GalleryView extends BetterViewAnimator {
    @InjectView(R.id.gallery_grid)
    AbsListView galleryView;

    @Inject
    Picasso picasso;

    private final GalleryAdapter adapter;

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Injector.inject(context, this);
        adapter = new GalleryAdapter(context, picasso);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        galleryView.setAdapter(adapter);
    }

    public GalleryAdapter getAdapter() {
        return adapter;
    }

    public Observable<Pair<Image, GalleryItemView>> observeImageClicks() {
        return ViewObservable.itemClicks(galleryView).map(new OnItemClickEventToImage(adapter));
    }

    private static class OnItemClickEventToImage implements Func1<OnItemClickEvent, Pair<Image, GalleryItemView>> {
        private GalleryAdapter adapter;

        private OnItemClickEventToImage(GalleryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public Pair<Image, GalleryItemView> call(OnItemClickEvent onItemClickEvent) {
            Image image = adapter.getItem(onItemClickEvent.position);
            GalleryItemView view = (GalleryItemView) onItemClickEvent.view;
            return new Pair<>(image, view);
        }
    }
}
