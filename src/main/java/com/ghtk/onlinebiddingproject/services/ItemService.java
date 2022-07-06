package com.ghtk.onlinebiddingproject.services;

import com.ghtk.onlinebiddingproject.models.entities.Item;

public interface ItemService {

    Item getById(Integer id);

    Item save(Item item);

    Item put(Item item);

    void deleteById(Integer id);

}
