package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.beyondsoft.expensesystem.domain.checker.LeaveData;
import com.beyondsoft.expensesystem.domain.checker.LeaveDetails;
import com.beyondsoft.expensesystem.domain.checker.ReportData_PayChex;

/**
 * LeaveDataDao
 * @author longzhe
 */
@SuppressWarnings("unchecked")
public class LeaveDataDao
{
	private static LeaveDataDao dao = null;
	
	public static LeaveDataDao getInstance()
	{
		if (dao == null)
		{
			dao = new LeaveDataDao();
		}

		return dao;
	}
	
	/**
	 * 获得Leave Types信息
	 * @author longzhe
	 * @param stmt 
	 * @param userid
	 * @return list
	 * @throws Exception 
	 * @author longzhe
	 */
	public List loadLeaveTypes(Statement stmt,Integer userId) throws Exception
	{
		List<Map> LeaveTypes = new ArrayList<Map>();
		Map Leave = null;
		String sql="";
		if (userId==0){
			sql = "select * from leavetypes;";
		}else{
			sql = "select leavetypes.* from leavetable" +
			" left join leavetypes on leavetable.leavetype=leavetypes.leavetypeid" +
			" where leavetable.owner=" +
			userId.toString() +
			";";
		}
		
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				Leave = new HashMap();
				Leave.put("id", rs.getInt("leaveTypeId"));
				Leave.put("name", rs.getString("leaveTypeName"));
				Leave.put("exceedHours",rs.getInt("exceedHours"));
				LeaveTypes.add(Leave);
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
		
		return LeaveTypes;
	}
	
	/**
	 * 查询Expense Data Details信息
	 * @author longzhe
	 * @param stmt 
	 * @param userid
	 * @return list
	 * @throws Exception 
	 * @throws Exception
	 */
	public List loadLeaveDataDetailsByUserID(Statement stmt,String userid) throws Exception
	{
		List<LeaveDetails> list = new ArrayList<LeaveDetails>();
		String sql = "select * from leavedetails where leaveId in ( select id from leavetable where owner = '"+userid+"' );";
		
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				LeaveDetails leavedetails = new LeaveDetails();
				leavedetails.setId(rs.getInt("id"));
				leavedetails.setLeaveDataId(rs.getInt("leaveId"));
				leavedetails.setDate(rs.getString("leavedate"));
				leavedetails.setHours(rs.getFloat("hours"));
				leavedetails.setPTOsubtype(rs.getString("PTOtype"));
				list.add(leavedetails);
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
		return list;
	}
	
