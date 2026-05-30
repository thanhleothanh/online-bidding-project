package com.ghtk.onlinebiddingproject.adapter.in.item;

import com.ghtk.onlinebiddingproject.adapter.in.item.dto.ItemImageDto;
import com.ghtk.onlinebiddingproject.adapter.in.item.dto.ItemRequestDto;
import com.ghtk.onlinebiddingproject.application.ItemService;
import com.ghtk.onlinebiddingproject.domain.model.item.Item;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemImage;
import com.ghtk.onlinebiddingproject.infrastructure.security.CurrentUserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ItemImageDto> create(@RequestBody ItemRequestDto request) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        Item item = Item.create(request.name(), request.description(), request.amount());
        Item created = itemService.create(item, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ItemImageDto(created.getId(), null, null));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ItemImageDto> update(@PathVariable Integer id, @RequestBody ItemRequestDto request) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        Item item = Item.create(request.name(), request.description(), request.amount());
        Item updated = itemService.update(id, item, userId);
        return ResponseEntity.ok(new ItemImageDto(updated.getId(), null, null));
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ItemImageDto> addImage(
            @PathVariable Integer id,
            @RequestBody byte[] imageData,
            @RequestParam String filename) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        Item updated = itemService.addImage(id, imageData, filename, userId);
        List<ItemImage> images = updated.getImages();
        ItemImage lastImage = images.get(images.size() - 1);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ItemImageDto(lastImage.getId(), lastImage.getImageUrl(), lastImage.getPublicId()));
    }

    @DeleteMapping("/{itemId}/images/{imageId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer itemId, @PathVariable Integer imageId) {
        Integer userId = CurrentUserUtils.getCurrentUserId();
        itemService.deleteImage(itemId, imageId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(itemService.getById(id));
    }
}
