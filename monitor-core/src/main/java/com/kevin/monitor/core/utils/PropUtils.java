package com.kevin.monitor.core.utils;

import java.util.Map;

public class PropUtils {

  public static String getPropValue(String value, Map<Object, Object> context) {
    if (value == null){
      throw new IllegalArgumentException("value can't be null");
    }

    if (context == null){
      throw new IllegalArgumentException("context can't be null");
    }

    int p = value.lastIndexOf('}');
    while (p != -1){
      int p1 = value.lastIndexOf("${");
      if (p1 == -1){
        return value;
      }
      
      String key = value.substring(p1 + 2, p); //+2 是跳过${
      if (!context.containsKey(key)){
        throw new RuntimeException("variable " + key + " not found");
      }
      String replaceValue = String.valueOf(context.get(key));
      String start = value.substring(0, p1);
      String end = value.substring(p + 1);
      value = start + replaceValue + end;
      p = value.lastIndexOf('}');
    }
    return value;
  }
}
