package com.ghtk.onlinebiddingproject.domain.model.auth;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getLegitimateScore() {
        return legitimateScore;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLegitimateScore(Integer legitimateScore) {
        this.legitimateScore = legitimateScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Profile{id=" + id + ", username='" + username + "', name='" + name + "'}";
    }
}
