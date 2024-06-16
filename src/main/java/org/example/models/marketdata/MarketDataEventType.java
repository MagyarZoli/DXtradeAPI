package org.example.models.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class MarketDataEventType
implements Model {

  private String type;

  private String format;

  @JsonCreator
  public MarketDataEventType(
      @JsonProperty(value = "type") String type,
      @JsonProperty(value = "format") String format
  ) {
    this.type = type;
    this.format = format;
  }
}
