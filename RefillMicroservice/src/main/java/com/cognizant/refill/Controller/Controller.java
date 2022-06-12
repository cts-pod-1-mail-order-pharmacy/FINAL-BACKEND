package com.cognizant.refill.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;
import java.util.Map;

import com.cognizant.refill.Model.AuthResponse;
import com.cognizant.refill.VO.Drugs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cognizant.refill.Model.RefillOrder;
import com.cognizant.refill.Model.RefillOrderLine;
import com.cognizant.refill.Service.RefillOrderService;

import com.cognizant.refill.Exception.*;
import com.cognizant.refill.FeignClient.FeignClientDrugs;
import com.cognizant.refill.FeignClient.FeignClientSubscribe;

@Slf4j
@RestController
@RequestMapping("refill")
public class Controller {
	@Autowired
	private com.cognizant.refill.FeignClient.AuthClient authClient;
	@Autowired
	RefillOrderService refillOrderService;
	@Autowired
	FeignClientDrugs feignClientDrugs;
	@Autowired
	FeignClientSubscribe feignClientSubscribe;
	private static final String UNAUTHORIZED_USER ="UNAUTHORIZED USER";
	@GetMapping("/viewRefillStatusAll/{subscriptionId}")
	public List<RefillOrder> viewRefillStatusAll(@PathVariable Long subscriptionId,
			@RequestHeader("Authorization") String token) throws Exception {
		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Long memberId = Long.parseLong(authResponse.getUid());

		List<Long> subscriptionIds = feignClientSubscribe.getAllSubsIdOfMember(memberId, token);
		for (Long subId : subscriptionIds) {
			log.info("subId"+subId);
			if (subId == subscriptionId) {
				return refillOrderService.viewRefillStatus(subscriptionId);
			}
		}
		log.info("this member do not have any sub id like this one");
		throw new TokenInvalidException("Not Authorised");

	}

	@GetMapping("/viewRefillStatus/{subscriptionId}")
	public List<RefillOrder> viewRefillStatus(@PathVariable Long subscriptionId,
			@RequestHeader("Authorization") String token) throws Exception {
		log.info("inside viewRefillStatus");
		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Long memberId = Long.parseLong(authResponse.getUid());
		List<Long> subscriptionIds = feignClientSubscribe.getAllSubsIdOfMember(memberId, token);
		System.out.println(subscriptionIds);
		for (Long subId : subscriptionIds) {
			if (subId == subscriptionId) {
				return refillOrderService.viewRefillStatusLatest(subscriptionId);
			}
		}
		throw new Exception("Not Authorised");
	}

	@PostMapping("/requestAdhocRefill")
	public ResponseEntity<?> requestAdhocRefill(@RequestParam("Subscription_ID") Long subscriptionId,
			@RequestParam("Policy_ID") int policyId, @RequestParam("Location") String location,
			@RequestBody List<RefillOrderLine> morePrescription, @RequestHeader("Authorization") String token) {
		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Map<String, Integer> drugs = feignClientSubscribe.getAllDrugsOfMember(subscriptionId);
		List<RefillOrderLine> refilOrderLine = new ArrayList<>();
		System.out.println(morePrescription);
		for (RefillOrderLine refillOrderLineNew : morePrescription) {

			if (drugs.get(refillOrderLineNew.getDrug()) == null
					|| !feignClientDrugs.isAvailable(refillOrderLineNew.getDrug(),location,token)) {
				return new ResponseEntity<>("refill request failed", HttpStatus.NOT_FOUND);
			}

			int quantity = drugs.get(refillOrderLineNew.getDrug()) + refillOrderLineNew.getDrugQuantity();
			refilOrderLine.add(new RefillOrderLine(refillOrderLineNew.getDrug(), quantity));
		}

		RefillOrder refillOrder = new RefillOrder();
		refillOrder.setRefillDate(new Date());
		refillOrder.setRefillDateNext(new Date());
		refillOrder.setSubscriptionId(subscriptionId);
		refillOrder.setRefillOrderLine(refilOrderLine);
		refillOrder.setVisited(true);
		RefillOrder refillOrderSave = refillOrderService.save(refillOrder);
		return new ResponseEntity<>(refillOrderSave, HttpStatus.CREATED);
	}

	@GetMapping("/getRefillDuesAsOfDate")
	public List<RefillOrder> getRefillDuesAsOfDate(@RequestParam("date") String date,
			@RequestParam("Subscription_ID") Long subscriptionid, @RequestHeader("Authorization") String token)
			throws ParseException {
		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Date d = new SimpleDateFormat("yyyy/MM/dd").parse(date);
		return refillOrderService.getRefillDuesAsOfDate(d, subscriptionid);
	}

	@GetMapping("/anyPendings/{subscription_id}")
	public boolean anyPendings(@PathVariable("subscription_id") Long subscriptionid,
			@RequestHeader("Authorization") String token) {
		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		return refillOrderService.anyPendings(subscriptionid);
	}



	@PostMapping("/firstRequestRefill/{Subscription_ID}/{location}/{refill_occurence}")
	public ResponseEntity<?> firstRequestRefill(@PathVariable("Subscription_ID") Long subscriptionId,
			@PathVariable String location, @PathVariable("refill_occurence") String refillOccurence,
			@RequestHeader("Authorization") String token) {
		Map<String, Integer> drugs = feignClientSubscribe.getAllDrugsOfMember(subscriptionId);

		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		List<RefillOrderLine> refilOrderLine = new ArrayList<>();
		for (Map.Entry<String, Integer> map : drugs.entrySet()) {
			String drug = map.getKey();
			int quantity = map.getValue();
			if (!feignClientDrugs.isAvailable(drug,location,token)) {
				return new ResponseEntity<>("refill request failed", HttpStatus.NOT_FOUND);
			}
			refilOrderLine.add(new RefillOrderLine(drug, quantity));
		}
		RefillOrder refillOrder = new RefillOrder();
		refillOrder.setRefillDate(new Date());

		refillOrder.setRefillDateNext(new Date());
		refillOrder.setSubscriptionId(subscriptionId);
		refillOrder.setRefillOrderLine(refilOrderLine);
		refillOrder.setVisited(false);
		RefillOrder refillOrderSave = refillOrderService.save(refillOrder);
		refillOrderService.checkDaily();
		return new ResponseEntity<>(refillOrderSave, HttpStatus.CREATED);

	}
	@PostMapping("/requestRefill")
	public ResponseEntity<?> requestRefill(@RequestParam("Subscription_ID") Long subscriptionId,
			@RequestHeader("Authorization") String token) {
		AuthResponse authResponse = authClient.validate(token);
		if (!authResponse.isValid()) {
			log.info("invalid");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Map<String, Integer> drugs = feignClientSubscribe.getAllDrugsOfMember(subscriptionId);

		List<RefillOrderLine> refilOrderLine = new ArrayList<>();
		for (Map.Entry<String, Integer> map : drugs.entrySet()) {
			String drug = map.getKey();
			int quantity = map.getValue();
			refilOrderLine.add(new RefillOrderLine(drug, quantity));
		}
		RefillOrder refillOrder = new RefillOrder();
		refillOrder.setRefillDate(new Date());
		refillOrder.setRefillDateNext(new Date());
		refillOrder.setSubscriptionId(subscriptionId);
		refillOrder.setRefillOrderLine(refilOrderLine);
		refillOrder.setVisited(false);
		RefillOrder refillOrderSave = refillOrderService.save(refillOrder);
		refillOrderService.checkDaily();
		return new ResponseEntity<>(refillOrderSave, HttpStatus.CREATED);
	}
}
