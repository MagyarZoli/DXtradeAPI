package org.example.auxiliary;

import org.example.models.conversion.CurrencyInstance;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.CurrencyConversionException;
import javax.money.convert.MonetaryConversions;

public class MonetaryConversion {

  public static Number convRate(String fromCurrency, String toCurrency) throws CurrencyConversionException {
    MonetaryAmount oneFromCurrency = Monetary.getDefaultAmountFactory()
        .setCurrency(fromCurrency)
        .setNumber(1)
        .create();
    CurrencyConversion conversionToCurrency = MonetaryConversions.getConversion(toCurrency);
    MonetaryAmount convRate = oneFromCurrency.with(conversionToCurrency);
    return convRate.getNumber();
  }

  public static Number convRate(CurrencyInstance fromCurrency, CurrencyInstance toCurrency) throws CurrencyConversionException {
    return convRate(fromCurrency.name(), toCurrency.name());
  }
}
