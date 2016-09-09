package ru.ltst.u2020mvp;

import dagger.Component;
import ru.ltst.u2020mvp.data.ReleaseDataModule;
import ru.ltst.u2020mvp.ui.ReleaseUiModule;

/**
* The core release component for u2020 applications
*/
@ApplicationScope
@Component(modules = { U2020AppModule.class, ReleaseUiModule.class, ReleaseDataModule.class })
public interface U2020Component extends U2020Graph {
    /**
     * An initializer that creates the graph from an application.
     */
    final static class Initializer {
        static U2020Component init(U2020App app) {
            return DaggerU2020Component.builder()
                    .u2020AppModule(new U2020AppModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }
}

