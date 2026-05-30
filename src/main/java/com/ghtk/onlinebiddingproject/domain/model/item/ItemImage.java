package com.ghtk.onlinebiddingproject.domain.model.item;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemImage {

    private Integer id;
    private String imageUrl;
    private String publicId;

    public boolean hasCloudinaryId() {
        return publicId != null && !publicId.isBlank();
    }

}
