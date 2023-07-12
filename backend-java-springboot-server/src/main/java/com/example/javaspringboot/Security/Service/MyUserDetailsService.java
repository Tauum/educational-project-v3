package com.example.javaspringboot.Security.Service;

import com.example.javaspringboot.Security.Model.Credentials;
import com.example.javaspringboot.Security.Model.MyUserDetails;
import com.example.javaspringboot.Security.Repository.CredentialsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private CredentialsRepository credentialsRepository;

    public MyUserDetailsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    // TODO : "UserDetailsService returned null, which is an interface contract violation", WHEN NO PROFILE
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Credentials> credentials = credentialsRepository.findByCurrentEmailIgnoreCase(email);
        if (credentials.isEmpty()) return null;
        return new MyUserDetails(credentials.get());
    }


}