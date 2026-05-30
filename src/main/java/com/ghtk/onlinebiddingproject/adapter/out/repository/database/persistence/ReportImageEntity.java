package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "report_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "report_id", nullable = false)
    private Integer reportId;
}
