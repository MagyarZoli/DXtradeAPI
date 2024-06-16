package org.example.transfer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.Data;
import org.example.models.Model;

import java.io.IOException;

@Data
@JsonDeserialize(using = ClientId.ClientIdDeserializer.class)
public class ClientId
implements Model {

  private String clientID;

  @JsonCreator
  public ClientId(
      @JsonProperty(value = "clientID") String clientID
  ) {
    this.clientID = clientID;
  }

  public void setClientID(String clientID) {
    this.clientID = maxSize64(clientID);
  }

  private String maxSize64(String str) {
    int length = str.length();
    if (length > 64) {
      StringBuilder newStr = new StringBuilder();
      char[] strChars = str.toCharArray();
      char[] lengthChars = String.valueOf(length).toCharArray();
      int lengthCharsSize = lengthChars.length;
      newStr.append(strChars, 0, (64 - 1 - lengthCharsSize))
          .append(lengthChars, 0, lengthCharsSize)
          .append(strChars, strChars.length - 1, 1);
      return newStr.toString();
    }
    return str;
  }

  public static class ClientIdDeserializer
  extends StdDeserializer<ClientId> {

    public ClientIdDeserializer() {
      this(null);
    }

    public ClientIdDeserializer(Class<?> vc) {
      super(vc);
    }

    @Override
    public ClientId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
      JsonNode node = jsonParser.getCodec().readTree(jsonParser);
      String clientID = node.asText();
      return new ClientId(clientID);
    }
  }
}
