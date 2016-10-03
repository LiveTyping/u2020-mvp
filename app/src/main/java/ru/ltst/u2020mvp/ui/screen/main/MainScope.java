package ru.ltst.u2020mvp.ui.screen.main;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Component;
import dagger.Module;
import ru.ltst.u2020mvp.U2020Component;

/**
 * Created by Danil on 07.09.2016.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface MainScope {
    @MainScope
    @Component(dependencies = {U2020Component.class}, modules = {MainActivityModule.class})
    interface MainComponent {
        void inject(MainActivity mainActivity);
        MainPresenter presenter();
    }

    @Module
    final class MainActivityModule {
        private final MainActivity mainActivity;

        MainActivityModule(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

    }
}
