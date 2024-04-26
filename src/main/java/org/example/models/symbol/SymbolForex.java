package org.example.models.symbol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SymbolForex
implements Symbol {

  ADAUSD,
  AUDCAD,
  AUDCHF,
  AUDJPY,
  AUDNZD,
  AUDUSD,
  BTCUSD,
  CADCHF,
  CADJPY,
  CHFJPY,
  DASHUSD,
  DOGEUSD,
  DOTUSD,
  ETHUSD,
  EURAUD,
  EURCAD,
  EURCHF,
  EURCZK,
  EURGBP,
  EURHUF,
  EURJPY,
  EURNOK,
  EURNZD,
  EURPLN,
  EURRUB,
  EURUSD,
  GBPAUD,
  GBPCAD,
  GBPCHF,
  GBPJPY,
  GBPNZD,
  GBPUSD,
  LTCUSD,
  NEOUSD,
  NZDCAD,
  NZDCHF,
  NZDJPY,
  NZDUSD,
  USDCAD,
  USDCHF,
  USDCZK,
  USDHKD,
  USDHUF,
  USDILS,
  USDILS_IS("USDILS.is"),
  USDJPY,
  USDMXN,
  USDNOK,
  USDPLN,
  USDRUB,
  USDSEK,
  USDSGD,
  USDTRY,
  USDZAR,
  XMRUSD,
  XRPUSD;

  private String name;

  @Override
  public String toString() {
    if (this.name == null) {
      return super.name();
    }
    return this.name;
  }
}
