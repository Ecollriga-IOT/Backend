package org.example.intento3.service.impl;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
public class MQTTService {
    private MqttClient mqttClient;

    //private String brokerUrl = "tcp://mqtt-dashboard.com:1883";  // URL del broker MQTT
    private String brokerUrl = "tcp://mqtt-dashboard.com:8884";  // URL del broker MQTT

    private String clientId = "backend-client-" + Math.random(); // Client ID
    private String subscribeTopic = "topics/mobile_00001/RQ"; // El tema para suscribirse
    private String publishTopic = "topics/esp32_00001"; // El tema para publicar
    private MqttConnectOptions options;

    public void connect() throws MqttException {
        options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName("");  // Usuario si es necesario
        options.setPassword("".toCharArray());  // Contraseña si es necesario

        mqttClient = new MqttClient(brokerUrl, clientId);
        mqttClient.connect(options);
        System.out.println("Conectado al broker MQTT: " + brokerUrl);
    }

    public void subscribeToTopic() throws MqttException {
        mqttClient.subscribe(subscribeTopic, (topic, message) -> {
            System.out.println("Mensaje recibido en el tema " + topic + ": " + new String(message.getPayload()));

            // Procesamos los datos de temperatura y humedad
            try {
                String payload = new String(message.getPayload());
                String[] data = payload.split(",");  // Asumimos que los valores están separados por coma

                // Si el formato es correcto, parseamos los datos de temperatura y humedad
                if (data.length == 2) {
                    double temperature = Double.parseDouble(data[0].trim());
                    double humidity = Double.parseDouble(data[1].trim());

                    // Llamamos a un método para procesar estos datos (puedes almacenarlos en una base de datos o tomar decisiones)
                    handleSensorData(temperature, humidity);
                } else {
                    System.out.println("Datos recibidos no válidos");
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al procesar los datos del sensor");
            }
        });
    }

    public void publishMessage(String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttClient.publish(publishTopic, mqttMessage);
        System.out.println("Mensaje publicado en el tema " + publishTopic + ": " + message);
    }

    public void disconnect() throws MqttException {
        mqttClient.disconnect();
        System.out.println("Desconectado del broker");
    }

    // Método para manejar los datos del sensor
    private void handleSensorData(double temperature, double humidity) {
        System.out.println("Temperatura recibida: " + temperature + "°C");
        System.out.println("Humedad recibida: " + humidity + "%");

        // Aquí puedes actualizar los registros en la base de datos o tomar decisiones basadas en los valores
        // Por ejemplo, actualizar las condiciones ideales de riego para un campo específico.
    }
}
