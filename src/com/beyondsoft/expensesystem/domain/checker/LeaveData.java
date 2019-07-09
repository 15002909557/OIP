package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;


/**
 * 用户假期信息
 * 
 * @author Longzhe
 * 
 */

@SuppressWarnings("serial")
public class LeaveData implements Serializable {
	private int Id = -1;
	private int LeaveType = -1;
	private String LeaveTypeName = "";
	private float DefaultHours = 0;
	private float TotalHours = 0;
	private float DefaultUsed = 0;
	private float usedHours = 0;
	private float AvailableHours = 0;
	private int owner = -1;
	private String ownerName = "";
	private String comments = "";
	private Date employDate=null;
	private float IncrementHours = 0;
	
	private List<LeaveDetails> details;
	
	public boolean initPTOTotalHoursandAvailableHours(){
		if (this.LeaveTypeName.equals("PTO")){
			//java.util.Date dateNow=new java.util.Date();
			//int PTOleave = 0;
			/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date  date;
			try{
				date = sdf.parse("2011-01-01");
			}catch(Exception e) {
				return false;
			}		
			
			date = this.employDate;   
			
			if (null==date){
				date= new java.util.Date();
			}
			
			PTOleave = (int)((((dateNow.getTime()-date.getTime())/86400000.0) / 365.0) *15*8);
			this.TotalHours=PTOleave+this.DefaultHours;
			*/
			float temp1 = this.DefaultHours + this.IncrementHours - this.usedHours - this.DefaultUsed;
			this.AvailableHours=temp1;
			//System.out.println("temp1="+temp1);
			return true;
		}else{
			return false;
		}
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getLeaveType() {
		return LeaveType;
	}

	public void setLeaveType(int leaveType) {
		LeaveType = leaveType;
	}

	public String getLeaveTypeName() {
		return LeaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		LeaveTypeName = leaveTypeName;
	}

	public float getDefaultHours() {
		return DefaultHours;
	}

	public void setDefaultHours(float defaultHours) {
		DefaultHours = defaultHours;
	}

	public float getDefaultUsed() {
		return DefaultUsed;
	}

	public void setDefaultUsed(float defaultUsed) {
		DefaultUsed = defaultUsed;
	}

	public float getTotalHours() {
		return TotalHours;
	}

	public void setTotalHours(float totalHours) {
		TotalHours = totalHours;
	}

	public float getUsedHours() {
		return usedHours;
	}

	public void setUsedHours(float usedHours) {
		this.usedHours = usedHours;
	}

	public float getAvailableHours() {
		return AvailableHours;
	}

	public void setAvailableHours(float availableHours) {
		AvailableHours = availableHours;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getEmployDate() {
		return employDate;
	}

	public void setEmployDate(Date employDate) {
		this.employDate = employDate;
	}

	public float getIncrementHours() {
		return IncrementHours;
	}

	public void setIncrementHours(float incrementHours) {
		IncrementHours = incrementHours;
	}

	public List<LeaveDetails> getDetails() {
		return details;
	}

	public void setDetails(List<LeaveDetails> details) {
		this.details = details;
	}
	
	
}
