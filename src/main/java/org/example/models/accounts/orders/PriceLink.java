package org.example.models.accounts.orders;

public enum PriceLink {

  /**
   * Stop price of the order will be taken from parent trigger order's fill price.
   */
  TRIGGERED_STOP,

  /**
   * Limit price of the order will be taken from parent trigger order's fill price.
   */
  TRIGGERED_LIMIT
}
