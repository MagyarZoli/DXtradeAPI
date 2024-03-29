package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExpirationDetails {

  private String maturityDate;

  private String lastTradeDate;

  @JsonCreator
  public ExpirationDetails(
      @JsonProperty(value = "maturityDate") String maturityDate,
      @JsonProperty(value = "lastTradeDate") String lastTradeDate
  ) {
    this.maturityDate = maturityDate;
    this.lastTradeDate = lastTradeDate;
  }
}
