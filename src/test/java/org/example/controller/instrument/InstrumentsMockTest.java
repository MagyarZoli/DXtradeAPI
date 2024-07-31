package org.example.controller.instrument;

import org.apache.http.client.methods.HttpRequestBase;
import org.example.auxiliary.InvokeMethod;
import org.example.connection.Connection;
import org.example.controller.Instruments;
import org.example.models.ModelAssert;
import org.example.models.instruments.*;
import org.example.models.symbol.*;
import org.example.transfer.AccountCode;
import org.example.transfer.JSONObjectAssert;
import org.example.transfer.SessionToken;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class InstrumentsMockTest {

  Connection connectionMock;

  static Instruments instrumentsSpy;
  static AccountCode accountCode;

  final String usernameFake = "usernameFake";
  final TradingStatus tradingStatus = TradingStatus.FULL;
  final Number minOrderSize = 1, limitValue = 2, minOrderSizeIncrement = 3, endVolume = 4, tierValue = 5,
    minRate = 6, maxRate = 7, defaultValue = 8, rateIncrement = 9;
  final LimitType limitType = LimitType.LOTS;
  final LimitValue naxOrderSize = new LimitValue(limitValue, limitType);
  final RateType rateType = RateType.FLAT;
  final MarginTier tier = new MarginTier(endVolume, tierValue);
  final List<MarginTier> tiers = List.of(tier);
  final TraderSelectedMarginRates availableMarginRates = new TraderSelectedMarginRates(minRate, maxRate, defaultValue, rateIncrement);
  final MarginRate marginRate = new MarginRate(rateType, tiers, availableMarginRates);

  static final String TOKEN = "123456789";
  static final String TIMEOUT = "00:30:00";
  static final SessionToken SESSION_TOKEN_FAKE = new SessionToken(TOKEN, TIMEOUT);
  static final JSONObject ERROR_RESPONSE_FAKE = new JSONObject()
      .put("errorCode", "1")
      .put("description", "description message!");
  static final List<Symbol> SYMBOL_LIST = InvokeMethod.args(
      SymbolCurrency.values(),
      SymbolForex.values(),
      SymbolCFD.values(),
      SymbolCFDStock.values()
  );
  static final InstrumentType TYPE = InstrumentType.FOREX;
  static final Symbol SYMBOL = SymbolForex.EURUSD;

  @BeforeEach
  void setUp() {
    connectionMock = mock(Connection.class);
    instrumentsSpy = spy(new Instruments(connectionMock, SESSION_TOKEN_FAKE));
    accountCode = new AccountCode(usernameFake);
  }

  static Stream<Symbol> symbolStream() {
    return SYMBOL_LIST.stream();
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> getAccountInstrumentsQueryJSONObjectInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getAccountInstrumentsQueryJSONObject(accountCode),
        () -> instrumentsSpy.getAccountInstrumentsQueryJSONObject(accountCode, SYMBOL),
        () -> instrumentsSpy.getAccountInstrumentsQueryJSONObject(accountCode, TYPE),
        () -> instrumentsSpy.getAccountInstrumentsQueryJSONObject(accountCode, SYMBOL, TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<InstrumentDetails>> getAccountInstrumentsQueryInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getAccountInstrumentsQuery(accountCode),
        () -> instrumentsSpy.getAccountInstrumentsQuery(accountCode, SYMBOL),
        () -> instrumentsSpy.getAccountInstrumentsQuery(accountCode, TYPE),
        () -> instrumentsSpy.getAccountInstrumentsQuery(accountCode, SYMBOL, TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> getInstrumentsQueryJSONObjectInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getInstrumentsQueryJSONObject(),
        () -> instrumentsSpy.getInstrumentsQueryJSONObject(SYMBOL),
        () -> instrumentsSpy.getInstrumentsQueryJSONObject(TYPE),
        () -> instrumentsSpy.getInstrumentsQueryJSONObject(SYMBOL, TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<InstrumentsModel>> getInstrumentsQueryInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getInstrumentsQuery(),
        () -> instrumentsSpy.getInstrumentsQuery(SYMBOL),
        () -> instrumentsSpy.getInstrumentsQuery(TYPE),
        () -> instrumentsSpy.getInstrumentsQuery(SYMBOL, TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> getAccountIInstrumentsSymbolJSONObjectInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getAccountInstrumentsSymbolJSONObject(accountCode, SYMBOL),
        () -> instrumentsSpy.getAccountInstrumentsTypeJSONObject(accountCode, TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<InstrumentsModel>> getAccountInstrumentsSymbolInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getAccountInstrumentsSymbol(accountCode, SYMBOL),
        () -> instrumentsSpy.getAccountInstrumentsType(accountCode, TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> getInstrumentsSymbolJSONObjectInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getInstrumentsSymbolJSONObject(SYMBOL),
        () -> instrumentsSpy.getInstrumentsTypeJSONObject(TYPE)
    );
  }

  static Stream<InvokeMethod.Invoke<InstrumentsModel>> getInstrumentsSymbolInvoke() {
    return Stream.of(
        () -> instrumentsSpy.getInstrumentsSymbol(SYMBOL),
        () -> instrumentsSpy.getInstrumentsType(TYPE)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getAccountInstrumentsQueryJSONObjectInvoke")
  void testAccountInstrumentsQueryJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    JSONObject responseFake = new InstrumentDetails(List.of(
        new InstrumentDetail(accountCode, TYPE, SYMBOL, tradingStatus, minOrderSize, naxOrderSize, minOrderSizeIncrement, marginRate)
    )).parseToJSONObject();

    assertThat(responseFake).isNotNull();
    when(connectionMock.authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(responseFake);

    JSONObject jsonObject = method.methodInvoke();

    verify(connectionMock).authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class));
    verify(instrumentsSpy).getInstrumentsJSONObject(any(String.class));

    JSONObjectAssert.assertThat(jsonObject).hasValueIsArray("instruments");
    JSONObjectAssert.assertThat(jsonObject.getJSONArray("instruments").getJSONObject(0))
        .hasValueIsEqualTo("account", accountCode.getAccount())
        .hasValueIsEqualTo("instrumentType", TYPE)
        .hasValueIsEqualTo("symbol", SYMBOL.name()) //java.lang.AssertionError: Expected value: EURUSD, but was: ["SymbolForex","EURUSD"]
        .hasValueIsEqualTo("minOrderSize", minOrderSize)
        .hasValueIsEqualTo("maxOrderSize", naxOrderSize.parseToJSONObject())
        .hasValueIsEqualTo("minOrderSizeIncrement", minOrderSizeIncrement)
        .hasValueIsEqualTo("marginRate", marginRate.parseToJSONObject());
  }

  @ParameterizedTest
  @MethodSource(value = "getAccountInstrumentsQueryInvoke")
  void testAccountInstrumentsQuery(InvokeMethod.Invoke<InstrumentDetails> method) {
    JSONObject responseFake = new InstrumentDetails(List.of(
        new InstrumentDetail(accountCode, TYPE, SYMBOL, tradingStatus, minOrderSize, naxOrderSize, minOrderSizeIncrement, marginRate)
    )).parseToJSONObject();

    assertThat(responseFake).isNotNull();
    when(connectionMock.authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(responseFake);

    InstrumentDetails instrumentDetails = method.methodInvoke();

    verify(connectionMock).authConnection(any(String.class), any(HttpRequestBase.class), any(SessionToken.class));
    verify(instrumentsSpy).getInstrumentsJSONObject(any(String.class));

    ModelAssert.assertThat((InstrumentDetail) instrumentDetails.getInstruments().get(0))
        //.hasValueIsEqualTo("account", accountCode.getAccount())
        .hasValueIsEqualTo("instrumentType", TYPE)
        .hasValueIsEqualTo("symbol", SYMBOL)
        .hasValueIsEqualTo("minOrderSize", minOrderSize)
        .hasValueIsEqualTo("maxOrderSize", naxOrderSize.parseToJSONObject())
        .hasValueIsEqualTo("minOrderSizeIncrement", minOrderSizeIncrement)
        .hasValueIsEqualTo("marginRate", marginRate.parseToJSONObject());
  }
}
