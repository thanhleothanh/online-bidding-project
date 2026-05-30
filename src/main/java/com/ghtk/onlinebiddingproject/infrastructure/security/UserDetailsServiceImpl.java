package com.ghtk.onlinebiddingproject.infrastructure.security;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ProfileEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ProfileJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ProfileJpaRepository profileJpaRepository;

    public UserDetailsServiceImpl(ProfileJpaRepository profileJpaRepository) {
        this.profileJpaRepository = profileJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProfileEntity profile = profileJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String roleName = profile.getRole() != null ? profile.getRole().getName() : "USER";
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

        return new UserDetailsImpl(
                profile.getId(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getName(),
                profile.getEmail(),
                profile.getImageUrl(),
                profile.getStatus() != null ? profile.getStatus() : UserStatus.INACTIVE,
                authorities
        );
    }
}
