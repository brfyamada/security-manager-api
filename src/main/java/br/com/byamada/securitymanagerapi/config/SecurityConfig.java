//[SPRING SECURITY] [STEP 5] Creating the configuration for spring security
package br.com.byamada.securitymanagerapi.config;

import br.com.byamada.securitymanagerapi.securityFilters.JWTAuthenticationFilter;
import br.com.byamada.securitymanagerapi.securityFilters.JWTLoginFilter;
import br.com.byamada.securitymanagerapi.securityFilters.SystemRequestAuthenticationFilter;
import br.com.byamada.securitymanagerapi.service.UserDerailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.com.byamada.securitymanagerapi.config.SecurityConstraints.SIGN_UP_URL;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDerailsService userDerailsService;

    private final SystemRequestAuthenticationFilter systemRequestAuthenticationFilter;

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
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
                .antMatchers("/*/protected/**").hasRole("USER")
                .antMatchers("/*/admin/**").hasRole("ADMIN")
                //[SPRING SECURITY] [JWT] [STEP 7] Add filter to spring security configuration
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter( authenticationManager(), userDerailsService))
                .addFilterBefore(systemRequestAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //só para gerar password de teste
        //log.info("Password encoder {}", passwordEncoder.encode("admin"));
        auth.userDetailsService(userDerailsService).passwordEncoder(passwordEncoder);
    }


}
