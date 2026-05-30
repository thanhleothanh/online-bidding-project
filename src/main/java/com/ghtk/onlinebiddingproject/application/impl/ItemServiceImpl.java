package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.ItemService;
import com.ghtk.onlinebiddingproject.domain.model.item.ImageStoragePort;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;

import java.util.List;
import java.util.Map;

public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ImageStoragePort imageStoragePort;

    public ItemServiceImpl(ItemRepository itemRepository, ImageStoragePort imageStoragePort) {
        this.itemRepository = itemRepository;
        this.imageStoragePort = imageStoragePort;
    }

    @Override
    public Item create(Item item, Integer userId) {
        return itemRepository.save(item);
    }

    @Override
    public Item update(Integer id, Item item, Integer userId) {
        Item existing = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        existing.setName(item.getName());
        existing.setDescription(item.getDescription());
        existing.setAmount(item.getAmount());
        return itemRepository.save(existing);
    }

    @Override
    public Item addImage(Integer itemId, byte[] imageData, String filename, Integer userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Map<String, String> result = imageStoragePort.upload(imageData, filename);
        ItemImage image = new ItemImage(null, result.get("url"), result.get("public_id"));
        item.addImage(image);
        return itemRepository.save(item);
    }

    @Override
    public void deleteImage(Integer itemId, Integer imageId, Integer userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        ItemImage imageToRemove = item.getImages().stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (imageToRemove.hasCloudinaryId()) {
            imageStoragePort.delete(imageToRemove.getPublicId());
        }
        item.removeImage(imageId);
        itemRepository.save(item);
    }

    @Override
    public Item getById(Integer id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    public void deleteItemImages(List<ItemImage> images) {
        for (ItemImage image : images) {
            if (image.hasCloudinaryId()) {
                imageStoragePort.delete(image.getPublicId());
            }
        }
    }
}
