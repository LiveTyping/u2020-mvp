package ru.ltst.u2020mvp.data.api;

import ru.ltst.u2020mvp.data.api.model.response.ImageResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface ImageService {
    @GET("image/{id}")
    Observable<ImageResponse> image(@Path("id") String id);
}
