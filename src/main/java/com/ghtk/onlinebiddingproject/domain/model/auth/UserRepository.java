package com.ghtk.onlinebiddingproject.domain.model.auth;

import java.util.Optional;

public interface UserRepository {
    Optional<Profile> findById(Integer id);
    Optional<Profile> findByUsername(String username);
    Profile save(Profile profile);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void insertUser(Integer profileId);
    void insertAdmin(Integer profileId);
}
