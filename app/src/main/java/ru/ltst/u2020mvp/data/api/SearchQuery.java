package ru.ltst.u2020mvp.data.api;

import org.threeten.bp.LocalDate;

import ru.ltst.u2020mvp.util.Preconditions;

import static org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static ru.ltst.u2020mvp.util.Preconditions.checkNotNull;

public final class SearchQuery {
  private final LocalDate createdSince;

  private SearchQuery(Builder builder) {
    this.createdSince = checkNotNull(builder.createdSince, "createdSince == null");
  }

  @Override public String toString() {
    // Returning null here is not ideal, but it lets retrofit drop the query param altogether.
    return createdSince == null ? null : "created:>=" + ISO_LOCAL_DATE.format(createdSince);
  }

  public static final class Builder {
    private LocalDate createdSince;

    public Builder createdSince(LocalDate createdSince) {
      this.createdSince = createdSince;
      return this;
    }

    public SearchQuery build() {
      return new SearchQuery(this);
    }
  }
}
