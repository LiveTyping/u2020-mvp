package ru.ltst.u2020mvp.data.api.mock;


import java.util.Arrays;
import java.util.Collections;

import ru.ltst.u2020mvp.data.api.model.RepositoriesResponse;

import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.BUTTERKNIFE;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.DAGGER;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.JAVAPOET;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.MOSHI;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.OKHTTP;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.OKIO;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.PICASSO;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.RETROFIT;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.SQLBRITE;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.TELESCOPE;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.U2020;
import static ru.ltst.u2020mvp.data.api.mock.MockRepositories.WIRE;


public enum MockRepositoriesResponse {
    SUCCESS("Success", new RepositoriesResponse(Arrays.asList( //
            BUTTERKNIFE, //
            DAGGER, //
            JAVAPOET, //
            OKHTTP, //
            OKIO, //
            PICASSO, //
            RETROFIT, //
            SQLBRITE, //
            TELESCOPE, //
            U2020, //
            WIRE, //
            MOSHI))),
    ONE("One", new RepositoriesResponse(Collections.singletonList(DAGGER))),
    EMPTY("Empty", new RepositoriesResponse(null));

    public final String name;
    public final RepositoriesResponse response;

    MockRepositoriesResponse(String name, RepositoriesResponse response) {
        this.name = name;
        this.response = response;
    }

    @Override
    public String toString() {
        return name;
    }
}
