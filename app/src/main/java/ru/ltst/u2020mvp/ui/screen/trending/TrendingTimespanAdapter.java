package ru.ltst.u2020mvp.ui.screen.trending;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.ui.misc.EnumAdapter;


public final class TrendingTimespanAdapter extends EnumAdapter<TrendingTimespan> {
    public TrendingTimespanAdapter(Context context) {
        super(context, TrendingTimespan.class);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return inflater.inflate(R.layout.trending_timespan_view, container, false);
    }
}
