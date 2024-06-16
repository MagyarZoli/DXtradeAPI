package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class AccountPortfolioList
implements Model, AccountsModel {

  private List<AccountPortfolio> portfolios;

  @JsonCreator
  public AccountPortfolioList(
      @JsonProperty(value = "portfolios") List<AccountPortfolio> portfolios
  ) {
    this.portfolios = portfolios;
  }

  @Override
  public List<? extends AccountModel> getAccounts() {
    return getPortfolios();
  }
}
