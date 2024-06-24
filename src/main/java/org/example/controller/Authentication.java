package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpPost;
import org.example.connection.Connection;
import org.example.exception.APIException;
import org.example.transfer.SessionToken;
import org.example.transfer.authentication.LoginRequest;
import org.json.JSONException;
import org.json.JSONObject;

@RequiredArgsConstructor
@Data
public class Authentication {

  @NonNull
  private Connection connection;

  private SessionToken sessionToken;

  @Setter(value = AccessLevel.PRIVATE)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private JSONObject jsonResponse;

  public JSONObject loginJSONObject(@NonNull final String username, @NonNull final String password) {
    JSONObject login = new JSONObject()
        .put("username", username)
        .put("domain", "default")
        .put("password", password);
    return loginJSONObject(login);
  }

  public JSONObject loginJSONObject(@NonNull final String username, final String domain, @NonNull final String password) {
    JSONObject login = new JSONObject()
        .put("username", username)
        .put("domain", domain)
        .put("password", password);
    return loginJSONObject(login);
  }

  public JSONObject loginJSONObject(@NonNull LoginRequest loginRequest) {
    return loginJSONObject(loginRequest.getLoginRequestJSONObject());
  }

  public JSONObject loginJSONObject(@NonNull JSONObject login) {
    if (sessionToken == null) {
      jsonResponse = connection.connection("login", new HttpPost(), login.toString());
      if (jsonResponse != null) {
        System.out.println(jsonResponse.toString());
        try {
          String sessionToken = jsonResponse.getString("sessionToken");
          String timeout = jsonResponse.getString("timeout");
          //setSessionToken(new SessionToken(jsonResponse.getString("sessionToken"), jsonResponse.getString("timeout")));
          setSessionToken(new SessionToken(sessionToken, timeout));
        } catch (JSONException e) {
          String errorCode = jsonResponse.getString("errorCode");
          String description = jsonResponse.getString("description");
          throw new APIException(errorCode, description);
        }
      }
      return jsonResponse;
    }
    return new JSONObject()
        .put("sessionToken", sessionToken.getSessionToken())
        .put("timeout", sessionToken.getTimeout());
  }

  public SessionToken login(@NonNull final String username, @NonNull final String password) {
    JSONObject login = new JSONObject()
        .put("username", username)
        .put("domain", "default")
        .put("password", password);
    return login(login);
  }

  public SessionToken login(@NonNull final String username, String domain, @NonNull final String password) {
    if (domain == null || domain.isBlank() || domain.isEmpty()) {
      domain = "default";
    }
    JSONObject login = new JSONObject()
        .put("username", username)
        .put("domain", domain)
        .put("password", password);
    return login(login);
  }

  public SessionToken login(@NonNull LoginRequest loginRequest) {
    return login(loginRequest.getLoginRequestJSONObject());
  }

  public SessionToken login(@NonNull JSONObject login) {
    jsonResponse = loginJSONObject(login);
    if (jsonResponse == null) {
      return null;
    }
    return new SessionToken(jsonResponse.getString("sessionToken"), jsonResponse.getString("timeout"));
  }

  public JSONObject logoutJSONObject() {
    if (sessionToken != null) {
      jsonResponse = connection.authConnection("logout", new HttpPost(), sessionToken);
      sessionToken = null;
      return jsonResponse;
    }
    jsonResponse.put("errorCode", 401);
    jsonResponse.put("description", "Authorization required. You are currently not logged in.");
    return jsonResponse;
  }

  public Boolean logout() {
    if (sessionToken != null) {
      jsonResponse = connection.authConnection("logout", new HttpPost(), sessionToken);
      sessionToken = null;
      return true;
    }
    return false;
  }

  public JSONObject pingJSONObject() {
    jsonResponse = connection.authConnection("ping", new HttpPost(), sessionToken);
    return jsonResponse;
  }

  public SessionToken ping() {
    jsonResponse = pingJSONObject();
    setSessionToken(new SessionToken(jsonResponse.getString("sessionToken"), jsonResponse.getString("timeout")));
    return getSessionToken();
  }

  public JSONObject pingJSONObject(SessionToken sessionToken) {
    jsonResponse = connection.authConnection("ping", new HttpPost(), sessionToken);
    return jsonResponse;
  }

  public SessionToken ping(SessionToken sessionToken) {
    jsonResponse = pingJSONObject();
    setSessionToken(new SessionToken(jsonResponse.getString("sessionToken"), jsonResponse.getString("timeout")));
    return getSessionToken();
  }
}
