package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarginTier {

  /**
   * End volume of a tier.
   * For TIERED margin rate type is the end volume for the tier in traded units.
   * Infinity value is set for the rate, applied to the volumes greater than the last defined volume.
   * For FLAT is always Infinity, meaning the rate shall be applied to any volume.
   */
  private Number endVolume;

  /**
   * Margin rate ratio. E.g. 10% is reported as 0.1.
   */
  private Number value;

  @JsonCreator
  public MarginTier(
      @JsonProperty(value = "endVolume") Number endVolume,
      @JsonProperty(value = "value") Number value
  ) {
    this.endVolume = endVolume;
    this.value = value;
  }
}
