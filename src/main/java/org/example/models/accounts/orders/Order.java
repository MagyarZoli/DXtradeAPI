package org.example.models.accounts.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.models.accounts.CashTransaction;
import org.example.models.accounts.Side;
import org.example.transfer.AccountCode;
import org.example.transfer.ClientId;

import java.util.List;

@Data
public class Order
implements Model {

  /**
   * Unique code of the account.
   */
  private AccountCode account;

  /**
   * Numeric version of the account.
   */
  private Integer version;

  /**
   * Internal numeric id of an order chain in the system.
   */
  private Number orderId;

  /**
   * Internal unique string code of an order chain in the system.
   */
  private String orderCode;

  /**
   * Unique order id assigned by the client during order placement.
   */
  private ClientId clientOrderId;

  /**
   * Internal unique code of the last order modification.
   */
  private String actionCode;

  /**
   * Always 1.
   */
  private Integer legCount;

  /**
   * Order type.
   */
  private OrderType type;

  /**
   * Symbol of the order’s instrument.
   */
  private String instrument;

  /**
   * Status of the order.
   */
  private OrderStatus status;

  /**
   * Boolean value indicating if order is in final state
   */
  private Boolean finalStatus;

  /**
   * List of exactly 1 order leg.
   */
  private List<OrderLeg> legs;

  /**
   * Side of the order. Either BUY (long) or SELL (short).
   */
  private Side side;

  /**
   * Time in force (expiration time for the order).
   */
  private TIF tif;

  /**
   * Timestamp in UTC of the last order modification or fill.
   */
  private String transactionTime;

  /**
   * Order margin rate.
   */
  private Number marginRate;

  /**
   * List of the links between this order and other orders in its group.
   * Absent if the order is not a part of a group.
   * Links are calculated only for the 'list-open-orders' request.
   */
  private List<OrderLink> links;

  /**
   * List of all executions for this order.
   * This includes order status changes, replaces, cancels and trades. Reports are ordered chronologically.
   */
  private List<Execution> executions;

  /**
   * List of all cash transactions related to this order.
   * Reports are ordered chronologically.
   */
  private List<CashTransaction> cashTransactions;

  /**
   * Price offset for protection orders.
   */
  private Number priceOffset;

  /**
   * Price link for protection orders.
   */
  private PriceLink priceLink;

  /**
   * For broker orders, a numeric id of the client order this order hedges.
   * Included only for Dealer users.
   */
  private Number hedgedOrderId;

  /**
   * For broker orders, order id at the liquidity provider.
   * Included only for Dealer users
   */
  private String externalOrderId;

  @JsonCreator
  public Order(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "orderId") Number orderId,
      @JsonProperty(value = "orderCode") String orderCode,
      @JsonProperty(value = "clientOrderId") ClientId clientOrderId,
      @JsonProperty(value = "actionCode") String actionCode,
      @JsonProperty(value = "legCount") Integer legCount,
      @JsonProperty(value = "type") OrderType type,
      @JsonProperty(value = "instrument") String instrument,
      @JsonProperty(value = "status") OrderStatus status,
      @JsonProperty(value = "finalStatus") Boolean finalStatus,
      @JsonProperty(value = "legs") List<OrderLeg> legs,
      @JsonProperty(value = "side") Side side,
      @JsonProperty(value = "tif") TIF tif,
      @JsonProperty(value = "transactionTime") String transactionTime,
      @JsonProperty(value = "marginRate") Number marginRate,
      @JsonProperty(value = "links") List<OrderLink> links,
      @JsonProperty(value = "executions") List<Execution> executions,
      @JsonProperty(value = "cashTransactions") List<CashTransaction> cashTransactions,
      @JsonProperty(value = "priceOffset") Number priceOffset,
      @JsonProperty(value = "priceLink") PriceLink priceLink,
      @JsonProperty(value = "hedgedOrderId") Number hedgedOrderId,
      @JsonProperty(value = "externalOrderId") String externalOrderId
  ) {
    this.account = account;
    this.version = version;
    this.orderId = orderId;
    this.orderCode = orderCode;
    this.clientOrderId = clientOrderId;
    this.actionCode = actionCode;
    this.legCount = legCount;
    this.type = type;
    this.instrument = instrument;
    this.status = status;
    this.finalStatus = finalStatus;
    this.legs = legs;
    this.side = side;
    this.tif = tif;
    this.transactionTime = transactionTime;
    this.marginRate = marginRate;
    this.links = links;
    this.executions = executions;
    this.cashTransactions = cashTransactions;
    this.priceOffset = priceOffset;
    this.priceLink = priceLink;
    this.hedgedOrderId = hedgedOrderId;
    this.externalOrderId = externalOrderId;
  }
}
