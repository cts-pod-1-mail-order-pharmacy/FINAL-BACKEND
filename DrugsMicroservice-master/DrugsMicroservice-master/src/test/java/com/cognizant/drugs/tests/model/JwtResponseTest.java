package com.cognizant.drugs.tests.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cognizant.drugs.Model.JwtResponse;


public class JwtResponseTest {
	
private static JwtResponse jwtResponse;
	
	public static final int EXPECTED_ID =1;
	public static final String EXPECTED_TOKEN ="paracetamol";
	public static final String EXPECTED_USERNAME ="paracetamol";
	public static List<String> EXPECTED_ROLES;
	
	@BeforeClass
	public static void setUp()
	{
		EXPECTED_ROLES=new ArrayList<String> ();
		EXPECTED_ROLES.add("Admin");
		EXPECTED_ROLES.add("User");
				
		jwtResponse=new JwtResponse();		
		jwtResponse.setId(EXPECTED_ID);
		jwtResponse.setToken(EXPECTED_TOKEN);
		jwtResponse.setUsername(EXPECTED_USERNAME);
		jwtResponse.setRoles(EXPECTED_ROLES);
		
	}
	
	@Test
    public void testDrugDetails() throws Exception {
	 
	 assertEquals(EXPECTED_ID, jwtResponse.getId());
	 assertEquals(EXPECTED_TOKEN, jwtResponse.getToken());
	 assertEquals(EXPECTED_USERNAME, jwtResponse.getUsername());	 
	 assertEquals(EXPECTED_ROLES, jwtResponse.getRoles());
    }

}


