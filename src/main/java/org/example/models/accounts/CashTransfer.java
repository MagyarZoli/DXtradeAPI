package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.AccountCode;

import java.util.List;

@Data
public class CashTransfer
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
   * Unique code of a transfer.
   * Can be absent if this transfer is originated from a trade
   */
  private String transferCode;

  /**
   * Transfer comment, if present.
   */
  private String comment;

  /**
   * Timestamp in UTC of this transfer.
   */
  private String transactionTime;

  /**
   * List of all cash transactions related to this transfer.
   * Reports are ordered chronologically.
   */
  private List<CashTransaction> cashTransactions;

  @JsonCreator
  public CashTransfer(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "transferCode") String transferCode,
      @JsonProperty(value = "comment") String comment,
      @JsonProperty(value = "transactionTime") String transactionTime,
      @JsonProperty(value = "cashTransactions") List<CashTransaction> cashTransactions
  ) {
    this.account = account;
    this.version = version;
    this.transferCode = transferCode;
    this.comment = comment;
    this.transactionTime = transactionTime;
    this.cashTransactions = cashTransactions;
  }
}
