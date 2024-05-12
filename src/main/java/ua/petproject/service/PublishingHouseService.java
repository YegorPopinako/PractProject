package ua.petproject.service;

import ua.petproject.models.PublishingHouse;

public interface PublishingHouseService {

    PublishingHouse findOrCreatePublishingHouse(String publishingHouseName);
}
