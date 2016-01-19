package ru.ltst.u2020mvp.data.api.mock;

public final class MockImage {

    public static final MockImage DAGGER = new Builder()
            .title("Much Dagger")
            .image("0y3uACw.jpg")
            .views(4000)
            .build();
    public static final MockImage PICASSO = new Builder()
            .title("Nice Picasso")
            .image("9PcLf86.jpg")
            .views(854)
            .build();
    public static final MockImage SCALPEL = new Builder()
            .title("Omg Scalpel")
            .image("DgKWqio.jpg")
            .build();
    public static final MockImage OPENSOURCE = new Builder()
            .title("Open Source Amaze")
            .image("e3LxhEC.jpg")
            .build();
    public static final MockImage RXJAVA = new Builder()
            .title("So RxJava")
            .image("p3jUQjI.jpg")
            .views(2000)
            .build();
    public static final MockImage MADGE = new Builder()
            .title("Madge Amaze")
            .image("P8hx3pg.jpg")
            .build();
    public static final MockImage BUTTERKNIFE = new Builder()
            .title("Very ButterKnife")
            .image("vSxLdXJ.jpg")
            .views(3040)
            .build();
    public static final MockImage AOSP = new Builder()
            .title("Very ButterKnife")
            .image("DOGE-6.jpg")
            .build();
    public static final MockImage OKHTTP = new Builder()
            .title("Many OkHttp")
            .image("DOGE-10.jpg")
            .views(1500)
            .build();
    public static final MockImage RETROFIT = new Builder()
            .title("Wow Retrofit")
            .image("DOGE-16.jpg")
            .views(3000)
            .build();

    public final String title;
    public final String image;
    public final int views;

    public MockImage(int views, String image, String title) {
        this.views = views;
        this.image = image;
        this.title = title;
    }

    private MockImage(Builder builder) {
        this.title = builder.title;
        this.image = builder.image;
        this.views = builder.views;
    }

    public static class Builder {
        private String title;
        private String image;
        private int views;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder image(String path) {
            this.image = path;
            return this;
        }

        public Builder views(int views) {
            this.views = views;
            return this;
        }

        public MockImage build() {
            return new MockImage(this);
        }
    }
}
