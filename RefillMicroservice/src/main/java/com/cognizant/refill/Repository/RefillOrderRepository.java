package com.cognizant.refill.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.refill.Model.RefillOrder;
@Repository
public interface RefillOrderRepository extends CrudRepository<RefillOrder, Long>{

	List<RefillOrder> findBySubscriptionId(Long subscriptionId);
	@Query(value = "select * from REFILL_ORDER where subscription_id= :subscriptionid and status=false and refill_date>= :date",nativeQuery = true)
	List<RefillOrder> getRefillDuesAsOfDate(Date date, Long subscriptionid);
	@Query(value = "select * from refill_order where refill_date in (select max(refill_date)  from refill_order where subscription_id= :subscriptionid) and subscription_id= :subscriptionid",nativeQuery = true)
	List<RefillOrder> findBySubscriptionIdLatest(Long subscriptionid);
	@Query(value = "select * from REFILL_ORDER where subscription_id= :subscriptionid and status=false",nativeQuery = true)
	List<RefillOrder> getPendings(Long subscriptionid);
	

}
