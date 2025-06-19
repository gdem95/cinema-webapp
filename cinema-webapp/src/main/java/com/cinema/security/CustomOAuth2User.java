package com.cinema.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oauth2User;
    private final String email;
    
    public CustomOAuth2User(OAuth2User oauth2User, String email) {
        this.oauth2User = oauth2User;
        this.email = email;
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }
    
    /**
     * Restituisce l'email (o il valore di fallback) come nome univoco,
     * in modo che #authentication.name restituisca il valore previsto.
     */
    @Override
    public String getName() {
        return email;
    }
}


