package org.example.controller;

import lombok.*;
import org.apache.http.client.methods.HttpGet;
import org.example.auxiliary.Check;
import org.example.auxiliary.JSONParser;
import org.example.connection.Connection;
import org.example.models.users.UserDetailsList;
import org.example.transfer.SessionToken;
import org.example.transfer.users.FullUserName;
import org.example.transfer.users.UserQuery;
import org.json.JSONObject;

@RequiredArgsConstructor
@Data
public class Users {

  @NonNull
  private Connection connection;

  @NonNull
  private SessionToken sessionToken;

  @Setter(value = AccessLevel.PRIVATE)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private JSONObject jsonResponse;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  public static final String USER_DETAILS_KEY = "userDetails";

  public JSONObject getUsersJSONObject(@NonNull String endpoint) {
    return getUsersJSONObject(endpoint, null);
  }

  public JSONObject getUsersJSONObject(@NonNull String endpoint, String ifNoneMatch) {
    jsonResponse = connection.authConnection(endpoint, new HttpGet(), sessionToken, ifNoneMatch);
    return jsonResponse;
  }

  public JSONObject getUserDetailsJSONObject(@NonNull FullUserName userName) {
    return getUserDetailsJSONObject(userName, null, null, null, null, null);
  }

  public JSONObject getUserDetailsJSONObject(@NonNull FullUserName userName, Integer limit, Integer startFrom, String lastUpdateFrom, String lastUpdateTo) {
    return getUserDetailsJSONObject(userName, limit, startFrom, lastUpdateFrom, lastUpdateTo, null);
  }

  public JSONObject getUserDetailsJSONObject(@NonNull FullUserName userName, UserQuery userQuery) {
    return getUserDetailsJSONObject(userName, userQuery, null);
  }

  public JSONObject getUserDetailsJSONObject(@NonNull FullUserName userName, UserQuery userQuery, String ifNoneMatch) {
    return getUserDetailsJSONObject(userName, userQuery.getLimit(), userQuery.getStartFrom(), userQuery.getLastUpdateFrom(), userQuery.getLastUpdateTo(), ifNoneMatch);
  }

  public JSONObject getUserDetailsJSONObject(
      @NonNull FullUserName userName, Integer limit, Integer startFrom, String lastUpdateFrom, String lastUpdateTo,
      String ifNoneMatch
  ) {
    StringBuffer endpoint = new StringBuffer("users/").append(userName.getOwner());
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "last-update-from", lastUpdateFrom);
    Check.endpointQueryType(endpoint, "last-update-to", lastUpdateTo);
    return getUsersJSONObject(endpoint.toString().replaceFirst("&", "?"), ifNoneMatch);
  }

  public UserDetailsList getUserDetails(@NonNull FullUserName userName) {
    return getUserDetails(userName, null, null, null, null, null);
  }

  public UserDetailsList getUserDetails(@NonNull FullUserName userName, Integer limit, Integer startFrom, String lastUpdateFrom, String lastUpdateTo) {
    return getUserDetails(userName, limit, startFrom, lastUpdateFrom, lastUpdateTo, null);
  }

  public UserDetailsList getUserDetails(@NonNull FullUserName userName, UserQuery userQuery) {
    return getUserDetails(userName, userQuery, null);
  }

  public UserDetailsList getUserDetails(@NonNull FullUserName userName, UserQuery userQuery, String ifNoneMatch) {
    return getUserDetails(userName, userQuery.getLimit(), userQuery.getStartFrom(), userQuery.getLastUpdateFrom(), userQuery.getLastUpdateFrom(), ifNoneMatch);
  }

  public UserDetailsList getUserDetails(
      @NonNull FullUserName userName, Integer limit, Integer startFrom, String lastUpdateFrom, String lastUpdateTo,
      String ifNoneMatch
  ) {
    JSONObject result = getUserDetailsJSONObject(userName, limit, startFrom, lastUpdateFrom, lastUpdateTo, ifNoneMatch);
    return JSONParser.jsonObjectParserToClass(result, UserDetailsList.class);
  }

  public JSONObject getUsersDetailsJSONObject() {
    return getUsersDetailsJSONObject(null, null, null, null);
  }

  public JSONObject getUsersDetailsJSONObject(UserQuery userQuery) {
    return getUsersDetailsJSONObject(userQuery.getLimit(), userQuery.getStartFrom(), userQuery.getLastUpdateFrom(), userQuery.getLastUpdateFrom());
  }

  public JSONObject getUsersDetailsJSONObject(Integer limit, Integer startFrom, String lastUpdateFrom, String lastUpdateTo) {
    StringBuffer endpoint = new StringBuffer("users");
    Check.endpointQueryType(endpoint, "limit", limit);
    Check.endpointQueryType(endpoint, "start-from", startFrom);
    Check.endpointQueryType(endpoint, "last-update-from", lastUpdateFrom);
    Check.endpointQueryType(endpoint, "last-update-to", lastUpdateTo);
    return getUsersJSONObject(endpoint.toString().replaceFirst("&", "?"));
  }

  public UserDetailsList getUsersDetails() {
    return getUsersDetails(null, null, null, null);
  }

  public UserDetailsList getUsersDetails(UserQuery userQuery) {
    return getUsersDetails(userQuery.getLimit(), userQuery.getStartFrom(), userQuery.getLastUpdateFrom(), userQuery.getLastUpdateFrom());
  }

  public UserDetailsList getUsersDetails(Integer limit, Integer startFrom, String lastUpdateFrom, String lastUpdateTo) {
    JSONObject result = getUsersDetailsJSONObject(limit, startFrom, lastUpdateFrom, lastUpdateTo);
    return JSONParser.jsonObjectParserToClass(result, UserDetailsList.class);
  }
}
