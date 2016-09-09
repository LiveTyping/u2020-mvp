package ru.ltst.u2020mvp.ui.screen.main;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.mvp.BasePresenter;
import ru.ltst.u2020mvp.base.navigation.activity.ActivityScreenSwitcher;
import ru.ltst.u2020mvp.data.Funcs;
import ru.ltst.u2020mvp.data.IntentFactory;
import ru.ltst.u2020mvp.data.api.GithubService;
import ru.ltst.u2020mvp.data.api.Order;
import ru.ltst.u2020mvp.data.api.Results;
import ru.ltst.u2020mvp.data.api.SearchQuery;
import ru.ltst.u2020mvp.data.api.Sort;
import ru.ltst.u2020mvp.data.api.model.RepositoriesResponse;
import ru.ltst.u2020mvp.data.api.model.Repository;
import ru.ltst.u2020mvp.data.api.transforms.SearchResultToRepositoryList;
import ru.ltst.u2020mvp.ui.misc.EnumAdapter;
import ru.ltst.u2020mvp.ui.misc.SimpleItemSelectedListener;
import ru.ltst.u2020mvp.ui.screen.main.view.MainViewImpl;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingAdapter;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingTimespan;
import ru.ltst.u2020mvp.ui.screen.trending.TrendingTimespanAdapter;
import ru.ltst.u2020mvp.util.Intents;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Danil on 08.09.2016.
 */
@MainScope
public class MainPresenter extends BasePresenter<MainViewImpl>
        implements SwipeRefreshLayout.OnRefreshListener, TrendingAdapter.RepositoryClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String KEY_LAST_TIMESPAN = "MainPresenter.last.timespan";

    private final GithubService githubService;
    private final Picasso picasso;
    private final IntentFactory intentFactory;
    private final Application application;
    private final ActivityScreenSwitcher activityScreenSwitcher;

    private final PublishSubject<TrendingTimespan> timespanSubject;
    private final EnumAdapter<TrendingTimespan> timespanAdapter;
    private final TrendingAdapter trendingAdapter;
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    private MainViewImpl mainView;
    private int lastTimespanPosition = TrendingTimespan.WEEK.ordinal();
    private boolean isFirstStart = true;

    @Inject
    public MainPresenter(GithubService githubService,
                         Picasso picasso,
                         IntentFactory intentFactory,
                         Application application,
                         ActivityScreenSwitcher activityScreenSwitcher) {
        this.githubService = githubService;
        this.picasso = picasso;
        this.intentFactory = intentFactory;
        this.application = application;
        this.activityScreenSwitcher = activityScreenSwitcher;

        timespanSubject = PublishSubject.create();
        timespanAdapter = new TrendingTimespanAdapter(
                new ContextThemeWrapper(application, R.style.Theme_U2020_TrendingTimespan));
        trendingAdapter = new TrendingAdapter(picasso, this);
    }

    @Override
    protected void onLoad(OnActivityResult onActivityResult) {
        mainView = getView();
        Observable<Result<RepositoriesResponse>> result = timespanSubject
                .flatMap(trendingSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .share();
        subscriptions.add(result
                .filter(Results.isSuccessful())
                .map(SearchResultToRepositoryList.instance())
                .subscribe(trendingAdapter));
        subscriptions.add(result
                .filter(Funcs.not(Results.isSuccessful()))
                .subscribe(trendingError));

        // Load the default selection.
        mainView.bindData(trendingAdapter,
                timespanAdapter,
                this,
                new SimpleItemSelectedListener() {
                    @Override
                    public void onItemSelected(int position) {
                        lastTimespanPosition = position;
                        reloadData(position);
                    }
                },
                this);

        trendingAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (trendingAdapter.getItemCount() == 0) {
                    mainView.showEmpty();
                } else {
                    mainView.showContent();
                }
            }
        });
        mainView.setTimespanPosition(lastTimespanPosition);
    }


    @Override
    public void onRefresh() {
        reloadData(lastTimespanPosition);
    }

    private void reloadData(int position) {
        mainView.showLoading();
        timespanSubject.onNext(timespanAdapter.getItem(position));
    }

    @Override
    public void onRepositoryClick(Repository repository) {
        Intents.maybeStartActivity(application, intentFactory.createUrlIntent(repository.html_url));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_search:
//                activityScreenSwitcher.open(new YourActivity.Screen(activityParams));
                Toast.makeText(application, "Search!", LENGTH_SHORT).show();
                break;
            case R.id.nav_trending:
                Toast.makeText(application, "Trending!", LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unknown navigation item: " + item.getTitle());
        }

        mainView.closeDrawer();
        item.setChecked(true);

        return true;
    }

    @Override
    protected void onRestore(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(KEY_LAST_TIMESPAN)) {
            lastTimespanPosition = savedInstanceState.getInt(KEY_LAST_TIMESPAN);
        }
    }

    @Override
    protected void onSave(@NonNull Bundle outState) {
        outState.putInt(KEY_LAST_TIMESPAN, lastTimespanPosition);
    }

    @Override
    public void onNetworkConnectionStateChanged(boolean isConnected) {
        if (isConnected) {
            mainView.setTimespanPosition(lastTimespanPosition);
            mainView.showContent();
        } else {
            mainView.onNetworkError();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }

    private final Func1<TrendingTimespan, Observable<Result<RepositoriesResponse>>> trendingSearch =
            new Func1<TrendingTimespan, Observable<Result<RepositoriesResponse>>>() {
                @Override
                public Observable<Result<RepositoriesResponse>> call(TrendingTimespan trendingTimespan) {
                    SearchQuery trendingQuery = new SearchQuery.Builder() //
                            .createdSince(trendingTimespan.createdSince()) //
                            .build();
                    return githubService.repositories(trendingQuery, Sort.STARS, Order.DESC)
                            .subscribeOn(Schedulers.io());
                }
            };

    private final Action1<Result<RepositoriesResponse>> trendingError = new Action1<Result<RepositoriesResponse>>() {
        @Override
        public void call(Result<RepositoriesResponse> result) {
            if (result.isError()) {
                Timber.e(result.error(), "Failed to get trending repositories");
            } else {
                Response<RepositoriesResponse> response = result.response();
                Timber.e("Failed to get trending repositories. Server returned %d", response.code());
            }
            mainView.showError(result.error());
        }
    };
}
