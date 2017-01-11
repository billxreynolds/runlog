package com.breynolds.runlog.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "RUN_LOG")
public class RunLogEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6908641690112989929L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "RUN_DATE")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date runDate;

	@Column(name = "ROUTE")
	private String route;

	@Column(name = "MILEAGE")
	private BigDecimal mileage;

	@Column(name = "RUN_TIME")
	private Time runTime;

	@Column(name = "COMMENTS")
	private String comments;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public BigDecimal getMileage() {
		return mileage;
	}

	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}

	public Time getRunTime() {
		return runTime;
	}

	public void setRunTime(Time runTime) {
		this.runTime = runTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMileageAsString() {
		String result = "";
		try {
			result = mileage.toString();
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	public String getRunTimeAsString() {
		String result = "";
		try {
			result = runTime.toString();
		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	public RunLogEntity() {

	}

}
