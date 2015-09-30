package ru.ltst.u2020mvp.data.api.mock;

import java.util.List;

public class MockGallery {
    public final int status;
    public final boolean success;
    public final List<MockImage> data;

    public MockGallery(int status, boolean success, List<MockImage> data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }


}
