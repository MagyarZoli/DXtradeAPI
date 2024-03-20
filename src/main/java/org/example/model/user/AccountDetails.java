package org.example.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.model.AccountCode;

import java.sql.Timestamp;

@Data
public class AccountDetails {

  /**
   * Full username of the owner in the login@domain format.
   */
  private String owner;

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
   * Base currency of the account.
   */
  private String baseCurrency;

  /**
   * Timestamp in UTC when an account was registered.
   */
  private Timestamp registrationTime;

  /**
   * Status of the account.
   * One of FULL_TRADING, CLOSE_ONLY, NO_TRADING, TERMINATED, EXPIRED.
   */
  private AccountStatus accountStatus;

  /**
   * Unique identifier of a custom per-account pricing stream.
   */
  private String pricingStream;

  /**
   * This flag shows if the account is Position-based or Net-based
   */
  private Boolean positionBased;

  @JsonCreator
  public AccountDetails(
      @JsonProperty(value = "owner") String owner,
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "baseCurrency") String baseCurrency,
      @JsonProperty(value = "registrationTime") Timestamp registrationTime,
      @JsonProperty(value = "accountStatus") AccountStatus accountStatus,
      @JsonProperty(value = "pricingStream") String pricingStream,
      @JsonProperty(value = "positionBased") Boolean positionBased
  ) {
    this.owner = owner;
    this.account = account;
    this.version = version;
    this.baseCurrency = baseCurrency;
    this.registrationTime = registrationTime;
    this.accountStatus = accountStatus;
    this.pricingStream = pricingStream;
    this.positionBased = positionBased;
  }
}
