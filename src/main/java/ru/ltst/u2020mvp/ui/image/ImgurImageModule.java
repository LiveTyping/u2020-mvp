package ru.ltst.u2020mvp.ui.image;

import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.ltst.u2020mvp.data.api.ImageService;
import ru.ltst.u2020mvp.data.api.model.response.Image;
import ru.ltst.u2020mvp.data.api.transforms.ImageResponseToImage;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ImgurImageModule {
    private final @NonNull String imageId;

    public ImgurImageModule(String imageId) {
        this.imageId = imageId;
    }

    @Provides
    Observable<Image> provideImageObservable(ImageService imageService) {
        return imageService.image(imageId).
                map(new ImageResponseToImage()).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
