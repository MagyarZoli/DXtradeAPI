package org.example.controller.conversion;

import org.apache.http.client.methods.HttpRequestBase;
import org.example.models.ModelAssert;
import org.example.models.conversion.CurrencyInstance;
import org.example.transfer.SessionToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class ConversionMockCaptorTest
extends ConversionMockTest {

  private final ArgumentCaptor<String> endpointCaptor = ArgumentCaptor.forClass(String.class);
  private final ArgumentCaptor<HttpRequestBase> httpRequestBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
  private final ArgumentCaptor<SessionToken> sessionTokenCaptor = ArgumentCaptor.forClass(SessionToken.class);

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testCaptorGetConversionJSONObjectFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", fromCurrency)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    conversionSpy.getConversionJSONObject(endpoint);

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    verify(conversionSpy).getConversionJSONObject(endpointCaptor.capture());

    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo(endpoint);
    assertThat(httpRequestBaseCaptor.getValue()).isNotNull();
    ModelAssert.assertThat(sessionTokenCaptor.getValue())
        .hasValueIsEqualTo("sessionToken", TOKEN)
        .hasValueIsEqualTo("timeout", TIMEOUT);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testCaptorGetConversionJSONObjectToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.USD)
            .put("convRate", 123.45)
            .put("toCurrency", toCurrency)
        ))
    );

    conversionSpy.getConversionJSONObject(endpoint);

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    verify(conversionSpy).getConversionJSONObject(endpointCaptor.capture());

    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo(endpoint);
    assertThat(httpRequestBaseCaptor.getValue()).isNotNull();
    ModelAssert.assertThat(sessionTokenCaptor.getValue())
        .hasValueIsEqualTo("sessionToken", TOKEN)
        .hasValueIsEqualTo("timeout", TIMEOUT);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testCaptorGetConversionFromCurrency(CurrencyInstance fromCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + fromCurrency + "&toCurrency=" + CurrencyInstance.USD;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", fromCurrency)
            .put("convRate", 123.45)
            .put("toCurrency", CurrencyInstance.USD)
        ))
    );

    conversionSpy.getConversion(endpoint);

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    verify(conversionSpy).getConversion(endpointCaptor.capture());
    verify(conversionSpy).getConversionJSONObject(endpointCaptor.capture());

    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo(endpoint);
    assertThat(httpRequestBaseCaptor.getValue()).isNotNull();
    ModelAssert.assertThat(sessionTokenCaptor.getValue())
        .hasValueIsEqualTo("sessionToken", TOKEN)
        .hasValueIsEqualTo("timeout", TIMEOUT);
  }

  @ParameterizedTest
  @EnumSource(value = CurrencyInstance.class)
  void testCaptorGetConversionToCurrency(CurrencyInstance toCurrency) {
    String endpoint = "conversionRates?fromCurrency=" + CurrencyInstance.USD + "&toCurrency=" + toCurrency;

    when(connectionMock.authConnection(eq(endpoint), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject()
        .put("conversionRates", new JSONArray().put(new JSONObject()
            .put("fromCurrency", CurrencyInstance.USD)
            .put("convRate", 123.45)
            .put("toCurrency", toCurrency)
        ))
    );

    conversionSpy.getConversion(endpoint);

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    verify(conversionSpy).getConversion(endpointCaptor.capture());
    verify(conversionSpy).getConversionJSONObject(endpointCaptor.capture());

    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo(endpoint);
    assertThat(httpRequestBaseCaptor.getValue()).isNotNull();
    ModelAssert.assertThat(sessionTokenCaptor.getValue())
        .hasValueIsEqualTo("sessionToken", TOKEN)
        .hasValueIsEqualTo("timeout", TIMEOUT);
  }
}
