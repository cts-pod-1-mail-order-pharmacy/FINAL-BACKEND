package com.cognizant.refill.Model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="refill_order_line")
public class RefillOrderLine {
	public RefillOrderLine(String drug, int drugQuantity) {
		super();
		this.drug = drug;
		this.drugQuantity = drugQuantity;
	}
	@Id
	@GeneratedValue
	Long id;
	@Column(name = "drug")
	private String drug;
	@Column(name="drug_quantity")
	private int drugQuantity;
	
}
