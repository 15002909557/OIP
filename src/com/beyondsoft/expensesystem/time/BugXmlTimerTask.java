package com.beyondsoft.expensesystem.time;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.beyondsoft.expensesystem.action.system.AdministratorAction;
import com.beyondsoft.expensesystem.action.system.SysUserAction;
import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.system.Administrator;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.ConnectionManager;

public class BugXmlTimerTask extends TimerTask
{

private HttpServletRequest request;

public HttpServletRequest getRequest() 
{
	return request;
}

public void setRequest(HttpServletRequest request) 
{
	this.request = request;
}



@Override
public void run() {
	List<SysUser> sendlist = null;
	Administrator adm = null;
	Connection conn = null;
	try 
	{
		conn = ConnectionManager.getConn();
		boolean result = SysUserDao.getInstance().updateDisable(conn);
		System.out.println("IBS result="+result);
		sendlist = new SysUserAction().findAll(conn);
		adm = new AdministratorAction().getSysEmail(conn);
	} catch (Exception e) 
	{
		e.printStackTrace();
	}finally
	{
		if(conn!=null)
		{
			try 
			{
				conn.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
		SendMail.sendMailTo(sendlist,adm.getEmail(),adm.getPsw(),adm.getSender());
	}
}


