package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.AccountCode;
import org.example.models.Model;

@Data
public class InstrumentDetail
implements Model {

  /**
   * Unique code of an account.
   */
  private AccountCode account;

  /**
   * Instrument type as configured on the platform (e.g. INDEX).
   */
  private InstrumentType type;

  /**
   * Unique instrument symbol.
   */
  private String symbol;

  /**
   * Trading permissions of the account for the instrument. One of FULL, CLOSE_ONLY, NO.
   */
  private TradingStatus tradingStatus;

  /**
   * Minimum order size for an instrument.
   */
  private Number minOrderSize;

  /**
   * Maximum order size for an instrument.
   */
  private LimitValue maxOrderSize;

  /**
   * Order size increment for an instrument (e.g. 0.0001).
   */
  private Number minOrderSizeIncrement;

  /**
   * Margin rate for an instrument.
   */
  private MarginRate marginRate;

  @JsonCreator
  public InstrumentDetail(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "type") InstrumentType type,
      @JsonProperty(value = "symbol") String symbol,
      @JsonProperty(value = "tradingStatus") TradingStatus tradingStatus,
      @JsonProperty(value = "minOrderSize") Number minOrderSize,
      @JsonProperty(value = "maxOrderSize") LimitValue maxOrderSize,
      @JsonProperty(value = "minOrderSizeIncrement") Number minOrderSizeIncrement,
      @JsonProperty(value = "marginRate") MarginRate marginRate
  ) {
    this.account = account;
    this.type = type;
    this.symbol = symbol;
    this.tradingStatus = tradingStatus;
    this.minOrderSize = minOrderSize;
    this.maxOrderSize = maxOrderSize;
    this.minOrderSizeIncrement = minOrderSizeIncrement;
    this.marginRate = marginRate;
  }
}
