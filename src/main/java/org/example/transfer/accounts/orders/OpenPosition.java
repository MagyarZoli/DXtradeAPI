package org.example.transfer.accounts.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.example.models.accounts.Side;
import org.example.models.accounts.orders.OrderType;
import org.example.models.accounts.orders.PositionEffect;
import org.example.models.accounts.orders.TIF;
import org.example.models.symbol.Symbol;

@Getter
@ToString
@EqualsAndHashCode
public class OpenPosition {

  private final OrderType type = OrderType.MARKET;
  private final PositionEffect positionEffect = PositionEffect.OPEN;
  private final TIF tif = TIF.GTC;

  @NonNull
  private final Symbol instrument;

  @NonNull
  private final Quantity quantity;

  @NonNull
  private final Side side;

  @NonNull
  private final String orderCode;

  @JsonCreator
  public OpenPosition(
      @NonNull @JsonProperty(value = "instrument") Symbol instrument,
      @NonNull @JsonProperty(value = "quantity") Quantity quantity,
      @NonNull @JsonProperty(value = "side") Side side
  ) {
    this.instrument = instrument;
    this.quantity = quantity;
    this.side = side;
    this.orderCode = OrderCode.makesOrderCode();
  }

  @JsonCreator
  public OpenPosition(
      @NonNull @JsonProperty(value = "instrument") Symbol instrument,
      @NonNull @JsonProperty(value = "quantity") Quantity quantity,
      @NonNull @JsonProperty(value = "side") Side side,
      @NonNull @JsonProperty(value = "orderCode") String orderCode
  ) {
    this.instrument = instrument;
    this.quantity = quantity;
    this.side = side;
    this.orderCode = orderCode;
  }
}
