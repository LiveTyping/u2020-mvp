package ru.ltst.u2020mvp.data.api;

import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import ru.ltst.u2020mvp.ui.ApplicationScope;

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
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides
    @ApplicationScope
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client, ApiHeaders headers) {
        return new RestAdapter.Builder() //
                .setClient(client) //
                .setEndpoint(endpoint) //
                .setRequestInterceptor(headers) //
                .build();
    }
}
