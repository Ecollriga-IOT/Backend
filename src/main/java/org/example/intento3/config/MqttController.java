package org.example.intento3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Opcional: CORS si tu frontend está en otro dominio
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    private final MqttPublisherService mqttPublisherService;

    @Autowired
    public MqttController(MqttPublisherService mqttPublisherService) {
        this.mqttPublisherService = mqttPublisherService;
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
}