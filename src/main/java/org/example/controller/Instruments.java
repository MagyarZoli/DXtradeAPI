package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpGet;
import org.example.auxiliary.Check;
import org.example.auxiliary.JSONParser;
import org.example.connection.Connection;
import org.example.models.instruments.InstrumentDetails;
import org.example.models.instruments.InstrumentList;
import org.example.models.instruments.InstrumentType;
import org.example.models.symbol.Symbol;
import org.example.transfer.AccountCode;
import org.example.transfer.SessionToken;
import org.json.JSONObject;

@RequiredArgsConstructor
@Data
public class Instruments {

  @NonNull
  private Connection connection;

  @NonNull
  private SessionToken sessionToken;

  @Setter(value = AccessLevel.PRIVATE)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private JSONObject jsonResponse;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String INSTRUMENT_DETAILS_KEY = "instrumentDetails";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String INSTRUMENTS_KEY = "instruments";

  public JSONObject getInstrumentsJSONObject(@NonNull String endpoint) {
    jsonResponse = connection.authConnection(endpoint, new HttpGet(), sessionToken);
    return jsonResponse;
  }

  public JSONObject getAccountInstrumentsQueryJSONObject(@NonNull AccountCode account) {
    return getAccountInstrumentsQueryJSONObject(account, null, null, null, null);
  }

  public JSONObject getAccountInstrumentsQueryJSONObject(@NonNull AccountCode account, Symbol symbol) {
    return getAccountInstrumentsQueryJSONObject(account, symbol, null, null, null);
  }

  public JSONObject getAccountInstrumentsQueryJSONObject(@NonNull AccountCode account, InstrumentType types) {
    return getAccountInstrumentsQueryJSONObject(account, null, types, null, null);
  }

  public JSONObject getAccountInstrumentsQueryJSONObject(@NonNull AccountCode account, Symbol symbol, InstrumentType types) {
    return getAccountInstrumentsQueryJSONObject(account, symbol, types, null, null);
  }

