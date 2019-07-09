package com.beyondsoft.expensesystem.form.checker;

import java.util.Date;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.beyondsoft.expensesystem.domain.checker.ExpenseData;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.util.BaseActionForm;
import com.beyondsoft.expensesystem.domain.checker.LeaveDetails;
@SuppressWarnings("unchecked")
public class DataCheckerForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;

	private String today = null;
	private String comments = "";
	private int expenseDataId = -1;
	private int projectId = -1;
	private int userId = -1;
	private Date createTime = null;
	private String componentName = null;
	private String skillLevel = null;
	private String location = null;
	private String OTType = null;
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
	private List skillLevels = null;
	private List locations = null;
	private List OTTypes = null;
	private List leaveTypes = null;
	private List groups = null;
	private List projectNames = null;
	private List productNames = null;
	private List wbsNames = null;
	public List getWbsNames() {
		return wbsNames;
	}


	public void setWbsNames(List wbsNames) {
		this.wbsNames = wbsNames;
	}


	private Project project = new Project();
	private ExpenseData expenseData = new ExpenseData();
	private int groupId = -1;
	
	
	
	
	//Dancy 2011-10-20
	private LeaveDetails leavedetails=new LeaveDetails();
	
	private FormFile files = null; // by collie 0427
	
	//new project区分sw fw
	private List componentNames_fw = null;
	private List PO_fw = null;
	private List productNames_fw = null;
	private List skillLevels_fw = null;
	private List locations_fw = null;
	private List OTTypes_fw = null;
//	private List description = null;
	
	private List productNames_sw = null;
	private List projectNames_sw = null;
	private List skillLevels_sw = null;
	private List locations_sw = null;
	private List OTTypes_sw = null;
	
	//hanxiaoyu01 2012-12-13为修改TotalSummary而设
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	private String t6;
	private String t7;
	
	public List getProductNames_fw() {
		return productNames_fw;
	}

	
	public void setProductNames_fw(List productNames_fw) {
		this.productNames_fw = productNames_fw;
	}

	public List getSkillLevels_fw() {
		return skillLevels_fw;
	}

	public void setSkillLevels_fw(List skillLevels_fw) {
		this.skillLevels_fw = skillLevels_fw;
	}

	public List getLocations_fw() {
		return locations_fw;
	}

	public void setLocations_fw(List locations_fw) {
		this.locations_fw = locations_fw;
	}

	public List getOTTypes_fw() {
		return OTTypes_fw;
	}

	public void setOTTypes_fw(List types_fw) {
		OTTypes_fw = types_fw;
	}

	public List getProductNames_sw() {
		return productNames_sw;
	}

	public void setProductNames_sw(List productNames_sw) {
		this.productNames_sw = productNames_sw;
	}

	public List getProjectNames_sw() {
		return projectNames_sw;
	}

	public void setProjectNames_sw(List projectNames_sw) {
		this.projectNames_sw = projectNames_sw;
	}

	public List getSkillLevels_sw() {
		return skillLevels_sw;
	}

	public void setSkillLevels_sw(List skillLevels_sw) {
		this.skillLevels_sw = skillLevels_sw;
	}

	public List getLocations_sw() {
		return locations_sw;
	}

	public void setLocations_sw(List locations_sw) {
		this.locations_sw = locations_sw;
	}

	public List getOTTypes_sw() {
		return OTTypes_sw;
	}

	public void setOTTypes_sw(List types_sw) {
		OTTypes_sw = types_sw;
	}

	public List getGroups() {
		return groups;
	}

	public void setGroups(List groups) {
		this.groups = groups;
		System.out.println(groups.size());
	}
	
	public List getLeaveTypes()
	{
		return leaveTypes;
	}

	public void setLeaveTypes(List leaveTypes)
	{
		this.leaveTypes = leaveTypes;
	}
	
	public List getSkillLevels()
	{
		return skillLevels;
	}

	public void setSkillLevels(List skillLevels)
	{
		this.skillLevels = skillLevels;
	}

	public List getLocations()
	{
		return locations;
	}

	public void setLocations(List locations)
	{
		this.locations = locations;
	}

	public List getOTTypes()
	{
		return OTTypes;
	}

	public void setOTTypes(List oTTypes)
	{
		OTTypes = oTTypes;
	}

	public String getHours()
	{
		return hours;
	}

	public void setHours(String hours)
	{
		this.hours = hours;
	}

	public ExpenseData getExpenseData()
	{
		return expenseData;
	}

	public void setExpenseData(ExpenseData expenseData)
	{
		this.expenseData = expenseData;
	}

	public int getExpenseDataId()
	{
		return expenseDataId;
	}

	public void setExpenseDataId(int expenseDataId)
	{
		this.expenseDataId = expenseDataId;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project)
	{
		this.project = project;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOTType() {
		return OTType;
	}

	public void setOTType(String oTType) {
		OTType = oTType;
	}

	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}

	public int getRemove() {
		return remove;
	}

	public void setRemove(int remove) {
		this.remove = remove;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(List projectNames) {
		this.projectNames = projectNames;
	}

	public List getProductNames() {
		return productNames;
	}

	public void setProductNames(List productNames) {
		this.productNames = productNames;
	}

	public FormFile getFiles() {
		return files;
	}

	public void setFiles(FormFile files) {
		this.files = files;
	}

//	public List getDescription() {
//		return description;
//	}
//
//	public void setDescription(List description) {
//		this.description = description;
//	}
	
	
	//Dancy 2011-10-20

	public LeaveDetails getLeaveDetails()
	{
		return leavedetails;
	}

	public void setLeaveDetails(LeaveDetails leavedetails)
	{
		this.leavedetails = leavedetails;
	}

	public List getComponentNames_fw() {
		return componentNames_fw;
	}

	public void setComponentNames_fw(List componentNames_fw) {
		this.componentNames_fw = componentNames_fw;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public List getPO_fw() {
		return PO_fw;
	}

	public void setPO_fw(List po_fw) {
		PO_fw = po_fw;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	//hanxiaoyu01 2012-12-13
	public String getT1() {
		return t1;
	}


	public void setT1(String t1) {
		this.t1 = t1;
	}


	public String getT2() {
		return t2;
	}


	public void setT2(String t2) {
		this.t2 = t2;
	}


	public String getT3() {
		return t3;
	}


	public void setT3(String t3) {
		this.t3 = t3;
	}


	public String getT4() {
		return t4;
	}


	public void setT4(String t4) {
		this.t4 = t4;
	}


	public String getT5() {
		return t5;
	}


	public void setT5(String t5) {
		this.t5 = t5;
	}


	public String getT6() {
		return t6;
	}


	public void setT6(String t6) {
		this.t6 = t6;
	}


	public String getT7() {
		return t7;
	}


	public void setT7(String t7) {
		this.t7 = t7;
	}


	
}

