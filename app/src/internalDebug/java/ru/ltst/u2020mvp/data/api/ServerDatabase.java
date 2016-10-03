package ru.ltst.u2020mvp.data.api;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.mock.MockGalleryResponse;
import ru.ltst.u2020mvp.data.api.mock.MockImage;
import ru.ltst.u2020mvp.data.api.mock.MockImageLoader;
import ru.ltst.u2020mvp.data.api.model.request.Section;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.api.model.response.ImageResponse;
import ru.ltst.u2020mvp.util.EnumPreferences;
import timber.log.Timber;

@ApplicationScope
public final class ServerDatabase {
    private static final AtomicLong NEXT_ID = new AtomicLong();

    public static long nextId() {
        return NEXT_ID.getAndIncrement();
    }

    public static String nextStringId() {
        return Long.toHexString(nextId());
    }

    private final MockImageLoader mockImageLoader;
    private final SharedPreferences preferences;

    // TODO maybe id->image map and section->id multimap so we can re-use images?
    private final Map<Section, List<Image>> imagesBySection = new LinkedHashMap<>();
    private final ArrayMap<String, Image> imagesById = new ArrayMap<>();

    private boolean initialized;

    @Inject
    public ServerDatabase(MockImageLoader mockImageLoader, SharedPreferences preferences) {
        this.mockImageLoader = mockImageLoader;
        this.preferences = preferences;
    }

    private synchronized void initializeMockData() {
        if (initialized) return;
        initialized = true;
        Timber.d("Initializing mock data...");

        List<Image> hotImages = new ArrayList<>();
        imagesBySection.put(Section.HOT, hotImages);

        final MockGalleryResponse enumValue = EnumPreferences.getEnumValue(
                preferences, MockGalleryResponse.class,
                MockGalleryResponse.class.getCanonicalName(), MockGalleryResponse.SUCCESS);

        if (enumValue.response.data != null) {
            for (MockImage mockImage : enumValue.response.data) {
                hotImages.add(mockImageLoader.newImage(mockImage));
            }
        }

        for (Image hotImage : hotImages) {
            imagesById.put(hotImage.id, hotImage);
        }
    }

    public List<Image> getImagesForSection(Section section) {
        initializeMockData();
        return imagesBySection.get(section);
    }

    public ImageResponse getImageForId(@NonNull String id) {
        initializeMockData();
        return new ImageResponse(200, true, imagesById.get(id));
    }
}
