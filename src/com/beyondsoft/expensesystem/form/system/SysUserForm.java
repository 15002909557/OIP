package com.beyondsoft.expensesystem.form.system;

import java.util.List;

import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.BaseActionForm;
@SuppressWarnings("unchecked")
public class SysUserForm extends BaseActionForm
{
	private static final long serialVersionUID = 1L;
	
	private SysUser sysUser = new SysUser();
	private List permissionLevel = null;
	private String	pwd	= "";
	
	public SysUser getSysUser()
	{
		return sysUser;
	}
	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}
	public String getPwd()
	{
		return pwd;
	}
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}
	public List getPermissionLevel()
	{
		return permissionLevel;
	}
	public void setPermissionLevel(List permissionLevel)
	{
		this.permissionLevel = permissionLevel;
	}
}
