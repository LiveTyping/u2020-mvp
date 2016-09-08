package ru.ltst.u2020mvp.data;

import android.app.Application;
import android.content.SharedPreferences;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.ApiModule;
import ru.ltst.u2020mvp.data.api.oauth.AccessToken;

import static android.content.Context.MODE_PRIVATE;

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

//    @Provides
//    @ApplicationScope
//    IntentFactory provideIntentFactory() {
//        return IntentFactory.REAL;
//    }

    @Provides
    @ApplicationScope
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences prefs) {
        return RxSharedPreferences.create(prefs);
    }

    @Provides
    @ApplicationScope
    @AccessToken
    Preference<String> provideAccessToken(RxSharedPreferences prefs) {
        return prefs.getString("access-token");
    }

    @Provides
    @ApplicationScope
    Moshi provideMoshi() {
        return new Moshi.Builder()
                .add(new InstantAdapter())
                .build();
    }

    static OkHttpClient.Builder createOkHttpClient(Application app) {
        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cache(cache);

    }
}
