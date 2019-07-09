package com.beyondsoft.expensesystem.domain.checker;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int projectId = -1;
	private String component = "";
	private int componentid = -1;
	private int userId = -1;
	private Date createTime = null;
	private String skillLevel = null;
	private String location = null;
	private String OTType = null;
	private int groupId = -1;

	private String comments = null;
	private int confirm = 0;
	private int remove = 0;
	private ExpenseData expenseData = new ExpenseData();
	
	private String groupName = null;
	
	private double rate = 0 ;
	private String product = "N/A";
	private int isSummary =0; 
	private String locationId = null;
	private String HPManager = null;	
	private String WBS = null;
	private String programme = null;
	private int structureLevel = 0;
	
	private String reportGroup = null;
	private String program = null;
	private String testAsset = null;
	private String activityType = null;
	private String targetMilestone = null;
	private String description = null;//还原by Dancy 20111109
	private String country = "";
	private String skillSubType = "";
	
	//modify by collie
	private String HPManager2 = "";
	private String site = "";
	private String testType = "";
	private String testPhase = "";
	private String session = "";
	private String POName = "";
	private int poid = -1;
	
	private String username = "";// by collie 0418
	private int hidden = 0; // by collie 0418
	
	private String skillCategory = ""; // by collie 0517 加SkillCategory
	
	private int projectID = -1;//by dancy 2012-01-16
	private float OTRate =0f;
	private float RateP = 0f;
	private float SumHours = 0f;
	private double sumNonLaborCost = 0f;
	private String createDate = "";
	private double POInitinal = 0f;
	
	
	public double getPOInitinal() {
		return POInitinal;
	}
	public void setPOInitinal(double pOInitinal) {
		POInitinal = pOInitinal;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public double getSumNonLaborCost() {
		return sumNonLaborCost;
	}
	public void setSumNonLaborCost(Double sumNonLaborCost) {
		this.sumNonLaborCost = sumNonLaborCost;
	}
	public float getOTRate() {
		return OTRate;
	}
	public void setOTRate(float rate) {
		OTRate = rate;
	}
	public float getRateP() {
		return RateP;
	}
	public void setRateP(float rateP) {
		RateP = rateP;
	}
	public float getSumHours() {
		return SumHours;
	}
	public void setSumHours(float sumHours) {
		SumHours = sumHours;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getSkillCategory() {
		return skillCategory;
	}
	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getHidden() {
		return hidden;
	}
	public void setHidden(int hidden) {
		this.hidden = hidden;
	}
	public String getHPManager2() {
		return HPManager2;
	}
	public void setHPManager2(String manager2) {
		HPManager2 = manager2;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getDescription() {//还原by Dancy 20111109
		return description;
	}
	public void setDescription(String description) {//还原by Dancy 20111109
		this.description = description;
	}
	public String getReportGroup() {
		return reportGroup;
	}
	public void setReportGroup(String reportGroup) {
		this.reportGroup = reportGroup;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getTestAsset() {
		return testAsset;
	}
	public void setTestAsset(String testAsset) {
		this.testAsset = testAsset;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getTargetMilestone() {
		return targetMilestone;
	}
	public void setTargetMilestone(String targetMilestone) {
		this.targetMilestone = targetMilestone;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getIsSummary() {
		return isSummary;
	}
	public void setIsSummary(int isSummary) {
		this.isSummary = isSummary;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getHPManager() {
		return HPManager;
	}
	public void setHPManager(String hPManager) {
		HPManager = hPManager;
	}
	public String getWBS() {
		return WBS;
	}
	public void setWBS(String WBS) {
		this.WBS = WBS;
	}
	public String getProgramme() {
		return programme;
	}
	public void setProgramme(String programme) {
		this.programme = programme;
	}
	public int getStructureLevel() {
		return structureLevel;
	}
	public void setStructureLevel(int structureLevel) {
		this.structureLevel = structureLevel;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public ExpenseData getExpenseData()
	{
		return expenseData;
	}
	public void setExpenseData(ExpenseData expenseData)
	{
		this.expenseData = expenseData;
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
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSkillSubType() {
		return skillSubType;
	}
	public void setSkillSubType(String skillSubType) {
		this.skillSubType = skillSubType;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public String getTestPhase() {
		return testPhase;
	}
	public void setTestPhase(String testPhase) {
		this.testPhase = testPhase;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getPOName() {
		return POName;
	}
	public void setPOName(String name) {
		POName = name;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public int getComponentid() {
		return componentid;
	}
	public void setComponentid(int componentid) {
		this.componentid = componentid;
	}
	public int getPoid() {
		return poid;
	}
	public void setPoid(int poid) {
		this.poid = poid;
	}

	
}
