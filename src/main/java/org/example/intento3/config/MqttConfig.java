package org.example.intento3.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.example.intento3.config.SensorData;
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

    // Configuración de opciones de conexión
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { brokerUrl });
        options.setCleanSession(true);
        options.setKeepAliveInterval(60);
        // No se establecen username y password ya que no se requieren
        return options;
    }

    // Fábrica de clientes MQTT
    @Bean
    public MqttPahoClientFactory mqttClientFactory(String uniqueClientId) {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions());
        return factory;
    }

    // Canal de entrada para recibir mensajes MQTT
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    // Adaptador para recibir mensajes MQTT
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

    // Manejador para procesar mensajes entrantes
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String payload = (String) message.getPayload();
            System.out.println("Mensaje recibido: " + payload);
            try {
                // Usando Jackson para convertir JSON a SensorData
                ObjectMapper mapper = new ObjectMapper();
                SensorData data = mapper.readValue(payload, SensorData.class);
                System.out.println("Datos del Sensor - Humedad: " + data.getHumidity() + ", Temperatura: " + data.getTemperature());
                // Aquí puedes almacenar los datos en una base de datos, enviar a otro servicio, etc.
            } catch (Exception e) {
                System.err.println("Error al procesar el mensaje MQTT: " + e.getMessage());
            }
        };
    }

    // Canal de salida para enviar mensajes MQTT
    @Bean
    public MessageChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    // Convertidor de mensajes para outbound
    @Bean
    public MessageConverter mqttMessageConverter() {
        return new DefaultPahoMessageConverter();
    }

    // Manejador para publicar mensajes MQTT
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