package demo.democonsulclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * test the consul feign client
 * 
 * @author Fu
 *
 */
@FeignClient(name = "demo-consul-producer", path = "/")
public interface ProducerService {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello();
	
	@RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public String hello2();
    
	@RequestMapping(value = "/hello3", method = RequestMethod.GET)
    public String greeting(@RequestParam(value="id", defaultValue="uniqueId") String id );

}
