package ru.ltst.u2020mvp.data.api;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Inject;

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
