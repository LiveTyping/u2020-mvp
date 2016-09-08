package ru.ltst.u2020mvp.data;

import android.app.Application;
import android.net.Uri;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.InternalReleaseApiModule;
import ru.ltst.u2020mvp.data.api.oauth.AccessToken;
import timber.log.Timber;

@Module(includes = {DataModule.class, InternalReleaseApiModule.class})
public class InternalReleaseDataModule {

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Application app) {
        return DataModule.createOkHttpClient(app).build();
    }

    @Provides
    @ApplicationScope
    IntentFactory provideIntentFactory() {
        return IntentFactory.REAL;
    }


//    @Provides
//    @ApplicationScope
//    @AccessToken
//    Preference<String> provideAccessToken(RxSharedPreferences prefs) {
//        return prefs.getString("access-token");
//    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttp3Downloader(client))
                .listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri))
                .build();
    }
}
