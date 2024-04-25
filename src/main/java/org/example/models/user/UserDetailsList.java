package org.example.models.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

import java.util.List;

@Data
public class UserDetailsList
implements Model {

  private List<UserDetails> userDetails;

  @JsonCreator
  public UserDetailsList(
      @JsonProperty(value = "userDetails") List<UserDetails> userDetails
  ) {
    this.userDetails = userDetails;
  }
}
