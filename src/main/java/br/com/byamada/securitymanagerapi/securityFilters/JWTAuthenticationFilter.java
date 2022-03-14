//[SPRING SECURITY] [JWT] [STEP 4] Filter to do the authentication , retrieve data from token and create
// an UsernamePasswordAuthenticationToken, to do filters you can use GenericFilterBean or BasicAuthenticationFilter

package br.com.byamada.securitymanagerapi.securityFilters;

import br.com.byamada.securitymanagerapi.service.UserDerailsService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.byamada.securitymanagerapi.config.security.SecurityConstraints.*;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private final UserDerailsService userDerailsService;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDerailsService userDerailsService) {
        super(authenticationManager);
        this.userDerailsService = userDerailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if(header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);
        if(token == null) return null;
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        UserDetails userDetails = userDerailsService.loadUserByUsername(username);
        return username != null ?
                new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities())
                : null;
    }
}
