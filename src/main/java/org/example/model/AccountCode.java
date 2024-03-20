package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NonNull;
import org.example.auxiliary.URLCoder;

@ToString
@EqualsAndHashCode
public class AccountCode {

  @Getter
  @Setter
  private String account;

  @Getter
  private String originalAccount;

  @JsonCreator
  public AccountCode(@JsonProperty(value = "account") String account) {
    this.account = account;
    this.originalAccount = URLCoder.urlDecode(account);
  }

  public AccountCode(String clearing, @NonNull String originalAccount) {
    if (clearing == null || clearing.isEmpty() || clearing.isBlank()) {
      clearing = "default";
    }
    this.originalAccount = clearing + ":" + account;
    this.account = URLCoder.urlEncode(originalAccount);
  }

  public void setOriginalAccount(String originalAccount) {
    this.originalAccount = originalAccount;
    this.account = URLCoder.urlEncode(originalAccount);
  }
}