	/**
	 * 通过 groupid 查找 当前group和当前groups一下的所有groups的
	 * Leave Data Details 信息
	 * @author longzhe
	 * @param stmt 
	 * @param userid
	 * @return list
	 * @throws Exception 
	 * @throws Exception
	 */
	public List searchLeaveDataDetailsByGroupName(Statement stmt,String groupname, String selectdate) throws Exception
	{
		List<LeaveDetails> LeaveDetailslist = new ArrayList<LeaveDetails>();
		String sql = "select leavedetails.id as 'detailsid', leavetypes.leaveTypeName, leavedetails.leavedate, leavedetails.hours , leavetable.owner , user_table.username,  leavedetails.PTOtype "
						+"from leavetable left join leavetypes on leavetable.leavetype=leavetypes.leaveTypeId, leavedetails , user_table " 
						+"where leavetable.id=leavedetails.leaveId " 
						+"and leavetable.owner=user_table.user_id "
						+"and leavetable.owner in " 
						+"( " 
						+"select user_id from user_table where workloadgroupId in ( select groupId from groups where locate(  '"+groupname+"' , groups.groupName ) =1 ) "
						+") "
						+"and leavedate='"+selectdate+"';" ;
		
		ResultSet rs = null;
		try
		{
			System.out.println("groupname="+groupname);
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				LeaveDetails leavedetails = new LeaveDetails();
				
				leavedetails.setId(rs.getInt("detailsid"));
				leavedetails.setLeavetypename(rs.getString("leaveTypeName"));
				leavedetails.setDate(rs.getString("leavedate"));
				leavedetails.setHours(rs.getFloat("hours"));
				leavedetails.setOwner(rs.getInt("owner"));
				leavedetails.setUsername(rs.getString("username"));
				leavedetails.setPTOsubtype(rs.getString("PTOtype"));
				
				LeaveDetailslist.add(leavedetails);
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
		return LeaveDetailslist;
	}
	
	/**
	 * 通过 groupid 查找 当前group和当前groups一下的所有groups的
	 * Leave Data Details 信息
	 * @author longzhe
	 * @param stmt 
	 * @param userid
	 * @return list
	 * @throws Exception 
	 * @throws Exception
	 */
	public List searchGroupLeavedataListByGroupName(Statement stmt,String groupname) throws Exception
	{
		List<LeaveData> groupleavedataList = new ArrayList<LeaveData>();
		String sql = "select leavetable.* ,  leavetypes.leaveTypeName , user_table.username  from  leavetable ,  leavetypes , user_table where user_table.account_disabled='f' and leavetypes.leaveTypeId=leavetable.leavetype and leavetable.owner=user_table.user_id "
						+ " and leavetable.owner in " 
						+ " ( " 
						+ " select user_id from user_table where workloadgroupId in ( select groupId from groups where locate(  '"+groupname+"' , groups.groupName ) =1 )" 
						+ " ) order by user_table.username ;" ;
		//System.out.println(sql);
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				LeaveData leavedata = new LeaveData();
				
				leavedata.setId(rs.getInt("id"));
				leavedata.setLeaveType(rs.getInt("leavetype"));
				leavedata.setOwner(rs.getInt("owner"));
				leavedata.setOwnerName(rs.getString("username"));
				
				leavedata.setDefaultHours(rs.getFloat("defaultHours"));
				leavedata.setIncrementHours(rs.getFloat("Increment"));
				leavedata.setDefaultUsed(rs.getFloat("defaultUsedHours"));
				leavedata.setUsedHours(rs.getFloat("UsedHours"));
				
				leavedata.setLeaveTypeName(rs.getString("leaveTypeName"));
				leavedata.initPTOTotalHoursandAvailableHours();
				
				groupleavedataList.add(leavedata);
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
		return groupleavedataList;
	}
	
	/**
	 * 保存假期信息
	 * 
	 * @param conn
	 * @param project
	 * @param errors
	 * @throws Exception 
	 * @throws Exception
	 */
	public boolean saveLeaveData(Connection conn, LeaveData leavedata) throws Exception
	{
		boolean result = false;
		String sql1 = "select * from leavetable where leavetype=? and owner=?;";
		String sql = "insert into leavetable(leavetype, owner, defaultHours, defaultUsedHours, comments ) values( ?,?,?,?,? );";
		PreparedStatement pstmt = null;
		try{
			ResultSet rs = null;
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, leavedata.getLeaveType());
			pstmt.setInt(2, leavedata.getOwner());
			
			rs = pstmt.executeQuery();
			
			boolean isnull = rs.next();
			//System.out.println("rs.wasNull():"+(isnull)+leavedata.getLeaveType()+leavedata.getOwner());
			
			if (!isnull){
				pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, leavedata.getLeaveType());
				pstmt.setInt(2, leavedata.getOwner());
				pstmt.setFloat(3, leavedata.getDefaultHours());
				pstmt.setFloat(4, leavedata.getDefaultUsed());
				pstmt.setString(5, leavedata.getComments());
				
				int re = pstmt.executeUpdate();
				result = re>0;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		return result;
	}
	
	/**
	 * 通过groupID 查询leavedata
	 * 
	 * @param conn
	 * @param project
	 * @param errors
	 * @throws Exception 
	 */
	public List searchLeaveDataByGroupID(Statement stmt, String gid) throws Exception
	{
		List leavedataList = new ArrayList();
		String sql = "select leavetypes.leaveTypeName,user_table.employDate, leavetable.*" +
				" from leavetable" +
				" LEFT JOIN leavetypes on leavetable.leavetype = leavetypes.leaveTypeId" +
				" left join user_table on user_table.user_id=leavetable.owner" +
				" where user_table.workloadgroupId=" +
				gid +
				" and user_table.leaveTime is null "+
				" order by owner ;";
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				LeaveData templeavedata = new LeaveData();
				templeavedata.setId(rs.getInt("id"));
				templeavedata.setLeaveType(rs.getInt("leavetype"));
				templeavedata.setLeaveTypeName(rs.getString("leaveTypeName"));
				templeavedata.setOwner(rs.getInt("owner"));
				templeavedata.setDefaultHours(rs.getFloat("defaultHours"));
				templeavedata.setIncrementHours(rs.getFloat("Increment"));
				templeavedata.setDefaultUsed(rs.getFloat("defaultUsedHours"));
				templeavedata.setUsedHours(rs.getFloat("usedHours"));
				templeavedata.setComments(rs.getString("comments"));
				templeavedata.setEmployDate(rs.getDate("employDate"));	
				templeavedata.initPTOTotalHoursandAvailableHours();				
				leavedataList.add(templeavedata);
			}
			System.out.println("size: "+leavedataList.size());
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return leavedataList;
	}
	/**
	 * 通过LeaveID 删除leavedata
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 */
	public boolean deleteLeave(Statement stmt, int leaveid) throws Exception
	{
		String sql = "delete from  leavetable where id='"+leaveid+"';";
		boolean result = false;
		
		try{
			int re = stmt.executeUpdate(sql);
			result = re>0;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return result;
	}
	
	/**
	 * 通过LeaveID 查找leavedata
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 */
	public LeaveData searchLeaveDataByID(Statement stmt, String leaveid) throws Exception
	{
		String sql = "select leavetable.id,   leavetable.leavetype,  leavetable.owner,  leavetable.defaultHours , "
						+"leavetable.defaultUsedHours,  leavetable.comments,  user_table.username, leavetypes.leaveTypeName "
						+"from leavetable LEFT JOIN leavetypes on leavetable.leavetype = leavetypes.leaveTypeId  , user_table "  
						+"where id='"+leaveid+"' and leavetable.owner = user_table.user_id;";
		LeaveData leavedata = new LeaveData();
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				leavedata.setId(rs.getInt("id"));
				leavedata.setLeaveTypeName(rs.getString("leaveTypeName"));
				leavedata.setOwnerName(rs.getString("username"));
				leavedata.setDefaultHours(rs.getFloat("defaultHours"));
				leavedata.setDefaultUsed(rs.getFloat("defaultUsedHours"));
				leavedata.setComments(rs.getString("comments"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return leavedata;
	}
	
	/**
	 * 通过指定时间段，指定GroupName查找leavedata
	 * 
	 * @param conn
	 * @param StartDate
	 * @param EndDate
	 * @param GroupName
	 * @throws Exception by collie 0718
	 */
	public ArrayList<ReportData_PayChex> searchLeaveDataForReport5(Connection conn,
			String startDate, String endDate, String groupName) throws Exception
	{
		ArrayList<ReportData_PayChex> listReportData = new ArrayList<ReportData_PayChex>();

		int countOfRecord = 0;

		String sql = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;// by collie 0503

		// Get projects,hours,comments,details,dates // by collie 0503
		sql = "select user_table.user_id,user_table.username,leavedetails.leavedate,groups.groupId,groups.groupName,leavedetails.leavedate,sum(leavedetails.hours) as hours," +
				"user_table.workloadRate" +
				", user_table.HPEmployeeNumber" +
				" from user_table" +
				" left join groups on user_table.workloadgroupId=groups.groupId" +
				" left join leavetable on user_table.user_id=leavetable.owner" +
				" left join leavedetails on leavetable.id=leavedetails.leaveId" +
				" where leavedetails.leavedate>='" +
				startDate +
				"'" +
				" and leavedetails.leavedate<='" +
				endDate +
				"'" +
				" and locate(?,groups.groupName)=1" +
				//" and leavedetails.hours<>0" +
				" and user_table.HPEmployeeNumber is not null<>0" +
				" and user_table.HPEmployeeNumber <> '' " +
				" group by user_table.user_id,leavedetails.leavedate" +
				" order by user_table.username,leavedetails.leavedate";
		try {
			pstmt = conn.prepareStatement(sql);	// by collie 0503
			//pstmt = conn.createStatement();
			pstmt.setString(1, groupName);// by collie 0503
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReportData_PayChex ReportData = new ReportData_PayChex();
				ReportData.setIntUserID(rs.getInt("user_id"));
				ReportData.setDouRate(rs.getDouble("workloadRate"));
				ReportData.setDatDate(rs.getDate("leavedate"));
				ReportData.setStrHours8(rs.getString("hours"));
				if (null!=rs.getString("HPEmployeeNumber")){
					ReportData.setStrEmployNumber6(rs.getString("HPEmployeeNumber"));
				}else{
					ReportData.setStrEmployNumber6("      ");
				}
				//ReportData.setStrHours8(ReportData.fillSpaceTo(ReportData.getStrHours8(), 8));
								
				listReportData.add(ReportData);

				countOfRecord = countOfRecord + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		return listReportData;
	}
	
	/**
	 * 通过userID 查找leavedata
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 */
	public List searchLeaveDataByUserID(Statement stmt, String userid) throws Exception
	{
		String sql = "select leavetable.id,   leavetable.leavetype,  leavetable.owner, " 
						+" leavetable.defaultHours , leavetable.defaultUsedHours,  user_table.username , "
						+" leavetable.Increment , "
						+" user_table.employDate , " 
						+" leavetypes.leaveTypeName "
						+"from leavetable LEFT JOIN leavetypes on leavetable.leavetype = leavetypes.leaveTypeId  , user_table "  
						+"where owner='"+userid+"' and leavetable.owner = user_table.user_id;";
		
		List<LeaveData> leavedataList = new ArrayList<LeaveData>();
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				LeaveData leavedata = new LeaveData();
				leavedata.setId(rs.getInt("id"));
				leavedata.setLeaveTypeName(rs.getString("leaveTypeName"));
				leavedata.setOwnerName(rs.getString("username"));
				leavedata.setDefaultHours(rs.getFloat("defaultHours"));
				leavedata.setDefaultUsed(rs.getFloat("defaultUsedHours"));
				leavedata.setEmployDate(rs.getDate("employDate"));
				leavedata.setIncrementHours(rs.getFloat("Increment"));
				leavedataList.add(leavedata);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (rs != null) {
				rs.close();
			}
		}
		return leavedataList;
	}
	
	/**
	 * 通过LeaveID 更新leavedata
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 */
	public boolean editLeaveData(Connection conn, LeaveData leavedata) throws Exception
	{
		boolean result = false;
		
		String sql = "update leavetable set defaultHours=? , defaultUsedHours=?, comments=? where id=?;";
		PreparedStatement pstmt = null;
		System.out.println("DAO_getDefaultHours="+leavedata.getDefaultHours());
		System.out.println("DAO_getUsedHours="+leavedata.getUsedHours());
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setFloat(1, leavedata.getDefaultHours());
			pstmt.setFloat(2, leavedata.getDefaultUsed());
			pstmt.setString(3, leavedata.getComments());
			pstmt.setInt(4, leavedata.getId());
			int re = pstmt.executeUpdate();
			result = re>0;
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != pstmt)
				pstmt.close();
		}
		
		return result;
	}
	
	/**
	 * 查找leavedata，此方法用于在Daily Records页面打开leave窗口
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 */
	public LeaveData searchLeaveData(Statement stmt, String leaveid) throws Exception
	{
		String sql = "select leavetable.id,   leavetable.leavetype,  leavetable.owner,  leavetable.defaultHours , "
						+"leavetable.defaultUsedHours,  leavetable.comments,  user_table.username, leavetypes.leaveTypeName "
						+"from leavetable LEFT JOIN leavetypes on leavetable.leavetype = leavetypes.leaveTypeId  , user_table "  
						+"where id='"+leaveid+"' and leavetable.owner = user_table.user_id;";
		LeaveData leavedata = new LeaveData();
		ResultSet rs = null;
		
		try{
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				leavedata.setId(rs.getInt("id"));
				leavedata.setLeaveTypeName(rs.getString("leaveTypeName"));
				leavedata.setOwnerName(rs.getString("username"));
				leavedata.setDefaultHours(rs.getFloat("defaultHours"));
				leavedata.setDefaultUsed(rs.getFloat("defaultUsedHours"));
				leavedata.setComments(rs.getString("comments"));
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return leavedata;
	}
	
	/**
	 * 保存leavedatadetails，此方法用于在打开的leave窗口请假
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public boolean saveLeaveDataDetails(Connection conn, LeaveDetails leavedetails, String forWho) throws Exception
	{
		boolean result = false;
		String sql0 = "";
		String sql = "";
		String sql2 = "";
		
		if("Myself".equals(forWho))
		{
			//@Dancy 2011-10-18 add sql0 请假记录查询
			sql0 = "select * from leavedetails where leaveId=(select id from leavetable where leavetype='"+leavedetails.getLeavetypeid()+"'"
							+" and owner='"+leavedetails.getOwner()+"') " +
							" and owner='"+leavedetails.getOwner()+"' " +
							" and leavedate='"+leavedetails.getDate()+"';";
			
			sql = "insert into leavedetails ( leaveId, owner, leavedate, hours, PTOtype ) "
						+"values ( (select id from leavetable where leavetype='"+leavedetails.getLeavetypeid()+
								"' and owner='"+leavedetails.getOwner()+"') ,'"+
								leavedetails.getOwner()+"','"+
								leavedetails.getDate()+"' , '"+
								leavedetails.getHours()+"' , "+
								"'"+leavedetails.getPTOsubtype()+"' );";
			sql2 = "update leavetable set usedHours=usedHours+"+leavedetails.getHours()+
			" where leavetype="+leavedetails.getLeavetypeid()+
			" and owner="+leavedetails.getOwner()+"; ";
		}
		else
		{
			//@Dancy 2011-10-18 add sql0 请假记录查询
			sql0 = "select * from leavedetails where leaveId='"+leavedetails.getLeaveDataId()+"' "
						+" and leavedate='"+leavedetails.getDate()+"';";
			
			sql = "insert into leavedetails ( leaveId, owner, leavedate, hours, PTOtype ) "
				+"values ( "+leavedetails.getLeaveDataId()+", (select owner from leavetable where id='"+leavedetails.getLeaveDataId()+"'), '"+leavedetails.getDate()+"', "+leavedetails.getHours()+", '"+leavedetails.getPTOsubtype()+"' );";
			
			sql2 = "update leavetable set usedHours=usedHours+"+leavedetails.getHours()+
			" where id="+leavedetails.getLeaveDataId()+";";
		}
		System.out.println(sql0);
		System.out.println(sql);
		System.out.println(sql2);
		
		Statement stmt = null;
		
		try{
			stmt = conn.createStatement();  
			
			ResultSet rs = stmt.executeQuery(sql0);
			
			if(rs.next())//遍历数据表，若存在记录
			{
				result = false;
				//return result;
			}else{
	            //修改默认的自动提交数据，执行多条数据  
	            conn.setAutoCommit(false);  
				
	            stmt.addBatch(sql);
	            stmt.addBatch(sql2);
	            
	            int[] re = stmt.executeBatch();// 提交事务
	            
				if(re[0] < 1 || re[1] < 1)
				{
					conn.rollback();
					//conn.setAutoCommit(true);
				}else
				{
					result = true;
					conn.commit();  
				}
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			try {
			    // 产生的任何SQL异常都需要进行回滚,并设置为系统默认的提交方式,即为TRUE
			    if (conn != null) {
			     conn.rollback();
			     conn.setAutoCommit(true);
			    }
			   } catch (Exception se1) {
			    se1.printStackTrace();
			   }
			throw ex;
		}finally
		{
			if(null != stmt)
				stmt.close();
			conn.setAutoCommit(true);
		}
		return result;
	}
	/**
	 * 修改leavedatadetails，此方法用于在打开的leave窗口修改请假信息
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public boolean modifyLeaveRecord(Connection conn, LeaveDetails leavedetails) throws Exception
	{
		boolean result = false;
		
		String sql = "update leavetable set usedHours=usedHours-(select hours from leavedetails where id="+leavedetails.getId()+")+"+leavedetails.getHours()
						+ "  where leavetype="+leavedetails.getLeavetypeid()+" and owner="+leavedetails.getOwner()+";";
		
		String sql2 = "update leavedetails set leaveId=( "
						+"select id from leavetable where leavetype="+leavedetails.getLeavetypeid()+" and owner="+leavedetails.getOwner()+" "
						+"),  PTOtype='"+leavedetails.getPTOsubtype()+"' ,  hours="+leavedetails.getHours()+" where id="+leavedetails.getId()+";";
		
		System.out.println(sql);
		System.out.println(sql2);
		
		Statement stmt = null;
		try{
			stmt = conn.createStatement();  
			//修改默认的自动提交数据，执行多条数据  
			conn.setAutoCommit(false);  
			
			stmt.addBatch(sql);
            stmt.addBatch(sql2);
			
            int[] re = stmt.executeBatch();// 提交事务
            
            if(re[0] < 1 || re[1] < 1)
			{
				conn.rollback();
			}else
			{
				result = true;
				conn.commit();  
			}
            
		}catch(Exception ex)
		{
			ex.printStackTrace();
			try {
			    // 产生的任何SQL异常都需要进行回滚,并设置为系统默认的提交方式,即为TRUE
			    if (conn != null) {
			     conn.rollback();
			     conn.setAutoCommit(true);
			    }
			   } catch (Exception se1) {
			    se1.printStackTrace();
			   }
			throw ex;
		}finally
		{
			if(null != stmt)
				stmt.close();
			conn.setAutoCommit(true);
		}
		
		return result;
	}
	/**
	 * 删除leavedatadetails，此方法用于在打开的leave窗口删除请假信息
	 * 
	 * @param conn
	 * @param project
	 * @throws Exception 
	 * @author longzhe
	 */
	public boolean deleteLeaveRecord(Connection conn, LeaveDetails leavedetails) throws Exception
	{
		boolean result = false;
		String sql = "update leavetable set usedHours=usedHours-(select hours from leavedetails where id="+leavedetails.getId()+") where id=(select leaveId from leavedetails where id="+leavedetails.getId()+");";
		String sql2 = "delete from leavedetails where id="+leavedetails.getId()+";";
		
		System.out.println(sql);
		System.out.println(sql2);
		
		Statement stmt = null;
		
		try{
			stmt = conn.createStatement();  
			//修改默认的自动提交数据，执行多条数据  
            conn.setAutoCommit(false);  
			
            stmt.addBatch(sql);
            stmt.addBatch(sql2);
            
            int[] re = stmt.executeBatch();// 提交事务
            
            
			
			if(re[0] < 1 || re[1] < 1)
			{
				conn.rollback();
			}else
			{
				result = true;
				conn.commit();
			}
            
		}catch(Exception ex)
		{
			ex.printStackTrace();
			try {
			    // 产生的任何SQL异常都需要进行回滚,并设置为系统默认的提交方式,即为TRUE
			    if (conn != null) {
			     conn.rollback();
			     conn.setAutoCommit(true);
			    }
			   } catch (Exception se1) {
			    se1.printStackTrace();
			   }
			throw ex;
		}finally
		{
			if(null != stmt)
				stmt.close();
			conn.setAutoCommit(true);
		}
		
		return result;
	}
	/**
	 * 获取PTO透支时间
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 * Author zhusimin
	 */
	public int getPTOOverdraft(Statement stmt) throws Exception
	{
		String sql = "select exceedHours from leavetypes where leaveTypeName='PTO';";
		
		int PTOOverdraft = 0;
		ResultSet rs = null;
		try{
			rs = stmt.executeQuery(sql);
			while(rs.next())
				PTOOverdraft = rs.getInt("exceedHours");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
		
		return PTOOverdraft;
	}
	/**
	 * 修改PTO透支时间
	 * 
	 * @param stmt
	 * @param PTOOverdraft
	 * @return
	 * @throws Exception
	 * Author longzhe
	 */
	public boolean modifyPTOOverdraft(Statement stmt, int PTOOverdraft) throws Exception
	{
		String sql = "update leavetypes set exceedHours = "+PTOOverdraft+" where leaveTypeName='PTO';";
		
		boolean result = false;
		try{
			int rs = stmt.executeUpdate(sql);
			result = rs>0;
		} catch (Exception e) {
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
//@Dancy
	public String getupdateGridDate(Connection conn ) throws Exception {
		String sql = "";
		PreparedStatement pstmt = null;
		String updateDate = "";
		//boolean result = false;
		try {
			sql = "select s.updateGridDate from systemsettings s";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
				updateDate = rs.getString("updateGridDate");
			//ResultSet rs = pstmt.executeQuery();
			//int re = pstmt.executeUpdate();
			//result = re>0;
			//while(rs.next()){
			//	leavedetails.setUpdateGridDate(rs.getDate("updateGridDate"));				
			//}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (pstmt != null) {
					pstmt.close();
				}
			}
			return updateDate;
	}
			
}
