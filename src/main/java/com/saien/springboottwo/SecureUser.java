package com.saien.springboottwo;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecureUser implements UserDetails{

    private final UserRequest userRequest;

    public SecureUser(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("read"));
	}

	@Override
	public String getPassword() {
		return userRequest.getPassword();
	}

	@Override
	public String getUsername() {
		return userRequest.getUsername();
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
