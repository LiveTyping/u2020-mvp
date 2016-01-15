package ru.ltst.u2020mvp;


import ru.ltst.u2020mvp.data.LumberYard;

public interface InternalU2020Graph extends U2020Graph {
    LumberYard lumberYard();
    void inject(InternalU2020App debugApp);
}
