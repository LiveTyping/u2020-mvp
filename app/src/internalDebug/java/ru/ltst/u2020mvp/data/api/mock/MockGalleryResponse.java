package ru.ltst.u2020mvp.data.api.mock;

import java.util.Arrays;
import java.util.Collections;

import static ru.ltst.u2020mvp.data.api.mock.MockImage.AOSP;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.BUTTERKNIFE;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.DAGGER;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.MADGE;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.OKHTTP;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.OPENSOURCE;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.PICASSO;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.RETROFIT;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.RXJAVA;
import static ru.ltst.u2020mvp.data.api.mock.MockImage.SCALPEL;

public enum MockGalleryResponse {

  SUCCESS("Success", new MockGallery(200, true, Arrays.asList(
          DAGGER,
          PICASSO,
          SCALPEL,
          OPENSOURCE,
          RXJAVA,
          MADGE,
          BUTTERKNIFE,
          AOSP,
          OKHTTP,
          RETROFIT
  ))),
  ONE("One", new MockGallery(200, true, Collections.singletonList(DAGGER))),
  EMPTY("Empty", new MockGallery(200, true, null));

  public final String name;
  public final MockGallery response;

  MockGalleryResponse(String name, MockGallery response) {
    this.name = name;
    this.response = response;
  }

  @Override public String toString() {
    return name;
  }
}
