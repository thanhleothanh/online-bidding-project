package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.item.ImageStoragePort;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryImageStoragePort implements ImageStoragePort {

    private final Map<String, byte[]> storage = new HashMap<>();

    @Override
    public Map<String, String> upload(byte[] data, String filename) {
        String publicId = UUID.randomUUID().toString();
        String url = "https://cloudinary.example.com/" + publicId;
        storage.put(publicId, data);
        return Map.of("url", url, "public_id", publicId);
    }

    @Override
    public void delete(String publicId) {
        storage.remove(publicId);
    }

    public Map<String, byte[]> getStorage() {
        return storage;
    }
}
