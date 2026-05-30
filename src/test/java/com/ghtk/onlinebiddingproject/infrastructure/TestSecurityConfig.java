package com.ghtk.onlinebiddingproject.infrastructure;

import com.ghtk.onlinebiddingproject.constants.UserStatusConstants;
import com.ghtk.onlinebiddingproject.infrastructure.security.UserDetailsImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public WithSecurityContextFactory<WithMockTestUser> withMockTestUserFactory() {
        return annotation -> {
            UserDetailsImpl userDetails = new UserDetailsImpl(
                    annotation.id(),
                    annotation.username(),
                    "password",
                    annotation.username(),
                    annotation.username() + "@test.com",
                    "https://example.com/image.jpg",
                    UserStatusConstants.ACTIVE,
                    List.of(new SimpleGrantedAuthority(annotation.role()))
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            return context;
        };
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @WithSecurityContext(factory = WithMockTestUserSecurityContextFactory.class)
    public @interface WithMockTestUser {
        int id() default 1;
        String username() default "testuser";
        String role() default "USER";
    }

    public static class WithMockTestUserSecurityContextFactory implements WithSecurityContextFactory<WithMockTestUser> {
        @Override
        public SecurityContext createSecurityContext(WithMockTestUser annotation) {
            UserDetailsImpl userDetails = new UserDetailsImpl(
                    annotation.id(),
                    annotation.username(),
                    "password",
                    annotation.username(),
                    annotation.username() + "@test.com",
                    "https://example.com/image.jpg",
                    UserStatusConstants.ACTIVE,
                    List.of(new SimpleGrantedAuthority(annotation.role()))
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            context.setAuthentication(authentication);
            return context;
        }
    }
}
