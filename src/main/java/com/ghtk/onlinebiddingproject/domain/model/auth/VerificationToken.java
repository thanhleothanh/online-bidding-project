package com.ghtk.onlinebiddingproject.domain.model.auth;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Integer getId() {
        return id;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VerificationToken{id=" + id + ", profileId=" + profileId + ", token='" + token + "'}";
    }
}
