package ua.petproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.petproject.models.PublishingHouse;
import ua.petproject.repository.PublishingHouseRepository;
import ua.petproject.service.PublishingHouseService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PublishingHouseServiceImpl implements PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;

    @Override
    public PublishingHouse findOrCreatePublishingHouse(String publishingHouseName) {
        Optional<PublishingHouse> publishingHouse = Optional.ofNullable(publishingHouseRepository.findByName(publishingHouseName));
        return publishingHouse.orElseGet(() -> publishingHouseRepository.save(new PublishingHouse(publishingHouseName)));
    }
}
