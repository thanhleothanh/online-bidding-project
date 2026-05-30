package com.ghtk.onlinebiddingproject.domain.model.item;

import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@EqualsAndHashCode(of = "id")
public class Item {

    private Integer id;
    private String name;
    private String description;
    private Integer amount;
    private Integer auctionId;
    private final List<ItemImage> images;

    public Item(Integer id, String name, String description, Integer amount, Integer auctionId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.auctionId = auctionId;
        this.images = new ArrayList<>();
    }

    public Item(Integer id, String name, String description, Integer amount, Integer auctionId, List<ItemImage> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.auctionId = auctionId;
        this.images = images != null ? new ArrayList<>(images) : new ArrayList<>();
    }

    public static Item create(String name, String description, Integer amount) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank");
        }
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Item amount must be positive");
        }
        return new Item(null, name, description, amount, null);
    }

    public void addImage(ItemImage image) {
        if (image == null) {
            throw new IllegalArgumentException("Image cannot be null");
        }
        images.add(image);
    }

    public void removeImage(Integer imageId) {
        images.removeIf(img -> img.getId().equals(imageId));
    }

    public void removeImageByPublicId(String publicId) {
        images.removeIf(img -> publicId.equals(img.getPublicId()));
    }

    public int getImageCount() {
        return images.size();
    }

    public boolean hasImages() {
        return !images.isEmpty();
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getAmount() { return amount; }
    public Integer getAuctionId() { return auctionId; }
    public List<ItemImage> getImages() { return List.copyOf(images); }

}
