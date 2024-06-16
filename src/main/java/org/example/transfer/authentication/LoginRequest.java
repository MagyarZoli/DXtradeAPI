package org.example.transfer.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

public class LoginRequest {

  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final String username;

  @Getter
  @ToString.Include
  @EqualsAndHashCode.Include
  private final String domain;

  private final String password;

  @JsonCreator
  public LoginRequest(
      @JsonProperty(value = "username") final String username,
      @JsonProperty(value = "password") final String password
  ) {
    this.username = username;
    this.domain = "default";
    this.password = password;
  }

  @JsonCreator
  public LoginRequest(
      @JsonProperty(value = "username") final String username,
      @JsonProperty(value = "domain") final String domain,
      @JsonProperty(value = "password") final String password
  ) {
    this.username = username;
    this.domain = domain;
    this.password = password;
  }

  public JSONObject getLoginRequestJSONObject() {
    return new JSONObject()
        .put("username", username)
        .put("domain", domain)
        .put("password", password);
  }
}
