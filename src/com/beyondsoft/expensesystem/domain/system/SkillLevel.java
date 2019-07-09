package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

public class SkillLevel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int skillLevelId = -1;
	private String skillLevelName = "";
	private String shortname = "";
	
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public int getSkillLevelId()
	{
		return skillLevelId;
	}
	public void setSkillLevelId(int skillLevelId)
	{
		this.skillLevelId = skillLevelId;
	}
	public String getSkillLevelName()
	{
		return skillLevelName;
	}
	public void setSkillLevelName(String skillLevelName)
	{
		this.skillLevelName = skillLevelName;
	}
}
