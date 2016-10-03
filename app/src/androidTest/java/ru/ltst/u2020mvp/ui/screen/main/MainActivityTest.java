package ru.ltst.u2020mvp.ui.screen.main;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.f2prateek.rx.preferences.Preference;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.BaseTest;
import ru.ltst.u2020mvp.data.NetworkDelay;
import ru.ltst.u2020mvp.util.RecyclerViewMatcher;
import ru.ltst.u2020mvp.util.TimerTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends BaseTest {
    @Inject
    @NetworkDelay
    Preference<Long> networkDelay;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public TimerTestRule timerTestRule = new TimerTestRule();

    @Before
    public void setup() {
        getTestComponent().inject(this);
        networkDelay.set(500L);
    }

    @Test
    public void swipeTest() {
        //check loading displayed
        onView(withId(R.id.trending_loading))
                .check(matches(isCompletelyDisplayed()));
        timerTestRule.scheduleTimeout(2000);

        //check content displayed
        onView(withId(R.id.trending_swipe_refresh))
                .check(matches(isCompletelyDisplayed()));
        onView(RecyclerViewMatcher.withRecyclerView(R.id.trending_list)
                .atPosition(0))
                .check(matches(isCompletelyDisplayed()));

        //check swipe working
        onView(withId(R.id.trending_list))
                .perform(swipeUp());
        onView(withId(R.id.trending_list))
                .perform(swipeDown());
    }

    @Test
    public void hamburgerMenuTest() {
        //click on navigation icon
        onView(allOf(instanceOf(ImageView.class), withParent(withId(R.id.trending_toolbar))))
                .perform(click());
        onView(withId(R.id.main_navigation))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void swipeToRefreshTest() {
        timerTestRule.scheduleTimeout(1000);
        onView(withId(R.id.trending_swipe_refresh))
                .perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
                .check((view, noViewFoundException) -> ((SwipeRefreshLayout) view).isRefreshing());
    }

    @Test
    public void spinnerTest() {
        onView(withId(R.id.trending_timespan))
                .perform(click());
        onView(withText("today"))
                .check(matches(isCompletelyDisplayed()))
                .perform(click());
        onView(withId(R.id.trending_swipe_refresh))
                .check((view, noViewFoundException) -> ((SwipeRefreshLayout) view).isRefreshing());
    }

    @Test
    public void navigationMenuTest() {
        //shortcut to open navigation menu
        hamburgerMenuTest();
        onView(withText("Search"))
                .perform(click());
        onView(withId(R.id.main_navigation))
                .check(matches(not(isDisplayed())));
    }

    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
}