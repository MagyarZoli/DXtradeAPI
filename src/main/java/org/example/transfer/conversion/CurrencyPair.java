package org.example.transfer.conversion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.example.models.conversion.CurrencyInstance;
import org.example.models.symbol.SymbolForexPair;

@Data
public class CurrencyPair {

  private CurrencyInstance fromCurrency;
  private CurrencyInstance toCurrency;

  @JsonCreator
  public CurrencyPair(
      @NonNull @JsonProperty(value = "fromCurrency") CurrencyInstance fromCurrency,
      @NonNull @JsonProperty(value = "toCurrency") CurrencyInstance toCurrency
  ) {
    this.fromCurrency = fromCurrency;
    this.toCurrency = toCurrency;
  }

  public CurrencyPair(SymbolForexPair symbol) {
    fromCurrency = symbol.getFromCurrency();
    toCurrency = symbol.getToCurrency();
  }

  public void reversCurrency() {
    CurrencyInstance c = fromCurrency;
    fromCurrency = toCurrency;
    toCurrency = c;
  }

  public CurrencyPair reverse() {
    reversCurrency();
    return this;
  }
}
