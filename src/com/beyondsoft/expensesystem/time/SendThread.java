package com.beyondsoft.expensesystem.time;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.beyondsoft.expensesystem.dao.checker.ProjectDao;
import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.system.Administrator;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.ConnectionManager;
import com.beyondsoft.expensesystem.util.EmailTool;

public class SendThread extends Thread{
	private int pid;
	public SendThread(int pid){
		this.pid=pid;
	}
	public void run(){
		Connection conn=null;
		List<SysUser> sendlist=null;
		Administrator adm=null;
		Project project=null;
		try{
			conn=ConnectionManager.getConn();
			sendlist=SysUserDao.getInstance().findApprover(conn);
			
			adm=EmailTool.getInstance().getEmail(conn);
			project=ProjectDao.getInstance().findProjectById(conn,pid);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		SendMail.sendMailTo2(sendlist,adm.getEmail(),adm.getPsw(),adm.getSender(),project);
	}
}
