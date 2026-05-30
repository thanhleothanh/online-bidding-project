package com.ghtk.onlinebiddingproject.adapter.out.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ghtk.onlinebiddingproject.domain.model.item.ImageStoragePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryAdapter implements ImageStoragePort {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryAdapter.class);

    private final Cloudinary cloudinary;

    public CloudinaryAdapter(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map<String, String> upload(byte[] data, String filename) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", filename,
                    "resource_type", "auto"
            ));
            return Map.of(
                    "url", (String) result.get("secure_url"),
                    "public_id", (String) result.get("public_id")
            );
        } catch (IOException e) {
            logger.error("Failed to upload image to Cloudinary: {}", filename, e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            logger.error("Failed to delete image from Cloudinary: {}", publicId, e);
            throw new RuntimeException("Failed to delete image", e);
        }
    }
}
