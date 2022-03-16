//[SPRING SECURITY] [JWT] [STEP 6] Filter to authenticate from system, to do filters you can use GenericFilterBean or BasicAuthenticationFilter
package br.com.byamada.securitymanagerapi.securityFilters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class SystemRequestAuthenticationFilter extends GenericFilterBean {

    @Value("${app.authentication-token}")
    private String authenticationToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = this.getAuthentication((HttpServletRequest) request);
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String appToken = request.getHeader("X-App-Token");
        if(authenticationToken.equals(appToken)){
            return new UsernamePasswordAuthenticationToken("SYSTEM",null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_SYSTEM")));
        }
        return null;
    }
}
