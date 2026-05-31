package com.ghtk.onlinebiddingproject.application.impl.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryImageStoragePort;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestPortFactory;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;
import java.util.List;
import org.junit.jupiter.api.Test;

class ItemServiceImplInMemoryTest extends AbstractItemServiceTest {

    @Override
    protected void initDependencies() {
        itemRepository = TestRepositoryFactory.createItemRepository();
        imageStoragePort = TestPortFactory.createImageStoragePort();
    }

    private InMemoryImageStoragePort storage() {
        return (InMemoryImageStoragePort) imageStoragePort;
    }

    @Test
    void shouldAddImage() {
        Item item = Item.create("Laptop", "Description", 1000);
        Item created = itemService.create(item, 1);

        Item updated = itemService.addImage(created.getId(), new byte[]{1, 2, 3}, "photo.jpg", 1);

        assertEquals(1, updated.getImageCount());
        assertTrue(storage().getStorage().size() > 0);
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
    void shouldDeleteItemImages() {
        ItemImage image1 = new ItemImage(1, "https://example.com/1.jpg", "pub1");
        ItemImage image2 = new ItemImage(2, "https://example.com/2.jpg", "pub2");

        itemService.deleteItemImages(List.of(image1, image2));

        assertFalse(storage().getStorage().containsKey("pub1"));
        assertFalse(storage().getStorage().containsKey("pub2"));
    }
}
