package com.example.javaspringboot.Additional.Model;

import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InformationUtility{

//  public Boolean versionMatch(Long version, L){
//    if (version.equals(this.version)) return true;
//    throw new IllegalStateException("Version missmatch");
//  }

//  public Boolean versionMatch(Long version, String callerClass, String callerMethod, String value){
//    if (version.equals(this.version)) return true;
//    throw new IllegalStateException(String.format(
//        "Version missmatch when {} called {} with value {}",
//        callerClass, callerMethod, value));
//  }
//
//  public Information updateInformation(Long version, String callerClass, String callerMethod, String value) {
//    if(this.creation == null){
//      createInitial();
//    }
//    else if (versionMatch(version, callerClass, callerMethod, value)) {
//      this.setLast_updated(LocalDateTime.now());
//      this.version = this.version + 1;
//    }
//    return this;
//  }
//
//  public Information createInitial(){
//    return new Information(LocalDateTime.now(), null, 0L);
//  }


}
