package ru.ltst.u2020mvp.data.api.model.request;

public enum Sort {
    VIRAL("viral"),
    TIME("time");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
