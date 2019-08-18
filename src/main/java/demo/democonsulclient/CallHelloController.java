package demo.democonsulclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CallHelloController {

    @Autowired
    private LoadBalancerClient loadBalancer;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ProducerService producerService;
    
    @RequestMapping("/useFeign")
    public String useFeign() {
    	
    	//return producerService.greeting("3");
    	return producerService.hello();
    }

    @RequestMapping("/call")
    public String call() {
        ServiceInstance serviceInstance = loadBalancer.choose("demo-consul-producer");

        System.out.println("service address： " + serviceInstance.getUri());
        System.out.println("service id： " + serviceInstance.getServiceId());

        String callServiceResult = new RestTemplate().getForObject(serviceInstance.getUri().toString() + "/hello", String.class);
        System.out.println(callServiceResult);
        return callServiceResult;
    }
    
    @RequestMapping("/useExchange")
    public String useExchange() {
    	
    	//String response = restTemplate.exchange("http://student-service/getStudentDetailsForSchool/{schoolname}", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, null).getBody();
    	
    	// HttpHeaders requestHeaders = new HttpHeaders();
        //set up HTTP Basic Authentication Header
    	//String authorizationHeader = "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
        //requestHeaders.add("Authorization", authorizationHeader);
        //requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        //request entity is created with request headers
        //HttpEntity<CallHelloController> requestEntity = new HttpEntity<>(requestHeaders);
        
    	// load balanced request by using exchange
    	String response = restTemplate.exchange("http://demo-consul-producer/hello", HttpMethod.GET, null, String.class).getBody();
    	
    	// load balanced request by using getForObject
    	//String response = restTemplate.getForObject("http://service-consul-producer/hello", String.class);
    	
    	return response;
    }
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}