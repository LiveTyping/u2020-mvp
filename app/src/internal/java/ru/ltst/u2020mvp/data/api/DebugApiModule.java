package ru.ltst.u2020mvp.data.api;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.android.AndroidMockValuePersistence;
import ru.ltst.u2020mvp.data.ApiEndpoint;
import ru.ltst.u2020mvp.data.IsMockMode;
import ru.ltst.u2020mvp.data.api.model.MockImageService;
import ru.ltst.u2020mvp.data.prefs.StringPreference;
import ru.ltst.u2020mvp.ui.ApplicationScope;

@Module(includes = ApiModule.class)
public final class DebugApiModule {

    @Provides
    @ApplicationScope
    Endpoint provideEndpoint(@ApiEndpoint StringPreference apiEndpoint) {
        return Endpoints.newFixedEndpoint(apiEndpoint.get());
    }

    @Provides
    @ApplicationScope
    MockRestAdapter provideMockRestAdapter(RestAdapter restAdapter, SharedPreferences preferences) {
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        AndroidMockValuePersistence.install(mockRestAdapter, preferences);
        return mockRestAdapter;
    }

    @Provides
    @ApplicationScope
    GalleryService provideGalleryService(RestAdapter restAdapter, MockRestAdapter mockRestAdapter,
                                         @IsMockMode boolean isMockMode, MockGalleryService mockService) {
        if (isMockMode) {
            return mockRestAdapter.create(GalleryService.class, mockService);
        }
        return restAdapter.create(GalleryService.class);
    }

    @Provides
    @ApplicationScope
    ImageService provideImageService(RestAdapter restAdapter, MockRestAdapter mockRestAdapter,
                                         @IsMockMode boolean isMockMode, MockImageService mockService) {
        if (isMockMode) {
            return mockRestAdapter.create(ImageService.class, mockService);
        }
        return restAdapter.create(ImageService.class);
    }
}
