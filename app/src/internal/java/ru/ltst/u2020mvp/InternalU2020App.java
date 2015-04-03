package ru.ltst.u2020mvp;

import javax.inject.Inject;

import ru.ltst.u2020mvp.data.LumberYard;
import timber.log.Timber;

public class InternalU2020App extends U2020App {
    @Inject
    LumberYard lumberYard;

    @Override
    public void onCreate() {
        super.onCreate();

        lumberYard.cleanUp();
        Timber.plant(lumberYard.tree());
    }

    @Override
    public void buildComponentAndInject() {
        super.buildComponentAndInject();
        component().inject(this);
    }
}
