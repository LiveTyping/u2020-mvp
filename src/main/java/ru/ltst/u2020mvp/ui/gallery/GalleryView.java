package ru.ltst.u2020mvp.ui.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

import ru.ltst.u2020mvp.Injector;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.rx.OperatorViewItemClicks;
import ru.ltst.u2020mvp.ui.misc.BetterViewAnimator;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
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

    public Observable<Image> observeImageClicks() {
        return Observable.create(new OperatorViewItemClicks<>(galleryView)).map(new ItemClicksToImage(adapter));
    }

    private static class ItemClicksToImage implements Func1<OperatorViewItemClicks.ItemClick, Image> {
        private GalleryAdapter adapter;

        private ItemClicksToImage(GalleryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public Image call(OperatorViewItemClicks.ItemClick itemClick) {
            return adapter.getItem(itemClick.position);
        }
    }
}
