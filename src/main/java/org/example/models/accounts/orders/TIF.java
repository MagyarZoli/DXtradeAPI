package org.example.models.accounts.orders;

public enum TIF {

  /**
   * (Markets, Limits, Stops)
   */
  GTC,

  /**
   * (Limits, Stops)
   */
  DAY,


  /**
   * (Limits, Stops, requires the expireDate field)
   */
  GTD,
  IOC,
  FOK;
}
