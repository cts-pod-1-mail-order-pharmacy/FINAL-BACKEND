package com.cts.subscriptionservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.cts.subscriptionservice.VO.Drugs;
import com.cts.subscriptionservice.entity.DrugSubscribed;
import com.cts.subscriptionservice.entity.MemberSubscription;
import com.cts.subscriptionservice.entity.Prescription;
import com.cts.subscriptionservice.repository.SubscriptionRepository;
import com.cts.subscriptionservice.service.SubscriptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceTest {


	@Autowired
	private SubscriptionService service;

	@MockBean
	private SubscriptionRepository repo;

	@Test
	public void testSave() throws Exception {
		DrugSubscribed drug= new DrugSubscribed();
		drug.setDrugName("para");
		drug.setQuantity(24);
		List<DrugSubscribed> dlist=new ArrayList<>();
		dlist.add(drug);

		String Date1 = "2020/08/08";
		Date dt = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);

		Prescription pres=new Prescription();
		pres.setDoctorDetails("xyz");
		pres.setDosageDefinitionPerDay(String.valueOf(23));
		pres.setDrugs(dlist);
		pres.setPrescriptionId(1L);
		pres.setPrescriptionDate(dt);
		pres.setInsuranceProvider("xyz");
		pres.setInsurancePolicyNumber("123");
		pres.setPrescriptionCourse("xyz");

		MemberSubscription sub=new MemberSubscription();
		sub.setMemberId(1L);
		sub.setSubscriptionDate(dt);
		sub.setMemberLocation("ddn");
		sub.setPrescriptionId(pres);
		sub.setRefillOccurrence("xyz");
		sub.setSubscriptionStatus(true);
		Mockito.when(repo.save(sub)).thenReturn(sub);
		assertThat(service.save(sub)).isEqualTo(sub);
	}

	@Test
	public void testDelete() throws Exception {
		DrugSubscribed drug= new DrugSubscribed();
		drug.setDrugName("para");
		drug.setQuantity(24);
		List<DrugSubscribed> dlist=new ArrayList<>();
		dlist.add(drug);

		String Date1 = "2020/08/08";
		Date dt = new SimpleDateFormat("dd/mm/yyyy").parse(Date1);

		Prescription pres=new Prescription();
		pres.setDoctorDetails("xyz");
		pres.setDosageDefinitionPerDay(String.valueOf(23));
		pres.setDrugs(dlist);
		pres.setPrescriptionId(1L);
		pres.setPrescriptionDate(dt);
		pres.setInsuranceProvider("xyz");
		pres.setInsurancePolicyNumber("123");
		pres.setPrescriptionCourse("xyz");

		MemberSubscription sub=new MemberSubscription();
		sub.setMemberId(1L);
		sub.setSubscriptionDate(dt);
		sub.setMemberLocation("ddn");
		sub.setPrescriptionId(pres);
		sub.setRefillOccurrence("xyz");
		sub.setSubscriptionStatus(true);
		service.delete(sub.getMemberSubscriptionId());
		verify(repo,times(1)).deleteById(sub.getMemberSubscriptionId());
	}




}
