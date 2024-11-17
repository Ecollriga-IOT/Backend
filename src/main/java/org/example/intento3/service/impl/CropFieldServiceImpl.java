package org.example.intento3.service.impl;


import jakarta.persistence.EntityNotFoundException;
import org.example.intento3.exception.ResourceNotFoundException;
import org.example.intento3.model.CropField;
import org.example.intento3.model.IrrigationRecord;
import org.example.intento3.model.User;
import org.example.intento3.repository.CropFieldRepository;
import org.example.intento3.repository.IrrigationRecordRepository;
import org.example.intento3.service.CropFieldService;
import org.example.intento3.service.IoTSensorService;
import org.example.intento3.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CropFieldServiceImpl implements CropFieldService {
    private final IrrigationRecordRepository irrigationRecordRepository;
    CropFieldRepository cropFieldRepository;
    UserService userService;
    IoTSensorService ioTSensorService;

    public CropFieldServiceImpl(
            CropFieldRepository cropFieldRepository,
            UserService userService,
            IrrigationRecordRepository irrigationRecordRepository,
            IoTSensorService ioTSensorService) {
        this.cropFieldRepository = cropFieldRepository;
        this.userService = userService;
        this.irrigationRecordRepository = irrigationRecordRepository;
        this.ioTSensorService = ioTSensorService;
    }

    @Override
    public CropField createCropField(Long userId, CropField cropField) {
        existsUserByUserId(userId);
        cropField.setUser(userService.getUserById(userId));
        validateCropField(cropField);
        cropFieldRepository.save(cropField);
        return cropField;
    }

    @Override
    public CropField getCropFieldById(Long cropFieldId) {
        existsCropFieldByCropFieldId(cropFieldId);
        return cropFieldRepository.findById(cropFieldId).orElseThrow(() -> new ResourceNotFoundException("CropField not found"));
    }

    @Override
    @Transactional
    public CropField updateCropField(Long cropFieldId, CropField cropField) {
        CropField existingCropField = getCropFieldById(cropFieldId);
        if (existingCropField == null) {
            // Manejar el caso cuando el CropField no existe
            throw new EntityNotFoundException("CropField with id " + cropFieldId + " not found.");
        }

        // Copiar los atributos del cropField recibido al cropField existente
        existingCropField.setCropFieldName(cropField.getCropFieldName());
        existingCropField.setCropFieldDescription(cropField.getCropFieldDescription());
        existingCropField.setLatitudeData(cropField.getLatitudeData());
        existingCropField.setLongitudeData(cropField.getLongitudeData());
        existingCropField.setCropFieldSize(cropField.getCropFieldSize());
        existingCropField.setSoilType(cropField.getSoilType());
        existingCropField.setCropType(cropField.getCropType());
        existingCropField.setCropVariety(cropField.getCropVariety());
        existingCropField.setCropPlant(cropField.getCropPlant());
        existingCropField.setCropPlantingDate(cropField.getCropPlantingDate());
        existingCropField.setNumPlants(cropField.getNumPlants());
        existingCropField.setIdealTemperature(cropField.getIdealTemperature());
        existingCropField.setIdealHumidity(cropField.getIdealHumidity());
        existingCropField.setIrrigation(cropField.isIrrigation());

        // Sincronizar la colección irrigationRecords
        if (cropField.getIrrigationRecords() != null) {
            // Limpiar y agregar los nuevos registros de riego
            existingCropField.getIrrigationRecords().clear();
            for (IrrigationRecord record : cropField.getIrrigationRecords()) {
                record.setCropField(existingCropField);
                existingCropField.getIrrigationRecords().add(record);
            }
        } else {
            // Si no se envía una nueva lista de irrigationRecords, limpiar la existente
            existingCropField.getIrrigationRecords().clear();
        }

        // Guardar el cropField actualizado
        validateCropField(existingCropField);
        return cropFieldRepository.save(existingCropField);
    }



    @Override
    public void deleteCropField(Long cropFieldId) {
        existsCropFieldByCropFieldId(cropFieldId);
        cropFieldRepository.deleteById(cropFieldId);
    }

    @Override
    public List<CropField> getCropFieldsByUserId(Long userId) {
        existsUserByUserId(userId);
        return cropFieldRepository.findByUserId(userId);
    }


    private void existsCropFieldByCropFieldId(Long cropFieldId) {
        if (!cropFieldRepository.existsById(cropFieldId)) {
            throw new ResourceNotFoundException("CropField not found");
        }
    }

    private void existsUserByUserId(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("No existe el usuario con el id: " + userId);
        }
    }

    private void validateCropField(CropField cropField) {
        if (cropField.getCropFieldName() == null || cropField.getCropFieldName().isEmpty()) {
            throw new ResourceNotFoundException("El nombre del campo no puede estar vacío");
        }
        if (cropField.getCropPlant() == null || cropField.getCropPlant().isEmpty()) {
            throw new ResourceNotFoundException("El cultivo no puede estar vacío");
        }
        if (cropField.getSoilType() == null || cropField.getSoilType().isEmpty()) {
            throw new ResourceNotFoundException("El tipo de suelo no puede estar vacío");
        }


    }
}
