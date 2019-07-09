package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;

/*
 * 2011-12-06 by dancy
 */
public class ProjectOrder implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	private int POID = -1;
	private String PONumber = null;
	private int CostLocationID = -1;//String ��Ϊint by Dancy 2011-12-14
	private double POAmount = 0;
	private String POStartDate = null;
	private String POEndDate = null;
	private int BillCycleid = -1;
	private int POManagerid = -1;
	private String description = "";
	private String lock = null;
	private String POStatus = null;
	private int LastStatusChangeID = -1;
	private String NotStartStatusDate = null;
	private String OpenStatusDate = null;
	private String CloseStatusDate = null;
	private double AlertBalance = 0;
	private double CloseBalance = 0;
	private String BalanceUpdateDate = null;
	private String WBSNumber  = null;
	private String BYSQuatationNumber = null;
	private String comment = null;
	
	//20111207
	private String POManager = null;
	//20111208
	private int proid = -1;
	private int comid = -1;
	private int pid = -1;
	private String pnum = null;
	//20111212
	private String pdct = null;
	private String pcom= null;
	//20111214
	private String LocationCode = null;
	private String email = null;
	private double poUsed = 0;
	private double poBalance = 0;
	
	//20120119
	private String lastRecordDate = null;

	public String getLastRecordDate() {
		return lastRecordDate;
	}
	public void setLastRecordDate(String lastRecordDate) {
		this.lastRecordDate = lastRecordDate;
	}
	public double getPoUsed() {
		return poUsed;
	}
	public void setPoUsed(double poUsed) {
		this.poUsed = poUsed;
	}
	public double getPoBalance() {
		return poBalance;
	}
	public void setPoBalance(double poBalance) {
		this.poBalance = poBalance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocationCode() {
		return LocationCode;
	}
	public void setLocationCode(String locationCode) {
		LocationCode = locationCode;
	}
	public String getPdct() {
		return pdct;
	}
	public void setPdct(String pdct) {
		this.pdct = pdct;
	}
	public String getPcom() {
		return pcom;
	}
	public void setPcom(String pcom) {
		this.pcom = pcom;
	}
	public int getProid() {
		return proid;
	}
	public void setProid(int proid) {
		this.proid = proid;
	}
	public int getComid() {
		return comid;
	}
	public void setComid(int comid) {
		this.comid = comid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getPOManager() {
		return POManager;
	}
	public void setPOManager(String manager) {
		POManager = manager;
	}
	public int getPOID() {
		return POID;
	}
	public void setPOID(int poid) {
		POID = poid;
	}
	public String getPONumber() {
		return PONumber;
	}
	public void setPONumber(String number) {
		PONumber = number;
	}
	public int getCostLocationID() {
		return CostLocationID;
	}
	public void setCostLocationID(int costLocationID) {
		CostLocationID = costLocationID;
	}
	public double getPOAmount() {
		return POAmount;
	}
	public void setPOAmount(double amount) {
		POAmount = amount;
	}
	public String getPOStartDate() {
		return POStartDate;
	}
	public void setPOStartDate(String startDate) {
		POStartDate = startDate;
	}
	public String getPOEndDate() {
		return POEndDate;
	}
	public void setPOEndDate(String endDate) {
		POEndDate = endDate;
	}
	public int getBillCycleid() {
		return BillCycleid;
	}
	public void setBillCycleid(int billCycleid) {
		BillCycleid = billCycleid;
	}
	public int getPOManagerid() {
		return POManagerid;
	}
	public void setPOManagerid(int managerid) {
		POManagerid = managerid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getPOStatus() {
		return POStatus;
	}
	public void setPOStatus(String status) {
		POStatus = status;
	}
	public int getLastStatusChangeID() {
		return LastStatusChangeID;
	}
	public void setLastStatusChangeID(int lastStatusChangeID) {
		LastStatusChangeID = lastStatusChangeID;
	}
	public String getNotStartStatusDate() {
		return NotStartStatusDate;
	}
	public void setNotStartStatusDate(String notStartStatusDate) {
		NotStartStatusDate = notStartStatusDate;
	}
	public String getOpenStatusDate() {
		return OpenStatusDate;
	}
	public void setOpenStatusDate(String openStatusDate) {
		OpenStatusDate = openStatusDate;
	}
	public String getCloseStatusDate() {
		return CloseStatusDate;
	}
	public void setCloseStatusDate(String closeStatusDate) {
		CloseStatusDate = closeStatusDate;
	}
	public double getAlertBalance() {
		return AlertBalance;
	}
	public void setAlertBalance(double alertBalance) {
		AlertBalance = alertBalance;
	}
	public double getCloseBalance() {
		return CloseBalance;
	}
	public void setCloseBalance(double closeBalance) {
		CloseBalance = closeBalance;
	}
	public String getBalanceUpdateDate() {
		return BalanceUpdateDate;
	}
	public void setBalanceUpdateDate(String balanceUpdateDate) {
		BalanceUpdateDate = balanceUpdateDate;
	}
	public String getWBSNumber() {
		return WBSNumber;
	}
	public void setWBSNumber(String number) {
		WBSNumber = number;
	}
	public String getBYSQuatationNumber() {
		return BYSQuatationNumber;
	}
	public void setBYSQuatationNumber(String quatationNumber) {
		BYSQuatationNumber = quatationNumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	

}
