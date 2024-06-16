package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class TraderSelectedMarginRates
implements Model {

  private Number minRate;

  private Number maxRate;

  private Number defaultValue;

  private Number rateIncrement;

  @JsonCreator
  public TraderSelectedMarginRates(
      @JsonProperty(value = "minRate") Number minRate,
      @JsonProperty(value = "maxRate") Number maxRate,
      @JsonProperty(value = "defaultValue") Number defaultValue,
      @JsonProperty(value = "rateIncrement") Number rateIncrement
  ) {
    this.minRate = minRate;
    this.maxRate = maxRate;
    this.defaultValue = defaultValue;
    this.rateIncrement = rateIncrement;
  }
}
