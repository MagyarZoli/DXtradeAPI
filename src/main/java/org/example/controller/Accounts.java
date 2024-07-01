package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpGet;
import org.example.auxiliary.Check;
import org.example.auxiliary.JSONParser;
import org.example.connection.Connection;
import org.example.models.accounts.*;
import org.example.transfer.AccountCode;
import org.example.transfer.SessionToken;
import org.example.transfer.accounts.AccountTransfer;
import org.json.JSONObject;

@RequiredArgsConstructor
@Data
public class Accounts {

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
  public static final String ACCOUNT_EVENTS_KEY = "accountEvents";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String METRICS_KEY = "metrics";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String POSITIONS_KEY = "positions";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String PORTFOLIOS_KEY = "portfolios";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String CASH_TRANSFERS_KEY = "cashTransfers";

  public JSONObject getAccountsJSONObject(@NonNull String endpoint) {
    return getAccountsJSONObject(endpoint, null);
  }

  public JSONObject getAccountsJSONObject(@NonNull String endpoint, String ifNoneMatch) {
    jsonResponse = connection.authConnection(endpoint, new HttpGet(), sessionToken, ifNoneMatch);
    return jsonResponse;
  }

  public JSONObject getAccountEventsJSONObject(@NonNull AccountCode account) {
    return getAccountEventsJSONObject(
        account, null, null, null, null,
        null, null, null
    );
  }

