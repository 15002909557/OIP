package com.beyondsoft.expensesystem.domain.checker;

import java.sql.Date;

public class LeaveDetails {
	
	private int Id = -1;
	private int LeaveDataId = -1;
	private int leavetypeid = -1;
	private String leavetypename = "";
	private int owner = -1;
	private String username = "";
	private float Hours = 0f;
	private String PTOsubtype = "";
	private String date = "";
	private int HoursBeforeModify = -1;
	//@Dancy 2011-10-19
	private Date updateGridDate=null;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getLeaveDataId() {
		return LeaveDataId;
	}
	public void setLeaveDataId(int leaveDataId) {
		LeaveDataId = leaveDataId;
	}
	public String getPTOsubtype() {
		return PTOsubtype;
	}
	public void setPTOsubtype(String osubtype) {
		PTOsubtype = osubtype;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLeavetypename() {
		return leavetypename;
	}
	public void setLeavetypename(String leavetypename) {
		this.leavetypename = leavetypename;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getLeavetypeid() {
		return leavetypeid;
	}
	public void setLeavetypeid(int leavetypeid) {
		this.leavetypeid = leavetypeid;
	}
	public int getHoursBeforeModify() {
		return HoursBeforeModify;
	}
	public void setHoursBeforeModify(int hoursBeforeModify) {
		HoursBeforeModify = hoursBeforeModify;
	}
	//@Dancy
	public Date getUpdateGridDate(){
		return updateGridDate;
	}
	public void setUpdateGridDate(Date updateGrid){
		updateGridDate = updateGrid;
	}
	public float getHours() {
		return Hours;
	}
	public void setHours(float hours) {
		Hours = hours;
	}
}
