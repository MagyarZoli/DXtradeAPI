package org.example.controller.conversion;

import org.example.controller.Conversion;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(value = MockitoExtension.class)
public class ConversionMockReflectionTest
extends ConversionMockTest {

  Method method;
  JSONObject result;

  @RepeatedTest(value = 3)
  void testConvRateCorrectionReflection() {
    Conversion conversion = new Conversion(connectionMock, SESSION_TOKEN_FAKE);
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency);
    Class<?>[] parameters = new Class[] {String.class, Integer.class};

    assertThatCode(() -> method = conversion.getClass().getDeclaredMethod("convRateCorrection", parameters)).doesNotThrowAnyException();

    method.setAccessible(true);
    Object[] methodArgument = new Object[] {endpoint, 4};

    assertThatCode(() -> result = (JSONObject) method.invoke(conversion, methodArgument)).doesNotThrowAnyException();
    assertThat(result).isNotNull();
    assertThat(result.getString("toCurrency"))
        .isNotNull()
        .isEqualTo(actual.getString("toCurrency"));
    assertThat(result.getString("fromCurrency"))
        .isNotNull()
        .isEqualTo(actual.getString("fromCurrency"));
  }

  @RepeatedTest(value = 3)
  void testConvRateCheckReflection() {
    Conversion conversion = new Conversion(connectionMock, SESSION_TOKEN_FAKE);
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject().put("conversionRates", new JSONArray().put(new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency)
    ));
    Class<?>[] parameters = new Class[] {JSONObject.class, String.class};

    assertThatCode(() -> method = conversion.getClass().getDeclaredMethod("convRateCheck", parameters)).doesNotThrowAnyException();

    method.setAccessible(true);
    Object[] methodArgument = new Object[] {actual, endpoint};

    assertThatCode(() -> result = (JSONObject) method.invoke(conversion, methodArgument)).doesNotThrowAnyException();
    assertThat(result).isNotNull();

    JSONObject resultRates = result.getJSONArray("conversionRates").getJSONObject(0);
    JSONObject actualRates = actual.getJSONArray("conversionRates").getJSONObject(0);

    assertThat(resultRates.getString("toCurrency"))
        .isNotNull()
        .isEqualTo(actualRates.getString("toCurrency"));
    assertThat(resultRates.getString("fromCurrency"))
        .isNotNull()
        .isEqualTo(actualRates.getString("fromCurrency"));
  }

  @RepeatedTest(value = 3)
  void testConvRateCorrectionPowermock() {
    String fromCurrency = "EUR";
    String toCurrency = "USD";
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + toCurrency;
    JSONObject actual = new JSONObject()
        .put("toCurrency", toCurrency)
        .put("fromCurrency", fromCurrency);

    assertThatCode(() -> result = Whitebox.invokeMethod(conversionSpy, "convRateCorrection", endpoint, 4)).doesNotThrowAnyException();
    assertThat(result).isNotNull();
    assertThat(result.getString("toCurrency"))
        .isNotNull()
        .isEqualTo(actual.getString("toCurrency"));
    assertThat(result.getString("fromCurrency"))
        .isNotNull()
        .isEqualTo(actual.getString("fromCurrency"));
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

    assertThatCode(() -> result = Whitebox.invokeMethod(conversionSpy, "convRateCheck", actual, endpoint)).doesNotThrowAnyException();
    assertThat(result).isNotNull();

    JSONObject resultRates = result.getJSONArray("conversionRates").getJSONObject(0);
    JSONObject actualRates = actual.getJSONArray("conversionRates").getJSONObject(0);

    assertThat(resultRates.getString("toCurrency")).isEqualTo(actualRates.getString("toCurrency"));
    assertThat(resultRates.getString("fromCurrency")).isEqualTo(actualRates.getString("fromCurrency"));
  }
}
