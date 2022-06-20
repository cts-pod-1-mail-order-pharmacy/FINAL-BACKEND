package com.cts.subscriptionservice.FeignClients;

import com.cts.subscriptionservice.VO.Drugs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://drugsservice.us-east-1.elasticbeanstalk.com",name = "drugs-service")
public interface DrugsClient {
    @GetMapping("/drugs/getDispatchableDrugStock/{drugId}/{location}")
    public Drugs getDispatchableDrugStock(@PathVariable Long drugId, @PathVariable String location, @RequestHeader("Authorization") String token);

    @GetMapping("/drugs/isavailable/{drugName}/{location}")
    public boolean isAvailable(@PathVariable String drugName, @PathVariable String location, @RequestHeader("Authorization") String token);

}
