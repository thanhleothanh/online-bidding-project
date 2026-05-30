package com.ghtk.onlinebiddingproject.domain.model.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void shouldCreateItem() {
        Item item = Item.create("Laptop", "Gaming laptop", 1000);

        assertNull(item.getId());
        assertEquals("Laptop", item.getName());
        assertEquals("Gaming laptop", item.getDescription());
        assertEquals(1000, item.getAmount());
        assertTrue(item.getImages().isEmpty());
    }

    @Test
    void shouldRejectBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> Item.create("  ", "Description", 100));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(IllegalArgumentException.class,
                () -> Item.create(null, "Description", 100));
    }

    @Test
    void shouldRejectZeroAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> Item.create("Item", "Description", 0));
    }

    @Test
    void shouldRejectNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> Item.create("Item", "Description", -1));
    }

    @Test
    void shouldAddImage() {
        Item item = Item.create("Laptop", "Description", 1000);
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "pub123");

        item.addImage(image);

        assertEquals(1, item.getImageCount());
        assertTrue(item.hasImages());
    }

    @Test
    void shouldRejectNullImage() {
        Item item = Item.create("Laptop", "Description", 1000);

        assertThrows(IllegalArgumentException.class, () -> item.addImage(null));
    }

    @Test
    void shouldRemoveImageById() {
        Item item = Item.create("Laptop", "Description", 1000);
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "pub123");
        item.addImage(image);

        item.removeImage(1);

        assertEquals(0, item.getImageCount());
        assertFalse(item.hasImages());
    }

    @Test
    void shouldRemoveImageByPublicId() {
        Item item = Item.create("Laptop", "Description", 1000);
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "pub123");
        item.addImage(image);

        item.removeImageByPublicId("pub123");

        assertEquals(0, item.getImageCount());
    }

    @Test
    void shouldReturnImmutableImagesList() {
        Item item = Item.create("Laptop", "Description", 1000);
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "pub123");
        item.addImage(image);

        var images = item.getImages();

        assertThrows(UnsupportedOperationException.class, () -> images.add(new ItemImage(2, "url", "pub")));
    }
}
