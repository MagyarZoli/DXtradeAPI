package org.example.auxiliary;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigPropertyTest {

  @Data
  public static class Example {

    @ConfigProperty(value = "username")
    private String usernameValue;
  }

  @Test
  void testAnnotation() {
    Example example = new Example();
    ConfigReader.initConfigProperties(example);
    String usernameReader = ConfigReader.getPropertyValue("username");

    assertNotNull(example.getUsernameValue());
    assertNotNull(usernameReader);
    assertEquals(example.getUsernameValue(), usernameReader);
  }
}
