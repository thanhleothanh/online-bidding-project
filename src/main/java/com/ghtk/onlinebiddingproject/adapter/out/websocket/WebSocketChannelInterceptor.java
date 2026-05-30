package com.ghtk.onlinebiddingproject.adapter.out.websocket;

import com.ghtk.onlinebiddingproject.infrastructure.security.UserDetailsImpl;
import com.ghtk.onlinebiddingproject.infrastructure.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);
    static final String USERNAME_HEADER = "username";
    private final UserDetailsServiceImpl userDetailsService;

    public WebSocketChannelInterceptor(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = readHeaderAccessor(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            String username = readUsernameHeader(accessor);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new AccessDeniedException("Username not found in the database!");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            accessor.setUser(authentication);
            logger.info("User with username '{}' made a WebSocket connection", username);
        }
        return message;
    }

    private StompHeaderAccessor readHeaderAccessor(Message<?> message) {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            throw new AuthenticationCredentialsNotFoundException("Fail to read headers.");
        }
        return accessor;
    }

    private String readUsernameHeader(StompHeaderAccessor accessor) {
        final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username not found in header!");
        }
        return username;
    }
}
