package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.AccountCode;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMetric
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
   * Balance + Floating Profit/Loss.
   */
  private Number equity;

  /**
   * Total balance of the account in account’s base currency.
   */
  private Number balance;

  /**
   * Total balance available for withdrawal in account’s base currency.
   */
  private Number availableBalance;

  /**
   * Funds available for trading in the account’s base currency.
   */
  private Number availableFunds;

  private Number credit;

  /**
   * Free margin (Equity - Margin).
   */
  private Number marginFree;

  /**
   * Open (Unrealized) Profit/Loss.
   */
  private Number openPl;

  /**
   * Total PnL on the account.
   */
  private Number totalPl;

  /**
   * Used margin.
   */
  private Number margin;

  /**
   * Number of open (non-zero quantity) positions.
   */
  private Number openPositionsCount;

  /**
   * Number of order chains in non-final state
   */
  private Number openOrdersCount;

  /**
   * If requested, contains a list of per-position metrics.
   */
  private List<PositionMetrics> positions;

  /**
   * If requested, contains a list of individual currency position metrics.
   */
  private List<CurrencyMetrics> balances;

  @JsonCreator
  public AccountMetric(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "equity") Number equity,
      @JsonProperty(value = "balance") Number balance,
      @JsonProperty(value = "availableBalance") Number availableBalance,
      @JsonProperty(value = "availableFunds") Number availableFunds,
      @JsonProperty(value = "credit") Number credit,
      @JsonProperty(value = "marginFree") Number marginFree,
      @JsonProperty(value = "openPl") Number openPl,
      @JsonProperty(value = "totalPl") Number totalPl,
      @JsonProperty(value = "margin") Number margin,
      @JsonProperty(value = "openPositionsCount") Number openPositionsCount,
      @JsonProperty(value = "openOrdersCount") Number openOrdersCount,
      @JsonProperty(value = "positions") List<PositionMetrics> positions,
      @JsonProperty(value = "balances") List<CurrencyMetrics> balances
  ) {
    this.account = account;
    this.version = version;
    this.equity = equity;
    this.balance = balance;
    this.availableBalance = availableBalance;
    this.availableFunds = availableFunds;
    this.credit = credit;
    this.marginFree = marginFree;
    this.openPl = openPl;
    this.totalPl = totalPl;
    this.margin = margin;
    this.openPositionsCount = openPositionsCount;
    this.openOrdersCount = openOrdersCount;
    this.positions = positions;
    this.balances = balances;
  }
}
