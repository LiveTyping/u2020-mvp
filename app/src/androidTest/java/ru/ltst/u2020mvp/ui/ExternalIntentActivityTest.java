package ru.ltst.u2020mvp.ui;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.BaseTest;
import ru.ltst.u2020mvp.data.IntentFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExternalIntentActivityTest extends BaseTest {
    @Inject
    IntentFactory factory;

    @Rule
    public ActivityTestRule<ExternalIntentActivity> rule = new ActivityTestRule<>(ExternalIntentActivity.class, false, false);
    private Intent launchIntent;

    @Before
    public void setup() {
        getTestComponent().inject(this);
        launchIntent = factory.createUrlIntent("http://google.com");
        rule.launchActivity(launchIntent);
    }

    @Test
    public void generalTest() {
        onView(withId(R.id.action))
                .check(matches(withText("android.intent.action.VIEW")));
        onView(withId(R.id.data))
                .check(matches(withText("http://google.com")));
    }
}