package ru.ltst.u2020mvp.util;


import android.support.test.espresso.Espresso;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TimerTestRule implements TestRule {
    private final TimerIdlingResource resource;

    public TimerTestRule() {
        resource = new TimerIdlingResource();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Espresso.registerIdlingResources(resource);
                base.evaluate();
                Espresso.unregisterIdlingResources(resource);
            }
        };
    }

    public void scheduleTimeout(long millis) {
        resource.scheduleTimeout(millis);
    }
}
