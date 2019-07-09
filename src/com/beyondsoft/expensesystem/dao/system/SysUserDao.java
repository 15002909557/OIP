package com.beyondsoft.expensesystem.dao.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tools.bean.PageModel;
import tools.util.HQLQuery;


import com.beyondsoft.expensesystem.domain.system.PermissionLevel;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.BaseActionForm;
@SuppressWarnings("unchecked")
public class SysUserDao {
	private static SysUserDao dao = null;

	public static SysUserDao getInstance() {
		if (dao == null) {
			dao = new SysUserDao();
		}

		return dao;
	}
	
	/**
	 * ����û�������Ϣ
	 * @author longzhe
	 * @param stmt 
	 * @param userid
	 * @return list
	 * @throws Exception 
	 * @author longzhe
	 */
	public int changePWD(Statement stmt, String username, String oldpassword, String newpassword, String forWho) throws Exception
	{
		int result = -1;
		String password = "";
		String sql="select password from user_table where username='"+username+"';";
		//@Dancy 2011-11-11 �޸�����ʱ��¼��ǰ������changePSW��
		String sql2="update user_table set password='"+newpassword+"', changePSW = curdate(), account_disabled='f', announcement=null where username='"+username+"';";
		ResultSet rs = null;
		
		try{
			if("self".equalsIgnoreCase(forWho)) // �޸��Լ�������
			{
				rs = stmt.executeQuery(sql);
				if(rs.next())
				{
					password = rs.getString("password");
					if(oldpassword.equals(password))
					{
						int re = stmt.executeUpdate(sql2);
						if(re>0) //update successfully
						{
							result = 4;
						}
						else  //update failed
						{
							result = 3;
						}
							
					}
					else //old password incorrect
					{
						result=2;
					}
				}
				else  // user does not exist
				{
					result=1;
				}
			}
			else   //change passsword for others
			{
				int re = stmt.executeUpdate(sql2);
				if(re>0) //update successfully
				{
					result = 4;
				}
				else  //update failed
				{
					result = 3;
				}
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		
		return result;
	}

	/**
	 * ��ѯ�û��б���Ϣ
	 * 
	 * @param conn
	 * @param groupName
	 * @return List<SysUser>
	 * @throws Exception
	 * Author xiaofei / by collie 0418
	 */
	public List<SysUser> downloadUserList(Connection conn, String groupName)
			throws Exception {
		String sql = "select u.user_id,u.username from user_table u,`groups` g where"
					+" u.workloadgroupId=g.groupId and g.groupName='" +groupName+"'"
					+" order by u.username";
		if("All Data".equals(groupName))
		{
			sql = "select u.user_id,u.username from user_table u"
				+" order by u.username";
		}
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<SysUser> listUser = new ArrayList<SysUser>();	
				
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			while (rs.next()) {
				SysUser sysUser = new SysUser();
				sysUser.setUserId(rs.getInt("user_id"));
				sysUser.setUserName(rs.getString("username"));								
				listUser.add(sysUser);
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

		return listUser;
	}
	
	/**
	 * ͨ���û����ѯ�û���Ϣ
	 * @param userName
	 * @return
	 * @throws Exception
	 * @update by dancy  2012-11-14
	 * @flag
	 */
	public SysUser findByName(Connection conn, String userName)
			throws Exception {
		SysUser user = null;

		String sql = "select p.levelName,datediff(curdate(),u.changePSW) as 'remain', "
				+"g.groupName,g.lockday,g.approveday,g.approve,g.defaultUI,g.groupId,g.headorhour,g.announcement as 'gaa',"
				+" u.user_id,u.username,u.password,u.levelId,u.approveLevel,u.announcement as 'ann'"
				+" from `groups` g, user_table u, permissionlevels p"
				+ " where g.groupId=u.workloadgroupId and p.levelId=u.levelId and u.username = ?"
				+ " and u.leaveTime is null";
//				+" and u.account_disabled='f'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userName);
				rs = pstmt.executeQuery();
	
				while (rs.next()) 
				{
					user = new SysUser();
					user.setUserId(rs.getInt("user_id"));
					user.setUserName(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setGroupName(rs.getString("groupName"));
					user.setLockday(rs.getString("lockday"));
					user.setGroupID(rs.getInt("groupId"));				
					user.setApproved(rs.getInt("approve"));
					user.setApproveday(rs.getString("approveday"));
					user.setLevelID(rs.getInt("levelId"));
					user.setApproveLevel(rs.getInt("approveLevel"));
					user.setDefaultUI(rs.getInt("defaultUI"));
					user.setUI(rs.getInt("defaultUI"));
					user.setHeadorHour(rs.getInt("headorhour"));
					user.setLevel(rs.getString("levelName"));
					user.setGroupAnnounce(rs.getString("gaa"));
					user.setAnnouncement(rs.getString("ann"));
					user.setExpireDay(rs.getLong("remain"));
				}


		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) 
			{
				rs.close();
			}
			if (pstmt != null)
			{
				pstmt.close();
			}
		}

		return user;
	}

	/**
	 * ��ѯ�û���Ϣ
	 * 
	 * @param stmt
	 * @param userId
	 *            �û����
	 * @return
	 * @throws Exception
	 */
	public SysUser load(Statement stmt, int userId) throws Exception {
		SysUser user = null;
		String sql = "select p.levelName,u.massage,"
				+"g.groupName,g.defaultUI,u.user_id,u.username,u.password,u.levelId,u.email,u.HPEmployeeNumber,"
				+"u.leaveTime from groups g,user_table u,permissionLevels p"
				+ " where g.groupId=u.workloadgroupId and p.levelId=u.levelId and u.user_id = "
				+ userId;
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				user = new SysUser();
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setHPEmployeeNumber(rs.getString("HPEmployeeNumber"));
				user.setLeaveTime(rs.getDate("leaveTime"));
				user.setGroupName(rs.getString("groupName"));
				user.setLevelID(rs.getInt("levelId"));
				user.setLevel(rs.getString("levelName"));			
				user.setDefaultUI(rs.getInt("defaultUI"));
				if(rs.getString("massage")==null)
				{
					user.setMassage("");
				}
				else
				{
					user.setMassage(rs.getString("massage"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return user;
	}


	/**
	 * ��ѯ���û����Ƿ����
	 * 
	 * @param conn
	 * @param userName
	 *            �û���
	 * @return true:����,false:������
	 * @throws Exception
	 */
	public boolean isExist(Connection conn, String userName) throws Exception {
		boolean isExist = false;
		String sql = "select count(username) as count from user_table where username = '"
				+ userName + "'";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next() && rs.getInt("count") > 0) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		return isExist;
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param conn
	 * @param sysUser
	 * @param errors
	 * @throws Exception
	 */
	public void saveUser(Connection conn, SysUser sysUser, BaseActionForm form)
			throws Exception {
		String sql = "";
		PreparedStatement pstmt = null;
		try {
			if (sysUser.getUserId() == -1) {
				if (this.isExist(conn, sysUser.getUserName())) {
					form
							.setStrErrors("The username has been used, please enter another one!");
					return;
				}
				sql = "insert into user_table(username,password,levelId,account_disable) values(?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, sysUser.getUserName());
				pstmt.setString(2, sysUser.getPassword());
				pstmt.setInt(3, sysUser.getLevelID());
				if(0==sysUser.getRemove())
				pstmt.setString(4, "f");
				else
				pstmt.setString(4, "t");
				pstmt.executeUpdate();
			} else {
				sql = "update user_table set levelId = ? where user_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sysUser.getLevelID());
				pstmt.setInt(2, sysUser.getUserId());
				pstmt.executeUpdate();
				if (sysUser.getPassword().length() > 0) {
					sql = "update user_table set password = ? where user_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, sysUser.getPassword());
					pstmt.setInt(2, sysUser.getUserId());
					pstmt.executeUpdate();
				}
			}
			form.setStrErrors("");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	/**
	 * ɾ���û���Ϣ
	 * 
	 * @param stmt
	 * @param userId
	 *            �û�����б�
	 * @throws Exception
	 * @Dancy 2011-10-10 �޸��û���ְ����
	 * 
	 */
	public boolean deleteUL(Statement stmt, String leaveTime, String userId) throws Exception
	{
		boolean result = false;
		String sql = "update user_table set account_disabled = 't',leaveTime='"
					+leaveTime+"' where user_id=" + Integer.parseInt(userId);
		try
		{
			int re = stmt.executeUpdate(sql);
			result = re>0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	/**
	 * �ָ��û���Ϣ
	 * 
	 * @param stmt
	 * @param userId
	 *            �û�����б�
	 * @throws Exception
	 */
	public boolean enable(Statement stmt, String userId) throws Exception
	{
		boolean result = false;
		String sql = "update user_table set account_disabled = 'f' where user_id in(" + userId + ");";
		try
		{
			int re = stmt.executeUpdate(sql);
			result = re>0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return result;
	}


	/**
	 * ����ݿ��õ�PermissionLevel �б�
	 * 
	 * @param stmt
	 * @throws Exception
	 */
	
	public List searchPermissionLevel(Statement stmt) throws Exception {
		List<PermissionLevel> pmlist = new ArrayList<PermissionLevel>();
		String sql = "select levelId,levelName from permissionlevels";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				PermissionLevel pm = new PermissionLevel();
				pm.setLevelId(rs.getInt("levelId"));
				pm.setLevelName(rs.getString("levelName"));
				pmlist.add(pm);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return pmlist;
	}

	/**
	 * �޸��û���Ϣ
	 * 
	 * @param stmt
	 * @param user
	 * @throws Exception
	 */
	public boolean modifyUse(Connection conn, SysUser user, int GID)throws Exception {
		boolean result = false;
		String sql = null;
		PreparedStatement pstmt = null;
		try {
			sql = "update user_table set levelId=? , massage=?, email=?, HPEmployeeNumber=?, workloadgroupId=? where user_id= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user.getLevelID());
			pstmt.setString(2, user.getMassage());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getHPEmployeeNumber());			
			pstmt.setInt(5, user.getGroupID());
			pstmt.setInt(6, user.getUserId());
			int re = pstmt.executeUpdate();
			result = re > 0;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) 
			{
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸��û�approve��Ϣ
	 * 
	 * @param stmt
	 * @param userId
	 *            �û�����б�
	 * @throws Exception
	 */
	public boolean modifyApprove(Statement stmt, String userId, String approve) throws Exception
	{
		boolean result = false;
		String sql = "update user_table set approveLevel = '"+approve+"' where user_id = "+userId+";";
		try
		{
			int re = stmt.executeUpdate(sql);
			result = re>0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	/**
	 * �ϴ��û��б���Ϣ
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 * Author zhusimin
	 */
	public List<String> uploadUserList(Connection conn,List<SysUser> listUser)
			throws Exception {
		String sql = "update user_table set " +
				//"username=?," +
				"email=?," +
				//"levelId=?," +
				//"workloadgroupId=?," +
				"employDate=?," +
				"approveLevel=?, " +
				"workloadRate=?, " +
				"HPEmployeeNumber=?, " +
				"PTOrate=? " +
				"where user_id=?";
			
		PreparedStatement pstmt = null;	
		List<String> result = new ArrayList<String>();
		
		try
		{
			// System.out.println("downloadProjectList.sql="+sql);
			if (null != listUser)
			{
				int index = 0;
				int sumsuccess = 0;
				System.out.println("listUser.size():"+listUser.size());
				while (index < listUser.size())
				{
					pstmt = conn.prepareStatement(sql);
					//pstmt.setString(1, listUser.get(index).getUserName());
					// "projectName=''," +
					pstmt.setString(1, listUser.get(index).getEmail());
					// "skillLevel=''," +
					//pstmt.setInt(3, listUser.get(index).getLevelID());
					// "location=''," +
					//pstmt.setInt(2, listUser.get(index).getWorkloadgroupID());
					// "OTType=''," +
//					pstmt.setDate(2, listUser.get(index).getEmploydate());
					pstmt.setString(2, listUser.get(index).getEmployDate2());
					// "comments=''," +
					pstmt.setInt(3, listUser.get(index).getApproveLevel());
					// "confirm=0," +
					pstmt.setDouble(4, listUser.get(index).getWorkloadRate());
					pstmt.setString(5, listUser.get(index).getHPEmployeeNumber());
					pstmt.setInt(6, listUser.get(index).getPTOrate());
					//System.out.println(listUser.get(index).getPTOrate());
					pstmt.setInt(7, listUser.get(index).getUserId());
					// "remove=0," +
					
					if (pstmt.executeUpdate() > 0)
					{
						sumsuccess=sumsuccess*1+1;
					} else
					{
						result.add("--The user with id "
								+ listUser.get(index).getUserId()
								+ " is not updated successfully!");						
					}
					pstmt.close();

					index = index + 1;
				}
				result.add("Totally " + sumsuccess + " users are updated!");
			}

		} catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		} finally
		{
			if (pstmt != null)
			{
				pstmt.close();
			}
		}

		return result;
	}
	
	/**
	 * �����û��б���Ϣ
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 * Author zhusimin
	 */
	public List<SysUser> downloadUserList2(Connection conn, String groupName)
			throws Exception {
		String sql = "select" +
				" groups.groupname" +
				",approvelevel.discribe" +
				",u.* " +
				"from " +
				"user_table u left join approvelevel on u.approveLevel=approvelevel.value" +
				" left join groups on u.workloadgroupId=groups.groupid" +
				" where locate(?,groups.groupname)=1" +
				" and u.account_disabled='f'" +
				" order by length(groups.groupName),groups.groupName,approvelevel.discribe,u.username,u.email,u.employDate,u.levelId,u.user_id";
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<SysUser> listUser = new ArrayList<SysUser>();	
			
		try {
			//System.out.println("downloadProjectList.sql="+sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, groupName);
			rs = pstmt.executeQuery();
			System.out.println(sql+" "+groupName);
			
			while (rs.next()) {
				SysUser sysUser = new SysUser();
				sysUser.setLevelID(rs.getInt("levelId"));
				sysUser.setUserId(rs.getInt("user_id"));
				sysUser.setUserName(rs.getString("username"));
				sysUser.setPTOrate(rs.getInt("PTOrate"));
				sysUser.setEmploydate(rs.getDate("employDate"));
				sysUser.setWorkloadgroupID(rs.getInt("workloadgroupId"));
				sysUser.setGroupName(rs.getString("groupname"));
				sysUser.setApproveLevel(rs.getInt("approveLevel"));
				sysUser.setEmail(rs.getString("email"));
				sysUser.setWorkloadRate(rs.getDouble("workloadRate"));
				sysUser.setHPEmployeeNumber(rs.getString("HPEmployeeNumber"));
				listUser.add(sysUser);
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

		return listUser;
	}
	/**
	 * add new user
	 * 
	 * @param stmt
	 * @param userId
	 *            �û�����б�
	 * @throws Exception
	 */
	//@Dancy 1117 �޸�adduser(),���leave
//	public int addUser(Connection conn, SysUser usere ) throws Exception
	public int addUser(Connection conn, SysUser user ) throws Exception
	{
		//����ֵ��-1=ʧ�ܣ�0=�ɹ���1=�û�����ڡ� 
		boolean result = false;
		result = this.isExist(conn, user.getUserName());
		if(!result) //������ͬ�� ��������û�
		{

			String sql = "insert into user_table ( username , password , email , levelId , workloadgroupId , approveLevel , HPEmployeeNumber,changePSW) values "
							+" (?,?,?,?,?,?,?,curdate() )";

			PreparedStatement pstmt = null;
			try
			{
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, user.getUserName());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getEmail());
				pstmt.setInt(4, user.getLevelID());
				pstmt.setInt(5, user.getGroupID());
				pstmt.setInt(6, user.getApproveLevel());
				pstmt.setString(7, user.getHPEmployeeNumber());

				int re = pstmt.executeUpdate();
				result = re>0;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
			if(result)
				return 0;
			else
				return -1;
		}
		else
			return 1;
		
	}
	
	/**
	 * 
	 * @author hanxiaoyu01
	 * @param conn
	 * @return
	 * @throws Exception
	 * ���û��޸������ʱ�� 2012-12-11
	 */
	public List<SysUser> findAll(Connection conn) throws Exception {
		List<SysUser> list=new ArrayList<SysUser>();
		//String sql="select *,datediff(curdate(),changePSW) as 'remain' from user_table where datediff(curdate(),changePSW)>=75 and email!=''";
		String sql="select *,datediff(curdate(),changePSW) as 'remain' from user_table where datediff(curdate(),changePSW)>=75 and email!='' and leaveTime is null";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				SysUser user=new SysUser();
				user.setUserName(rs.getString("username"));
				user.setChangePSW(rs.getDate("changePSW"));
				user.setEmail(rs.getString("email"));
				user.setExpireDay(rs.getLong("remain"));
				list.add(user);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
//			if(pstmt!=null){
//				pstmt.close();
//			}
		}
		return list;
	}
	
	/**
	 * ��ѯ�û��б���Ϣ
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchUser(Connection conn, PageModel model) throws Exception {
		String sql = "select u.user_id,u.username,u.password,u.levelId, u.approveLevel , "
						+" case when u.account_disabled = 't' then ' Disabled ' else ' Enable ' end  as 'account_disabled' " 
						+" from user_table u "
						+" where u.user_id <> -1 order by u.username, u.user_id ";
		List list = null;
		HQLQuery _PageFind = null;
		try {
			_PageFind = new HQLQuery(conn, model, 16).createQuery(sql);
			list = _PageFind.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return list;
	}
	
	/**
	 * �жϵ�ǰ����All Data��Group���Ƿ��Ѵ���Data Checker�û�
	 * hanxiaoyu01 2013-01-28
	 */
	
	public boolean checkUser(Connection conn,int gid) throws Exception{
		boolean result=false;
		String sql="select * from user_table u where levelId=4 and workloadgroupId=? and account_disabled='f'";
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    try{
	    	pstmt=conn.prepareStatement(sql);
	    	pstmt.setInt(1, gid);
	    	rs=pstmt.executeQuery();
	    	if(rs.next()){
	    		result=true;
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

	/**
	 * hanxiaoyu 2013-01-30 ��������ڵ��û��������
	 * 
	 */
	public boolean updateDisable(Connection conn) throws Exception
	{
		String sql = "update user_table set account_disabled='t' where datediff(curdate(),changePSW)>90";
		PreparedStatement pstmt = null;
		boolean result = false;
		try{
			pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			result = i>0;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(pstmt!=null)
			{
				pstmt.close();
			}
		}
		return result;
	}
	
	public List<SysUser> findApprover(Connection conn) throws Exception {
		List<SysUser> list=new ArrayList<SysUser>();
		String sql="select * from user_table where datediff(curdate(),changePSW)<=90 and levelId=3 and leaveTime is null and account_disabled='f' and email!=''";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try{
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				SysUser user=new SysUser();
				user.setUserName(rs.getString("username"));
				user.setChangePSW(rs.getDate("changePSW"));
				user.setEmail(rs.getString("email"));
				list.add(user);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
		}
		return list;
	}
/**
 * 取得PO PM的email， 一个PO只有一个PM email吧？
 * @param conn
 * @param poid
 * @return
 * @throws Exception
 */
	public List<String> findSendlist( Connection conn,int poid) throws Exception {
		List<String> list=new ArrayList<String>();
		System.out.println("enter here to get send list!");
		//每个po只有一个email，如果是输入多个的用分号隔开 by dancy
		String sql="select email from po where poid="+poid;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String mail = "";
		String[] str = {""};;
		try{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				if(rs.getString("email")!=null)
				{
					mail = rs.getString("email");
				}

			}
			System.out.println("po email here in dao is:"+mail);
			if(!mail.equals(""))
			{
				str = mail.split(";");
				//将mail按照分号分隔然后存放到send list中 by dancy
				for(int i=0;i<str.length;i++)
				{
					System.out.println("emails:"+str[i]);
					list.add(str[i]);
				}
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
//			if(pstmt!=null)
//			{
//				pstmt.close();
//			}
		}
		return list;
	}
}