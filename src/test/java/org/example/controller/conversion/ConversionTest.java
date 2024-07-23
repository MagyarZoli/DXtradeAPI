package org.example.controller.conversion;

import org.example.controller.AuthConnection;
import org.example.controller.Conversion;
import org.example.models.conversion.ConversionRate;
import org.example.models.conversion.ConversionRateResponse;
import org.example.models.conversion.CurrencyInstance;
import org.example.transfer.SessionToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConversionTest
extends AuthConnection {

  private static Conversion conversion;

  private JSONObject result;
  private Method methodCall;

  @BeforeAll
  public static void beforeAll() {
    AuthConnection.beforeAll();
    conversion = new Conversion(connection, auth.getSessionToken());
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionJSONObjectFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;
    JSONObject conversionRates = conversion.getConversionJSONObject(endpoint);
    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);
    Number convRate = null;
    try {
      convRate = conversionRate.getNumber("convRate");
    } catch (Exception e) {
      fail(e);
    }

    System.out.println(conversionRates);
    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionJSONObjectToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;
    JSONObject conversionRates = conversion.getConversionJSONObject(endpoint);
    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);
    Number convRate = null;
    try {
      convRate = conversionRate.getNumber("convRate");
    } catch (Exception e) {
      fail(e);
    }

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;
    ConversionRateResponse conversionRates = conversion.getConversion(endpoint);
    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);
    Number convRate = conversionRate.getConvRate();

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;
    ConversionRateResponse conversionRates = conversion.getConversion(endpoint);
    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);
    Number convRate = conversionRate.getConvRate();

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesJSONObjectFromCurrency(CurrencyInstance fromCurrency) {
    JSONObject conversionRates = conversion.getConversionRatesJSONObject(fromCurrency, CurrencyInstance.USD);
    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);
    Number convRate = null;
    try {
      convRate = conversionRate.getNumber("convRate");
    } catch (Exception e) {
      fail(e);
    }

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesJSONObjectToCurrency(CurrencyInstance toCurrency) {
    JSONObject conversionRates = conversion.getConversionRatesJSONObject(CurrencyInstance.USD, toCurrency);
    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);
    Number convRate = null;
    try {
      convRate = conversionRate.getNumber("convRate");
    } catch (Exception e) {
      fail(e);
    }

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesFromCurrency(CurrencyInstance fromCurrency) {
    ConversionRateResponse conversionRates = conversion.getConversionRates(fromCurrency, CurrencyInstance.USD);
    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);
    Number convRate = conversionRate.getConvRate();

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesToCurrency(CurrencyInstance toCurrency) {
    ConversionRateResponse conversionRates = conversion.getConversionRates(CurrencyInstance.USD, toCurrency);
    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);
    Number convRate = conversionRate.getConvRate();

    assertNotNull(conversionRates);
    assertNotNull(convRate);
  }

  @RepeatedTest(value = 3)
  void testConvRateCorrectionReflection() {
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency);
    Class<?>[] parameters = new Class[]{String.class, Integer.class};

    assertDoesNotThrow(() -> methodCall = conversion.getClass().getDeclaredMethod("convRateCorrection", parameters));

    methodCall.setAccessible(true);
    Object[] methodArgument = new Object[]{endpoint, 4};

    assertDoesNotThrow(() -> result = (JSONObject) methodCall.invoke(conversion, methodArgument));
    assertNotNull(result);
    assertEquals(result.getString("toCurrency"), actual.getString("toCurrency"));
    assertEquals(result.getString("fromCurrency"), actual.getString("fromCurrency"));
  }

  @RepeatedTest(value = 3)
  void testConvRateCheckReflection() {
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject().put("conversionRates", new JSONArray().put(new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency)
    ));
    Class<?>[] parameters = new Class[]{JSONObject.class, String.class};

    assertDoesNotThrow(() -> methodCall = conversion.getClass().getDeclaredMethod("convRateCheck", parameters));

    methodCall.setAccessible(true);
    Object[] methodArgument = new Object[]{actual, endpoint};

    assertDoesNotThrow(() -> result = (JSONObject) methodCall.invoke(conversion, methodArgument));
    assertNotNull(result);

    JSONObject resultRates = result.getJSONArray("conversionRates").getJSONObject(0);
    JSONObject actualRates = actual.getJSONArray("conversionRates").getJSONObject(0);

    assertEquals(resultRates.getString("toCurrency"), actualRates.getString("toCurrency"));
    assertEquals(resultRates.getString("fromCurrency"), actualRates.getString("fromCurrency"));
  }

  @RepeatedTest(value = 3)
  void testConvRateCorrectionPowermock() {
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency);

    assertDoesNotThrow(() -> result = Whitebox.invokeMethod(conversion, "convRateCorrection", endpoint, 4));
    assertNotNull(result);
    assertEquals(result.getString("toCurrency"), actual.getString("toCurrency"));
    assertEquals(result.getString("fromCurrency"), actual.getString("fromCurrency"));
  }

  @RepeatedTest(value = 3)
  void testConvRateCheckPowermock() {
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject().put("conversionRates", new JSONArray().put(new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency)
    ));

    assertDoesNotThrow(() -> result = Whitebox.invokeMethod(conversion, "convRateCheck", actual, endpoint));
    assertNotNull(result);

    JSONObject resultRates = result.getJSONArray("conversionRates").getJSONObject(0);
    JSONObject actualRates = actual.getJSONArray("conversionRates").getJSONObject(0);

    assertEquals(resultRates.getString("toCurrency"), actualRates.getString("toCurrency"));
    assertEquals(resultRates.getString("fromCurrency"), actualRates.getString("fromCurrency"));
  }

  ///EXCEPTION_TEST

  private final SessionToken wrongSessionToken = new SessionToken("wrong", "00:30:00");

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionJSONObjectFromCurrency(CurrencyInstance fromCurrency)  {
    conversion = new Conversion(connection, wrongSessionToken);
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    assertThrows(RuntimeException.class, () -> conversion.getConversionJSONObject(endpoint));
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionJSONObjectToCurrency(CurrencyInstance toCurrency)  {
    conversion = new Conversion(connection, wrongSessionToken);
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    assertThrows(RuntimeException.class, () -> conversion.getConversionJSONObject(endpoint));
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionFromCurrency(CurrencyInstance fromCurrency) {
    conversion = new Conversion(connection, wrongSessionToken);
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    assertThrows(RuntimeException.class, () -> conversion.getConversion(endpoint));
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionToCurrency(CurrencyInstance toCurrency) {
    conversion = new Conversion(connection, wrongSessionToken);
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    assertThrows(RuntimeException.class, () -> conversion.getConversion(endpoint));
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionRatesJSONObjectFromCurrency(CurrencyInstance fromCurrency) {
    conversion = new Conversion(connection, wrongSessionToken);

    assertThrows(RuntimeException.class, () -> conversion.getConversionRatesJSONObject(fromCurrency, CurrencyInstance.USD));
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionRatesJSONObjectToCurrency(CurrencyInstance toCurrency) {
    conversion = new Conversion(connection, wrongSessionToken);

    assertThrows(RuntimeException.class, () -> conversion.getConversionRatesJSONObject(CurrencyInstance.USD, toCurrency));
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionRatesFromCurrency(CurrencyInstance fromCurrency) {
    conversion = new Conversion(connection, wrongSessionToken);

    assertThrows(RuntimeException.class, () -> conversion.getConversionRates(fromCurrency, CurrencyInstance.USD));
  }


  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testExceptionGetConversionRatesToCurrency(CurrencyInstance toCurrency) {
    conversion = new Conversion(connection, wrongSessionToken);

    assertThrows(RuntimeException.class, () -> conversion.getConversionRates(CurrencyInstance.USD, toCurrency));
  }
}