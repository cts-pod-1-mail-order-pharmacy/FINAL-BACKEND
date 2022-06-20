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
public class DrugDetailsTest {

	private DrugDetails drugD;
	
	
	 @Test
	    public void testAllGettersAndSetters() throws ParseException {
		 drugD = new DrugDetails();
	        
	        drugD.setId(1l);
	        drugD.setLocation("xyz");
	        drugD.setQuantity(20);
	       
	        assertEquals(1,drugD.getId());
	        assertEquals("xyz",drugD.getLocation());
	        assertEquals(20, drugD.getQuantity());
	        
	    }
	

}
