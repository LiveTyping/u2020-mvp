package ru.ltst.u2020mvp.tests;

import dagger.ObjectGraph;
import ru.ltst.u2020mvp.ui.base.U2020Activity;

public class TestInjector {
    public static ObjectGraph inject(U2020Activity activity, Object object, Object... module) {
        ObjectGraph testGraph =  activity.plus(module);
        testGraph.inject(object);
        return testGraph;
    }
}
