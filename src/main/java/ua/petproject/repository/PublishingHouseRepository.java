package ua.petproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.petproject.models.PublishingHouse;

public interface PublishingHouseRepository extends JpaRepository<PublishingHouse, Long> {

    PublishingHouse findByName(String name);
}
