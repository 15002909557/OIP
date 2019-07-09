package com.beyondsoft.expensesystem.action.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.dao.checker.GroupsDao;
import com.beyondsoft.expensesystem.dao.system.AdministratorDao;
import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.system.PermissionLevel;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.form.system.SysUserForm;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;
import com.beyondsoft.expensesystem.util.MD5;

@SuppressWarnings({ "unchecked", "deprecation" })
public class SysUserAction extends BaseDispatchAction
{
	/**
	 * �޸��û�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePWD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		String username = (String) request.getParameter("username");
		System.out.println("change password for "+username);
		String oldpassword = (String) request.getParameter("oldpassword");
		String newpasword = (String) request.getParameter("newpassword");
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		
//		Pattern p = Pattern.compile("^([a-zA-Z0-9]|[!@#=-_)]){12,20}$");
//		Matcher ma = p.matcher(newpasword);
//		boolean b = ma.matches();
//		
//		if(b)
//		{
			MD5 m = new MD5();
			oldpassword = m.getMD5ofStr(oldpassword);
			newpasword = m.getMD5ofStr(newpasword);
			int result = -1;
			Connection conn = null;
			Statement stmt = null;
			try
			{
				conn = DataTools.getConnection(request);
				stmt = conn.createStatement();
				
				if(user.getUserName().equals(username)) 
				{
					result = SysUserDao.getInstance().changePWD(stmt, username, oldpassword, newpasword, "self");
				}
				else  
				{
					result = SysUserDao.getInstance().changePWD(stmt, username, oldpassword, newpasword, "other");
				}
				switch(result)
				{
					case -1:
						System.out.println("Change failed");
						request.setAttribute("result", "Modification failed!<P>Please try again after a while.");
						break;
					case 1:
						System.out.println("incorrect");
						request.setAttribute("result", "Username or old password wrong!<br>Please confirm.");
						break;
					case 2:
						System.out.println("incorrect");
						request.setAttribute("result", "Username or old password wrong!<br>Please confirm.");
						break;
					case 3:
						System.out.println("failed");
						request.setAttribute("result", "Modification failed!<br>Please try again after a while.");
						break;
					case 4:
						System.out.println("success");
						request.setAttribute("result", "<font color='green'>Change Password Success!<br />Please use the new password at next logon.</font>");
						break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				conn.rollback();
			}
			finally
			{
				if (stmt != null)
				{
					stmt.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
//		}else{
//			System.out.println("�޸����� �������ʽ����");
//			request.setAttribute("result", "Please format you new password following the red information!");
//		}
		return mapping.findForward(mainForm.getOperPage());
	}
	/**���ǰ�û���lead��load��ǰgroup�µ������û��������޸ı��˵�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward toChangePWD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		String gid = (String) request.getParameter("gid");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(3==user.getLevelID() || 4== user.getLevelID()) // �����lead �� checker ���ܸı��˵�����
		{
			List<SysUser> Userlist = new ArrayList<SysUser>(); // User List
			Connection conn = null;
			Statement stmt = null;
			try {
				conn = DataTools.getConnection(request);
				stmt = conn.createStatement();
				Userlist = GroupsDao.getInstance().getUsersbyGid(stmt, Integer.parseInt(gid));
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
			request.getSession().setAttribute("userList_changePWD", Userlist);
		}
		
		return mapping.findForward(mainForm.getOperPage());
//		return this.search(mapping, form, request, response);
		
	}


	/**
	 * �����û� ��ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @flag
	 */
	
