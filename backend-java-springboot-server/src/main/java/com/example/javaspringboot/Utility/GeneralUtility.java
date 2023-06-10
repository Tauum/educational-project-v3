package com.example.javaspringboot.Utility;

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

}
