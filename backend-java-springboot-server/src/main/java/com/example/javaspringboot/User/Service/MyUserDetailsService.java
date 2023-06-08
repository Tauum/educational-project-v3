package com.example.javaspringboot.User.Service;

import com.example.javaspringboot.User.Model.Credentials;
import com.example.javaspringboot.User.Model.MyUserDetails;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Credentials credentials = credentialsService.findCredentialsByCurrentEmail(email);
        if (credentials == null) return null;
        return new MyUserDetails(credentials);
    }


}