package ru.ltst.u2020mvp.data.api;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.ltst.u2020mvp.ApplicationScope;

@ApplicationScope
public final class ApiHeaders implements Interceptor {
    private static final String AUTHORIZATION_PREFIX = "Client-ID";

    private final String authorizationValue;

    @Inject
    public ApiHeaders(@ClientId String clientId) {
        this.authorizationValue = AUTHORIZATION_PREFIX + " " + clientId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.header("Authorization", authorizationValue);
        return chain.proceed(builder.build());
    }
}
