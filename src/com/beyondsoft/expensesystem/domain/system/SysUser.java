package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 系统用户
 * 
 * @author zhusimin
 * 
 */
@SuppressWarnings("serial")
public class SysUser implements Serializable {
	private int userId = -1;
	private String userName = "";
	private String password = "";
	private int remove = 0;
	private String groupName = "";
	private String lockday = "";
	private String approveday = null; 
	private int groupID = -1;
	private int workloadgroupID = -1;
	private List<String> imPermissions;
	private Date employdate = null;
	private String email = "";
	private String massage = "";
	private int levelID = -1;
	private int ApproveLevel = 0;
	private int Approved = 0;
	private int UI = 0;
	private int defaultUI = -1;
	private int HeadorHour = 0;
	private double workloadRate = 0;
	private String HPEmployeeNumber =null;
	private int PTOOverdraft = 0;
	private int PTOrate = 0;
	private Date leaveTime =null;
	private String EmployDate2 = null;
	private String level = ""; //2012-11-14 by dancy
	private String announcement = null;
	private String groupAnnounce = null;
	//hanxiaoyu 2012-12-11
	private Date changePSW;
	private long expireDay;//密码即将过期的天数
	private String approveLevelName = "";
	
	
	public String getApproveLevelName() {
		return approveLevelName;
	}

	public void setApproveLevelName(String approveLevelName) {
		this.approveLevelName = approveLevelName;
	}

	public String getGroupAnnounce() {
		return groupAnnounce;
	}

	public void setGroupAnnounce(String groupAnnounce) {
		this.groupAnnounce = groupAnnounce;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}


	public int getPTOrate() {
		return PTOrate;
	}

	public void setPTOrate(int orate) {
		PTOrate = orate;
	}

	public int getPTOOverdraft() {
		return PTOOverdraft;
	}

	public void setPTOOverdraft(int overdraft) {
		PTOOverdraft = overdraft;
	}

	public int getHeadorHour() {
		return HeadorHour;
	}

	public void setHeadorHour(int headorHour) {
		HeadorHour = headorHour;
	}

	public int getUI() {
		return UI;
	}

	public void setUI(int ui) {
		UI = ui;
	}

	public int getApproved() {
		return Approved;
	}

	public void setApproved(int approved) {
		Approved = approved;
	}

	public int getApproveLevel() {
		return ApproveLevel;
	}

	public void setApproveLevel(int approveLevel) {
		ApproveLevel = approveLevel;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getEmploydate() {
		return employdate;
	}

	public void setEmploydate(Date employdate) {
		this.employdate = employdate;
	}

	public List<String> getImPermissions() {
		return imPermissions;
	}

	public int getPermissions(String permissionName) {
		int value = 1;
		if (imPermissions.contains(permissionName))
			value = 0;
		return value;
	}

	public void setImPermissions(List<String> imPermissions) {
		this.imPermissions = imPermissions;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRemove() {
		return remove;
	}

	public void setRemove(int remove) {
		this.remove = remove;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getLockday() {
		return lockday;
	}

	public void setLockday(String lockday) {
		this.lockday = lockday;
	}

	public String getApproveday() {
		return approveday;
	}

	public void setApproveday(String approveday) {
		this.approveday = approveday;
	}

	public int getDefaultUI() {
		return defaultUI;
	}

	public void setDefaultUI(int defaultUI) {
		this.defaultUI = defaultUI;
	}

	public int getWorkloadgroupID() {
		return workloadgroupID;
	}

	public void setWorkloadgroupID(int workloadgroupID) {
		this.workloadgroupID = workloadgroupID;
	}

	public double getWorkloadRate() {
		return workloadRate;
	}

	public void setWorkloadRate(double workloadRate) {
		this.workloadRate = workloadRate;
	}

	public String getHPEmployeeNumber() {
		return HPEmployeeNumber;
	}
/*
 * @Dancy 2011-10-8 
 */
	public void setHPEmployeeNumber(String employeeNumber) {
		this.HPEmployeeNumber = employeeNumber;
	}
	
	/*
	 * @Dancy 2011-10-10
	 */
	public Date getLeaveTime() {

		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getEmployDate2() {
		return EmployDate2;
	}

	public void setEmployDate2(String employDate2) {
		EmployDate2 = employDate2;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public Date getChangePSW() {
		return changePSW;
	}

	public void setChangePSW(Date changePSW) {
		this.changePSW = changePSW;
	}

	public long getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(long expireDay) {
		this.expireDay = expireDay;
	}
	
}


