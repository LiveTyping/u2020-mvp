package ru.ltst.u2020mvp.data.api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.ltst.u2020mvp.data.api.model.request.Sort;
import ru.ltst.u2020mvp.data.api.model.response.Image;

final public class SortUtil {
    private static final Comparator<Image> TIME = (lhs, rhs) -> {
        long left = lhs.datetime;
        long right = rhs.datetime;
        return left < right ? 1 : (left == right ? 0 : -1);
    };
    private static final Comparator<Image> VIRAL = (lhs, rhs) -> {
        // Just use views for mock data.
        int left = lhs.views;
        int right = rhs.views;
        return left < right ? 1 : (left == right ? 0 : -1);
    };

    static public void sort(List<Image> images, Sort sort) {
        switch (sort) {
            case TIME:
                Collections.sort(images, TIME);
                break;

            case VIRAL:
                Collections.sort(images, VIRAL);
                break;

            default:
                throw new IllegalArgumentException("Unknown sort: " + sort);
        }
    }

    private SortUtil() {
    }
}
