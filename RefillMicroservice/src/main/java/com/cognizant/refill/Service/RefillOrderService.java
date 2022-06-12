package com.cognizant.refill.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Map;



import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cognizant.refill.FeignClient.FeignClientSubscribe;
import com.cognizant.refill.Interface.RefillOrderDAO;
import com.cognizant.refill.Model.RefillOrder;
import com.cognizant.refill.Model.RefillOrderLine;
import com.cognizant.refill.Repository.RefillOrderRepository;

import lombok.extern.log4j.Log4j;
@Slf4j
@Service
public class RefillOrderService implements RefillOrderDAO{
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	@Autowired RefillOrderRepository refillOrderRepository;
	@Autowired FeignClientSubscribe feignClientSubscribe;
	public List<RefillOrder> viewRefillStatus(Long subscriptionId) {
		return refillOrderRepository.findBySubscriptionId(subscriptionId);
	}
	@Transactional
	public RefillOrder save(RefillOrder order) {
		return refillOrderRepository.save(order);
	}
	public List<RefillOrder> getRefillDuesAsOfDate(Date date, Long subscriptionid) {
		return refillOrderRepository.getRefillDuesAsOfDate(date,subscriptionid);
	}
	public List<RefillOrder> viewRefillStatusLatest(Long subscriptionId) {
		return refillOrderRepository.findBySubscriptionIdLatest(subscriptionId);
	}
	public List<RefillOrder> viewAllData(){
		return (List<RefillOrder>) refillOrderRepository.findAll();
	}
	@Transactional 
	@Scheduled(cron = "0 18 2 * * ?")
    public void checkDaily() {
		List<RefillOrder> refillOrders=viewAllData();
       for(RefillOrder refillOrder:refillOrders) {
    	   log.info(refillOrder.getRefillOrderId()+"  "+refillOrder.getSubscriptionId()+" "+refillOrder.getRefillOrderId());
    	   if((!refillOrder.isVisited())&&refillOrder.getRefillDateNext()!=null&&dateFormat.format(new Date()).equals(dateFormat.format(refillOrder.getRefillDateNext()))) {
	    	   Long subId=refillOrder.getSubscriptionId();
	    	   String frequency=feignClientSubscribe.findRefillOccurrenceBySubscriptionId(subId);
	    	   Date prevDate=refillOrder.getRefillDate();
	    	   Calendar c = Calendar.getInstance();
	    	   c.setTime(prevDate);
	    	   if(frequency.equals("weekly")) {
	    		   log.info("weekly chosen");
	    		  c.add(Calendar.DAY_OF_MONTH, 7);    
	    	   }
	    	   else {
	    		   log.info("monthly chosen");
	    		   c.add(Calendar.DAY_OF_MONTH, 30);    
	    	   }
	    	   Date newest=c.getTime();
	    	   
	    	   Map<String,Integer> drugs=feignClientSubscribe.getAllDrugsOfMember(refillOrder.getSubscriptionId());
	    		

			List<RefillOrderLine> refilOrderLine=new ArrayList<>();
			for (Map.Entry<String,Integer> map : drugs.entrySet()) 
			{  
				String drug=map.getKey();
				int quantity=map.getValue();
				refilOrderLine.add(new RefillOrderLine(drug,quantity));
			}
			refillOrder.setVisited(true);
			save(refillOrder);
			RefillOrder refillOrderNew=new RefillOrder();
			refillOrderNew.setRefillDate(new Date());
			refillOrderNew.setRefillDateNext(newest);
			refillOrderNew.setSubscriptionId(refillOrder.getSubscriptionId());
			refillOrderNew.setRefillOrderLine(refilOrderLine);
			save(refillOrderNew);
			
    	   }
       }
    }
	public boolean anyPendings(Long subscriptionid) {
		List<RefillOrder> refillOrder=refillOrderRepository.getPendings(subscriptionid);
		boolean returnValue=false;
		if(refillOrder.isEmpty()) {
			returnValue=false;
		}
		else {
			returnValue=true;
		}
			return returnValue;
	}
}
