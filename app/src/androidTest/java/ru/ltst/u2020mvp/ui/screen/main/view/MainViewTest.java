package ru.ltst.u2020mvp.ui.screen.main.view;

import android.accounts.NetworkErrorException;
import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Spinner;

import com.f2prateek.rx.preferences.Preference;

import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import ru.ltst.u2020mvp.base.BaseTest;
import ru.ltst.u2020mvp.data.NetworkDelay;
import ru.ltst.u2020mvp.ui.screen.main.MainActivity;
import ru.ltst.u2020mvp.util.SimpleViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static ru.ltst.u2020mvp.R.id.main_drawer_layout;
import static ru.ltst.u2020mvp.R.id.trending_empty;
import static ru.ltst.u2020mvp.R.id.trending_error;
import static ru.ltst.u2020mvp.R.id.trending_loading;
import static ru.ltst.u2020mvp.R.id.trending_network_error;
import static ru.ltst.u2020mvp.R.id.trending_swipe_refresh;
import static ru.ltst.u2020mvp.R.id.trending_timespan;

public class MainViewTest extends BaseTest {
    @Inject
    @NetworkDelay
    Preference<Long> networkDelay;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        getTestComponent().inject(this);
        networkDelay.set(1000L);
    }

    @Test
    public void setTimespanPosition() throws Exception {
        onView(withId(main_drawer_layout))
            .perform(new SimpleViewAction<MainView>() {
                @Override
                protected void call(UiController uiController, MainView view) {
                    view.setTimespanPosition(1);
                }
            });
        onView(withId(trending_timespan))
                .check((view, noViewFoundException) ->
                        assertEquals(1, ((Spinner) view).getSelectedItemPosition()));
    }

    @Test
    public void showLoading() throws Exception {
        onView(withId(main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        view.showLoading();
                    }
                });
        onView(withId(trending_loading))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void showLoading2() throws Exception {
        onView(withId(main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        view.showContent();
                        view.showLoading();
                    }
                });
    }

    @Test
    public void showContent() throws Exception {
        onView(withId(main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        view.showContent();
                    }
                });
        onView(withId(trending_swipe_refresh))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void showEmpty() throws Exception {
        onView(withId(main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        view.showEmpty();
                    }
                });
        onView(withId(trending_empty))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void showError() throws Exception {
        onView(withId(main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        view.showError(new NetworkErrorException());
                    }
                });
        onView(withId(trending_error))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void onNetworkError() throws Exception {
        onView(withId(main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        view.onNetworkError();
                    }
                });
        onView(withId(trending_network_error))
                .check(matches(isCompletelyDisplayed()));
    }

}