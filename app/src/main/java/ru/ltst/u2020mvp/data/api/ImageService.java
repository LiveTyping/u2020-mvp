package ru.ltst.u2020mvp.data.api;

import retrofit.Result;
import retrofit.http.GET;
import retrofit.http.Path;
import ru.ltst.u2020mvp.data.api.model.response.ImageResponse;
import rx.Observable;

public interface ImageService {
    @GET("image/{id}")
    Observable<Result<ImageResponse>> image(@Path("id") String id);
}
