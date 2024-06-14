package org.example.transfer.accounts.orders;

import lombok.Getter;

import java.util.Random;

public class OrderCode {

  @Getter
  private String orderCode;

  private static final Integer ORIGIN = 10_000;
  private static final Integer BOUND = 100_000;

  public OrderCode() {
    setOrderCode();
  }

  public OrderCode(Integer origin, Integer bound) {
    setOrderCode(origin, bound);
  }

  public void setOrderCode() {
    this.orderCode = OrderCode.makesOrderCode();
  }

  public void setOrderCode(Integer origin, Integer bound) {
    this.orderCode = OrderCode.makesOrderCode(origin, bound);
  }

  public static String makesOrderCode() {
    return String.valueOf(new Random().nextInt(ORIGIN, BOUND));
  }

  public static String makesOrderCode(Integer order, Integer bound) {
    return String.valueOf(new Random().nextInt(order, bound));
  }
}
