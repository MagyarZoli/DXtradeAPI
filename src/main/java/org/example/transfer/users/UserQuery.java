package org.example.transfer.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class UserQuery {

  private Integer limit;
  private Integer startFrom;
  private String lastUpdateFrom;
  private String lastUpdateTo;

  @JsonCreator
  public UserQuery(
      @JsonProperty(value = "limit") Integer limit,
      @JsonProperty(value = "startFrom") Integer startFrom,
      @JsonProperty(value = "lastUpdateFrom") String lastUpdateFrom,
      @JsonProperty(value = "lastUpdateTo") String lastUpdateTo
  ) {
    this.limit = limit;
    this.startFrom = startFrom;
    this.lastUpdateFrom = lastUpdateFrom;
    this.lastUpdateTo = lastUpdateTo;
  }
}
