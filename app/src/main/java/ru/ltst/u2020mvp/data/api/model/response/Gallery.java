package ru.ltst.u2020mvp.data.api.model.response;

import java.util.List;

public final class Gallery extends ImgurResponse {
    public final List<Image> data;

    public Gallery(int status, boolean success, List<Image> data) {
        super(status, success);
        this.data = data;
    }

    private Gallery(Builder builder) {
        super(builder.status, builder.success);
        this.data = builder.data;
    }

    public static final class Builder {
        private List<Image> data;
        private int status;
        private boolean success;

        public Builder setData(List<Image> data) {
            this.data = data;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Gallery build() {
            return new Gallery(this);
        }
    }
}
