package org.example.models.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;

@Data
public class PositionMetrics
implements Model {

  /**
   * Unique code of a position.
   */
  private String positionCode;

  /**
   * Numeric version of the account.
   */
  private Integer version;

  /**
   * Symbol of the position’s instrument.
   */
  private String symbol;

  /**
   * Rate used to calculate FPL (the "opposite" from the position, Bid for long positions, Ask for Short positions multiplied by conversion rate from instrument’s currency to account’s base currency)
   */
  private Number rate;

  /**
   * Floating profit-loss in account’s base currency.
   */
  private Number fpl;

  /**
   * Current conversion rate from the instrument’s currency to account’s base currency
   */
  private Number convRate;

  /**
   * Bid price of the instrument at the moment of position opening.
   */
  private Number bidOpen;

  /**
   * Ask price of the instrument at the moment of position opening.
   */
  private Number askOpen;

  private Number marginRate;

  /**
   * Margin in account’s base currency.
   */
  private Number margin;

  /**
   * Current quantity of the position in units (not in lots).
   */
  private Number quantity;

  /**
   * Current quantity of the position in notional units (not in lots).
   */
  private Number quantityNotional;

  /**
   * Average price for a position in account currency.
   */
  private Number openPrice;

  /**
   * Average price for a position in instrument currency.
   */
  private Number openPriceInstrumentCcy;

  /**
   * Floating profit-loss in instrument’s base currency
   */
  private Number fplInstrumentCcy;

  /**
   * Instrument’s base currency symbol.
   */
  private String instrumentCurrency;

  /**
   * Current instrument price from the raw LP feed (only for offset accounts)
   */
  private Number currentPriceInstrumentCcy;

  @JsonCreator
  public PositionMetrics(
      @JsonProperty(value = "positionCode") String positionCode,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "symbol") String symbol,
      @JsonProperty(value = "rate") Number rate,
      @JsonProperty(value = "fpl") Number fpl,
      @JsonProperty(value = "convRate") Number convRate,
      @JsonProperty(value = "bidOpen") Number bidOpen,
      @JsonProperty(value = "askOpen") Number askOpen,
      @JsonProperty(value = "marginRate") Number marginRate,
      @JsonProperty(value = "margin") Number margin,
      @JsonProperty(value = "quantity") Number quantity,
      @JsonProperty(value = "quantityNotional") Number quantityNotional,
      @JsonProperty(value = "openPrice") Number openPrice,
      @JsonProperty(value = "openPriceInstrumentCcy") Number openPriceInstrumentCcy,
      @JsonProperty(value = "fplInstrumentCcy") Number fplInstrumentCcy,
      @JsonProperty(value = "instrumentCurrency") String instrumentCurrency,
      @JsonProperty(value = "currentPriceInstrumentCcy") Number currentPriceInstrumentCcy
  ) {
    this.positionCode = positionCode;
    this.version = version;
    this.symbol = symbol;
    this.rate = rate;
    this.fpl = fpl;
    this.convRate = convRate;
    this.bidOpen = bidOpen;
    this.askOpen = askOpen;
    this.marginRate = marginRate;
    this.margin = margin;
    this.quantity = quantity;
    this.quantityNotional = quantityNotional;
    this.openPrice = openPrice;
    this.openPriceInstrumentCcy = openPriceInstrumentCcy;
    this.fplInstrumentCcy = fplInstrumentCcy;
    this.instrumentCurrency = instrumentCurrency;
    this.currentPriceInstrumentCcy = currentPriceInstrumentCcy;
  }
}
