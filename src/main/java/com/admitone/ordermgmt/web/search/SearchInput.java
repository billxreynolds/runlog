package com.admitone.ordermgmt.web.search;

public class SearchInput {

	private Integer startID;
	private Integer endID;
	private String startDate;
	private String endDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Integer getStartID() {
		return startID;
	}
	public void setStartID(Integer startID) {
		this.startID = startID;
	}
	public Integer getEndID() {
		return endID;
	}
	public void setEndID(Integer endID) {
		this.endID = endID;
	}
	
}
