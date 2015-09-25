package ru.ltst.u2020mvp.data.api;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import ru.ltst.u2020mvp.data.api.mock.MockImageLoader;
import ru.ltst.u2020mvp.data.api.model.request.Section;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.api.model.response.ImageResponse;
import ru.ltst.u2020mvp.ApplicationScope;
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

    // TODO maybe id->image map and section->id multimap so we can re-use images?
    private final Map<Section, List<Image>> imagesBySection = new LinkedHashMap<>();
    private final ArrayMap<String, Image> imagesById = new ArrayMap<>();

    private boolean initialized;

    @Inject
    public ServerDatabase(MockImageLoader mockImageLoader) {
        this.mockImageLoader = mockImageLoader;
    }

    private synchronized void initializeMockData() {
        if (initialized) return;
        initialized = true;
        Timber.d("Initializing mock data...");

        List<Image> hotImages = new ArrayList<>();
        imagesBySection.put(Section.HOT, hotImages);

        hotImages.add(mockImageLoader.newImage("0y3uACw.jpg") //
                .title("Much Dagger") //
                .views(4000) //
                .build()); //
        hotImages.add(mockImageLoader.newImage("9PcLf86.jpg") //
                .title("Nice Picasso") //
                .views(854) //
                .build());
        hotImages.add(mockImageLoader.newImage("DgKWqio.jpg") //
                .title("Omg Scalpel") //
                .build());
        hotImages.add(mockImageLoader.newImage("e3LxhEC.jpg") //
                .title("Open Source Amaze") //
                .build());
        hotImages.add(mockImageLoader.newImage("p3jUQjI.jpg") //
                .title("So RxJava") //
                .views(2000) //
                .build());
        hotImages.add(mockImageLoader.newImage("P8hx3pg.jpg") //
                .title("Madge Amaze") //
                .build());
        hotImages.add(mockImageLoader.newImage("vSxLdXJ.jpg") //
                .title("Very ButterKnife") //
                .views(3040) //
                .build());
        hotImages.add(mockImageLoader.newImage("DOGE-6.jpg") //
                .title("Good AOSP") //
                .build());
        hotImages.add(mockImageLoader.newImage("DOGE-10.jpg") //
                .title("Many OkHttp") //
                .views(1500) //
                .build());
        hotImages.add(mockImageLoader.newImage("DOGE-16.jpg") //
                .title("Wow Retrofit") //
                .views(3000) //
                .build());

        for (Image hotImage : hotImages) {
            imagesById.put(hotImage.id, hotImage);
        }
    }

    public List<Image> getImagesForSection(Section section) {
        initializeMockData();
        return imagesBySection.get(section);
    }

    public ImageResponse getImageForId(@NonNull String id) {
        return new ImageResponse(200, true, imagesById.get(id));
    }
}
