package org.example.intento3.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;


/**
@Service
public class MqttPublisherService {

    private final MessageChannel mqttOutputChannel;

    @Autowired
    public MqttPublisherService(MessageChannel mqttOutputChannel) {
        this.mqttOutputChannel = mqttOutputChannel;
    }

    public void sendControlMessage(int controlValue) {
        try {
            String payload = "{\"control\":" + controlValue + "}";
            mqttOutputChannel.send(MessageBuilder.withPayload(payload).build());
            System.out.println("Mensaje enviado: " + payload);
        } catch (Exception e) {
            System.err.println("Error al enviar mensaje MQTT: " + e.getMessage());
        }
    }
}
 **/


@Service
public class MqttPublisherService {

    private final MessageChannel mqttOutputChannel;

    @Autowired
    public MqttPublisherService(MessageChannel mqttOutputChannel) {
        this.mqttOutputChannel = mqttOutputChannel;
    }

    public void sendControlMessage(int controlValue) {
        try {
            String payload = "{\"control\":" + controlValue + "}";
            mqttOutputChannel.send(MessageBuilder.withPayload(payload).build());
            System.out.println("Mensaje enviado: " + payload);
        } catch (Exception e) {
            System.err.println("Error al enviar mensaje MQTT: " + e.getMessage());
        }
    }
}
