package com.beyondsoft.expensesystem.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AnnounceTool
{
	private static AnnounceTool announcetool = null;
	public static AnnounceTool getInstance()
	{
		if(announcetool == null)
			announcetool = new AnnounceTool();
		return announcetool;
	}
	/**
	 * 设置System Announcement
	 * 
	 * @param stmt
	 * @param announce
	 * @return
	 * @throws Exception
	 */
	public boolean setSysAnnounce(Statement stmt, String announce) throws Exception
	{
		//去除announcement中的制表符，换行符
		Pattern p = Pattern.compile("\\t|\r|\n");
		Matcher m = p.matcher(announce);
		announce = m.replaceAll("");
		
		String sql = "update systemsettings set systemAnnouncement='"+announce+"';";
		boolean result = false;
		try{
			int i = stmt.executeUpdate(sql);
			result = i>0;
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
		return result;
	}
	
	/**
	 * 查询System Announcement
	 * 
	 * @param stmt
	 * @param announce
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public String getSysAnnounce(Statement stmt) throws Exception 
	{
		String announce = "";
		String sql = "select systemAnnouncement from systemsettings";
		ResultSet rs = null;
		try{
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				announce = rs.getString("systemAnnouncement");
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (stmt != null)
			{
				stmt.close();
			}
		}
		return announce;
	}
	/**
	 * 修改Group的Announcement信息
	 * 
	 * @author longzhe
	 * @param stmt
	 * @param groupid
	 * @param announcement
	 * @return result
	 * @throws Exception
	 * @throws Exception
	 */
	public boolean setGroupAnnouncement(Connection conn, int gid,String announcement,int ui)
			throws Exception {
		boolean result = false;
		//去除announcement中的制表符，换行符
		Pattern p = Pattern.compile("\\t|\r|\n");
		Matcher m = p.matcher(announcement);
		announcement = m.replaceAll("");
		PreparedStatement pstmt = null;
		String sql = "update groups set announcement = ?,defaultUI=? where groupId = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, announcement);
			pstmt.setInt(2, ui);
			pstmt.setInt(3, gid);
			int re = pstmt.executeUpdate();
			result = re>0;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	public boolean setGroupUI(Connection conn, int gid, int ui)
			throws Exception {
		boolean result = false;
		PreparedStatement pstmt = null;
		
		String sql = "update groups set defaultUI = ? where groudId=?;";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ui);
			pstmt.setInt(2, gid);
			int re = pstmt.executeUpdate();
			result = re>0;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	/**
	 * 查询Group的Announcement信息
	 * 
	 * @author longzhe
	 * @param stmt
	 * @param groupid
	 * @param announcement
	 * @return result
	 * @throws Exception
	 * @throws Exception
	 * @update by dancy 2012-11-14
	 */
	public String getGroupAnnouncement(Statement stmt, int gid) throws Exception
	{
		String announce = "";
		String sql = "select announcement from groups where groupId="+gid;
		try{
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				announce = rs.getString("announcement");
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
		return announce;
	}
	
}
