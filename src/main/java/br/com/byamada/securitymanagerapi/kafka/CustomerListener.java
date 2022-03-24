// [KAFKA - AVRO] [step 8] Example of consuming message
package br.com.byamada.securitymanagerapi.kafka;

import br.com.byamada.customerserviceapi.avro.CustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerListener {

/*
    @KafkaListener(
            containerFactory = "customerEventListenerFactory",
            topics = "${kafka.customer.topic}",
            groupId = "${kafka.customer.group-id}"
    )*/
    public void consume(CustomerDTO message){
        CustomerPostRequest cst = CustomerPostRequest.builder()
                .name(message.getName())
                .document(message.getDocument())
                .age(message.getAge())
                .build();

        log.info(" *** SECURITY MANAGER *** Customer Saved from topic {}", cst.toString());
    }

}