	public ActionForward toinsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		Connection conn = null;
		Statement stmt = null;
		List<Map> Permissionlist = new ArrayList<Map>();
		List<Map> Approvelist = new ArrayList<Map>();
		
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Permissionlist = AdministratorDao.getInstance().searchPermissionLevels(stmt);
			Approvelist = AdministratorDao.getInstance().searchAppvoeLevel(stmt);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		String gids=(String)request.getAttribute("gids");
		request.setAttribute("gids", gids);
		request.setAttribute("Permissionlist", Permissionlist);
		request.setAttribute("Approvelist", Approvelist);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	
	/**
	 * ���³�ʼ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			mainForm.setSysUser(SysUserDao.getInstance().load(stmt,
					Integer.parseInt(mainForm.getRecid())));
			mainForm.getSysUser().setPassword("");
			mainForm.setPermissionLevel(AdministratorDao.getInstance()
					.searchPermissionLevels(stmt));
		}
		catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR,
					new ActionError("errors.init"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		return mapping.findForward(mainForm.getOperPage());
	}


	/**
	 * ͨ��id����User
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 *  @flag
	 */
	public ActionForward searchUserByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUser user = null;
		String uid = (String) request.getParameter("userid");
		String gid = (String)request.getParameter("gid");
		String gname = (String)request.getParameter("gname");
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			user = SysUserDao.getInstance().load(stmt, Integer.parseInt(uid));
		}catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.delete"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		request.setAttribute("user", user);
		request.setAttribute("gid", gid);
		request.setAttribute("gname", gname);
		return searchDetails( mapping,  form, request,  response);
	}
	/**
	 * ����ݿ��õ�permission Level , groups��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 *  @flag
	 */
	public ActionForward searchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		SysUser su = (SysUser)request.getSession().getAttribute("user");
		int gid = su.getGroupID();
		
		List<PermissionLevel> pmlist = new ArrayList<PermissionLevel>();
		List<Groups> glist = new ArrayList<Groups>();
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			glist = GroupsDao.getInstance().load(stmt, gid);
			pmlist = SysUserDao.getInstance().searchPermissionLevel(stmt);
		}catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.delete"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		request.setAttribute("glist", glist);
		request.setAttribute("pmlist", pmlist);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * �޸�User��Ϣ permission Level , groups��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 *  Author=Longzhe
	 */
	public ActionForward modifyUse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		SysUser user = new SysUser();
		int uid = Integer.parseInt(request.getParameter("UID"));
		int GID = Integer.parseInt(request.getParameter("GID")); //ԭ����GroupId
		String gname = (String)request.getParameter("GroupsName");//ԭ����GroupName
		int level = Integer.parseInt(request.getParameter("PermissionLevel"));
		System.out.println("level:"+level);
		String email = (String)request.getParameter("Email").trim();
		String HPEmployeeNumber = (String)request.getParameter("EmployeeID");
		int gid = Integer.parseInt(request.getParameter("move"));//�޸ĵ�GroupId
		String massage = (String)request.getParameter("Massage").trim();
		user.setUserId(uid);
		user.setLevelID(level);
		user.setEmail(email);
		if("".equals(HPEmployeeNumber) || null == HPEmployeeNumber)
			user.setHPEmployeeNumber(null);
		else
			user.setHPEmployeeNumber(HPEmployeeNumber);
		user.setGroupID(gid);
		user.setMassage(massage);
		boolean result = false;
		Connection conn = null;
		try{
			conn = DataTools.getConnection(request);
			result = SysUserDao.getInstance().modifyUse(conn, user, GID);
			System.out.println("modify User "+result);
			//����޸���Group ��ô������ϢҲҪ�޸ĵ���Ӧ��Group��
			
		}catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.delete"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		
		String wait = "wait";
		request.setAttribute("wait", wait);
		String groupid = String.valueOf(GID);
		request.setAttribute("gid", groupid);
		request.setAttribute("gname", gname);
		return mapping.findForward(mainForm.getOperPage());
	}
