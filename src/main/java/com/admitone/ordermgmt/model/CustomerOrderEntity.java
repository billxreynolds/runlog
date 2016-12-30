package com.admitone.ordermgmt.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class CustomerOrderEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6205109031246580456L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID")
	private Integer orderId;

	//@Column(name = "USER_ID")
	//private Integer userId;

	@Column(name = "EVENT_ID")
	private Integer eventId;

	@Column(name = "ORDER_DT")
	private Timestamp orderDt;

	@Column(name = "NUM_TICKETS")
	private Integer numTickets;

	@Column(name = "ORDER_STATUS")
	private String orderStatus;

	public CustomerOrderEntity() {

	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getUserId() {
		return user.getUserId();
	}

	public void setUserId(Integer userId) {
		if (user == null) {
			user = new UserEntity();
		}
		user.setUserId(userId);
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Timestamp getOrderDt() {
		return orderDt;
	}

	public void setOrderDt(Timestamp orderDt) {
		this.orderDt = orderDt;
	}

	public Integer getNumTickets() {
		return numTickets;
	}

	public void setNumTickets(Integer numTickets) {
		this.numTickets = numTickets;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserEntity user;
	
	public UserEntity getUser() {
		return this.user;
	}

	public void setUser (UserEntity user) {
		this.user = user;
	}
	
	public String getUsername () {
		return this.user.getUsername();
	}
	
	//@ManyToOne
	//private EventEntity event;

}
