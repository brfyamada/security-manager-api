package br.com.byamada.securitymanagerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

//[KAFKA - AVRO] [step 0] adding kafka annotation
@EnableKafka
@SpringBootApplication
public class SecurityManagerApi {
    public static void main(String args[]) {
        SpringApplication.run(SecurityManagerApi.class);
    }
}
