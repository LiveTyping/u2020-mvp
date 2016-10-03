package ru.ltst.u2020mvp.base;

import android.content.Context;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.ltst.u2020mvp.ui.screen.main.MainActivity;
import ru.ltst.u2020mvp.ui.screen.main.MainScope;

@RunWith(AndroidJUnit4.class)
public class ComponentFinderTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void findActivityComponentTest() {
        Context context = activityTestRule.getActivity();
        ViewMatchers.assertThat(ComponentFinder.findActivityComponent(context),
                CoreMatchers.instanceOf(MainScope.MainComponent.class));
    }
}