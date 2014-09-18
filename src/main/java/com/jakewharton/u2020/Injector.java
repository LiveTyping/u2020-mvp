package com.jakewharton.u2020;

import android.content.Context;
import android.support.annotation.NonNull;

public class Injector {
    public static <T> T inject (Context context, T object) {
        U2020InjectionService service = (U2020InjectionService) context.getSystemService(U2020InjectionService.NAME);
        return service.inject(object);
    }

    public static boolean isValidSystemService(@NonNull String name) {
        return U2020InjectionService.NAME.equals(name);
    }
}
