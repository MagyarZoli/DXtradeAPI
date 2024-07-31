package org.example.auxiliary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.models.Model;
import org.example.models.ModelAssert;
import org.example.transfer.JSONObjectAssert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JSONParserTest {

  static JSONObject jsonObject;
  static JSONArray jsonArray;
  static JSONObject request;

  @BeforeAll
  static void beforeAll() {
    jsonObject = new JSONObject();
    jsonObject.put("key", 1);
    jsonObject.put("value", 11);

    jsonArray = new JSONArray();
    for (int i = 1; i < 4; i++) {
      JSONObject obj = new JSONObject();
      obj.put("key", 1 + i);
      obj.put("value", 11 * i);
      jsonArray.put(obj);
    }

    request = new JSONObject();
    request.put("request", jsonArray);
  }

  @Test
  void testJSONObjectParseToJSONArray() {
    JSONArray array = JSONParser.jsonObjectParseToJSONArray(request, "request");
    System.out.println(array);
  }

  @Test
  void testJSONObjectParserToClass() {
    TestClass testClass = JSONParser.jsonObjectParserToClass(jsonObject, TestClass.class);
    System.out.println(testClass.toString());
  }

  @Test
  void testJSONArrayParserToClass() {
    List<TestClass> testClass = JSONParser.jsonObjectArrayParserToClass(request, "request", TestClass.class);
    for (TestClass clazz : testClass) {
      System.out.println(clazz.toString());
    }
  }

  @Test
  void testClassToJSONObject() {
    TestClass testClass = new TestClass("Key", "Value");
    JSONObject testClassJSONObject = testClass.parseToJSONObject();

    System.out.println(testClassJSONObject);
    JSONObjectAssert.assertThat(testClassJSONObject)
        .hasValueIsEqualTo("key", "Key")
        .hasValueIsEqualTo("value", "Value");
    ModelAssert.assertThat(testClass)
        .hasValueIsEqualTo("key", "Key")
        .hasValueIsEqualTo("value", "Value");

    TestClassList testClassList = new TestClassList(List.of(testClass), 1);
    JSONObject testClassListJSONObject = testClassList.parseToJSONObject();

    System.out.println(testClassListJSONObject);
    JSONObjectAssert.assertThat(testClassListJSONObject)
        .hasValueIsArray("testClasses")
        .hasValueIsEqualTo("version", 1);
    ModelAssert.assertThat(testClassList)
        .hasValueIsEqualTo("version", 1);
  }

  @Data
  static class TestClass
  implements Model {

    private String key;
    private String value;

    @JsonCreator
    public TestClass(
        @JsonProperty(value = "key") String key,
        @JsonProperty(value = "value") String value
    ) {
      this.key = key;
      this.value = value;
    }
  }

  @Data
  static class TestClassList
  implements Model {

    private List<TestClass> testClasses;
    private Number version;

    @JsonCreator
    public TestClassList(
        @JsonProperty(value = "testClasses") List<TestClass> testClasses,
        @JsonProperty(value = "version") Number version
    ) {
      this.testClasses = testClasses;
      this.version = version;
    }
  }
}