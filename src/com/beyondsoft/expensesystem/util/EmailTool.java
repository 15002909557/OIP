package com.beyondsoft.expensesystem.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.beyondsoft.expensesystem.domain.system.Administrator;

public class EmailTool {
	private static EmailTool emailtool = null;
	public static EmailTool getInstance()
	{
		if(emailtool == null)
			emailtool = new EmailTool();
		return emailtool;
	}
	
	/**
	 * ����ϵͳemail
	 */
	public void setEmail(Connection conn,String email,String sender,String psw)throws Exception{
		String sql="update systemsettings set email=?,sender=?,psw=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, sender);
			pstmt.setString(3, psw);
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
	}
	
	/**
	 * ��ѯϵͳemail
	 */
	public Administrator getEmail(Connection conn)throws Exception
	{
		String sql = "select email,sender,psw from systemsettings";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Administrator adm = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				adm = new Administrator();
//				System.out.println("email="+rs.getString("email"));
				adm.setEmail(rs.getString("email"));
				adm.setSender(rs.getString("sender"));
				adm.setPsw(rs.getString("psw"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(rs!=null)
			{
				rs.close();
			}
		}
		return adm;
	}
}
