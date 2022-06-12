package com.cognizant.refill.tests.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cognizant.refill.Model.RefillOrderLine;

public class RefillOrderLineTest {
	
	private static RefillOrderLine refillOrderLine;
	
	public static final String EXPECTED_DRUG = "paracetamol";
	public static final int EXPECTED_DRUG_QUANTITY =10;
	
	@BeforeClass
	public static void setUp()
	{
		refillOrderLine=new RefillOrderLine();
		refillOrderLine.setDrug(EXPECTED_DRUG);
		refillOrderLine.setDrugQuantity(EXPECTED_DRUG_QUANTITY);		
	}
	
	@Test
	public void testGetDrug()
	{
		assertEquals(EXPECTED_DRUG, refillOrderLine.getDrug());
	}
	
	@Test
	public void testGetDrugQuantity()
	{
		assertEquals(EXPECTED_DRUG_QUANTITY, refillOrderLine.getDrugQuantity());
	}	

}
