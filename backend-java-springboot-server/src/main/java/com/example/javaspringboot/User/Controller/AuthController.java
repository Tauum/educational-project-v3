package com.example.javaspringboot.User.Controller;

import com.example.javaspringboot.Security.Response.LoginResponse;
import com.example.javaspringboot.Security.Response.RegisterResponse;
import com.example.javaspringboot.User.Model.Credentials;
import com.example.javaspringboot.User.Model.EnumResult;
import com.example.javaspringboot.User.Model.Registration;
import com.example.javaspringboot.User.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Credentials credentials) {

        LoginResponse loginResponse = authService.authenticateUserSignIn(credentials);

        return  ResponseEntity.status(loginResponse.getHttpCode())
            .header(HttpHeaders.SET_COOKIE,
                loginResponse.getResponseCookie().toString())
            .header(HttpHeaders.WARNING,loginResponse.getEnumResult().toString())
            .body(loginResponse.getUserProfile());
    }

    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI(HttpServletRequest request) {

        LoginResponse loginResponse = authService.whoAmI(request);
            return  ResponseEntity.status(loginResponse.getHttpCode())
                .header(HttpHeaders.SET_COOKIE, loginResponse.getResponseCookie().toString())
                .header(HttpHeaders.WARNING,loginResponse.getEnumResult().toString())
                .body( loginResponse.getUserProfile());

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody Registration attempt) {

        RegisterResponse registerResponse = authService.register(attempt);
        return ResponseEntity.status(registerResponse.getHttpCode())
            .header(HttpHeaders.WARNING,registerResponse.getEnumResult().toString())
            .body(registerResponse.getEnumResult());
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() { // something is throwing an error here on sign out

        LoginResponse logoutResponse = authService.logout();

        return  ResponseEntity.status(logoutResponse.getHttpCode())
            .header(HttpHeaders.SET_COOKIE, logoutResponse.getResponseCookie().toString())
            .header(HttpHeaders.WARNING,logoutResponse.getEnumResult().toString())
                .body(EnumResult.ACCEPTED.toString() + "" + EnumResult.LOGOUT.toString());

    }

}