/**
 * �����һ���û�
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 * @flag
 */
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		
		
		SysUser user = new SysUser();
		
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		int level = Integer.parseInt((String) request.getParameter("level"));
		int approvelevel = Integer.parseInt((String) request.getParameter("approvelevel"));
		String email = (String) request.getParameter("email");
		//@Dancy 2011-11-17
		String employeeID = (String)request.getParameter("employeeID");
		
		String gid = (String) request.getParameter("groupId");		
		System.out.println("level="+level+" and approvelevel="+approvelevel);
		
		MD5 m = new MD5();
		
		int result=-1;

		user.setUserName(username);
		user.setPassword(m.getMD5ofStr(password));
		user.setLevelID(level);
		user.setApproveLevel(approvelevel);
		user.setGroupID(Integer.parseInt(gid));
		user.setEmail(email);
		//Dancy 1117
		user.setHPEmployeeNumber(employeeID);
		
		Connection conn = null;
		try{
			conn = DataTools.getConnection(request);
			result = SysUserDao.getInstance().addUser(conn, user);
			System.out.println("add User "+result);

		}catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * @author hanxiaoyu01
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * �鵽������Ҫ���ʼ�֪ͨ��������û�����
	 */
	public List<SysUser>  findAll(Connection conn)throws Exception{
		List<SysUser> userlist=null;
		List<SysUser> sendlist=new ArrayList<SysUser>();
		try{
			userlist=SysUserDao.getInstance().findAll(conn);
		}catch(Exception e){
			e.printStackTrace();
		}
		long di;
		 for(int i=0;i<userlist.size();i++){
			 di=userlist.get(i).getExpireDay();
			 //2013-04-09 change di>=85 to di>=80 by dancy
			 if(di>=80&&di<90)
			 {
				 sendlist.add(userlist.get(i));
			 }
		 }
		 
		 return sendlist;
	}

	/**
	 * ��ѯ�û��б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		SysUserForm mainForm = (SysUserForm) form;
		List list = null;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			list = SysUserDao.getInstance().searchUser(conn, mainForm.getPageModel());
		}
		catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			this.saveErrors(request, errors);
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		request.setAttribute("list", list);

		return mapping.findForward(mainForm.getOperPage());
	}
	
/**
 * �û��ֲ�����
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws IOException
 * @author dancy 2013-01-06
 */
	public ActionForward downloadLocal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		//get current user
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		// �ļ���Ĭ�ϱ�����
		String fileName = "";
		// �ļ��Ĵ��·��
		String path = "";
		if(u.getLevelID()==5)
		{
			path = "C:/OIP/IBSManuals/User's Manual of IBS OIP for Data Filler.docx";
			fileName = "User's Manual of IBS OIP for Data Filler";
		}
		else if(u.getLevelID()==4)
		{
			path = "C:/OIP/IBSManuals/User's Manual of IBS OIP for Data Checker.docx";
			fileName = "User's Manual of IBS OIP for Data Checker";
		}
		else
		{
			
			path = "C:/OIP/IBSManuals/User's Manual of IBS OIP for Data Approver.docx";
			fileName = "User's Manual of IBS OIP for Data Approver";
		}
		
		File file = null;
		
		// ѭ��ȡ�����е����
		byte[] b = new byte[1024];
		int len;
		//�����
		OutputStream ops = null;
		FileInputStream fis = null;
		file = new File(path);
		if(file.exists())//file exists
		{
			// ��������ĸ�ʽ
			response.setHeader("Content-disposition","inline; filename="+fileName+".docx");
			response.setContentType("application/vnd.ms-word"); 
			try 
			{			
				ops = response.getOutputStream();
				fis = new FileInputStream(file);
				while ((len = fis.read(b)) > 0)
				{
					ops.write(b, 0, len);
					ops.flush();
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				if(fis!=null)
				{
					fis.close();
					fis = null;
				}
				if(ops!=null)
				{
					ops.close();
					ops = null;
				}
			}
		}
		else
		{
			//file does not exist
			//forward to another page?
		}
		return null;
	}
	
	/**
	 * hanxiaoyu01 2013-01-25 Add New Userʱ���System Level�õ�Group���������
	 */

	public ActionForward getGroups(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		int level=Integer.parseInt(request.getParameter("level"));
		List<Map> glist=new ArrayList<Map>();
		if(level==1||level==2||level==3){//ֻ��All Data
			Map map=new HashMap<String,String>();
			map.put("gid","-1");
			map.put("gname", "All Data");
			glist.add(map);
		}else{//û��All Data
			SysUser su = (SysUser) request.getSession().getAttribute("user");		
			List<Groups> Grouplist = new ArrayList<Groups>();
			Statement stmt=null;
			try{
				stmt = DataTools.getConnection(request).createStatement();
				Grouplist = GroupsDao.getInstance().load(stmt, su.getGroupID());
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(stmt!=null){
					stmt.close();
				}
			}
			for(int i=0;i<Grouplist.size();i++){
				if(Grouplist.get(i).getGid()!=-1){
					Map map=new HashMap<String,String>();
					map.put("gid",Grouplist.get(i).getGid());
					map.put("gname", Grouplist.get(i).getGname());
					glist.add(map);
				}
			}
		}
		JSONArray jsonArray=JSONArray.fromObject(glist);
		PrintWriter out=response.getWriter();
		out.print(jsonArray.toString());
		out.close();
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author hanxiaoyu01 2013-01-28
	 * �жϵ�ǰ����All Data��Group���Ƿ��Ѵ���Data Checker�û�
	 * 
	 */
	public ActionForward checkUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		int gid=Integer.parseInt(request.getParameter("gid"));
		Connection conn=null;
		boolean result=false;
		try{
			conn= DataTools.getConnection(request);
			result=SysUserDao.getInstance().checkUser(conn, gid);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		PrintWriter out=response.getWriter();
		if(result==true){//�Ѵ���Checker�û�
			out.print("false");
		}else{
			out.print("true");
		}
		out.close();
		return null;
	}

}
