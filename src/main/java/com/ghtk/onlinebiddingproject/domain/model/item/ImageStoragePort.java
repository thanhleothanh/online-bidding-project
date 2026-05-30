package com.ghtk.onlinebiddingproject.domain.model.item;

import java.util.Map;

public interface ImageStoragePort {
    Map<String, String> upload(byte[] data, String filename);
    void delete(String publicId);
}
