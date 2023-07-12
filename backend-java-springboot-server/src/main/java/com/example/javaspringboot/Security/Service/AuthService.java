package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.MyUserDetails;
import com.example.javaspringboot.Security.Repository.RoleRepository;
import com.example.javaspringboot.Security.Request.JwtUtils;
import com.example.javaspringboot.Security.Request.LoginRequest;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Model.User;
import com.example.javaspringboot.User.Model.UserProfile;
import com.example.javaspringboot.User.Service.UserService;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Utility.Response.LoginResponse;
import com.example.javaspringboot.Utility.Response.RegisterResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  UserService userService;

  @Autowired
  RoleRepository roleRepository;
  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  CredentialsLoginService credentialsLoginService;

  public LoginResponse  authenticateUserSignIn(LoginRequest loginRequest) {

    User user = new User(loginRequest.getEmail(), loginRequest.getPassword());

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getCredentials().getCurrentEmail(),
                user.getCredentials().getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

    List<String> roles = myUserDetails.getAuthorities()
        .stream().map(item -> item.getAuthority()).collect(Collectors.toList());

    return filterLogin(myUserDetails);
  }

  // TODO : PREVENT 200 WHEN ALREADY LOGGED OUT
  public LoginResponse whoAmI(HttpServletRequest request){

    String jwt = jwtUtils.getJwtFromCookies(request);
    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
      MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return filterLogin(userDetails);
    }
    return logout();
  }

  public RegisterResponse register(@RequestBody Registration attempt){
    return filterRegister(userService.addUser(attempt));
  }

  // TODO : PREVENT 200 WHEN ALREADY LOGGED OUT
  public LoginResponse logout() {
    SecurityContextHolder.clearContext();
    return new LoginResponse(
        jwtUtils.getCleanJwtCookie(),
        null,
        EnumResult.NOT_LOGGED_IN,
        200);
  }

  public LoginResponse filterLogin(MyUserDetails myUserDetails){
    if (myUserDetails.getEmail() != null || !myUserDetails.getEmail().isBlank()) {
      UserProfile userProfile = userService.
          findUserProfilebyMyUserDetails(myUserDetails.getCredentials().getId());
      if (userProfile != null) {
        if (!userProfile.getCredentials().isEnabled()){
          credentialsLoginService.createCredentialsLogin(myUserDetails.getEmail(),EnumResult.DISABLED);
          return new LoginResponse(
              null,
              null,
              EnumResult.DISABLED,
              403);
        }
        else if (userProfile.getCredentials().isExpired()){
          credentialsLoginService.createCredentialsLogin(myUserDetails.getEmail(),EnumResult.CREDENTIALS_EXPIRED);
          return new LoginResponse(
              null,
              null,
              EnumResult.CREDENTIALS_EXPIRED,
              403);
        }
        else {
          credentialsLoginService.createCredentialsLogin(myUserDetails.getEmail(),EnumResult.ACCEPTED);
          return new LoginResponse(
              jwtUtils.generateJwtCookie(myUserDetails),
              userProfile,
              EnumResult.ACCEPTED,
              200);
        }
      }
    }
    credentialsLoginService.createCredentialsLogin( myUserDetails.getEmail(),EnumResult.NOT_FOUND);
    return new LoginResponse(null,null,EnumResult.UNKNOWN,400);
  }

  public LoginResponse rejectLogin(LoginRequest loginRequest){
    credentialsLoginService.createCredentialsLogin(loginRequest.getEmail(),EnumResult.NOT_FOUND);
    return new LoginResponse(null,null,EnumResult.NOT_FOUND,400);
  }

  public RegisterResponse filterRegister(EnumResult enumResult){

    if(enumResult == EnumResult.ACCEPTED) return new RegisterResponse(
        enumResult,
        200
    );
    else if(enumResult == null) return new RegisterResponse(
        EnumResult.UNKNOWN,
        500
    );
    else{
      return new RegisterResponse(
          enumResult,
          400
      );
    }
  }

  public static class RoleService {

  }
}
