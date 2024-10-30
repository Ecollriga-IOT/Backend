package org.example.intento3.repository;

import org.example.intento3.model.CropField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CropFieldRepository extends JpaRepository<CropField, Long> {
    List<CropField> findByCropType(String cropType);
    List<CropField> findByUserId(Long userId);
}
