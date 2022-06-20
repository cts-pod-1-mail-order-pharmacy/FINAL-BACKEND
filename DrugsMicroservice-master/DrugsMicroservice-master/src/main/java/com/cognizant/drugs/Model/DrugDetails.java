package com.cognizant.drugs.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="drug_details")
public class DrugDetails {
	@Id
    @GeneratedValue
    private Long id;
	@Column(name = "location")
	String location;
	@Column(name = "quantity")
	int quantity;
}
