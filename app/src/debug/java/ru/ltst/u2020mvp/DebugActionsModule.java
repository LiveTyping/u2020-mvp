package ru.ltst.u2020mvp;


import java.util.LinkedHashSet;
import java.util.Set;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ui.debug.ContextualDebugActions;

import static dagger.Provides.Type.SET_VALUES;

@Module
public final class DebugActionsModule {
    @Provides(type = SET_VALUES)
    Set<ContextualDebugActions.DebugAction> provideDebugActions() {
        Set<ContextualDebugActions.DebugAction> actions = new LinkedHashSet<>();
        return actions;
    }
}
