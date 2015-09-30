package ru.ltst.u2020mvp.data;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.ApiModule;

import static android.content.Context.MODE_PRIVATE;
import static java.util.concurrent.TimeUnit.SECONDS;

@Module(includes = ApiModule.class)
public final class DataModule {
    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Provides
    @ApplicationScope
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("u2020", MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    Clock provideClock() {
        return Clock.REAL;
    }

    @Provides
    @ApplicationScope
    IntentFactory provideIntentFactory() {
        return IntentFactory.REAL;
    }

    @Provides
    @ApplicationScope
    Gson provideGson() {
        return new Gson();
    }

    static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, SECONDS);
        client.setReadTimeout(10, SECONDS);
        client.setWriteTimeout(10, SECONDS);

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);

        return client;
    }
}
