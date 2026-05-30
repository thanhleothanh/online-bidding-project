package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity {

    @Id
    @Column(name = "profile_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProfileEntity profile;
}
