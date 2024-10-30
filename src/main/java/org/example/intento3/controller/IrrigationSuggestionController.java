package org.example.intento3.controller;


import org.example.intento3.dto.IrrigationSuggestionResponse;
import org.example.intento3.service.IoTSensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/regadiot/v1/irrigation-suggestion")
public class IrrigationSuggestionController {
    private final IoTSensorService ioTSensorService;

    public IrrigationSuggestionController(IoTSensorService ioTSensorService) {
        this.ioTSensorService = ioTSensorService;
    }

    @GetMapping("/{cropFieldId}")
    public ResponseEntity<IrrigationSuggestionResponse> getIrrigationSuggestion(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(ioTSensorService.getIrrigationSuggestion(cropFieldId));
    }
}
