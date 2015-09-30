package ru.ltst.u2020mvp.data.api;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import ru.ltst.u2020mvp.ApplicationScope;

@Module
public final class ApiModule {
    public static final String PRODUCTION_API_URL = "https://api.imgur.com/3/";
    private static final String CLIENT_ID = "3436c108ccc17d3";

    @Provides
    @ClientId
    @ApplicationScope
    String provideClientId() {
        return CLIENT_ID;
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(HttpUrl baseUrl, @Named("Api") OkHttpClient client, Gson gson) {
        return new Retrofit.Builder() //
                .client(client) //
                .baseUrl(baseUrl) //
                .addConverterFactory(GsonConverterFactory.create(gson)) //
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //
                .build();
    }

    static OkHttpClient createApiClient(OkHttpClient client, ApiHeaders apiHeaders) {
        client = client.clone();
        client.interceptors().add(apiHeaders);
        return client;
    }

}
