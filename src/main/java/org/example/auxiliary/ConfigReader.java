package org.example.auxiliary;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
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

  public static void initConfigProperties(Object obj) {
    Properties properties = loadConfig();
    if (properties != null) {
      Class<?> clazz = obj.getClass();
      for (Field field : clazz.getDeclaredFields()) {
        if (field.isAnnotationPresent(ConfigProperty.class)) {
          ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
          String key = annotation.value();
          String value = properties.getProperty(key);
          if (value != null) {
            try {
              field.setAccessible(true);
              field.set(obj, value);
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }
}
