package ru.ltst.u2020mvp.data;

import ru.ltst.u2020mvp.data.api.ApiModule;

public enum ApiEndpoints {
    PRODUCTION("Production", ApiModule.PRODUCTION_API_URL.toString()),
    MOCK_MODE("Mock Mode", "http://localhost/mock/"),
    CUSTOM("Custom", null);

    public final String name;
    public final String url;

    ApiEndpoints(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ApiEndpoints from(String endpoint) {
        for (ApiEndpoints value : values()) {
            if (value.url != null && value.url.equals(endpoint)) {
                return value;
            }
        }
        return CUSTOM;
    }

    public static boolean isMockMode(String endpoint) {
        return from(endpoint) == MOCK_MODE;
    }
}
