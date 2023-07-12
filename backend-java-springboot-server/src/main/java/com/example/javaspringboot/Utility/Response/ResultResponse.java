package com.example.javaspringboot.Utility.Response;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

  Object result;
  EnumResult enumResult;
  int httpCode;

  public ResultResponse(EnumResult enumResult, int httpCode) {
    this.enumResult = enumResult;
    this.httpCode = httpCode;
  }

  public ResultResponse filterResult(Map.Entry<EnumResult, Object> result) {
    EnumResult key = result.getKey();
    Object value = result.getValue();
    switch (key) {
      case ACCEPTED:
        if (value != null)
          return new ResultResponse(value, key, 200);
        else
          return new ResultResponse(key, 200);
      case NOT_FOUND, DOES_NOT_EXIST:
        return new ResultResponse(null, key, 200);
      case ERROR, UNKNOWN, UNDETERMINED:
        return new ResultResponse(null, key, 500);
      case BAD_REQUEST:
        return new ResultResponse(null, key, 400);
      case DUPLICATE, ALREADY_EXISTS:
        return new ResultResponse(null, key, 409);
      default:
        try {
          if (!isNullOrWhitespace(value.toString())) return new ResultResponse(value, key, 200);
        } catch (Exception e) {
        }
        return new ResultResponse(null, key, 400);
    }
//    return new ResultResponse(null, EnumResult.UNKNOWN, 500);
  }

  public ResultResponse filterResults(Map.Entry<EnumResult, List> results) {
    EnumResult key = results.getKey();
    Object value = results.getValue();
    switch (key) {
      case ACCEPTED:
        if (value != null)
          return new ResultResponse(value, key, 200);
        else
          return new ResultResponse(key, 200);
      case NOT_FOUND, DOES_NOT_EXIST:
        return new ResultResponse(null, key, 200);
      case ERROR, UNKNOWN, UNDETERMINED:
        return new ResultResponse(null, key, 500);
      case BAD_REQUEST:
        return new ResultResponse(null, key, 400);
      case DUPLICATE, ALREADY_EXISTS:
        return new ResultResponse(null, key, 409);
      default:
        try {
          if (!isNullOrWhitespace(value.toString())) return new ResultResponse(value, key, 200);
        } catch (Exception e) {
        }
        return new ResultResponse(null, key, 400);
    }
  }
}