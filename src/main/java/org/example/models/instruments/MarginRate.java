package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarginRate {

  /**
   * Either FLAT or TIERED.
   */
  private RateType rateType;

  /**
   * The list of Margin Tier objects (up to 4) sorted by endVolume in increasing order.
   * Infinity endVolume means the default tier and always ends the list.
   */
  private MarginTier[] tiers;

  private TraderSelectedMarginRates availableMarginRates;

  @JsonCreator
  public MarginRate(
      @JsonProperty(value = "rateType") RateType rateType,
      @JsonProperty(value = "tiers") MarginTier[] tiers,
      @JsonProperty(value = "availableMarginRates") TraderSelectedMarginRates availableMarginRates
  ) {
    this.rateType = rateType;
    this.tiers = tiers;
    this.availableMarginRates = availableMarginRates;
  }
}
