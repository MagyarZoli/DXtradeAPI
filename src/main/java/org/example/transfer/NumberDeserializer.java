package org.example.transfer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class NumberDeserializer
extends StdDeserializer<Number> {

  public NumberDeserializer() {
    this(null);
  }

  public NumberDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Number deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode node = jsonParser.getCodec().readTree(jsonParser);
    String transactionCode = node.asText();
    String numericPart = extractNumericPart(transactionCode);
    return Long.parseLong(numericPart);
  }

  private String extractNumericPart(String transactionCode) {
    int lastColonIndex = transactionCode.lastIndexOf(':');
    if (lastColonIndex != -1 && lastColonIndex < transactionCode.length() - 1) {
      return transactionCode.substring(lastColonIndex + 1);
    }
    throw new IllegalArgumentException("Invalid transaction code format: " + transactionCode);
  }
}
