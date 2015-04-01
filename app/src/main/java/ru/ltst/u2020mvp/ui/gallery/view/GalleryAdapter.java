package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.misc.BindableAdapter;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class GalleryAdapter extends BindableAdapter<Image> {
    private List<Image> images = Collections.emptyList();

    private final Picasso picasso;

    public GalleryAdapter(Context context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
    }

    public void replaceWith(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Image getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.gallery_view_image, container, false);
    }

    @Override
    public void bindView(Image item, int position, View view) {
        ((GalleryItemView) view).bindTo(item, picasso);
    }
}
