package com.example.javaspringboot.Security.Request;

import com.example.javaspringboot.Security.Service.MyUserDetailsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private MyUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = parseJwt(request);
            // TODO : FIX THIS WHEN ALREADY SIGNED IN
            if (jwt != null) {
                if (jwtUtils.validateJwtToken(jwt)){
//                    var claims = jwtUtils.getClaimsFromToken(jwt);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.retrieveEmailFromClaims(claims));
                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtils.getEmailFromJwtToken(jwt));

                    UsernamePasswordAuthenticationToken authentication = new
                        UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

//                    UsernamePasswordAuthenticationToken authentication = new
//                        UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
                else{// dont know if this is right, but if the token is invalid it will revoke it, give empty token with 1s life
                    response.setHeader(HttpHeaders.SET_COOKIE, jwtUtils.getCleanJwtCookie().toString());
                }
            }
        }
        // TODO: UPDATE TO NEW ERROR CHECKING
        catch (Exception e) { logger.error("Cannot set user authentication: {}", e); }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) { return jwtUtils.getJwtFromCookies(request); }
}
