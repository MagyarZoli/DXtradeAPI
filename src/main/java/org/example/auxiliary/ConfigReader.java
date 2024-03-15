package org.example.auxiliary;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

  private static final String CONFIG_FILE = "config.properties";

  public static Properties loadConfig() {
    Properties result = new Properties();
    try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      if (input == null) {
        return null;
      }
      result.load(input);
      return result;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String getPropertyValue(@NonNull String key) {
    Properties properties = loadConfig();
    if (properties != null) {
      String result = properties.getProperty(key);
      if (result != null) return result;
    }
    return null;
  }
}
