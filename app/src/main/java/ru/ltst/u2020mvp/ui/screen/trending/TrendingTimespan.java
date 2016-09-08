package ru.ltst.u2020mvp.ui.screen.trending;


import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.TemporalUnit;

public enum TrendingTimespan {
  DAY("today", 1, ChronoUnit.DAYS),
  WEEK("last week", 1, ChronoUnit.WEEKS),
  MONTH("last month", 1, ChronoUnit.MONTHS);

  private final String name;
  private final long duration;
  private final TemporalUnit durationUnit;

  TrendingTimespan(String name, int duration, TemporalUnit durationUnit) {
    this.name = name;
    this.duration = duration;
    this.durationUnit = durationUnit;
  }

  public LocalDate createdSince() {
    return LocalDate.now().minus(duration, durationUnit);
  }

  @Override public String toString() {
    return name;
  }
}
