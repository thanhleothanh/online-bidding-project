package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.exceptions.NotFoundException;
import com.ghtk.onlinebiddingproject.models.entities.Item;
import com.ghtk.onlinebiddingproject.models.entities.ItemImage;
import com.ghtk.onlinebiddingproject.repositories.ItemImageRepository;
import com.ghtk.onlinebiddingproject.repositories.ItemRepository;
import com.ghtk.onlinebiddingproject.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemImageRepository itemImageRepository;

    @Override
    public Item getById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy item với id này!"));
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public Item save(Item item) {
        Item newItem = itemRepository.save(item);
        List<ItemImage> itemImages = item.getItemImages();
        if (itemImages != null && itemImages.size() != 0) {
            for (ItemImage itemImage : itemImages) {
                itemImage.setItem(newItem);
                itemImageRepository.save(itemImage);
            }
        }
        return newItem;
    }

    @Override
    public Item put(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteById(Integer id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy item với id này!"));
        itemRepository.delete(item);
    }
}
