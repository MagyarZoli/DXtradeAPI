package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.example.auxiliary.Check;
import org.example.auxiliary.JSONParser;
import org.example.connection.Connection;
import org.example.models.accounts.Side;
import org.example.models.accounts.orders.*;
import org.example.models.symbol.Symbol;
import org.example.transfer.AccountCode;
import org.example.transfer.SessionToken;
import org.example.transfer.accounts.orders.OpenPosition;
import org.example.transfer.accounts.orders.OrderCode;
import org.example.transfer.accounts.orders.Quantity;
import org.json.JSONObject;

@RequiredArgsConstructor
@Data
public class Orders {

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
  public static final String ORDERS_KEY = "orders";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String ORDERS_HISTORY_KEY = "orders";

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String ORDER_RESPONSES_KEY = "orderResponses";

  public JSONObject getOrdersJSONObject(@NonNull String endpoint) {
    return getOrdersJSONObject(endpoint, null);
  }

  public JSONObject getOrdersJSONObject(@NonNull String endpoint, String ifNoneMatch) {
    jsonResponse = connection.authConnection(endpoint, new HttpGet(), sessionToken, ifNoneMatch);
    return jsonResponse;
  }

  public JSONObject addOrdersJSONObject(@NonNull String endpoint) {
    return addOrdersJSONObject(endpoint, null);
  }

  public JSONObject addOrdersJSONObject(@NonNull String endpoint, JSONObject orderRequest) {
    jsonResponse = connection.authConnection(endpoint, new HttpPost(), sessionToken, orderRequest);
    return jsonResponse;
  }

  public JSONObject setOrdersJSONObject(@NonNull String endpoint, JSONObject orderRequest) {
    jsonResponse = connection.authConnection(endpoint, new HttpPut(), sessionToken, orderRequest);
    return jsonResponse;
  }

  public JSONObject removeOrdersJSONObject(@NonNull String endpoint) {
    jsonResponse = connection.authConnection(endpoint, new HttpDelete(), sessionToken);
    return jsonResponse;
  }

