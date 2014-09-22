package ru.ltst.u2020mvp.data.api.model.response;

public final class Image {
    public final String id;

    public final String link;
    public final String title;
    public final String description;

    public final int width;
    public final int height;
    public final long datetime;
    public final int views;
    public final boolean is_album;

    public Image(String id, String link, String title, String description,
                 int width, int height, long datetime, int views, boolean is_album) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.width = width;
        this.height = height;
        this.datetime = datetime;
        this.views = views;
        this.is_album = is_album;
    }
}
