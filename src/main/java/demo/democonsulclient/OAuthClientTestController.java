package demo.democonsulclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.client.OAuth2RestOperations;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OAuthClientTestController {

    
    @Autowired
    @Qualifier("oauth2RestTemplate")
    private OAuth2RestOperations restTemplate;
    
    @Autowired
    private MeService meService;
    
    @RequestMapping("/me")
    public String me() {
    	
    	//return producerService.greeting("3");
    	return meService.me();
    }
    
    @RequestMapping("/me/test")
    public String test() {
    	return meService.test();
    }
    
    @RequestMapping("/me/token")
    public String token() {
    	OAuth2AccessToken accesstoken = restTemplate.getAccessToken();
    	
    	return accesstoken.getValue();
    	
    }
    
//    @Bean(name="oauth2RestTemplate")
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
    
}
