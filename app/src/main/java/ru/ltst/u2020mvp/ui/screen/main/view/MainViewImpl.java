package ru.ltst.u2020mvp.ui.screen.main.view;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;

import ru.ltst.u2020mvp.base.mvp.BaseView;
import ru.ltst.u2020mvp.ui.misc.EnumAdapter;
import ru.ltst.u2020mvp.ui.misc.SimpleItemSelectedListener;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingAdapter;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingTimespan;

/**
 * Created by Danil on 08.09.2016.
 */
public interface MainViewImpl extends BaseView {
    void bindData(TrendingAdapter trendingAdapter,
                  EnumAdapter<TrendingTimespan> timespanAdapter,
                  SwipeRefreshLayout.OnRefreshListener onRefreshListener,
                  SimpleItemSelectedListener onTimespanItemSelectedListener,
                  NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener);

    void setTimespanPosition(int position);

    void closeDrawer();

    void onNetworkError();
}
