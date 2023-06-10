package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.EnumRole;
import com.example.javaspringboot.Security.Model.Role;
import com.example.javaspringboot.Security.Repository.RoleRepository;
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

  public Role createMissingRole(EnumRole enumRole){
    return roleRepo.save(new Role(enumRole));
  }

}