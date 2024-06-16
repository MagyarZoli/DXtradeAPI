package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.AccountCode;

@Data
public class Position
implements Model, AccountModel {

  /**
   * Unique code of the account.
   */
  private AccountCode account;

  /**
   * Numeric version of the account.
   */
  private Integer version;

  /**
   * Unique code of a position.
   */
  private String positionCode;

  /**
   * Symbol of the position’s instrument.
   */
  private String symbol;

  /**
   * Current quantity of the position in units (not in lots).
   */
  private Number quantity;

  /**
   * Side of the position. Either BUY (long) or SELL (short).
   */
  private Side side;

  /**
   * Current quantity notional of the position in notional units (not in lots).
   */
  private Number quantityNotional;

  /**
   * Timestamp in UTC when this position was last updated.
   */
  private String lastUpdateTime;

  /**
   * Price at which the position was opened
   */
  private Number openPrice;

  private Number marginRate;

  private String openTime;

  @JsonCreator
  public Position(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "positionCode") String positionCode,
      @JsonProperty(value = "symbol") String symbol,
      @JsonProperty(value = "quantity") Number quantity,
      @JsonProperty(value = "side") Side side,
      @JsonProperty(value = "quantityNotional") Number quantityNotional,
      @JsonProperty(value = "lastUpdateTime") String lastUpdateTime,
      @JsonProperty(value = "openPrice") Number openPrice,
      @JsonProperty(value = "marginRate") Number marginRate,
      @JsonProperty(value = "openTime") String openTime
  ) {
    this.account = account;
    this.version = version;
    this.positionCode = positionCode;
    this.symbol = symbol;
    this.quantity = quantity;
    this.side = side;
    this.quantityNotional = quantityNotional;
    this.lastUpdateTime = lastUpdateTime;
    this.openPrice = openPrice;
    this.marginRate = marginRate;
    this.openTime = openTime;
  }
}
