package org.example.models.accounts.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.models.accounts.Side;
import org.example.transfer.AccountCode;

@Data
public class SingleOrderRequest
implements Model {

  private AccountCode account;
  private String orderCode;
  private OrderType type;
  private String instrument;
  private Integer quantity;
  private PositionEffect positionEffect;
  private String positionCode;
  private Side side;
  private Integer limitPrice;
  private Integer stopPrice;
  private Integer priceOffset;
  private PriceLink priceLink;
  private TIF tif;
  private Integer marginRate;
  private String expireDate;

  @JsonCreator
  public SingleOrderRequest(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "orderCode") String orderCode,
      @JsonProperty(value = "type") OrderType type,
      @JsonProperty(value = "instrument") String instrument,
      @JsonProperty(value = "quantity") Integer quantity,
      @JsonProperty(value = "positionEffect") PositionEffect positionEffect,
      @JsonProperty(value = "positionCode") String positionCode,
      @JsonProperty(value = "side") Side side,
      @JsonProperty(value = "limitPrice") Integer limitPrice,
      @JsonProperty(value = "stopPrice") Integer stopPrice,
      @JsonProperty(value = "priceOffset") Integer priceOffset,
      @JsonProperty(value = "priceLink") PriceLink priceLink,
      @JsonProperty(value = "tif") TIF tif,
      @JsonProperty(value = "marginRate") Integer marginRate,
      @JsonProperty(value = "expireDate") String expireDate
  ) {
    this.account = account;
    this.orderCode = orderCode;
    this.type = type;
    this.instrument = instrument;
    this.quantity = quantity;
    this.positionEffect = positionEffect;
    this.positionCode = positionCode;
    this.side = side;
    this.limitPrice = limitPrice;
    this.stopPrice = stopPrice;
    this.priceOffset = priceOffset;
    this.priceLink = priceLink;
    this.tif = tif;
    this.marginRate = marginRate;
    this.expireDate = expireDate;
  }
}
