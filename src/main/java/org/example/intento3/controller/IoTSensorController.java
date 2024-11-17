package org.example.intento3.controller;


import org.example.intento3.dto.IdealConditionsDto;
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


    public IoTSensorController(IoTSensorService ioTSensorService) {
        this.ioTSensorService = ioTSensorService;
    }




}
