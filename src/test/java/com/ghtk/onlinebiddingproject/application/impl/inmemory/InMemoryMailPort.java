package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.application.port.MailPort;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMailPort implements MailPort {

    private final List<VerificationEmail> sentEmails = new ArrayList<>();

    @Override
    public void sendVerificationEmail(String to, String name, String username, String tokenUrl) {
        sentEmails.add(new VerificationEmail(to, name, username, tokenUrl));
    }

    public List<VerificationEmail> getSentEmails() {
        return sentEmails;
    }

    public void clear() {
        sentEmails.clear();
    }

    public record VerificationEmail(String to, String name, String username, String tokenUrl) {}
}
