package org.example.intento3.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.example.intento3.config.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.converter.MessageConverter;

import java.util.UUID;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.url}")
    private String brokerUrl;

    @Value("${mqtt.client.id.prefix}")
    private String clientIdPrefix;

    @Value("${mqtt.default.topic.subscribe}")
    private String subscribeTopic;

    @Value("${mqtt.default.topic.publish}")
    private String publishTopic;

    // Generar un clientId único
    @Bean
    public String uniqueClientId() {
        return clientIdPrefix + "-" + UUID.randomUUID().toString();
    }

    @Autowired
    private SensorDataService sensorDataService;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{brokerUrl});
        options.setCleanSession(true);
        options.setKeepAliveInterval(60);

        try {
            System.out.println("Conectando al broker MQTT: " + brokerUrl);
        } catch (Exception e) {
            System.err.println("Error al conectar al broker MQTT: " + e.getMessage());
        }
        return options;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory(String uniqueClientId) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions());
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inbound(MqttPahoClientFactory mqttClientFactory, String uniqueClientId) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(uniqueClientId + "_inbound", mqttClientFactory, subscribeTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String payload = (String) message.getPayload();
            System.out.println("Mensaje recibido: " + payload);
            try {
                // Validación básica de JSON
                if (payload.contains("temperature") && payload.contains("humidity")) {
                    ObjectMapper mapper = new ObjectMapper();
                    SensorData data = mapper.readValue(payload, SensorData.class);
                    System.out.println("Datos del Sensor - Humedad: " + data.getHumidity() + ", Temperatura: " + data.getTemperature());

                    // Actualizar el servicio con los nuevos datos
                    sensorDataService.updateSensorData(data);
                } else {
                    System.err.println("El mensaje no contiene los campos esperados.");
                }
            } catch (Exception e) {
                System.err.println("Error al procesar el mensaje MQTT: " + e.getMessage());
            }
        };
    }




    @Bean
    public MessageChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageConverter mqttMessageConverter() {
        return new DefaultPahoMessageConverter();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutputChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory mqttClientFactory, String uniqueClientId) {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(uniqueClientId + "_outbound", mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(publishTopic);
        messageHandler.setConverter(mqttMessageConverter()); // Configurar el convertidor
        return messageHandler;
    }
}