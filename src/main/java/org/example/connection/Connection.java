package org.example.connection;

import lombok.NonNull;
import org.example.auxiliary.HttpMethod;
import org.example.controller.SessionToken;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

  public static final String BASE_URL = "https://dxtrade.ftmo.com/dxsca-web/";

  public static HttpURLConnection connection(@NonNull String endpoint, @NonNull HttpMethod method) {
    try {
      URL url = new URL(BASE_URL + endpoint);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(method.toString());
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoOutput(true);
      return connection;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static HttpURLConnection authConnection(@NonNull String endpoint, @NonNull HttpMethod method, SessionToken sessionToken) {
    HttpURLConnection connection = connection(endpoint, method);
    connection.setRequestProperty("Authorization", "DXAPI " + sessionToken.getSessionToken());
    return connection;
  }

  public static void outputStream(@NonNull JSONObject body, HttpURLConnection connection) {
    try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
      outputStream.write(body.toString().getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static JSONObject getResponse(HttpURLConnection connection) {
    try {
      JSONObject jsonResponse = null;
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
          jsonResponse = new JSONObject(responseAppend(reader));
        }
      }
      return jsonResponse;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Integer getResponseCode(HttpURLConnection connection) {
    try {
      return connection.getResponseCode();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String responseAppend(@NonNull BufferedReader reader) throws IOException {
    StringBuilder result = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
      result.append(line);
    }
    return result.toString();
  }
}
