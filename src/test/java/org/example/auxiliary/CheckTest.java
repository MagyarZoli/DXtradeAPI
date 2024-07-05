package org.example.auxiliary;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CheckTest {

  @Test
  void testIsThrowJSONObject() {
    assertThrows(IllegalArgumentException.class, () -> {
      Check.isThrowJSONObject("methodName", null);
    });
  }

  @Test
  void testIsThrowTypeString() {
    assertThrows(IllegalArgumentException.class, () -> {
      Check.isThrowType("pathName", (String) null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      Check.isThrowType("pathName", "");
    });
  }

  @Test
  void testIsThrowTypeInteger() {
    assertThrows(IllegalArgumentException.class, () -> {
      Check.isThrowType("pathName", (Integer) null);
    });
  }

  @Test
  void testEndpointQueryTypeString() {
    StringBuffer stringBuffer = new StringBuffer();
    Check.endpointQueryType(stringBuffer, "queryName", "queryType");
    assertEquals("&queryName=queryType", stringBuffer.toString());
  }

  @Test
  void testEndpointQueryTypeInteger() {
    StringBuffer stringBuffer = new StringBuffer();
    Check.endpointQueryType(stringBuffer, "queryName", 123);
    assertEquals("&queryName=123", stringBuffer.toString());
  }

  @Test
  void testEndpointQueryTypeBoolean() {
    StringBuffer stringBuffer = new StringBuffer();
    Check.endpointQueryType(stringBuffer, "queryName", true);
    assertEquals("&queryName=false", stringBuffer.toString());
  }

  @Test
  void testConnectionHeaderType() {
    HttpURLConnection connection = mock(HttpURLConnection.class);
    Check.connectionHeaderType(connection, "headerName", "headerType");
    verify(connection, times(1)).setRequestProperty("headerName", "headerType");
  }

  @Test
  void testPutBodyTypeString() {
    JSONObject body = new JSONObject();
    Check.putBodyType(body, "bodyName", "bodyType");
    assertEquals("bodyType", body.getString("bodyName"));
  }

  @Test
  void testPutBodyTypeInteger() {
    JSONObject body = new JSONObject();
    Check.putBodyType(body, "bodyName", 123);
    assertEquals(123, body.getInt("bodyName"));
  }
}