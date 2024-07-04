package org.example.auxiliary;

import org.example.models.conversion.CurrencyInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MonetaryConversionTest {

  private Number convRate;

  static Stream<CurrencyInstance> currencyInstanceStream() {
    return Stream.of(CurrencyInstance.values());
  }

  @ParameterizedTest
  @MethodSource(value = "currencyInstanceStream")
  void test(CurrencyInstance toCurrency) {
    assertDoesNotThrow(() -> convRate = MonetaryConversion.convRate(toCurrency, CurrencyInstance.USD));
    System.out.println(convRate);
  }
}