package com.ghtk.onlinebiddingproject.domain.model.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> findById(Integer id);
    Item save(Item item);
    void deleteById(Integer id);
    List<Item> findByAuctionId(Integer auctionId);
}
