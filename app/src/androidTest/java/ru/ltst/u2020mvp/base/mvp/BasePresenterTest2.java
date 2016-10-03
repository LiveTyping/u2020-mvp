package ru.ltst.u2020mvp.base.mvp;


import android.support.test.espresso.UiController;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.ui.screen.main.MainActivity;
import ru.ltst.u2020mvp.ui.screen.main.MainPresenter;
import ru.ltst.u2020mvp.ui.screen.main.view.MainView;
import ru.ltst.u2020mvp.util.SimpleViewAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class BasePresenterTest2 {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    MainPresenter mainPresenter;

    @Before
    public void setup() {
        mainPresenter = activityTestRule.getActivity().getComponent().presenter();
    }

    @Test
    public void takeView() throws Exception {
        assertEquals(true, mainPresenter.hasView());
        mainPresenter.getView(); //assert no errors
    }

    @Test
    public void dropView() throws Exception {
        expectedException.expect(NullPointerException.class);
        mainPresenter.dropView(null);
        onView(withId(R.id.main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        mainPresenter.dropView(view);
                    }
                });
        assertEquals(false, mainPresenter.hasView());
        expectedException.expect(NullPointerException.class);
        mainPresenter.getView();
    }

    @Test
    public void hasView() throws Exception {
        assertEquals(true, mainPresenter.hasView());
        onView(withId(R.id.main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        mainPresenter.dropView(view);
                    }
                });
        assertEquals(false, mainPresenter.hasView());
    }

    @Test
    public void getView() throws Exception {
        assertNotNull(mainPresenter.getView());
        onView(withId(R.id.main_drawer_layout))
                .perform(new SimpleViewAction<MainView>() {
                    @Override
                    protected void call(UiController uiController, MainView view) {
                        mainPresenter.dropView(view);
                    }
                });
        expectedException.expect(NullPointerException.class);
        mainPresenter.getView();
    }
}