package com.ghtk.onlinebiddingproject.ws;

import com.ghtk.onlinebiddingproject.constants.UserStatusConstants;
import com.ghtk.onlinebiddingproject.security.UserDetailsImpl;
import com.ghtk.onlinebiddingproject.security.UserDetailsServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    static final String USERNAME_HEADER = "username";
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @SneakyThrows
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = readHeaderAccessor(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            String username = readUsernameHeader(accessor);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
            if (userDetails == null)
                throw new AccessDeniedException("Username not found in the database!");
            if (userDetails.getStatus().equals(UserStatusConstants.BANNED)) {
                throw new AccessDeniedException("Tài khoản của bạn đã bị khoá!");
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null,
                            userDetails.getAuthorities());
            accessor.setUser(authentication);
            log.info("User with username '{}' made a WebSocket connection ", username);
        }
        return message;
    }

    private StompHeaderAccessor readHeaderAccessor(Message<?> message) {
        final StompHeaderAccessor accessor = getAccessor(message);
        if (accessor == null) {
            throw new AuthenticationCredentialsNotFoundException("Fail to read headers.");
        }
       return accessor;
    }

    private String readUsernameHeader(StompHeaderAccessor accessor) {
        final String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
        if (username == null || username.trim().isEmpty())
            throw new AuthenticationCredentialsNotFoundException("Username not found in header!");
        return username;
    }

    StompHeaderAccessor getAccessor(Message<?> message) {
        return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    }
}
