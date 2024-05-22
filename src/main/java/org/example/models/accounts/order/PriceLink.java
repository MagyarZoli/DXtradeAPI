package org.example.models.accounts.order;

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
