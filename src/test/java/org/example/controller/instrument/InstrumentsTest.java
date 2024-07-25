package org.example.controller.instrument;

import org.example.auxiliary.InvokeMethod;
import org.example.controller.AuthConnection;
import org.example.controller.Instruments;
import org.example.models.instruments.InstrumentDetails;
import org.example.models.instruments.InstrumentList;
import org.example.models.instruments.InstrumentType;
import org.example.models.instruments.InstrumentsModel;
import org.example.models.symbol.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InstrumentsTest
extends AuthConnection {

  private static Instruments instruments;
  private static List<Symbol> symbolList;

  @BeforeAll
  public static void beforeAll() {
    AuthConnection.beforeAll();
    instruments = new Instruments(connection, auth.getSessionToken());
    symbolList = InvokeMethod.args(
        SymbolCurrency.values(),
        SymbolForex.values(),
        SymbolCFD.values(),
        SymbolCFDStock.values()
    );
  }

  static Stream<Symbol> symbolStream() {
    return symbolList.stream();
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> methodInvokeJSONObject() {
    return Stream.of(
        () -> instruments.getAccountInstrumentsQueryJSONObject(accountCode),
        () -> instruments.getAccountInstrumentsQueryJSONObject(accountCode, SymbolForex.EURUSD),
        () -> instruments.getAccountInstrumentsQueryJSONObject(accountCode, InstrumentType.FOREX),
        () -> instruments.getAccountInstrumentsQueryJSONObject(accountCode, SymbolForex.EURUSD, InstrumentType.FOREX),
        () -> instruments.getInstrumentsQueryJSONObject(),
        () -> instruments.getInstrumentsQueryJSONObject(SymbolForex.EURUSD),
        () -> instruments.getInstrumentsQueryJSONObject(InstrumentType.FOREX),
        () -> instruments.getInstrumentsQueryJSONObject(SymbolForex.EURUSD, InstrumentType.FOREX),
        () -> instruments.getAccountInstrumentsSymbolJSONObject(accountCode, SymbolForex.EURUSD),
        () -> instruments.getInstrumentsSymbolJSONObject(SymbolForex.EURUSD),
        () -> instruments.getAccountInstrumentsTypeJSONObject(accountCode, InstrumentType.FOREX),
        () -> instruments.getInstrumentsTypeJSONObject(InstrumentType.FOREX)
    );
  }

  static Stream<InvokeMethod.Invoke<InstrumentsModel>> methodInvoke() {
    return Stream.of(
        () -> instruments.getAccountInstrumentsQuery(accountCode),
        () -> instruments.getAccountInstrumentsQuery(accountCode, SymbolForex.EURUSD),
        () -> instruments.getAccountInstrumentsQuery(accountCode, InstrumentType.FOREX),
        () -> instruments.getAccountInstrumentsQuery(accountCode, SymbolForex.EURUSD, InstrumentType.FOREX),
        () -> instruments.getInstrumentsQuery(),
        () -> instruments.getInstrumentsQuery(SymbolForex.EURUSD),
        () -> instruments.getInstrumentsQuery(InstrumentType.FOREX),
        () -> instruments.getInstrumentsQuery(SymbolForex.EURUSD, InstrumentType.FOREX),
        () -> instruments.getAccountInstrumentsSymbol(accountCode, SymbolForex.EURUSD),
        () -> instruments.getInstrumentsSymbol(SymbolForex.EURUSD),
        () -> instruments.getAccountInstrumentsType(accountCode, InstrumentType.FOREX),
        () -> instruments.getInstrumentsType(InstrumentType.FOREX)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "methodInvokeJSONObject")
  void testInstrumentsJSONObjectOverload(InvokeMethod.Invoke<JSONObject> method) {
    JSONObject jsonObject = method.methodInvoke();

    System.out.println(jsonObject);
    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @MethodSource(value = "methodInvoke")
  void testInstrumentsOverload(InvokeMethod.Invoke<InstrumentsModel> method) {
    InstrumentsModel instrumentsModel = method.methodInvoke();
    var instrumentModel = instrumentsModel.getInstruments();

    System.out.println(instrumentModel);
    assertNotNull(instrumentsModel);
    assertNotNull(instrumentModel);
  }

  @ParameterizedTest
  @NullSource
  @MethodSource(value = "symbolStream")
  void testGetInstrumentsJSONObject(Symbol symbol) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(accountCode.getAccount())
        .append("/instruments/query")
        .append("?symbols=")
        .append(symbol);
    JSONObject jsonObject = instruments.getInstrumentsJSONObject(endpoint.toString());
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @NullSource
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetInstrumentsJSONObject(InstrumentType type) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(accountCode.getAccount())
        .append("/instruments/query")
        .append("?type=")
        .append(type);
    JSONObject jsonObject = instruments.getInstrumentsJSONObject(endpoint.toString());
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @NullSource
  @MethodSource(value = "symbolStream")
  void testGetAccountInstrumentsQueryJSONObject(Symbol symbol) {
    JSONObject jsonObject = instruments.getAccountInstrumentsQueryJSONObject(accountCode, symbol);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @NullSource
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetAccountInstrumentsQueryJSONObject(InstrumentType type) {
    JSONObject jsonObject = instruments.getAccountInstrumentsQueryJSONObject(accountCode, type);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @NullSource
  @MethodSource(value = "symbolStream")
  void testGetAccountInstrumentsQuery(Symbol symbol) {
    InstrumentDetails instrumentDetails = instruments.getAccountInstrumentsQuery(accountCode, symbol);
    instrumentDetails.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentDetails);
  }

  @ParameterizedTest
  @NullSource
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetAccountInstrumentsQuery(InstrumentType type) {
    InstrumentDetails instrumentDetails = instruments.getAccountInstrumentsQuery(accountCode, type);
    instrumentDetails.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentDetails);
  }

  @ParameterizedTest
  @NullSource
  @MethodSource(value = "symbolStream")
  void testGetInstrumentsQueryJSONObject(Symbol symbol) {
    JSONObject jsonObject = instruments.getInstrumentsQueryJSONObject(symbol);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @NullSource
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetInstrumentsQueryJSONObject(InstrumentType type) {
    JSONObject jsonObject = instruments.getInstrumentsQueryJSONObject(type);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @NullSource
  @MethodSource(value = "symbolStream")
  void testGetInstrumentsQuery(Symbol symbol) {
    InstrumentList instrumentList = instruments.getInstrumentsQuery(symbol);
    instrumentList.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentList);
  }

  @ParameterizedTest
  @NullSource
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetInstrumentsQuery(InstrumentType type) {
    InstrumentList instrumentList = instruments.getInstrumentsQuery(type);
    instrumentList.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentList);
  }

  @ParameterizedTest
  @MethodSource(value = "symbolStream")
  void testGetAccountInstrumentsSymbolJSONObject(Symbol symbol) {
    JSONObject jsonObject = instruments.getAccountInstrumentsSymbolJSONObject(accountCode, symbol);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @MethodSource(value = "symbolStream")
  void testGetAccountInstrumentsSymbol(Symbol symbol) {
    InstrumentDetails instrumentDetails = instruments.getAccountInstrumentsSymbol(accountCode, symbol);
    instrumentDetails.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentDetails);
  }

  @ParameterizedTest
  @MethodSource(value = "symbolStream")
  void testGetInstrumentsSymbolJSONObject(Symbol symbol) {
    JSONObject jsonObject = instruments.getInstrumentsSymbolJSONObject(symbol);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @MethodSource(value = "symbolStream")
  void testGetInstrumentsSymbol(Symbol symbol) {
    InstrumentList instrumentList = instruments.getInstrumentsSymbol(symbol);
    instrumentList.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentList);
  }

  @ParameterizedTest
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetAccountInstrumentsTypeJSONObject(InstrumentType type) {
    JSONObject jsonObject = instruments.getAccountInstrumentsTypeJSONObject(accountCode, type);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetAccountInstrumentsType(InstrumentType type) {
    InstrumentDetails instrumentDetails = instruments.getAccountInstrumentsType(accountCode, type);
    instrumentDetails.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentDetails);
  }

  @ParameterizedTest
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetInstrumentsTypeJSONObject(InstrumentType type) {
    JSONObject jsonObject = instruments.getInstrumentsTypeJSONObject(type);
    System.out.println(jsonObject);

    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @EnumSource(value = InstrumentType.class, names = {"CURRENCY", "FOREX", "CFD", "CFD_STOCK"})
  void testGetInstrumentsType(InstrumentType type) {
    InstrumentList instrumentList = instruments.getInstrumentsType(type);
    instrumentList.getInstruments().forEach(System.out::println);

    assertNotNull(instrumentList);
  }
}