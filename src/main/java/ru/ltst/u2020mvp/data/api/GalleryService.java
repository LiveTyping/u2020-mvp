package ru.ltst.u2020mvp.data.api;

import ru.ltst.u2020mvp.data.api.model.response.Gallery;
import ru.ltst.u2020mvp.data.api.model.request.Section;
import ru.ltst.u2020mvp.data.api.model.request.Sort;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GalleryService {
    @GET("/gallery/{section}/{sort}/{page}")
    Observable<Gallery> listGallery( @Path("section") Section section,
                                     @Path("sort") Sort sort,
                                     @Path("page") int page);
}
