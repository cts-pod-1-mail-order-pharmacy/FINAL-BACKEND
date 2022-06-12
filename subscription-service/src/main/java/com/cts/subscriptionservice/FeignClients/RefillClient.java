package com.cts.subscriptionservice.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "REFILL-SERVICE",url = "http://localhost:9191")
public interface RefillClient {
    @GetMapping("/refill/anyPendings/{subscription_id}")
    public boolean anyPendings(@PathVariable("subscription_id") Long subscriptionid, @RequestHeader("Authorization")String token);

    @PostMapping("/refill/firstRequestRefill/{Subscription_ID}/{location}/{refill_occurence}")
    public ResponseEntity<?> firstRequestRefill(@PathVariable("Subscription_ID") Long subscriptionId, @PathVariable String location, @PathVariable("refill_occurence") String refilloccurence, @RequestHeader("Authorization")String token);
}
