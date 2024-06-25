package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpGet;
import org.example.auxiliary.JSONParser;
import org.example.auxiliary.MonetaryConversion;
import org.example.connection.Connection;
import org.example.exception.APIException;
import org.example.models.conversion.ConversionRateResponse;
import org.example.models.conversion.CurrencyInstance;
import org.example.models.symbol.SymbolForexPair;
import org.example.transfer.SessionToken;
import org.example.transfer.conversion.CurrencyPair;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.money.convert.CurrencyConversionException;

@Data
public class Conversion {

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private final Integer TRY_AGAN = 4;

  @NonNull
  private Connection connection;

  @NonNull
  private SessionToken sessionToken;

  @Setter(value = AccessLevel.PRIVATE)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private JSONObject jsonResponse;

  public Conversion(@NonNull Connection connection, @NonNull SessionToken sessionToken) {
    this.connection = connection;
    setSessionToken(sessionToken);
  }

  public void setSessionToken(SessionToken sessionToken) {
    if (sessionToken == null) {
      throw new APIException("sessionToken is null");
    }
    this.sessionToken = sessionToken;
  }

  public JSONObject getConversionJSONObject(@NonNull String endpoint) {
    jsonResponse = connection.authConnection(endpoint, new HttpGet(), sessionToken);
    return jsonResponse;
  }

  public ConversionRateResponse getConversion(@NonNull String endpoint) {
    jsonResponse = getConversionJSONObject(endpoint);
    return JSONParser.jsonObjectParserToClass(jsonResponse, ConversionRateResponse.class);
  }

  public JSONObject getConversionRatesJSONObject(@NonNull CurrencyInstance fromCurrency, @NonNull CurrencyInstance toCurrency) {
    String endpoint = "conversionRates" +
      "?fromCurrency=" + fromCurrency +
      "&toCurrency=" + toCurrency;
    return getConversionJSONObject(endpoint);
  }

  public JSONObject getConversionRatesJSONObject(@NonNull CurrencyPair currencyPair) {
    return getConversionRatesJSONObject(currencyPair.getFromCurrency(), currencyPair.getToCurrency());
  }

  public JSONObject getConversionRatesJSONObject(@NonNull SymbolForexPair symbol) {
    return getConversionRatesJSONObject(symbol.getFromCurrency(), symbol.getToCurrency());
  }

  public ConversionRateResponse getConversionRates(@NonNull CurrencyInstance fromCurrency, @NonNull CurrencyInstance toCurrency) {
    JSONObject jsonResponse = getConversionRatesJSONObject(fromCurrency, toCurrency);
    return JSONParser.jsonObjectParserToClass(jsonResponse, ConversionRateResponse.class);
  }

  public ConversionRateResponse getConversionRates(@NonNull CurrencyPair currencyPair) {
    return getConversionRates(currencyPair.getFromCurrency(), currencyPair.getToCurrency());
  }

  public ConversionRateResponse getConversionRates(@NonNull SymbolForexPair symbol) {
    return getConversionRates(symbol.getFromCurrency(), symbol.getToCurrency());
  }

  private JSONObject convRateCheck(@NonNull JSONObject jsonObject, @NonNull String endpoint) {
    try {
      JSONArray conversionRatesArray = (JSONArray) jsonObject.get("conversionRates");
      JSONObject conversionRate = conversionRatesArray.getJSONObject(0);
      conversionRate.getNumber("convRate");
      return jsonObject;
    } catch (RuntimeException e) {
      JSONObject convRateCorrection = convRateCorrection(endpoint, TRY_AGAN);
      return new JSONObject().put("conversionRates", new JSONArray().put(convRateCorrection));
    }
  }

  private JSONObject convRateCorrection(@NonNull String endpoint, Integer tryAgan) {
    try {
      String fromCurrencyStr = endpoint.substring(endpoint.indexOf('=') + 1, endpoint.indexOf('&'));
      String toCurrencyStr = endpoint.substring(endpoint.lastIndexOf('=') + 1);
      Number convRate = MonetaryConversion.convRate(fromCurrencyStr, toCurrencyStr);
      return new JSONObject()
        .put("toCurrency", toCurrencyStr)
        .put("convRate", convRate)
        .put("fromCurrency", fromCurrencyStr);
    } catch (CurrencyConversionException e) {
      if (tryAgan != 0) {
        return convRateCorrection(endpoint, --tryAgan);
      }
      throw new APIException(e);
    }
  }
}
