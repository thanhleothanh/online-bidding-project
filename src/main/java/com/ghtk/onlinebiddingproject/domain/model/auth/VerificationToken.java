package com.ghtk.onlinebiddingproject.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "profileId", "token"})
public class VerificationToken {

    private Integer id;
    private Integer profileId;
    private String token;
    private LocalDateTime expirationTime;

    public VerificationToken(Integer id, Integer profileId, String token, LocalDateTime expirationTime) {
        this.id = id;
        this.profileId = profileId;
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public VerificationToken(Integer profileId, String token) {
        this.profileId = profileId;
        this.token = token;
        this.expirationTime = LocalDateTime.now().plusMinutes(10);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationTime);
    }

    public void extend(int minutes) {
        this.expirationTime = this.expirationTime.plusMinutes(minutes);
    }

    public boolean hasValidToken() {
        return token != null && !isExpired();
    }

}
