package org.example.models.symbol;

import lombok.Getter;
import org.example.models.conversion.CurrencyInstance;

import static org.example.models.conversion.CurrencyInstance.*;

@Getter
public enum SymbolForexPair {

  ADAUSD(AUD, USD),
  AUDCAD(AUD, CAD),
  AUDJPY(AUD, JPY),
  AUDNZD(AUD, NZD),
  AUDUSD(AUD, USD),
  CADJPY(CAD, JPY),
  EURAUD(EUR, AUD),
  EURCAD(EUR, CAD),
  EURCZK(EUR, CZK),
  EURGBP(EUR, GBP),
  EURHUF(EUR, HUF),
  EURJPY(EUR, JPY),
  EURNOK(EUR, NOK),
  EURNZD(EUR, NZD),
  EURPLN(EUR, PLN),
  EURRUB(EUR, RUB),
  EURUSD(EUR, USD),
  GBPAUD(GBP, AUD),
  GBPCAD(GBP, CAD),
  GBPJPY(GBP, JPY),
  GBPNZD(GBP, NZD),
  GBPUSD(GBP, USD),
  NZDCAD(NZD, CAD),
  NZDJPY(NZD, JPY),
  NZDUSD(NZD, USD),
  USDCAD(USD, CAD),
  USDCZK(USD,CZK),
  USDHKD(USD, HKD),
  USDHUF(USD, HUF),
  USDILS(USD, ILS),
  USDJPY(USD, JPY),
  USDMXN(USD, MXN),
  USDNOK(USD, NOK),
  USDPLN(USD, PLN),
  USDRUB(USD, RUB),
  USDSEK(USD, SEK),
  USDSGD(USD, SGD),
  USDTRY(USD, TRY),
  USDZAR(USD, ZAR);

  private String name;
  private final CurrencyInstance fromCurrency;
  private final CurrencyInstance toCurrency;


  SymbolForexPair(CurrencyInstance fromCurrency, CurrencyInstance toCurrency) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
  }

  @Override
  public String toString() {
    if (this.name == null) {
      return super.name();
    }
    return this.name;
  }
}
