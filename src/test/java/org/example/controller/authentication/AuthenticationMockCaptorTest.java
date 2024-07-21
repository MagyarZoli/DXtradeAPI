package org.example.controller.authentication;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.example.auxiliary.InvokeMethod;
import org.example.transfer.SessionToken;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class AuthenticationMockCaptorTest
extends AuthenticationMockTest {

  final ArgumentCaptor<String> endpointCaptor = ArgumentCaptor.forClass(String.class);
  final ArgumentCaptor<HttpEntityEnclosingRequestBase> httpEntityEnclosingRequestBaseCaptor = ArgumentCaptor.forClass(HttpEntityEnclosingRequestBase.class);
  final ArgumentCaptor<HttpRequestBase> httpRequestBaseCaptor = ArgumentCaptor.forClass(HttpRequestBase.class);
  final ArgumentCaptor<JSONObject> jsonObjectCaptor = ArgumentCaptor.forClass(JSONObject.class);
  final ArgumentCaptor<SessionToken> sessionTokenCaptor = ArgumentCaptor.forClass(SessionToken.class);

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testCaptorLoginJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testCaptorLogin(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
  }

  @ParameterizedTest
  @MethodSource(value = "loginAllInvoke")
  void testCaptorLoginAll(InvokeMethod.InvokeRawType method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testCaptorPingJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
    when(connectionMock.authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    authSpy.pingJSONObject();

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("ping");
    assertThat(httpEntityEnclosingRequestBaseCaptor).isNotNull();
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(authSpy.getSessionToken());
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testCaptorPing(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
    when(connectionMock.authConnection(eq("ping"), any(HttpEntityEnclosingRequestBase.class), any(SessionToken.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    authSpy.pingJSONObject();

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("ping");
    assertThat(httpEntityEnclosingRequestBaseCaptor).isNotNull();
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(authSpy.getSessionToken());
  }

  @ParameterizedTest
  @MethodSource(value = "loginJSONObjectInvoke")
  void testCaptorLogoutJSONObject(InvokeMethod.Invoke<JSONObject> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
    when(connectionMock.authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject());

    authSpy.logoutJSONObject();

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("logout");
    assertThat(httpRequestBaseCaptor.getValue()).isNotNull();
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isNotEqualTo(authSpy.getSessionToken());
  }

  @ParameterizedTest
  @MethodSource(value = "loginInvoke")
  void testCaptorLogout(InvokeMethod.Invoke<SessionToken> method) {
    when(connectionMock.connection(eq("login"), any(HttpEntityEnclosingRequestBase.class), any(String.class))).thenReturn(SESSION_TOKEN_JSON_OBJECT_FAKE);

    method.methodInvoke();

    verify(connectionMock).connection(endpointCaptor.capture(), httpEntityEnclosingRequestBaseCaptor.capture(), any(String.class));
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("login");
    assertThat(httpEntityEnclosingRequestBaseCaptor.getValue()).isNotNull();
    verify(authSpy).loginJSONObject(jsonObjectCaptor.capture());
    assertThat(jsonObjectCaptor.getValue()).isNotNull();
    verify(authSpy).setSessionToken(sessionTokenCaptor.capture());
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isEqualTo(SESSION_TOKEN_FAKE);
    when(connectionMock.authConnection(eq("logout"), any(HttpRequestBase.class), any(SessionToken.class))).thenReturn(new JSONObject());

    authSpy.logout();

    verify(connectionMock).authConnection(endpointCaptor.capture(), httpRequestBaseCaptor.capture(), sessionTokenCaptor.capture());
    assertThat(endpointCaptor.getValue())
        .isNotNull()
        .isEqualTo("logout");
    assertThat(httpRequestBaseCaptor.getValue()).isNotNull();
    assertThat(sessionTokenCaptor.getValue())
        .isNotNull()
        .isNotEqualTo(authSpy.getSessionToken());
  }
}
