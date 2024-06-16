package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class ExpirationDetails
implements Model {

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
