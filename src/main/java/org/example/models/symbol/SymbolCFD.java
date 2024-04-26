package org.example.models.symbol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum SymbolCFD
implements Symbol {

  AUS200_CASH("AUS200.cash"),
  BAY_GN("BAYGn"),
  COCOA_C("COCOA.c"),
  COFFEE_C("COFFEE.c"),
  CORN_C("CORN.c"),
  DBK_GN("DBKGn"),
  DX_F("DX.f"),
  ERBN_F("ERBN.f"),
  EU50_CASH("EU50.cash"),
  FRA40_CASH("FRA40.cash"),
  GER40_CASH("GER40.cash"),
  HK50_CASH("HK50.cash"),
  JP225_CASH("JP225.cash"),
  NATGAS_F("NATGAS.f"),
  SOYBEN_C("SOYBEAN.c"),
  SPN35_CASH("SPN35.cash"),
  UK100_CASH("UK100.cash"),
  UKOIL_CASH("UKOIL.cash"),
  US100_CASH("US100.cash"),
  US2000_CASH("US2000.cash"),
  US30_CASH("US30.cash"),
  US500_CASH("US500.cash"),
  USOIL_CASH("USOIL.cash"),
  USTN10_F("USTN10.f"),
  WHEAT_C("WHEAT.c"),
  XAGAUD,
  XAGEUR,
  XAGUSD,
  XAUAUD,
  XAUEUR,
  XAUUSD,
  XPDUSD,
  XPT,
  XPTUSD;

  private String name;

  @Override
  public String toString() {
    if (this.name == null) {
      return super.name();
    }
    return this.name;
  }
}
