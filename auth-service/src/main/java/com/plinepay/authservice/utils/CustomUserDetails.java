package com.plinepay.authservice.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.plinepay.core.entities.User;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        email = user.getEmail();
        password = user.getPassword();
        authorities = new ArrayList<GrantedAuthority>(); // Liste des rôles a implémenter avec la table role founie
        // en suivant par exemple le commentaire ci-dessous.
        
        /** Important */
        // authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
        // .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
