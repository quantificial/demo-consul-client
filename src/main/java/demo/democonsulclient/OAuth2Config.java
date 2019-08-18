package demo.democonsulclient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import feign.RequestInterceptor;

@EnableOAuth2Client
@Configuration
public class OAuth2Config extends WebSecurityConfigurerAdapter {

    @Value("${oauth.resource:http://localhost:8081}")
    private String baseUrl;
    @Value("${oauth.authorize:http://localhost:8081/auth/oauth/authorize}")
    private String authorizeUrl;
    @Value("${oauth.token:http://localhost:8081/auth/oauth/token}")
    private String tokenUrl;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/**").permitAll();
		

	}
    
    
    @Bean
    protected OAuth2ProtectedResourceDetails resource() {

        //ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
    	ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
    	
        List scopes = new ArrayList<String>(2);
        scopes.add("write");
        scopes.add("read");
        
        resource.setClientId("appalone");
        resource.setClientSecret("secret");
        resource.setGrantType("client_credentials");
        resource.setAccessTokenUri(tokenUrl);
        resource.setScope(scopes);
        
        
//        resource.setAccessTokenUri(tokenUrl);
//        resource.setClientId("appalone");
//        resource.setClientSecret("secret");
//        resource.setGrantType("password");
//        resource.setScope(scopes);

//        resource.setUsername("**USERNAME**");
//        resource.setPassword("**PASSWORD**");

        return resource;
    }
    
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    @Bean(name="oauth2RestTemplate")
    public OAuth2RestOperations restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();

        //return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
        return new OAuth2RestTemplate(resource());
    }
    
}
