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

}
