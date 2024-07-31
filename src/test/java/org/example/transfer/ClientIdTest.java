package org.example.transfer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ClientIdTest {

  private static ClientId clientId;
  private Method methodCall;
  private String expected;

  @BeforeAll
  static void beforeAll() {
    clientId = new ClientId("test");
  }

  static Stream<Arguments> arguments() {
    return Stream.of(
        Arguments.of("test", "test"),
        Arguments.of("qwertzuiopasdfghjklyxcvbnmqwertzuiopasdfghjklyxcvbnmqwertzuiopasdfghjkl", "qwertzuiopasdfghjklyxcvbnmqwertzuiopasdfghjklyxcvbnmqwertzuio71l"),
        Arguments.of("qwertzuiopasdfghjklyxcvbnmqwertzuiopasdfghjklyxcvbnmqwertzuiopasdfghjklyxcvbnmqwertzuioasdfghjklyxcvbnm", "qwertzuiopasdfghjklyxcvbnmqwertzuiopasdfghjklyxcvbnmqwertzui103m")
    );
  }

  @ParameterizedTest
  @MethodSource(value = "arguments")
  void testSetClientID(String source, String actual) {
    clientId.setClientID(source);
    expected = clientId.getClientID();

    assertNotNull(expected);
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @MethodSource(value = "arguments")
  @SuppressWarnings(value = "rawtypes")
  void testMaxSize64Reflection(String source, String actual) {
    Class[] parameters = new Class[]{String.class};

    assertDoesNotThrow(() -> methodCall = clientId.getClass().getDeclaredMethod("maxSize64", parameters));

    methodCall.setAccessible(true);
    Object[] methodArgument = new Object[]{source};

    assertDoesNotThrow(() -> expected = (String) methodCall.invoke(clientId, methodArgument));
    assertNotNull(expected);
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @MethodSource(value = "arguments")
  void testMaxSize64Powermock(String source, String actual) {
    assertDoesNotThrow(() -> expected = Whitebox.invokeMethod(clientId, "maxSize64", source));
    assertNotNull(expected);
    assertEquals(expected, actual);
  }
}