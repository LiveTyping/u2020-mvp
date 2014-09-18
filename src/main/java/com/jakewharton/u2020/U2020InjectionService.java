package com.jakewharton.u2020;

public interface U2020InjectionService {
    static final String NAME = "com.jakewharton.u2020.U2020InjectionService";
    <T> T inject(T object);
}
