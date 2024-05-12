package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.petproject.models.PublishingHouse;
import ua.petproject.repository.PublishingHouseRepository;
import ua.petproject.service.PublishingHouseService;

@Service
@AllArgsConstructor
public class PublishingHouseServiceImpl implements PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;

    @Override
    public PublishingHouse findOrCreatePublishingHouse(String publishingHouseName) {
        PublishingHouse publishingHouse = publishingHouseRepository.findByName(publishingHouseName);
        if (publishingHouse == null) {
            publishingHouse = publishingHouseRepository.save(new PublishingHouse(publishingHouseName));
        }
        return publishingHouse;
    }
}
