package org.example.models.conversion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Currency;

import static java.util.Currency.getInstance;

@AllArgsConstructor
@Getter
public enum CurrencyInstance {

  AUD(getInstance("AUD"), Monetary.getCurrency("AUD")),
  CAD(getInstance("CAD"), Monetary.getCurrency("CAD")),
  CZK(getInstance("CZK"), Monetary.getCurrency("CZK")),
  EUR(getInstance("EUR"), Monetary.getCurrency("EUR")),
  GBP(getInstance("GBP"), Monetary.getCurrency("GBP")),
  HKD(getInstance("HKD"), Monetary.getCurrency("HKD")),
  HUF(getInstance("HUF"), Monetary.getCurrency("HUF")),
  ILS(getInstance("ILS"), Monetary.getCurrency("ILS")),
  JPY(getInstance("JPY"), Monetary.getCurrency("JPY")),
  MXN(getInstance("MXN"), Monetary.getCurrency("MXN")),
  NOK(getInstance("NOK"), Monetary.getCurrency("NOK")),
  NZD(getInstance("NZD"), Monetary.getCurrency("NZD")),
  PLN(getInstance("PLN"), Monetary.getCurrency("PLN")),
  RUB(getInstance("RUB"), Monetary.getCurrency("RUB")),
  SEK(getInstance("SEK"), Monetary.getCurrency("SEK")),
  TRY(getInstance("TRY"), Monetary.getCurrency("TRY")),
  USD(getInstance("USD"), Monetary.getCurrency("USD")),
  ZAR(getInstance("ZAR"), Monetary.getCurrency("ZAR")),

  BGN(getInstance("BGN"), Monetary.getCurrency("BGN")),
  BRL(getInstance("BRL"), Monetary.getCurrency("BRL")),
  CNY(getInstance("CNY"), Monetary.getCurrency("CNY")),
  DKK(getInstance("DKK"), Monetary.getCurrency("DKK")),
  HRK(getInstance("HRK"), Monetary.getCurrency("HRK")),
  IDR(getInstance("IDR"), Monetary.getCurrency("IDR")),
  KRW(getInstance("KRW"), Monetary.getCurrency("KRW")),
  MYR(getInstance("MYR"), Monetary.getCurrency("MYR")),
  PHP(getInstance("PHP"), Monetary.getCurrency("PHP")),
  RON(getInstance("RON"), Monetary.getCurrency("RON")),
  SGD(getInstance("SGD"), Monetary.getCurrency("SGD")),
  THB(getInstance("THB"), Monetary.getCurrency("THB"));

  private final Currency currency;
  private final CurrencyUnit currencyUnit;
}
