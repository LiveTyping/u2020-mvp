package ru.ltst.u2020mvp.data.api.model.response;

public final class ImageResponse extends ImgurResponse {
    public final Image data;

    public ImageResponse(int status, boolean success, Image data) {
        super(status, success);
        this.data = data;
    }
}
