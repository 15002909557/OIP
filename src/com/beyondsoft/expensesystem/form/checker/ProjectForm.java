package com.beyondsoft.expensesystem.form.checker;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.util.BaseActionForm;

public class ProjectForm extends BaseActionForm
{
	private static final long serialVersionUID = 1L;

	private Project project = new Project();

	private int projectId = -1;

	private int userId = -1;

	private Date createTime = null;

	private String projectName = null;

	private String skillLevel = null;

	private String location = null;

	private String OTType = null;

	private String comments = null;

	private int confirm = 0;

	private int remove = 0;

	private String startdate = null;

	private String enddate = null;
	
	private FormFile files = null; // by collie 0427
	
	public FormFile getFiles() {
		return files;
	}

	public void setFiles(FormFile files) {
		this.files = files;
	}

	public String getStartdate()
	{
		return startdate;
	}

	public void setStartdate(String startdate)
	{
		this.startdate = startdate;
	}

	public String getEnddate()
	{
		return enddate;
	}

	public void setEnddate(String enddate)
	{
		this.enddate = enddate;
	}

	public Project getProject()
	{
		return project;
	}

	public void setProject(Project project)
	{
		this.project = project;
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
}
