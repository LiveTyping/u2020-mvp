package ru.ltst.u2020mvp.data;

import android.app.Application;
import android.net.Uri;

import okhttp3.OkHttpClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.data.api.InternalReleaseApiModule;
import ru.ltst.u2020mvp.ApplicationScope;
import timber.log.Timber;

@Module(includes = { DataModule.class, InternalReleaseApiModule.class })
public class InternalReleaseDataModule {

    @Provides
    @ApplicationScope
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences preferences) {
        return RxSharedPreferences.create(preferences);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Application app) {
        return DataModule.createOkHttpClient(app).build();
    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttp3Downloader(client))
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception e) {
                        Timber.e(e, "Failed to load image: %s", uri);
                    }
                })
                .build();
    }
}
