package demo.democonsulclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * test the consul feign client
 * 
 * @author Fu
 *
 */
@FeignClient(name = "demo-consul-producer", path = "/")
public interface ProducerService {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	String hello();

}
