package ru.ltst.u2020mvp;

import retrofit.MockRestAdapter;
import ru.ltst.u2020mvp.U2020Graph;

public interface DebugInternalU2020Graph extends InternalU2020Graph {
    MockRestAdapter mockRestAdapter();
}
