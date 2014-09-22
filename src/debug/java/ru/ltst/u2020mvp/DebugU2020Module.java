package ru.ltst.u2020mvp;

import dagger.Module;
import ru.ltst.u2020mvp.data.DebugDataModule;
import ru.ltst.u2020mvp.ui.DebugUiModule;

@Module(
        addsTo = U2020Module.class,
        includes = {
                DebugUiModule.class,
                DebugDataModule.class
        },
        overrides = true
)
public final class DebugU2020Module {
}
