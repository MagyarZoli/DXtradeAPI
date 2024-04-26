package org.example.models.symbol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SymbolCFDStock
implements Symbol {

  AAPL,
  ABNB,
  ADBE,
  AIRF,
  ALVG,
  AMZN,
  ATVI,
  BABA,
  BAC,
  BAT_TST,
  BLK,
  CS,
  DB,
  DEVX,
  DIS,
  DPZ,
  ETFC,
  EXPE,
  GE,
  GOOG,
  HOG,
  IBE,
  INTC,
  JNJ,
  JPM,
  LVMH,
  LVS,
  MAR,
  META,
  MS,
  MSFT,
  MU,
  NFLX,
  NKE,
  NVDA,
  NWS,
  PCG,
  PEP,
  PFE,
  PG,
  PM,
  PYPL,
  QCOM,
  RACE,
  RBAG,
  RL,
  SBUX,
  T,
  TMUS,
  TRIP,
  TROW,
  TSLA,
  TWC,
  TWTR,
  UPS,
  V,
  VOD,
  VOWG_P("VOWG_p"),
  VZ,
  WFM,
  WMT,
  YELP,
  YHOO,
  YNDX,
  ZM;

  private String name;

  @Override
  public String toString() {
    if (this.name == null) {
      return super.name();
    }
    return this.name;
  }
}
