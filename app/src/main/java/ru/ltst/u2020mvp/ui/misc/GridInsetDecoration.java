package ru.ltst.u2020mvp.ui.misc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DimenRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ItemDecoration implementation that applies and inset margin
 * around each child of the RecyclerView.
 */
public class GridInsetDecoration extends RecyclerView.ItemDecoration {

    private int mInsets;

    public GridInsetDecoration(Context context, @DimenRes int insets) {
        mInsets = context.getResources().getDimensionPixelSize(insets);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //We can supply forced insets for each item view here in the Rect
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}