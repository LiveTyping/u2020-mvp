package ru.ltst.u2020mvp.data.api;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;

@Module(includes = ApiModule.class)
public final class ReleaseApiModule {
    private static final String CLIENT_ID = "3436c108ccc17d3";

    @Provides
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(ApiModule.PRODUCTION_API_URL);
    }

    @Provides
    GalleryService provideGalleryService(RestAdapter restAdapter) {
        return restAdapter.create(GalleryService.class);
    }

    @Provides
    ImageService provideImageService(RestAdapter restAdapter) {
        return restAdapter.create(ImageService.class);
    }
}
