package com.cognizant.drugs.tests.model;

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
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.drugs.Model.DrugDetails;
import com.cognizant.drugs.Model.Drugs;
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class DrugsTest {

	private Drugs drugs;
	
	
	 @Test
	    public void testAllGettersAndSetters() throws ParseException {
		 drugs = new Drugs();
	        String Date1="2020/08/08";
	        String Date2="2021/08/08";
	    	List<DrugDetails> drugDetails=new ArrayList<DrugDetails>();
	    	
	    	Date date1=new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
	    	Date date2=new SimpleDateFormat("dd/mm/yyyy").parse(Date2);
	    	
	        drugs.setId(1l);
	        drugs.setName("xyz");
	        drugs.setManufacturer("hat");
	        drugs.setManufacturedDate(date1);
	        drugs.setExpiryDate(date2);
	        drugs.setUnitsPackage(50);
	        drugs.setCost(200.00);
		 
	        assertEquals(1,drugs.getId());
	        assertEquals("xyz",drugs.getName());
	        assertEquals("hat", drugs.getManufacturer());
	        assertEquals(date1, drugs.getManufacturedDate());
	        assertEquals(date2, drugs.getExpiryDate());
	        assertEquals(50, drugs.getUnitsPackage());
	        assertEquals(200.00, drugs.getCost());
	        
	    }
	

}
