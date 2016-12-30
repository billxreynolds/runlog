package com.admitone.ordermgmt.service;

import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admitone.ordermgmt.model.CustomerOrderEntity;
import com.admitone.ordermgmt.model.EventEntity;
import com.admitone.ordermgmt.model.UserEntity;
import com.admitone.ordermgmt.repository.CustomerOrderRepository;
import com.admitone.ordermgmt.repository.EventRepository;
import com.admitone.ordermgmt.repository.UserRepository;

@Service
public class OrderMgmtService {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	CustomerOrderRepository custOrderRepo;

	@Autowired
	EventRepository eventRepo;

	@Autowired
	UserRepository userRepo;

	public CustomerOrderEntity saveCustOrder(CustomerOrderEntity custOrder) {
		CustomerOrderEntity saved = custOrderRepo.save(custOrder);
		if (log.isDebugEnabled()) {
			log.debug("saved: " + saved);
		}
		return saved;
	}

	public EventEntity saveEvent(EventEntity event) {
		EventEntity saved = eventRepo.save(event);
		if (log.isDebugEnabled()) {
			log.debug("saved: " + saved);
		}
		return saved;
	}

	public UserEntity saveUser(UserEntity user) {
		UserEntity saved = userRepo.save(user);
		if (log.isDebugEnabled()) {
			log.debug("saved: " + saved);
		}
		return saved;
	}

	public UserEntity findByUserId(Integer userId) {
		UserEntity user = userRepo.findByUserId(userId);
		return user;
	}
	
	public UserEntity findByUserName(String username) {
		UserEntity user = userRepo.findByUsername(username);
		return user;
	}

	public CustomerOrderEntity findCustomerOrder(Long orderId) {
		CustomerOrderEntity order = custOrderRepo.findOne(orderId);
		return order;
	}

	public CustomerOrderEntity findCustomerOrderByUserAndEventId(UserEntity user, Integer eventId) {
		CustomerOrderEntity order = custOrderRepo.findByUserAndEventId(user, eventId);
		return order;
	}
	
	public Iterable<CustomerOrderEntity> findByEventIdBetween(Integer startEventID, Integer endEventID) {
		Iterable<CustomerOrderEntity> orders = custOrderRepo.findByEventIdBetween(startEventID, endEventID);
		return orders;
	}

	public Iterable<EventEntity> findEventsForDateRange(Timestamp start, Timestamp end) {
		Iterable<EventEntity> events = eventRepo.findByEventDtBetween(start, end);
		return events;
	}
	
	public EventEntity findByEventId(Integer eventId) {
		EventEntity event = eventRepo.findByEventId(eventId);
		return event;
	}

}
