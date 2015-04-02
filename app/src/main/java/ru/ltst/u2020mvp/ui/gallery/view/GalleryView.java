package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.squareup.picasso.Picasso;

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

public class GalleryView extends BetterViewAnimator implements BaseView {
    public static final int COLUMNS_COUNT = 2;
    @InjectView(R.id.gallery_grid)
    RecyclerView galleryView;

    @Inject
    Picasso picasso;

    private final GalleryAdapter adapter;

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        GalleryComponent component = ComponentFinder.findActivityComponent(context);
        component.inject(this);
        adapter = new GalleryAdapter(getContext(), picasso);
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
        return Observable.empty();
//        return WidgetObservable.itemClicks(galleryView).map(new OnItemClickEventToImage(adapter));
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
//
//    private static final class OnItemClickEventToImage implements Func1<OnItemClickEvent, Pair<Image, GalleryItemView>> {
//
//        private final GalleryAdapter adapter;
//
//        private OnItemClickEventToImage(GalleryAdapter adapter) {
//            this.adapter = adapter;
//        }
//
//        @Override
//        public Pair<Image, GalleryItemView> call(OnItemClickEvent onItemClickEvent) {
//            Image image = adapter.(onItemClickEvent.position());
//            GalleryItemView view = (GalleryItemView) onItemClickEvent.view();
//            return new Pair<>(image, view);
//        }
//    }

    public interface Injector {
        void inject(GalleryView view);
    }
}
