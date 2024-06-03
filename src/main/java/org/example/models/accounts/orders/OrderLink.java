package org.example.models.accounts.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.ClientId;

@Data
public class OrderLink
implements Model {

  /**
   * PARENT – this order is a parent of the linked one.
   * CHILD – this order is a child of the linked one.
   * OCO – this order is a member of an OCO group with the linked one.
   */
  private LinkType linkType;

  /**
   * Internal unique string code of the linked order.
   * Corresponds to the orderCode field in the Order object
   */
  private String linkedOrder;

  /**
   * Unique order id of the linked order.
   * Corresponds to the clientOrderId field in the Order object.
   */
  private ClientId linkedClientOrderId;

  @JsonCreator
  public OrderLink(
      @JsonProperty(value = "linkType") LinkType linkType,
      @JsonProperty(value = "linkedOrder") String linkedOrder,
      @JsonProperty(value = "linkedClientOrderId") ClientId linkedClientOrderId
  ) {
    this.linkType = linkType;
    this.linkedOrder = linkedOrder;
    this.linkedClientOrderId = linkedClientOrderId;
  }
}
