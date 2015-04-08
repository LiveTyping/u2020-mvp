package ru.ltst.u2020mvp;

import dagger.Component;
import ru.ltst.u2020mvp.data.InternalReleaseDataModule;
import ru.ltst.u2020mvp.ui.ApplicationScope;
import ru.ltst.u2020mvp.ui.InternalReleaseUiModule;

/**
* The core debug component for u2020 applications
*/
@ApplicationScope
@Component(modules = { U2020AppModule.class, InternalReleaseUiModule.class,
        InternalReleaseDataModule.class })
public interface U2020Component extends InternalU2020Graph {
    /**
     * An initializer that creates the graph from an application.
     */
    final class Initializer {
        static U2020Component init(U2020App app) {
            return DaggerU2020Component.builder()
                    .u2020AppModule(new U2020AppModule(app))
                    .build();
        }
        private Initializer() {} // No instances.
    }
}

