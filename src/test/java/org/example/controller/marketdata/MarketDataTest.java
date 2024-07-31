package org.example.controller.marketdata;

import org.example.controller.AuthConnection;
import org.example.controller.MarketData;
import org.example.models.symbol.SymbolForex;
import org.example.models.marketdata.MarketDataEventType;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarketDataTest
extends AuthConnection {

  private MarketData marketData;

  @BeforeEach
  void setUp() {
    marketData = new MarketData(connection, auth.getSessionToken());
  }

  @Test
  void testAddMarketDataJSONObject() {
    String symbol = SymbolForex.EURUSD.toString();
    MarketDataEventType marketDataEventType = new MarketDataEventType("Candle", null);
    JSONObject jsonObject = marketData.addMarketDataJSONObject(List.of(symbol), List.of(marketDataEventType), accountCode);

    System.out.println(jsonObject);
    assertNotNull(jsonObject);
    fail(jsonObject.toString());
  }
}