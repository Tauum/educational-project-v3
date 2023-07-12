//package com.example.javaspringboot.Security.Request;
//
//import com.example.javaspringboot.Security.Model.MyUserDetails;
//import com.example.javaspringboot.Security.Model.Role;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import lombok.val;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseCookie;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.WebUtils;
//
//
//// TODO: FOR SOME REASON WHEN EDITING THIS COOKIE VIA POSTMAN IT DOESNT REJECT
//@Component
//public class JwtUtils {
//    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
//
//    @Value("${Edowl.app.jwtSecret}") // TODO: SECURE THIS
//    private String jwtSecret;
//
//    @Value("${Edowl.app.jwtExpirationMs}")
//    private int jwtExpirationMs;
//
//    @Value("${Edowl.app.jwtCookieName}")
//    private String jwtCookie;
//
//    public String getJwtFromCookies(HttpServletRequest request) {
//        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
//        if (cookie != null) { return cookie.getValue(); }
//        else {  return null; }
//    }
//
//    public String getEmailFromJwtToken(String token) {
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    public ResponseCookie generateJwtCookie(MyUserDetails myUserDetails) {
//        String jwt = generateToken(myUserDetails);
//        return ResponseCookie.from(jwtCookie, jwt)
//                .path("/")
//                .maxAge(24 * 60 * 60)
//                .httpOnly(true)
//                .sameSite("None") // None, Lax, Strict, // TODO: I DONT KNOW
////                .domain("localhost") //192.168.2.128 //edowl.online // TODO: I THINK UPDATE THIS
//                .secure(true)
//                .build();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
////           if(validateClaims(getClaimsFromToken(authToken))) return true;
//        }
//        // todo: change this to the new format of errors
//        catch (SignatureException e) { logger.error("Invalid JWT signature: {}", e.getMessage()); }
//        catch (MalformedJwtException e) { logger.error("Invalid JWT token: {}", e.getMessage()); }
//        catch (ExpiredJwtException e) { logger.error("JWT token is expired: {}", e.getMessage()); }
//        catch (UnsupportedJwtException e) { logger.error("JWT token is unsupported: {}", e.getMessage());}
//        catch (IllegalArgumentException e) { logger.error("JWT claims string is empty: {}", e.getMessage()); }
//        return false;
//    }
//
//    public boolean validateClaims(Map claims) {
//        JwtClaimsEnum[] requiredClaims = {
//            JwtClaimsEnum.CREDENTIALS_ID,
//            JwtClaimsEnum.CREDENTIALS_EMAIL,
//            JwtClaimsEnum.CREDENTIALS_EXPIRED,
//            JwtClaimsEnum.CREDENTIALS_ENABLED,
//            JwtClaimsEnum.CREDENTIALS_ROLES,
//            JwtClaimsEnum.CREDENTIALS_VERSION,
//            JwtClaimsEnum.exp,
//            JwtClaimsEnum.iat
//        };
//
//        for (Object entry : claims.entrySet()) {
//            var a = (Map.Entry<String, String>) entry;
//            if (a.getValue() == null || !Arrays.stream(requiredClaims).anyMatch(c -> c.name().equals(a.getKey()))) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public Map<String, Object> getClaimsFromToken(String authToken){
//       return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody();
//    }
//
//    public String retrieveEmailFromClaims(Map claims){
//        return claims.get(JwtClaimsEnum.CREDENTIALS_EMAIL.name()).toString();
//    }
//
//    public String generateToken(MyUserDetails myUserDetails) {
//
//        return Jwts.builder()
////            .setHeaderParam("typ", "JWT")
////            .setIssuer("Edowl")
//////            .setClaims(buildClaims(myUserDetails))
////            .setSubject(myUserDetails.getEmail())
////            .setIssuedAt(new Date())
////            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
////            .signWith(SignatureAlgorithm.HS512, jwtSecret)
////            .compact();
//            .setSubject(myUserDetails.getEmail())
//            .setIssuedAt(new Date())
//            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//            .signWith(SignatureAlgorithm.HS512, jwtSecret)
//            .compact();
//    }
//
//    public Map<String, Object> buildClaims(MyUserDetails myUserDetails){
//        Map<String, Object> elements = new HashMap<>();
//
//        elements.put(JwtClaimsEnum.CREDENTIALS_ID.name(),myUserDetails.getCredentials().getId());
//        elements.put(JwtClaimsEnum.CREDENTIALS_EMAIL.name(),myUserDetails.getEmail());
//        elements.put(JwtClaimsEnum.CREDENTIALS_EXPIRED.name(),myUserDetails.getCredentials().isExpired());
//        elements.put(JwtClaimsEnum.CREDENTIALS_ENABLED.name(),myUserDetails.getCredentials().isEnabled());
//        elements.put(JwtClaimsEnum.CREDENTIALS_VERSION.name(),myUserDetails.getCredentials().getVersion());
//        elements.put(JwtClaimsEnum.CREDENTIALS_ROLES.name(), myUserDetails.getCredentials().getRoles().stream()
//            .map(Role::getName).collect(Collectors.toSet()));
//        return elements;
//    }
//
//    public ResponseCookie getCleanJwtCookie() {
//        return ResponseCookie.from(jwtCookie, null)
//            .path("/")
//            .maxAge(1)
//            .httpOnly(true)
//            .sameSite("None") // None, Lax, Strict,
////                .domain("localhost") //192.168.2.128 //edowl.online
//            .secure(true)
//            .build();
//    }
//}
package com.example.javaspringboot.Security.Request;

import com.example.javaspringboot.Security.Model.MyUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${EducationalProject.app.jwtSecret}")
    private String jwtSecret;

    @Value("${EducationalProject.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${EducationalProject.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) { return cookie.getValue(); }
        else {  return null; }
    }

    public ResponseCookie generateJwtCookie(MyUserDetails userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getEmail());
        return ResponseCookie.from(jwtCookie, jwt)
            .path("/")
            .maxAge(24 * 60 * 60)
            .httpOnly(true)
            .sameSite("None") // None, Lax, Strict,
//                .domain("localhost") //192.168.2.128 //edowl.online
            .secure(true)
            .build();
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, null)
            .path("/")
            .maxAge(1)
            .httpOnly(true)
            .sameSite("None") // None, Lax, Strict,
//                .domain("localhost") //192.168.2.128 //edowl.online
            .secure(true)
            .build();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }
        // todo: change this to the new format of errors
        catch (SignatureException e) { logger.error("Invalid JWT signature: {}", e.getMessage()); }
        catch (MalformedJwtException e) { logger.error("Invalid JWT token: {}", e.getMessage()); }
        catch (ExpiredJwtException e) { logger.error("JWT token is expired: {}", e.getMessage()); }
        catch (UnsupportedJwtException e) { logger.error("JWT token is unsupported: {}", e.getMessage());}
        catch (IllegalArgumentException e) { logger.error("JWT claims string is empty: {}", e.getMessage()); }
        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }
}
