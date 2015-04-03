package ru.ltst.u2020mvp;

import retrofit.MockRestAdapter;
import ru.ltst.u2020mvp.U2020Graph;
import ru.ltst.u2020mvp.ui.debug.DebugView;

public interface DebugInternalU2020Graph extends InternalU2020Graph {
    MockRestAdapter mockRestAdapter();
    void inject(DebugView view);
}
