package ru.ltst.u2020mvp;

import ru.ltst.u2020mvp.U2020App;
import ru.ltst.u2020mvp.U2020Module;

final class Modules {
    static Object[] list(U2020App app) {
        return new Object[]{
                new U2020Module(app)
        };
    }

    private Modules() {
        // No instances.
    }
}
