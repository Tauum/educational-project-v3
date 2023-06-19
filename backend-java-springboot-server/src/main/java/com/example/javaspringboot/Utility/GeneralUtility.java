package com.example.javaspringboot.Utility;

import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Utility.Response.ResultResponse;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.komamitsu.fastuuidparser.FastUuidParser;

public class GeneralUtility {
  public static boolean isNullOrWhitespace(String str) {
    return str == null || str.trim().isEmpty();
  }
  public static boolean isValidDateTime(String dateTimeString) {
    if (isNullOrWhitespace(dateTimeString)) return false;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      LocalDateTime.parse(dateTimeString, formatter);
      return true;
    } catch (DateTimeParseException e) { return false; }
  }
  public static boolean isValidInt(int intValue){
    try{
      if (!isNullOrWhitespace(String.valueOf(intValue))) return false;
      int num = Integer.parseInt(String.valueOf(intValue));
      return true;
      // is an integer!
    } catch (NumberFormatException e){ return false;}
  }

  public static boolean isValidBoolean(Boolean booleanValue){
    try {
      if (isNullOrWhitespace(booleanValue.toString())) return false;
      return true;
    }
    catch(Exception e) { }
    return false;
  }
  public static LocalDate convertStringToLocalDate(String localDateString){
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
    return LocalDate.parse(localDateString, formatter);
  }
  public static boolean isString(Object obj) {
    return obj instanceof String;
  }
  public static boolean uuidStringValidityCheck(String uuid2){
    try {
      if (isNullOrWhitespace(uuid2)) return false;
      String standardUuidString = uuid2.replace("uuid2-", "");
       FastUuidParser.fromString(standardUuidString);
      // UUID parsing successful
    } catch (Exception e) {
      // Handle invalid UUID format
      return false;
    }
    return true;
  }
  public static boolean uuidValidityCheck(UUID uuid){
    try {
      if(uuid == null) return false;
      String standardUuidString = uuid.toString().replace("uuid2-", "");
      FastUuidParser.fromString(standardUuidString);
      // UUID parsing successful
    } catch (Exception e) {
      // Handle invalid UUID format
      return false;
    }
    return true;
  }
  public static UUID uuid2StringToUuid(String uuid2){
    String standardUuidString = uuid2.replace("uuid2-", "");

    UUID uuid;
    try {
      return FastUuidParser.fromString(standardUuidString);
      // UUID parsing successful
    } catch (IllegalArgumentException e) {
      // Handle invalid UUID format
      // ...
    }
    return null;
  }

  public static Map.Entry<EnumResult, List> mapResultsResponse(EnumResult enumResult,List<Object> objects){
    return new AbstractMap.SimpleEntry<>(enumResult, objects);
  }
  public static Map.Entry<EnumResult, Object> mapResultResponse(EnumResult enumResult, Object object){
    return new AbstractMap.SimpleEntry<>(enumResult, object);
  }
  public static Map.Entry<EnumResult, Object> mapResultResponse(EnumResult enumResult){
    return new AbstractMap.SimpleEntry<>(enumResult, null);
  }
  public static Map.Entry<EnumResult, List> mapResultsResponse(EnumResult enumResult){
    return new AbstractMap.SimpleEntry<>(enumResult, new ArrayList());
  }


  public static boolean hasNullProperties(Object obj) {
    // Base case: If the object is null, return true
    if (obj == null) {
      return true;
    }

    // Get the object's class
    Class<?> clazz = obj.getClass();

    // Get all declared fields of the class
    Field[] fields = clazz.getDeclaredFields();

    // Iterate through the fields
    for (Field field : fields) {
      // Set the field to be accessible (in case it's private)
      field.setAccessible(true);

      try {
        // Get the value of the field for the given object
        Object value = field.get(obj);

        // If the value is null, return true
        if (value == null) {
          return true;
        }

        // If the value is an object, recursively check its properties
        if (field.getType().isAssignableFrom(Object.class)) {
          if (hasNullProperties(value)) {
            return true;
          }
        }
        if(isString(value) && isNullOrWhitespace((String) value)) return true;
      } catch (IllegalAccessException e) {
        // Handle exception if accessing field is not allowed
        e.printStackTrace();
      }
    }

    // If all properties are non-null, return false
    return false;
  }

}
