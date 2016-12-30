package com.admitone.ordermgmt.web.api;

import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.admitone.ordermgmt.model.CustomerOrderEntity;
import com.admitone.ordermgmt.model.EventEntity;
import com.admitone.ordermgmt.model.UserEntity;
import com.admitone.ordermgmt.service.OrderMgmtService;

@Controller
public class OrderMgmtController implements OrderMgmtConstants {

	@Autowired
	OrderMgmtService orderMgmtService;

	@GetMapping(value = { "/api/purchase" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public OrderMgmtResponse purchase(HttpServletRequest request) {				
		String username = request.getParameter("username");
		String numTickets = request.getParameter("numTickets");
		String eventId = request.getParameter("eventId");
				
		OrderMgmtResponse response = processInputs (username, numTickets, eventId);
		
		// hit an error, return immediately
		if (response.getCode().equals(ERROR_CODE)) {
			return response;
		}
		
		// locate the given user
		UserEntity user = orderMgmtService.findByUserName(username);		
		if (user == null) {
			return noUserFound(username);
		}			
		
		// locate the event
		EventEntity event = orderMgmtService.findByEventId(Integer.parseInt(eventId));
		if (event == null) {
			return noEventFound (eventId);
		}
		
		CustomerOrderEntity custOrder = 
				orderMgmtService.findCustomerOrderByUserAndEventId(user, Integer.parseInt(eventId));
		
		// no existing orders for this customer and event, create one 
		Timestamp orderDt = Timestamp.from(Instant.now());
		boolean isNewOrder = false;
		if (custOrder == null) {
			isNewOrder = true;
			custOrder = new CustomerOrderEntity();
			custOrder.setUserId(user.getUserId());
			custOrder.setEventId(Integer.parseInt(eventId));
			custOrder.setNumTickets(Integer.parseInt(numTickets));
			custOrder.setOrderStatus(ACTIVE_ORDER);
			custOrder.setOrderDt(orderDt);
			custOrder.setCreatedDt(orderDt);
			custOrder.setUpdatedDt(orderDt);
		}
		else {
			// update existing order
			custOrder.setNumTickets(custOrder.getNumTickets() + Integer.parseInt(numTickets));
			custOrder.setOrderDt(orderDt);
			custOrder.setUpdatedDt(orderDt);	
		}
		
		CustomerOrderEntity savedOrder = orderMgmtService.saveCustOrder(custOrder);
				
		if (isNewOrder && savedOrder.getOrderId() != null) {
			response.setDetails("Created order: " + savedOrder.getOrderId());
		}
		else if (!isNewOrder && savedOrder.getOrderId() != null) {
			response.setDetails("Updated order: " + savedOrder.getOrderId());
		}
		else {
			response.setCode(ERROR_CODE);
			response.setMessage(ERR_ORDER_NOT_CREATED);			
		}
				
		return response;
	}
	
	private OrderMgmtResponse processInputs (String username, String numTickets, String eventId) {
		OrderMgmtResponse response = new OrderMgmtResponse ();
		response.setCode(SUCCESS_CODE);
		response.setMessage(SUCCESS_MESSAGE);
		
		// process inputs
		if (StringUtils.isBlank(username)) {
			return missingParam ("username");
		}		
		if (StringUtils.isBlank(eventId)) {			
			return missingParam ("eventId");
		}
		if (StringUtils.isBlank(numTickets)) {
			return missingParam ("numTickets");
		}
		if (!StringUtils.isNumeric(eventId)) {
			return invalidData ("eventId", eventId);
		}
		if (!StringUtils.isNumeric(numTickets)) {
			return invalidData ("numTickets", numTickets);
		}
		return response;
	}
		
	private OrderMgmtResponse missingParam(String paramName) {
		OrderMgmtResponse response = new OrderMgmtResponse();
		response.setCode(ERROR_CODE);
		response.setMessage(ERR_MISSING_PARAM);
		response.setDetails("missing: " + paramName);
		return response;
	}
	
	private OrderMgmtResponse noUserFound(String userName) {
		OrderMgmtResponse response = new OrderMgmtResponse();
		response.setCode(ERROR_CODE);
		response.setMessage(ERR_NO_USER_FOUND);
		response.setDetails("unable to locate user: " + userName);
		return response;
	}
	
	private OrderMgmtResponse noEventFound(String eventId) {
		OrderMgmtResponse response = new OrderMgmtResponse();
		response.setCode(ERROR_CODE);
		response.setMessage(ERR_NO_EVENT_FOUND);
		response.setDetails("unable to locate event: " + eventId);
		return response;
	}
	
	private OrderMgmtResponse invalidData (String paramName, String data) {
		OrderMgmtResponse response = new OrderMgmtResponse();
		response.setCode(ERROR_CODE);
		response.setMessage(ERR_BAD_DATA);
		response.setDetails("bad data for: " + paramName + ", unable to process: " + data);
		return response;
	}
	
	@GetMapping(value = { "/api/cancel" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public OrderMgmtResponse cancel(HttpServletRequest request) {

		String username = request.getParameter("username");
		String numTickets = request.getParameter("numTickets");
		String eventId = request.getParameter("eventId");

		OrderMgmtResponse response = processInputs (username, numTickets, eventId);
		
		// hit an error, return immediately
		if (response.getCode().equals(ERROR_CODE)) {
			return response;
		}
						
		UserEntity user = orderMgmtService.findByUserName(username);
		if (user == null) {
			return noUserFound(username);
		}	
		
		// locate the event
		EventEntity event = orderMgmtService.findByEventId(Integer.parseInt(eventId));
		if (event == null) {
			return noEventFound (eventId);
		}
		
		CustomerOrderEntity custOrder = 
				orderMgmtService.findCustomerOrderByUserAndEventId(user, Integer.parseInt(eventId));
		
		// can't cancel more than current number of tickets
		if (Integer.parseInt(numTickets) > custOrder.getNumTickets()) {
			return invalidData ("numTickets", eventId);
		}
		
		int curNumTickets = custOrder.getNumTickets() - Integer.parseInt(numTickets);
		
		// order has been cancelled
		if (curNumTickets == 0) {
			custOrder.setOrderStatus(CANCELLED_ORDER);
		}		
		custOrder.setNumTickets(curNumTickets);
		Timestamp updatedDt = Timestamp.from(Instant.now());
		custOrder.setUpdatedDt(updatedDt);
		
		CustomerOrderEntity saved = orderMgmtService.saveCustOrder(custOrder);
					
		if (!saved.getUpdatedDt().equals(updatedDt)) {
			response.setCode(ERROR_CODE);
			response.setMessage(ERR_ORDER_NOT_UPDATED);
		}
		else {
			response.setDetails("Order: " + saved.getOrderId() + " updated to " + curNumTickets);
		}
	
		return response;
	}

	@GetMapping(value = { "/api/exchange" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public OrderMgmtResponse exchange(HttpServletRequest request) {

		String username = request.getParameter("username");
		String numTickets = request.getParameter("numTickets");
		String fromEventId = request.getParameter("fromEventId");
		String toEventId = request.getParameter("toEventId");

		// process inputs
		if (StringUtils.isBlank(username)) {
			return missingParam ("username");
		}		
		if (StringUtils.isBlank(fromEventId)) {			
			return missingParam ("fromEventId");
		}
		if (StringUtils.isBlank(numTickets)) {
			return missingParam ("numTickets");
		}
		if (!StringUtils.isNumeric(toEventId)) {
			return invalidData ("toEventId", toEventId);
		}
		if (!StringUtils.isNumeric(numTickets)) {
			return invalidData ("numTickets", numTickets);
		}
						
		UserEntity user = orderMgmtService.findByUserName(username);
		if (user == null) {
			return noUserFound(username);
		}	
		
		// locate the events
		EventEntity fromEvent = orderMgmtService.findByEventId(Integer.parseInt(fromEventId));
		if (fromEvent == null) {
			return noEventFound (fromEventId);
		}
		
		EventEntity toEvent = orderMgmtService.findByEventId(Integer.parseInt(toEventId));
		if (toEvent == null) {
			return noEventFound (toEventId);
		}
		
		// get order for the first event
		CustomerOrderEntity fromOrder = 
				orderMgmtService.findCustomerOrderByUserAndEventId(user, Integer.parseInt(fromEventId));
		
		// get order for the second event
		CustomerOrderEntity toOrder = 
				orderMgmtService.findCustomerOrderByUserAndEventId(user, Integer.parseInt(toEventId));
			
		// decrement tickets from the first show
		int fromOrderRemainingTickets = fromOrder.getNumTickets() - Integer.parseInt(numTickets);
		fromOrder.setNumTickets(fromOrderRemainingTickets);	
		
		// increment tickets at the second show
		int toOrderExchangedTickets = toOrder.getNumTickets() - Integer.parseInt(numTickets);
		toOrder.setNumTickets(toOrderExchangedTickets);

		orderMgmtService.saveCustOrder(fromOrder);
		orderMgmtService.saveCustOrder(toOrder);
		
		OrderMgmtResponse response = new OrderMgmtResponse();
		response.setCode(SUCCESS_CODE);
		response.setMessage(SUCCESS_MESSAGE);
				
		return response;
	}
	
	// not required by the spec, but we should have a way to
	// authorize the requests. easiest way is to shove a
	// key into the headers of the Http request, and
	// compare against a known-good key on the server side.
	// a better way would be to use a real authorization service like
	// SAML, OAuth or even Basic Http auth...
	private boolean authorizeRequest (HttpServletRequest request) {
		
		// 
		String apiKey = request.getHeader("ordermgmt-api-key");
		
		// TODO: compare against hard-coded API key
		return true;		
	}
	

}
