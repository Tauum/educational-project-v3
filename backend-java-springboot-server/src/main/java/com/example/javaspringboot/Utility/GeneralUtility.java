package com.example.javaspringboot.Utility;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class GeneralUtility {

  public static UUID uuid2StringToUuid(String uuid2){
    String standardUuidString = uuid2.replace("uuid2-", "");

    UUID uuid;
    try {
      return UUID.fromString(standardUuidString);
      // UUID parsing successful
    } catch (IllegalArgumentException e) {
      // Handle invalid UUID format
      // ...
    }
    return null;
  }

//  public static EnumResult filterRequestBody(String id){
//    if (id != null && !id.isBlank())
//  }

  public static boolean isNullOrWhitespace(String str) {
    return str == null || str.trim().isEmpty();
  }

  public static boolean isValidDateTime(String dateTimeString) {
    if (isNullOrWhitespace(dateTimeString)) return false;
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      LocalDateTime.parse(dateTimeString, formatter);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static LocalDate convertStringToLocalDate(String localDateString){
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
    return LocalDate.parse(localDateString, formatter);
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

  public static boolean isString(Object obj) {
    return obj instanceof String;
  }
}
