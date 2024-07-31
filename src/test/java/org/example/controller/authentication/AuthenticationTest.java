package org.example.controller.authentication;

import org.example.auxiliary.ConfigReader;
import org.example.auxiliary.InvokeMethod;
import org.example.connection.Connection;
import org.example.controller.Authentication;
import org.example.transfer.SessionToken;
import org.example.transfer.authentication.LoginRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationTest {

  private static Authentication auth;
  private static SessionToken sessionToken;

  private static String username;
  private static String domain;
  private static String password;
  private static JSONObject body;
  private static LoginRequest loginRequest;

  @BeforeEach
  void setUp() {
    Connection connection = new Connection();
    sessionToken = null;
    auth = new Authentication(connection);
    username = ConfigReader.getPropertyValue("username");
    domain = ConfigReader.getPropertyValue("domain");
    password = ConfigReader.getPropertyValue("password");
    body = new JSONObject()
        .put("username", username)
        .put("domain", domain)
        .put("password", password);
    loginRequest = new LoginRequest(username, domain, password);
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> loginJSONObjectOverload() {
    return Stream.of(
        () -> auth.loginJSONObject(username, password)//,
        //() -> auth.loginJSONObject(username, domain, password),
        //() -> auth.loginJSONObject(body),
        //() -> auth.loginJSONObject(loginRequest)
    );
  }

  static Stream<InvokeMethod.Invoke<SessionToken>> loginOverload() {
    return Stream.of(
        () -> auth.login(username, password),
        () -> auth.login(username, domain, password),
        () -> auth.login(body),
        () -> auth.login(loginRequest)
    );
  }

  static Stream<InvokeMethod.InvokeRawType> loginOverloadCheck() {
    return Stream.of(
        () -> auth.loginJSONObject(username, password),
        () -> auth.loginJSONObject(username, domain, password),
        () -> auth.loginJSONObject(body),
        () -> auth.loginJSONObject(loginRequest),
        () -> auth.login(username, password),
        () -> auth.login(username, domain, password),
        () -> auth.login(body),
        () -> auth.login(loginRequest)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "loginOverloadCheck")
  void checkLogin(InvokeMethod.InvokeRawType method) {
    System.out.println(method.methodInvoke());
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectOverload")
  void testLoginJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    JSONObject login = method.methodInvoke();
    String token = login.getString("sessionToken");
    String timeout = login.getString("timeout");
    sessionToken = new SessionToken(token, timeout);

    System.out.println(login);
    assertNotNull(auth.getSessionToken());
    assertNotNull(sessionToken);
  }

  @ParameterizedTest
  @MethodSource(value = "loginOverload")
  void testLogin(InvokeMethod.Invoke<SessionToken> method) {
    SessionToken login = method.methodInvoke();
    String token = login.getSessionToken();
    String timeout = login.getTimeout();
    sessionToken = new SessionToken(token, timeout);

    System.out.println(login);
    assertNotNull(auth.getSessionToken());
    assertNotNull(sessionToken);
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectOverload")
  void testPingJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    JSONObject login = method.methodInvoke();
    JSONObject ping = auth.pingJSONObject();
    String token = ping.getString("sessionToken");
    String timeout = ping.getString("timeout");
    sessionToken = new SessionToken(token, timeout);

    assertNotNull(ping.getString("sessionToken"));
    System.out.println(ping);
    assertNotNull(login);
    assertNotNull(auth.getSessionToken());
    assertNotNull(sessionToken);
  }

  @ParameterizedTest
  @MethodSource(value = "loginOverload")
  void testPing(InvokeMethod.Invoke<SessionToken> method) {
    SessionToken login = method.methodInvoke();
    SessionToken ping = auth.ping();
    String token = ping.getSessionToken();
    String timeout = ping.getTimeout();
    sessionToken = new SessionToken(token, timeout);

    System.out.println(ping);
    assertNotNull(login);
    assertNotNull(auth.getSessionToken());
    assertNotNull(sessionToken);
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectOverload")
  void testLogoutJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    JSONObject login = method.methodInvoke();

    assertNotNull(login);
    assertNotNull(auth.getSessionToken());

    JSONObject logout = auth.logoutJSONObject();

    System.out.println(logout);
    assertNotNull(logout);
    assertNull(auth.getSessionToken());
  }

  @ParameterizedTest
  @MethodSource(value = "loginOverload")
  void testLogout(InvokeMethod.Invoke<SessionToken> method) {
    SessionToken login = method.methodInvoke();

    assertNotNull(login);
    assertNotNull(auth.getSessionToken());

    Boolean logout = auth.logout();

    System.out.println(logout);
    assertTrue(logout);
    assertNull(auth.getSessionToken());
  }
}