  public JSONObject getAccountEventsJSONObject(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getAccountEventsJSONObject(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public JSONObject getAccountEventsJSONObject(@NonNull AccountCode account, AccountTransfer accountTransfer) {
    return getAccountEventsJSONObject(account, accountTransfer, null);
  }

  public JSONObject getAccountEventsJSONObject(@NonNull AccountCode account, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getAccountEventsJSONObject(
        account,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public JSONObject getAccountEventsJSONObject(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("/events");
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "time-from", timeFrom);
    Check.endpointQueryType(endpoint, "time-to", timeTo);
    Check.endpointQueryType(endpoint, "period", period);
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public AccountEventList getAccountEvents(@NonNull AccountCode account) {
    return getAccountEvents(
        account, null, null, null, null,
        null, null, null
    );
  }

  public AccountEventList getAccountEvents(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getAccountEvents(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public AccountEventList getAccountEvents(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    JSONObject result = getAccountEventsJSONObject(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, ifNoneMatch
    );
    return JSONParser.jsonObjectParserToClass(result, AccountEventList.class);
  }

  public AccountEventList getAccountEvents(@NonNull AccountCode account, AccountTransfer accountTransfer) {
    return getAccountEvents(account, accountTransfer, null);
  }

  public AccountEventList getAccountEvents(@NonNull AccountCode account, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getAccountEvents(
        account,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public JSONObject getEventsJSONObject() {
    return getEventsJSONObject(
        null, null, null, null, null,
        null, null, null
    );
  }

  public JSONObject getEventsJSONObject(
      AccountCode accounts, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getEventsJSONObject(
        accounts, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public JSONObject getEventsJSONObject(AccountCode accounts, AccountTransfer accountTransfer) {
    return getEventsJSONObject(accounts, accountTransfer, null);
  }

  public JSONObject getEventsJSONObject(AccountCode accounts, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getEventsJSONObject(
        accounts,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public JSONObject getEventsJSONObject(
      AccountCode accounts, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/events");
    Check.endpointQueryType(endpoint, "accounts", accounts);
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "time-from", timeFrom);
    Check.endpointQueryType(endpoint, "time-to", timeTo);
    Check.endpointQueryType(endpoint, "period", period);
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public AccountEventList getEvents() {
    return getEvents(
        null, null, null, null, null,
        null, null, null
    );
  }

  public AccountEventList getEvents(
      AccountCode accounts, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getEvents(
        accounts, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public AccountEventList getEvents(
      AccountCode accounts, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    JSONObject result = getEventsJSONObject(
        accounts, limit, startFrom, ofType, timeFrom,
        timeTo, period, ifNoneMatch
    );
    return JSONParser.jsonObjectParserToClass(result, AccountEventList.class);
  }

  public AccountEventList getEvents(AccountCode accounts, AccountTransfer accountTransfer) {
    return getEvents(accounts, accountTransfer, null);
  }

  public AccountEventList getEvents(AccountCode accounts, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getEvents(
        accounts,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public JSONObject getAccountMetricsJSONObject(@NonNull AccountCode account) {
    return getAccountMetricsJSONObject(account, false);
  }

  public JSONObject getAccountMetricsJSONObject(@NonNull AccountCode account, Boolean includePositions) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("/metrics");
    Check.endpointQueryType(endpoint, "include-positions", includePositions);
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public AccountMetricList getAccountMetrics(@NonNull AccountCode account) {
    return getAccountMetrics(account, false);
  }

  public AccountMetricList getAccountMetrics(@NonNull AccountCode account, Boolean includePositions) {
    JSONObject result = getAccountMetricsJSONObject(account, includePositions);
    return JSONParser.jsonObjectParserToClass(result, AccountMetricList.class);
  }

  public JSONObject getMetricsJSONObject() {
    return getMetricsJSONObject(null, false);
  }

  public JSONObject getMetricsJSONObject(String accounts) {
    return getMetricsJSONObject(accounts, false);
  }

  public JSONObject getMetricsJSONObject(String accounts, Boolean includePositions) {
    StringBuffer endpoint = new StringBuffer("accounts/metrics");
    Check.endpointQueryType(endpoint, "accounts", accounts);
    Check.endpointQueryType(endpoint, "include-positions", includePositions);
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public AccountMetricList getMetrics() {
    return getMetrics(null, false);
  }

  public AccountMetricList getMetrics(String accounts) {
    return getMetrics(accounts, false);
  }

  public AccountMetricList getMetrics(String accounts, Boolean includePositions) {
    JSONObject result = getMetricsJSONObject(accounts, includePositions);
    return JSONParser.jsonObjectParserToClass(result, AccountMetricList.class);
  }

  public JSONObject getAccountOpenPositionsJSONObject(@NonNull AccountCode account) {
    return getAccountOpenPositionsJSONObject(account, null);
  }

  public JSONObject getAccountOpenPositionsJSONObject(@NonNull AccountCode account, String ifNoneMatch) {
    return getAccountsJSONObject("accounts/" + account.getAccount() + "/positions", ifNoneMatch);
  }

  public PositionList getAccountOpenPositions(@NonNull AccountCode account) {
    return getAccountOpenPositions(account, null);
  }

  public PositionList getAccountOpenPositions(@NonNull AccountCode account, String ifNoneMatch) {
    JSONObject result = getAccountOpenPositionsJSONObject(account, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, PositionList.class);
  }

  public JSONObject getOpenPositionsJSONObject(@NonNull AccountCode account) {
    return getOpenPositionsJSONObject(account, null);
  }

  public JSONObject getOpenPositionsJSONObject(@NonNull AccountCode account, String ifNoneMatch) {
    StringBuffer endpoint = new StringBuffer("accounts/positions");
    Check.endpointQueryType(endpoint, "accounts", account.getAccount());
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public PositionList getOpenPositions(@NonNull AccountCode account) {
    return getOpenPositions(account, null);
  }

  public PositionList getOpenPositions(@NonNull AccountCode account, String ifNoneMatch) {
    JSONObject result = getOpenPositionsJSONObject(account, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, PositionList.class);
  }

  public JSONObject getAccountPortfoliosJSONObject(@NonNull AccountCode account) {
    return getAccountPortfoliosJSONObject(account, null);
  }

  public JSONObject getAccountPortfoliosJSONObject(@NonNull AccountCode account, String ifNoneMatch) {
    return getAccountsJSONObject("accounts/" + account.getAccount() + "/portfolio", ifNoneMatch);
  }

  public AccountPortfolioList getAccountPortfolios(@NonNull AccountCode account) {
    return getAccountPortfolios(account, null);
  }

  public AccountPortfolioList getAccountPortfolios(@NonNull AccountCode account, String ifNoneMatch) {
    JSONObject result = getAccountPortfoliosJSONObject(account, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, AccountPortfolioList.class);
  }

  public JSONObject getPortfoliosJSONObject(@NonNull AccountCode account) {
    return getPortfoliosJSONObject(account, null);
  }

  public JSONObject getPortfoliosJSONObject(@NonNull AccountCode account, String ifNoneMatch) {
    StringBuffer endpoint = new StringBuffer("accounts/portfolio");
    Check.endpointQueryType(endpoint, "accounts", account.getAccount());
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public AccountPortfolioList getPortfolios(@NonNull AccountCode account) {
    return getPortfolios(account, null);
  }

  public AccountPortfolioList getPortfolios(@NonNull AccountCode account, String ifNoneMatch) {
    JSONObject result = getPortfoliosJSONObject(account, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, AccountPortfolioList.class);
  }

  public JSONObject getAccountTransfersJSONObject(@NonNull AccountCode account) {
    return getAccountTransfersJSONObject(
        account, null, null, null, null,
        null, null, null
    );
  }

  public JSONObject getAccountTransfersJSONObject(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getAccountTransfersJSONObject(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public JSONObject getAccountTransfersJSONObject(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("/transfers");
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "time-from", timeFrom);
    Check.endpointQueryType(endpoint, "time-to", timeTo);
    Check.endpointQueryType(endpoint, "period", period);
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public JSONObject getAccountTransfersJSONObject(@NonNull AccountCode account, AccountTransfer accountTransfer) {
    return getAccountTransfersJSONObject(account, accountTransfer, null);
  }

  public JSONObject getAccountTransfersJSONObject(@NonNull AccountCode account, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getAccountTransfersJSONObject(
        account,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public CashTransferList getAccountTransfers(@NonNull AccountCode account) {
    return getAccountTransfers(
        account, null, null, null, null,
        null, null, null
    );
  }

  public CashTransferList getAccountTransfers(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getAccountTransfers(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public CashTransferList getAccountTransfers(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    JSONObject result = getAccountTransfersJSONObject(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, ifNoneMatch
    );
    return JSONParser.jsonObjectParserToClass(result, CashTransferList.class);
  }

  public CashTransferList getAccountTransfers(@NonNull AccountCode account, AccountTransfer accountTransfer) {
    return getAccountTransfers(account, accountTransfer, null);
  }

  public CashTransferList getAccountTransfers(@NonNull AccountCode account, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getAccountTransfers(
        account,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public JSONObject getTransfersJSONObject(@NonNull AccountCode account) {
    return getTransfersJSONObject(
        account, null, null, null, null,
        null, null, null
    );
  }

  public JSONObject getTransfersJSONObject(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getTransfersJSONObject(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public JSONObject getTransfersJSONObject(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/transfers");
    Check.endpointQueryType(endpoint, "accounts", account.getAccount());
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "time-from", timeFrom);
    Check.endpointQueryType(endpoint, "time-to", timeTo);
    Check.endpointQueryType(endpoint, "period", period);
    return getAccountsJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public JSONObject getTransfersJSONObject(@NonNull AccountCode account, AccountTransfer accountTransfer) {
    return getTransfersJSONObject(account, accountTransfer, null);
  }

  public JSONObject getTransfersJSONObject(@NonNull AccountCode account, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getTransfersJSONObject(
        account,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }

  public CashTransferList getTransfers(@NonNull AccountCode account) {
    return getTransfers(
        account, null, null, null, null,
        null, null, null
    );
  }

  public CashTransferList getTransfers(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period
  ) {
    return getTransfers(
        account, limit, startFrom, ofType, timeFrom,
        timeTo, period, null
    );
  }

  public CashTransferList getTransfers(
      @NonNull AccountCode account, Integer limit, Integer startFrom, String ofType, String timeFrom,
      String timeTo, String period, String ifNoneMatch
  ) {
    JSONObject result = getTransfersJSONObject(
        account, limit, startFrom, ofType, timeFrom, timeTo, period, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, CashTransferList.class);
  }

  public CashTransferList getTransfers(@NonNull AccountCode account, AccountTransfer accountTransfer) {
    return getTransfers(account, accountTransfer, null);
  }

  public CashTransferList getTransfers(@NonNull AccountCode account, AccountTransfer accountTransfer, String ifNoneMatch) {
    return getTransfers(
        account,
        accountTransfer.getLimit(),
        accountTransfer.getStartFrom(),
        accountTransfer.getOfType(),
        accountTransfer.getTimeFrom(),
        accountTransfer.getTimeTo(),
        accountTransfer.getPeriod(),
        ifNoneMatch
    );
  }
}
