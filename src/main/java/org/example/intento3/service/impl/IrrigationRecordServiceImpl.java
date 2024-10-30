package org.example.intento3.service.impl;

import org.example.intento3.model.IrrigationRecord;
import org.example.intento3.repository.CropFieldRepository;
import org.example.intento3.repository.IrrigationRecordRepository;
import org.example.intento3.service.IrrigationRecordService;
import org.example.intento3.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IrrigationRecordServiceImpl implements IrrigationRecordService {
    private final CropFieldRepository cropFieldRepository;
    private final UserService userService;
    private final IrrigationRecordRepository irrigationRecordRepository;

    public IrrigationRecordServiceImpl(CropFieldRepository cropFieldRepository, UserService userService, IrrigationRecordRepository irrigationRecordRepository) {
        this.cropFieldRepository = cropFieldRepository;
        this.userService = userService;
        this.irrigationRecordRepository = irrigationRecordRepository;
    }

    @Override
    public IrrigationRecord createIrrigationRecord(IrrigationRecord irrigationRecord, Long cropFieldId) {
        return null;
    }

    @Override
    public void deleteIrrigationRecord(Long irrigationRecordId) {
        irrigationRecordRepository.deleteById(irrigationRecordId);
    }

    @Override
    public IrrigationRecord getIrrigationRecordById(Long irrigationRecordId) {
        return irrigationRecordRepository.findById(irrigationRecordId).orElse(null);
    }

    @Override
    public List<IrrigationRecord> getAllIrrigationRecordsByCropFieldId(Long cropFieldId) {
        return irrigationRecordRepository.findByCropFieldId(cropFieldId);
    }

    @Override
    public List<IrrigationRecord> getIrrigationByBetweenDatesAndCropFieldId(LocalDate startDate, LocalDate endDate, Long cropFieldId) {
        return irrigationRecordRepository.findByIrrigationDateBetweenAndCropFieldId(startDate, endDate, cropFieldId);
    }
}

