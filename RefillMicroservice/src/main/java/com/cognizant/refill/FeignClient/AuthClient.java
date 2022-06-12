package com.cognizant.refill.FeignClient;
import com.cognizant.refill.Model.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;



@FeignClient(url="http://localhost:9191",name="AUTH-SERVICE")
public interface AuthClient {
	
	@GetMapping("/api/validate")
	public AuthResponse validate(@RequestHeader("Authorization") String token);


}


