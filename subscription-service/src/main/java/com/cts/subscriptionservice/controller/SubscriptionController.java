package com.cts.subscriptionservice.controller;

import com.cts.subscriptionservice.FeignClients.AuthClient;
import com.cts.subscriptionservice.FeignClients.DrugsClient;
import com.cts.subscriptionservice.FeignClients.RefillClient;
import com.cts.subscriptionservice.VO.Drugs;
import com.cts.subscriptionservice.entity.AuthResponse;
import com.cts.subscriptionservice.entity.DrugSubscribed;
import com.cts.subscriptionservice.entity.MemberSubscription;
import com.cts.subscriptionservice.entity.Prescription;
import com.cts.subscriptionservice.exception.TokenInvalidException;
import com.cts.subscriptionservice.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    private static final String UNAUTHORIZED_USER= "USER UNAUTHORIZED";

    @Autowired
    private AuthClient authClient;

    @Autowired
    private DrugsClient drugsClient;

    @Autowired
    private RefillClient refillClient;

    @GetMapping("/")
    public String dummyAPI(){
        log.info("In Home!");
        return "Home";
    }

    @PostMapping("/subscription/subscribe/{location}/{refill}")
    public String subscribe(@PathVariable String location, @PathVariable String refill, @RequestBody Prescription prescription, @RequestHeader("Authorization") String token) throws TokenInvalidException{
        AuthResponse authResponse = authClient.validate(token);
        if(!authResponse.isValid()){
            log.info("Invalid!");
            throw new TokenInvalidException(UNAUTHORIZED_USER);
        }

        Prescription p = prescription;
        MemberSubscription memberSubscription = new MemberSubscription();
        Long memberId = Long.parseLong(authResponse.getUid());
        memberSubscription.setMemberLocation(location);
        memberSubscription.setPrescriptionId(p);
        memberSubscription.setMemberId(memberId);

        List<DrugSubscribed> drugSubscribed = prescription.getDrugs();

        for(DrugSubscribed d: drugSubscribed){
            String dr = d.getDrugName();
            if(!drugsClient.isAvailable(dr, location, token)) {
                return "Some of the drugs are not available";
            }
        }
        memberSubscription.setSubscriptionDate(new Date());
        memberSubscription.setRefillOccurrence(refill);

        MemberSubscription subs = subscriptionService.save(memberSubscription);
        Long subscriptionId = subs.getMemberSubscriptionId();
        refillClient.firstRequestRefill(subscriptionId, location, refill, token);
        return "Order Subscribed";
    }

    @GetMapping("/subscription/getAllSubscriptions/{memberId}")
    public List<Long> getAllSubsIdOfMember(@PathVariable("memberId") Long memberId, @RequestHeader("Authorization") String token){
        AuthResponse authResponse = authClient.validate(token);
        if(!authResponse.isValid()){
            log.info("Invalid!");
            throw new TokenInvalidException(UNAUTHORIZED_USER);
        }
        return subscriptionService.getAllSubsIdOfMember(memberId);
    }

    @DeleteMapping("/subscription/unsubscribe/{subscription_id}/")
    public ResponseEntity<?> deleteSubscription(@PathVariable("subscription_id") Long subscriptionid,
                                                @RequestHeader("Authorization") String token) throws TokenInvalidException {
        AuthResponse authResponse = authClient.validate(token);
        if(!authResponse.isValid()){
            log.info("Invalid!");
            throw new TokenInvalidException(UNAUTHORIZED_USER);
        }
        if (refillClient.anyPendings(subscriptionid, token)) {
            log.info("dues!");
            return new ResponseEntity<>("you have not yet cleared all the dues", HttpStatus.PAYMENT_REQUIRED);
        }
        subscriptionService.delete(subscriptionid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/subscription/getAllDrugs/{subscriptionId}")
    public Map<String, Integer> getAllDrugsOfMember(@PathVariable("subscriptionId") Long subscriptionId){
        log.info("All the subscribed drugs!");
        return subscriptionService.getAllDrugsOfMember(subscriptionId);
    }

    @GetMapping("/subscription/getRefillOccurrence/{subscriptionId}")
    public String findRefillOccurrenceBySubscriptionId(@PathVariable("subscriptionId") Long subscriptionId){
        log.info("refill by quantity avail!");
        return subscriptionService.findRefillOccurrenceBySubscriptionId(subscriptionId);
    }
}
