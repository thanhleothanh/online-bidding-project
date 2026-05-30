package com.ghtk.onlinebiddingproject.domain.model.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemImageTest {

    @Test
    void shouldHaveCloudinaryId() {
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "pub123");

        assertTrue(image.hasCloudinaryId());
    }

    @Test
    void shouldNotHaveCloudinaryIdWhenNull() {
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", null);

        assertFalse(image.hasCloudinaryId());
    }

    @Test
    void shouldNotHaveCloudinaryIdWhenBlank() {
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "   ");

        assertFalse(image.hasCloudinaryId());
    }

    @Test
    void shouldGettersWork() {
        ItemImage image = new ItemImage(1, "https://example.com/img.jpg", "pub123");

        assertEquals(1, image.getId());
        assertEquals("https://example.com/img.jpg", image.getImageUrl());
        assertEquals("pub123", image.getPublicId());
    }
}
