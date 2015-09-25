package ru.ltst.u2020mvp.data.api;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import retrofit.Retrofit;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import ru.ltst.u2020mvp.ApplicationScope;

@Module(includes = ApiModule.class)
public final class InternalReleaseApiModule {

    @Provides
    @ApplicationScope
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse(ApiModule.PRODUCTION_API_URL);
    }

    @Provides
    @ApplicationScope
    @Named("Api")
    OkHttpClient provideApiClient(OkHttpClient client, ApiHeaders apiHeaders) {
        client = ApiModule.createApiClient(client, apiHeaders);
        return client;
    }

    @Provides
    @ApplicationScope
    GalleryService provideGalleryService(Retrofit retrofit) {
        return retrofit.create(GalleryService.class);
    }

    @Provides
    @ApplicationScope
    ImageService provideImageService(Retrofit retrofit) {
        return retrofit.create(ImageService.class);
    }
}
