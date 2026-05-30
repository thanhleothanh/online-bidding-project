package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryItemRepository implements ItemRepository {

    private final Map<Integer, Item> items = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final AtomicInteger nextImageId = new AtomicInteger(1);

    @Override
    public Optional<Item> findById(Integer id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item save(Item item) {
        List<ItemImage> assignedImages = new ArrayList<>();
        for (ItemImage img : item.getImages()) {
            assignedImages.add(new ItemImage(
                    img.getId() != null ? img.getId() : nextImageId.getAndIncrement(),
                    img.getImageUrl(), img.getPublicId()));
        }
        if (item.getId() == null) {
            int itemId = nextId.getAndIncrement();
            Item newItem = new Item(itemId, item.getName(), item.getDescription(),
                    item.getAmount(), item.getAuctionId(), assignedImages);
            items.put(itemId, newItem);
            return newItem;
        }
        Item updated = new Item(item.getId(), item.getName(), item.getDescription(),
                item.getAmount(), item.getAuctionId(), assignedImages);
        items.put(item.getId(), updated);
        return updated;
    }

    @Override
    public void deleteById(Integer id) {
        items.remove(id);
    }

    @Override
    public List<Item> findByAuctionId(Integer auctionId) {
        return items.values().stream()
                .filter(item -> auctionId.equals(item.getAuctionId()))
                .collect(Collectors.toList());
    }
}
