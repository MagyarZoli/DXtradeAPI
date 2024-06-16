package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class AccountEventList
implements Model, AccountsModel {

  private List<AccountEvent> accountEvents;

  @JsonCreator
  public AccountEventList(
      @JsonProperty(value = "accountEvents") List<AccountEvent> accountEvents
  ) {
    this.accountEvents = accountEvents;
  }

  @Override
  public List<? extends AccountModel> getAccounts() {
    return getAccountEvents();
  }
}
