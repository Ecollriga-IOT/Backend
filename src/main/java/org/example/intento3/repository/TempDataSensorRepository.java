package org.example.intento3.repository;

import org.example.intento3.model.TempDataSensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempDataSensorRepository extends JpaRepository<TempDataSensor, Long> {
    TempDataSensor findByUserId(Long userId);
}
