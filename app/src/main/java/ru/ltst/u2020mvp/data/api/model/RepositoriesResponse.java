package ru.ltst.u2020mvp.data.api.model;

import java.util.List;

public final class RepositoriesResponse {
  public final List<Repository> items;

  public RepositoriesResponse(List<Repository> items) {
    this.items = items;
  }
}
