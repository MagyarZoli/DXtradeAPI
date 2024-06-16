package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class CashTransferList
implements Model, AccountsModel {

  private List<CashTransfer> cashTransfers;

  @JsonCreator
  public CashTransferList(
      @JsonProperty(value = "cashTransfers") List<CashTransfer> cashTransfers
  ) {
    this.cashTransfers = cashTransfers;
  }

  @Override
  public List<? extends AccountModel> getAccounts() {
    return getCashTransfers();
  }
}
