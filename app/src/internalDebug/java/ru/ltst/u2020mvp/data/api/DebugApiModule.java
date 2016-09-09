package ru.ltst.u2020mvp.data.api;

import com.f2prateek.rx.preferences.Preference;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.ApiEndpoint;
import ru.ltst.u2020mvp.data.IsMockMode;
import ru.ltst.u2020mvp.data.NetworkDelay;
import ru.ltst.u2020mvp.data.NetworkFailurePercent;
import ru.ltst.u2020mvp.data.NetworkVariancePercent;
import ru.ltst.u2020mvp.data.api.mock.MockGithubService;
import ru.ltst.u2020mvp.data.api.oauth.OauthInterceptor;
import timber.log.Timber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Module
public final class DebugApiModule {
    @Provides
    @ApplicationScope
    HttpUrl provideHttpUrl(@ApiEndpoint Preference<String> apiEndpoint) {
        return HttpUrl.parse(apiEndpoint.get());
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").v(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return loggingInterceptor;
    }

    @Provides
    @ApplicationScope
    @Named("Api")
    OkHttpClient provideApiClient(OkHttpClient client,
                                  OauthInterceptor oauthInterceptor,
                                  HttpLoggingInterceptor loggingInterceptor) {
        return ApiModule.createApiClient(client, oauthInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @ApplicationScope
    NetworkBehavior provideBehavior(@NetworkDelay Preference<Long> networkDelay,
                                    @NetworkFailurePercent Preference<Integer> networkFailurePercent,
                                    @NetworkVariancePercent Preference<Integer> networkVariancePercent) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(networkDelay.get(), MILLISECONDS);
        behavior.setFailurePercent(networkFailurePercent.get());
        behavior.setVariancePercent(networkVariancePercent.get());
        return behavior;
    }

    @Provides
    @ApplicationScope
    MockRetrofit provideMockRetrofit(Retrofit retrofit,
                                     NetworkBehavior behavior) {
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    @Provides
    @ApplicationScope
    GithubService provideGithubService(Retrofit retrofit,
                                       @IsMockMode boolean isMockMode, MockGithubService mockService) {
        return isMockMode ? mockService : retrofit.create(GithubService.class);
    }
}
