package com.ghtk.onlinebiddingproject.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReportImage {

    private Integer id;
    private String imageUrl;
    private String publicId;
    private Integer reportId;

    public ReportImage(Integer id, String imageUrl, String publicId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

}
