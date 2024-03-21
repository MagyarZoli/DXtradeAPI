package org.example.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDetails {

  /**
   * Login of the user.
   */
  private String login;

  /**
   * Domain of the user.
   */
  private String domain;

  /**
   * Numeric version of the user.
   * Client can use this value to find out which of the updates is more recent (its version is greater).
   */
  private Integer version;

  /**
   * Full name of the user.
   */
  private String fullName;

  /**
   * List of accounts owned by the user.
   */
  private AccountDetails[] accounts;

  /**
   * Full username in the login@domain format.
   */
  private String username;

  @JsonCreator
  public UserDetails(
      @JsonProperty(value = "login") String login,
      @JsonProperty(value = "domain") String domain,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "fullName") String fullName,
      @JsonProperty(value = "accounts") AccountDetails[] accounts,
      @JsonProperty(value = "username") String username
  ) {
    this.login = login;
    this.domain = domain;
    this.version = version;
    this.fullName = fullName;
    this.accounts = accounts;
    this.username = username;
  }
}
