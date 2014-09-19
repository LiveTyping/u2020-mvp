package com.jakewharton.u2020.data.api;

import android.content.SharedPreferences;

import com.jakewharton.u2020.data.ApiEndpoint;
import com.jakewharton.u2020.data.IsMockMode;
import com.jakewharton.u2020.data.api.model.MockImageService;
import com.jakewharton.u2020.data.api.model.response.Image;
import com.jakewharton.u2020.data.prefs.StringPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.android.AndroidMockValuePersistence;

@Module(
        complete = false,
        library = true,
        overrides = true
)
public final class DebugApiModule {

    @Provides
    @Singleton
    Endpoint provideEndpoint(@ApiEndpoint StringPreference apiEndpoint) {
        return Endpoints.newFixedEndpoint(apiEndpoint.get());
    }

    @Provides
    @Singleton
    MockRestAdapter provideMockRestAdapter(RestAdapter restAdapter, SharedPreferences preferences) {
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        AndroidMockValuePersistence.install(mockRestAdapter, preferences);
        return mockRestAdapter;
    }

    @Provides
    @Singleton
    GalleryService provideGalleryService(RestAdapter restAdapter, MockRestAdapter mockRestAdapter,
                                         @IsMockMode boolean isMockMode, MockGalleryService mockService) {
        if (isMockMode) {
            return mockRestAdapter.create(GalleryService.class, mockService);
        }
        return restAdapter.create(GalleryService.class);
    }

    @Provides
    @Singleton
    ImageService provideImageService(RestAdapter restAdapter, MockRestAdapter mockRestAdapter,
                                         @IsMockMode boolean isMockMode, MockImageService mockService) {
        if (isMockMode) {
            return mockRestAdapter.create(ImageService.class, mockService);
        }
        return restAdapter.create(ImageService.class);
    }
}
