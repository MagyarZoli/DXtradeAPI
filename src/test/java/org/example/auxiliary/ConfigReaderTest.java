package org.example.auxiliary;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigReaderTest {

  final String PROPERTY = "config_test";
  final String ACTUAL = "configTest";
  final String EXCEPTION_PROPERTY = "config_testException";

  @Test
  void testLoadConfig() {
    Properties properties = ConfigReader.loadConfig();
    assertNotNull(properties);
    String config = properties.getProperty(PROPERTY);
    assertNotNull(config);
    assertEquals(config, ACTUAL);
  }

  @Test
  void testGetPropertyValue() {
    String config = ConfigReader.getPropertyValue(PROPERTY);
    assertNotNull(config);
    assertEquals(config, ACTUAL);
  }

  @Test
  void testExceptionLoadConfig() {
    Properties properties = ConfigReader.loadConfig();
    assertNotNull(properties);
    String config = properties.getProperty(EXCEPTION_PROPERTY);
    assertNull(config);
  }

  @Test
  void testExceptionGetPropertyValue() {
    String config = ConfigReader.getPropertyValue(EXCEPTION_PROPERTY);
    assertNull(config);
  }
}