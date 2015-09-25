package ru.ltst.u2020mvp.data.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.mock.MockRetrofit;
import retrofit.mock.NetworkBehavior;
import retrofit.mock.RxJavaBehaviorAdapter;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.ApiEndpoint;
import ru.ltst.u2020mvp.data.IsMockMode;
import ru.ltst.u2020mvp.data.NetworkDelay;
import ru.ltst.u2020mvp.data.NetworkFailurePercent;
import ru.ltst.u2020mvp.data.NetworkVariancePercent;
import ru.ltst.u2020mvp.data.api.mock.MockGalleryService;
import ru.ltst.u2020mvp.data.api.mock.MockImageService;
import ru.ltst.u2020mvp.data.prefs.IntPreference;
import ru.ltst.u2020mvp.data.prefs.LongPreference;
import ru.ltst.u2020mvp.data.prefs.StringPreference;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Module(includes = ApiModule.class)
public final class DebugApiModule {

    @Provides
    @ApplicationScope
    HttpUrl provideHttpUrl(@ApiEndpoint StringPreference apiEndpoint) {
        return HttpUrl.parse(apiEndpoint.get());
    }

    @Provides
    @ApplicationScope
    @Named("Api")
    OkHttpClient provideApiClient(OkHttpClient client,
                                  LoggingInterceptor loggingInterceptor,
                                  ApiHeaders apiHeaders) {
        client = ApiModule.createApiClient(client, apiHeaders);
        client.interceptors().add(loggingInterceptor);
        return client;
    }

    @Provides
    @ApplicationScope
    NetworkBehavior provideBehavior(@NetworkDelay LongPreference networkDelay,
                                    @NetworkFailurePercent IntPreference networkFailurePercent,
                                    @NetworkVariancePercent IntPreference networkVariancePercent) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(networkDelay.get(), MILLISECONDS);
        behavior.setFailurePercent(networkFailurePercent.get());
        behavior.setVariancePercent(networkVariancePercent.get());
        return behavior;
    }

    @Provides
    @ApplicationScope
    MockRetrofit provideMockRetrofit(NetworkBehavior behavior) {
        return new MockRetrofit(behavior, RxJavaBehaviorAdapter.create());
    }

    @Provides
    @ApplicationScope
    GalleryService provideGalleryService(Retrofit retrofit, MockRetrofit mockRetrofit,
                                         @IsMockMode boolean isMockMode, MockGalleryService mockGalleryService) {
        if (isMockMode) {
            return mockRetrofit.create(GalleryService.class, mockGalleryService);
        }
        return retrofit.create(GalleryService.class);
    }

    @Provides
    @ApplicationScope
    ImageService provideImageService(Retrofit retrofit, MockRetrofit mockRetrofit,
                                     @IsMockMode boolean isMockMode, MockImageService mockImageService) {
        if (isMockMode) {
            return mockRetrofit.create(ImageService.class, mockImageService);
        }
        return retrofit.create(ImageService.class);
    }
}
