package org.example.controller;

import org.example.auxiliary.ConfigReader;
import org.example.connection.Connection;
import org.example.transfer.AccountCode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AuthConnection {

  protected static Connection connection;
  protected static Authentication auth;
  protected static String username;
  protected static String domain;
  protected static AccountCode accountCode;

  @BeforeAll
  public static void beforeAll() {
    connection = new Connection();
    auth = new Authentication(connection);
    username = ConfigReader.getPropertyValue("username");
    domain = ConfigReader.getPropertyValue("domain");
    String password = ConfigReader.getPropertyValue("password");
    JSONObject body = new JSONObject()
        .put("username", username)
        .put("domain", domain)
        .put("password", password);
    auth.loginJSONObject(body);
    accountCode = new AccountCode(username);
  }

  @AfterAll
  public static void afterAll() {
    auth.logoutJSONObject();
  }
}
