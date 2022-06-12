package com.cts.subscriptionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MemberSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSubscriptionId;
    private Long memberId;
    private Date subscriptionDate;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    private Prescription prescriptionId;
    private String refillOccurrence;
    private String memberLocation;
    private boolean subscriptionStatus;
}
