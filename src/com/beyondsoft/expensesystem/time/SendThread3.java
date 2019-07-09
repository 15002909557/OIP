package com.beyondsoft.expensesystem.time;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.beyondsoft.expensesystem.dao.checker.PODao;
import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.checker.ProjectOrder;
import com.beyondsoft.expensesystem.domain.system.Administrator;
import com.beyondsoft.expensesystem.util.ConnectionManager;
import com.beyondsoft.expensesystem.util.EmailTool;

public class SendThread3 extends Thread{
	private int poid;
	public SendThread3(int poid)
	{
		this.poid=poid;
	}
	public void run()
	{
		Connection conn = null;
		boolean result_sendmail = false;
		List<String> sendlist = new ArrayList<String>();
		Administrator adm = null;
		ProjectOrder p = null;
		try
		{
			conn=ConnectionManager.getConn();
			//改成String list by dancy
			sendlist = SysUserDao.getInstance().findSendlist(conn,poid);
			System.out.println("sendlist="+sendlist);
			
			adm = EmailTool.getInstance().getEmail(conn);
			
			p = PODao.getInstance().searchPOBalance(conn,poid);
			
			result_sendmail = PODao.getInstance().searchbalance(conn, poid);
			System.out.println("result_sendmail in the sendmail function = "+result_sendmail);
			
			if(result_sendmail&&sendlist!=null)//收件人列表不能为空  by dancy
			{
				SendMail.sendMailTo3(sendlist,adm.getEmail(),adm.getPsw(),adm.getSender(),p);
			}
		}catch(Exception e)
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
	}
}
