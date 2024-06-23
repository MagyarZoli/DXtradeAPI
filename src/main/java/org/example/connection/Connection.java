package org.example.connection;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.transfer.SessionToken;
import org.example.exception.APIException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

@NoArgsConstructor
@Data
@Log4j2
public class Connection {

  private String baseUrl = "https://dxtrade.ftmo.com/dxsca-web/";

  @Setter(value = AccessLevel.PRIVATE)
  private Integer statusCode;

  public JSONObject connection(@NonNull String endpoint, @NonNull HttpRequestBase httpRequestBase) {
    return conn(endpoint, httpRequestBase, null);
  }

  public JSONObject connection(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, @NonNull JSONObject requestBody) {
    return connection(endpoint, httpRequestBase, requestBody.toString());
  }

  public JSONObject connection(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, @NonNull String requestBody) {
    return connection(endpoint, httpRequestBase, new StringEntity(requestBody, ContentType.APPLICATION_JSON));
  }

  public JSONObject connection(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, @NonNull HttpEntity requestBody) {
    return conn(endpoint, httpRequestBase, null, requestBody);
  }

  public JSONObject authConnection(@NonNull String endpoint, @NonNull HttpRequestBase httpRequestBase, @NonNull SessionToken sessionToken) {
    return conn(endpoint, httpRequestBase, sessionToken);
  }

  public JSONObject authConnection(@NonNull String endpoint, @NonNull HttpRequestBase httpRequestBase, @NonNull SessionToken sessionToken, String ifNoneMatch) {
    return conn(endpoint, httpRequestBase, sessionToken, ifNoneMatch);
  }

  public JSONObject authConnection(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, @NonNull SessionToken sessionToken, JSONObject requestBody) {
    return authConnection(endpoint, httpRequestBase, sessionToken, requestBody.toString());
  }

  public JSONObject authConnection(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, @NonNull SessionToken sessionToken, String requestBody) {
    return authConnection(endpoint, httpRequestBase, sessionToken, new StringEntity(requestBody, ContentType.APPLICATION_JSON));
  }

  public JSONObject authConnection(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, @NonNull SessionToken sessionToken, HttpEntity requestBody) {
    return conn(endpoint, httpRequestBase, sessionToken, requestBody);
  }

  private JSONObject conn(@NonNull String endpoint, @NonNull HttpRequestBase httpRequestBase, SessionToken sessionToken) {
    return conn(endpoint, httpRequestBase, sessionToken, null);
  }

  private JSONObject conn(@NonNull String endpoint, @NonNull HttpRequestBase httpRequestBase, SessionToken sessionToken, String ifNoneMatch) {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      httpRequestBase.setURI(URI.create(baseUrl + endpoint));
      httpRequestBase.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
      httpRequestBase.setHeader(HttpHeaders.ACCEPT, "application/json");
      if (sessionToken != null) {
        httpRequestBase.setHeader(HttpHeaders.AUTHORIZATION, "DXAPI " + sessionToken.getSessionToken());
      }
      if (ifNoneMatch != null) {
        httpRequestBase.setHeader(HttpHeaders.IF_NONE_MATCH, ifNoneMatch);
      }
      HttpResponse response = httpClient.execute(httpRequestBase);
      statusCode = response.getStatusLine().getStatusCode();
      HttpEntity responseEntity = response.getEntity();
      if (responseEntity != null) {
        log.info(statusCode);
        JSONObject jsonResponse;
        try {
          jsonResponse = new JSONObject(EntityUtils.toString(responseEntity));
          try {
            String errorCode = jsonResponse.getString("errorCode");
            String description = jsonResponse.getString("description");
            throw new APIException(errorCode + " " + description);
          } catch (Exception ignore) {
            return jsonResponse;
          }
        } catch (Exception e2) {
          throw new APIException(e2);
        }
      }
      throw new APIException("responseEntity is null");
    } catch (Exception e) {
      log.warn(e);
      throw new APIException(e);
    }
  }

  private JSONObject conn(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, SessionToken sessionToken, HttpEntity requestBody) {
    return conn(endpoint, httpRequestBase, sessionToken, requestBody, null);
  }

  private JSONObject conn(@NonNull String endpoint, @NonNull HttpEntityEnclosingRequestBase httpRequestBase, SessionToken sessionToken, HttpEntity requestBody, String ifNoneMatch) {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      httpRequestBase.setURI(URI.create(baseUrl + endpoint));
      httpRequestBase.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
      httpRequestBase.setHeader(HttpHeaders.ACCEPT, "application/json");

      if (sessionToken != null) {
        httpRequestBase.setHeader(HttpHeaders.AUTHORIZATION, "DXAPI " + sessionToken.getSessionToken());
      }
      if (ifNoneMatch != null) {
        httpRequestBase.setHeader(HttpHeaders.IF_NONE_MATCH, ifNoneMatch);
      }
      if (requestBody != null) {
        httpRequestBase.setEntity(requestBody);
      }

      HttpResponse response = httpClient.execute(httpRequestBase);
      int statusCode = response.getStatusLine().getStatusCode();

      if (statusCode < 200 || statusCode >= 300) {
        throw new APIException("Unexpected status code: " + statusCode);
      }

      HttpEntity responseEntity = response.getEntity();
      if (responseEntity == null) {
        throw new APIException("Response entity is null");
      }

      JSONObject jsonResponse = new JSONObject(EntityUtils.toString(responseEntity));

      if (jsonResponse.has("errorCode")) {
        String errorCode = jsonResponse.getString("errorCode");
        String description = jsonResponse.getString("description");
        throw new APIException("Error: " + errorCode + " - " + description);
      }
      return jsonResponse;
    } catch (JSONException e) {
      throw new APIException("JSON parsing error: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new APIException("I/O error: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new APIException("Unexpected error: " + e.getMessage(), e);
    }
  }
}
