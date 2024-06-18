package org.example.auxiliary;

import lombok.NonNull;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public final class Check {

  public static void isThrowJSONObject(@NonNull String methodName, JSONObject jsonObject) {
    if (jsonObject == null) {
      throw new IllegalArgumentException(methodName + " JSONObject is null");
    }
  }

  public static void isThrowType(@NonNull String pathName, String pathType) {
    if (pathType == null || pathType.isEmpty()) {
      throw new IllegalArgumentException(pathName + " is null or empty");
    }
  }

  public static void isThrowType(@NonNull String pathName, Integer pathType) {
    if (pathType == null) {
      throw new IllegalArgumentException(pathName + " is null");
    }
  }

  public static void endpointQueryType(@NonNull StringBuffer stringBuffer, @NonNull String queryName, String queryType) {
    if (queryType != null && !queryType.isEmpty()) {
      stringBuffer.append("&")
          .append(queryName)
          .append("=")
          .append(queryType);
    }
  }

  public static void endpointQueryType(@NonNull StringBuffer stringBuffer, @NonNull String queryName, Integer queryType) {
    if (queryType != null) {
      stringBuffer.append("&")
          .append(queryName)
          .append("=")
          .append(queryType);
    }
  }

  public static void endpointQueryType(@NonNull StringBuffer stringBuffer, @NonNull String queryName, Boolean queryType) {
    if (queryType != null) {
      stringBuffer.append("&")
          .append(queryName)
          .append("=")
          .append(false);
    }
  }

  public static void endpointQueryType(@NonNull StringBuffer stringBuffer, @NonNull String queryName, Object queryType) {
    if (queryType != null) {
      stringBuffer.append("&")
          .append(queryName)
          .append("=")
          .append(queryType);
    }
  }

  public static void connectionHeaderType(@NonNull HttpURLConnection connection, @NonNull String headerName, String headerType) {
    if (headerType != null) {
      connection.setRequestProperty(headerName, headerType);
    }
  }

  public static void putBodyType(@NonNull JSONObject body, @NonNull String bodyName, String bodyType) {
    if (bodyType != null && !bodyType.isEmpty()) {
      body.put(bodyName, bodyType);
    }
  }

  public static void putBodyType(@NonNull JSONObject body, @NonNull String bodyName, Number bodyType) {
    if (bodyType != null) {
      body.put(bodyName, bodyType);
    }
  }

  public static void convRateCheck(@NonNull JSONObject jsonObject) {
    try {
      Number convRate = jsonObject.getNumber("convRate");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
