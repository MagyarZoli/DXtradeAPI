package org.example.models.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.models.Model;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class MarketEvent
implements Model {

  private String symbol;

  private String type;

  @JsonCreator
  public MarketEvent(
      @JsonProperty(value = "symbol") String symbol,
      @JsonProperty(value = "type") String type
  ) {
    this.symbol = symbol;
    this.type = type;
  }
}
