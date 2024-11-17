package org.example.intento3.service.impl;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.intento3.dto.IdealConditionsDto;
import org.example.intento3.dto.ManageConfigurationSensorDto;
import org.example.intento3.exception.ResourceNotFoundException;
import org.example.intento3.model.CropField;
import org.example.intento3.repository.CropFieldRepository;
import org.example.intento3.service.IoTSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IoTSensorServiceImpl implements IoTSensorService {
    private final CropFieldRepository cropFieldRepository;
    @Autowired
    private MQTTService mqttService;

    public IoTSensorServiceImpl(CropFieldRepository cropFieldRepository, MQTTService mqttService) {
        this.cropFieldRepository = cropFieldRepository;

        this.mqttService = mqttService;
    }
    public void subscribeToSensorData(Long cropFieldId) {
        CropField cropField = cropFieldRepository.findById(cropFieldId)
                .orElseThrow(() -> new ResourceNotFoundException("Campo de cultivo no encontrado"));


    }

    // Método para manejar los datos recibidos del sensor
    private void handleSensorData(Long cropFieldId, MqttMessage message) {
        // Aquí debes procesar los datos del sensor, por ejemplo:
        String payload = new String(message.getPayload());
        String[] data = payload.split(",");
        if (data.length == 2) {
            double temperature = Double.parseDouble(data[0]);
            double humidity = Double.parseDouble(data[1]);

        }
    }




}
