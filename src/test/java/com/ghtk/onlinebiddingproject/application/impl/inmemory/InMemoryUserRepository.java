package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, Profile> profiles = new HashMap<>();
    private final Map<String, Profile> byUsername = new HashMap<>();
    private final Map<String, Profile> byEmail = new HashMap<>();
    private int nextId = 1;

    @Override
    public Optional<Profile> findById(Integer id) {
        return Optional.ofNullable(profiles.get(id));
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return Optional.ofNullable(byUsername.get(username));
    }

    @Override
    public Profile save(Profile profile) {
        if (profile.getId() == null) {
            Profile newProfile = new Profile(nextId++, profile.getUsername(), profile.getPassword(),
                    profile.getName(), profile.getEmail(), profile.getRole());
            newProfile.setStatus(profile.getStatus());
            profiles.put(newProfile.getId(), newProfile);
            byUsername.put(newProfile.getUsername(), newProfile);
            byEmail.put(newProfile.getEmail(), newProfile);
            return newProfile;
        }
        profiles.put(profile.getId(), profile);
        byUsername.put(profile.getUsername(), profile);
        byEmail.put(profile.getEmail(), profile);
        return profile;
    }

    @Override
    public boolean existsByUsername(String username) {
        return byUsername.containsKey(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return byEmail.containsKey(email);
    }

    @Override
    public void insertUser(Integer profileId) {
    }

    @Override
    public void insertAdmin(Integer profileId) {
    }

    public void clear() {
        profiles.clear();
        byUsername.clear();
        byEmail.clear();
    }
}
