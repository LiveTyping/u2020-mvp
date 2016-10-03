package ru.ltst.u2020mvp.util;

public final class Strings {

    public static final String DOT = ".";
    public static final String COLON = ":";

    private Strings() {
        // No instances.
    }

    public static boolean isBlank(CharSequence string) {
        return (string == null || string.toString().trim().length() == 0);
    }

    public static String valueOrDefault(String string, String defaultString) {
        return isBlank(string) ? defaultString : string;
    }

    public static String truncateAt(String string, int length) {
        if (isBlank(string) || length < 0)
            return string;
        return string.length() > length ? string.substring(0, length) : string;
    }
}
