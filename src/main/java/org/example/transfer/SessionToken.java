package org.example.transfer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.models.Model;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class SessionToken
implements Model {

  private String sessionToken;

  private String timeout;

  @JsonCreator
  public SessionToken(
      @NonNull @JsonProperty(value = "sessionToken") String sessionToken,
      @NonNull @JsonProperty(value = "timeout") String timeout
  ) {
    this.sessionToken = sessionToken;
    this.timeout = timeout;
  }
}
