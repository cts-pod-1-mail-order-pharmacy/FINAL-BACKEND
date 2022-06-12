package com.cognizant.refill.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drugs {
    private Long id;
    private String name;
    private String manufacturer;
    private Date manufacturedDate;
    private Date expiryDate;
    private int unitsPackage;
    private Double cost;
    private List<DrugDetails> drugDetails;
}