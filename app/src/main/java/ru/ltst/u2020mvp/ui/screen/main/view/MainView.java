package ru.ltst.u2020mvp.ui.screen.main.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.ui.misc.BetterViewAnimator;
import ru.ltst.u2020mvp.ui.misc.DividerItemDecoration;
import ru.ltst.u2020mvp.ui.misc.EnumAdapter;
import ru.ltst.u2020mvp.ui.misc.SimpleItemSelectedListener;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingAdapter;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingTimespan;

import static ru.ltst.u2020mvp.ui.misc.DividerItemDecoration.VERTICAL_LIST;

/**
 * Created by Danil on 07.09.2016.
 */
public class MainView extends DrawerLayout implements MainViewImpl {
    @BindView(R.id.trending_toolbar)
    Toolbar toolbarView;
    @BindView(R.id.trending_timespan)
    Spinner timespanView;
    @BindView(R.id.trending_animator)
    BetterViewAnimator animatorView;
    @BindView(R.id.trending_swipe_refresh)
    SwipeRefreshLayout swipeRefreshView;
    @BindView(R.id.trending_list)
    RecyclerView trendingView;
    @BindView(R.id.trending_loading_message)
    TextView loadingMessageView;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_navigation)
    NavigationView drawer;

    @BindColor(R.color.status_bar)
    int statusBarColor;

    @BindDimen(R.dimen.trending_divider_padding_start)
    float dividerPaddingStart;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        drawerLayout.setStatusBarBackgroundColor(statusBarColor);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        AnimationDrawable ellipsis =
                (AnimationDrawable) ContextCompat.getDrawable(getContext(), R.drawable.dancing_ellipsis);
        loadingMessageView.setCompoundDrawablesWithIntrinsicBounds(null, null, ellipsis, null);
        ellipsis.start();

        toolbarView.setNavigationIcon(R.drawable.menu_icon);
        toolbarView.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        swipeRefreshView.setColorSchemeResources(R.color.accent);

        trendingView.setLayoutManager(new LinearLayoutManager(getContext()));
        trendingView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL_LIST,
                dividerPaddingStart, safeIsRtl()));
    }


    @Override
    public void bindData(TrendingAdapter trendingAdapter,
                         EnumAdapter<TrendingTimespan> timespanAdapter,
                         SwipeRefreshLayout.OnRefreshListener onRefreshListener,
                         SimpleItemSelectedListener onTimespanItemSelectedListener,
                         NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener) {
        timespanView.setAdapter(timespanAdapter);
        trendingView.setAdapter(trendingAdapter);
        swipeRefreshView.setOnRefreshListener(onRefreshListener);
        timespanView.setOnItemSelectedListener(onTimespanItemSelectedListener);
        drawer.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public void setTimespanPosition(int position) {
        timespanView.setSelection(position);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }

    @Override
    public void showLoading() {
        if (animatorView.getDisplayedChildId() != R.id.trending_swipe_refresh) {
            animatorView.setDisplayedChildId(R.id.trending_loading);
        } else {
            // For whatever reason, the SRL's spinner does not draw itself when we call setRefreshing(true)
            // unless it is posted.
            post(() -> {
                swipeRefreshView.setRefreshing(true);
            });
        }
    }

    @Override
    public void showContent() {
        swipeRefreshView.setRefreshing(false);
        animatorView.setDisplayedChildId(R.id.trending_swipe_refresh);
    }

    @Override
    public void showEmpty() {
        swipeRefreshView.setRefreshing(false);
        animatorView.setDisplayedChildId(R.id.trending_empty);
    }

    @Override
    public void showError(Throwable throwable) {
        swipeRefreshView.setRefreshing(false);
        animatorView.setDisplayedChildId(R.id.trending_error);
    }

    @Override
    public void onNetworkError() {
        swipeRefreshView.setRefreshing(false);
        animatorView.setDisplayedChildId(R.id.trending_network_error);
    }

    private boolean safeIsRtl() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isRtl();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isRtl() {
        return getLayoutDirection() == LAYOUT_DIRECTION_RTL;
    }
}
