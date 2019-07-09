package com.beyondsoft.expensesystem.form.system;

import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.BaseActionForm;

public class LoginForm extends BaseActionForm
{
	private static final long serialVersionUID = 1L;
	private SysUser	sysUser	= new SysUser();

	public SysUser getSysUser()
	{
		return sysUser;
	}

	public void setSysUser(SysUser sysUser)
	{
		this.sysUser = sysUser;
	}
}
