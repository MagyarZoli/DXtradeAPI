package org.example.models.accounts.order;

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
