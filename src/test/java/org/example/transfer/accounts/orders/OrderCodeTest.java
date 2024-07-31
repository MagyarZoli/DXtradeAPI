package org.example.transfer.accounts.orders;

import org.example.transfer.accounts.orders.OrderCode;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCodeTest {

  private Integer intOrderCode;

  @RepeatedTest(value = 3)
  void testMakesOrderCode() {
    String orderCode = OrderCode.makesOrderCode();

    assertNotNull(orderCode);
    assertDoesNotThrow(() -> intOrderCode = Integer.parseInt(orderCode));
    assertTrue(10_000 <= intOrderCode && intOrderCode <= 100_000);
  }

  @RepeatedTest(value = 3)
  void testSetOrderCode() {
    OrderCode orderCodeClass = new OrderCode();
    String orderCode = orderCodeClass.getOrderCode();

    assertNotNull(orderCode);
    assertDoesNotThrow(() -> intOrderCode = Integer.parseInt(orderCode));
    assertTrue(10_000 <= intOrderCode && intOrderCode <= 100_000);
  }
}