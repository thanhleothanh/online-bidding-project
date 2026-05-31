package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.report.ReportImage;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportImageRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryReportImageRepository implements ReportImageRepository {

    private final Map<Integer, ReportImage> images = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public ReportImage save(ReportImage image) {
        if (image.getId() == null) {
            int id = nextId.getAndIncrement();
            ReportImage newImage = new ReportImage(id, image.getImageUrl(), image.getPublicId());
            images.put(id, newImage);
            return newImage;
        }
        images.put(image.getId(), image);
        return image;
    }

    @Override
    public Optional<ReportImage> findById(Integer id) {
        return Optional.ofNullable(images.get(id));
    }

    @Override
    public void deleteById(Integer id) {
        images.remove(id);
    }
}
