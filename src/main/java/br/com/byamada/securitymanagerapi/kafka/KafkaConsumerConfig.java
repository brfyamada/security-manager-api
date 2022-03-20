//[KAFKA - AVRO] [step 7] Configuration for KafkaConsumer

package br.com.byamada.securitymanagerapi.kafka;

import br.com.byamada.customerserviceapi.avro.CustomerDTO;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.customer.topic}")
    private String customerTopic;

    @Value(value = "${kafka.customer.group-id}")
    private String groupId;

    @Value(value = "${kafka.offset.latest}")
    private String offsetLatest;

    @Value(value = "${kafka.offset.earliest}")
    private String offsetEarliest;

    @Value(value = "${kafka.broker-url}")
    private String brokerUrls;

    @Value(value = "${kafka.schema-registry.url}")
    private String schemaRegistry;

    @Bean("customerEventListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, CustomerDTO> customerEventListener() {
        ConcurrentKafkaListenerContainerFactory<String, CustomerDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> props = createAvroProperties(groupId, offsetEarliest);
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        return factory;
    }

    private Map<String, Object> createAvroProperties(String groupId, String offset){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerUrls);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, KafkaAvroDeserializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);
        return props;
    }



}
