package com.cognizant.refill.tests.service;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cognizant.refill.Model.RefillOrder;
import com.cognizant.refill.Model.RefillOrderLine;

public class RefillOrderTest {
	
	private static RefillOrderLine refillOrderLine;
	private static RefillOrder refillOrder;	
	private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");	
	

	
	public static final int EXPECTED_REFILL_ORDER_ID =123;
	public static final int EXPECTED_SUBSCRIPTION_ORDER_ID =310;
	public static Date EXPECTED_REFILL_DATE;
	public static Date EXPECTED_REFILL_DATE_NEXT;
	public static final boolean EXPECTED_STATUS = true;
	public static final boolean EXPECTED_VISITED = true;
	public static  List<RefillOrderLine> EXPECTED_REFILL_ORDER_LINE;
	public static final String EXPECTED_DRUG = "paracetamol";
	public static final int EXPECTED_DRUG_QUANTITY =10;
	
	@BeforeClass
	public static void setUp() throws ParseException
	{		
		EXPECTED_REFILL_DATE=formatter.parse("2020-01-10");
		EXPECTED_REFILL_DATE_NEXT=formatter.parse("2020-01-20");
		
		refillOrder=new RefillOrder();		
		refillOrder.setRefillOrderId(EXPECTED_REFILL_ORDER_ID);
		refillOrder.setSubscriptionId(EXPECTED_SUBSCRIPTION_ORDER_ID);
		refillOrder.setStatus(EXPECTED_STATUS);
		refillOrder.setVisited(EXPECTED_VISITED);
		refillOrder.setRefillDate(EXPECTED_REFILL_DATE);
		refillOrder.setRefillDateNext(EXPECTED_REFILL_DATE_NEXT);
		
		refillOrderLine=new RefillOrderLine();
		refillOrderLine.setDrug(EXPECTED_DRUG);
		refillOrderLine.setDrugQuantity(EXPECTED_DRUG_QUANTITY);	
		List<RefillOrderLine> list=new ArrayList<RefillOrderLine>();
		list.add(refillOrderLine);	
		
		EXPECTED_REFILL_ORDER_LINE=list;
		refillOrder.setRefillOrderLine(EXPECTED_REFILL_ORDER_LINE);
	}
	
	
	 @Test
	    public void testRefillOrderDetails() throws Exception {
		 
		 assertEquals(EXPECTED_REFILL_ORDER_ID, refillOrder.getRefillOrderId());
		 assertEquals(EXPECTED_SUBSCRIPTION_ORDER_ID, refillOrder.getSubscriptionId());
		 assertEquals(EXPECTED_STATUS, refillOrder.isStatus());
		 assertEquals(EXPECTED_VISITED, refillOrder.isVisited());	
		 assertEquals(EXPECTED_REFILL_DATE, refillOrder.getRefillDate());
		 assertEquals(EXPECTED_REFILL_DATE_NEXT, refillOrder.getRefillDateNext());	 
		 assertEquals(EXPECTED_REFILL_ORDER_LINE, refillOrder.getRefillOrderLine());

	    }

	

}

