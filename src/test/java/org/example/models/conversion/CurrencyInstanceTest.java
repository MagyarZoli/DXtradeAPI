package org.example.models.conversion;

import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static java.util.Currency.getInstance;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CurrencyInstanceTest {

  private final String[] currencyCode = {
      "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
      "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL",
      "BSD", "BTN", "BWP", "BYN", "BYR", "BZD", "CAD", "CDF", "CHF", "CLP",
      "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP",
      "DZD", "EEK", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL",
      "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG",
      "HUF", "IDR", "ILS", "IQD", "IRR", "ISK", "JMD", "JOD", "JOD", "JPY",
      "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK",
      "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA",
      "MKD", "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR",
      "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN",
      "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF",
      "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS",
      "SRD", "STN", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP",
      "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF",
      "VES", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD", "XDR", "XOF",
      "XPD", "XPF", "XPT", "YER", "ZAR", "ZMK", "ZMW", "ZWD", "ZWL"
  };

  @Test
  void testEnum() {
    assertDoesNotThrow(CurrencyInstance.USD::name);
  }

  @Test
  void testCurrency() {
    List<Currency> currencyList = new ArrayList<>();
    for (String code : currencyCode) {
      currencyList.add(getInstance(code));
    }

    for (Currency currency : currencyList) {
      assertDoesNotThrow(currency::toString);
    }
  }

  @Test
  void testCurrencyUnit() {
    List<CurrencyUnit> currencyUnits = new ArrayList<>();
    for (String code : currencyCode) {
      currencyUnits.add(Monetary.getCurrency(code));
    }

    for (CurrencyUnit currencyUnit : currencyUnits) {
      assertDoesNotThrow(currencyUnit::toString);
    }
  }
}