package com.ghtk.onlinebiddingproject.services.impl;

import com.ghtk.onlinebiddingproject.exceptions.NotFoundException;
import com.ghtk.onlinebiddingproject.models.entities.Item;
import com.ghtk.onlinebiddingproject.repositories.ItemRepository;
import com.ghtk.onlinebiddingproject.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item getById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy item với id này!"));
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
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
