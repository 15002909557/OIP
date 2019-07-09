package com.beyondsoft.expensesystem.domain.checker;

public class StatusChangeLog {
	private String StatusFrom = null;
	private String StatusTo = null;
	private String ChangeDateFrom = null;
	private String ChangeDateTo = null;
	private String TimeStamp = null;
	private String changeUser = null;
	//2011-12-19 change user id
	private int changeUserID = -1;
	private int poNum = -1;
	private int LogID = -1;
	public String getStatusFrom() {
		return StatusFrom;
	}
	public void setStatusFrom(String statusFrom) {
		StatusFrom = statusFrom;
	}
	public String getStatusTo() {
		return StatusTo;
	}
	public void setStatusTo(String statusTo) {
		StatusTo = statusTo;
	}
	public String getChangeDateFrom() {
		return ChangeDateFrom;
	}
	public void setChangeDateFrom(String changeDateFrom) {
		ChangeDateFrom = changeDateFrom;
	}
	public String getChangeDateTo() {
		return ChangeDateTo;
	}
	public void setChangeDateTo(String changeDateTo) {
		ChangeDateTo = changeDateTo;
	}
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getChangeUser() {
		return changeUser;
	}
	public void setChangeUser(String changeUser) {
		this.changeUser = changeUser;
	}
	public int getLogID() {
		return LogID;
	}
	public void setLogID(int logID) {
		LogID = logID;
	}
	public int getChangeUserID() {
		return changeUserID;
	}
	public void setChangeUserID(int changeUserID) {
		this.changeUserID = changeUserID;
	}
	public int getPoNum() {
		return poNum;
	}
	public void setPoNum(int poNum) {
		this.poNum = poNum;
	}

}
