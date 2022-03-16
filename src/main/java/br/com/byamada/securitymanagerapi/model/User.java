//[SPRING SECURITY] [STEP 2] Creating an Entity to manage users
package br.com.byamada.securitymanagerapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "user_auth")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_user_id")
    private Long Id;

    @Column(name = "des_name")
    @NotEmpty
    private String name;

    @Column(name = "des_email")
    @NotEmpty
    @Email
    private String email;

    @Column(name = "des_password")
    @NotEmpty
    @Size(min = 6, message = "The min size of password is 6")
    private String password;

    @Column(name = "des_authorities")
    private String authorities;

    @Column(name = "dat_creation")
    private Date creationDate;

    @Column(name = "dat_update")
    private Date updateDate;

    @Column(name = "des_user_operation")
    private String userOperation;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
