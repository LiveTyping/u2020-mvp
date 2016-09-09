package ru.ltst.u2020mvp.data.api;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.oauth.OauthInterceptor;

@Module(includes = ApiModule.class)
public final class ReleaseApiModule {

    @Provides
    @ApplicationScope
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse(ApiModule.PRODUCTION_API_URL.toString());
    }


    @Provides
    @ApplicationScope
    GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }

    @Provides
    @ApplicationScope
    @Named("Api")
    OkHttpClient provideApiClient(OkHttpClient client,
                                  OauthInterceptor oauthInterceptor) {
        return ApiModule.createApiClient(client, oauthInterceptor).build();
    }
}
