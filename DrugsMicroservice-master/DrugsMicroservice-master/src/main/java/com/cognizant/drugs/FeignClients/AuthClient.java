package com.cognizant.drugs.FeignClients;

import com.cognizant.drugs.Model.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:9191",name = "AUTH-SERVICE")
public interface AuthClient {
    @GetMapping ("/api/validate")
    public AuthResponse validate(@RequestHeader("Authorization") final String token);

}
