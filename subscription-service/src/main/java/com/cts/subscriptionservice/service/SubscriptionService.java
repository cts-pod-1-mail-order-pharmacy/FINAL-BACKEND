package com.cts.subscriptionservice.service;

import com.cts.subscriptionservice.FeignClients.DrugsClient;
import com.cts.subscriptionservice.FeignClients.RefillClient;
import com.cts.subscriptionservice.VO.Drugs;
import com.cts.subscriptionservice.entity.DrugSubscribed;
import com.cts.subscriptionservice.entity.MemberSubscription;
import com.cts.subscriptionservice.entity.Prescription;
import com.cts.subscriptionservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class SubscriptionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DrugsClient drugsClient;

    @Autowired
    private RefillClient refillClient;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Transactional
    public MemberSubscription save(MemberSubscription memberSubscription){
        return subscriptionRepository.save(memberSubscription);
    }

    @Transactional
    public void delete(Long subId){
        subscriptionRepository.deleteById(subId);
    }
    public List<Long> getAllSubsIdOfMember(Long memberId){
        List<MemberSubscription> subsResult = subscriptionRepository.findAllByMemberId(memberId);
        List<Long> result = new ArrayList<>();
        for(MemberSubscription memberSubscription: subsResult){
            result.add(memberSubscription.getMemberSubscriptionId());
        }
        return result;
    }

    public Map<String, Integer> getAllDrugsOfMember(Long subscriptionId){
        Map<String, Integer> map = new HashMap<>();
        MemberSubscription memberSubscription = subscriptionRepository.findByMemberSubscriptionId(subscriptionId);
        Prescription prescription = memberSubscription.getPrescriptionId();
        List<DrugSubscribed> drugSubscribedList = prescription.getDrugs();
        for(DrugSubscribed drugSubscribed: drugSubscribedList){
            map.put(drugSubscribed.getDrugName(), drugSubscribed.getQuantity());
        }

        return map;
    }

    public String findRefillOccurrenceBySubscriptionId(Long subsId){
        MemberSubscription memberSubscription = subscriptionRepository.findByMemberSubscriptionId(subsId);
        return memberSubscription.getRefillOccurrence();
    }
}
