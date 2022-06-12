package com.cognizant.refill.tests.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.refill.Model.RefillOrder;
import com.cognizant.refill.Model.RefillOrderLine;
import com.cognizant.refill.Repository.RefillOrderRepository;
import com.cognizant.refill.Service.RefillOrderService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RefillServiceTest {

	@MockBean
	private RefillOrderRepository repo;
	
	@Autowired
	private RefillOrderService service;
	
	@Test
	public void testViewRefillStatus() throws Exception {
		String Date="2020/08/08";
		Date date=new SimpleDateFormat("dd/mm/yyyy").parse(Date);
		RefillOrderLine refillLine=new RefillOrderLine(1,"xyz",45);
		List<RefillOrderLine> x=new ArrayList<>();
		x.add(refillLine);
		RefillOrder refill=new RefillOrder(1,1,date,date,true,true,x);
		List<RefillOrder> r=new ArrayList<>();
		r.add(refill);
		
		Mockito.when(repo.findBySubscriptionId(1)).thenReturn(r);
		assertThat(service.viewRefillStatus(1)).isEqualTo(r);
		
	}
	
	@Test
	public void testSaveStatus() throws Exception {
		String Date="2020/08/08";
		Date date=new SimpleDateFormat("dd/mm/yyyy").parse(Date);
		RefillOrderLine refillLine=new RefillOrderLine(1,"xyz",45);
		List<RefillOrderLine> x=new ArrayList<>();
		x.add(refillLine);
		RefillOrder refill=new RefillOrder(1,1,date,date,true,true,x);
		List<RefillOrder> r=new ArrayList<>();
		r.add(refill);
		
		Mockito.when(repo.save(refill)).thenReturn(refill);
		assertThat(service.save(refill)).isEqualTo(refill);
		
	}
	@Test
	public void testRefillDuesAsOfStatus() throws Exception {
		String Date="2020/08/08";
		Date date=new SimpleDateFormat("dd/mm/yyyy").parse(Date);
		RefillOrderLine refillLine=new RefillOrderLine(1,"xyz",45);
		List<RefillOrderLine> x=new ArrayList<>();
		x.add(refillLine);
		RefillOrder refill=new RefillOrder(1,1,date,date,true,true,x);
		List<RefillOrder> r=new ArrayList<>();
		r.add(refill);
		
		Mockito.when(repo.getRefillDuesAsOfDate(date,1)).thenReturn(r);
		assertThat(service.getRefillDuesAsOfDate(date,1)).isEqualTo(r);
		
	}
	@Test
	public void testViewRefillStatusLatest() throws Exception {
		String Date="2020/08/08";
		Date date=new SimpleDateFormat("dd/mm/yyyy").parse(Date);
		RefillOrderLine refillLine=new RefillOrderLine(1,"xyz",45);
		List<RefillOrderLine> x=new ArrayList<>();
		x.add(refillLine);
		RefillOrder refill=new RefillOrder(1,1,date,date,true,true,x);
		List<RefillOrder> r=new ArrayList<>();
		r.add(refill);
		
		Mockito.when(repo.findBySubscriptionIdLatest(1)).thenReturn(r);
		assertThat(service.viewRefillStatusLatest(1)).isEqualTo(r);
		
	}
	@Test
	public void testViewAll() throws Exception {
		String Date="2020/08/08";
		Date date=new SimpleDateFormat("dd/mm/yyyy").parse(Date);
		RefillOrderLine refillLine=new RefillOrderLine(1,"xyz",45);
		List<RefillOrderLine> x=new ArrayList<>();
		x.add(refillLine);
		RefillOrder refill=new RefillOrder(1,1,date,date,true,true,x);
		List<RefillOrder> r=new ArrayList<>();
		r.add(refill);
		
		Mockito.when(repo.findAll()).thenReturn(r);
		assertThat(service.viewAllData()).isEqualTo(r);
		
	}
	@Test
	public void testAnyPendings() throws Exception {
		String Date="2020/08/08";
		Date date=new SimpleDateFormat("dd/mm/yyyy").parse(Date);
		RefillOrderLine refillLine=new RefillOrderLine(1,"xyz",45);
		List<RefillOrderLine> x=new ArrayList<>();
		x.add(refillLine);
		RefillOrder refill=new RefillOrder(1,1,date,date,true,true,x);
		List<RefillOrder> r=new ArrayList<>();
		r.add(refill);
		
		Mockito.when(repo.getPendings(1)).thenReturn(r);
		assertThat(service.anyPendings(1)).isTrue();
		
	}
	@Test
	public void testAnyPendingsFalse() throws Exception {
		
		List<RefillOrder> r=new ArrayList<>();
		Mockito.when(repo.getPendings(1)).thenReturn(r);
		assertThat(service.anyPendings(1)).isFalse();
		
	}
	
	
}
