package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.Objects;

public class ReportImage {

    private Integer id;
    private String imageUrl;
    private String publicId;

    public ReportImage(Integer id, String imageUrl, String publicId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    public Integer getId() { return id; }
    public String getImageUrl() { return imageUrl; }
    public String getPublicId() { return publicId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportImage that = (ReportImage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
