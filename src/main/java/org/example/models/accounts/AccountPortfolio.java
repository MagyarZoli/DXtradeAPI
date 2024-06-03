package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.models.accounts.orders.Order;
import org.example.transfer.AccountCode;

import java.util.List;

@Data
public class AccountPortfolio
implements Model, AccountModel {

  /**
   * Unique code of the account.
   */
  private AccountCode account;

  /**
   * Numeric version of the account.
   * Clients can use this value to find out which of the updates is more recent (its version is greater).
   */
  private Integer version;

  /**
   * Cash balances in various currencies. Zero balances are not included.
   */
  private List<Balance> balances;

  /**
   * Current positions on an account.
   */
  private List<Position> positions;

  /**
   * Current orders on an account.
   */
  private List<Order> orders;

  @JsonCreator
  public AccountPortfolio(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "balances") List<Balance> balances,
      @JsonProperty(value = "positions") List<Position> positions,
      @JsonProperty(value = "orders") List<Order> orders
  ) {
    this.account = account;
    this.version = version;
    this.balances = balances;
    this.positions = positions;
    this.orders = orders;
  }
}
