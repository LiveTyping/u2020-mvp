package ru.ltst.u2020mvp;

import ru.ltst.u2020mvp.ui.debug.DebugView;

public interface DebugInternalU2020Graph extends InternalU2020Graph{
    void inject(DebugView view);
    @IsInstrumentationTest boolean isInstrumentationTest();
}
