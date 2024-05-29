package org.example.models.accounts.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class OrderResponse
implements Model {

  /**
   * Internal unique numeric id of an order chain in the system.
   * This value is assigned when an order is initially placed and does not change when an order is replaced or cancelled.
   */
  private Number orderId;

  /**
   * Internal numeric id of an order.
   * This value is assigned when an order is initially placed and updated when an order is replaced or cancelled
   */
  private Number updateOrderId;

  @JsonCreator
  public OrderResponse(
      @JsonProperty(value = "orderId") Number orderId,
      @JsonProperty(value = "updateOrderId") Number updateOrderId
  ) {
    this.orderId = orderId;
    this.updateOrderId = updateOrderId;
  }
}
