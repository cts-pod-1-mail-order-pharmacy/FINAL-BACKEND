package com.cognizant.drugs.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "drug")
public class Drugs {
    

	 @Id
	 @GeneratedValue
	 private Long id;
	
	 @Column(name = "name")
	 private String name;
	 @Column(name = "manufacturer")
	 private String manufacturer;
	 @Column(name = "manufactured_date")
	 private Date manufacturedDate;
	 @Column(name = "expiry_date")
	 private Date expiryDate;
	 @Column(name = "units_package")
	 private int unitsPackage;
	 @Column(name = "cost")
	 private Double cost;
	 
	 @ManyToMany(cascade = CascadeType.ALL)
	 @JoinTable
	 private List<DrugDetails> drugDetails;

}
