package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpPost;
import org.example.auxiliary.JSONParser;
import org.example.connection.Connection;
import org.example.models.marketdata.MarketDataEventType;
import org.example.models.marketdata.MarketEvent;
import org.example.transfer.AccountCode;
import org.example.transfer.SessionToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Data
public class MarketData {

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
  public static final String EVENTS_KEY = "events";

  public JSONObject addMarketDataJSONObject(@NonNull JSONObject body) {
    jsonResponse = connection.authConnection("marketdata", new HttpPost(), sessionToken, body);

    JSONArray symbols = body.getJSONArray("symbols");
    JSONArray eventTypes = body.getJSONArray("eventTypes");

    JSONArray events = new JSONArray();
    for (int i = 0; i < symbols.length(); i++) {
      JSONObject jsonObject = new JSONObject()
          .put("symbol", symbols.get(i))
          .put("type", eventTypes.get(i));
      events.put(jsonObject);
    }

    JSONObject jsonResponse = new JSONObject()
        .put("events", events);

    return jsonResponse;
  }

  public JSONObject addMarketDataJSONObject(@NonNull String symbol, @NonNull MarketDataEventType eventTypes, @NonNull AccountCode account) {
    return addMarketDataJSONObject(List.of(symbol), List.of(eventTypes), account);
  }

  public JSONObject addMarketDataJSONObject(@NonNull String[] symbols, @NonNull MarketDataEventType[] eventTypes, @NonNull AccountCode account) {
    return addMarketDataJSONObject(Arrays.asList(symbols), Arrays.asList(eventTypes), account);
  }

  public JSONObject addMarketDataJSONObject(@NonNull String[] symbols, @NonNull List<MarketDataEventType> eventTypes, @NonNull AccountCode account) {
    return addMarketDataJSONObject(Arrays.asList(symbols), eventTypes, account);
  }

  public JSONObject addMarketDataJSONObject(@NonNull List<String> symbols, @NonNull MarketDataEventType[] eventTypes, @NonNull AccountCode account) {
    return addMarketDataJSONObject(symbols, Arrays.asList(eventTypes), account);
  }

  public JSONObject addMarketDataJSONObject(@NonNull List<String> symbols, @NonNull List<MarketDataEventType> eventTypes, @NonNull AccountCode account) {
    JSONObject body = new JSONObject();
    body.put("symbols", new JSONArray(symbols));
    JSONArray eventTypesArray = new JSONArray();
    for (MarketDataEventType eventType : eventTypes) {
      JSONObject eventTypeObject = new JSONObject();
      eventTypeObject.put("type", eventType.getType());
      eventTypeObject.put("format", eventType.getFormat());
      eventTypesArray.put(eventTypeObject);
    }
    body.put("eventTypes", eventTypesArray);
    body.put("account", account.getAccount());
    return addMarketDataJSONObject(body);
  }

  public List<MarketEvent> addMarketTypes(@NonNull String[] symbols, @NonNull MarketDataEventType[] eventTypes, @NonNull AccountCode account) {
    return addMarketTypes(Arrays.asList(symbols), Arrays.asList(eventTypes), account);
  }

  public List<MarketEvent> addMarketTypes(@NonNull String[] symbols, @NonNull List<MarketDataEventType> eventTypes, @NonNull AccountCode account) {
    return addMarketTypes(Arrays.asList(symbols), eventTypes, account);
  }

  public List<MarketEvent> addMarketTypes(@NonNull List<String> symbols, @NonNull MarketDataEventType[] eventTypes, @NonNull AccountCode account) {
    return addMarketTypes(symbols, Arrays.asList(eventTypes), account);
  }

  public List<MarketEvent> addMarketTypes(@NonNull List<String> symbols, @NonNull List<MarketDataEventType> eventTypes, @NonNull AccountCode account) {
    JSONObject result = addMarketDataJSONObject(symbols, eventTypes, account);
    return JSONParser.jsonObjectArrayParserToClass(result, EVENTS_KEY, MarketEvent.class);
  }
}
