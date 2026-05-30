package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryImageStoragePort;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryItemRepository;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceImplTest {

    private ItemServiceImpl itemService;
    private InMemoryItemRepository itemRepository;
    private InMemoryImageStoragePort imageStoragePort;

    @BeforeEach
    void setUp() {
        itemRepository = new InMemoryItemRepository();
        imageStoragePort = new InMemoryImageStoragePort();
        itemService = new ItemServiceImpl(itemRepository, imageStoragePort);
    }

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
    void shouldAddImage() {
        Item item = Item.create("Laptop", "Description", 1000);
        Item created = itemService.create(item, 1);

        Item updated = itemService.addImage(created.getId(), new byte[]{1, 2, 3}, "photo.jpg", 1);

        assertEquals(1, updated.getImageCount());
        assertTrue(imageStoragePort.getStorage().size() > 0);
    }

    @Test
    void shouldRejectAddImageForNonExistentItem() {
        assertThrows(RuntimeException.class,
                () -> itemService.addImage(999, new byte[]{1}, "file.jpg", 1));
    }

    @Test
    void shouldDeleteImage() {
        Item item = Item.create("Laptop", "Description", 1000);
        Item created = itemService.create(item, 1);
        Item withImage = itemService.addImage(created.getId(), new byte[]{1, 2, 3}, "photo.jpg", 1);

        ItemImage image = withImage.getImages().get(0);
        itemService.deleteImage(withImage.getId(), image.getId(), 1);

        Item updated = itemService.getById(withImage.getId());
        assertEquals(0, updated.getImageCount());
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

    @Test
    void shouldDeleteItemImages() {
        ItemImage image1 = new ItemImage(1, "https://example.com/1.jpg", "pub1");
        ItemImage image2 = new ItemImage(2, "https://example.com/2.jpg", "pub2");

        itemService.deleteItemImages(List.of(image1, image2));

        assertFalse(imageStoragePort.getStorage().containsKey("pub1"));
        assertFalse(imageStoragePort.getStorage().containsKey("pub2"));
    }
}
