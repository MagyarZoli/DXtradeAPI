package org.example.transfer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.models.Model;

import java.io.IOException;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = AccountCode.AccountCodeDeserializer.class)
public class AccountCode
    implements Model {

  @Getter(onMethod_ = @JsonValue)
  private String account;

  private String domain;

  @JsonCreator
  public AccountCode(
      @JsonProperty(value = "account") String account
  ) {
    this.account = "default:" + account;
  }

  @JsonCreator
  public AccountCode(
      @JsonProperty(value = "account") String account,
      @JsonProperty(value = "domain") String domain
  ) {
    this.account = domain + ':' + account;
  }

  public static class AccountCodeDeserializer
      extends JsonDeserializer<AccountCode> {

    @Override
    public AccountCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
      String value = jsonParser.getValueAsString();
      String domain = value.substring(0, value.indexOf(':'));
      value = value.replace(domain, "");
      return new AccountCode(value);
    }
  }
}