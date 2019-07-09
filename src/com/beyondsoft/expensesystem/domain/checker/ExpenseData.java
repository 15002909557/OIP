package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;

public class ExpenseData implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int expenseDataId = -1;
	private int projectId = -1;
	private int userId = -1;
	private String createTime = null;
	private String projectName = null;
	private String skillLevel = null;
	private String location = null;
	private String OTType = null;
	private String comments = new String();
	private int confirm = 0;
	private int remove = 0;
	private String hour1 = "";
	private String hour2 = "";
	private String hour3 = "";
	private String hour4 = "";
	private String hour5 = "";
	private String hour6 = "";
	private String hour7 = "";
	private String hours = "";
	private String comment1 = "";
	private String comment2 = "";
	private String comment3 = "";
	private String comment4 = "";
	private String comment5 = "";
	private String comment6 = "";
	private String comment7 = "";
	private double rate = 0.0;
	private int groupId = -1;
	private String product = "";
	private String leaveType = "";
	private int leaveQuotaHours = 0;
	private int isSummary = 0;
	private String groupName = "";
	private String userName="";
	private int AvailQuotaHours =0;
	private String program = "";
	private int hidden = 0;
	private String componentname = "";
	private String wbs = "";
	
	public String getWbs() {
		return wbs;
	}
	public void setWbs(String wbs) {
		this.wbs = wbs;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private int detailscount1 = 0; // by collie 0428: 把details信息传到Daily Records页面
	private int detailscount2 = 0; // by collie 0428: 把details信息传到Daily Records页面
	private int detailscount3 = 0; // by collie 0428: 把details信息传到Daily Records页面
	private int detailscount4 = 0; // by collie 0428: 把details信息传到Daily Records页面
	private int detailscount5 = 0; // by collie 0428: 把details信息传到Daily Records页面
	private int detailscount6 = 0; // by collie 0428: 把details信息传到Daily Records页面
	private int detailscount7 = 0; // by collie 0428: 把details信息传到Daily Records页面
	
	private String PONumber = "";
	
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getPONumber() {
		return PONumber;
	}
	public void setPONumber(String number) {
		PONumber = number;
	}
	public int getDetailscount1() {
		return detailscount1;
	}
	public void setDetailscount1(int detailscount1) {
		this.detailscount1 = detailscount1;
	}
	public int getDetailscount2() {
		return detailscount2;
	}
	public void setDetailscount2(int detailscount2) {
		this.detailscount2 = detailscount2;
	}
	public int getDetailscount3() {
		return detailscount3;
	}
	public void setDetailscount3(int detailscount3) {
		this.detailscount3 = detailscount3;
	}
	public int getDetailscount4() {
		return detailscount4;
	}
	public void setDetailscount4(int detailscount4) {
		this.detailscount4 = detailscount4;
	}
	public int getDetailscount5() {
		return detailscount5;
	}
	public void setDetailscount5(int detailscount5) {
		this.detailscount5 = detailscount5;
	}
	public int getDetailscount6() {
		return detailscount6;
	}
	public void setDetailscount6(int detailscount6) {
		this.detailscount6 = detailscount6;
	}
	public int getDetailscount7() {
		return detailscount7;
	}
	public void setDetailscount7(int detailscount7) {
		this.detailscount7 = detailscount7;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public int getAvailQuotaHours() {
		return AvailQuotaHours;
	}
	public void setAvailQuotaHours(int availQuotaHours) {
		AvailQuotaHours = availQuotaHours;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public double getRate()
	{
		return rate;
	}
	public void setRate(double rate)
	{
		this.rate = rate;
	}
	public int getGroupId()
	{
		return groupId;
	}
	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}
	public String getProduct()
	{
		return product;
	}
	public void setProduct(String product)
	{
		this.product = product;
	}
	public String getLeaveType()
	{
		return leaveType;
	}
	public void setLeaveType(String leaveType)
	{
		this.leaveType = leaveType;
	}
	public int getLeaveQuotaHours()
	{
		return leaveQuotaHours;
	}
	public void setLeaveQuotaHours(int leaveQuotaHours)
	{
		this.leaveQuotaHours = leaveQuotaHours;
	}
	public int getIsSummary()
	{
		return isSummary;
	}
	public void setIsSummary(int isSummary)
	{
		this.isSummary = isSummary;
	}
	public String getComment1()
	{
		return comment1;
	}
	public void setComment1(String comment1)
	{
		this.comment1 = comment1;
	}
	public String getComment2()
	{
		return comment2;
	}
	public void setComment2(String comment2)
	{
		this.comment2 = comment2;
	}
	public String getComment3()
	{
		return comment3;
	}
	public void setComment3(String comment3)
	{
		this.comment3 = comment3;
	}
	public String getComment4()
	{
		return comment4;
	}
	public void setComment4(String comment4)
	{
		this.comment4 = comment4;
	}
	public String getComment5()
	{
		return comment5;
	}
	public void setComment5(String comment5)
	{
		this.comment5 = comment5;
	}
	public String getComment6()
	{
		return comment6;
	}
	public void setComment6(String comment6)
	{
		this.comment6 = comment6;
	}
	public String getComment7()
	{
		return comment7;
	}
	public void setComment7(String comment7)
	{
		this.comment7 = comment7;
	}
	public String getHours()
	{
		return hours;
	}
	public void setHours(String hours)
	{
		this.hours = hours;
	}
	public int getExpenseDataId()
	{
		return expenseDataId;
	}
	public void setExpenseDataId(int expenseDataId)
	{
		this.expenseDataId = expenseDataId;
	}
	public String getHour1() {
		return hour1;
	}
	public void setHour1(String hour1) {
		this.hour1 = hour1;
	}
	public String getHour2() {
		return hour2;
	}
	public void setHour2(String hour2) {
		this.hour2 = hour2;
	}
	public String getHour3() {
		return hour3;
	}
	public void setHour3(String hour3) {
		this.hour3 = hour3;
	}
	public String getHour4() {
		return hour4;
	}
	public void setHour4(String hour4) {
		this.hour4 = hour4;
	}
	public String getHour5() {
		return hour5;
	}
	public void setHour5(String hour5) {
		this.hour5 = hour5;
	}
	public String getHour6() {
		return hour6;
	}
	public void setHour6(String hour6) {
		this.hour6 = hour6;
	}
	public String getHour7() {
		return hour7;
	}
	public void setHour7(String hour7) {
		this.hour7 = hour7;
	}
	public int getProjectId()
	{
		return projectId;
	}
	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}
	public int getUserId()
	{
		return userId;
	}
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public String getProjectName()
	{
		return projectName;
	}
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
	public String getSkillLevel()
	{
		return skillLevel;
	}
	public void setSkillLevel(String skillLevel)
	{
		this.skillLevel = skillLevel;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getOTType()
	{
		return OTType;
	}
	public void setOTType(String oTType)
	{
		OTType = oTType;
	}
	public String getComments()
	{
		return comments;
	}
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	public int getConfirm()
	{
		return confirm;
	}
	public void setConfirm(int confirm)
	{
		this.confirm = confirm;
	}
	public int getRemove()
	{
		return remove;
	}
	public void setRemove(int remove)
	{
		this.remove = remove;
	}
	public int getHidden() {
		return hidden;
	}
	public void setHidden(int hidden) {
		this.hidden = hidden;
	}
	public String getComponentname() {
		return componentname;
	}
	public void setComponentname(String componentname) {
		this.componentname = componentname;
	}

}
