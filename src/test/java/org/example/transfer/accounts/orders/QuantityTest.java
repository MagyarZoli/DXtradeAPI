package org.example.transfer.accounts.orders;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class QuantityTest {

  static Stream<Double> streamObj() {
    return Stream.of(
        10.0, 1.0, 0.1, 0.01, 0.001, 0.000_1,
        -10.0, -1.0, -0.1, -0.01, -0.001, -0.000_1
    );
  }

  @ParameterizedTest
  @MethodSource(value = "streamObj")
  void testMakesQuantity(Double number) {
    Integer rightQuantity = Quantity.makesQuantity(number);

    System.out.println(rightQuantity);
    assertTrue(10 <= rightQuantity && rightQuantity <= 1_000_000);
  }

  @ParameterizedTest
  @MethodSource(value = "streamObj")
  void testSetQuantity(Double number) {
    Quantity quantity = new Quantity(number);
    Integer rightQuantity = quantity.getQuantity();

    System.out.println(rightQuantity);
    assertTrue(10 <= rightQuantity && rightQuantity <= 1_000_000);
  }
}