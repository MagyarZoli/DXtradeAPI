package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class OptionDetails
implements Model {

  private Number strike;

  private OptionSide optionSide;

  private OptionStyle optionStyle;

  private Number sharesPerContract;

  @JsonCreator
  public OptionDetails(
      @JsonProperty(value = "strike") Number strike,
      @JsonProperty(value = "optionSide") OptionSide optionSide,
      @JsonProperty(value = "optionStyle") OptionStyle optionStyle,
      @JsonProperty(value = "sharesPerContract") Number sharesPerContract
  ) {
    this.strike = strike;
    this.optionSide = optionSide;
    this.optionStyle = optionStyle;
    this.sharesPerContract = sharesPerContract;
  }
}
