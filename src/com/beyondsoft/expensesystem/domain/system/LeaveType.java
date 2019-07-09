package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

public class LeaveType implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int leaveTypeId = -1;
	private String leaveTypeName = "";
	private int exceedHours = 0;
	
	public int getLeaveTypeId()
	{
		return leaveTypeId;
	}
	public void setLeaveTypeId(int leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}
	public int getExceedHours() {
		return exceedHours;
	}
	public void setExceedHours(int exceedHours) {
		this.exceedHours = exceedHours;
	}
}
