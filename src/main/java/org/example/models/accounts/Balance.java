package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.transfer.AccountCode;

@Data
public class Balance
implements Model {

  /**
   * Unique code of the account
   */
  private AccountCode account;

  /**
   * Numeric version of the account.
   */
  private Integer version;

  /**
   * Balance in the given currency, signed.
   */
  private Number value;

  /**
   * Currency of the balance.
   */
  private String currency;

  @JsonCreator
  public Balance(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "value") Number value,
      @JsonProperty(value = "currency") String currency
  ) {
    this.account = account;
    this.version = version;
    this.value = value;
    this.currency = currency;
  }
}
