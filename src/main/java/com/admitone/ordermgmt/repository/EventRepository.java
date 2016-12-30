package com.admitone.ordermgmt.repository;

import java.sql.Timestamp;

import org.springframework.data.repository.CrudRepository;

import com.admitone.ordermgmt.model.EventEntity;


public interface EventRepository extends CrudRepository<EventEntity, Long> {
	
	Iterable<EventEntity> findByEventDtBetween (Timestamp start, Timestamp end);
      
	EventEntity findByEventId (Integer eventId);
}
