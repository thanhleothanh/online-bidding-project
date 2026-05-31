package com.ghtk.onlinebiddingproject.application.impl.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ghtk.onlinebiddingproject.application.impl.ItemServiceImpl;
import com.ghtk.onlinebiddingproject.domain.model.item.ImageStoragePort;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractItemServiceTest {

    protected ItemRepository itemRepository;
    protected ImageStoragePort imageStoragePort;
    protected ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        initDependencies();
        itemService = new ItemServiceImpl(itemRepository, imageStoragePort);
    }

    protected abstract void initDependencies();

    @Test
    void shouldCreateItem() {
        Item item = Item.create("Laptop", "Gaming laptop", 1000);

        Item created = itemService.create(item, 1);

        assertNotNull(created.getId());
        assertEquals("Laptop", created.getName());
    }

    @Test
    void shouldUpdateItem() {
        Item item = Item.create("Laptop", "Description", 1000);
        Item created = itemService.create(item, 1);

        Item updated = itemService.update(created.getId(), Item.create("New Laptop", "New Desc", 2000), 1);

        assertEquals("New Laptop", updated.getName());
        assertEquals(2000, updated.getAmount());
    }

    @Test
    void shouldRejectUpdateForNonExistentItem() {
        assertThrows(RuntimeException.class,
                () -> itemService.update(999, Item.create("Item", "Desc", 100), 1));
    }

    @Test
    void shouldGetById() {
        Item item = Item.create("Laptop", "Description", 1000);
        Item created = itemService.create(item, 1);

        Item found = itemService.getById(created.getId());

        assertEquals(created.getId(), found.getId());
    }

    @Test
    void shouldRejectGetByIdForNonExistentItem() {
        assertThrows(RuntimeException.class, () -> itemService.getById(999));
    }
}
