package org.example.intento3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.intento3.model.IrrigationRecord;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CropFieldDto {
    private Long cropFieldId;
    private String cropFieldName;
    private String cropFieldDescription;
    private double latitudeData;
    private double longitudeData;
    private double cropFieldSize;
    private String soilType;
    private String cropType;
    private String cropVariety;
    private String cropPlant;
    private LocalDate cropPlantingDate;
    private LocalTime irrigationStartTime;
    private Long numPlants;
    private double idealTemperature;
    private double idealHumidity;
    private List<IrrigationRecord> irrigationRecords = new ArrayList<>();
}
