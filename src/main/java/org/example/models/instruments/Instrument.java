package org.example.models.instruments;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.example.models.Model;

import java.util.List;

@Data
public class Instrument
implements Model, InstrumentModel {

  /**
   * Instrument type as configured on the platform (e.g. INDEX).
   */
  private InstrumentType type;

  /**
   * Unique instrument symbol.
   */
  private String symbol;

  /**
   * Version of the instrument.
   * Numeric value that is increasing with each modification
   */
  private Integer version;

  /**
   * Human-readable description of an instrument.
   */
  private String description;

  /**
   * Minimum price increment of the instrument (e.g. 0.0001).
   */
  private Number priceIncrement;

  /**
   * Size of 1 pip in the system (e.g. 0.001).
   */
  private Number pipSize;

  /**
   * Trading currency of the instrument.
   */
  private String currency;

  /**
   * Lot size of the instrument.
   */
  private Number lotSize;

  /**
   * Multiplier of the instrument.
   */
  private Number multiplier;

  /**
   * Symbol of the underlying instrument.
   * Omitted if an instrument has no underlying.
   */
  private String underlying;

  /**
   * Symbol of the product instrument (e.g. DJIA ).
   */
  private String product;

  private OptionDetails optionDetails;

  private ExpirationDetails expirationDetails;

  private SettlementType settlementType;

  /**
   * For Forex instruments, contains the first currency in the pair (e.g. EUR for EUR/USD).
   */
  private String firstCurrency;

  /**
   * Trading calendar for this instrument.
   */
  @ToString.Exclude
  private List<InstrumentSession> tradingHours;

  /**
   * For Currency instruments contains the currency type(FIAT, CRYPTO_CURRENCY or CRYPTO_TOKEN)
   */
  private CurrencyType currencyType;

  @JsonCreator
  public Instrument(
      @JsonProperty(value = "type") InstrumentType type,
      @JsonProperty(value = "symbol") String symbol,
      @JsonProperty(value = "version") Integer version,
      @JsonProperty(value = "description") String description,
      @JsonProperty(value = "priceIncrement") Number priceIncrement,
      @JsonProperty(value = "pipSize") Number pipSize,
      @JsonProperty(value = "currency") String currency,
      @JsonProperty(value = "lotSize") Number lotSize,
      @JsonProperty(value = "multiplier") Number multiplier,
      @JsonProperty(value = "underlying") String underlying,
      @JsonProperty(value = "product") String product,
      @JsonProperty(value = "optionDetails") OptionDetails optionDetails,
      @JsonProperty(value = "expirationDetails") ExpirationDetails expirationDetails,
      @JsonProperty(value = "settlementType") SettlementType settlementType,
      @JsonProperty(value = "firstCurrency") String firstCurrency,
      @JsonProperty(value = "tradingHours") List<InstrumentSession> tradingHours,
      @JsonProperty(value = "currencyType") CurrencyType currencyType
  ) {
    this.type = type;
    this.symbol = symbol;
    this.version = version;
    this.description = description;
    this.priceIncrement = priceIncrement;
    this.pipSize = pipSize;
    this.currency = currency;
    this.lotSize = lotSize;
    this.multiplier = multiplier;
    this.underlying = underlying;
    this.product = product;
    this.optionDetails = optionDetails;
    this.expirationDetails = expirationDetails;
    this.settlementType = settlementType;
    this.firstCurrency = firstCurrency;
    this.tradingHours = tradingHours;
    this.currencyType = currencyType;
  }
}
