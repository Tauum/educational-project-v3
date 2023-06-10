package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Model.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    CredentialsService credentialsService;

    public MyUserDetailsService(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    // TODO : "UserDetailsService returned null, which is an interface contract violation", WHEN NO PROFILE
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Credentials credentials = credentialsService.findCredentialsByCurrentEmail(email);
        if (credentials == null) return null;
        return new MyUserDetails(credentials);
    }


}