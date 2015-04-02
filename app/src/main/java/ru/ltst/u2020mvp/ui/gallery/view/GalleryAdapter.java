package ru.ltst.u2020mvp.ui.gallery.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.ui.misc.BindableAdapter;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private List<Image> images = Collections.emptyList();

    private final Picasso picasso;
    private final LayoutInflater inflater;

    @Nullable
    private OnClickListener onClickListener;

    public GalleryAdapter(Context context, Picasso picasso) {
        this.picasso = picasso;
        inflater = LayoutInflater.from(context);
    }

    public void setOnClickListener(@Nullable OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void replaceWith(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GalleryItemView view = (GalleryItemView) inflater.inflate(R.layout.gallery_view_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindTo(images.get(position), picasso);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onImageClicked(images.get(position), holder.itemView);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        public final GalleryItemView itemView;
        public ViewHolder(GalleryItemView itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bindTo(Image image, Picasso picasso) {
            itemView.bindTo(image, picasso);
        }
    }

    public interface OnClickListener {
        void onImageClicked(Image image, GalleryItemView view);
    }
}
