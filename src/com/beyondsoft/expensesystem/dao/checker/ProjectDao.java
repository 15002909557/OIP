package com.beyondsoft.expensesystem.dao.checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import tools.bean.PageModel;
import tools.util.HQLQuery;

import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.util.BaseActionForm;

@SuppressWarnings("unchecked")
public class ProjectDao {
	private static ProjectDao dao = null;

	public static ProjectDao getInstance() {
		if (dao == null) {
			dao = new ProjectDao();
		}

		return dao;
	}

	/**
	 * 
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 * Author xiaofei // by collie 0427
	 * hanxiaoyu01 2012-12-27
	 */
	public List<String> uploadProjectList(Connection conn,List<Project> listProject) throws Exception 
	{
		 String usql = "select user_id from user_table where username=?";
			  // remove PONumber by dancy 20130608
		    String sql = "update projects set "
				+"product=?," 
				+"componentName=?, componentid = (select componentid from components where componentName=?),"
				+ "skillLevel=?,location=?,"
				+ "OTType=?," + "WBS=?," + "confirm=?," + "hidden=?," + "comments=? "
				+" where projectId=?";
		   
		 ResultSet rs = null;
		PreparedStatement pstmt = null;
		List<String> result = new ArrayList<String>();
		try {
			if (null != listProject) 
			{
				int index = 0;
				int sumsuccess = 0;
				while (index < listProject.size()) 
				{

					pstmt = conn.prepareStatement(usql);
					pstmt.setString(1, listProject.get(index).getUsername());
					rs = pstmt.executeQuery();
					if(rs.next())
					{
						sql = "update projects set "
							+"product=?," 
							+"componentName=?, componentid = (select componentid from components where componentName=?),"
							+ "skillLevel=?,"+"location=?,"+"userId=?,"
							+ "OTType=?," + "WBS=?," + "confirm=?," + "hidden=?," + "comments=? "
							+" where projectId=?";
						pstmt = conn.prepareStatement(sql);
				    	pstmt.setString(1, listProject.get(index).getProduct());
						pstmt.setString(2, listProject.get(index).getComponent());
						pstmt.setString(3, listProject.get(index).getComponent());
						pstmt.setString(4, listProject.get(index).getSkillLevel());
						pstmt.setString(5, listProject.get(index).getLocation());
						pstmt.setInt(6, rs.getInt("user_id"));
						pstmt.setString(7, listProject.get(index).getOTType());
						pstmt.setString(8, listProject.get(index).getWBS());
						pstmt.setInt(9, listProject.get(index).getConfirm());
						pstmt.setInt(10, listProject.get(index).getHidden());
						pstmt.setString(11, listProject.get(index).getComments());
						pstmt.setInt(12, listProject.get(index).getProjectId());
					}
					else //如果UserName不匹配则不更新userId
					{
						pstmt = conn.prepareStatement(sql);
				    	pstmt.setString(1, listProject.get(index).getProduct());
						pstmt.setString(2, listProject.get(index).getComponent());
						pstmt.setString(3, listProject.get(index).getComponent());
						pstmt.setString(4, listProject.get(index).getSkillLevel());
						pstmt.setString(5, listProject.get(index).getLocation());
						pstmt.setString(6, listProject.get(index).getOTType());
						pstmt.setString(7, listProject.get(index).getWBS());
						pstmt.setInt(8, listProject.get(index).getConfirm());
						pstmt.setInt(9, listProject.get(index).getHidden());
						pstmt.setString(10, listProject.get(index).getComments());						
						pstmt.setInt(11, listProject.get(index).getProjectId());
					}
					if (pstmt.executeUpdate() > 0) 
					{
						sumsuccess = sumsuccess + 1;
					} 
					else
					{
						result.add("--The project with id "
								+ listProject.get(index).getProjectId()
								+ " is not updated successfully!");
					}

					index = index + 1;
				}
				result.add("Totally " + sumsuccess + " projects are updated!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
		System.out.println("result="+result.size());
		return result;
	}
	
	
	/**
	 * �鿴��Ӧ��PO�Ƿ���ڣ����ڷ���true,�����ڷ���false
	 * @author hanxiaoyu01
	 * 2012-12-28
	 */
	public boolean checkPO(Connection conn,String product,String component,String PONumber)throws Exception{
		String sql="select pcp.* from pcp,products p,components c,po " +
				"where pcp.productid=p.productid and pcp.componentid=c.componentid and po.poid=pcp.poid " +
				"and p.product=? and c.componentName=? and po.PONumber=?";
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		boolean result=false;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, product);
			pstmt.setString(2, component);
			pstmt.setString(3, PONumber);
			rs=pstmt.executeQuery();
			if(rs.next()){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result;
	}
	
	/**
	 * �鿴��û������POd����
	 * 
	 */
	public boolean checkPO(Connection conn,String PONumber)throws Exception{
		String sql="select * from po where PONumber=?";
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		boolean result=false;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, PONumber);
			rs=pstmt.executeQuery();
			if(rs.next()){
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result;
	}
	/**
	 * ��ѯ��Ŀ�б���Ϣ
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 * Author xiaofei / by collie 0418
	 * @update by dancy 2012-11-15
	 * @update by hanxiaoyu01 2012-12-27�޸�sql ,ȥ�����õ��ֶ�
	 * remove po number by dancy 20130608
	 */
	public List<Project> downloadProjectList(Connection conn, String groupName)
			throws Exception {
		String sql="select g.groupname,u.username,p.* from projects p,user_table u,groups g " +
		"where p.userid=u.user_id and p.groupid=g.groupid and g.groupName=? " +
		"order by g.groupName,u.username,p.product,p.location,p.skillLevel,p.OTType,p.HPManager,p.projectId";
		// ���Ϊ All Data�����ѯ���У�hanxiaoyu01 2012-12-25
		
		String sql2="select g.groupname,u.username,p.* from projects p,user_table u,groups g " +
				"where p.userid=u.user_id and p.groupid=g.groupid " +
				"order by g.groupName,u.username,p.product,p.location,p.skillLevel,p.OTType,p.HPManager,p.projectId";
		
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		List<Project> listProject = new ArrayList<Project>();	
				
		try {
			if("All Data".equals(groupName)){
				pstmt=conn.prepareStatement(sql2);
			}else{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, groupName);
			}
			
			rs = pstmt.executeQuery();			
			while (rs.next()) {
				Project project = new Project();
				project.setProjectId(rs.getInt("projectId"));
				project.setUserId(rs.getInt("userId"));
				project.setUsername(rs.getString("username"));
				project.setComponent(rs.getString("componentName"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setLocation(rs.getString("location"));
				project.setComments(rs.getString("comments"));
				project.setConfirm(rs.getInt("confirm"));
				project.setGroupId(rs.getInt("groupId"));
				project.setGroupName(rs.getString("groupName"));
				project.setProduct(rs.getString("product"));
				project.setWBS(rs.getString("WBS"));
				project.setHidden(rs.getInt("hidden"));		
				project.setOTType(rs.getString("OTType"));
				listProject.add(project);
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

		return listProject;
	}
	
	/**
	 * ��ѯ��Ŀ�б���Ϣ
	 * 
	 * @param conn
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List searchProject(Connection conn, PageModel model)
			throws Exception {
		String sql = "select p.projectId,p.userId,p.createTime,p.projectName,p.skillLevel,p.location,p.OTType,p.comments,p.confirm,p.remove"
				+ " from projects p where p.projectId <> -1 order by p.projectId";
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
	 * ��ѯ��Ŀ��Ϣ
	 * 
	 * @param stmt
	 * @param projectId
	 *            �û����
	 * @return
	 * @throws Exception
	 */
	public Project load(Statement stmt, int projectId) throws Exception {
		Project project = null;
		System.out.println("Project in DAO :" + projectId);
		String sql = "select p.projectId,p.userId,p.createTime,p.skillLevel,"
				+ " p.location,p.OTType,p.comments,p.confirm,p.product,"
				+ "p.WBS,p.hidden, u.username,"
				+ " p.componentid , p.componentName ,p.groupId"
				+ " from projects p, user_table u"
				+ " where p.projectId = " + projectId + " and u.user_id=p.userId" ;
		
		System.out.println("sql="+sql);
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt("projectId"));
				project.setUserId(rs.getInt("userId"));
				project.setUsername(rs.getString("username"));
				project.setCreateTime(rs.getDate("createTime"));
				project.setComponent(rs.getString("componentName"));
				project.setComponentid(rs.getInt("componentid"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setLocation(rs.getString("location"));
				project.setOTType(rs.getString("OTType"));
				project.setGroupId(rs.getInt("groupId"));
				if(rs.getString("comments")==null)
				{
					project.setComments("");
				}
				else
				{
					project.setComments(rs.getString("comments"));
				}
				project.setConfirm(rs.getInt("confirm"));
				project.setProduct(rs.getString("product"));
				if(rs.getString("WBS")==null)
				{
					project.setWBS("");
				}
				else
				{
					project.setWBS(rs.getString("WBS"));
				}				
				project.setHidden(rs.getInt("hidden"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) 
			{
				rs.close();
			}
		}
		return project;
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param conn
	 * @param project
	 * @param errors
	 * @throws Exception
	 */
	public void saveProject(Connection conn, Project project,
			BaseActionForm form) throws Exception {
		String sql = "";
		PreparedStatement pstmt = null;
		try {
			sql = "update projects set comments = ? where projectId = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, project.getComments());
			pstmt.setInt(2, project.getProjectId());
			pstmt.executeUpdate();
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

public boolean ifExistProject(Connection conn, Project project,String flag) throws Exception
{
	String str = "";
	if("edit".equals(flag))
	{
		str = " and ProjectId!="+project.getProjectId();
	}	
	String 	sql = "select * from projects where componentid=? and product=? and skillLevel=? " 
			+ "and location=? and OTType=? and groupId=? and userId=? and wbs=?"+str;
	PreparedStatement pstmt = null;
	//�ж��Ƿ���ڸ���Ŀ
	boolean result = false;
	
	try {
	
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, project.getComponentid());
		pstmt.setString(2, project.getProduct());
		pstmt.setString(3, project.getSkillLevel());
		pstmt.setString(4, project.getLocation());
		pstmt.setString(5, project.getOTType());
		pstmt.setInt(6, project.getGroupId());
		pstmt.setInt(7,project.getUserId());
		pstmt.setString(8,project.getWBS());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())//������
		{	
			result = true;
		}
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		if (pstmt != null) {
			pstmt.close();
		}
	}
	return result;
}
	/**
	 * �½���Ŀ
	 * 
	 * @param conn
	 * @param project
	 * @param errors
	 * @throws Exception
	 * @update by dancy 2012-11-15  remove
	 * remove POName by dancy 2013/06/08
	 */
	public int saveNewProject(Connection conn, Project project,
			BaseActionForm form) throws Exception {
		String sql = "";
		String sql2=" ";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2=null;
		ResultSet rs=null;
		int pid=0;
		boolean re = this.ifExistProject(conn, project,"new");
		if(re)
		{
			form.setStrErrors("Exist the project");
		}
		else
		{
			try 
			{
				sql = "call newProject2(?,curdate(),?,?,?,?,?,?,?,?,?,?,@a);";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, project.getGroupId());
				pstmt.setInt(2, project.getComponentid());
				pstmt.setString(3, project.getSkillLevel());
				pstmt.setString(4, project.getLocation());
				pstmt.setString(5, project.getOTType());
				pstmt.setString(6, project.getComments());
				pstmt.setInt(7, 0);
				pstmt.setInt(8, project.getGroupId());
				pstmt.setString(9, project.getProduct());
				pstmt.setInt(10,project.getUserId());
				pstmt.setString(11, project.getWBS());
				pstmt.executeUpdate();
				form.setStrErrors("");
				sql2="select @a";
				pstmt2=conn.prepareStatement(sql2);
				rs=pstmt2.executeQuery();
				if(rs.next())
				{
					pid=rs.getInt("@a");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (pstmt != null) {
					pstmt.close();
				}
				if(pstmt2!=null){
					pstmt2.close();
				}
				if(rs!=null){
					rs.close();
				}
			}

		
		}
		return pid+1;
	}
	/**
	 * �޸�project��Ϣ name,skillLevel,location,OTType,comments,confirm��group ��
	 * 
	 * @param conn
	 * @param project
	 * @param errors
	 * @throws Exception
	 * @flag
	 * remove PO Number by dancy
	 */
	public int modifyProject(Connection conn, Project project, BaseActionForm form) throws Exception 
	{
		int flag = 0;
		boolean result = false;
		boolean re  = this.ifExistProject(conn, project,"edit");
		String sql = "update projects set"
						+" componentid=?, skillLevel=?, location=?, OTType=?, "
						+" comments=?, confirm=?, groupId=?, product=?, "
						+" WBS=?, hidden=?, userId=?, "
						+" componentName=(select componentName from components where componentid=?) "
						+" where projectId=?;";
		String sql2 = "update expensedata set userId=? where projectId=?";
		PreparedStatement pstmt = null;
		if(re)
		{
			form.setStrErrors("Exist the project");
			flag = -1;
		}
		else
		{
			try
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, project.getComponentid());
				pstmt.setString(2, project.getSkillLevel());
				pstmt.setString(3, project.getLocation());
				pstmt.setString(4, project.getOTType());
				pstmt.setString(5, project.getComments());
				pstmt.setInt(6, project.getConfirm());
				pstmt.setInt(7, project.getGroupId());
				pstmt.setString(8, project.getProduct());
				pstmt.setString(9, project.getWBS());
				pstmt.setInt(10, project.getHidden());
				pstmt.setInt(11, project.getUserId());
				pstmt.setInt(12, project.getComponentid());
				pstmt.setInt(13, project.getProjectId());// updated by dancy 2013/06/08
				int i = pstmt.executeUpdate();
				result = i>0;
				
				pstmt = null;
				if(result)
				{
					pstmt = conn.prepareStatement(sql2);
					pstmt.setInt(1, project.getUserId());
					pstmt.setInt(2, project.getProjectId());
					i = pstmt.executeUpdate();
					result = i>0;
					flag = 1;
				}
				else
				{
					flag = 0;
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}finally {
				if (pstmt != null) {
					pstmt.close();
				}
			}
		}
		return flag;
	}
	
	/**
	 * ɾ��Project
	 * 
	 * @param stmt
	 * @param project
	 * @param errors
	 * @throws Exception
	 */
	public boolean deleteProject(Statement stmt,int pid) throws Exception 
	{
		boolean result = false;
		String sql = "update projects set remove=1 where projectId="+pid+";";
		try{
			int i = stmt.executeUpdate(sql);
			result = i>0;
		}catch(Exception e){
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
	 * ����ݿ��õ�skillLevel,location,OTType��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public Vector searchDetails(Statement stmt) throws Exception 
	{
		Vector<List<String>> vec = new Vector();
		List<String> skillLevellist = new ArrayList<String>();
		List<String> locationlist = new ArrayList<String>();
		List<String> OTTypelist = new ArrayList<String>();
		List<String> userlist = new ArrayList<String>();
		List<String> componentlist = new ArrayList<String>();
		List<String> productlist = new ArrayList<String>();
		List<String> wbslist = new ArrayList<String>();

		String sql1="select s.skillLevelName from skilllevels s where hide=0 group by s.skillLevelName order by s.skillLevelName";//添加了hide条件，FWJ 2013-05-06
		String sql2 = "select l.locationName from locations l where hide=0 group by l.locationName order by l.locationName;";//添加了hide条件，FWJ 2013-05-06
		String sql3 = "select o.OTTypeName from ottype o where hide=0 group by o.OTTypeName order by length(o.OTTypeName);";//添加了hide条件，FWJ 2013-05-06
		String sql4 = "select u.user_id, u.username from user_table u where account_disabled='f' order by u.username;";
		String sql5 = "select componentName, componentid from components where hide=0 order by componentName;";//添加了hide条件，FWJ 2013-05-06
		String sql6 = "select product from products where hide=0 order by product;";//添加了hide条件，FWJ 2013-05-06
		String sql7 ="select * from wbs where hide=0";//添加了hide条件，FWJ 2013-05-03
		ResultSet rs1 = null;ResultSet rs2 = null;ResultSet rs3 = null;
		ResultSet rs4 = null;ResultSet rs5 = null;ResultSet rs6 = null;
		ResultSet rs7 = null;
		try{
			 rs1 = stmt.executeQuery(sql1);
			while(rs1.next())
				skillLevellist.add(rs1.getString("skillLevelName"));

			 rs2 = stmt.executeQuery(sql2);
			while(rs2.next())
				locationlist.add(rs2.getString("locationName"));
			
			 rs3 = stmt.executeQuery(sql3);
			while(rs3.next())
				OTTypelist.add(rs3.getString("OTTypeName"));
			
			 rs4 = stmt.executeQuery(sql4);
			while(rs4.next())
			{
				userlist.add(rs4.getString("user_id"));
				userlist.add(rs4.getString("username"));
			}
			
			rs5 = stmt.executeQuery(sql5);
			while(rs5.next())
			{
				componentlist.add(rs5.getString("componentName"));
				componentlist.add(rs5.getString("componentid"));
			}
			
			rs6 = stmt.executeQuery(sql6);
			while(rs6.next())
			{
				productlist.add(rs6.getString("product"));
			}
			
			rs7 = stmt.executeQuery(sql7);
			while(rs7.next())
			{
				wbslist.add(rs7.getString("wbs"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if(null != rs1)
				rs1.close();
			if(null != rs2)
				rs2.close();
			if(null != rs3)
				rs3.close();
			if(null != rs4)
				rs4.close();
			if(null != rs5)
				rs5.close();
			if(null != rs6)
				rs6.close();
			if(null != rs7)
				rs7.close();
			if (stmt != null)
				stmt.close();
		}
		vec.add(0,skillLevellist);
		vec.add(1,locationlist);
		vec.add(2,OTTypelist);
		vec.add(3,userlist);
		vec.add(4,componentlist);
		vec.add(5,productlist);
		vec.add(6,wbslist);
		return vec;
	}
	/**
	 * �ƶ�������Ϣ
	 * @param conn
	 * @param GroupId
	 * @param UserId
	 * @return result
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public boolean moveLeave(Connection conn, int GroupId, int UserId) throws Exception
	{
		boolean result = false;
		String sql = "update projects set groupId = "+GroupId+" where userId = "+UserId+" and leaveType!='null';";
		Statement stmt = null;
		try{
			stmt = conn.createStatement();
			int re = stmt.executeUpdate(sql);
			System.out.println(sql);
			result = re>0;
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (conn != null)
				conn.close();
		}
		
		return result;
	}
	/**
	 * ɾ��û��confirm��Project
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public boolean deleteTempProject(Connection conn, int projectid)
			throws Exception {
		boolean result = true;
		
		String sql = "call deleteTempProject("+projectid+")";
		try{
			Statement stmt = conn.createStatement();
			int re = stmt.executeUpdate(sql);
			result = re>0;
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			if(null != conn)
				conn.close();
		}
		return result;
	}
	/**
	 * ������Ŀ�ֶ��б� ProjectName
	 * 
	 * @param conn
	 * @param projectname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addProjectName(Connection conn, String projectname, int fwsw)
					throws Exception
	{
		boolean result = false;
		//System.out.println("pname="+projectname+",fwsw="+fwsw);
		String sql = "insert into projectnames( projectname, groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� ProjectName
	 * 
	 * @param conn
	 * @param projectname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean modifyProjectName(Connection conn, String projectname, int pid)
					throws Exception
	{
		boolean result = false;
		String sql = "update projectnames set projectname=? where projectnameid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, projectname);
			pstmt.setInt(2, pid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� ProjectName
	 * 
	 * @param conn
	 * @param projectname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean removeProjectName(Connection conn, int pid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from projectnames where projectnameid = ?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * ������Ŀ�ֶ��б� ProductName
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addProductName(Connection conn, String productname, int fwsw)
					throws Exception
	{
		boolean result = false;
		//System.out.println("productname="+productname+",fwsw="+fwsw);
		String sql = "insert into products( product, groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� ProductName
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean modifyProductName(Connection conn, String productname, int pdid)
					throws Exception
	{
		boolean result = false;
		String sql = "update products set product = ? where productid = ?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productname);
			pstmt.setInt(2, pdid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� ProductName
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean removeProductName(Connection conn, int pdid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from products where productid = ?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pdid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * ������Ŀ�ֶ��б� SkillLevel
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addSkillLevel(Connection conn, String skillname, String shortname, int fwsw)
					throws Exception
	{
		boolean result = false;
		//System.out.println("skillname="+skillname+",fwsw="+fwsw);
		String sql = "insert into skilllevels( skillLevelName, skillLevelShortName, groupnum ) values ( ?, ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, skillname);
			pstmt.setString(2, shortname);
			pstmt.setInt(3, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� SkillLevel
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean modifySkillLevel(Connection conn, String skillname, String shortname, int sklid)
					throws Exception
	{
		boolean result = false;
		String sql = "update skilllevels set skillLevelName=? , skillLevelShortName=? where skillLevelId=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, skillname);
			pstmt.setString(2, shortname);
			pstmt.setInt(3, sklid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� SkillLevel
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean removeSkillLevel(Connection conn, int sklid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from skilllevels where skillLevelId=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sklid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * ������Ŀ�ֶ��б� Location
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addLocation(Connection conn, String locationname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into locations( locationName,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, locationname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� Location
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean modifyLocation(Connection conn, String locationname, int loid)
					throws Exception
	{
		boolean result = false;
		String sql = "update locations set locationName=? where locationId=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, locationname);
			pstmt.setInt(2, loid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� Location
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean removeLocation(Connection conn, int loid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from locations where locationId=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, loid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * ������Ŀ�ֶ��б� OTType
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addOTType(Connection conn, String otname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into ottype( OTTypeName,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, otname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� OTType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifyOTType(Connection conn, String otname, int otid)
					throws Exception
	{
		boolean result = false;
		String sql = "update ottype set OTTypeName=? where OTTypeId=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, otname);
			pstmt.setInt(2, otid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� OTType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeOTType(Connection conn, int otid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from ottype where OTTypeId=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, otid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ������Ŀ�ֶ��б� OTType
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addWorkType(Connection conn, String wtname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into worktype( worktype,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, wtname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� WorkType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifyWorkType(Connection conn, String wtname, int wtid)
					throws Exception
	{
		boolean result = false;
		String sql = "update worktype set worktype=? where id=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, wtname);
			pstmt.setInt(2, wtid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� WorkType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeWorkType(Connection conn, int wtid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from worktype where id=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, wtid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ������Ŀ�ֶ��б� OTType
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addMilestone(Connection conn, String msname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into milestones( milestone,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� WorkType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifyMilestone(Connection conn, String msname, int msid)
					throws Exception
	{
		boolean result = false;
		String sql = "update milestones set milestone=? where milestoneid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msname);
			pstmt.setInt(2, msid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� Milestone
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeMilestone(Connection conn, int msid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from milestones where milestoneid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, msid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ������Ŀ�ֶ��б� TestType
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addTestType(Connection conn, String ttname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into testtypes( testtype,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ttname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� TestType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifyTestType(Connection conn, String ttname, int ttid)
					throws Exception
	{
		boolean result = false;
		String sql = "update testtypes set testtype=? where testtypeid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ttname);
			pstmt.setInt(2, ttid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� TestType
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeTestType(Connection conn, int ttid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from testtypes where testtypeid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ttid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ������Ŀ�ֶ��б� TestSession
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addTestSession(Connection conn, String tsname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into testsession( testsessionname,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tsname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� TestSession
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifyTestSession(Connection conn, String tsname, int tsid)
					throws Exception
	{
		boolean result = false;
		String sql = "update testsession set testsessionname=? where id=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tsname);
			pstmt.setInt(2, tsid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� TestSession
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeTestSession(Connection conn, int tsid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from testsession where id=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tsid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * ������Ŀ�ֶ��б� Decription
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addDecription(Connection conn, String dename, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into descriptions( description,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dename);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� Decription
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifyDecription(Connection conn, String dename, int deid)
					throws Exception
	{
		boolean result = false;
		String sql = "update descriptions set description=? where descriptionid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dename);
			pstmt.setInt(2, deid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� Decription
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeDecription(Connection conn, int deid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from descriptions where descriptionid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, deid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ������Ŀ�ֶ��б� SkillCategory
	 * 
	 * @param conn
	 * @param productname
	 * @param fwsw
	 * @return
	 * @throws Exception
	 */
	public boolean addSkillCategory(Connection conn, String stname, int fwsw)
					throws Exception
	{
		boolean result = false;
		String sql = "insert into skillcategories( skillcategory,  groupnum ) values ( ?, ? );";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, stname);
			pstmt.setInt(2, fwsw);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * �޸���Ŀ�ֶ��б� SkillCategory
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean modifySkillCategory(Connection conn, String scname, int scid)
					throws Exception
	{
		boolean result = false;
		String sql = "update skillcategories set skillcategory=? where skillcategoryid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, scname);
			pstmt.setInt(2, scid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	/**
	 * ɾ����Ŀ�ֶ��б� SkillCategory
	 * 
	 * @param conn
	 * @param otname
	 * @param otid
	 * @return
	 * @throws Exception
	 */
	public boolean removeSkillCategory(Connection conn, int scid)
					throws Exception
	{
		boolean result = false;
		String sql = "delete from skillcategories where skillcategoryid=?;";
		PreparedStatement pstmt = null;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, scid);
			int re = pstmt.executeUpdate();
			result = re>0;
		} catch (Exception e) {
			// ��ӡ������Ϣ
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2013-02-18
	 * ��Ĭ��ֵǰ�����е�Ĭ��ֵɾ��
	 */
	public void deleteDefaultProject(Connection conn,int userId)throws Exception{
		String sql="delete from default_project where userId=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
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
	 * 2013-02-18
	 * ��project����Ĭ��
	 * remove POName by dancy 20130608ֵ
	 */
	public int setDefaultProject(Connection conn,int userId,Project project)throws Exception{
		String sql="insert into default_project values (?,?,?,?,?,?,?)";
		PreparedStatement pstmt=null;
		int result=0;
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, project.getComponentid());
			pstmt.setString(3, project.getProduct());
			pstmt.setString(4, project.getWBS());
			pstmt.setString(5, project.getSkillLevel());
			pstmt.setString(6, project.getLocation());
			pstmt.setString(7, project.getOTType());
			result=pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}
		return result;
		
	}
	
	/**
	 * @author hanxiaoyu01
	 * �õ���ǰ�û�Project��Ĭ��ֵ
	 * 2013-02-18
	 */
	public Project getDefaultProject(Connection conn,int userId)throws Exception{
		String sql="select * from default_project where userId=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Project project=new Project();
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs=pstmt.executeQuery();
			if(rs.next()){
				project.setComponentid(rs.getInt("componentid"));
				project.setProduct(rs.getString("productName"));
//				project.setPOName(rs.getString("POName"));
				project.setWBS(rs.getString("WBS"));
				project.setSkillLevel(rs.getString("skillLevel"));
				project.setLocation(rs.getString("Location"));
				project.setOTType(rs.getString("OTType"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
		return project;
		
	}
	/**
	 * @author hanxiaoyu01
	 * remove PO Number by dancy 20130613
	 */
	public Project findProjectById(Connection conn,int pid)throws Exception{
		String sql="select u.username,p.componentName,p.product,p.WBS,p.skillLevel,p.location,p.OTType,g.groupName from projects p,user_table u,groups g where projectId=? and u.user_Id=p.userId and p.groupId=g.groupId";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Project p=new Project();
		try{
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs=pstmt.executeQuery();
			if(rs.next()){
				p.setProjectID(pid);
				p.setUsername(rs.getString("username"));
				p.setComponent(rs.getString("componentName"));
				p.setProduct(rs.getString("product"));
				p.setWBS(rs.getString("WBS"));
				p.setSkillLevel(rs.getString("skillLevel"));
				p.setLocation(rs.getString("location"));
				p.setOTType(rs.getString("OTType"));
				p.setGroupName(rs.getString("groupName"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
			}
		}
		
		return p;
	}
}

