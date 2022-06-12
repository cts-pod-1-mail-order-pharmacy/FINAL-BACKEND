package com.cts.subscriptionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    private String insurancePolicyNumber;
    private String insuranceProvider;
    private Date prescriptionDate;
    @ManyToMany(
            cascade = CascadeType.ALL
    )
    private List<DrugSubscribed> drugs;
    private String dosageDefinitionPerDay;
    private String prescriptionCourse;
    private String doctorDetails;
}
