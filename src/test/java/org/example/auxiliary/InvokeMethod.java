package org.example.auxiliary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvokeMethod {

  @SafeVarargs
  public static <A, E extends A> List<A> args(E[]... args) {
    List<A> a = new ArrayList<>();
    for (E[] arg : args) {
      Collections.addAll(a, arg);
    }
    return a;
  }

  @FunctionalInterface
  public interface Invoke<R> {
    R methodInvoke();
  }

  @FunctionalInterface
  public interface InvokeRawType {
    Object methodInvoke();
  }
}


