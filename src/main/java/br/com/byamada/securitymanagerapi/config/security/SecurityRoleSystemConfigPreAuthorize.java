//[SPRING SECURITY] [JWT] [STEP 8] It's a Optional Config class, create that if you need to force set a role to all controller methods
package br.com.byamada.securitymanagerapi.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PrePostInvocationAttributeFactory;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class SecurityRoleSystemConfigPreAuthorize extends AbstractFallbackMethodSecurityMetadataSource {

    private static final String ROLE_VERIFICATION = "hasRole('ROLE_SYSTEM')";

    private final PrePostInvocationAttributeFactory attributeFactory;


    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        Annotation[] annotations = method.getAnnotations();
        if(AnnotationUtils.findAnnotation(targetClass, Controller.class) != null && annotations != null) {
            var attributes = new ArrayList<ConfigAttribute>();

            for(Annotation a: annotations) {
                if(a instanceof PreAuthorize) {
                    String preAuthorizeAttribute = "(" + ((PreAuthorize) a).value() + ") || "+ ROLE_VERIFICATION;
                    attributes.add(attributeFactory.createPreInvocationAttribute(null, null, preAuthorizeAttribute));
                }
            }
            return attributes;
        }
        return Collections.emptyList();
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
