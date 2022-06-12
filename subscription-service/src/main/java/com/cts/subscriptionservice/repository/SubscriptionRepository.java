package com.cts.subscriptionservice.repository;

import com.cts.subscriptionservice.entity.MemberSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<MemberSubscription, Long> {
    List<MemberSubscription> findAllByMemberId(Long memberId);
    MemberSubscription findByMemberSubscriptionId(Long subscriptionId);
}
