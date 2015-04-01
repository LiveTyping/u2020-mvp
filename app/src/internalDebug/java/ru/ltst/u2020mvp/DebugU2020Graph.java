package ru.ltst.u2020mvp;

import retrofit.MockRestAdapter;
import ru.ltst.u2020mvp.U2020Graph;

public interface DebugU2020Graph extends U2020Graph {
    MockRestAdapter mockRestAdapter();
}
