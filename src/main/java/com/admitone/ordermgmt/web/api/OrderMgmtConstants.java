package com.admitone.ordermgmt.web.api;

public interface OrderMgmtConstants {

	// REST status messages
	public String SUCCESS_CODE = "SUCCESS";
	public String SUCCESS_MESSAGE = "Request processed successfully";
		
	// REST error messages
	public String ERROR_CODE = "ERROR";
	public String ERR_UNAUTHORIZED_REQ = "Unauthorized Request";
	public String ERR_INVALID_API = "Invalid API Key";
	public String ERR_MISSING_PARAM = "Missing Required Param";
	public String ERR_BAD_DATA = "Bad Data Type";
	public String ERR_NO_DATA_FOUND = "No Data Found";
	public String ERR_NO_USER_FOUND = "No User Found";
	public String ERR_ORDER_NOT_CREATED = "Order Not Created";
	public String ERR_ORDER_NOT_UPDATED = "Order Not Updated";
	public String ERR_INVALID_CANCEL_NUM_TIX = "Attempt to cancel more tickets than ordered";
	public String ERR_INVALID_EXCHANGE_NUM_TIX = "Attempt to exchange more tickets than ordered";
	
	// DB status flags
	public String ACTIVE_ORDER = "ACTIVE";
	public String CANCELLED_ORDER = "CANCELLED";
}
