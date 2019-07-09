package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;

/*
 * 2011-4-9
 * Author=longzhe
 * Description: ������¼ÿ��expensedata��¼�ĸ�����Ϣ��
 * 				��Ӧ��ݿ����expensedata_details��
 */
@SuppressWarnings("serial")
public class ExpenseDetail implements Serializable
{
	private int id = -1;
	private int expenseid = -1;
	private int worktypeid = -1;
	private int layerphareid = -1;
	private int testtype = -1;
//	private int testsession = -1;
	private int milestone = -1;
	private int skillcategory = -1;
	private String hour = "";
	private String comments = "";
	private String firmware = "";
	private String worktype="";
	private String testtypeName = "";
	private String milestoneName = "";
	private String descriptionName = "";
	
	//@Dancy 2011-10-25
	private int description = -1;
	private String memo = "";
	//Added the targetlaunchid by FWJ on 2013-03-07
	private int targetlaunchid = -1;
	private String targetlaunch = "";

	//Added by FWJ on 2013-05-20
	private int budgetid = -1;
	private String budget = "";
	private String descriptionofskill="";
	
	
	public int getBudgetid() {
		return budgetid;
	}
	public void setBudgetid(int budgetid) {
		this.budgetid = budgetid;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getDescriptionofskill() {
		return descriptionofskill;
	}
	public void setDescriptionofskill(String descriptionofskill) {
		this.descriptionofskill = descriptionofskill;
	}

	
	/*Added the Get and Set for targetlaunch by FWJ on 2013-03-07*/
	public int getTargetlaunchid() {
		return targetlaunchid;
	}
	public void setTargetlaunchid(int targetlaunchid) {
		this.targetlaunchid = targetlaunchid;
	}
	public String getTargetlaunch() {
		return targetlaunch;
	}
	public void setTargetlaunch(String targetlaunch) {
		this.targetlaunch = targetlaunch;
	}
	public String getFirmware() {
		return firmware;
	}
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getExpenseid() {
		return expenseid;
	}
	public void setExpenseid(int expenseid) {
		this.expenseid = expenseid;
	}
	public int getWorktypeid() {
		return worktypeid;
	}
	public void setWorktypeid(int worktypeid) {
		this.worktypeid = worktypeid;
	}
	public int getLayerphareid() {
		return layerphareid;
	}
	public void setLayerphareid(int layerphareid) {
		this.layerphareid = layerphareid;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public int getTesttype() {
		return testtype;
	}
	public void setTesttype(int testtype) {
		this.testtype = testtype;
	}
//	public int getTestsession() {
//		return testsession;
//	}
//	public void setTestsession(int testsession) {
//		this.testsession = testsession;
//	}
	public int getMilestone() {
		return milestone;
	}
	public void setMilestone(int milestone) {
		this.milestone = milestone;
	}
	public int getSkillcategory() {
		return skillcategory;
	}
	public void setSkillcategory(int skillcategory) {
		this.skillcategory = skillcategory;
	}
	
	
	//@Dancy 2011-10-25
	public int getDescription()
	{
		return description;
	}
	public void setDescription(int  description)
	{
		this.description = description;
	}
	
	public String getComm()
	{
		return memo;
	}
	public void setComm(String comm)
	{
		this.memo=comm;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getTesttypeName() {
		return testtypeName;
	}
	public void setTesttypeName(String testtypeName) {
		this.testtypeName = testtypeName;
	}
	public String getMilestoneName() {
		return milestoneName;
	}
	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}
	public String getDescriptionName() {
		return descriptionName;
	}
	public void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
