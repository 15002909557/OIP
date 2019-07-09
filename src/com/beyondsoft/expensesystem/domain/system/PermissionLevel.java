package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

public class PermissionLevel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int levelId = -1;
	private String levelName = "";
	private int remove = 0;
	
	public int getLevelId()
	{
		return levelId;
	}
	public void setLevelId(int levelId)
	{
		this.levelId = levelId;
	}
	public String getLevelName()
	{
		return levelName;
	}
	public void setLevelName(String levelName)
	{
		this.levelName = levelName;
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
