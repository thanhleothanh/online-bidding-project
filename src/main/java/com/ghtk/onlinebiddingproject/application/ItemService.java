package com.ghtk.onlinebiddingproject.application;

import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;

import java.util.List;

public interface ItemService {
    Item create(Item item, Integer userId);
    Item update(Integer id, Item item, Integer userId);
    Item addImage(Integer itemId, byte[] imageData, String filename, Integer userId);
    void deleteImage(Integer itemId, Integer imageId, Integer userId);
    Item getById(Integer id);
    void deleteItemImages(List<ItemImage> images);
}
