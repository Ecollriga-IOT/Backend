package org.example.intento3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    private final MqttPublisherService mqttPublisherService;
    private final SensorDataService sensorDataService;


    @Autowired
    public MqttController(MqttPublisherService mqttPublisherService, SensorDataService sensorDataService) {
        this.mqttPublisherService = mqttPublisherService;
        this.sensorDataService = sensorDataService;
    }

    @PostMapping("/control/open")
    public ResponseEntity<String> openDevice() {
        mqttPublisherService.sendControlMessage(1); // 1 para abrir
        return ResponseEntity.ok("Comando 'Open' enviado.");
    }

    @PostMapping("/control/close")
    public ResponseEntity<String> closeDevice() {
        mqttPublisherService.sendControlMessage(0); // 0 para cerrar
        return ResponseEntity.ok("Comando 'Close' enviado.");
    }
    @GetMapping("/control/sensor")
    public ResponseEntity<SensorData> sensorDevice() {
        mqttPublisherService.sendControlMessage(3); // Enviar comando para obtener datos
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(null);
        }
        SensorData data = sensorDataService.getLatestSensorData();
        if (data != null) {
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}