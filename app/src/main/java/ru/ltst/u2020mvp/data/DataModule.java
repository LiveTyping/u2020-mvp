package ru.ltst.u2020mvp.data;

import android.app.Application;
import android.content.SharedPreferences;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.data.api.ApiModule;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

@Module(includes = ApiModule.class)
public final class DataModule {
    static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    @Provides
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("u2020", MODE_PRIVATE);
    }

    static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();

        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            Timber.e(e, "Unable to install disk cache.");
        }

        return client;
    }
}
