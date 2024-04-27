package org.example.models.marketdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CandleType
implements Type {

  MIN_1("m"),
  MIN_5("m5"),
  MIN_15("15m"),
  MIN_30("30m"),
  HOUR_1("h"),
  HOUR_2("2h"),
  HOUR_4("4h"),
  DAY("d"),
  WEEK("w"),
  MONTH("mo");

  private final String name;

  @Override
  public String toString() {
    if (this.name == null) {
      return super.name();
    }
    return this.name;
  }
}