  public JSONObject getAccountOrdersJSONObject(@NonNull AccountCode account) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(account.getAccount())
        .append("/orders");
    return getOrdersJSONObject(endpoint.toString());
  }

  public JSONObject getAccountOrdersJSONObject(@NonNull AccountCode account, String ifNoneMatch) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(account.getAccount())
        .append("/orders");
    return getOrdersJSONObject(endpoint.toString(), ifNoneMatch);
  }

  public OrderList getAccountOrders(@NonNull AccountCode account) {
    JSONObject result = getAccountOrdersJSONObject(account, null);
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public OrderList getAccountOrders(@NonNull AccountCode account, String ifNoneMatch) {
    JSONObject result = getAccountOrdersJSONObject(account, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public JSONObject addAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif
  ) {
    return addAccountOrdersJSONObject(
        account, type, instrument, quantity, positionEffect,
        side, tif, OrderCode.makesOrderCode()
    );
  }

  public JSONObject addAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Quantity quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif
  ) {
    return addAccountOrdersJSONObject(
        account, type, instrument, quantity.getQuantity(), positionEffect,
        side, tif, OrderCode.makesOrderCode()
    );
  }

  public JSONObject addAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode
  ) {
    JSONObject body = new JSONObject()
        .put("account", account.getAccount())
        .put("orderCode", orderCode)
        .put("type", type)
        .put("instrument", instrument)
        .put("quantity", quantity)
        .put("positionEffect", positionEffect)
        .put("side", side)
        .put("tif", tif);
    return addAccountOrdersJSONObject(account, body);
  }

  public JSONObject addAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Quantity quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode
  ) {
    return addAccountOrdersJSONObject(
        account, type, instrument, quantity.getQuantity(), positionEffect,
        side, tif, orderCode
    );
  }

  public JSONObject addAccountOrdersJSONObject(@NonNull AccountCode account, @NonNull OpenPosition orderPosition) {
    return addAccountOrdersJSONObject(
        account,
        orderPosition.getType(),
        orderPosition.getInstrument(),
        orderPosition.getQuantity(),
        orderPosition.getPositionEffect(),
        orderPosition.getSide(),
        orderPosition.getTif(),
        orderPosition.getOrderCode()
    );
  }

  public JSONObject addAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode,
      String positionCode, Integer limitPrice, Integer stopPrice, Integer priceOffset, PriceLink priceLink,
      Integer marginRate, String expireDate
  ) {
    JSONObject body = new JSONObject()
        .put("account", account.getAccount())
        .put("orderCode", orderCode)
        .put("type", type)
        .put("instrument", instrument)
        .put("quantity", quantity)
        .put("positionEffect", positionEffect)
        .put("positionCode", positionCode)
        .put("side", side)
        .put("tif", tif);
    Check.putBodyType(body, "limitPrice", limitPrice);
    Check.putBodyType(body, "stopPrice", stopPrice);
    Check.putBodyType(body, "priceOffset", priceOffset);
    Check.putBodyType(body, "priceLink", priceLink.name());
    Check.putBodyType(body, "marginRate", marginRate);
    Check.putBodyType(body, "expireDate", expireDate);
    return addAccountOrdersJSONObject(account, body);
  }

  public JSONObject addAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Quantity quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode,
      String positionCode, Integer limitPrice, Integer stopPrice, Integer priceOffset, PriceLink priceLink,
      Integer marginRate, String expireDate
  ) {
    return addAccountOrdersJSONObject(
        account, type, instrument, quantity.getQuantity(), positionEffect,
        side, tif, orderCode,
        positionCode, limitPrice, stopPrice, priceOffset, priceLink,
        marginRate, expireDate
    );
  }

  public OrderResponse addAccountOrders(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif
  ) {
    JSONObject result = addAccountOrdersJSONObject(
        account, type, instrument, quantity, positionEffect,
        side, tif
    );
    return JSONParser.jsonObjectParserToClass(result, OrderResponse.class);
  }

  public OrderResponse addAccountOrders(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Quantity quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif
  ) {
    return addAccountOrders(
        account, type, instrument, quantity.getQuantity(), positionEffect,
        side, tif
    );
  }

  public OrderResponse addAccountOrders(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode
  ) {
    JSONObject result = addAccountOrdersJSONObject(
        account, type, instrument, quantity, positionEffect,
        side, tif, orderCode
    );
    return JSONParser.jsonObjectParserToClass(result, OrderResponse.class);
  }

  public OrderResponse addAccountOrders(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Quantity quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode
  ) {
    return addAccountOrders(
        account, type, instrument, quantity.getQuantity(), positionEffect,
        side, tif, orderCode
    );
  }

  public OrderResponse addAccountOrders(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode,
      String positionCode, Integer limitPrice, Integer stopPrice, Integer priceOffset, PriceLink priceLink,
      Integer marginRate, String expireDate
  ) {
    JSONObject result = addAccountOrdersJSONObject(
        account, type, instrument, quantity, positionEffect,
        side, tif, orderCode,
        positionCode,limitPrice, stopPrice, priceOffset, priceLink,
        marginRate, expireDate
    );
    return JSONParser.jsonObjectParserToClass(result, OrderResponse.class);
  }

  public OrderResponse addAccountOrders(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Quantity quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String orderCode,
      String positionCode, Integer limitPrice, Integer stopPrice, Integer priceOffset, PriceLink priceLink,
      Integer marginRate, String expireDate
  ) {
    return addAccountOrders(
        account, type, instrument, quantity.getQuantity(), positionEffect,
        side, tif, orderCode,
        positionCode, limitPrice, stopPrice, priceOffset, priceLink,
        marginRate, expireDate
    );
  }

  public JSONObject addAccountOrdersJSONObject(@NonNull AccountCode account, @NonNull JSONObject orderRequest) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(account.getAccount())
        .append("/orders");
    return addOrdersJSONObject(endpoint.toString(), orderRequest);
  }

  public OrderResponse addAccountOrders(@NonNull AccountCode account, @NonNull JSONObject orderRequest) {
    JSONObject result = addAccountOrdersJSONObject(account, orderRequest);
    return JSONParser.jsonObjectParserToClass(result, OrderResponse.class);
  }

  public JSONObject setAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String positionCode
  ) {
    JSONObject body = new JSONObject();
    body.put("account", account.getAccount());
    body.put("orderCode", OrderCode.makesOrderCode());
    body.put("type", type);
    body.put("instrument", instrument);
    body.put("quantity", quantity);
    body.put("positionEffect", positionEffect);
    body.put("side", side);
    body.put("tif", tif);
    body.put("positionCode", positionCode);
    return setAccountOrdersJSONObject(account, body);
  }


  public JSONObject setAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String positionCode, @NonNull String orderCode
  ) {
    JSONObject body = new JSONObject()
        .put("account", account.getAccount())
        .put("orderCode", orderCode)
        .put("type", type)
        .put("instrument", instrument)
        .put("quantity", quantity)
        .put("positionEffect", positionEffect)
        .put("side", side)
        .put("tif", tif)
        .put("positionCode", positionCode);
    return setAccountOrdersJSONObject(account, body);
  }

  public JSONObject setAccountOrdersJSONObject(
      @NonNull AccountCode account, @NonNull OrderType type, @NonNull Symbol instrument, @NonNull Integer quantity, @NonNull PositionEffect positionEffect,
      @NonNull Side side, @NonNull TIF tif, @NonNull String positionCode, @NonNull String orderCode,
      Integer limitPrice, /*Integer stopPrice,*/ Double stopPrice, Integer priceOffset, PriceLink priceLink,  Integer marginRate,
      String expireDate
  ) {
    JSONObject body = new JSONObject()
        .put("account", account.getAccount())
        .put("orderCode", orderCode)
        .put("type", type)
        .put("instrument", instrument)
        .put("quantity", quantity)
        .put("positionEffect", positionEffect)
        .put("side", side)
        .put("tif", tif)
        .put("positionCode", positionCode);
    Check.putBodyType(body, "limitPrice", limitPrice);
    Check.putBodyType(body, "stopPrice", stopPrice);
    Check.putBodyType(body, "priceOffset", priceOffset);
    Check.putBodyType(body, "priceLink", priceLink.name());
    Check.putBodyType(body, "marginRate", marginRate);
    Check.putBodyType(body, "expireDate", expireDate);
    return setAccountOrdersJSONObject(account, body);
  }

  public JSONObject getAccountsOrdersJSONObject(@NonNull AccountCode accounts) {
    return getAccountsOrdersJSONObject(accounts, null);
  }

  public JSONObject getAccountsOrdersJSONObject(@NonNull AccountCode accounts, String ifNoneMatch) {
    StringBuffer endpoint = new StringBuffer("accounts/orders");
    Check.endpointQueryType(endpoint, "accounts/", accounts.getAccount());
    return getOrdersJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public OrderList getAccountsOrders(@NonNull AccountCode accounts) {
    return getAccountsOrders(accounts, null);
  }

  public OrderList getAccountsOrders(@NonNull AccountCode accounts, String ifNoneMatch) {
    JSONObject result = getAccountsOrdersJSONObject(accounts, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public JSONObject setAccountOrdersJSONObject(@NonNull AccountCode account, @NonNull JSONObject orderRequest) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(account.getAccount())
        .append("/orders");
    return setOrdersJSONObject(endpoint.toString(), orderRequest);
  }

  public JSONObject getAccountOrdersHistoryJSONObject(@NonNull AccountCode account) {
    return getAccountOrdersHistoryJSONObject(
        account, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public JSONObject getAccountOrdersHistoryJSONObject(
      @NonNull AccountCode account, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("orders/history");
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "page", page);
    Check.endpointQueryType(endpoint, "page-size", pageSize);
    Check.endpointQueryType(endpoint, "in-status", inStatus);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "issued-from", issuedFrom);
    Check.endpointQueryType(endpoint, "issued-to", issuedTo);
    Check.endpointQueryType(endpoint, "period", period);
    Check.endpointQueryType(endpoint, "completed-from", completedFrom);
    Check.endpointQueryType(endpoint, "completed-to", completedTo);
    Check.endpointQueryType(endpoint, "at-least-version", atLeastVersion);
    Check.endpointQueryType(endpoint, "at-most-version", atMostVersion);
    Check.endpointQueryType(endpoint, "side", side);
    Check.endpointQueryType(endpoint, "with-client-id", withClientId);
    Check.endpointQueryType(endpoint, "with-order-id", withOrderId);
    Check.endpointQueryType(endpoint, "for-instrument", forInstrument);
    Check.endpointQueryType(endpoint, "transaction-from", transactionFrom);
    Check.endpointQueryType(endpoint, "transaction-to", transactionTo);
    return getOrdersJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public OrderList getAccountOrdersHistory(@NonNull AccountCode account) {
    JSONObject result = getAccountOrdersHistoryJSONObject(
        account, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public OrderList getAccountOrdersHistory(
      @NonNull AccountCode account, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    JSONObject result = getAccountOrdersHistoryJSONObject(
        account, limit, page, pageSize, inStatus,
        ofType, issuedFrom, issuedTo, period, completedFrom,
        completedTo, atLeastVersion, atMostVersion, side, withClientId,
        withOrderId, forInstrument, transactionFrom, transactionTo
    );
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public JSONObject addAccountOrdersHistoryJSONObject(@NonNull AccountCode account) {
    return addAccountOrdersHistoryJSONObject(
        account, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public JSONObject addAccountOrdersHistoryJSONObject(
      @NonNull AccountCode account, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/").append(account.getAccount()).append("orders/history");
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "page", page);
    Check.endpointQueryType(endpoint, "page-size", pageSize);
    Check.endpointQueryType(endpoint, "in-status", inStatus);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "issued-from", issuedFrom);
    Check.endpointQueryType(endpoint, "issued-to", issuedTo);
    Check.endpointQueryType(endpoint, "period", period);
    Check.endpointQueryType(endpoint, "completed-from", completedFrom);
    Check.endpointQueryType(endpoint, "completed-to", completedTo);
    Check.endpointQueryType(endpoint, "at-least-version", atLeastVersion);
    Check.endpointQueryType(endpoint, "at-most-version", atMostVersion);
    Check.endpointQueryType(endpoint, "side", side);
    Check.endpointQueryType(endpoint, "with-client-id", withClientId);
    Check.endpointQueryType(endpoint, "with-order-id", withOrderId);
    Check.endpointQueryType(endpoint, "for-instrument", forInstrument);
    Check.endpointQueryType(endpoint, "transaction-from", transactionFrom);
    Check.endpointQueryType(endpoint, "transaction-to", transactionTo);
    return addOrdersJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public OrderList addAccountOrdersHistory(@NonNull AccountCode account) {
    return addAccountOrdersHistory(
        account, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public OrderList addAccountOrdersHistory(
      @NonNull AccountCode account, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    JSONObject result = addAccountOrdersHistoryJSONObject(
        account, limit, page, pageSize, inStatus,
        ofType, issuedFrom, issuedTo, period, completedFrom,
        completedTo, atLeastVersion, atMostVersion, side, withClientId,
        withOrderId, forInstrument, transactionFrom, transactionTo
    );
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public JSONObject getAccountsOrdersHistoryJSONObject() {
    return getAccountsOrdersHistoryJSONObject(
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public JSONObject getAccountsOrdersHistoryJSONObject(
      AccountCode accounts, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/orders/history");
    Check.endpointQueryType(endpoint, "accounts/", accounts.getAccount()); // FIXME: 2024. 04. 25. account null -> throw NullPointerException
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "page", page);
    Check.endpointQueryType(endpoint, "page-size", pageSize);
    Check.endpointQueryType(endpoint, "in-status", inStatus);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "issued-from", issuedFrom);
    Check.endpointQueryType(endpoint, "issued-to", issuedTo);
    Check.endpointQueryType(endpoint, "period", period);
    Check.endpointQueryType(endpoint, "completed-from", completedFrom);
    Check.endpointQueryType(endpoint, "completed-to", completedTo);
    Check.endpointQueryType(endpoint, "at-least-version", atLeastVersion);
    Check.endpointQueryType(endpoint, "at-most-version", atMostVersion);
    Check.endpointQueryType(endpoint, "side", side);
    Check.endpointQueryType(endpoint, "with-client-id", withClientId);
    Check.endpointQueryType(endpoint, "with-order-id", withOrderId);
    Check.endpointQueryType(endpoint, "for-instrument", forInstrument);
    Check.endpointQueryType(endpoint, "transaction-from", transactionFrom);
    Check.endpointQueryType(endpoint, "transaction-to", transactionTo);
    return getOrdersJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public OrderList getAccountsOrdersHistory() {
    return getAccountsOrdersHistory(
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public OrderList getAccountsOrdersHistory(
      AccountCode accounts, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    JSONObject result = getAccountsOrdersHistoryJSONObject(
        accounts, limit, page, pageSize, inStatus,
        ofType, issuedFrom, issuedTo, period, completedFrom,
        completedTo, atLeastVersion, atMostVersion, side, withClientId,
        withOrderId, forInstrument, transactionFrom, transactionTo
    );
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public JSONObject addAccountsOrdersHistoryJSONObject() {
    return addAccountsOrdersHistoryJSONObject(
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public JSONObject addAccountsOrdersHistoryJSONObject(
      AccountCode accounts, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    StringBuffer endpoint = new StringBuffer("accounts/orders/history");
    Check.endpointQueryType(endpoint, "accounts/", accounts.getAccount());
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "page", page);
    Check.endpointQueryType(endpoint, "page-size", pageSize);
    Check.endpointQueryType(endpoint, "in-status", inStatus);
    Check.endpointQueryType(endpoint, "of-type", ofType);
    Check.endpointQueryType(endpoint, "issued-from", issuedFrom);
    Check.endpointQueryType(endpoint, "issued-to", issuedTo);
    Check.endpointQueryType(endpoint, "period", period);
    Check.endpointQueryType(endpoint, "completed-from", completedFrom);
    Check.endpointQueryType(endpoint, "completed-to", completedTo);
    Check.endpointQueryType(endpoint, "at-least-version", atLeastVersion);
    Check.endpointQueryType(endpoint, "at-most-version", atMostVersion);
    Check.endpointQueryType(endpoint, "side", side);
    Check.endpointQueryType(endpoint, "with-client-id", withClientId);
    Check.endpointQueryType(endpoint, "with-order-id", withOrderId);
    Check.endpointQueryType(endpoint, "for-instrument", forInstrument);
    Check.endpointQueryType(endpoint, "transaction-from", transactionFrom);
    Check.endpointQueryType(endpoint, "transaction-to", transactionTo);
    return addOrdersJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public OrderList addAccountsOrdersHistoryList() {
    return addAccountsOrdersHistoryList(
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null, null,
        null, null, null, null
    );
  }

  public OrderList addAccountsOrdersHistoryList(
      AccountCode accounts, Integer limit, Integer page, Integer pageSize, String inStatus,
      String ofType, String issuedFrom, String issuedTo, String period, String completedFrom,
      String completedTo, Integer atLeastVersion, Integer atMostVersion, String side, String withClientId,
      String withOrderId, String forInstrument, String transactionFrom, String transactionTo
  ) {
    JSONObject result = addAccountsOrdersHistoryJSONObject(
        accounts, limit, page, pageSize, inStatus,
        ofType, issuedFrom, issuedTo, period, completedFrom,
        completedTo, atLeastVersion, atMostVersion, side, withClientId,
        withOrderId, forInstrument, transactionFrom, transactionTo
    );
    return JSONParser.jsonObjectParserToClass(result, OrderList.class);
  }

  public JSONObject removeAccountOrdersJSONObject(@NonNull AccountCode account, @NonNull String order) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(account.getAccount())
        .append("/orders/")
        .append(order);
    return removeOrdersJSONObject(endpoint.toString());
  }

  public OrderResponse removeAccountOrders(@NonNull AccountCode account, @NonNull String order) {
    JSONObject result = removeAccountOrdersJSONObject(account, order);
    return JSONParser.jsonObjectParserToClass(result, OrderResponse.class);
  }

  public JSONObject removeAccountOrdersGroupJSONObject(@NonNull AccountCode account) {
    return removeAccountOrdersGroupJSONObject(account, null, null);
  }

  public JSONObject removeAccountOrdersGroupJSONObject(@NonNull AccountCode account, String contingencyType) {
    return removeAccountOrdersGroupJSONObject(account, contingencyType, OrderCode.makesOrderCode());
  }

  public JSONObject removeAccountOrdersGroupJSONObject(@NonNull AccountCode account, String contingencyType, String orderCodes) {
    StringBuffer endpoint = new StringBuffer("accounts/")
        .append(account.getAccount())
        .append("/orders/group");
    Check.endpointQueryType(endpoint, "order-codes", orderCodes);
    Check.endpointQueryType(endpoint, "contingency-type", contingencyType);
    return removeOrdersJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public OrderResponseList removeAccountOrdersGroupList(@NonNull AccountCode account) {
    return removeAccountOrdersGroupList(account, null, null);
  }

  public OrderResponseList removeAccountOrdersGroupList(@NonNull AccountCode account, String contingencyType) {
    return removeAccountOrdersGroupList(account, contingencyType, OrderCode.makesOrderCode());
  }

  public OrderResponseList removeAccountOrdersGroupList(@NonNull AccountCode account, String contingencyType, String orderCodes) {
    JSONObject result = removeAccountOrdersGroupJSONObject(account, contingencyType, orderCodes);
    return JSONParser.jsonObjectParserToClass(result, OrderResponseList.class);
  }
}
