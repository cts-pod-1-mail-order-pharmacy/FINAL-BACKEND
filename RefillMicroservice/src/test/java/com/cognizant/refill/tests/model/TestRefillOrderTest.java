package com.cognizant.refill.tests.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.refill.Model.RefillOrder;

@ExtendWith(MockitoExtension.class)
public class TestRefillOrderTest {

	private RefillOrder refill;
	
	
	 @Test
	    public void testAllGettersAndSetters() throws ParseException {
		  refill=new RefillOrder();
	        String Date1="2020/08/08";
	        String Date2="2021/08/08";
	    	
	    	Date date1=new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
	    	Date date2=new SimpleDateFormat("dd/mm/yyyy").parse(Date2);
	    	
	    	refill.setRefillOrderId(1);
	    	refill.setSubscriptionId(2);
	    	refill.setRefillDate(date1);
	    	refill.setRefillDateNext(date2);
	        refill.setStatus(true);
	        refill.setVisited(true);
	        assertEquals(1,refill.getRefillOrderId());
	        assertEquals(2, refill.getSubscriptionId());
	        assertEquals(date1, refill.getRefillDate());
	        assertEquals(date2, refill.getRefillDateNext());
	        assertEquals(true, refill.isStatus());
	        assertEquals(true, refill.isVisited());
	        
	    }
	

}
