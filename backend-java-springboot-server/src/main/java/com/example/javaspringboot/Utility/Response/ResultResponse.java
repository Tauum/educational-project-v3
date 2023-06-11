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
  List<Object> results;
  EnumResult enumResult;
  int httpCode;
  public ResultResponse(EnumResult enumResult, int httpCode) {
    this.enumResult = enumResult;
    this.httpCode = httpCode;
  }

  public ResultResponse(Object result, EnumResult enumResult) {
    this.result = result;
    this.enumResult = enumResult;
  }

  public ResultResponse( List<Object> results, EnumResult enumResult) {
    this.results = results;
    this.enumResult = enumResult;
  }

  public ResultResponse(List<Object> results, EnumResult enumResult, int httpCode) {
    this.results = results;
    this.enumResult = enumResult;
    this.httpCode = httpCode;
  }

  public ResultResponse(Object result, EnumResult enumResult, int httpCode) {
    this.result = result;
    this.enumResult = enumResult;
    this.httpCode = httpCode;
  }

  public ResultResponse filterResult(Map.Entry<EnumResult, Object> result) {
    EnumResult key = result.getKey();
    Object value = result.getValue();
    switch (key) {
      case ACCEPTED:
        if (value != null) return new ResultResponse(value, EnumResult.ACCEPTED, 200);
        else return new ResultResponse(EnumResult.ACCEPTED, 200);
      case NOT_FOUND:
      case DOES_NOT_EXIST:
        return new ResultResponse(null, key, 200);
      case ERROR:
        return new ResultResponse(null, EnumResult.ERROR, 500);
      case BAD_REQUEST:
        return new ResultResponse(null, EnumResult.BAD_REQUEST, 400);
      default:
        if (!isNullOrWhitespace(value.toString()))
          return new ResultResponse(null, key, 200);
        break;
    }
    return new ResultResponse(null, EnumResult.UNKNOWN, 500);
  }

  public ResultResponse filterResults(Map.Entry<EnumResult, List> result) {
    EnumResult key = result.getKey();
    Object value = result.getValue();
    switch (key) {
      case ACCEPTED:
        if (value != null)
          return new ResultResponse(value, EnumResult.ACCEPTED, 200);
        break;
      case NOT_FOUND:
      case DOES_NOT_EXIST:
        return new ResultResponse(null, key, 200);
      case ERROR:
        return new ResultResponse(null, EnumResult.ERROR, 500);
      case BAD_REQUEST:
        return new ResultResponse(null, EnumResult.BAD_REQUEST, 400);
      default:
        if (!isNullOrWhitespace(value.toString()))
          return new ResultResponse(null, key, 200);
        break;
    }
    return new ResultResponse(null, EnumResult.UNKNOWN, 500);
  }
}