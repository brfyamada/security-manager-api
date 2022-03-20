package br.com.byamada.securitymanagerapi.kafka;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CustomerPostRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String document;

    private int age;
}
