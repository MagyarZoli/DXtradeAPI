package org.example.auxiliary;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLCoder {

  public static String urlEncode(String accountCode) {
    return URLEncoder.encode(accountCode, StandardCharsets.UTF_8);
  }

  public static String urlDecode(String encodedAccountCode) {
    return URLDecoder.decode(encodedAccountCode, StandardCharsets.UTF_8);
  }
}
