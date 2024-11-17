package org.example.intento3.config;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class SensorDataService {
    private final AtomicReference<SensorData> latestSensorData = new AtomicReference<>();

    public void updateSensorData(SensorData data) {
        latestSensorData.set(data);
        System.out.println("SensorDataService actualizado: " + data);

    }

    public SensorData getLatestSensorData() {
        SensorData data = latestSensorData.get();
        if (data == null) {
            System.err.println("[SensorDataService] No se encontraron datos del sensor.");
        }
        return data;
    }
}
