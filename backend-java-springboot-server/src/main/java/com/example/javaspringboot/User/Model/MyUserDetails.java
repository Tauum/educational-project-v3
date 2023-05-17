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
    private User user;
    private Set<GrantedAuthority> authorities;

    public MyUserDetails(User user) {
        this.user = user;
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getCredentials().getPassword();
    }

    @Override
    public String getUsername() { return user.getCredentials().getCurrentEmail(); }

    public String getEmail() { return user.getCredentials().getCurrentEmail(); }

    public String getEmailOriginal() { return user.getCredentials().getOriginalEmail(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true ;}

    @Override
    public boolean isCredentialsNonExpired() { return !user.getCredentials().getExpired(); }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}