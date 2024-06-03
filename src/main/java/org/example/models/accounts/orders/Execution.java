package org.example.models.accounts.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.AccountCode;
import org.example.transfer.ClientId;

@Data
public class Execution
implements Model {

  /**
   * Unique code of the account.
   */
  private AccountCode account;

  /**
   * Unique string code of the execution.
   */
  private Number executionCode;

  /**
   * Internal unique string code of an order chain in the system.
   */
  private String orderCode;

  /**
   * Internal numeric id of an updated order.
   */
  private Number updateOrderId;

  /**
   * Numeric version of the account.
   */
  private Integer version;

  /**
   * Unique order id assigned by the client during order placement.
   */
  private ClientId clientOrderId;

  /**
   * Internal unique code of the last order modification.
   */
  private String actionCode;

  /**
   * Symbol of the order’s instrument.
   */
  private String instrument;

  /**
   * Status of the order after this execution.
   */
  private OrderStatus status;

  /**
   * Boolean value indicating if order is in final state after this execution.
   */
  private Boolean finalStatus;

  /**
   * Quantity filled so far in units (not in lots).
   * If no fills took place, this field equals to 0.0.
   */
  private Number filledQuantity;

  /**
   * The quantity of the fill, reported by the execution in units (not in lots).
   * If the execution is not a fill, this field equals to 0.0.
   */
  private Number lastQuantity;

  /**
   * Quantity filled so far in notional units (not in lots).
   * If no fills took place, this field equals to 0.0.
   */
  private Number filledQuantityNotional;

  /**
   * The quantity notional of the fill, reported by the execution in notional units (not in lots).
   */
  private Number lastQuantityNotional;

  /**
   * Quantity available for further execution (in units) after this execution.
   */
  private Number remainingQuantity;

  /**
   * For executions of TRADE execution type, price of the trade.
   * Omitted for all other execution types.
   */
  private Number lastPrice;

  /**
   * Volume-weighted average price of all fills for this order up to (and including) this execution.
   */
  private Number averagePrice;

  /**
   * Timestamp in UTC of the last order modification or fill.
   */
  private String transactionTime;

  private Number marginRate;

  /**
   * If an order is rejected, this field contains a human-readable reject reason.
   * In this case the rejectCode field is also present.
   */
  private String rejectReason;

  /**
   * Numeric code of an error in case of order rejections.
   * Present (along with rejectReason) only if an order has been rejected
   */
  private String rejectCode;

  @JsonCreator
  public Execution(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "executionCode") Number executionCode,
      @JsonProperty(value = "orderCode") String orderCode,
      @JsonProperty(value = "updateOrderId") Number updateOrderId,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "clientOrderId") ClientId clientOrderId,
      @JsonProperty(value = "actionCode") String actionCode,
      @JsonProperty(value = "instrument") String instrument,
      @JsonProperty(value = "status") OrderStatus status,
      @JsonProperty(value = "finalStatus") Boolean finalStatus,
      @JsonProperty(value = "filledQuantity") Number filledQuantity,
      @JsonProperty(value = "lastQuantity") Number lastQuantity,
      @JsonProperty(value = "filledQuantityNotional") Number filledQuantityNotional,
      @JsonProperty(value = "lastQuantityNotional") Number lastQuantityNotional,
      @JsonProperty(value = "remainingQuantity") Number remainingQuantity,
      @JsonProperty(value = "lastPrice") Number lastPrice,
      @JsonProperty(value = "averagePrice") Number averagePrice,
      @JsonProperty(value = "transactionTime") String transactionTime,
      @JsonProperty(value = "marginRate") Number marginRate,
      @JsonProperty(value = "rejectReason") String rejectReason,
      @JsonProperty(value = "rejectCode") String rejectCode
  ) {
    this.account = account;
    this.executionCode = executionCode;
    this.orderCode = orderCode;
    this.updateOrderId = updateOrderId;
    this.version = version;
    this.clientOrderId = clientOrderId;
    this.actionCode = actionCode;
    this.instrument = instrument;
    this.status = status;
    this.finalStatus = finalStatus;
    this.filledQuantity = filledQuantity;
    this.lastQuantity = lastQuantity;
    this.filledQuantityNotional = filledQuantityNotional;
    this.lastQuantityNotional = lastQuantityNotional;
    this.remainingQuantity = remainingQuantity;
    this.lastPrice = lastPrice;
    this.averagePrice = averagePrice;
    this.transactionTime = transactionTime;
    this.marginRate = marginRate;
    this.rejectReason = rejectReason;
    this.rejectCode = rejectCode;
  }
}
