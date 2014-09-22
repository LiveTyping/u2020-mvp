package ru.ltst.u2020mvp.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;
import com.squareup.spoon.Spoon;

import javax.inject.Inject;

import dagger.ObjectGraph;
import retrofit.MockRestAdapter;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.tests.util.ViewActions;
import ru.ltst.u2020mvp.ui.gallery.GalleryActivity;
import ru.ltst.u2020mvp.ui.gallery.GalleryModule;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isRoot;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

public class GalleryActivityTest extends ActivityInstrumentationTestCase2<GalleryActivity> {
    public static final int WAIT_DELAY = 5000;

    GalleryActivity activity;
    ObjectGraph graph;

    @Inject MockRestAdapter mockRestAdapter;
    @Inject GalleryActivity.Presenter presenter;

    public GalleryActivityTest() {
        super(GalleryActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        graph = TestInjector.inject(activity, this, new Module());
        mockRestAdapter.setDelay(0);
        mockRestAdapter.setErrorPercentage(0);
        mockRestAdapter.setVariancePercentage(0);
    }

    @Override
    public void tearDown() throws Exception {
        graph = null;
        super.tearDown();
    }

    public void testGalleryLoadData() throws Throwable {
        assertNotNull(activity);
        assertNotNull(presenter);
        String tag = "1. Root view visible, progress bar showing";
        onView(isRoot()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        screenshot(tag);
        tag = "2. Grid shown with images loaded";
        onView(isRoot()).perform(ViewActions.waitAtLeast(WAIT_DELAY));
        onView(withId(R.id.gallery_grid)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        screenshot(tag);
    }

    public void testFirstImageClickOpenImageActivity() throws Throwable {
        String tag = "1. Grid shown with images loaded";
        onView(isRoot()).perform(ViewActions.waitAtLeast(WAIT_DELAY));
        screenshot(tag);
        tag = "2. Opened imgur image activity with first image";
        onData(anything()).inAdapterView(withId(R.id.gallery_grid)).atPosition(0).perform(click());
        screenshot(tag);
    }

    public void testListScrolling() throws Throwable {
        String tag = "1. Grid shown with images loaded";
        onView(isRoot()).perform(ViewActions.waitAtLeast(WAIT_DELAY));
        screenshot(tag);
        onView(withId(R.id.gallery_grid)).perform(ViewActions.swipeTop());
        tag = "2. Scrolled to bottom";
        screenshot(tag);
        tag = "3. Scrolled to top";
        onView(withId(R.id.gallery_grid)).perform(ViewActions.swipeBottom());
        screenshot(tag);
    }

    protected void screenshot(String description) throws Throwable {
        Thread.sleep(100);
        Spoon.screenshot(activity, description.replaceAll("[^a-zA-Z0-9_-]", "_"));
    }

    @dagger.Module(
            injects = GalleryActivityTest.class,
            addsTo = GalleryModule.class,
            overrides = true,
            complete = false
    )
    public static class Module {

    }
}
