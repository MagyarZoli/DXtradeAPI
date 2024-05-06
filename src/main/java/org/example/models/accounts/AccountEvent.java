package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.EventType;
import org.example.models.Model;
import org.example.transfer.AccountCode;

@Data
public class AccountEvent
implements EventType<AccountEventType>, Model, AccountModel {

  /**
   * Unique code of the account.
   */
  private AccountCode account;

  /**
   * One of the following values: MARGIN_CALL or LIQUIDATION.
   */
  private AccountEventType eventType;

  /**
   * Risk level threshold in fractional %
   */
  private Number riskLevelThreshold;

  /**
   * Actual risk level in fractional %
   */
  private Number actualRiskLevel;

  @JsonCreator
  public AccountEvent(
      @JsonProperty(value = "account") AccountCode account,
      @JsonProperty(value = "eventType") AccountEventType eventType,
      @JsonProperty(value = "riskLevelThreshold") Number riskLevelThreshold,
      @JsonProperty(value = "actualRiskLevel") Number actualRiskLevel
  ) {
    this.account = account;
    this.eventType = eventType;
    this.riskLevelThreshold = riskLevelThreshold;
    this.actualRiskLevel = actualRiskLevel;
  }
}
