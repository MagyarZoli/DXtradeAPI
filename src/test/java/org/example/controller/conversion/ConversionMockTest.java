package org.example.controller.conversion;

import org.apache.http.client.methods.HttpRequestBase;
import org.example.auxiliary.InvokeMethod;
import org.example.connection.Connection;
import org.example.controller.Conversion;
import org.example.models.Model;
import org.example.models.ModelAssert;
import org.example.models.conversion.ConversionRate;
import org.example.models.conversion.ConversionRateResponse;
import org.example.models.conversion.CurrencyInstance;
import org.example.models.symbol.SymbolForexPair;
import org.example.transfer.JSONObjectAssert;
import org.example.transfer.SessionToken;
import org.example.transfer.conversion.CurrencyPair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class ConversionMockTest {

  Connection connectionMock;
  Number convRate = null;

  static Conversion conversionSpy;

  static final String TOKEN = "123456789";
  static final String TIMEOUT = "00:30:00";
  static final SessionToken SESSION_TOKEN_FAKE = new SessionToken(TOKEN, TIMEOUT);
  static final JSONObject ERROR_RESPONSE_FAKE = new JSONObject()
      .put("errorCode", "1")
      .put("description", "description message!");

  @BeforeEach
  void setUp() {
    connectionMock = mock(Connection.class);
    conversionSpy = spy(new Conversion(connectionMock, SESSION_TOKEN_FAKE));
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> conversionRatesJSONObjectInvoke() {
    return Stream.of(
        () -> conversionSpy.getConversionRatesJSONObject(CurrencyInstance.EUR, CurrencyInstance.USD),
        () -> conversionSpy.getConversionRatesJSONObject(new CurrencyPair(CurrencyInstance.EUR, CurrencyInstance.USD)),
        () -> conversionSpy.getConversionRatesJSONObject(SymbolForexPair.EURUSD)
    );
  }

  static Stream<InvokeMethod.Invoke<ConversionRateResponse>> conversionRatesInvoke() {
    return Stream.of(
        () -> conversionSpy.getConversionRates(CurrencyInstance.EUR, CurrencyInstance.USD),
        () -> conversionSpy.getConversionRates(new CurrencyPair(CurrencyInstance.EUR, CurrencyInstance.USD)),
        () -> conversionSpy.getConversionRates(SymbolForexPair.EURUSD)
    );
  }

  static Stream<InvokeMethod.InvokeRawType> conversionRatesAllInvoke() {
    return Stream.of(
        () -> conversionSpy.getConversionRatesJSONObject(CurrencyInstance.EUR, CurrencyInstance.USD),
        () -> conversionSpy.getConversionRatesJSONObject(new CurrencyPair(CurrencyInstance.EUR, CurrencyInstance.USD)),
        () -> conversionSpy.getConversionRatesJSONObject(SymbolForexPair.EURUSD),
        () -> conversionSpy.getConversionRates(CurrencyInstance.EUR, CurrencyInstance.USD),
        () -> conversionSpy.getConversionRates(new CurrencyPair(CurrencyInstance.EUR, CurrencyInstance.USD)),
        () -> conversionSpy.getConversionRates(SymbolForexPair.EURUSD)
    );
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionJSONObjectFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", fromCurrency)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    JSONObject conversionRates = conversionSpy.getConversionJSONObject(endpoint);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionJSONObject(eq(endpoint));
    assertThat(conversionRates).isNotNull();
    JSONObjectAssert.assertThat(conversionRates).hasValueIsArray("conversionRates");

    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getNumber("convRate")).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    JSONObjectAssert.assertThat(conversionRate)
        .hasValueIsEqualTo("fromCurrency", fromCurrency)
        .hasValueIsEqualTo("toCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionJSONObjectToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.USD)
            .put("convRate", 123.45)
            .put("toCurrency", toCurrency)
        ))
    );

    JSONObject conversionRates = conversionSpy.getConversionJSONObject(endpoint);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionJSONObject(eq(endpoint));
    assertThat(conversionRates).isNotNull();
    JSONObjectAssert.assertThat(conversionRates).hasValueIsArray("conversionRates");

    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getNumber("convRate")).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    JSONObjectAssert.assertThat(conversionRate)
        .hasValueIsEqualTo("fromCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("toCurrency", toCurrency)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", fromCurrency)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    ConversionRateResponse conversionRates = conversionSpy.getConversion(endpoint);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversion(eq(endpoint));
    verify(conversionSpy).getConversionJSONObject(eq(endpoint));
    assertThat(conversionRates).isNotNull();

    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);
    convRate = conversionRate.getConvRate();

    assertThat(conversionRate).isNotNull();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    ModelAssert.assertThat((Model) conversionRate)
        .hasValueIsEqualTo("fromCurrency", fromCurrency)
        .hasValueIsEqualTo("toCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.USD)
            .put("convRate", 123.45)
            .put("toCurrency", toCurrency)
        ))
    );

    ConversionRateResponse conversionRates = conversionSpy.getConversion(endpoint);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversion(eq(endpoint));
    verify(conversionSpy).getConversionJSONObject(eq(endpoint));
    assertThat(conversionRates).isNotNull();

    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);
    convRate = conversionRate.getConvRate();

    assertThat(conversionRate).isNotNull();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    ModelAssert.assertThat((Model) conversionRate)
        .hasValueIsEqualTo("fromCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("toCurrency", toCurrency)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesJSONObjectFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", fromCurrency)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    JSONObject conversionRates = conversionSpy.getConversionRatesJSONObject(fromCurrency, CurrencyInstance.USD);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRatesJSONObject(eq(fromCurrency), any(CurrencyInstance.class));
    assertThat(conversionRates).isNotNull();
    JSONObjectAssert.assertThat(conversionRates).hasValueIsArray("conversionRates");

    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getNumber("convRate")).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    JSONObjectAssert.assertThat(conversionRate)
        .hasValueIsEqualTo("fromCurrency", fromCurrency)
        .hasValueIsEqualTo("toCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesJSONObjectToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" +  CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.USD)
            .put("convRate", 123.45)
            .put("toCurrency", toCurrency)
        ))
    );

    JSONObject conversionRates = conversionSpy.getConversionRatesJSONObject(CurrencyInstance.USD, toCurrency);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRatesJSONObject(any(CurrencyInstance.class), eq(toCurrency));
    assertThat(conversionRates).isNotNull();
    JSONObjectAssert.assertThat(conversionRates).hasValueIsArray("conversionRates");

    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getNumber("convRate")).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    JSONObjectAssert.assertThat(conversionRate)
        .hasValueIsEqualTo("fromCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("toCurrency", toCurrency)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", fromCurrency)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    ConversionRateResponse conversionRates = conversionSpy.getConversionRates(fromCurrency, CurrencyInstance.USD);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRates(eq(fromCurrency), any(CurrencyInstance.class));
    verify(conversionSpy).getConversionRatesJSONObject(eq(fromCurrency), any(CurrencyInstance.class));
    assertThat(conversionRates).isNotNull();

    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getConvRate()).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    ModelAssert.assertThat((Model) conversionRate)
        .hasValueIsEqualTo("fromCurrency", fromCurrency)
        .hasValueIsEqualTo("toCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testGetConversionRatesToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.USD)
            .put("convRate", 123.45)
            .put("toCurrency", toCurrency)
        ))
    );

    ConversionRateResponse conversionRates = conversionSpy.getConversionRates(CurrencyInstance.USD, toCurrency);

    verify(connectionMock).authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRates(any(CurrencyInstance.class),eq(toCurrency));
    verify(conversionSpy).getConversionRatesJSONObject(any(CurrencyInstance.class), eq(toCurrency));
    assertThat(conversionRates).isNotNull();

    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getConvRate()).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    ModelAssert.assertThat((Model) conversionRate)
        .hasValueIsEqualTo("fromCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("toCurrency", toCurrency)
        .hasValueIsEqualTo("convRate", 123.45);
  }


  @ParameterizedTest
  @MethodSource(value = "conversionRatesJSONObjectInvoke")
  void testGetConversionRatesJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.EUR)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    JSONObject conversionRates = method.methodInvoke();
    
    verify(connectionMock).authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRatesJSONObject(any(CurrencyInstance.class), any(CurrencyInstance.class));
    assertThat(conversionRates).isNotNull();
    JSONObjectAssert.assertThat(conversionRates).hasValueIsArray("conversionRates");

    JSONArray conversionRatesArray = (JSONArray) conversionRates.get("conversionRates");
    JSONObject conversionRate = conversionRatesArray.getJSONObject(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getNumber("convRate")).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    JSONObjectAssert.assertThat(conversionRate)
        .hasValueIsEqualTo("fromCurrency", CurrencyInstance.EUR)
        .hasValueIsEqualTo("toCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @MethodSource(value = "conversionRatesInvoke")
  void testGetConversionRates(InvokeMethod.Invoke<ConversionRateResponse> method) {
    when(connectionMock.authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.EUR)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    ConversionRateResponse conversionRates = method.methodInvoke();

    verify(connectionMock).authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRates(any(CurrencyInstance.class), any(CurrencyInstance.class));
    verify(conversionSpy).getConversionRatesJSONObject(any(CurrencyInstance.class), any(CurrencyInstance.class));
    assertThat(conversionRates).isNotNull();

    List<ConversionRate> conversionRateList = conversionRates.getConversionRates();
    ConversionRate conversionRate = conversionRateList.get(0);

    assertThat(conversionRate).isNotNull();
    assertThatCode(() -> convRate = conversionRate.getConvRate()).doesNotThrowAnyException();
    assertThat(convRate)
        .isNotNull()
        .isEqualTo(123.45);
    ModelAssert.assertThat((Model) conversionRate)
        .hasValueIsEqualTo("fromCurrency", CurrencyInstance.EUR)
        .hasValueIsEqualTo("toCurrency", CurrencyInstance.USD)
        .hasValueIsEqualTo("convRate", 123.45);
  }

  @ParameterizedTest
  @MethodSource(value = "conversionRatesAllInvoke")
  void testGetConversionRatesAll(InvokeMethod.InvokeRawType method) {
    when(connectionMock.authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.EUR)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    var conversionRates = method.methodInvoke();

    verify(connectionMock).authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class));
    verify(conversionSpy).getConversionRatesJSONObject(any(CurrencyInstance.class), any(CurrencyInstance.class));
    assertThat(conversionRates).isNotNull();
  }
}
