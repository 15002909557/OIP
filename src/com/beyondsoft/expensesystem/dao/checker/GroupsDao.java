package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.system.SysUser;

/**
 * GroupsDao
 * 
 * @author longzhe
 */
@SuppressWarnings("unchecked")
public class GroupsDao { 
	private static GroupsDao dao = null;

	public static GroupsDao getInstance() {
		if (dao == null) {
			dao = new GroupsDao();
		}

		return dao;
	}
	
	/**
	 * ��ѯGroup�б���Ϣ
	 * 
	 * @param conn
	 * @param groupName
	 * @return List<SysUser>
	 * @throws Exception
	 * Author xiaofei / by collie 0418
	 */
	public List<Groups> downloadGroupList(Connection conn, String groupName)
			throws Exception {
		String sql = "select groupId,groupName from groups order by groups.groupname";
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<Groups> listGroup = new ArrayList<Groups>();	
				
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Groups group = new Groups();
				group.setGid(rs.getInt("groupId"));
				group.setGname(rs.getString("groupName"));
				listGroup.add(group);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}

		return listGroup;
	}
	
	/**
	 * ��ѯGroups��Ϣ
	 * 
	 * @author longzhe
	 * @param stmt
	 * @param userid
	 * @param loaduser
	 *            �Ƿ������û�
	 * @return glist
	 * @throws Exception
	 * @throws Exception
	 * @update by dancy 2012-11-14
	 * @flag
	 */

	public List load(Statement stmt, int gid) throws Exception {
		String sql = "";
		String flag = "";
		if(gid!=-1)
		{
			flag = " where groupId="+gid;
		}
		Groups group = null;
		List<Groups> glist = new ArrayList<Groups>();
		sql = "select * from groups"+flag+" order by groups.groupName ASC ";
		ResultSet rs = null;
		try {

			rs = stmt.executeQuery(sql);
			while (rs.next()) 
			{
				group = new Groups();
				group.setGid(rs.getInt("groupId"));
				group.setGname(rs.getString("groupName"));
				group.setApprove(rs.getInt("approve"));
				group.setApproveday(rs.getString("approveday"));
				group.setLockday(rs.getString("lockday"));
				group.setDefaultUI(rs.getInt("defaultUI"));
				if(null!=rs.getString("announcement"))
					group.setAnnouncement(rs.getString("announcement").trim());
				glist.add(group);


			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return glist;
	}
	/**
	 * ���ҵ�ǰGroup�µ�SysUser��Ϣ
	 * 
	 * @param stmt
	 * @param groupid
	 * @return list
	 * @throws Exception 
	 * @author longzhe
	 * @update by dancy 2012-11-15
	 */
	
	public List getUserbyGid(Statement stmt,int gid) throws Exception
	{
		List<SysUser> Userlist = new ArrayList<SysUser>(); //User List
		SysUser sysu = null;
		ResultSet rs = null;
		String sql = "select u.user_id,u.username,p.levelName,u.HPEmployeeNumber,u.massage,a.discribe,"
				+"u.leaveTime,u.email from user_table u,permissionlevels p,approvelevel a "
				+"where p.levelId=u.levelId and a.value=u.approveLevel and u.leaveTime is null and u.workloadgroupId="+gid;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				sysu = new SysUser();
				sysu.setUserId(rs.getInt("user_id"));
				sysu.setUserName(rs.getString("username"));
				sysu.setLevel(rs.getString("levelName"));
				sysu.setEmail(rs.getString("email"));
				sysu.setHPEmployeeNumber(rs.getString("HPEmployeeNumber"));
				sysu.setLeaveTime(rs.getDate("leaveTime"));
				sysu.setMassage(rs.getString("massage"));
				sysu.setApproveLevelName(rs.getString("discribe"));
				Userlist.add(sysu);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (rs != null) 
			{
				rs.close();
			}
		}
		return Userlist;
	}
	
	/**
	 * @author hanxiaoyu01
	 *  ͨ����id���� 2013-02-19
	 * @param stmt
	 * @param gid
	 * @return
	 * @throws Exception
	 */
	public List getUserbyGid(Statement stmt,String gid) throws Exception
	{
		List<SysUser> Userlist = new ArrayList<SysUser>(); //User List
		SysUser sysu = null;
		ResultSet rs = null;
//		String sql = "select u.user_id,u.username,p.levelName,u.HPEmployeeNumber,u.massage,a.discribe,"
//				+"u.leaveTime,u.email from user_table u,permissionlevels p,approvelevel a "
//				+"where p.levelId=u.levelId and a.value=u.approveLevel and u.leaveTime is null and u.workloadgroupId in ("+gid+")";
		String sql = "select u.user_id,u.username,p.levelName,u.HPEmployeeNumber,u.massage,a.discribe,"
			+"u.leaveTime,u.email from user_table u,permissionlevels p,approvelevel a "
			+"where p.levelId=u.levelId and a.value=u.approveLevel and u.leaveTime is null and u.workloadgroupId="+gid;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				sysu = new SysUser();
				sysu.setUserId(rs.getInt("user_id"));
				sysu.setUserName(rs.getString("username"));
				sysu.setLevel(rs.getString("levelName"));
				sysu.setEmail(rs.getString("email"));
				sysu.setHPEmployeeNumber(rs.getString("HPEmployeeNumber"));
				sysu.setLeaveTime(rs.getDate("leaveTime"));
				sysu.setMassage(rs.getString("massage"));
				sysu.setApproveLevelName(rs.getString("discribe"));
				Userlist.add(sysu);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (rs != null) 
			{
				rs.close();
			}
		}
		return Userlist;
	}
	
	/**
	 * ����Group�µ�����SysUser��Ϣ
	 * 
	 * @param stmt
	 * @param groupid
	 * @return list
	 * @throws Exception 
	 * @author longzhe
	 * @flag
	 */
	public List getUsersbyGid(Statement stmt,int gid) throws Exception
	{
		List<SysUser> Userlist = new ArrayList<SysUser>(); //User List
		SysUser sysu;
		String flag = "";
		if(gid>0)
		{
			flag = "where groupId="+gid;
		}
			
		String sql = "select u.user_id,u.username,permissionLevels.levelName,u.levelId,u.email,u.HPEmployeeNumber,u.leaveTime" +
				", u.massage " 
						+"from user_table u,permissionLevels " 
						+" where (u.account_disabled='f' or u.leaveTime is null) and permissionLevels.levelId=u.levelId and u.workloadgroupId in "
						+" ( select groups.groupId from groups "+flag+")" 
						+" order by u.username";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				sysu = new SysUser();
				sysu.setUserId(rs.getInt("user_id"));
				sysu.setUserName(rs.getString("username"));
				sysu.setLevel(rs.getString("levelName"));
				sysu.setLevelID(rs.getInt("levelId"));
				sysu.setEmail(rs.getString("email"));
				sysu.setHPEmployeeNumber(rs.getString("HPEmployeeNumber"));
				sysu.setLeaveTime(rs.getDate("leaveTime"));
				sysu.setMassage(rs.getString("massage"));
				Userlist.add(sysu);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return Userlist;
	}
	
	/**
	 * ���ָ��Group�¿�������ɵ�Group
	 * 
	 * @param stmt
	 * @param groupName
	 * @return String
	 * @throws Exception 
	 * @author xiaofei
	 */
	public String getNewSubGroup(Statement stmt,String groupName) throws Exception
	{
		String newSubGroup = "";
		int tempGroupIndex=0;
		String sql = "SELECT groups.groupName,groups.groupId from groups where locate('"+groupName+"',groups.groupName)=1 and groupName<>'"+groupName+"'";
		try{
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				newSubGroup=rs.getString("groupName");
				
				if (tempGroupIndex<Integer.parseInt(newSubGroup.split("\\.")[(groupName.split("\\.")).length]))
					tempGroupIndex=Integer.parseInt(newSubGroup.split("\\.")[(groupName.split("\\.")).length]);
			}
			rs.last();
			if (rs.getRow()==0)
				tempGroupIndex=1;
			else
				tempGroupIndex=tempGroupIndex+1;
			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		newSubGroup=groupName+Integer.toString(tempGroupIndex)+".";
		return newSubGroup;
	}
	public int createGroup(Connection conn,String groupName,String comments, int ui,String lockday, String approveday)throws Exception 
	{
		boolean result = false;
		int gid = -1;
		//ͬ���ж�
		String sql0 = "select groupName from groups where groupName='"+groupName+"'";
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement pstmt = null;
		String sql = "insert into groups(groupName,comments,defaultUI,lockday,approveday) values(?,?,?,?,?)";
		//ȡ������Ӽ�¼�е�ID
		String sql2 = "select groupId from groups where groupName='"+groupName+"'";

		try{
			Statement stmt = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql0);
			if(!rs.next())
			{				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, groupName);
				pstmt.setString(2, comments);
				pstmt.setInt(3, ui);
				pstmt.setString(4, lockday);
				pstmt.setString(5, approveday);
				int i = pstmt.executeUpdate();
				result = i>0;
			}			
			if(result)
			{
				rs2 = stmt.executeQuery(sql2);
				while(rs2.next())
				{
					gid = rs2.getInt("groupId");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return gid;
	}
	/**
	 * �����û�����group��Ĭ��uiֵ��Ӧ��ݱ�groups��inputUITemplateId�ֶ�
	 * @param groupname
	 * @param ui
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 * @flag
	 */
	public void saveGroupUI(String groupname, int ui, Statement stmt) throws Exception
	{
		String sql = "update groups set defaultUI="+ui+" where groupName='"+groupname+"'";
		try{
			stmt.executeUpdate(sql);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(null != stmt)
				stmt.close();
		}
	}
	/**
	 * �����û�����group��Ĭ��HeadorHourֵ��Ӧ��ݱ�groups��headorhour�ֶ�
	 * @param groupname
	 * @param ui
	 * @throws Exception
	 * 
	 * @Author=Longzhe
	 * @flag
	 */
	public void saveGroupHeadorHour(String groupname, int headorhour, Statement stmt) throws Exception
	{
		String sql = "update groups set headorhour=" + headorhour + " where groupname='" + groupname +"'";
		try{
			stmt.executeUpdate(sql);
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(null != stmt)
				stmt.close();
		}
	}
	
	
	public List<Groups> searchLockDayByGroupName(Statement stmt,
			int gid) throws Exception {
		List<Groups> glist = new ArrayList<Groups>();
		String flag = "";
		if(gid>0)
		{
			flag = " where groupId="+gid;
		}
		String sql="select groupId,lockday from groups"+flag;
		ResultSet rs = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				Groups group = new Groups();
				group.setGid(rs.getInt("groupId"));
				group.setLockday(rs.getString("lockday"));
				glist.add(group);			
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		return glist;
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-24
	 * ��gid����group
	 */
	public Groups findGroupByGroupId2(Connection conn,int gid)throws Exception{
		String sql="select * from groups where groupId=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Groups group=new Groups();
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, gid);
			rs=pstmt.executeQuery();
			if(rs.next()){
				group.setGid(rs.getInt("groupId"));
				group.setGname(rs.getString("groupName"));
				group.setComments(rs.getString("comments"));
				group.setLockday(rs.getString("lockday"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
		return group;
	}
	
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-24
	 * ��gid����group
	 */
	public String findGroupByGroupId(Connection conn,int gid)throws Exception{
		String sql="select lockday from groups where groupId=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String highLockday = "";
		
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, gid);
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				highLockday = rs.getString("lockday");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
		return highLockday;
	}
	/**
	 * @author hanxiaoyu01
	 * 2012-12-24
	 * ɾ��group
	 */
	public void deleteGroup(Connection conn,int gid) throws Exception{
		String sql="delete from groups where groupId=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, gid);
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
	 *@author hanxiaoyu01
	 *2012-12-25
	 *�޸�group 
	 *
	 */
	public void updateGroup(Connection  conn,Groups group)throws Exception{
		String sql="update groups set groupName=?,comments=? where groupId=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, group.getGname());
			pstmt.setString(2, group.getComments());
			pstmt.setInt(3, group.getGid());
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
	 * @author hanxiaoyu01
	 * 2012-12-25
	 * group name�����ж�
	 */
	public int checkGroupName(Connection conn,String gname,int gid)throws Exception{
		int result=0;
		String sql="select * from groups where groupName=? and groupId!=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,gname);
			pstmt.setInt(2,gid);
			rs=pstmt.executeQuery();
			while(rs.next()){
				result++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
		return result;
	}
	
	public boolean checkNewAddGroup(Connection conn,String gname) throws Exception{
		String sql = "select * from groups where groupName = ?";
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		boolean result = false;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, gname.trim());
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				result=true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
}
