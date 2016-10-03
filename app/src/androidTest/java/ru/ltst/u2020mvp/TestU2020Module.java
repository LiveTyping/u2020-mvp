package ru.ltst.u2020mvp;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.IsInstrumentationTest;

@Module
public class TestU2020Module {
    // Low-tech flag to force certain debug build behaviors when running in an instrumentation test.
    // This value is used in the creation of singletons so it must be set before the graph is created.
    static boolean instrumentationTest = true;

    @Provides
    @ApplicationScope
    @IsInstrumentationTest
    boolean provideIsInstrumentationTest() {
        return instrumentationTest;
    }
}
