package ru.ltst.u2020mvp;

import dagger.Component;
import ru.ltst.u2020mvp.data.DebugDataModule;
import ru.ltst.u2020mvp.ui.DebugUiModule;

/**
 * The core debug component for u2020 applications
 */
@ApplicationScope
@Component(modules = {U2020AppModule.class, DebugUiModule.class, DebugDataModule.class,
        DebugU2020Module.class, DebugActionsModule.class})
public interface U2020Component extends DebugInternalU2020Graph {
    /**
     * An initializer that creates the graph from an application.
     */
    final class Initializer {
        static ru.ltst.u2020mvp.U2020Component init(U2020App app) {
            return DaggerU2020Component.builder()
                    .u2020AppModule(new U2020AppModule(app))
                    .build();
        }

        private Initializer() {
        } // No instances.
    }
}

