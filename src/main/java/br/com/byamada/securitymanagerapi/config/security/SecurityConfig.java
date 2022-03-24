//[SPRING SECURITY] [STEP 5] Creating the configuration for spring security
package br.com.byamada.securitymanagerapi.config.security;

import br.com.byamada.securitymanagerapi.securityFilters.JWTAuthenticationFilter;
import br.com.byamada.securitymanagerapi.securityFilters.JWTLoginFilter;
import br.com.byamada.securitymanagerapi.securityFilters.SystemRequestAuthenticationFilter;
import br.com.byamada.securitymanagerapi.service.UserDerailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.com.byamada.securitymanagerapi.config.security.SecurityConstraints.SIGN_UP_URL;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
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
                //.antMatchers("/*/admin/**").hasRole("ADMIN")
                //[SPRING SECURITY] [JWT] [STEP 7] Add filter to spring security configuration
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JWTAuthenticationFilter( authenticationManager(), userDerailsService))
                .addFilterBefore(systemRequestAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //só para gerar password de teste
        //log.info("Password encoder {}", passwordEncoder().encode("admin"));
        auth.userDetailsService(userDerailsService).passwordEncoder(passwordEncoder());
    }

    //[SPRING SECURITY] [OAUTH2] [AUTHORIZATION SERVER] [Step 3] It's necessary to config OauthConfig class
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
