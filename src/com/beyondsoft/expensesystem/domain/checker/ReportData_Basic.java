package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportData_Basic implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private List<Date> listDate = new ArrayList<Date>();	
	private List<Project> listProject = new ArrayList<Project>();	
	private List<Double> listHours = new ArrayList<Double>(); 
	private List<String> listHoursComment = new ArrayList<String>();
	private List<String> listFirmware = new ArrayList<String>();
	private List<String> listWorkType = new ArrayList<String>();
	private List<String> listMilestone = new ArrayList<String>();
	private List<String> listTestType = new ArrayList<String>();
	//FWJ on 2013-05-07
	private List<String> listTargetLaunch = new ArrayList<String>();
	//FWJ on 2013-05-23
	private List<String> listDes= new ArrayList<String>();
	private List<String> listBudget= new ArrayList<String>();
	
	public List<String> getListDes() {
		return listDes;
	}
	public void setListDes(List<String> listDes) {
		this.listDes = listDes;
	}
	public List<String> getListBudget() {
		return listBudget;
	}
	public void setListBudget(List<String> listBudget) {
		this.listBudget = listBudget;
	}
	public List<String> getListTargetLaunch() {
		return listTargetLaunch;
	}
	public void setListTargetLaunch(List<String> listTargetLaunch) {
		this.listTargetLaunch = listTargetLaunch;
	}
	//Added on 2013-03-26
	private List<Invoice> listMonthExpense = new ArrayList<Invoice>();
	private List<String> listMonthExpenseID = new ArrayList<String>();
	private List<String> listMonthproject = new ArrayList<String>();

	private List<String> listYear = new ArrayList<String>();
	private List<String> listMonth = new ArrayList<String>();
	private List<String> listCategory = new ArrayList<String>();
	private List<String> listPONumber = new ArrayList<String>();
	private List<String> listClient = new ArrayList<String>();
	private List<String> listCost = new ArrayList<String>();
	private List<String> listWBS = new ArrayList<String>();
	private List<String> listInvoiceNumber = new ArrayList<String>();
	private List<String> listComments = new ArrayList<String>();
	
	public List<String> getListMonthproject() {
		return listMonthproject;
	}
	public void setListMonthproject(List<String> listMonthproject) {
		this.listMonthproject = listMonthproject;
	}
	public List<Invoice> getListMonthExpense() {
		return listMonthExpense;
	}
	public void setListMonthExpense(List<Invoice> listMonthExpense) {
		this.listMonthExpense = listMonthExpense;
	}
	public List<String> getListMonthExpenseID() {
		return listMonthExpenseID;
	}
	public void setListMonthExpenseID(List<String> listMonthExpenseID) {
		this.listMonthExpenseID = listMonthExpenseID;
	}
	public List<String> getListYear() {
		return listYear;
	}
	public void setListYear(List<String> listYear) {
		this.listYear = listYear;
	}
	public List<String> getListMonth() {
		return listMonth;
	}
	public void setListMonth(List<String> listMonth) {
		this.listMonth = listMonth;
	}
	public List<String> getListCategory() {
		return listCategory;
	}
	public void setListCategory(List<String> listCategory) {
		this.listCategory = listCategory;
	}
	public List<String> getListPONumber() {
		return listPONumber;
	}
	public void setListPONumber(List<String> listPONumber) {
		this.listPONumber = listPONumber;
	}
	public List<String> getListClient() {
		return listClient;
	}
	public void setListClient(List<String> listClient) {
		this.listClient = listClient;
	}
	public List<String> getListCost() {
		return listCost;
	}
	public void setListCost(List<String> listCost) {
		this.listCost = listCost;
	}
	public List<String> getListWBS() {
		return listWBS;
	}
	public void setListWBS(List<String> listWBS) {
		this.listWBS = listWBS;
	}
	public List<String> getListInvoiceNumber() {
		return listInvoiceNumber;
	}
	public void setListInvoiceNumber(List<String> listInvoiceNumber) {
		this.listInvoiceNumber = listInvoiceNumber;
	}
	public List<String> getListComments() {
		return listComments;
	}
	public void setListComments(List<String> listComments) {
		this.listComments = listComments;
	}
	public List<String> getListWorkType() {
		return listWorkType;
	}
	public void setListWorkType(List<String> listWorkType) {
		this.listWorkType = listWorkType;
	}
	public List<String> getListMilestone() {
		return listMilestone;
	}
	public void setListMilestone(List<String> listMilestone) {
		this.listMilestone = listMilestone;
	}
	public List<String> getListTestType() {
		return listTestType;
	}
	public void setListTestType(List<String> listTestType) {
		this.listTestType = listTestType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private int countOfRecord = 0;
		
	public int getCountOfRecord() {
		return countOfRecord;
	}
	public void setCountOfRecord(int countOfRecord) {
		this.countOfRecord = countOfRecord;
	}
	public List<String> getListHoursComment() {
		return listHoursComment;
	}
	public void setListHoursComment(List<String> listHoursComment) {
		this.listHoursComment = listHoursComment;
	}
	public List<Date> getListDate() {
		return listDate;
	}
	public void setListDate(List<Date> listDate) {
		this.listDate = listDate;
	}	
	public List<Project> getListProject() {
		return listProject;
	}
	public void setListProject(List<Project> listProject) {
		this.listProject = listProject;
	}
	public List<Double> getListHours() {
		return listHours;
	}
	public void setListHours(List<Double> listHours) {
		this.listHours = listHours;
	}
	public List<String> getListFirmware() {
		return listFirmware;
	}
	public void setListFirmware(List<String> listFirmware) {
		this.listFirmware = listFirmware;
	}
}
