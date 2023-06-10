package com.example.javaspringboot.Utility.Response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public class ResultResponse {
    List<Object> results;
    Object result;
    EnumResult enumResult;
    int httpCode;

    public ResultResponse(Object result, EnumResult enumResult, int httpCode) {
      this.result = result;
      this.enumResult = enumResult;
      this.httpCode = httpCode;
    }
    public ResultResponse(List<Object> results, EnumResult enumResult, int httpCode) {
      this.results = results;
      this.enumResult = enumResult;
      this.httpCode = httpCode;
    }

    public ResultResponse(EnumResult enumResult, int httpCode) {
      this.enumResult = enumResult;
      this.httpCode = httpCode;
    }

    public ResultResponse filterListResults(List<Object> results) {
        if (results != null) {
          if (result == null) return new ResultResponse(results, EnumResult.ACCEPTED, 200);
          else return new ResultResponse(results, result, EnumResult.ACCEPTED, 200);
        }
      return new ResultResponse(EnumResult.NOT_FOUND, 400);
    }

    public ResultResponse filterListResult(Object result, EnumResult enumResult) {
      if (enumResult == EnumResult.ACCEPTED) {
        if (result != null) return new ResultResponse(result, EnumResult.ACCEPTED, 200);
      } else if (enumResult == EnumResult.ERROR) {
        return new ResultResponse(null, EnumResult.ERROR, 500);
      } else if (enumResult == EnumResult.NOT_FOUND || enumResult == EnumResult.DOES_NOT_EXIST) {
        return new ResultResponse( null, enumResult, 400);
      }
      return new ResultResponse( null, EnumResult.DOES_NOT_EXIST, 400);
    }

    public ResultResponse filterListResults(List<Object> results, EnumResult enumResult) {
      if (enumResult == EnumResult.ACCEPTED) {
        if (results != null) {
          if (result == null) {
            return new ResultResponse(results, EnumResult.ACCEPTED, 200);
          } else {
            return new ResultResponse(results, result, EnumResult.ACCEPTED, 200);
          }
        }
      } else if (enumResult == EnumResult.ERROR) {
        return new ResultResponse(null, null, EnumResult.ERROR, 500);
      } else if (enumResult == EnumResult.NOT_FOUND || enumResult == EnumResult.DOES_NOT_EXIST) {
        return new ResultResponse(null, null, enumResult, 400);
      }
      return new ResultResponse(null, null, EnumResult.DOES_NOT_EXIST, 400);
    }

}