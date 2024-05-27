package org.example.models.accounts.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.models.accounts.Side;

@Data
public class OrderLeg
implements Model {

  /**
   * Symbol of the order’s instrument.
   */
  private String instrument;

  /**
   * The effect of this order on a position.
   * Indicates whether the resulting position after a trade should be a new position or the trade should close an existing position.
   * Required for position-based trading.
   * If the value is Close, the positionCode field is also present.
   */
  private PositionEffect positionEffect;

  /**
   * ID of a position that should be modified by this order.
   * Used for position based trading.
   * Present when positionEffect is Close and omitted otherwise.
   */
  private String positionCode;

  private Side side;

  /**
   * Price of the order
   */
  private Number price;

  /**
   * Always 1.0.
   */
  private Number legRatio;

  /**
   * Initial quantity of the order in units (not in lots) as specified in a "Place" request.
   * This value is not present if this is an attached Limit or Stop "closing" order linked to a position.
   * In this case the positionCode field is present and positionEffect is "Close".
   * It means initial quantity of the order equals to the quantity of the position.
   * If this is a Market order closing the position, it may have quantity set to be able to partially close the position.
   * If quantity is omitted for Market order with the "Closing" position effect, then whole position will be closed.
   */
  private Number quantity;

  /**
   * Quantity filled so far in units (not in lots).
   * If no fills took place, this field equals to 0.0.
   */
  private Number filledQuantity;

  /**
   * Quantity available for further execution (in units).
   */
  private Number remainingQuantity;

  /**
   * Volume-weighted average price of all fills for this leg.
   * The value is 0.0 if there are no fills yet.
   */
  private Number averagePrice;

  @JsonCreator
  public OrderLeg(
      @JsonProperty(value = "instrument") String instrument,
      @JsonProperty(value = "positionEffect") PositionEffect positionEffect,
      @JsonProperty(value = "positionCode") String positionCode,
      @JsonProperty(value = "side") Side side,
      @JsonProperty(value = "price") Number price,
      @JsonProperty(value = "legRatio") Number legRatio,
      @JsonProperty(value = "quantity") Number quantity,
      @JsonProperty(value = "filledQuantity") Number filledQuantity,
      @JsonProperty(value = "remainingQuantity") Number remainingQuantity,
      @JsonProperty(value = "averagePrice") Number averagePrice
  ) {
    this.instrument = instrument;
    this.positionEffect = positionEffect;
    this.positionCode = positionCode;
    this.side = side;
    this.price = price;
    this.legRatio = legRatio;
    this.quantity = quantity;
    this.filledQuantity = filledQuantity;
    this.remainingQuantity = remainingQuantity;
    this.averagePrice = averagePrice;
  }
}
