package org.example.models.symbol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SymbolCurrency
implements Symbol {

  ADA,
  AUD_S("AUD$"),
  BCH_S("BCH$"),
  BTC_S("BTC$"),
  CAD_S("CAD$"),
  CHF_S("CHF$"),
  CNH_S("CNH$"),
  CNY_S("CNY$"),
  COCOA,
  COFFEE,
  CORN,
  CZK_S("CZK$"),
  DASH_S("DASH$"),
  DKK_S("DKK$"),
  DOGE_S("DOGE$"),
  DOT,
  EOS_S("EOS$"),
  ETC_S("ETC$"),
  ETH_S("ETH$"),
  EUR_S("EUR$"),
  GBP_S("GBP$"),
  HKD_S("HKD$"),
  HUF_S("HUF$"),
  ILS_S("ILS$"),
  INR_S("INR$"),
  JPY_S("JPY$"),
  KRW_S("KRW$"),
  LTC_S("LTC$"),
  MXN_S("MXN$"),
  NEO,
  NOK_S("NOK$"),
  NZD_S("NZD$"),
  PLN_S("PLN$"),
  RUB_S("RUB$"),
  SEK_S("SEK$"),
  SGD_S("SGD$"),
  SKK_S("SKK$"),
  SOYBEAN,
  THB_S("THB$"),
  TRY_S("TRY$"),
  UAH_S("UAH$"),
  USD_S("USD$"),
  USDT_S("USDT$"),
  WHEAT,
  XDG_S("XDG$"),
  XLM_S("XLM$"),
  XMR_S("XMR$"),
  XRP_S("XRP$"),
  ZAR_S("ZAR$");

  private String name;

  @Override
  public String toString() {
    if (this.name == null) {
      return super.name();
    }
    return this.name;
  }
}
