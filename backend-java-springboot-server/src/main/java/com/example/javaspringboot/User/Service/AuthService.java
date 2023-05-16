package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.Security.Request.JwtUtils;
import com.example.javaspringboot.Security.Response.LoginResponse;
import com.example.javaspringboot.Security.Response.RegisterResponse;
import com.example.javaspringboot.User.Model.Credentials;
import com.example.javaspringboot.User.Model.EnumResult;
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

  public LoginResponse authenticateUserSignIn(Credentials credentials) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(credentials.getCurrentEmail(),
                credentials.getPassword()));

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
   ResponseCookie responseCookie = jwtUtils.getCleanJwtCookie();

    return new LoginResponse(
        Optional.of(responseCookie),
        null,
        EnumResult.NOT_LOGGED_IN,
        200
    );
  }

  public LoginResponse filterLogin(MyUserDetails userDetails){
    if (userDetails.getEmail() != null || !userDetails.getEmail().isBlank()) {
      UserProfile userProfile = userService.findUserProfileUserByEmail(userDetails.getEmail());
      if (userProfile != null) {
        if (!userProfile.isEnabled())
          return new LoginResponse(
              null,
              null,
              EnumResult.DISABLED,
              403
          );
        else {
          return new LoginResponse(
              Optional.ofNullable(jwtUtils.generateJwtCookie(userDetails)),
              Optional.of(userProfile),
              EnumResult.ACCEPTED,
              200
          );
        }
      }
    }
    return new LoginResponse(
        null,
        null,
        EnumResult.NOT_FOUND,
        400
    );
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
