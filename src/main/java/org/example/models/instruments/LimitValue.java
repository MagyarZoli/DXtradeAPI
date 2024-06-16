package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class LimitValue
implements Model {

  /**
   * The actual limit value.
   */
  private Number value;

  /**
   * Either LOTS or NOTIONAL_CURRENCY.
   */
  private LimitType limitType;

  @JsonCreator
  public LimitValue(
      @JsonProperty(value = "value") Number value,
      @JsonProperty(value = "limitType") LimitType limitType
  ) {
    this.value = value;
    this.limitType = limitType;
  }
}
