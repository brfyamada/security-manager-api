//[SPRING SECURITY] [JWT] [STEP 9] Define Custom method to force PreAuthorize ROLE_SYSTEM to all methods
package br.com.byamada.securitymanagerapi.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.ExpressionBasedAnnotationAttributeFactory;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class GlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        log.info("GlobalMethodSecurityConfiguration load into the application");
        ExpressionBasedAnnotationAttributeFactory attributeFactory = new ExpressionBasedAnnotationAttributeFactory(getExpressionHandler());
        return new SecurityRoleSystemConfigPreAuthorize(attributeFactory);
    }
}
