package com.ghtk.onlinebiddingproject.infrastructure.security;

import com.ghtk.onlinebiddingproject.constants.UserStatusConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final String name;
    private final String email;
    private final String imageUrl;
    private final UserStatusConstants status;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String password, String name, String email,
                           String imageUrl, UserStatusConstants status,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.status = status;
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserStatusConstants getStatus() {
        return status;
    }

    public boolean isSuspended() {
        return status == UserStatusConstants.SUSPENDED;
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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatusConstants.BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatusConstants.ACTIVE;
    }
}
