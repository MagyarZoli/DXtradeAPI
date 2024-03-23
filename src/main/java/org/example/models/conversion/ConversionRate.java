package org.example.models.conversion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConversionRate {

  /**
   * Source currency.
   */
  private String fromCurrency;

  /**
   * Target currency.
   */
  private String toCurrency;

  /**
   * Current conversion rate from the source currency to the target currency.
   */
  private Number convRate;

  @JsonCreator
  public ConversionRate(
      @JsonProperty(value = "fromCurrency") String fromCurrency,
      @JsonProperty(value = "toCurrency") String toCurrency,
      @JsonProperty(value = "convRate") Number convRate
  ) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
    this.convRate = convRate;
  }
}
