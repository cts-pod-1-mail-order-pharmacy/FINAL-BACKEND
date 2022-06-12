package com.cognizant.refill.FeignClient;

import com.cognizant.refill.VO.Drugs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="DRUGS-SERVICE",url="http://localhost:9191")
public interface FeignClientDrugs {
	@GetMapping("/drugs/getDispatchableDrugStock/{drugId}/{location}")
	public Drugs getDispatchableDrugStock(@PathVariable Long drugId, @PathVariable String location, @RequestHeader("Authorization") String token);

	@GetMapping("/drugs/isavailable/{drugName}/{location}")
	public boolean isAvailable(@PathVariable String drugName, @PathVariable String location, @RequestHeader("Authorization") String token);
}
