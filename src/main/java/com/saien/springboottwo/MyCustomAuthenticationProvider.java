package com.saien.springboottwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyCustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)  {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        System.out.println("usernameusernameusername" + username);
        System.out.println("password--------------" + password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // In real case scenarios, userDetailsService should throw an error when user is not found
        // therefore there is no need for null check
        if (userDetails != null){
            if (passwordEncoder.matches(password, userDetails.getPassword())){
               UsernamePasswordAuthenticationToken authenticationToken =
                       new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
                return authenticationToken;
            }
        }
        throw new BadCredentialsException("Error!!");
    }

    /**
     * Because I am going to use HttpBasicAuthentication
     * and HttpBasicAuthentication uses UsernamePasswordAuthenticationToken
     * @param authenticationType
     * @return
     */
    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.equals(authenticationType);
    }
    
}
