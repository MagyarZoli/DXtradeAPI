package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class CurrencyMetrics
implements Model {

  /**
   * Numeric version of the account.
   */
  private Integer version;

  /**
   * Symbol of the currency.
   */
  private String symbol;

  /**
   * Cash in the corresponding currency.
   */
  private Number balance;

  /**
   * Funds allocated to hold the current portfolio of product positions and / or orders for the whole account
   */
  private Number allocatedFunds;

  private Number availableFunds;

  private Number availableBalanceForWithdrawal;

  /**
   * Current conversion rate from this currency to account’s base currency.
   */
  private Number convRate;

  /**
   * Current price precision of this currency
   */
  private Number precision;

  @JsonCreator
  public CurrencyMetrics(
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "symbol") String symbol,
      @JsonProperty(value = "balance") Number balance,
      @JsonProperty(value = "allocatedFunds") Number allocatedFunds,
      @JsonProperty(value = "availableFunds") Number availableFunds,
      @JsonProperty(value = "availableBalanceForWithdrawal") Number availableBalanceForWithdrawal,
      @JsonProperty(value = "convRate") Number convRate,
      @JsonProperty(value = "precision") Number precision
  ) {
    this.version = version;
    this.symbol = symbol;
    this.balance = balance;
    this.allocatedFunds = allocatedFunds;
    this.availableFunds = availableFunds;
    this.availableBalanceForWithdrawal = availableBalanceForWithdrawal;
    this.convRate = convRate;
    this.precision = precision;
  }
}
