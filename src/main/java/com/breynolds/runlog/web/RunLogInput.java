package com.breynolds.runlog.web;

public class RunLogInput {

	private Integer runID;
	private String runDate;
	private String route;
	private String mileage;
	private String runTime;
	private String comments;
	private boolean existing;

	public Integer getRunID() {
		return runID;
	}

	public void setRunID(Integer runID) {
		this.runID = runID;
	}

	public String getRunDate() {
		return runDate;
	}

	public void setRunDate(String runDate) {
		this.runDate = runDate;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}

}
