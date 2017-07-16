package ru.ltst.u2020mvp.data.api;

import retrofit2.adapter.rxjava.Result;
import rx.functions.Func1;

public final class Results {
  private static final Func1<Result<?>, Boolean> SUCCESS =
          result -> !result.isError() && result.response().isSuccessful();

  public static Func1<Result<?>, Boolean> isSuccess() {
    return SUCCESS;
  }

  private Results() {
    throw new AssertionError("No instances.");
  }
}
