package ru.ltst.u2020mvp.data.api;

import com.squareup.moshi.Moshi;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import ru.ltst.u2020mvp.ApplicationScope;

@Module
public final class ApiModule {
    public static final HttpUrl PRODUCTION_API_URL = HttpUrl.parse("https://api.imgur.com/3/");
    private static final String CLIENT_ID = "3436c108ccc17d3";

    @Provides
    @ClientId
    @ApplicationScope
    String provideClientId() {
        return CLIENT_ID;
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(HttpUrl baseUrl, @Named("Api") OkHttpClient client, Moshi moshi) {
        return new Retrofit.Builder() //
                .client(client) //
                .baseUrl(baseUrl) //
                .addConverterFactory(MoshiConverterFactory.create(moshi)) //
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //
                .build();
    }

    static OkHttpClient.Builder createApiClient(OkHttpClient client, ApiHeaders apiHeaders) {
        return client.newBuilder().addInterceptor(apiHeaders);
    }

}
