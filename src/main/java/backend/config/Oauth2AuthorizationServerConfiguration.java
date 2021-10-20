package backend.config;

import backend.service.oauth.Oauth2ClientDetailsService;
import backend.service.oauth.Oauth2UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final Oauth2ClientDetailsService oauth2ClientDetailsService;

    private final Oauth2UserDetailsService oauth2UserDetailsService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final AccessTokenConverter accessTokenConverter;

    private final TokenStore tokenStore;

    public Oauth2AuthorizationServerConfiguration(Oauth2ClientDetailsService oauth2ClientDetailsService,
                                                  Oauth2UserDetailsService oauth2UserDetailsService,
                                                  AuthenticationManager authenticationManager,
                                                  PasswordEncoder passwordEncoder,
                                                  AccessTokenConverter accessTokenConverter,
                                                  TokenStore tokenStore) {
        this.oauth2ClientDetailsService = oauth2ClientDetailsService;
        this.oauth2UserDetailsService = oauth2UserDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenConverter = accessTokenConverter;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer server) throws Exception {
        server.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(oauth2ClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .userDetailsService(oauth2UserDetailsService)
        ;
    }

}
