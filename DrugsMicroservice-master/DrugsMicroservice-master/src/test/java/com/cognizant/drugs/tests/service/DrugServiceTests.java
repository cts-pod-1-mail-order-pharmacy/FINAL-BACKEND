package com.cognizant.drugs.tests.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.drugs.Model.DrugDetails;
import com.cognizant.drugs.Model.Drugs;
import com.cognizant.drugs.Repository.DrugsRepository;
import com.cognizant.drugs.Service.DrugsService;
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class DrugServiceTests {

	@Autowired
	private DrugsService service;
	
	@MockBean
	private DrugsRepository repo;
	
	@Test
	public void testSearchById() throws ParseException {
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);

		Mockito.when(repo.findById(1)).thenReturn(Optional.of(drugs));
		assertThat(service.searchDrugsByID(1).get()).isEqualTo(drugs);
	}

	@Test
	public void testSearchByName() throws ParseException {
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);
		
		Mockito.when(repo.findByName("Para")).thenReturn(Optional.of(drugs));
		assertThat(service.searchDrugsByName("Para").get()).isEqualTo(drugs);
		
	
	}
	@Test
	public void testSearchByLocation() throws Exception{
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);

		Mockito.when(repo.findById(1)).thenReturn(Optional.of(drugs));
		Mockito.when(service.searchDrugsByID(1)).thenReturn(Optional.of(drugs));
		assertThat(service.findByLocation(1,"Delhi")).isEqualTo(drugs);
	}
	
	@Test
	public void testSave() throws Exception {
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);
		Mockito.when(repo.save(drugs)).thenReturn(drugs);
		assertThat(service.save(drugs)).isEqualTo(drugs);
	}
	@Test
	public void testLocationNotFound() throws ParseException {
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);

		Mockito.when(repo.findById(1)).thenReturn(Optional.of(drugs));
		Mockito.when(service.searchDrugsByID(1)).thenReturn(Optional.of(drugs));
		assertThat(service.findByLocation(1,"Dtyh")).isNull();
		
	}
	@Test
	public void testIsAvailable() throws Exception{
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);

		Mockito.when(repo.findByName("Para")).thenReturn(Optional.of(drugs));
		Mockito.when(service.searchDrugsByName("Para")).thenReturn(Optional.of(drugs));
		assertThat(service.isAvailable("Para","Delhi")).isTrue();
	}
	@Test
	public void testIsNotAvailable() throws Exception{
		List<DrugDetails> drugDetails=new ArrayList<>();
		DrugDetails drugDetails1 = new DrugDetails(1, "Delhi", 34);
		drugDetails.add(drugDetails1);
		Date d1;String Date1 = "2020/08/08";
		d1 = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);
		Drugs drugs = new Drugs();
		drugs.setId(1);
		drugs.setName("Para");
		drugs.setManufacturedDate(d1);
		drugs.setExpiryDate(d1);
		drugs.setCost(2000.00);
		drugs.setUnitsPackage(20);
		drugs.setDrugDetails(drugDetails);

		Mockito.when(repo.findByName("Para")).thenReturn(Optional.of(drugs));
		Mockito.when(service.searchDrugsByName("Para")).thenReturn(Optional.of(drugs));
		assertThat(service.isAvailable("Para","Delh")).isFalse();
	}
	
	
	
}
