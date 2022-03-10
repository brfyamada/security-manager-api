//[SPRING SECURITY] [STEP 5] Creating the configuration for spring security
package br.com.byamada.securitymanagerapi.config;

import br.com.byamada.securitymanagerapi.service.UserDerailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDerailsService userDerailsService;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/webjars/**",
            "/actuator/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        //http.authorizeRequests().antMatchers("/**").permitAll();
        http.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest()
                .authenticated()
        .and()
        .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //só para gerar password de teste
        log.info("Password encoder {}", passwordEncoder.encode("admin"));

        auth.userDetailsService(userDerailsService).passwordEncoder(passwordEncoder);
    }


}
