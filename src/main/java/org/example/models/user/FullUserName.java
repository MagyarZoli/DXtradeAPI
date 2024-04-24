package org.example.models.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.example.models.Model;

@Data
public class FullUserName
implements Model {

  @Getter(onMethod_ = @JsonValue)
  private String owner;

  @ToString.Exclude
  private String username;

  @ToString.Exclude
  private String domain;

  @JsonCreator
  public FullUserName(
      @JsonProperty(value = "owner") String owner
  ) {
    this.owner = owner;
    if (owner != null && owner.contains("@")) {
      String[] parts = owner.split("@");
      if (parts.length == 2) {
        this.username = parts[0];
        this.domain = parts[1];
      }
    }
  }

  public FullUserName(String username, String domain) {
    this.username = username;
    this.domain = domain;
    this.owner = username + "@" + domain;
  }
}