  public JSONObject getAccountInstrumentsQueryJSONObject(@NonNull AccountCode account, Symbol symbols, InstrumentType types, Integer limit, Integer startFrom) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("/instruments/query");
    Check.endpointQueryType(endpoint, "symbols", symbols);
    Check.endpointQueryType(endpoint, "types", types);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "startFrom", startFrom);
    return getInstrumentsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public InstrumentDetails getAccountInstrumentsQuery(@NonNull AccountCode account) {
    return getAccountInstrumentsQuery(account, null, null, null, null);
  }

  public InstrumentDetails getAccountInstrumentsQuery(@NonNull AccountCode account, Symbol symbol) {
    return getAccountInstrumentsQuery(account, symbol, null, null, null);
  }

  public InstrumentDetails getAccountInstrumentsQuery(@NonNull AccountCode account, InstrumentType type) {
    return getAccountInstrumentsQuery(account, null, type, null, null);
  }

  public InstrumentDetails getAccountInstrumentsQuery(@NonNull AccountCode account, Symbol symbol, InstrumentType type) {
    return getAccountInstrumentsQuery(account, symbol, type, null, null);
  }

  public InstrumentDetails getAccountInstrumentsQuery(@NonNull AccountCode account, Symbol symbols, InstrumentType types, Integer limit, Integer startFrom) {
    JSONObject result = getAccountInstrumentsQueryJSONObject(account, symbols, types, limit, startFrom);
    return JSONParser.jsonObjectParserToClass(result, InstrumentDetails.class);
  }

  public JSONObject getInstrumentsQueryJSONObject() {
    return getInstrumentsQueryJSONObject(null, null, null, null, null, null);
  }

  public JSONObject getInstrumentsQueryJSONObject(Symbol symbol) {
    return getInstrumentsQueryJSONObject(symbol, null, null, null, null, null);
  }

  public JSONObject getInstrumentsQueryJSONObject(InstrumentType type) {
    return getInstrumentsQueryJSONObject(null, type, null, null, null, null);
  }

  public JSONObject getInstrumentsQueryJSONObject(Symbol symbol, InstrumentType type) {
    return getInstrumentsQueryJSONObject(symbol, type, null, null, null, null);
  }

  public JSONObject getInstrumentsQueryJSONObject(
      Symbol symbols, InstrumentType types, Integer limit, Integer startFrom, String sortByAsc,
      String sortByDesc
  ) {
    StringBuffer endpoint = new StringBuffer("instruments/query");
    Check.endpointQueryType(endpoint, "symbols", symbols);
    Check.endpointQueryType(endpoint, "types", types);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "sort-by-asc", sortByAsc);
    Check.endpointQueryType(endpoint, "sort-by-desc", sortByDesc);
    return getInstrumentsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public InstrumentList getInstrumentsQuery() {
    return getInstrumentsQuery(null, null, null, null, null, null);
  }

  public InstrumentList getInstrumentsQuery(Symbol symbol) {
    return getInstrumentsQuery(symbol, null, null, null, null, null);
  }

  public InstrumentList getInstrumentsQuery(InstrumentType type) {
    return getInstrumentsQuery(null, type, null, null, null, null);
  }

  public InstrumentList getInstrumentsQuery(Symbol symbol, InstrumentType type) {
    return getInstrumentsQuery(symbol, type, null, null, null, null);
  }

  public InstrumentList getInstrumentsQuery(
      Symbol symbols, InstrumentType types, Integer limit, Integer startFrom, String sortByAsc,
      String sortByDesc
  ) {
    JSONObject result = getInstrumentsQueryJSONObject(symbols, types, limit, startFrom, sortByAsc, sortByDesc);
    return JSONParser.jsonObjectParserToClass(result, InstrumentList.class);
  }

  public JSONObject getAccountInstrumentsSymbolJSONObject(@NonNull AccountCode account, @NonNull Symbol symbol) {
    return getAccountInstrumentsSymbolJSONObject(account, symbol, null, null);
  }

  public JSONObject getAccountInstrumentsSymbolJSONObject(@NonNull AccountCode account, @NonNull Symbol symbol, Integer limit, Integer startFrom) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("/instruments/").append(symbol);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "startFrom", startFrom);
    return getInstrumentsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public InstrumentDetails getAccountInstrumentsSymbol(@NonNull AccountCode account, @NonNull Symbol symbol) {
    return getAccountInstrumentsSymbol(account, symbol, null, null);
  }

  public InstrumentDetails getAccountInstrumentsSymbol(@NonNull AccountCode account, @NonNull Symbol symbol, Integer limit, Integer startFrom) {
    JSONObject result = getAccountInstrumentsSymbolJSONObject(account, symbol, limit, startFrom);
    return JSONParser.jsonObjectParserToClass(result, InstrumentDetails.class);
  }

  public JSONObject getInstrumentsSymbolJSONObject(@NonNull Symbol symbol) {
    return getInstrumentsSymbolJSONObject(symbol, null, null, null, null);
  }

  public JSONObject getInstrumentsSymbolJSONObject(@NonNull Symbol symbol, Integer limit, Integer startFrom, String sortByAsc, String sortByDesc) {
    StringBuffer endpoint = new StringBuffer("instruments/").append(symbol);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "sort-by-asc", sortByAsc);
    Check.endpointQueryType(endpoint, "sort-by-desc", sortByDesc);
    return getInstrumentsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public InstrumentList getInstrumentsSymbol(@NonNull Symbol symbol) {
    return getInstrumentsSymbol(symbol, null, null, null, null);
  }

  public InstrumentList getInstrumentsSymbol(@NonNull Symbol symbol, Integer limit, Integer startFrom, String sortByAsc, String sortByDesc) {
    JSONObject result = getInstrumentsSymbolJSONObject(symbol, limit, startFrom, sortByAsc, sortByDesc);
    return JSONParser.jsonObjectParserToClass(result, InstrumentList.class);
  }

  public JSONObject getAccountInstrumentsTypeJSONObject(@NonNull AccountCode account, @NonNull InstrumentType type) {
    return getAccountInstrumentsTypeJSONObject(account, type, null, null);
  }

  public JSONObject getAccountInstrumentsTypeJSONObject(@NonNull AccountCode account, @NonNull InstrumentType type, Integer limit, Integer startFrom) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("/instruments/type/").append(type);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "startFrom", startFrom);
    return getInstrumentsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public InstrumentDetails getAccountInstrumentsType(@NonNull AccountCode account, @NonNull InstrumentType type) {
    return getAccountInstrumentsType(account, type, null, null);
  }

  public InstrumentDetails getAccountInstrumentsType(@NonNull AccountCode account, @NonNull InstrumentType type, Integer limit, Integer startFrom) {
    JSONObject result = getAccountInstrumentsTypeJSONObject(account, type, limit, startFrom);
    return JSONParser.jsonObjectParserToClass(result, InstrumentDetails.class);
  }

  public JSONObject getInstrumentsTypeJSONObject(@NonNull InstrumentType type) {
    return getInstrumentsTypeJSONObject(type, null, null, null, null);
  }

  public JSONObject getInstrumentsTypeJSONObject(@NonNull InstrumentType type, Integer limit, Integer startFrom, String sortByAsc, String sortByDesc) {
    StringBuffer endpoint = new StringBuffer("instruments/").append(type);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "sort-by-asc", sortByAsc);
    Check.endpointQueryType(endpoint, "sort-by-desc", sortByDesc);
    return getInstrumentsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public InstrumentList getInstrumentsType(@NonNull InstrumentType type) {
    return getInstrumentsType(type, null, null, null, null);
  }

  public InstrumentList getInstrumentsType(@NonNull InstrumentType type, Integer limit, Integer startFrom, String sortByAsc, String sortByDesc) {
    JSONObject result = getInstrumentsTypeJSONObject(type, limit, startFrom, sortByAsc, sortByDesc);
    return JSONParser.jsonObjectParserToClass(result, InstrumentList.class);
  }
}
