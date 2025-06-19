package com.cinema.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cinema.model.User;
import com.cinema.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        if ("amazon".equalsIgnoreCase(registrationId)) {
            
            String email = (String) oauth2User.getAttributes().get("email");
            
            if (email == null || email.isEmpty()) {
                email = (String) oauth2User.getAttributes().get("user_id");
            }
            if (email == null || email.isEmpty()) {
                throw new OAuth2AuthenticationException("Nessun identificativo (email o user_id) presente nella risposta di Amazon");
            }
            
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                
                user = new User();
                user.setEmail(email);
                user.setPassword("");  
                user.setRole("USER");
                userRepository.save(user);
            }
            
            return new CustomOAuth2User(oauth2User, email);
        }
        
        return oauth2User;
    }
}


