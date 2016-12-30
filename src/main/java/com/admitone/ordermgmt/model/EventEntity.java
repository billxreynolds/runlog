package com.admitone.ordermgmt.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
public class EventEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7090402912722873335L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID")
	private Integer eventId;

	@Column(name = "EVENT_TYPE")
	private String eventType;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EVENT_DT")
	private Timestamp eventDt;

	public EventEntity() {

	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getEventDt() {
		return eventDt;
	}

	public void setEventDt(Timestamp eventDt) {
		this.eventDt = eventDt;
	}

	//@OneToMany(mappedBy = "event")
	//private Collection<CustomerOrderEntity> customerOrders;
	
}
