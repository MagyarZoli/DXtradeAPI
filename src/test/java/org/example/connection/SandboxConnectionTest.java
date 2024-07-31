package org.example.connection;

import org.apache.http.client.methods.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SandboxConnectionTest {

  private static Connection connection;
  private static JSONObject body;
  private static JSONObject jsonResponse;
  private static Integer statusCode;

  @BeforeAll
  static void beforeAll() {
    connection = new Connection();
    connection.setBaseUrl("http://localhost:8080/");
    body = new JSONObject()
        .put("id", 1)
        .put("sandbox", "trade is good");
  }

  static Stream<Arguments> getArguments() {
    return Stream.of(
        Arguments.of("get", false),
        Arguments.of("get/1", false)
    );
  }

  static Stream<Arguments> postArguments() {
    return Stream.of(
        Arguments.of("post", false),
        Arguments.of("post/1", false),
        Arguments.of("post/body", true)
    );
  }

  static Stream<Arguments> putArguments() {
    return Stream.of(
        Arguments.of("put", false),
        Arguments.of("put/1", false),
        Arguments.of("put/body", true)
    );
  }

  static Stream<Arguments> deleteArguments() {
    return Stream.of(
        Arguments.of("delete", false),
        Arguments.of("delete/1", false)
    );
  }

  static Stream<Arguments> AllArguments() {
    return Stream.of(
        Arguments.of("post", false, "POST", 201),
        Arguments.of("post/1", false, "POST", 201),
        Arguments.of("post/body", true, "POST", 201),
        Arguments.of("get", false, "GET", 200),
        Arguments.of("get/1", false, "GET", 200),
        Arguments.of("put", false, "PUT", 200),
        Arguments.of("put/1", false, "PUT", 200),
        Arguments.of("put/body", true, "PUT", 200),
        Arguments.of("delete", false, "DELETE", 200),
        Arguments.of("delete/2", false, "DELETE", 200)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "getArguments")
  void testGet(String endpoint, Boolean isOutput) {
    jsonResponse = connection.connection(endpoint, new HttpGet());

    assertDoesNotThrow(() -> statusCode = connection.getStatusCode());
    assertEquals(statusCode, 200);
    assertNotNull(jsonResponse);
    System.out.println(jsonResponse);
  }

  @ParameterizedTest
  @MethodSource(value = "postArguments")
  void testPost(String endpoint, Boolean isOutput) {
    if (isOutput) {
      jsonResponse = connection.connection(endpoint, new HttpPost(), body);
    } else {
      jsonResponse = connection.connection(endpoint, new HttpPost());
    }

    assertDoesNotThrow(() -> statusCode = connection.getStatusCode());
    assertEquals(statusCode, 201);
    assertNotNull(jsonResponse);
    System.out.println(jsonResponse);
  }

  @ParameterizedTest
  @MethodSource(value = "putArguments")
  void testPut(String endpoint, Boolean isOutput) {
    if (isOutput) {
      jsonResponse = connection.connection(endpoint, new HttpPut(), body);
    } else {
      jsonResponse = connection.connection(endpoint, new HttpPut());
    }

    assertDoesNotThrow(() -> statusCode = connection.getStatusCode());
    assertEquals(statusCode, 200);
    assertNotNull(jsonResponse);
    System.out.println(jsonResponse);
  }

  @ParameterizedTest
  @MethodSource(value = "deleteArguments")
  void testDelete(String endpoint, Boolean isOutput) {
    jsonResponse = connection.connection(endpoint, new HttpDelete());

    assertDoesNotThrow(() -> statusCode = connection.getStatusCode());
    assertEquals(statusCode, 200);
    assertNotNull(jsonResponse);
    System.out.println(jsonResponse);
  }

  @ParameterizedTest
  @MethodSource(value = "AllArguments")
  void testAll(String endpoint, Boolean isOutput, String httpMethod, Integer actualResponseCode) {
    HttpRequestBase httpRequestBase = switch (httpMethod) {
      case "GET" -> new HttpGet();
      case "POST" -> new HttpPost();
      case "PUT" -> new HttpPut();
      case "DELETE" -> new HttpDelete();
      default -> null;
    };
    if (httpRequestBase != null) {
      if (isOutput) {
        assert httpRequestBase instanceof HttpEntityEnclosingRequestBase;
        jsonResponse = connection.connection(endpoint, (HttpEntityEnclosingRequestBase) httpRequestBase, body);
      } else {
        jsonResponse = connection.connection(endpoint, httpRequestBase);
      }

      assertDoesNotThrow(() -> statusCode = connection.getStatusCode());
      assertEquals(statusCode, actualResponseCode);
      assertNotNull(jsonResponse);
      System.out.println(jsonResponse);
    } else {
      fail();
    }
  }
}
