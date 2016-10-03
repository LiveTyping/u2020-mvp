package ru.ltst.u2020mvp.data.api.model.response;

public final class  Image {
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

    public Image(Builder builder) {
        this.id = builder.id;
        this.link = builder.link;
        this.title = builder.title;
        this.description = builder.description;
        this.width = builder.width;
        this.height = builder.height;
        this.datetime = builder.datetime;
        this.views = builder.views;
        this.is_album = builder.is_album;
    }

    public static class Builder {
        private String id;

        private String link;
        private String title;
        private String description;

        private int width;
        private int height;
        private long datetime;
        private int views;
        private boolean is_album;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setLink(String link) {
            this.link = link;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setDatetime(long datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder setViews(int views) {
            this.views = views;
            return this;
        }

        public Builder setIsAlbum(boolean is_album) {
            this.is_album = is_album;
            return this;
        }

        public Image build() {
            return new Image(this);
        }
    }
}
