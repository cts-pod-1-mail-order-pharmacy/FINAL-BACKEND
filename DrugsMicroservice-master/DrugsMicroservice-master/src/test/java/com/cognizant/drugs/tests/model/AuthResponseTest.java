package com.cognizant.drugs.tests.model;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import com.cognizant.drugs.Model.AuthResponse;
import org.junit.BeforeClass;
import org.junit.Test;

public class AuthResponseTest {

	private static AuthResponse authResponse;

	public static final String EXPECTED_ID ="2113171";
	public static final String EXPECTED_USERNAME ="paracetamol";
	public static final boolean EXPECTED_ISVALID = true;

	@BeforeClass
	public static void setUp()
	{
		authResponse = new AuthResponse();
		authResponse.setUid(EXPECTED_ID);
		authResponse.setName(EXPECTED_USERNAME);
		authResponse.setValid(EXPECTED_ISVALID);
	}

	@Test
	public void testDrugDetails() throws Exception {

		assertEquals(EXPECTED_ID, authResponse.getUid());
		assertEquals(EXPECTED_USERNAME, authResponse.getName());
		assertEquals(EXPECTED_ISVALID, authResponse.isValid());
	}
}