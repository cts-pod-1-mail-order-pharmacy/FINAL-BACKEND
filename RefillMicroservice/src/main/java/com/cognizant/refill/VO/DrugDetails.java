package com.cognizant.refill.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugDetails {
    private Long id;
    private String location;
    private int quantity;
}
