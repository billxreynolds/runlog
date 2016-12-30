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
		
		// locate the given user
		UserEntity user = orderMgmtService.findByUserName(username);		
		if (user == null) {
			return noUserFound(username);
		}			
		
		// place order
		Timestamp orderDt = Timestamp.from(Instant.now());
		CustomerOrderEntity custOrder = new CustomerOrderEntity();
		custOrder.setUserId(user.getUserId());
		custOrder.setEventId(Integer.parseInt(eventId));
		custOrder.setNumTickets(Integer.parseInt(numTickets));
		custOrder.setOrderStatus(ACTIVE_ORDER);
		custOrder.setOrderDt(orderDt);
		custOrder.setCreatedDt(orderDt);
		custOrder.setUpdatedDt(orderDt);
		
		OrderMgmtResponse response = new OrderMgmtResponse();
		CustomerOrderEntity savedOrder = orderMgmtService.saveCustOrder(custOrder);
				
		if (savedOrder.getOrderId() != null) {
			response.setCode(SUCCESS_CODE);
			response.setMessage(SUCCESS_MESSAGE);
			response.setDetails("Created order: " + savedOrder.getOrderId());
		}
		else {
			response.setCode(ERROR_CODE);
			response.setMessage(ERR_ORDER_NOT_CREATED);			
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
		response.setDetails("unable to locate: " + userName);
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
						
		UserEntity user = orderMgmtService.findByUserName(username);
		if (user == null) {
			return noUserFound(username);
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
		
		OrderMgmtResponse response = new OrderMgmtResponse();
		if (!saved.getUpdatedDt().equals(updatedDt)) {
			response.setCode(ERROR_CODE);
			response.setMessage(ERR_ORDER_NOT_UPDATED);
		}
		else {
			response.setCode(SUCCESS_CODE);
			response.setMessage(SUCCESS_MESSAGE);
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
		
		// get the first show
		CustomerOrderEntity fromOrder = 
				orderMgmtService.findCustomerOrderByUserAndEventId(user, Integer.parseInt(fromEventId));
		
		// get the second show
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
	
	private boolean authorizeRequest (HttpServletRequest request) {
		
		// 
		String apiKey = request.getHeader("ordermgmt-api-key");
		
		// TODO: compare against hard-coded API key
		return true;		
	}
	

}
