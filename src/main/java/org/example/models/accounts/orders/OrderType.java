package org.example.models.accounts.orders;

/**
 * Order type cannot be changed after the order is issued. The value must be omitted in ‘Replace’ requests except group
 * requests, which may contain new orders in Replace requests.
 */
public enum OrderType {

  MARKET,
  LIMIT,
  STOP
}
