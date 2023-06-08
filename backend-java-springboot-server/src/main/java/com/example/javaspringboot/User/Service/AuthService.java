package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.Security.Request.JwtUtils;
import com.example.javaspringboot.Security.Response.LoginResponse;
import com.example.javaspringboot.Security.Response.RegisterResponse;
import com.example.javaspringboot.Security.Response.EnumResult;
import com.example.javaspringboot.Security.Request.LoginRequest;
import com.example.javaspringboot.User.Model.MyUserDetails;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Model.User;
import com.example.javaspringboot.User.Model.UserProfile;
import com.example.javaspringboot.User.Repository.RoleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  PasswordEncoder encoder;
  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  CredentialsLoginService credentialsLoginService;

  public LoginResponse authenticateUserSignIn(LoginRequest loginRequest) {

    User user = new User(loginRequest.getEmail(), loginRequest.getPassword());

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getCredentials().getCurrentEmail(),
                user.getCredentials().getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(
        Collectors.toList());

    return filterLogin(userDetails);
  }

  public LoginResponse whoAmI(HttpServletRequest request){

    String jwt = jwtUtils.getJwtFromCookies(request);
    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
      MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return filterLogin(userDetails);
    }
    return null;
  }

  public RegisterResponse register(@RequestBody Registration attempt){
    return filterRegister(userService.addUser(attempt));
  }

  public LoginResponse logout() {
    SecurityContextHolder.clearContext();
    return new LoginResponse(
        jwtUtils.getCleanJwtCookie(),
        null,
        EnumResult.NOT_LOGGED_IN,
        200);
  }

  public LoginResponse filterLogin(MyUserDetails userDetails){
    if (userDetails.getEmail() != null || !userDetails.getEmail().isBlank()) {
      UserProfile userProfile = userService.findUserProfileUserByCurrentEmail(userDetails.getEmail());
      if (userProfile != null) {
        if (!userProfile.isEnabled()){
          credentialsLoginService.createCredentialsLogin(userDetails.getEmail(),EnumResult.DISABLED);
          return new LoginResponse(
              null,
              null,
              EnumResult.DISABLED,
              403);
        }
        else if (userProfile.isCredentialsExpired()){
          credentialsLoginService.createCredentialsLogin(userDetails.getEmail(),EnumResult.CREDENTIALS_EXPIRED);
          return new LoginResponse(
              null,
              null,
              EnumResult.CREDENTIALS_EXPIRED,
              403);
        }
        else {
          credentialsLoginService.createCredentialsLogin(userDetails.getEmail(),EnumResult.ACCEPTED);
          return new LoginResponse(
              jwtUtils.generateJwtCookie(userDetails),
              userProfile,
              EnumResult.ACCEPTED,
              200);
        }
      }
    }
    credentialsLoginService.createCredentialsLogin( userDetails.getEmail(),EnumResult.NOT_FOUND);
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
}
