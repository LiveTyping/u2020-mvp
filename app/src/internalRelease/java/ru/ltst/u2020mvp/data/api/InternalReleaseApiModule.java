package ru.ltst.u2020mvp.data.api;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import retrofit2.Retrofit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import ru.ltst.u2020mvp.ApplicationScope;

@Module(includes = ApiModule.class)
public final class InternalReleaseApiModule {

    @Provides
    @ApplicationScope
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse(ApiModule.PRODUCTION_API_URL.toString());
    }

    @Provides
    @ApplicationScope
    @Named("Api")
    OkHttpClient provideApiClient(OkHttpClient client, ApiHeaders apiHeaders) {
        return ApiModule.createApiClient(client, apiHeaders).build();
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
