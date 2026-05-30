package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileJpaRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO user (id) VALUES (:profileId)", nativeQuery = true)
    void insertUser(@Param("profileId") Integer profileId);

    @Modifying
    @Query(value = "INSERT INTO admin (id) VALUES (:profileId)", nativeQuery = true)
    void insertAdmin(@Param("profileId") Integer profileId);
}
