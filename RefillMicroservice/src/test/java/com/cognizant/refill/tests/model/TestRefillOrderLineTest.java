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
import com.cognizant.refill.Model.RefillOrderLine;

@ExtendWith(MockitoExtension.class)
public class TestRefillOrderLineTest {

	private RefillOrderLine refill;

	@Test
	public void testAllGettersAndSetters() throws ParseException {

		refill = new RefillOrderLine();

		refill.setId(1);
		refill.setDrug("xyz");
		refill.setDrugQuantity(43);



		assertEquals(1, refill.getId());
		assertEquals("xyz", refill.getDrug());
		assertEquals(43, refill.getDrugQuantity());

	}

}
