package ru.ltst.u2020mvp.data.api.mock;

import android.content.SharedPreferences;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.adapter.rxjava.Result;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.GalleryService;
import ru.ltst.u2020mvp.data.api.ServerDatabase;
import ru.ltst.u2020mvp.data.api.SortUtil;
import ru.ltst.u2020mvp.data.api.model.request.Section;
import ru.ltst.u2020mvp.data.api.model.request.Sort;
import ru.ltst.u2020mvp.data.api.model.response.Gallery;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.util.EnumPreferences;
import rx.Observable;

@ApplicationScope
public final class MockGalleryService implements GalleryService {
    private static final Gallery BAD_REQUEST = new Gallery(200, false, null);
    private static final int PAGE_SIZE = 50;

    private final SharedPreferences preferences;
    private final ServerDatabase serverDatabase;
    private final Map<Class<? extends Enum<?>>, Enum<?>> responses = new LinkedHashMap<>();

    @Inject
    MockGalleryService(ServerDatabase serverDatabase, SharedPreferences preferences) {
        this.serverDatabase = serverDatabase;
        this.preferences = preferences;

        // Initialize mock responses.
        loadResponse(MockGalleryResponse.class, MockGalleryResponse.SUCCESS);
    }

    @Override
    public Observable<Result<Gallery>> listGallery(Section section, Sort sort, int page) {
        // Fetch desired section.
        List<Image> images = serverDatabase.getImagesForSection(section);
        if (images == null) {
            return Observable.just(Result.response(Response.success(BAD_REQUEST)));
        }

        // Figure out proper list subset.
        int pageStart = (page - 1) * PAGE_SIZE;
        if (pageStart >= images.size() || pageStart < 0) {
            return Observable.just(Result.response(Response.success(BAD_REQUEST)));
        }
        int pageEnd = Math.min(pageStart + PAGE_SIZE, images.size());

        // Sort and trim images.
        SortUtil.sort(images, sort);
        images = images.subList(pageStart, pageEnd);

        return Observable.just(Result.response(Response.success(new Gallery(200, true, images))));
    }

    /**
     * Initializes the current response for {@code responseClass} from {@code SharedPreferences}, or
     * uses {@code defaultValue} if a response was not found.
     */
    private <T extends Enum<T>> void loadResponse(Class<T> responseClass, T defaultValue) {
        responses.put(responseClass, EnumPreferences.getEnumValue(preferences, responseClass, //
                responseClass.getCanonicalName(), defaultValue));
    }

    public <T extends Enum<T>> Enum getResponse(Class<T> responseClass) {
        return responseClass.cast(responses.get(responseClass));
    }

    public <T extends Enum<T>> void setResponse(Class<T> responseClass, T value) {
        responses.put(responseClass, value);
        EnumPreferences.saveEnumValue(preferences, responseClass.getCanonicalName(), value);
    }
}
