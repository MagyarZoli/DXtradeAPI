package org.example.controller.authentication;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.example.auxiliary.InvokeMethod;
import org.example.auxiliary.JSONParser;
import org.example.connection.Connection;
import org.example.controller.Authentication;
import org.example.exception.APIException;
import org.example.models.ModelAssert;
import org.example.transfer.SessionToken;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class AuthenticationMockTest {

  Connection connectionMock;
  String sessionToken;
  String timeout;
  String errorCode;
  String description;

  static Authentication authSpy;

  static final String USERNAME_FAKE = "UserFake";
  static final String DOMAIN_FAKE = "DomainFake";
  static final String PASSWORD_FAKE = "PasswordFake";
  static final JSONObject BODY_FAKE = new JSONObject()
      .put("username", USERNAME_FAKE)
      .put("domain", DOMAIN_FAKE)
      .put("password", PASSWORD_FAKE);
  static final JSONObject SESSION_TOKEN_JSON_OBJECT_FAKE = new JSONObject()
      .put("sessionToken", "0123456789")
      .put("timeout", "00:30:00");
  static final SessionToken SESSION_TOKEN_FAKE = JSONParser.jsonObjectParserToClass(SESSION_TOKEN_JSON_OBJECT_FAKE, SessionToken.class);
  static final JSONObject ERROR_RESPONSE_FAKE = new JSONObject()
      .put("errorCode", "1")
      .put("description", "description message!");

  @BeforeEach
  void setUp() {
    connectionMock = mock(Connection.class);
    authSpy = spy(new Authentication(connectionMock));
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> loginJSONObjectInvoke() {
    return Stream.of(
        () -> authSpy.loginJSONObject(USERNAME_FAKE, PASSWORD_FAKE),
        () -> authSpy.loginJSONObject(USERNAME_FAKE, DOMAIN_FAKE, PASSWORD_FAKE),
        () -> authSpy.loginJSONObject(BODY_FAKE)
    );
  }

  static Stream<InvokeMethod.Invoke<SessionToken>> loginInvoke() {
    return Stream.of(
        () -> authSpy.login(USERNAME_FAKE, PASSWORD_FAKE),
        () -> authSpy.login(USERNAME_FAKE, DOMAIN_FAKE, PASSWORD_FAKE),
        () -> authSpy.login(BODY_FAKE)
    );
  }

  static Stream<InvokeMethod.InvokeRawType> loginAllInvoke() {
    return Stream.of(
        () -> authSpy.loginJSONObject(USERNAME_FAKE, PASSWORD_FAKE),
        () -> authSpy.loginJSONObject(USERNAME_FAKE, DOMAIN_FAKE, PASSWORD_FAKE),
        () -> authSpy.loginJSONObject(BODY_FAKE),
        () -> authSpy.login(USERNAME_FAKE, PASSWORD_FAKE),
        () -> authSpy.login(USERNAME_FAKE, DOMAIN_FAKE, PASSWORD_FAKE),
        () -> authSpy.login(BODY_FAKE)
    );
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testLoginJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    JSONObject login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getString("sessionToken")).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getString("timeout")).doesNotThrowAnyException();
    assertThatCode(() -> errorCode = login.getString("errorCode"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"errorCode\"] not found.");
    assertThatCode(() -> description = login.getString("description"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"description\"] not found.");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).loginJSONObject(any(JSONObject.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login)
        .isNotNull()
        .isSameAs(SESSION_TOKEN_JSON_OBJECT_FAKE);
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testLogin(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    SessionToken login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getSessionToken()).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getTimeout()).doesNotThrowAnyException();
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).login(any(JSONObject.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login).isNotNull();
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
  }

  @ParameterizedTest
  @MethodSource(value = "loginAllInvoke")
  void testLoginAll(InvokeMethod.InvokeRawType method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    var login = method.methodInvoke();

    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login).isNotNull();
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testPingJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    JSONObject login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getString("sessionToken")).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getString("timeout")).doesNotThrowAnyException();
    assertThatCode(() -> errorCode = login.getString("errorCode"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"errorCode\"] not found.");
    assertThatCode(() -> description = login.getString("description"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"description\"] not found.");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).loginJSONObject(any(JSONObject.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login)
        .isNotNull()
        .isSameAs(SESSION_TOKEN_JSON_OBJECT_FAKE);
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    JSONObject ping = authSpy.pingJSONObject();

    assertThatCode(() -> sessionToken = ping.getString("sessionToken")).doesNotThrowAnyException();
    assertThatCode(() -> timeout = ping.getString("timeout")).doesNotThrowAnyException();
    assertThatCode(() -> errorCode = ping.getString("errorCode"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"errorCode\"] not found.");
    assertThatCode(() -> description = ping.getString("description"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"description\"] not found.");
    verify(connectionMock).authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class));
    verify(authSpy).pingJSONObject();
    assertThat(ping).isNotNull();
    ModelAssert.assertThat(authSpy.getSessionToken())
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testPing(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    SessionToken login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getSessionToken()).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getTimeout()).doesNotThrowAnyException();
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).login(any(JSONObject.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login).isNotNull();
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    SessionToken ping = authSpy.ping();

    assertThatCode(() -> sessionToken = ping.getSessionToken()).doesNotThrowAnyException();
    assertThatCode(() -> timeout = ping.getTimeout()).doesNotThrowAnyException();
    verify(connectionMock).authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class));
    verify(authSpy).ping();
    assertThat(ping).isNotNull();
    ModelAssert.assertThat(authSpy.getSessionToken())
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testLogoutJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    JSONObject login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getString("sessionToken")).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getString("timeout")).doesNotThrowAnyException();
    assertThatCode(() -> errorCode = login.getString("errorCode"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"errorCode\"] not found.");
    assertThatCode(() -> description = login.getString("description"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"description\"] not found.");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login)
        .isNotNull()
        .isSameAs(SESSION_TOKEN_JSON_OBJECT_FAKE);
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject());

    JSONObject logout = authSpy.logoutJSONObject();

    verify(connectionMock).authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class));
    assertThat(logout).isNotNull();
    assertThat(authSpy.getSessionToken()).isNull();
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testLogout(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    SessionToken login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getSessionToken()).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getTimeout()).doesNotThrowAnyException();
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login).isNotNull();
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject());

    Boolean logout = authSpy.logout();

    verify(connectionMock).authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class));
    assertThat(logout).isTrue();
    assertThat(authSpy.getSessionToken()).isNull();
  }

  ///EXCEPTION_TEST/////////////////////////////////////////////////////////////////////////////////////////////////////

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testExceptionLoginJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(ERROR_RESPONSE_FAKE);
    assertThatCode(method::methodInvoke)
        .isInstanceOf(APIException.class)
        .hasMessage("1:description message!");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy, never()).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNull();
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testExceptionLogin(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(ERROR_RESPONSE_FAKE);
    assertThatCode(method::methodInvoke)
        .isInstanceOf(APIException.class)
        .hasMessage("1:description message!");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy, never()).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNull();
  }

  @ParameterizedTest
  @MethodSource(value = "loginAllInvoke")
  void testExceptionLoginAll(InvokeMethod.InvokeRawType method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(ERROR_RESPONSE_FAKE);
    assertThatCode(method::methodInvoke)
        .isInstanceOf(APIException.class)
        .hasMessage("1:description message!");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy, never()).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNull();
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testExceptionPingJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    JSONObject login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getString("sessionToken")).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getString("timeout")).doesNotThrowAnyException();
    assertThatCode(() -> errorCode = login.getString("errorCode"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"errorCode\"] not found.");
    assertThatCode(() -> description = login.getString("description"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"description\"] not found.");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).loginJSONObject(any(JSONObject.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login)
        .isNotNull()
        .isSameAs(SESSION_TOKEN_JSON_OBJECT_FAKE);
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class))).thenReturn(ERROR_RESPONSE_FAKE);

    JSONObject ping = authSpy.pingJSONObject();

    verify(connectionMock).authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class));
    assertThat(ping).isNotNull();
    assertThatCode(() -> sessionToken = ping.getString("sessionToken"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"sessionToken\"] not found.");
    assertThatCode(() -> timeout = ping.getString("timeout"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"timeout\"] not found.");
    assertThatCode(() -> errorCode = ping.getString("errorCode")).doesNotThrowAnyException();
    assertThatCode(() -> description = ping.getString("description")).doesNotThrowAnyException();
    assertThat(errorCode)
        .isNotNull()
        .isEqualTo(ERROR_RESPONSE_FAKE.getString("errorCode"));
    assertThat(description)
        .isNotNull()
        .isEqualTo(ERROR_RESPONSE_FAKE.getString("description"));
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testExceptionPing(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    SessionToken login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getSessionToken()).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getTimeout()).doesNotThrowAnyException();
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    verify(authSpy).login(any(JSONObject.class));
    verify(authSpy).setSessionToken(any(SessionToken.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login).isNotNull();
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class))).thenReturn(ERROR_RESPONSE_FAKE);

    assertThatCode(authSpy::ping).isInstanceOf(JSONException.class);
    verify(connectionMock).authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class));
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testExceptionLogoutJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    JSONObject login = method.methodInvoke();

    assertThatCode(() -> sessionToken = login.getString("sessionToken")).doesNotThrowAnyException();
    assertThatCode(() -> timeout = login.getString("timeout")).doesNotThrowAnyException();
    assertThatCode(() -> errorCode = login.getString("errorCode"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"errorCode\"] not found.");
    assertThatCode(() -> description = login.getString("description"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"description\"] not found.");
    verify(connectionMock).connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class));
    assertThat(authSpy.getSessionToken()).isNotNull();
    assertThat(login)
        .isNotNull()
        .isSameAs(SESSION_TOKEN_JSON_OBJECT_FAKE);
    ModelAssert.assertThat(SESSION_TOKEN_FAKE)
        .hasValueIsEqualTo("sessionToken", sessionToken)
        .hasValueIsEqualTo("timeout", timeout);
    when(connectionMock.authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(ERROR_RESPONSE_FAKE);

    JSONObject logout = authSpy.logoutJSONObject();

    verify(connectionMock).authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class));
    assertThat(logout).isNotNull();
    assertThatCode(() -> sessionToken = logout.getString("sessionToken"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"sessionToken\"] not found.");
    assertThatCode(() -> timeout = logout.getString("timeout"))
        .isInstanceOf(JSONException.class)
        .hasMessage("JSONObject[\"timeout\"] not found.");
    assertThatCode(() -> errorCode = logout.getString("errorCode")).doesNotThrowAnyException();
    assertThatCode(() -> description = logout.getString("description")).doesNotThrowAnyException();
    assertThat(errorCode)
        .isNotNull()
        .isEqualTo(ERROR_RESPONSE_FAKE.getString("errorCode"));
    assertThat(description)
        .isNotNull()
        .isEqualTo(ERROR_RESPONSE_FAKE.getString("description"));
    assertThat(authSpy.getSessionToken()).isNull();
  }
}
