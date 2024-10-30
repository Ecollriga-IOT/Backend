package org.example.intento3.repository;

import org.example.intento3.model.IrrigationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface IrrigationRecordRepository extends JpaRepository<IrrigationRecord, Long> {
    List<IrrigationRecord> findByCropFieldId(Long cropFieldId);
    List<IrrigationRecord> findByIrrigationDateBetweenAndCropFieldId(LocalDate startDate, LocalDate endDate, Long cropFieldId);
}
