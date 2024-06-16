package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class AccountMetricList
implements Model, AccountsModel {

  private List<AccountMetric> metrics;

  @JsonCreator
  public AccountMetricList(
      @JsonProperty(value = "metrics") List<AccountMetric> metrics
  ) {
    this.metrics = metrics;
  }

  @Override
  public List<? extends AccountModel> getAccounts() {
    return getMetrics();
  }
}
