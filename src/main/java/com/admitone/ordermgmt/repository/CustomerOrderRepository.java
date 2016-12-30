package com.admitone.ordermgmt.repository;

import org.springframework.data.repository.CrudRepository;

import com.admitone.ordermgmt.model.CustomerOrderEntity;
import com.admitone.ordermgmt.model.UserEntity;


/**
 * 
 * @author bill
 */
public interface CustomerOrderRepository extends CrudRepository<CustomerOrderEntity, Long> {

	// TODO -  add QueryDSL to support more complex lookups
	// fine for now that we just have 2 columns, will
	// be tougher to maintain as more columns are added...
	public CustomerOrderEntity findByUserAndEventId (UserEntity user, Integer eventId);
	
	public Iterable<CustomerOrderEntity> findByEventIdBetween (Integer startEventId, Integer endEventId);
          
}
