package com.cognizant.refill.tests.service;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import com.cognizant.refill.Controller.Controller;
import com.cognizant.refill.FeignClient.AuthClient;
import com.cognizant.refill.FeignClient.FeignClientDrugs;
import com.cognizant.refill.FeignClient.FeignClientSubscribe;
import com.cognizant.refill.Model.RefillOrder;
import com.cognizant.refill.Model.RefillOrderLine;
import com.cognizant.refill.Service.RefillOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = Controller.class)
public class RefillControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	FeignClientDrugs feignClientDrugs;

	@MockBean
	FeignClientSubscribe feignClientSubscribe;
	@MockBean
	AuthClient authClient;
	@MockBean
	private RefillOrderService refillOrderService;

	@Autowired
	private ObjectMapper objectMapper;
	String exampleJson = "{\"refillOrderId\":1,\"subscriptionId\":101,\"refillDate\":\"2020-08-06\",\"status\":false,\"refillOrderLine\":[{\"id\":2,\"drug\":\"crocine\",\"drugQuantity\":1},{\"id\":3,\"drug\":\"pcm\",\"drugQuantity\":1}]}";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	
	
	
	
	

	@Test
	public void testRequestRefill() throws Exception {
		RefillOrderLine rol1 = new RefillOrderLine(1, "crocine", 1);
		RefillOrderLine rol2 = new RefillOrderLine(2, "cough syrup", 5);
		List<RefillOrderLine> morePrescription = new ArrayList<RefillOrderLine>();
		morePrescription.add(rol1);
		morePrescription.add(rol2);

		RefillOrder mockRefillOrder = new RefillOrder(1, 101, formatter.parse("2020-02-01"),
				formatter.parse("2020-02-20"), false, true, Arrays.asList(rol1, rol2));
		
		//RefillOrder refillOrderNew=new RefillOrder(15,101,formatter.parse("2020-08-11"),formatter.parse("2020-08-18"),true,false,Arrays.asList(new RefillOrderLine(1,"paracetamol",10),new RefillOrderLine(1,"syrup",20)));
		RefillOrder refillOrderNew=new RefillOrder(15,101,new Date(),new Date(),true,false,Arrays.asList(new RefillOrderLine(1,"paracetamol",10),new RefillOrderLine(1,"syrup",20)));

		Map<String, Integer> drugsMock = new HashMap<String, Integer>();
		drugsMock.put("paracetamol", 10);
		drugsMock.put("syrup", 20);
		
		
		String token = "hello";
		long memid = 1;
		int subid = 1;
		
		List<Integer> subscriptionIds = new ArrayList<>();
		List<RefillOrder> rfl = new ArrayList<>();
		rfl.add(mockRefillOrder);
		subscriptionIds.add(1);
		Mockito.when(authClient.validate(token)).thenReturn(true);
	//	Mockito.when(feignClientSubscribe.getAllSubIdOfMember(token, memid)).thenReturn(subscriptionIds);
		Mockito.when(feignClientSubscribe.getAllDrugsOfMember(Mockito.anyInt())).thenReturn(drugsMock);
		//Mockito.when(refillOrderService.viewRefillStatusLatest(mockRefillOrder.getSubscriptionId())).thenReturn(rfl);
		Mockito.when(refillOrderService.save(Mockito.any(RefillOrder.class))).thenReturn(refillOrderNew);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/requestRefill").param("Subscription_ID", "101")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		//System.out.println(result.getResponse().getStatus());
		System.out.println(result.getResponse().getContentAsString());
		//String expected = objectMapper.writeValueAsString(mockRefillOrder);
		String actualResult = result.getResponse().getContentAsString();
		RefillOrder resultObject=objectMapper.readValue(actualResult, RefillOrder.class);
		assertEquals(mockRefillOrder.getSubscriptionId(), resultObject.getSubscriptionId());
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

	}
	
	
	
	

	@Test
	public void testFirstRequestRefillCreated() throws Exception { // status - Created 201

		RefillOrderLine rol1 = new RefillOrderLine(1, "crocine", 1);
		RefillOrderLine rol2 = new RefillOrderLine(2, "cough syrup", 5);
		List<RefillOrderLine> morePrescription = new ArrayList<RefillOrderLine>();
		morePrescription.add(rol1);
		morePrescription.add(rol2);
		RefillOrder mockRefillOrder = new RefillOrder(1, 101, formatter.parse("2020-02-01"),
				formatter.parse("2020-02-20"), false, true, Arrays.asList(rol1, rol2));

		String token = "hello";
		Mockito.when(authClient.validate(token)).thenReturn(true);

		Map<String, Integer> drugsMock = new HashMap<String, Integer>();
		drugsMock.put("paracetamol", 10);
		drugsMock.put("syrup", 20);

		Mockito.when(feignClientSubscribe.getAllDrugsOfMember(Mockito.anyInt())).thenReturn(drugsMock);
		Mockito.when(feignClientDrugs.isAvailable(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(refillOrderService.save(Mockito.any(RefillOrder.class))).thenReturn(mockRefillOrder);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/firstRequestRefill/1234/ddun/weekly")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token);
		;
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// System.out.println(result.getResponse().getStatus());
		String expected = objectMapper.writeValueAsString(mockRefillOrder);
		String actualResult = result.getResponse().getContentAsString();
		// System.out.println(result.getResponse().getContentAsString());
		assertEquals(expected, actualResult);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

	}

	@Test
	public void testFirstRequestRefillNotFound() throws Exception { // status - NotFound 404

		RefillOrderLine rol1 = new RefillOrderLine(1, "crocine", 1);
		RefillOrderLine rol2 = new RefillOrderLine(2, "cough syrup", 5);
		List<RefillOrderLine> morePrescription = new ArrayList<RefillOrderLine>();
		morePrescription.add(rol1);
		morePrescription.add(rol2);
		RefillOrder mockRefillOrder = new RefillOrder(1, 101, formatter.parse("2020-02-01"),
				formatter.parse("2020-02-20"), false, true, Arrays.asList(rol1, rol2));

		Map<String, Integer> drugsMock = new HashMap<String, Integer>();
		drugsMock.put("paracetamol", 10);
		drugsMock.put("syrup", 20);
		String token = "hello";
		Mockito.when(authClient.validate(token)).thenReturn(true);

		Mockito.when(feignClientSubscribe.getAllDrugsOfMember(Mockito.anyInt())).thenReturn(drugsMock);
		Mockito.when(feignClientDrugs.isAvailable(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(false);
		Mockito.when(refillOrderService.save(Mockito.any(RefillOrder.class))).thenReturn(mockRefillOrder);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/firstRequestRefill/1234/ddun/weekly")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		// System.out.println(result.getResponse().getStatus());
		String expected = objectMapper.writeValueAsString(mockRefillOrder);
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

	}
	
	
	
	
	

	
	  @Test public void testViewRefillStatus() throws Exception { 
		  
		  RefillOrderLine rol = new	  RefillOrderLine( 2,"crocine", 1);
		  RefillOrder mockRefill = new RefillOrder(1,1,formatter.parse("2020-02-01"),formatter.parse("2020-02-20"),false,true,
	  Arrays.asList(rol));
	  
	  List<RefillOrder> list=new ArrayList<RefillOrder>(); 
	  list.add(mockRefill);
	  

	  String token = "hello";
	  long memid = 1;
	  int subid = 1;
		
	  
	  		Mockito.when(authClient.validate(token)).thenReturn(true);
	  		Mockito.when(authClient.memberId(token)).thenReturn(memid);  		
	
			Mockito.when(feignClientSubscribe.getAllSubIdOfMember(Mockito.anyString(),Mockito.anyLong())).thenReturn(Arrays.asList(subid));
			
			Mockito.when(refillOrderService.viewRefillStatusLatest(Mockito.anyInt())).thenReturn(list);

	  
	  RequestBuilder requestBuilder =	  MockMvcRequestBuilders.get("/viewRefillStatus/1")
			  .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
	  
	  MvcResult result =	  mockMvc.perform(requestBuilder).andReturn();
	  
	  System.out.println(result.getResponse().getContentAsString());
	  System.out.println(result.getResponse().getStatus());
		
	  String expected = objectMapper.writeValueAsString(list);
	  String actualResult = result.getResponse().getContentAsString();
	  
	  assertEquals(expected,actualResult);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	  
	  }
	 

	@Test
		public void testViewRefillStatusAll()  throws Exception {
		  RefillOrderLine rol = new	  RefillOrderLine( 2,"crocine", 1);
		  RefillOrder mockRefill = new RefillOrder(1,1,formatter.parse("2020-02-01"),formatter.parse("2020-02-20"),false,true,
	  Arrays.asList(rol));
	  
	  List<RefillOrder> list=new ArrayList<RefillOrder>(); 
	  list.add(mockRefill);
	  

	  String token = "hello";
	  long memid = 1;
	  int subid = 1;
		
	  
	  		Mockito.when(authClient.validate(token)).thenReturn(true);
	  		Mockito.when(authClient.memberId(token)).thenReturn(memid);  		
	
			Mockito.when(feignClientSubscribe.getAllSubIdOfMember(Mockito.anyString(),Mockito.anyLong())).thenReturn(Arrays.asList(subid));
			
			Mockito.when(refillOrderService.viewRefillStatus(Mockito.anyInt())).thenReturn(list);

	  
	  RequestBuilder requestBuilder =	  MockMvcRequestBuilders.get("/viewRefillStatusAll/1")
			  .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
	  
	  MvcResult result =	  mockMvc.perform(requestBuilder).andReturn();
	  
	  System.out.println(result.getResponse().getContentAsString());
	  System.out.println(result.getResponse().getStatus());
		
	  String expected = objectMapper.writeValueAsString(list);
	  String actualResult = result.getResponse().getContentAsString();
	  
	  assertEquals(expected,actualResult);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	
		}
	
	
	

	@Test	
	public void testRequestAdhocRefill()  throws Exception {
		
			RefillOrderLine rol1 = new RefillOrderLine(1,"crocine", 1);
			RefillOrderLine rol2 = new RefillOrderLine(2,"cough syrup", 5);
			List<RefillOrderLine> morePrescription=new ArrayList<RefillOrderLine>();
			morePrescription.add(rol1);
			morePrescription.add(rol2);
			RefillOrder mockRefillOrder = new RefillOrder(1, 101,formatter.parse("2020-02-01"),formatter.parse("2020-02-20"),false,true,Arrays.asList(rol1,rol2));		
		
			
			Map<String, Integer> drugsMock=new HashMap<String, Integer>();
			drugsMock.put("paracetamol", 10);
			drugsMock.put("syrup", 20);
			
			//System.out.println((drugsMock.get("syrup")) instanceof Integer);
			
			String token="hello";
			
			Mockito.when(authClient.validate(token)).thenReturn(true);
			Mockito.when(feignClientSubscribe.getAllDrugsOfMember(Mockito.anyInt())).thenReturn(drugsMock);
			
//			Map<String,Integer> map=mock(HashMap.class);
//			RefillOrderLine rolMock=mock(RefillOrderLine.class);			
			
//			Map<String, Integer> drugsMockSpy=Mockito.spy(drugsMock);
//			Mockito.doReturn(new Integer(1)).when(drugsMockSpy).get(Mockito.anyString());
			
			//Mockito.doCallRealMethod().when(drugsMock.get(Mockito.anyString())).thenReturn(new Integer(1));
			
			//Mockito.when(map.get(Mockito.anyString())).thenReturn(new Integer(1));
			Mockito.when(feignClientDrugs.isAvailable(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(true);
			
//
			//
//			Mockito.when(rolMock.getDrugQuantity()).thenReturn(Mockito.anyInt());
			
			Mockito.when(refillOrderService.save(Mockito.any(RefillOrder.class))).thenReturn(mockRefillOrder);
			
			
			//Mockito.when(map.get(Mockito.anyString())).thenReturn(new Integer(1));
			
			
			String morePrescriptionJson=objectMapper.writeValueAsString(morePrescription);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post("/requestAdhocRefill").param("Subscription_ID", "101").param("Policy_ID", "9876")
					.param("Location", "ddun").header("Authorization", token)
					.accept(MediaType.APPLICATION_JSON).content(morePrescriptionJson)
					.contentType(MediaType.APPLICATION_JSON);
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
			System.out.println(result.getResponse().getStatus());
			String actualResult=result.getResponse().getContentAsString();
			System.out.println(actualResult);	
			assertEquals(HttpStatus.NOT_FOUND.value(),result.getResponse().getStatus());
			//get(refillOrderLineNew.getDrug())
			//verify(mock(HashMap.class),times(1)).get(Mockito.anyString());
			//verify(mock(HashMap.class),times(1)).isAvailable(Mockito.anyString(),Mockito.anyString(),Mockito.anyString());
	}
	
	
	
	@Test
	public void testGetRefillDuesAsOfDate()  throws Exception {
				  RefillOrderLine rol = new	  RefillOrderLine( 2,"crocine", 1);
				  RefillOrder mockRefill = new RefillOrder(1,1,formatter.parse("2020-02-01"),formatter.parse("2020-02-20"),false,true,
			  Arrays.asList(rol));
			  
			  List<RefillOrder> list=new ArrayList<RefillOrder>(); 
			  list.add(mockRefill);
			  
			
			  String token = "hello";
//			  long memid = 1;
//			  int subid = 1;
				
			  
			  		Mockito.when(authClient.validate(token)).thenReturn(true);		
					
					
					Mockito.when(refillOrderService.getRefillDuesAsOfDate(Mockito.any(Date.class),Mockito.anyInt())).thenReturn(list);
			
			  
			  RequestBuilder requestBuilder =	  MockMvcRequestBuilders.get("/getRefillDuesAsOfDate")
					  .accept(MediaType.APPLICATION_JSON).param("date","2020/02/01").param("Subscription_ID","1").header("Authorization", token);
			  
			  MvcResult result =	  mockMvc.perform(requestBuilder).andReturn();
			  
			  System.out.println(result.getResponse().getContentAsString());
			  System.out.println(result.getResponse().getStatus());
				
			  String expected = objectMapper.writeValueAsString(list);
			  String actualResult = result.getResponse().getContentAsString();
			  
			  assertEquals(expected,actualResult);
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}
	
	
	
	@Test
	public void testAnyPendings1()  throws Exception {		// pending
			
			  String token = "hello";
//			  long memid = 1;
//			  int subid = 1;
				
			  
			  		Mockito.when(authClient.validate(token)).thenReturn(true);	
					
					
					Mockito.when(refillOrderService.anyPendings(Mockito.anyInt())).thenReturn(true);
			
			  
			  RequestBuilder requestBuilder =	  MockMvcRequestBuilders.get("/anyPendings/1")
					  .accept(MediaType.APPLICATION_JSON).param("subscription_id","1").header("Authorization", token);
			  
			  MvcResult result =	  mockMvc.perform(requestBuilder).andReturn();	  
			
				
			  String expected = "true";
			  String actualResult = result.getResponse().getContentAsString();
			  
			  assertEquals(expected,actualResult);
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}
	
	
	@Test
	public void testAnyPendings2()  throws Exception {		// pending
			
			  String token = "hello";
//			  long memid = 1;
//			  int subid = 1;
				
			  
			  		Mockito.when(authClient.validate(token)).thenReturn(true);	
					
					
					Mockito.when(refillOrderService.anyPendings(Mockito.anyInt())).thenReturn(false);
			
			  
			  RequestBuilder requestBuilder =	  MockMvcRequestBuilders.get("/anyPendings/101")
					  .accept(MediaType.APPLICATION_JSON).param("subscription_id","101").header("Authorization", token);
			  
			  MvcResult result =	  mockMvc.perform(requestBuilder).andReturn();	  
			
				
			  String expected = "false";
			  String actualResult = result.getResponse().getContentAsString();
			  
			  assertEquals(expected,actualResult);
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}
	
	
	
	
}
