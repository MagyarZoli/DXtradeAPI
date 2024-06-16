package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.AccountCode;
import org.example.transfer.ClientId;
import org.example.transfer.NumberDeserializer;


@Data
public class CashTransaction
implements Model {

  /**
   * Unique code of the account this transaction belongs to.
   */
  private AccountCode account;

  /**
   * Unique string code of the transaction
   */
  @JsonDeserialize(using = NumberDeserializer.class)
  private Number transactionCode;

  /**
   * Internal unique string code of an order chain in the system.
   * Absent if this transaction is not related to an order.
   */
  private String orderCode;

  /**
   * Unique string code of the trade. Corresponds to the executionCode of a trade (see Execution).
   */
  private String tradeCode;

  /**
   * Numeric version of the account to which this transaction relates.
   */
  private Integer version;

  /**
   * Unique order id assigned by the client during order placement.
   */
  private ClientId clientOrderId;

  /**
   * Cash transaction type.
   */
  private TransactionType type;

  /**
   * Amount of the transaction in account’s base currency for margin accounts and in transaction's currency for cash accounts, signed (positive means profit, negative means loss)
   */
  private Number value;

  /**
   * Currency of this transaction.
   */
  private String currency;

  /**
   * Timestamp in UTC of this transaction.
   */
  private String transactionTime;

  @JsonCreator
  public CashTransaction(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "transactionCode") Number transactionCode,
      @JsonProperty(value = "orderCode") String orderCode,
      @JsonProperty(value = "tradeCode") String tradeCode,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "clientOrderId") ClientId clientOrderId,
      @JsonProperty(value = "type") TransactionType type,
      @JsonProperty(value = "value") Number value,
      @JsonProperty(value = "currency") String currency,
      @JsonProperty(value = "transactionTime") String transactionTime
  ) {
    this.account = account;
    this.transactionCode = transactionCode;
    this.orderCode = orderCode;
    this.tradeCode = tradeCode;
    this.version = version;
    this.clientOrderId = clientOrderId;
    this.type = type;
    this.value = value;
    this.currency = currency;
    this.transactionTime = transactionTime;
  }
}
