package com.ghtk.onlinebiddingproject.application.impl.item;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Tag("integration-tests")
@Sql(scripts = "classpath:sql/seed-item-full.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ItemServiceImplDatabaseTest extends AbstractItemServiceTest {

    @Autowired
    private ItemRepository autItemRepository;

    @Override
    protected void initDependencies() {
        itemRepository = autItemRepository;
        imageStoragePort = null;
    }

    private Item createTestItem() {
        Item item = Item.create("Laptop", "Gaming laptop", 1000);
        item.setAuctionId(1);
        return item;
    }

    @Test
    void shouldCreateItem() {
        Item item = createTestItem();

        Item created = itemService.create(item, 1);

        assertNotNull(created.getId());
        assertEquals("Laptop", created.getName());
    }

    @Test
    void shouldUpdateItem() {
        Item item = createTestItem();
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
        Item item = createTestItem();
        Item created = itemService.create(item, 1);

        Item found = itemService.getById(created.getId());

        assertEquals(created.getId(), found.getId());
    }

    @Test
    void shouldRejectGetByIdForNonExistentItem() {
        assertThrows(RuntimeException.class, () -> itemService.getById(999));
    }

    @Test
    void shouldBulkImageDeletion() {
        assertDoesNotThrow(() -> itemService.deleteItemImages(List.of()));
    }
}
