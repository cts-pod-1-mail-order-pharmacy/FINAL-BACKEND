package com.cognizant.refill.Interface;


import java.util.Date;
import java.util.List;
import com.cognizant.refill.Model.RefillOrder;

public interface RefillOrderDAO {
	public List<RefillOrder> viewRefillStatus(Long subscriptionId) ;
	public RefillOrder save(RefillOrder order);
	public List<RefillOrder> getRefillDuesAsOfDate(Date date, Long subscriptionid);
	public List<RefillOrder> viewRefillStatusLatest(Long subscription_id);
	public List<RefillOrder> viewAllData();
    public void checkDaily();
	public boolean anyPendings(Long subscriptionid) ;
}
