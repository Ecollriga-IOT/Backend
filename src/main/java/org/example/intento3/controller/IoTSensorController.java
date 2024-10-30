package org.example.intento3.controller;


import org.example.intento3.dto.IdealConditionsDto;
import org.example.intento3.dto.IrrigationSuggestionDto;
import org.example.intento3.dto.ManageConfigurationSensorDto;
import org.example.intento3.model.IrrigationRecord;
import org.example.intento3.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/regadiot/v1/iot-management")
public class IoTSensorController {
    private final IoTSensorService ioTSensorService;
    private final IrrigationService irrigationService;


    public IoTSensorController(IoTSensorService ioTSensorService, IrrigationService irrigationService) {
        this.ioTSensorService = ioTSensorService;
        this.irrigationService = irrigationService;
    }

    @GetMapping("/ideal-conditions/{cropFieldId}")
    public ResponseEntity<IdealConditionsDto> getIdealConditions(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(ioTSensorService.getIdealConditions(cropFieldId));
    }

    @GetMapping("/manage-configuration")
    public ResponseEntity<ManageConfigurationSensorDto> manageConfigurationSensor() {
        return ResponseEntity.ok(ioTSensorService.manageConfigurationSensor());
    }

    @PutMapping("/irrigation-suggestion")
    public ResponseEntity<IrrigationSuggestionDto> updateIrrigationSuggestion(@RequestBody IrrigationSuggestionDto irrigationSuggestionDto) {
        return ResponseEntity.ok(ioTSensorService.updateIrrigationSuggestion(irrigationSuggestionDto));
    }

    @PostMapping("/irrigation-complete/{cropFieldId}")
    public ResponseEntity<IrrigationRecord> completeIrrigationRecord(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(irrigationService.completeIrrigationRecord(cropFieldId));
    }
}
