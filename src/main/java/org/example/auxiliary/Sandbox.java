package org.example.auxiliary;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class Sandbox {

  public static <C> C initSandbox(Class<C> clazz, String url) {
    try {
      for (Field field : clazz.getDeclaredFields()) {
        if (field.isAnnotationPresent(SandboxURL.class)) {
          field.setAccessible(true);
          if (Modifier.isStatic(field.getModifiers())) {
            field.set(null, url);
          } else {
            field.set(clazz, url);
          }
        }
      }
      return clazz.getDeclaredConstructor().newInstance();
    } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
