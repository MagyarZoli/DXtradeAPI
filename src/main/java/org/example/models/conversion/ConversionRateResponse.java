package org.example.models.conversion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConversionRateResponse {

  private List<ConversionRate> conversionRates;

  @JsonCreator
  public ConversionRateResponse(
      @JsonProperty(value = "conversionRates") List<ConversionRate> conversionRates
  ) {
    this.conversionRates = conversionRates;
  }
}
