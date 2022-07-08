package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.exceptions.NotFoundException;
import com.ghtk.onlinebiddingproject.models.entities.Item;
import com.ghtk.onlinebiddingproject.models.entities.ItemImage;
import com.ghtk.onlinebiddingproject.repositories.ItemImageRepository;
import com.ghtk.onlinebiddingproject.repositories.ItemRepository;
import com.ghtk.onlinebiddingproject.services.ItemImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemImageServiceImpl implements ItemImageService {
    @Autowired
    private ItemImageRepository itemImageRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public ItemImage save(Integer id, ItemImage itemImage) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy item với id này!"));
        itemImage.setItem(item);
        return itemImageRepository.save(itemImage);
    }
}
