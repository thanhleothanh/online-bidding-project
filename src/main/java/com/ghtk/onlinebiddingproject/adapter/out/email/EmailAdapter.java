package com.ghtk.onlinebiddingproject.adapter.out.email;

import com.ghtk.onlinebiddingproject.application.port.MailPort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailAdapter implements MailPort {

    private static final Logger logger = LoggerFactory.getLogger(EmailAdapter.class);

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailAdapter(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendVerificationEmail(String to, String name, String username, String tokenUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            Map<String, Object> props = new HashMap<>();
            props.put("name", name);
            props.put("userName", username);
            props.put("email", to);
            props.put("token", tokenUrl);

            Context context = new Context();
            context.setVariables(props);
            String html = templateEngine.process("client-register", context);

            helper.setTo(to);
            helper.setSubject("Verify Your Email Address");
            helper.setText(html, true);

            mailSender.send(message);
            logger.info("Verification email sent to {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send verification email to {}", to, e);
        }
    }
}
