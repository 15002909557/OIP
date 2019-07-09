package com.beyondsoft.expensesystem.domain.system;

import java.io.Serializable;

public class PermissionName implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int permissionId = -1;
	private String permissionName = "";
	private int remove = 0;
	
	public int getPermissionId()
	{
		return permissionId;
	}
	public void setPermissionId(int permissionId)
	{
		this.permissionId = permissionId;
	}
	public String getPermissionName()
	{
		return permissionName;
	}
	public void setPermissionName(String permissionName)
	{
		this.permissionName = permissionName;
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
