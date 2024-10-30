package org.example.intento3.controller;


import org.example.intento3.model.IrrigationRecord;
import org.example.intento3.service.IrrigationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/regadiot/v1/irrigation")
public class IrrigationController {
    private IrrigationService irrigationService;

    public IrrigationController(IrrigationService irrigationService) {
        this.irrigationService = irrigationService;
    }

    @PostMapping("/start/{cropFieldId}")
    public ResponseEntity<Void> startIrrigation(@PathVariable Long cropFieldId) {
        irrigationService.startIrrigation(cropFieldId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete/{cropFieldId}")
    public ResponseEntity<IrrigationRecord> completeIrrigationRecord(@PathVariable Long cropFieldId) {
        return ResponseEntity.ok(irrigationService.completeIrrigationRecord(cropFieldId));
    }
}
