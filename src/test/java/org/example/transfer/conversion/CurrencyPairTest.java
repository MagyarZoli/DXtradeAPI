package org.example.transfer.conversion;

import org.example.models.conversion.CurrencyInstance;
import org.example.models.symbol.SymbolForexPair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyPairTest {

  @Test
  void testReverseCurrencyInstance() {
    CurrencyInstance fromCurrency = CurrencyInstance.USD;
    CurrencyInstance toCurrency = CurrencyInstance.AUD;
    CurrencyPair currencyPair = new CurrencyPair(fromCurrency, toCurrency).reverse();

    System.out.println(currencyPair);
    assertNotNull(currencyPair);
    assertEquals(fromCurrency, currencyPair.getToCurrency());
    assertEquals(toCurrency, currencyPair.getFromCurrency());
  }

  @Test
  void testReverseSymbolForexPair() {
    SymbolForexPair symbolForexPair = SymbolForexPair.AUDJPY;
    CurrencyPair currencyPair = new CurrencyPair(symbolForexPair).reverse();

    System.out.println(currencyPair);
    assertNotNull(currencyPair);
    assertEquals(symbolForexPair.getFromCurrency(), currencyPair.getToCurrency());
    assertEquals(symbolForexPair.getToCurrency(), currencyPair.getFromCurrency());
  }
}