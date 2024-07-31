package org.example.controller.user;

import org.example.auxiliary.InvokeMethod;
import org.example.controller.AuthConnection;
import org.example.controller.Users;
import org.example.transfer.users.FullUserName;
import org.example.models.users.UserDetails;
import org.example.models.users.UserDetailsList;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersTest
extends AuthConnection {

  private static Users users;
  private static FullUserName fullUserName;

  @BeforeAll
  public static void beforeAll() {
    AuthConnection.beforeAll();
    users = new Users(connection, auth.getSessionToken());
    fullUserName = new FullUserName(username, domain);
  }

  static Stream<InvokeMethod.Invoke<JSONObject>> methodInvokeJSONObject() {
    return Stream.of(
        () -> users.getUsersJSONObject("users/" + fullUserName.getOwner()),
        () -> users.getUserDetailsJSONObject(fullUserName),
        () -> users.getUsersDetailsJSONObject()
    );
  }

  static Stream<InvokeMethod.Invoke<UserDetailsList>> methodInvoke() {
    return Stream.of(
        () -> users.getUserDetails(fullUserName),
        () -> users.getUsersDetails()
    );
  }

  @ParameterizedTest
  @MethodSource(value = "methodInvokeJSONObject")
  void testUsersJSONObjectOverload(InvokeMethod.Invoke<JSONObject> method) {
    JSONObject jsonObject = method.methodInvoke();

    System.out.println(jsonObject);
    assertNotNull(jsonObject);
  }

  @ParameterizedTest
  @MethodSource(value = "methodInvoke")
  void testUsersOverload(InvokeMethod.Invoke<UserDetailsList> method) {
    UserDetailsList userDetailsList = method.methodInvoke();
    List<UserDetails> userDetails = userDetailsList.getUserDetails();

    userDetails.forEach(System.out::println);
    assertNotNull(userDetailsList);
    assertNotNull(userDetails);
  }
}