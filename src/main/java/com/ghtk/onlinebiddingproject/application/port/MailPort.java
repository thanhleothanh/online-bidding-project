package com.ghtk.onlinebiddingproject.application.port;

public interface MailPort {
    void sendVerificationEmail(String to, String name, String username, String tokenUrl);
}
