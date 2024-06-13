package org.example.transfer.accounts.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NonNull;
import org.example.exception.APIException;

import java.io.IOException;

@JsonDeserialize(using = Quantity.QuantityDeserializer.class)
public class Quantity {

  @Getter
  private Integer quantity;

  public static final Double MIN_QUANTITY = 0.000_1;
  public static final Double MAX_QUANTITY = 10.0;
  public static final Integer DEFAULT_QUANTITY = 0;

  @JsonCreator
  public Quantity(
      @JsonProperty(value = "quantity") Integer quantity
  ) {
    this.quantity = quantity;
  }

  public Quantity(@NonNull Double value) {
    this.quantity = Quantity.makesQuantity(value);
  }

  public void setQuantity(@NonNull Double value) {
    this.quantity = Quantity.makesQuantity(value);
  }

  public static Integer makesQuantity(@NonNull Double quantity) {
    if (quantity < 0) {
      quantity = quantity * -1;
    }
    if (MIN_QUANTITY <= quantity) {
      if (quantity <= MAX_QUANTITY) {
        return (int) (quantity * 100_000);
      } else {
        throw new APIException("quantity too high " + quantity + " > " + MAX_QUANTITY);
      }
    } else {
      throw new APIException("quantity too low " + quantity + " < " + MIN_QUANTITY);
    }
  }

  public static class QuantityDeserializer
  extends JsonDeserializer<Quantity> {

    @Override
    public Quantity deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
      Integer quantity = jsonParser.getIntValue();
      Double value = (double) quantity / 100_000.0;
      return new Quantity(value);
    }
  }
}
