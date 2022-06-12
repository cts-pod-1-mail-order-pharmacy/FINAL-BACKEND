package com.cognizant.drugs.tests.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cognizant.drugs.Controller.DrugsController;
import com.cognizant.drugs.Feign.AuthClient;
import com.cognizant.drugs.Model.DrugDetails;
import com.cognizant.drugs.Model.Drugs;
import com.cognizant.drugs.Service.DrugsService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(value=DrugsController.class)
public class AppTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DrugsService drugsService;
    @MockBean
    private AuthClient authClient;
    @Autowired
    private ObjectMapper objectMapper;

    private SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
    private DrugDetails dt1;
    private DrugDetails dt2;
    private Drugs drugs;



    @Test
    public void testSearchDrugsByIdFound() throws Exception {	// Status - OK(200)

        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));

        Mockito.when(drugsService.searchDrugsByID(Mockito.anyInt())).thenReturn(Optional.of(drugs));
        Mockito.when(authClient.validate(token)).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchDrugsByID/1")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        //MvcResult result = mockMvc.perform(requestBuilder).andReturn().andExpect(status().isOk());
        //System.out.println("MockDrug as sting--> "+mockDrug.toString());
        String jsonMockDrug = objectMapper.writeValueAsString(drugs);
        //System.out.println("MockDrug as json--> "+jsonMockDrug);
        //System.out.println("Response as sting--> "+result.getResponse().getContentAsString());
        //Drugs actual=objectMapper.readValue(result.getResponse().getContentAsString(), Drugs.class);
        //System.out.println(actual);
        String actualResult=result.getResponse().getContentAsString();

        //assertEquals(mockDrug.toString(),result.getResponse().getContentAsString());
        assertEquals(jsonMockDrug,actualResult);
    }
//@Test
//public void testValidate() throws Exception{
//	String token="hello";
//	String URI="/api/auth/validate";
//	Mockito.when(jwtUtils.validateJwtToken(token)).thenReturn(true);
//	RequestBuilder requestBuilder=MockMvcRequestBuilders.post(URI).header("Authorization", token);
//	MvcResult result=mockMvc.perform(requestBuilder).andReturn();
//	MockHttpServletResponse response=result.getResponse();
//	String output=response.getContentAsString();
//	assertThat(output).isEqualTo("true");
//	assertEquals(HttpStatus.OK.value(),response.getStatus());
//
//}

    @Test
    public void testSearchDrugsByIdNotFound() throws Exception { // Status - NotFound(404)
        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(drugsService.searchDrugsByID(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(authClient.validate(token)).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchDrugsByID/1")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    @Test
    public void testSearchDrugsByNameFound() throws Exception {	// Status - OK(200)

        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(true);
        Mockito.when(drugsService.searchDrugsByName(Mockito.anyString())).thenReturn(Optional.of(drugs));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchDrugsByName/paracetamol")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String jsonMockDrug = objectMapper.writeValueAsString(drugs);
        String actualResult=result.getResponse().getContentAsString();
        assertEquals(jsonMockDrug,actualResult);
    }

    @Test
    public void testSearchDrugsByNameNotFound() throws Exception {	// Status - NotFound(404)
        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(true);
        Mockito.when(drugsService.searchDrugsByName(Mockito.anyString())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchDrugsByName/paracetamol")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
    }
    @Test
    public void testnotAuthorisedName() throws Exception {	// Status - NotFound(404)
        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(false);
        Mockito.when(drugsService.searchDrugsByName(Mockito.anyString())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchDrugsByName/para")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());
    }
    @Test
    public void testnotAuthorisedId() throws Exception {	// Status - NotFound(404)
        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(false);
        Mockito.when(drugsService.searchDrugsByID(Mockito.anyInt())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/searchDrugsByID/1")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());
    }

    @Test
    public void testnotAuthorisedDispachable() throws Exception {	// Status - NotFound(404)
        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(false);


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getDispatchableDrugStock/1/delhi")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());
    }




    @Test
    public void testGetDispatchableDrugStockFound() throws Exception {	// Status - OK(200)

        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(true);
        Mockito.when(drugsService.findByLocation(Mockito.anyInt(),Mockito.anyString())).thenReturn(drugs);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getDispatchableDrugStock/1/delhi")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        String jsonMockDrug = objectMapper.writeValueAsString(drugs);
        String actualResult=result.getResponse().getContentAsString();
        assertEquals(jsonMockDrug,actualResult);
    }

    @Test(expected = Exception.class)
    public void testGetDispatchableDrugStockNotFound() throws Exception {	// Status - NotFound(404)
        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(true);
        Mockito.when(drugsService.searchDrugsByName(Mockito.anyString())).thenReturn(Optional.of(drugs));
        Mockito.when(drugsService.findByLocation(Mockito.anyInt(),Mockito.anyString())).thenReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getDispatchableDrugStock/1/delhi")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(null);

    }


    @Test
    public void testIsAvailableFound() throws Exception {	// Status - OK(200)

        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(true);
        Mockito.when(drugsService.searchDrugsByName("paracetamol")).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/isAvailable/paracetamol/delhi")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        //MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String jsonMockDrug = objectMapper.writeValueAsString(drugs);
        String actualResult=result.getResponse().getContentAsString();
        System.out.println(result.getResponse().getStatus());
        System.out.println(actualResult);
        //assertEquals(jsonMockDrug,actualResult);
    }


    @Test
    public void testIsAvailableNotFound() throws Exception {	// Status - OK(200)

        dt1=new DrugDetails(1,"ddun",10);
        dt2=new DrugDetails(2,"delhi",20);

        drugs = new Drugs();
        drugs.setId(1);
        drugs.setName("para");
        String token="hello";
        drugs.setManufacturedDate(formatter.parse("2020-02-01"));
        drugs.setExpiryDate(formatter.parse("2019-02-01"));
        drugs.setCost(200.00);
        drugs.setUnitsPackage(5);
        drugs.setDrugDetails(Arrays.asList(dt1,dt2));
        Mockito.when(authClient.validate(token)).thenReturn(false);
        Mockito.when(drugsService.searchDrugsByName("paracetamol")).thenReturn(Optional.of(drugs));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/isAvailable/paracetamol/delhi")
                .accept(MediaType.APPLICATION_JSON).header("Authorization", token);
        mockMvc.perform(requestBuilder).andExpect(status().isUnauthorized());

    }

}
