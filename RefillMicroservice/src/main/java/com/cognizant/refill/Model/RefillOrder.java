package com.cognizant.refill.Model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="refill_order")
public class RefillOrder {
	@Id
	@GeneratedValue
	@Column(name="refill_order_id")
	Long refillOrderId;
	@Column(name="subscription_id")
	Long subscriptionId;
	@Column(name="refill_date")
//	@Temporal(TemporalType.DATE)
	Date refillDate;	
	@Column(name="refill_date_next")
//	@Temporal(TemporalType.DATE)
	Date refillDateNext;	
	@Column(name="status")
	boolean status;
	@Column(name="visited")
	boolean visited;
	@OneToMany(cascade = CascadeType.ALL)
	List<RefillOrderLine> refillOrderLine;
	
	
}
