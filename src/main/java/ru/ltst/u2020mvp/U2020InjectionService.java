package ru.ltst.u2020mvp;

public interface U2020InjectionService {
    static final String NAME = "ru.ltst.u2020mvp.U2020InjectionService";
    <T> T inject(T object);
}
