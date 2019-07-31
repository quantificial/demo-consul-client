package demo.democonsulclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/services")
    public Object services() {
        return discoveryClient.getInstances("demo-consul-producer");
    }

    @RequestMapping("/discover")
    public Object discover() {
        return loadBalancer.choose("demo-consul-producer").getUri().toString();
    }
}