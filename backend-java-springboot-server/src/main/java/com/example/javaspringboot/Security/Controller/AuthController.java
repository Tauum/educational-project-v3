package com.example.javaspringboot.Security.Controller;

import com.example.javaspringboot.Security.Request.LoginRequest;
import com.example.javaspringboot.Utility.Response.EnumResult;
import com.example.javaspringboot.Utility.Response.LoginResponse;
import com.example.javaspringboot.Utility.Response.RegisterResponse;
import com.example.javaspringboot.Security.Service.AuthService;
import com.example.javaspringboot.User.Model.Registration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

//@PreAuthorize("*") // TODO: FIGURE OUT BECAUSE THIS MAKES MUCH MORE SENSE
@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = authService.authenticateUserSignIn(loginRequest);

        if (loginResponse.getHttpCode() != 200) {
            return ResponseEntity.status(loginResponse.getHttpCode())
                .header(HttpHeaders.WARNING, loginResponse.getEnumResult().toString())
                .body(loginResponse.getEnumResult().toString());
        }

        return  ResponseEntity.status(loginResponse.getHttpCode())
            .header(HttpHeaders.SET_COOKIE, loginResponse.getResponseCookie().toString())
            .header(HttpHeaders.WARNING,loginResponse.getEnumResult().toString())
            .body(loginResponse.getUserProfile());
    }

//    @PreAuthorize("")
    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI(HttpServletRequest request) {
        LoginResponse loginResponse = authService.whoAmI(request);

        if (loginResponse == null) {
            return  ResponseEntity.status(loginResponse.getHttpCode())
                .header(HttpHeaders.SET_COOKIE, loginResponse.getResponseCookie().toString())
                .header(HttpHeaders.WARNING,loginResponse.getEnumResult().toString())
                .body(EnumResult.ACCEPTED + "" + EnumResult.NOT_LOGGED_IN);
        }

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
                .body(EnumResult.ACCEPTED.toString() + "" + EnumResult.NOT_LOGGED_IN.toString());

    }

}


