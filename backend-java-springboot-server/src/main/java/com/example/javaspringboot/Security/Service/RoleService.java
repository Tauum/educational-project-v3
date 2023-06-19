package com.example.javaspringboot.Security.Service;

import static com.example.javaspringboot.Utility.GeneralUtility.isNullOrWhitespace;
import static com.example.javaspringboot.Utility.GeneralUtility.mapResultResponse;

import com.example.javaspringboot.Security.Model.EnumRole;
import com.example.javaspringboot.Security.Model.Role;
import com.example.javaspringboot.Security.Repository.RoleRepository;
import com.example.javaspringboot.Utility.Response.EnumResult;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  RoleRepository roleRepo;

  public RoleService(RoleRepository roleRepo){
    this.roleRepo = roleRepo;
  }

  public Role findRoleByName(EnumRole enumRole){
    Role role = roleRepo.findByName(enumRole);
    if (role == null){
      role = createMissingRole(enumRole);
    }
    return role;
  }

  public Entry<EnumResult, Object> findByName(String enumRole){
    if (isNullOrWhitespace(enumRole)) return mapResultResponse(EnumResult.BAD_REQUEST);
    try {
      EnumRole converted = EnumRole.valueOf(enumRole.toUpperCase());
      Role find = roleRepo.findByName(converted);
      if(find == null) return mapResultResponse(EnumResult.ACCEPTED,createMissingRole(converted));
      else return mapResultResponse(EnumResult.ACCEPTED, find);
    }
    catch(Exception e){ return mapResultResponse(EnumResult.ERROR); }
  }

  public Role createMissingRole(EnumRole enumRole){
    return roleRepo.save(new Role(enumRole));
  }

}