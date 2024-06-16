package org.example.transfer.users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.example.models.Model;

import java.io.IOException;

@Data
@JsonDeserialize(using = FullUserName.FullUserNameDeserializer.class)
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

  public static class FullUserNameDeserializer
  extends JsonDeserializer<FullUserName> {

    @Override
    public FullUserName deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
      String fullUsername = jsonParser.getText();
      if (fullUsername != null && fullUsername.contains("@")) {
        String[] parts = fullUsername.split("@");
        if (parts.length == 2) {
          return new FullUserName(parts[0], parts[1]);
        }
      }
      return null;
    }
  }
}
