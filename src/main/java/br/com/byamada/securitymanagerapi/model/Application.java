//[SPRING SECURITY] [STEP 10] Creating an Entity to manage applications
package br.com.byamada.securitymanagerapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Table(name="application_auth")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_application_id")
    private Long Id;

    @Column(name = "des_name")
    @NotEmpty
    private String name;

    @Column(name = "des_client_id")
    @NotEmpty
    private String clientId;

    @Column(name = "des_secret_key")
    @NotEmpty
    private String clientSecret;

    @Column(name = "des_owner")
    @NotEmpty
    private String owner;

    @Column(name = "dat_creation")
    private Date creationDate;

    @Column(name = "dat_update")
    private Date updateDate;

    @Column(name = "des_user_operation")
    private String userOperation;


}
