package org.example.transfer.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
public class AccountTransfer {

  private Integer limit;
  private Integer startFrom;
  private String ofType;
  private String timeFrom;
  private String timeTo;
  private String period;

  @JsonCreator
  public AccountTransfer(
      @JsonProperty(value = "limit") Integer limit,
      @JsonProperty(value = "startFrom") Integer startFrom,
      @JsonProperty(value = "ofType") String ofType,
      @JsonProperty(value = "timeFrom") String timeFrom,
      @JsonProperty(value = "timeTo") String timeTo,
      @JsonProperty(value = "period") String period
  ) {
    this.limit = limit;
    this.startFrom = startFrom;
    this.ofType = ofType;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
    this.period = period;
  }
}
