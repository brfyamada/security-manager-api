//[SPRING SECURITY] [JWT] [STEP 2] Configuring Constraints for Jwt config
package br.com.byamada.securitymanagerapi.config.security;

public class SecurityConstraints {

    public static final String SECRET = "meu-segredo";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    //expiration in one day
    public static final long EXPIRATION_TIME = 86400000L;

}
