package org.example.intento3.service;
import org.example.intento3.dto.IdealConditionsDto;
import org.example.intento3.dto.IrrigationSuggestionDto;
import org.example.intento3.dto.IrrigationSuggestionResponse;
import org.example.intento3.dto.ManageConfigurationSensorDto;
import org.example.intento3.model.IrrigationSuggestion;

public interface IoTSensorService {
    public abstract IdealConditionsDto getIdealConditions(Long cropFieldId);
    public abstract ManageConfigurationSensorDto manageConfigurationSensor();
    public abstract IrrigationSuggestion saveIrrigationSuggestion(Long cropFieldId);
    public abstract IrrigationSuggestionDto updateIrrigationSuggestion(IrrigationSuggestionDto irrigationSuggestionDto);
    public abstract IrrigationSuggestionResponse getIrrigationSuggestion(Long cropFieldId);
}
