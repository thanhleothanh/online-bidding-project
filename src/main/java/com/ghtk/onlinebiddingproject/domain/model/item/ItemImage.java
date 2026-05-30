package com.ghtk.onlinebiddingproject.domain.model.item;

import java.util.Objects;

public class ItemImage {

    private Integer id;
    private String imageUrl;
    private String publicId;

    public ItemImage(Integer id, String imageUrl, String publicId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.publicId = publicId;
    }

    public boolean hasCloudinaryId() {
        return publicId != null && !publicId.isBlank();
    }

    public Integer getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublicId() {
        return publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemImage itemImage = (ItemImage) o;
        return Objects.equals(id, itemImage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
