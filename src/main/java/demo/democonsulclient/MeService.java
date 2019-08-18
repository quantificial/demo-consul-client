package demo.democonsulclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "oauth2-jwt-resource", path = "/", configuration = OAuth2Config.class)
public interface MeService {

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public String me();
	
	@RequestMapping(value = "/me/test", method = RequestMethod.GET)
    public String test();
}
