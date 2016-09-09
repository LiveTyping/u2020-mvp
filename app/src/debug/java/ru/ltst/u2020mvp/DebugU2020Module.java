package ru.ltst.u2020mvp;

import dagger.Module;
import dagger.Provides;

@Module
public final class DebugU2020Module {
  // Low-tech flag to force certain debug build behaviors when running in an instrumentation test.
  // This value is used in the creation of singletons so it must be set before the graph is created.
  static boolean instrumentationTest = false;

  @Provides
  @ApplicationScope
  @IsInstrumentationTest
  boolean provideIsInstrumentationTest() {
    return instrumentationTest;
  }
}
