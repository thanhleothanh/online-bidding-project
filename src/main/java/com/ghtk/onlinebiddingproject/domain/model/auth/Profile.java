package com.ghtk.onlinebiddingproject.domain.model.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "username", "name"})
public class Profile {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String bio;
    private String imageUrl;
    private Integer legitimateScore;
    private UserStatus status;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Profile(Integer id, String username, String password, String name, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = UserStatus.INACTIVE;
        this.bio = "";
        this.legitimateScore = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateProfile(String name, String bio, String imageUrl) {
        if (name != null) this.name = name;
        if (bio != null) this.bio = bio;
        if (imageUrl != null) this.imageUrl = imageUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void deductLegitimateScore(int amount) {
        this.legitimateScore = Math.max(0, this.legitimateScore - amount);
        this.updatedAt = LocalDateTime.now();
    }

    public void addLegitimateScore(int amount) {
        this.legitimateScore += amount;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isSuspended() {
        return status.isSuspended();
    }

    public boolean isActive() {
        return status.isActive();
    }

    public boolean canAuthenticate() {
        return status.canAuthenticate();
    }

}
