package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.support.v4.util.Pair;
import android.util.AttributeSet;
import android.widget.AbsListView;

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
import rx.Observable;
import rx.android.widget.OnItemClickEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Func1;

public class GalleryView extends BetterViewAnimator implements BaseView {
    @InjectView(R.id.gallery_grid)
    AbsListView galleryView;

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
        galleryView.setAdapter(adapter);
    }

    public GalleryAdapter getAdapter() {
        return adapter;
    }

    public Observable<Pair<Image, GalleryItemView>> observeImageClicks() {
        return WidgetObservable.itemClicks(galleryView).map(new OnItemClickEventToImage(adapter));
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

    private static final class OnItemClickEventToImage implements Func1<OnItemClickEvent, Pair<Image, GalleryItemView>> {

        private final GalleryAdapter adapter;

        private OnItemClickEventToImage(GalleryAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public Pair<Image, GalleryItemView> call(OnItemClickEvent onItemClickEvent) {
            Image image = adapter.getItem(onItemClickEvent.position());
            GalleryItemView view = (GalleryItemView) onItemClickEvent.view();
            return new Pair<>(image, view);
        }
    }

    public interface Injector {
        void inject(GalleryView view);
    }
}
