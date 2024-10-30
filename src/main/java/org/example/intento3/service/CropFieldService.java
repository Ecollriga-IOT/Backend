package org.example.intento3.service;

import org.example.intento3.model.CropField;

import java.util.List;

public interface CropFieldService {
    public abstract CropField createCropField(Long userId, CropField cropField);
    public abstract CropField getCropFieldById(Long cropFieldId);
    public abstract CropField updateCropField(Long userId, CropField cropField);
    public abstract void deleteCropField(Long cropFieldId);
    public abstract List<CropField> getCropFieldsByUserId(Long userId);
}
