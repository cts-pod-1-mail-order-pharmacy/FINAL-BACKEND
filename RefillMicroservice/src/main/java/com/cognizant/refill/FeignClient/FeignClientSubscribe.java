package com.cognizant.refill.FeignClient;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="SUBSCRIPTION-SERVICE",url="http://localhost:9191")
public interface FeignClientSubscribe {

	@GetMapping("/subscription/getAllDrugs/{subscriptionId}")
	public Map<String, Integer> getAllDrugsOfMember(@PathVariable("subscriptionId") Long subscriptionId);

	@GetMapping("/subscription/getAllSubscriptions/{memberId}")
	public List<Long> getAllSubsIdOfMember(@PathVariable("memberId") Long memberId, @RequestHeader("Authorization") String token);

	@GetMapping("/subscription/getRefillOccurrence/{subscriptionId}")
	public String findRefillOccurrenceBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId);
	
}
