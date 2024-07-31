package org.example.models.instruments;

import org.example.models.symbol.Symbol;
import org.example.models.symbol.SymbolCurrency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SymbolCurrencyTest {

  @ParameterizedTest
  @EnumSource(value = SymbolCurrency.class)
  void test(SymbolCurrency symbolCurrency) {
    System.out.println(symbolCurrency + " " + symbolCurrency.name());
  }

  @ParameterizedTest
  @EnumSource(value = SymbolCurrency.class)
  void test(Symbol symbol) {
    System.out.println(symbol + " " + symbol.name());
  }
}