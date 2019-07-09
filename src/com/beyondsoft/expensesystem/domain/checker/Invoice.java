package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.sql.Date;

//yanglin
@SuppressWarnings("serial")
public class Invoice implements Serializable
{
	private int invoiceID = -1;
	private String InvoiceNumber = null;
	private int POid = -1;
	private Date PeriodStartDate = null;
	private Date PeriodEndDate = null;
	private double Amount = 0;
	private String Comment = "";
	private String WBSNumber = null;
	
	//Added on 2013-03-18 by FWJ
	private int monthprojectid = -1;
	private String monthproject = null;
	private int monthid = 0;
	private String month = null;
	private int categoryid = -1;
	private String category = null;
	private int clientid = -1;
	private String client = null;
	private int costid = -1;
	private String cost = null;
	private int monthlyexpenseid=-1;
	private int yearid=-1;
	private String year=null;
	private String month_comment = "";
	private String PONum = null;
	private int WBSid=0;
	private String monthproject_text=null;
	
	//Added on 2013-06-19 FWJ
	private String payment = null;
	
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
	public int getMonthid() {
		return monthid;
	}
	public void setMonthid(int monthid) {
		this.monthid = monthid;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getClientid() {
		return clientid;
	}
	public void setClientid(int clientid) {
		this.clientid = clientid;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public int getCostid() {
		return costid;
	}
	public void setCostid(int costid) {
		this.costid = costid;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public int getMonthlyexpenseid() {
		return monthlyexpenseid;
	}
	public void setMonthlyexpenseid(int monthlyexpenseid) {
		this.monthlyexpenseid = monthlyexpenseid;
	}
	public int getYearid() {
		return yearid;
	}
	public void setYearid(int yearid) {
		this.yearid = yearid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth_comment() {
		return month_comment;
	}
	public void setMonth_comment(String monthComment) {
		month_comment = monthComment;
	}
	public String getPONum() {
		return PONum;
	}
	public void setPONum(String pONum) {
		PONum = pONum;
	}
	public int getWBSid() {
		return WBSid;
	}
	public void setWBSid(int wBSid) {
		WBSid = wBSid;
	}
	public String getMonthproject_text() {
		return monthproject_text;
	}
	public void setMonthproject_text(String monthprojectText) {
		monthproject_text = monthprojectText;
	}
	public String getWBSNumber() {
		return WBSNumber;
	}
	public void setWBSNumber(String number) {
		WBSNumber = number;
	}
	public int getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}
	public String getInvoiceNumber() {
		return InvoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}
	public int getPOid() {
		return POid;
	}
	public void setPOid(int oid) {
		POid = oid;
	}
	public Date getPeriodStartDate() {
		return PeriodStartDate;
	}
	public void setPeriodStartDate(Date periodStartDate) {
		PeriodStartDate = periodStartDate;
	}
	public Date getPeriodEndDate() {
		return PeriodEndDate;
	}
	public void setPeriodEndDate(Date periodEndDate) {
		PeriodEndDate = periodEndDate;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		Amount = amount;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}

}
