package com.example.javaspringboot.User.Model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//https://www.codejava.net/frameworks/spring-boot/spring-boot-security-role-based-authorization-tutorial
@Data @NoArgsConstructor @AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private Credentials credentials;
    private Set<GrantedAuthority> authorities;

    public MyUserDetails(Credentials credentials) {
        this.credentials = credentials;
        this.authorities = credentials.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getUsername() { return credentials.getCurrentEmail(); }

    public String getEmail() { return credentials.getCurrentEmail(); }

    public String getEmailOriginal() { return credentials.getOriginalEmail(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true ;}

    @Override
    public boolean isCredentialsNonExpired() { return !credentials.getExpired(); }

    @Override
    public boolean isEnabled() {
        return credentials.isEnabled();
    }
}