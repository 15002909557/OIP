package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.sql.Date;

//FWJ
@SuppressWarnings("serial")
public class Monthlyproject implements Serializable
{

	private int id =-1;
	private int monthprojectid = -1;
	private String monthproject = null;
	private int locationid = -1;
	private String location = null;
	private int businesscategoryid = -1;
	private String businesscategory = null;
	private int paymentid = -1;
	private String payment = null;
	
	private double budget = 0;
	private double usedBudegt = 0;
	private double remainBalance = 0;
	
	private double costInLatestMonth = 0;
	
	private String month =null;
	private String year = null;
	private String cost =null;
	private String po = null;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMonthprojectid() {
		return monthprojectid;
	}

	public void setMonthprojectid(int monthprojectid) {
		this.monthprojectid = monthprojectid;
	}

	public String getMonthproject() {
		return monthproject;
	}

	public void setMonthproject(String monthproject) {
		this.monthproject = monthproject;
	}

	public int getLocationid() {
		return locationid;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getBudget() {
		return budget;
	}

	public int getBusinesscategoryid() {
		return businesscategoryid;
	}

	public void setBusinesscategoryid(int businesscategoryid) {
		this.businesscategoryid = businesscategoryid;
	}

	public String getBusinesscategory() {
		return businesscategory;
	}

	public int getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(int paymentid) {
		this.paymentid = paymentid;
	}

	public void setBusinesscategory(String businesscategory) {
		this.businesscategory = businesscategory;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getUsedBudegt() {
		return usedBudegt;
	}

	public void setUsedBudegt(double usedBudegt) {
		this.usedBudegt = usedBudegt;
	}

	public double getRemainBalance() {
		return remainBalance;
	}

	public void setRemainBalance(double remainBalance) {
		this.remainBalance = remainBalance;
	}

	public double getCostInLatestMonth() {
		return costInLatestMonth;
	}

	public void setCostInLatestMonth(double costInLatestMonth) {
		this.costInLatestMonth = costInLatestMonth;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	
}
