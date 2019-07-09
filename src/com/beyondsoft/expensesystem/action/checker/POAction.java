package com.beyondsoft.expensesystem.action.checker;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.*;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.form.checker.POForm;
import com.beyondsoft.expensesystem.time.SendThread3;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;
import com.beyondsoft.expensesystem.dao.checker.PODao;

import com.beyondsoft.expensesystem.dao.system.AdministratorDao;
import com.beyondsoft.expensesystem.domain.checker.CaseDefect;
import com.beyondsoft.expensesystem.domain.checker.DefaultCaseDefect;
import com.beyondsoft.expensesystem.domain.checker.Groups;
import com.beyondsoft.expensesystem.domain.checker.NonLaborCost;
import com.beyondsoft.expensesystem.domain.checker.Project;
import com.beyondsoft.expensesystem.domain.checker.ProjectOrder;
import com.beyondsoft.expensesystem.domain.checker.StatusChangeLog;
import com.beyondsoft.expensesystem.domain.system.SysUser;




import java.util.Calendar;


public class POAction extends BaseDispatchAction {

/********************************Case & Defect***************************************/

	/**
	 * ���caseDefect
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */

	public ActionForward addCaseDefect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CaseDefect cd = new CaseDefect();
		int productID = Integer.parseInt((String) request.getParameter("productid"));
		int componentID = Integer.parseInt((String) request.getParameter("componentid"));
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		int cases = Integer.parseInt((String) request.getParameter("cases"));
		int urgentdefect = Integer.parseInt((String) request.getParameter("urgentdefect"));
		int highdefect = Integer.parseInt((String) request.getParameter("highdefect"));
		int normaldefect = Integer.parseInt((String) request.getParameter("normaldefect"));
		int lowdefect = Integer.parseInt((String) request.getParameter("lowdefect"));
		int milestoneid = Integer.parseInt((String) request.getParameter("milestoneid"));
		
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		
		cd.setProductID(productID);
		cd.setComponentID(componentID);
		cd.setSDate(sdate);
		cd.setEDate(edate);
		cd.setCases(cases);
		cd.setUrgentdefect(urgentdefect);
		cd.setHighdefect(highdefect);
		cd.setNormaldefect(normaldefect);
		cd.setLowdefect(lowdefect);
		cd.setMilestoneid(milestoneid);
		cd.setCreator(u.getUserName());
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().addCaseDefect(conn, cd);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		System.out.println("add caseDefect result is:"+result);
		request.setAttribute("result", String.valueOf(result));
		return this.searchCaseDefect(mapping, form, request, response);		
	}

/**
 * ��ʼ��caseDefectҳ�棨��ҳ��ʾ��
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 * @author Dancy
 * @flag
 */	
	@SuppressWarnings("unchecked")
	public ActionForward searchCaseDefect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		Connection conn = null;
		Statement stmt = null;
		List<Map> Productlist = new ArrayList<Map>();
		List<Map> Componentlist = new ArrayList<Map>();
		List<Map> Milestonelist = new ArrayList<Map>();
		//FWJ 2013-05-10
		List<Map> Productlist_hide = new ArrayList<Map>();
		List<Map> Componentlist_hide = new ArrayList<Map>();
		List<Map> Milestonelist_hide = new ArrayList<Map>();
		
		List<CaseDefect> Caselist = new ArrayList<CaseDefect>();
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		//DefaultCaseDefect dcd=null;
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Productlist = AdministratorDao.getInstance().searchProductNames(stmt,1);
			Componentlist = AdministratorDao.getInstance().searchComponent(stmt,1);
			Milestonelist = AdministratorDao.getInstance().searchMilestoneList(stmt,1);
			//FWJ 2013-05-10
			Productlist_hide = AdministratorDao.getInstance().searchProductNames(stmt,0);
			Componentlist_hide = AdministratorDao.getInstance().searchComponent(stmt,0);
			Milestonelist_hide = AdministratorDao.getInstance().searchMilestoneList(stmt,0);
			
			Caselist = PODao.getInstance().getCaseList(stmt,u);
			//hanxiaoyu01 2013-02-16 �ҵ���ǰ�û�Case and Defect��Ĭ��ֵ
			//dcd=AdministratorDao.getInstance().findDefaultCaseDefect(conn, u.getUserId());
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
		request.getSession().setAttribute("list", Caselist);
		request.getSession().setAttribute("displaylist", Caselist);
		//hanxiaoyu01 2013-02-16
		//request.getSession().setAttribute("dcd", dcd);
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = Caselist.size() / ItemNumber;
		if (Caselist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ��
		int page = 0;
		request.getSession().setAttribute("page", page);
		request.getSession().setAttribute("Productlist", Productlist);
		request.getSession().setAttribute("Componentlist", Componentlist);
		request.getSession().setAttribute("Milestonelist", Milestonelist);
		
		request.getSession().setAttribute("Productlist_hide", Productlist_hide);
		request.getSession().setAttribute("Componentlist_hide", Componentlist_hide);
		request.getSession().setAttribute("Milestonelist_hide", Milestonelist_hide);
		return turnPage(mapping, form, request, response, ItemNumber, page);
		
	}
	
	
	/**
	 * @ ��ҳ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	
	@SuppressWarnings("unchecked")
	public ActionForward turnPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			int ItemNumber, int page) {
		POForm mainForm = (POForm) form;
		List<CaseDefect> list = (List<CaseDefect>) request.getSession()
				.getAttribute("list");
		List<CaseDefect> templist = new ArrayList<CaseDefect>();
		
		int maxpage = Integer.parseInt(request.getSession().getAttribute(
				"TotalPage").toString());
		if (page >= maxpage){	page = maxpage - 1;}
		else{	page = page-1;	}

		if (page < 0)
			page = 0;
		
		int firstitem = page * ItemNumber;

		int lastitem = (page + 1) * ItemNumber;
		if (lastitem > list.size())
			lastitem = list.size();
		

		for (int i = firstitem; i < lastitem; i++) {
			templist.add(list.get(i));
		}
		if (page >= maxpage)
			request.getSession().setAttribute("page", maxpage);
		else
			request.getSession().setAttribute("page", page+1);
		request.getSession().setAttribute("displaylist", templist);
		return mapping.findForward(mainForm.getOperPage());
	}

	public ActionForward turnPageJSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		POForm mainForm = (POForm) form;
		int page = Integer.parseInt(request.getParameter("page").toString());
		this.turnPage(mapping, form, request, response, 8, page);
		request.getSession().setAttribute("page", page);
		return mapping.findForward(mainForm.getOperPage());
	}
	//添加了addNewRecord方法 FWJ 2013-05-10
	public ActionForward addNewRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		POForm mainForm = (POForm) form;
		Connection conn = null;
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		DefaultCaseDefect dcd=null;
		try{
		conn = DataTools.getConnection(request);
		dcd=AdministratorDao.getInstance().findDefaultCaseDefect(conn, u.getUserId());
		request.setAttribute("oper", "new");
		}catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		request.getSession().setAttribute("dcd", dcd);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ɾ��caseDefect
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward deleteCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pid = Integer.parseInt(request.getParameter("id"));

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			@SuppressWarnings("unused")
			boolean result = PODao.getInstance().deleteCase(stmt, pid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		return searchDetails(mapping, form, request, response);
	}
	
	/**
	 * Ԥ�޸�caseDefect
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward editCase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		POForm mainForm = (POForm) form;
		CaseDefect cdResult = null ;
		int pid = Integer.parseInt(request.getParameter("id"));
//		List<Map> Productlist = new ArrayList<Map>();
//		List<Map> Componentlist = new ArrayList<Map>();
//		List<Map> Milestonelist = new ArrayList<Map>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			cdResult = PODao.getInstance().editCase(stmt, pid);
//			Productlist = AdministratorDao.getInstance().searchProductNames(stmt);
//			Componentlist = AdministratorDao.getInstance().searchComponent(stmt);
//			Milestonelist = AdministratorDao.getInstance().searchMilestoneList(stmt);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {

			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		
		request.setAttribute("cd", cdResult);
//		request.getSession().setAttribute("Productlist", Productlist);
//		request.getSession().setAttribute("Componentlist", Componentlist);
//		request.getSession().setAttribute("Milestonelist", Milestonelist);
		request.setAttribute("oper", "edit");
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ����caseDefect
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward saveCaseDefect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CaseDefect cd = new CaseDefect();
		int PID = Integer.parseInt((String) request.getParameter("id"));
		System.out.println("EDIT PID=="+PID);
		int productID = Integer.parseInt((String) request.getParameter("productid"));
		int componentID = Integer.parseInt((String) request.getParameter("componentid"));
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		int cases = Integer.parseInt((String) request.getParameter("cases"));
		int urgentdefect = Integer.parseInt((String) request.getParameter("urgentdefect"));
		int highdefect = Integer.parseInt((String) request.getParameter("highdefect"));
		int normaldefect = Integer.parseInt((String) request.getParameter("normaldefect"));
		int lowdefect = Integer.parseInt((String) request.getParameter("lowdefect"));
		int milestoneid = Integer.parseInt((String) request.getParameter("milestoneid"));
		
		cd.setId(PID);
		cd.setProductID(productID);
		cd.setComponentID(componentID);
		cd.setSDate(sdate);
		cd.setEDate(edate);
		cd.setCases(cases);
		cd.setUrgentdefect(urgentdefect);
		cd.setHighdefect(highdefect);
		cd.setNormaldefect(normaldefect);
		cd.setLowdefect(lowdefect);
		cd.setMilestoneid(milestoneid);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().saveCaseDefect(conn, cd, PID);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		
		request.setAttribute("result", String.valueOf(result));
		return this.searchDetails(mapping, form, request, response);
		
	}
	
	/**
	 * �޸�caseDefect����󷵻ص��ü�¼����ҳ ����ҳ��ʾcasedefect��Ϣ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */		
	@SuppressWarnings("unchecked")
	public ActionForward searchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		Connection conn = null;
		Statement stmt = null;
		List<CaseDefect> Caselist = new ArrayList<CaseDefect>();
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Caselist = PODao.getInstance().getCaseList(stmt,u);
			
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
		request.getSession().setAttribute("list", Caselist);
		request.getSession().setAttribute("displaylist", Caselist);
		
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = Caselist.size() / ItemNumber;
		if (Caselist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ�� ��ͬ��toinsert�ĵط�����סҳ�룬����󷵻ص�ǰ��¼����ҳ��
		int page = Integer.parseInt(request.getSession().getAttribute("pagec").toString());
		//int page = Integer.parseInt(request.getSession().getAttribute("pagec").toString());
		request.getSession().setAttribute("page", page);
		
		return turnPage(mapping, form, request, response, ItemNumber, page);
		
	}

	
	/**
	 * ��������ѯ�õ� caseDefect List
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */		
	@SuppressWarnings("unchecked")
	public ActionForward searchCaseDefectCD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		//POForm mainForm = (POForm) form;
		String pid = (String) request.getParameter("productid");
		int p = Integer.parseInt(pid);
		String cid = (String) request.getParameter("componentid");
		int c = Integer.parseInt(cid);
	
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		if("Start Date".equals(sdate)||sdate==null){
			sdate = "";
		}
		if("End Date".equals(edate)||edate==null){
			edate = "";
		}
	
		String mid = (String) request.getParameter("milestoneid");
		int flag = 0;
		if(!"".equals(sdate)&&!"".equals(edate))
		{
			flag=1;
		}
		
		int m = Integer.parseInt(mid);
		StringBuffer str = new StringBuffer();
		
		if(c!=-1&&m!=-1&&flag==1&&p!=-1)
		{
			str.append(" where productID ="+p+" and componentID="+c
					+" and milestoneid="+m+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c!=-1&&m!=-1&&flag==0&&p!=-1)
		{
			str.append(" where productID ="+p+" and componentID="+c
					+" and milestoneid="+m);
		}
		else if(c==-1&&m!=-1&&flag==1&&p!=-1)
		{
			str.append(" where productID ="+p
					+" and milestoneid="+m+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c==-1&&m!=-1&&flag==0&&p!=-1)
		{
			str.append(" where productID ="+p
					+" and milestoneid="+m);
		}
		else if(c!=-1&&m==-1&&flag==1&&p!=-1)
		{
			str.append(" where productID ="+p+" and componentID="+c
					+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c!=-1&&m==-1&&flag==0&&p!=-1)
		{
			str.append(" where productID ="+p+" and componentID="+c);
		}
		else if(c==-1&&m==-1&&flag==1&&p!=-1)
		{
			str.append(" where productID ="+p+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c==-1&&m==-1&&flag==0&&p!=-1)
		{
			str.append(" where productID ="+p);
		}
		
		if(c!=-1&&m!=-1&&flag==1&&p==-1)
		{
			str.append(" where componentID="+c
					+" and milestoneid="+m+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c!=-1&&m!=-1&&flag==0&&p==-1)
		{
			str.append(" where componentID="+c
					+" and milestoneid="+m);
		}
		else if(c==-1&&m!=-1&&flag==1&&p==-1)
		{
			str.append(" where milestoneid="+m+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c==-1&&m!=-1&&flag==0&&p==-1)
		{
			str.append(" where milestoneid="+m);
		}
		else if(c!=-1&&m==-1&&flag==1&&p==-1)
		{
			str.append(" where componentID="+c
					+" and sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		else if(c!=-1&&m==-1&&flag==0&&p==-1)
		{
			str.append(" where componentID="+c);
		}
		else if(c==-1&&m==-1&&flag==1&&p==-1)
		{
			str.append(" where sdate>='"+sdate+"' and edate<='"+edate+"'");
		}
		//hanxiaoyu01 2013-01-05 ����������ͬ�û��ж�����������֪���ǲ�������
//		System.out.println("str��ֵΪ��"+str);
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		String f = "";
		if(str.length()==0)
		{
			if(u.getGroupID()!=-1)
			{
				f = " where creator in (select username from user_table where workloadgroupId="+u.getGroupID()+") ";
			}
			if(u.getLevelID()>4)
			{
				f = " where creator='"+u.getUserName()+"'";
			}
		}
		else
		{
			if(u.getGroupID()!=-1)
			{
				f = " and creator in (select username from user_table where workloadgroupId="+u.getGroupID()+") ";
			}
			if(u.getLevelID()>4)
			{
				f = " and creator='"+u.getUserName()+"'";
			}
		}
	    str.append(f);
		str.append(" order by id desc;");
		
		//System.out.println("testSql::"+str);
		Connection conn = null;
		Statement stmt = null;
		List<CaseDefect> Caselist = new ArrayList<CaseDefect>();
		System.out.println("The sql in the case and defect was"+str);
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Caselist = PODao.getInstance().searchCase(stmt,str);
			
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

		request.getSession().setAttribute("list", Caselist);
		request.getSession().setAttribute("displaylist", Caselist);
		
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = Caselist.size() / ItemNumber;
		if (Caselist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ�� ��ͬ��toinsert�ĵط�����סҳ�룬����󷵻ص�ǰ��¼����ҳ��
		int page = Integer.parseInt(request.getSession().getAttribute("pagec").toString());
		//int page = Integer.parseInt(request.getSession().getAttribute("pagec").toString());
		request.getSession().setAttribute("page", page);
		request.getSession().setAttribute("pid", pid);
		request.getSession().setAttribute("cid", cid);
		request.getSession().setAttribute("mid", mid);
		request.getSession().setAttribute("sdate", sdate);
		request.getSession().setAttribute("edate", edate);
		
		return turnPage(mapping, form, request, response, ItemNumber, page);
		
	}	
	
	
/************************Set Name List (Component)***************************/
	/**
	 * ���omponent
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward addComponentName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String componentName = (String) request.getParameter("ctname");
		String fwsw = (String) request.getParameter("fwsw10");
		//System.out.println("ctname="+componentName+" fwsw="+fwsw);
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().addComponentName(conn, componentName, Integer.parseInt(fwsw));
			//System.out.println("add new component name:"+componentName+" "+result);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("operate", "add");
		request.setAttribute("Added", "componentName");
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * �޸�omponent
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward modifyComponentName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String componentid = (String) request.getParameter("ctid");
		String componentname = (String) request.getParameter("ct"+componentid+"I");

		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().modifyComponentName(conn, componentname, Integer.parseInt(componentid));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ct"+componentid);
		return mapping.findForward(mainForm.getOperPage());
	}
	

	/**
	 * �h��omponent
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward removeComponentName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String componentid = (String) request.getParameter("ctid");
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().removeComponentName(conn, Integer.parseInt(componentid));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		request.setAttribute("result", result);
		request.setAttribute("id", "ct"+componentid);
		request.setAttribute("operate", "remove");
		return mapping.findForward(mainForm.getOperPage());
	}
	
	
	
	public ActionForward backPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		POForm mainForm = (POForm) form;
		return mapping.findForward(mainForm.getOperPage());
	}
	

	/**
	 * new project��ʱ��ͨ��Componentid �� ProductName �������ڵ�PO
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author longzhe
	 * @return 
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public Object searchbyComponentAndProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String componentid = (String) request.getParameter("componentid");
		String productname = (String) request.getParameter("productname");
		System.out.println("componentid = " + componentid + ", productname = " + productname);
		//���ﵽ��ݿ���ȥ����Ӧ��po�б�
		Connection conn = null;
		Statement stmt = null;
		List<Map> polist = new ArrayList<Map>();
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			polist = PODao.getInstance().searchbyComponentAndProduct(stmt, Integer.parseInt(componentid), productname);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != stmt) {
				stmt.close();
			}
			if (null != conn) {
				conn.close();
			}
		}
		JSONArray jsonArray =JSONArray.fromObject(polist);
		
		PrintWriter out = response.getWriter();
		out.print(jsonArray.toString());
		out.close();
		
		return null;
	}


/******************************PO Assignment*************************************/
	
	/**
	 * ȡ�����е�productList,componentList,POlist(��ʼ�� PO Assignment)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy 2011-12-08
	 * @flag
	 */
	@SuppressWarnings("unchecked")
	public ActionForward searchPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		
		Connection conn = null;
		Statement stmt = null;
		List<Map> Productlist = new ArrayList<Map>();
		List<Map> Componentlist = new ArrayList<Map>();
		List<ProjectOrder> POlist = new ArrayList<ProjectOrder>();		
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			Productlist = AdministratorDao.getInstance().searchProductNames(stmt,1);//FWJ 05-10
			Componentlist = AdministratorDao.getInstance().searchComponent(stmt,1);//FWJ 05-10
			POlist = PODao.getInstance().getPOlist(stmt);
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
		request.getSession().setAttribute("Productlist", Productlist);
		request.getSession().setAttribute("Componentlist", Componentlist);
		request.getSession().setAttribute("polist", POlist);
		return mapping.findForward(mainForm.getOperPage());
		
	}

	/**
	 * ��component + product���ponumber
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  0=ʧ�ܣ�1=�ɹ���-1=��¼�Ѵ���
	 * @throws Exception
	 * @author Dancy 2011-12-08
	 * @comment used by dancy 2012-01-18
	 */
	
	public ActionForward addPOlist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	throws Exception {

		int pid = Integer.parseInt((String) request.getParameter("poid"));
		int productid= Integer.parseInt((String) request.getParameter("productid"));
		int componentid= Integer.parseInt((String) request.getParameter("componentid"));
		Connection conn = null;
		boolean result = false;
		
		try
		{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().addPONumber(conn, pid,productid,componentid);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
		{
			if(null != conn)
				conn.close();
		}
		response.getWriter().write(String.valueOf(result));
		
		return null;
	}

	
	public ActionForward deletePONumberOfPCP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String pno = request.getParameter("pno");
		String productid = request.getParameter("productid");
		String componentid = request.getParameter("componentid");
		
		boolean result = false;
		Connection conn = null;
		Statement stmt = null;
		try{
		conn = DataTools.getConnection(request);
		stmt = conn.createStatement();
		result = PODao.getInstance().deletePONumberOfPCP(stmt,Integer.parseInt(productid),Integer.parseInt(componentid),Integer.parseInt(pno));
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
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
		response.getWriter().write(String.valueOf(result));
		return null;
	}
	
	/**
	 * ��ѯ���component + product��Ӧ��ponumber list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 * @author Dancy 2011-12-09
	 * @comment
	 */
	
	@SuppressWarnings("unchecked")
	public Object searchPCP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		
		String productid = (String) request.getParameter("product");
		String componentid =(String) request.getParameter("component");
		
		Connection conn = null;
		Statement stmt = null;
		List<Map> PONumberlist = null;		
		try
		{
			conn = DataTools.getConnection(request);
			stmt=conn.createStatement();
			PONumberlist = PODao.getInstance().getPONumberlist(stmt,Integer.parseInt(productid),Integer.parseInt(componentid));
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
		
		JSONArray jsonArray =JSONArray.fromObject(PONumberlist);
		PrintWriter out = response.getWriter();
		out.print(jsonArray.toString());
		out.close();
		return null;
	}
	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  Object searchPCPDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		String poid = request.getParameter("poid");
		String lastRecordDate = "";
		List<Map> pcplist = null;
		List<NonLaborCost> nlist = new ArrayList<NonLaborCost>();
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			nlist = PODao.getInstance().searchNonLaborByPOID(stmt, Integer.parseInt(poid));
			if(nlist.size()>0)
				lastRecordDate = nlist.get(nlist.size()-1).getNdate();
			pcplist = PODao.getInstance().searchPCPDetails(stmt,Integer.parseInt(poid),lastRecordDate);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}finally
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
		JSONArray jsonArray =JSONArray.fromObject(pcplist);
		PrintWriter out = response.getWriter();
		out.print(jsonArray.toString());
		out.close();
		
		return null;
	}
/*****************************PO List*************************************/	
/**
 * @function ��ʼ��PO List
 * @author dancy
 * @param mapping,form,request,response
 * @return
 * @throws Exception
 */
	@SuppressWarnings("unchecked")
	public ActionForward showPOList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		List<Map> managerList = new ArrayList<Map>();
		Connection conn = null;
		Statement stmt  = null;
		List<ProjectOrder> plist = new ArrayList<ProjectOrder>();
		List<ProjectOrder> POlist = new ArrayList<ProjectOrder>();
		try{
			conn = DataTools.getConnection(request);
			stmt=conn.createStatement();
			managerList = AdministratorDao.getInstance().searchPOManager(stmt,1);
			//添加了对polist的获取
			POlist = PODao.getInstance().getPOlist(stmt);
			plist = PODao.getInstance().getPODetailsList(stmt);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
			
		}finally{
			if (stmt != null)
			{
				stmt.close();
			}
			if (conn != null)
			{
				conn.close();
			}
		}
		//设置不同的session，在 当前页面搜索的值 与 更新po的值不同，以免混淆
		request.getSession().setAttribute("list_po", plist);
		request.getSession().setAttribute("list", plist);
		request.getSession().setAttribute("displaylist", plist);
		//添加了对polist的session设置
		request.getSession().setAttribute("polist", POlist);
		
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = plist.size() / ItemNumber;
		if (plist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ��
		int page = 0;
		
		request.getSession().setAttribute("managerList", managerList);
		//防止searchwithfilter方法中的page取值为空 FWJ 2013-05-06
		String pagefrom=request.getParameter("pagefrom");
		if(pagefrom!=null&&pagefrom.equals("checker01")){
			System.out.println("runs");
			request.getSession().setAttribute("pagex", page);
//			request.getSession().setAttribute("page", page);
		}else{
			page=Integer.parseInt(request.getSession().getAttribute("pagex").toString());
			System.out.println("The page in the showPOList="+page);
		}
		//点击back之后，移除session中的筛选框的信息。 FWJ on 2013-04-22
		
		String backpage = request.getParameter("backpage");
	//	request.setAttribute("backpage",backpage);

		if (backpage!=null&&(backpage.equals("Invoice_list")||backpage.equals("monthlyproject_list")))
		{
			request.getSession().removeAttribute("pofilters");
			request.getSession().removeAttribute("yearfilters");
			request.getSession().removeAttribute("monthfilters");
			request.getSession().removeAttribute("categoryfilters");
			request.getSession().removeAttribute("clientfilters");
			request.getSession().removeAttribute("wbsfilters");
			
			//Added by FWJ 2013-06-18
			request.getSession().removeAttribute("monthprojectfilters");
			request.getSession().removeAttribute("locationfilters");
			request.getSession().removeAttribute("bcategoryfilters");
			
//			this.searchWithFilter(mapping, form, request, response);
		}
		
		this.searchWithFilter(mapping, form, request, response);
		return turnPOPage(mapping, form, request, response, ItemNumber, page);
		
	}

	/**
	 * @ ��ҳ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 * @param plist 
	 */	
	
	@SuppressWarnings("unchecked")
	public ActionForward turnPOPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			int ItemNumber, int page) {
		POForm mainForm = (POForm) form;		
		List<ProjectOrder> list = (List<ProjectOrder>) request.getSession()
		.getAttribute("list_po");
		List<ProjectOrder> templist = new ArrayList<ProjectOrder>();
		
		// �ж�ҳ�뷶Χ ���������ҳ�룬�͵����һҳ
		int maxpage = Integer.parseInt(request.getSession().getAttribute(
				"TotalPage").toString());
		if (page >= maxpage){	page = maxpage - 1;}
		else{	page = page-1;	}

		// �ж�ҳ�뷶Χ ���С��0���͵���0
		if (page < 0)
			page = 0;
		
		// ��һ����¼
		int firstitem = page * ItemNumber;

		// ���һ����¼
		int lastitem = (page + 1) * ItemNumber;
		// �ж����һ����¼�Ƿ񳬹�����
		if (lastitem > list.size())
			lastitem = list.size();
		

		for (int i = firstitem; i < lastitem; i++) {
			templist.add(list.get(i));
		}
		if (page >= maxpage)
		{
			request.getSession().setAttribute("page", maxpage);
		}else
		{
			request.getSession().setAttribute("page", page+1);
		}
		System.out.println("The page in the turnpopage ="+request.getSession().getAttribute("page"));
		request.getSession().setAttribute("displaylist", templist);
		return mapping.findForward(mainForm.getOperPage());
	}

	public ActionForward turnPOPageJSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		POForm mainForm = (POForm) form;
		int page = Integer.parseInt(request.getParameter("page").toString());
		this.turnPOPage(mapping, form, request, response, 8, page);
		request.getSession().setAttribute("pagex", page);
		request.getSession().setAttribute("page", page);
		System.out.println("The page in the turnPOPageJSP="+request.getSession().getAttribute("page"));
		return mapping.findForward(mainForm.getOperPage());
	}	

	
	/**
	 * @function ɾ��һ��PO��¼
	 * @author dancy 2012-12-15
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward deletePO(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String poid = request.getParameter("poid");
//		System.out.println("TO DEL :"+poid);
		Connection conn = null;
		Statement stmt = null;
		@SuppressWarnings("unused")
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			result = PODao.getInstance().deletePO(stmt,Integer.parseInt(poid));
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			
		}		
		return this.showPOList(mapping, form, request, response);
	}
	
	/**
	 * ʵ�ֶ�������POStatus,lockStatus,CostLocationID,POManagerid����ϲ�ѯ
	 * @author dancy 2012-12-20
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String[] status = (String[]) request.getParameterValues("status");
		String[] lock = (String[]) request.getParameterValues("lock");
		String[] manager = (String[]) request.getParameterValues("manager");
		String ml = (String) request.getParameter("mlist");
		int j = 0;
		StringBuffer str = new StringBuffer();
		if(lock.length==3||manager.length==Integer.parseInt(ml))
		{
			j = 1;
		}
		else
		{
			j = 0;
		}
		for(int i=j;i<status.length-1;i++)
		{
			str.append("'"+status[i]+"',");
		}
		str.append("'"+status[status.length-1]+"'");
		
		
		StringBuffer ltr = new StringBuffer();
		for(int i=j;i<lock.length-1;i++)
		{
			ltr.append("'"+lock[i]+"',");
		}
		ltr.append("'"+lock[lock.length-1]+"'");
		
		StringBuffer mtr = new StringBuffer();
		for(int i=j;i<manager.length-1;i++)
		{
			mtr.append(manager[i]+",");
		}
		mtr.append(manager[manager.length-1]);		
		
		StringBuffer sqlStr = new StringBuffer();

		sqlStr.append(" where POStatus in ("+str+") and lockStatus in ("+ltr+") "
				+"and POManagerid in ("+mtr+") order by POID desc;");
		Connection conn = null;
		Statement stmt = null;
		List<ProjectOrder> polist = new ArrayList<ProjectOrder>();
//		System.out.println("sql = "+sqlStr);
		try
		{
			conn = DataTools.getConnection(request);
			stmt=conn.createStatement();
			polist = PODao.getInstance().search(stmt, sqlStr);
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
		request.getSession().setAttribute("list", polist);
		request.getSession().setAttribute("displaylist", polist);
		
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = polist.size() / ItemNumber;
		if (polist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ��
		int page = 0;
		request.getSession().setAttribute("page", page);
		return turnPOPage(mapping, form, request, response, ItemNumber, page);
	}	
	
	
	
	
	
	
/*****************************PO EDIT**************************/
	/**
	 * @function ���޸�PO
	 * @author dancy 2012-12-15
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public ActionForward toEditPO(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String poid = request.getParameter("poid");
		System.out.println("TO EDIT :"+poid);
		ProjectOrder po = null;
		List<Map> codeList = new ArrayList<Map>();
		List<Map> cycleList = new ArrayList<Map>();
		List<StatusChangeLog> loglist = new ArrayList<StatusChangeLog>();
		Connection conn = null;
		Statement stmt = null;
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();	
			
			codeList = AdministratorDao.getInstance().searchLocationCode(stmt);
			cycleList = AdministratorDao.getInstance().searchBillCycle(stmt);
			
			po = PODao.getInstance().searchPOByID(stmt,Integer.parseInt(poid));
			loglist = PODao.getInstance().searchStatusLog(conn,Integer.parseInt(poid));
			
			if(null==po.getNotStartStatusDate())
			{
				po.setNotStartStatusDate("");
			}
			if(null==po.getOpenStatusDate())
			{
				po.setOpenStatusDate("");
			}
			if(null==po.getCloseStatusDate())
			{
				po.setCloseStatusDate("");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(null!=stmt){
				stmt.close();
			}
		}
		po.setPOID(Integer.parseInt(poid));
		request.setAttribute("poid", poid);
		request.setAttribute("po", po);
		request.setAttribute("loglist", loglist);
		request.setAttribute("codeList", codeList);
		request.setAttribute("cycleList", cycleList);
		return this.showPOList(mapping, form, request, response);
	}
	
	/**
	 * @function �޸�PO
	 * @author dancy 2012-12-19���
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePOInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		String poid = (String) request.getParameter("poid");
		String lockV = (String) request.getParameter("lockStatus");
		String ps = (String) request.getParameter("poStatus");//�ı���״̬
		String date0 = (String) request.getParameter("date0");
		if("".equals(date0))
		{
			date0 = null;
			
		}
		String date1 = (String) request.getParameter("date1");
		if("".equals(date1))
		{
			date1 = null;
			
		}
		String date2 = (String) request.getParameter("date2");
		if("".equals(date2))
		{
			date2 = null;
			
		}
		String amount = (String) request.getParameter("amount");
		amount=amount.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		//System.out.println("amount:"+amount);
		String alertBalance = (String) request.getParameter("alertBalance");
		alertBalance=alertBalance.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		//System.out.println("alertBalance:"+alertBalance);
		String closeBalance = (String) request.getParameter("closeBalance");
		closeBalance=closeBalance.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		//System.out.println("closeBalance:"+closeBalance);
		String email = (String) request.getParameter("email");
		if("".equals(email)){
			email = null;
		}

		String poUsed = (String) request.getParameter("poUsed");
		if("".equals(poUsed)){
			poUsed  = "0.0";
		}
		poUsed=poUsed.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		System.out.println("poUsed:"+poUsed);
//		String poBalance = (String) request.getParameter("poBalance");
		String poBalance = (String) request.getParameter("pb");
		if("".equals(poBalance)){
			poBalance  = "0.0";
		}
		poBalance=poBalance.replaceAll(",", "");//FWJ 替换数据中的逗号 2013-05-21
		System.out.println("poBalance:"+poBalance);
		String startDate = (String) request.getParameter("startDate");
		String endDate = (String) request.getParameter("endDate");
		String managerID = (String) request.getParameter("manager");
		String desr = (String) request.getParameter("description");
		if("".equals(desr))
		{
			desr  = null;
		}
		String comment = (String) request.getParameter("comment");
		if("null".equals(comment)||"".equals(comment)){
			comment = null;
		}
//		String wbs = (String) request.getParameter("wbsNumber");
		String quo = (String) request.getParameter("quoNumber");
//		String cycleID = (String) request.getParameter("billCycle");
		//��ȡ��ǰ�û�id
		String uid = (String) request.getParameter("uid");
		//ȡ��״̬�ı�ǰ������
		String dt = (String) request.getParameter("dt");
		if("".equals(dt)||"null".equals(dt)){
			dt = null;
		}
		//ȡ��״̬�ı�������
		String ds = (String) request.getParameter("ds");
		if("".equals(ds)){
			ds = null;
		}
		//�ı�ǰ��״̬
		String cf = (String) request.getParameter("cf");
		
		System.out.println("cf:"+cf+"//ps:"+ps);
		//System.out.println("dt:"+dt+"//ds:"+ds);
		
		ProjectOrder po = new ProjectOrder();
		
		po.setLock(lockV);
		po.setPOStatus(ps);
		
		po.setNotStartStatusDate(date0);
		po.setOpenStatusDate(date1);
		po.setCloseStatusDate(date2);
				
		po.setPOAmount(Double.parseDouble(amount));
		po.setAlertBalance(Double.parseDouble(alertBalance));
		po.setCloseBalance(Double.parseDouble(closeBalance));
		po.setEmail(email);
		po.setPoUsed(Double.parseDouble(poUsed));
		po.setPoBalance(Double.parseDouble(poBalance));
		System.out.println("PO remainning in the PO edit is="+poBalance);
		po.setPOStartDate(startDate);
		po.setPOEndDate(endDate);
		po.setPOManagerid(Integer.parseInt(managerID));
		po.setDescription(desr);
		po.setComment(comment);
//		po.setWBSNumber(wbs);
		po.setBYSQuatationNumber(quo);
//		po.setBillCycleid(Integer.parseInt(cycleID));
	
		StatusChangeLog scl = new StatusChangeLog();
		scl.setPoNum(Integer.parseInt(poid));
		scl.setStatusFrom(cf);
		scl.setStatusTo(ps);
		scl.setChangeDateFrom(dt);
		scl.setChangeDateTo(ds);
		scl.setChangeUserID(Integer.parseInt(uid));
		
		boolean result = false;
		Connection conn = null;
		conn = DataTools.getConnection(request);

		try{
			
			result = PODao.getInstance().savePO(conn, po, Integer.parseInt(poid));
			
			if((result&&!cf.equals(ps))||(result&&!String.valueOf(dt).equals(String.valueOf(ds))))
			{
				PODao.getInstance().addStatusLog(conn,scl);		
			}
			
			if (result) 
			{
				System.out.println("to send email when save po true!");
				Thread thread=new SendThread3(Integer.parseInt(poid));
				thread.start();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		request.setAttribute("result", String.valueOf(result));
//		return this.toEditPO(mapping, form, request, response);//���ؼ����޸�ҳ��
		return this.showPOList(mapping, form, request, response); //changed by dancy 0529
	}	
/********************************PO ADD*************************************/
	/**
	 * @function Ԥ���PO����ȡLocationCode,POManager,BillCycle�ֵ�?
	 * @author dancy
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toAddPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		//新添加的对operation的值的获取，FWJ 2013-04-16
		String operate = (String) request.getParameter("oper");
		System.out.println("operate:"+operate);
		
		POForm mainForm = (POForm) form;
		Connection conn = null;
		Statement stmt = null;
		List<Map> managerList = new ArrayList<Map>();
		List<Map> cycleList = new ArrayList<Map>();
		List<Map> wbsList = new ArrayList<Map>();
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			managerList = AdministratorDao.getInstance().searchPOManager(stmt,0);
			cycleList = AdministratorDao.getInstance().searchBillCycle(stmt);
			wbsList = AdministratorDao.getInstance().searchWBS(stmt);
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
		
		request.setAttribute("managerList", managerList);
		request.setAttribute("cycleList", cycleList);
		request.setAttribute("wbsList", wbsList);
		
		//添加了对operate值的判断并决定所做的操作 FWJ 2013-04-16
		if(null == operate)
			return mapping.findForward(mainForm.getOperPage());
		else if("copy".equals(operate))
		{
			request.setAttribute("copy", "copy");
			return this.toEditPO(mapping, form, request, response);
		}
		return null;
//		return mapping.findForward(mainForm.getOperPage());
		
	}
	/**
	 * @function ���PO  FWJ 2013-05-21更改
	 * @author dancy
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward addPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		//POForm mainForm = (POForm) form;
		String pno = (String) request.getParameter("pno");
		String lockV = (String) request.getParameter("lockStatus");
		String ps = (String) request.getParameter("poStatus");
		String date0 = (String) request.getParameter("date0");
		if("".equals(date0)){
			date0 = null;
			
		}
		String date1 = (String) request.getParameter("date1");
		if("".equals(date1)){
			date1 = null;
			
		}
		String date2 = (String) request.getParameter("date2");
		if("".equals(date2)){
			date2 = null;
			
		}
		String amount = (String) request.getParameter("amount");
		amount=amount.replaceAll(",", "");//替换到逗号 FWJ 2013-05-21
		//System.out.println("amount:"+amount);
		String alertBalance = (String) request.getParameter("alertBalance");
		alertBalance=alertBalance.replaceAll(",", "");//替换到逗号 FWJ 2013-05-21
		//System.out.println("alertBalance:"+alertBalance);
		String closeBalance = (String) request.getParameter("closeBalance");
		//System.out.println("closeBalance:"+closeBalance);
		closeBalance=closeBalance.replaceAll(",", "");//替换到逗号 FWJ 2013-05-21
		String email = (String) request.getParameter("email");
		String startDate = (String) request.getParameter("startDate");
		String endDate = (String) request.getParameter("endDate");
		String managerID = (String) request.getParameter("manager");
		String desr = (String) request.getParameter("description");
		if("".equals(desr)){
			desr  = null;
		}
		String comment = (String) request.getParameter("comment");
		String quo = (String) request.getParameter("quoNumber");
//		String cycleID = (String) request.getParameter("billCycle");
		
		ProjectOrder po = new ProjectOrder();
		po.setPONumber(pno);
		po.setLock(lockV);
		po.setPOStatus(ps);
		
		po.setNotStartStatusDate(date0);
		po.setOpenStatusDate(date1);
		po.setCloseStatusDate(date2);
				
		po.setPOAmount(Double.parseDouble(amount));
		po.setAlertBalance(Double.parseDouble(alertBalance));
		po.setCloseBalance(Double.parseDouble(closeBalance));
		po.setEmail(email);
		po.setPoUsed(Double.parseDouble("0"));
//		po.setPoBalance(Double.parseDouble("0"));
		//修改Remaining Balance为amount
		po.setPoBalance(Double.parseDouble(amount));
		po.setPOStartDate(startDate);
		po.setPOEndDate(endDate);
		po.setPOManagerid(Integer.parseInt(managerID));
		po.setDescription(desr);
		po.setComment(comment);
//		po.setWBSNumber(wbs);
		po.setBYSQuatationNumber(quo);
//		po.setBillCycleid(Integer.parseInt(cycleID));
		
		//12-21
		String uid = (String) request.getParameter("uid");
		String dateFrom = null;
		if(null!=po.getNotStartStatusDate())
		{
			dateFrom = po.getNotStartStatusDate();
		}
		else if(null!=po.getOpenStatusDate())
		{
			dateFrom = po.getOpenStatusDate();
		}
		else if(null!=po.getCloseStatusDate())
		{
			dateFrom = po.getCloseStatusDate();
		}
		StatusChangeLog scl = new StatusChangeLog();
		
		scl.setStatusFrom(ps);
		scl.setStatusTo(null);
		scl.setChangeDateFrom(dateFrom);
		scl.setChangeDateTo(null);
		scl.setChangeUserID(Integer.parseInt(uid));
		
		//@SuppressWarnings("unused")
		int result = -1;
		
		Connection conn = null;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().addPO(conn, po);
			int pid  =PODao.getInstance().searchPOID(conn,pno);
			//System.out.println("poid:"+pid);
			//System.out.println("result:"+result);
			if(result==1)
			{
				scl.setPoNum(pid);
				PODao.getInstance().addStatusLog(conn, scl);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		return this.showPOList(mapping, form, request, response);//modified by dancy 2012-01-06
	}


	/**
	 * @function ��set name list����� locationcodes
	 * @author longzhe
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */	
	@SuppressWarnings("unchecked")
	public Object searchLocationcode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//���ﵽ��ݿ���ȥ����Ӧ��po�б�
		List<Map> locationcodes = null;
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			locationcodes = PODao.getInstance().searchLocationcode(stmt);
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(null!=stmt){
				stmt.close();
			}
			if(null!=conn){
				conn.close();
			}
		}
		//���ﹹ�췵��ֵ
		JSONArray jsonArray =JSONArray.fromObject(locationcodes);
		//System.out.println(jsonArray);
		//request.getSession().setAttribute("jsonArray", jsonArray.toString()+";");
		System.out.println(jsonArray.toString());
		PrintWriter out = response.getWriter();
		out.print(jsonArray.toString());
		out.close();
		
		return null;
	}

/**********************************************************************/

/**	
 * ��ʼ��NonLaborCost list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 * @flag
	 */	
	public ActionForward searchNonLabor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		Connection conn = null;
		Statement stmt = null;

		List<ProjectOrder> plist = new ArrayList<ProjectOrder>();
		List<Groups> glist = new ArrayList<Groups>();
		List<NonLaborCost> nlist = new ArrayList<NonLaborCost>();
		
		//2012-12-26 by dancy �����û�������
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			plist = PODao.getInstance().loadPONumberList(stmt,u);
			glist = PODao.getInstance().loadGroupList(stmt,u);
			nlist = PODao.getInstance().getNonLaborCost(stmt,u);

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
		
		request.getSession().setAttribute("list", nlist);
		request.getSession().setAttribute("nlist", nlist);
		
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = nlist.size() / ItemNumber;
		if (nlist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ��
		int page = 0;
		request.getSession().setAttribute("page", page);
		request.getSession().setAttribute("plist", plist);
		request.getSession().setAttribute("glist", glist);
		return turnLPage(mapping, form, request, response, ItemNumber, page);
		
	}
	
	/**
	 * @ ��ҳ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	
	@SuppressWarnings("unchecked")
	public ActionForward turnLPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			int ItemNumber, int page) {
		POForm mainForm = (POForm) form;		
		List<NonLaborCost> list = (List<NonLaborCost>) request.getSession()
		.getAttribute("list");
		List<NonLaborCost> templist = new ArrayList<NonLaborCost>();
		
		// �ж�ҳ�뷶Χ ���������ҳ�룬�͵����һҳ
		int maxpage = Integer.parseInt(request.getSession().getAttribute(
				"TotalPage").toString());
		if (page >= maxpage)
		{	page = maxpage - 1;}
		else{	page = page-1;	}

		// �ж�ҳ�뷶Χ ���С��0���͵���0
		if (page < 0)
			page = 0;
		
		// ��һ����¼
		int firstitem = page * ItemNumber;

		// ���һ����¼
		int lastitem = (page + 1) * ItemNumber;
		// �ж����һ����¼�Ƿ񳬹�����
		if (lastitem > list.size())
			lastitem = list.size();
		

		for (int i = firstitem; i < lastitem; i++) {
			templist.add(list.get(i));
		}
		if (page >= maxpage)
			request.getSession().setAttribute("page", maxpage);
		else
			request.getSession().setAttribute("page", page);
		request.getSession().setAttribute("nlist", templist);
		return mapping.findForward(mainForm.getOperPage());
	}

	public ActionForward turnLPageJSP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		POForm mainForm = (POForm) form;
		int page = Integer.parseInt(request.getParameter("page").toString());
		this.turnLPage(mapping, form, request, response, 8, page);
		request.getSession().setAttribute("page", page);
		return mapping.findForward(mainForm.getOperPage());
	}	

	
/**
 * ��һ�������߶�������ϲ�ѯ
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward searchNonLaborCost(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		String data = (String) request.getParameter("data");
		if(data==null)
		{
			data = "1";
		}
		String groupId = (String) request.getParameter("group");
		String pno = (String) request.getParameter("pno");
		
		System.out.println("test data value::"+data);
		System.out.println("test groupId value::"+groupId);
		System.out.println("test pno value::"+pno);
		
		StringBuffer str = new StringBuffer();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);//ȡ�õ�ǰ���ڵ�ǰ������
	
		if("-1".equals(groupId)&&!"all".equals(pno)&&"1".equals(data))
		{
			str.append(" where n.PurchaseOrder='"+pno+"'");
		}
		else if(!"-1".equals(groupId)&&!"all".equals(pno)&&"1".equals(data))
		{
			str.append(" where n.GroupID="+groupId+" and n.PurchaseOrder='"+pno+"'");
		}
		else if(!"-1".equals(groupId)&&"all".equals(pno)&&"1".equals(data))
		{
			str.append(" where n.GroupID="+groupId);
		}

		else if("-1".equals(groupId)&&!"all".equals(pno)&&"0".equals(data))
		{
			str.append(" where n.PurchaseOrder='"+pno+"' and n.Date >='"+sdf.format(cal.getTime())+"' and n.Date<=curdate()");
		}
		else if(!"-1".equals(groupId)&&!"all".equals(pno)&&"0".equals(data))
		{
			str.append(" where n.GroupID="+groupId+" and n.PurchaseOrder='"+pno+"' and n.Date >='"+sdf.format(cal.getTime())+"' and n.Date<=curdate()");
		}
		else if(!"-1".equals(groupId)&&"all".equals(pno)&&"0".equals(data))
		{
			str.append(" where n.GroupID="+groupId+" and n.Date >='"+sdf.format(cal.getTime())+"' and n.Date<=curdate()");
		}
		else if("-1".equals(groupId)&&"all".equals(pno)&&"0".equals(data))
		{
			str.append(" where n.Date >='"+sdf.format(cal.getTime())+"' and n.Date<=curdate()");
		}
		//hanxiaoyu01 2013-01-05   ��ݲ�ͬ�û����ж�������Դ��PODao��getNonLaborCost������
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		String flag = "";
		
		if(u.getGroupID()!=-1)
		{
			if(str.length()==0)
			{
				flag = " where n.GroupID="+u.getGroupID();
			}
			else
			{
				flag = " and n.GroupID="+u.getGroupID();
			}
				
		}
		
		if(u.getLevelID()>4)
		{
			flag = flag +" and n.creator='"+u.getUserName()+"'";
		}
		str.append(flag);				
				
		str.append(" order by n.NonLaborCostID desc");
//		System.out.println("test sql::"+str);
		Connection conn = null;
		Statement stmt = null;
		List<NonLaborCost> nlist = new ArrayList<NonLaborCost>();		
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			nlist = PODao.getInstance().searchNonLaborCost(stmt,str);
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

		request.getSession().setAttribute("list", nlist);
		request.getSession().setAttribute("nlist", nlist);
		
		// ÿҳ��¼����
		int ItemNumber = 8;
		// ��ҳ��
		int TotalPage = 0;
		TotalPage = nlist.size() / ItemNumber;
		if (nlist.size() % ItemNumber > 0)
			TotalPage++;
		request.getSession().setAttribute("TotalPage", TotalPage);
		// ��ǰҳ�� ��ͬ��toinsert�ĵط�����סҳ�룬����󷵻ص�ǰ��¼����ҳ��
		int page = Integer.parseInt(request.getSession().getAttribute("pagec").toString());
		request.getSession().setAttribute("page", page);
		request.setAttribute("data", data);
		request.setAttribute("groupId", groupId);
		request.setAttribute("pno", pno);
		return turnLPage(mapping, form, request, response, ItemNumber, page);
		
	}
	/**
	 * ɾ��һ��nonLaborCost��¼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward deleteNonLaborCost(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		String nid = request.getParameter("nid");
		//System.out.println("TO DEL :"+nid);
		Connection conn = null;
		Statement stmt = null;
		@SuppressWarnings("unused")
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			result = PODao.getInstance().deleteNonLaborCost(stmt,Integer.parseInt(nid));
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			
		}		
		return this.searchNonLabor(mapping, form, request, response);
	}
	/**
	 * @function Ϊ�½�NonLaborȡ�ò���
	 * @author longzhe
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */
	
	 /**
	 *@author hanxiaoyu01
	 *2012-11-08
	 *ȥ����Project,ActiveType,SkillLevelName,TestAsset,Descriptionͬʱ����Comments��Quantity,Product,
	 *Component
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toinsertNonLabor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		POForm mainForm = (POForm) form;
		Connection conn = null;
		Statement stmt = null;
		
		String operate = (String) request.getParameter("oper");
		System.out.println("operate:"+operate);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		
		Vector<List> v = new Vector<List>();

		List<Groups> glist = new ArrayList<Groups>();
		List<Map> locationslist = new ArrayList<Map>();
		List<Map>  productlist=new ArrayList<Map>();
		List<Map>  componentlist=new ArrayList<Map>();
		List<Map>  wbslist=new ArrayList<Map>();
		try
		{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			glist = PODao.getInstance().loadGroupList(stmt,user);
			locationslist = PODao.getInstance().loadLocationsList(stmt);
			productlist=PODao.getInstance().loadProductList(stmt);
			componentlist=PODao.getInstance().loadComponentList(stmt);
			wbslist = AdministratorDao.getInstance().searchWBS(stmt);
			v.add(glist);
			v.add(locationslist);
			v.add(productlist);
			v.add(componentlist);
			v.add(wbslist);
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
		
		request.setAttribute("List", v);
		if(null == operate)
			return mapping.findForward(mainForm.getOperPage());
		else if("modify".equals(operate))
			return this.searchNonLaborByID(mapping, form, request, response);
		else if("copy".equals(operate))
		{
			request.setAttribute("copy", "copy");
			return this.searchNonLaborByID(mapping, form, request, response);
		}
		return null;
	}
	/**
	 * @function �½�NonLabor
	 * @author longzhe
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */	
	
	/**
	 * @author hanxiaoyu01
	 * 2012-11-09
	 *ȥ����Project,ActiveType,SkillLevelName,TestAsset,Descriptionͬʱ����Comments��Quantity,Product,
	 *Component
	 * 
	 */
	public ActionForward saveNonLabor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		POForm mainForm = (POForm) form;
		
		String date = (String) request.getParameter("date");
		String groupID = (String) request.getParameter("group");
		String poid = (String) request.getParameter("poid");
		String locale = (String) request.getParameter("locale");
		String cost = (String) request.getParameter("cost");
		cost=cost.replaceAll(",", "");//FWJ 转换格式 2013-05-21
		String note = (String) request.getParameter("notes");
		String comments=(String) request.getParameter("comments");
		String product=(String) request.getParameter("product");
		String quantity=(String) request.getParameter("quantity");
		String componentId=(String)request.getParameter("component");
		String wbs =(String)request.getParameter("wbs");
		
		SysUser u = (SysUser) request.getSession().getAttribute("user");
		
		NonLaborCost nonlaborcost = new NonLaborCost();
		nonlaborcost.setNdate(date);
		nonlaborcost.setGroupNameID(Integer.parseInt(groupID));
		nonlaborcost.setPOID(Integer.parseInt(poid));
		nonlaborcost.setLocaleID(Integer.parseInt(locale));
		nonlaborcost.setNonLaborCost(Double.parseDouble(cost));
		nonlaborcost.setNotes(note);
		nonlaborcost.setComments(comments);
		nonlaborcost.setQuantity(quantity);
		nonlaborcost.setProductId(Integer.parseInt(product));
		nonlaborcost.setComponentId(Integer.parseInt(componentId));
		nonlaborcost.setWBS(wbs);
		nonlaborcost.setCreator(u.getUserName());
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().saveNonLabor(conn, nonlaborcost);
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		} finally{
			if (conn != null)
			{
				conn.close();
			}
		}
		System.out.println("add nonlaborcost result in action =" + result);
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * @function ����NonLabor
	 * @author longzhe
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward searchNonLaborByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		POForm mainForm = (POForm) form;
		String LaborID = (String) request.getParameter("nonid");
		
		NonLaborCost nonlaborcost = new NonLaborCost();
		Connection conn = null;
		try{
			conn = DataTools.getConnection(request);
			nonlaborcost = PODao.getInstance().searchNonLaborByID(conn, Integer.parseInt(LaborID));
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		} finally{
			if (conn != null)
			{
				conn.close();
			}
		}
		
		request.setAttribute("nonlaborcost", nonlaborcost);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * @function �½�NonLabor
	 * @author longzhe
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward editNonLabor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{		
		POForm mainForm = (POForm) form;
		
		String nid = (String) request.getParameter("nid");
		String date = (String) request.getParameter("date");
		String groupID = (String) request.getParameter("group");
		String poid = (String) request.getParameter("poid");
		String locale = (String) request.getParameter("locale");
		String cost = (String) request.getParameter("cost");
		cost=cost.replaceAll(",", "");//FWJ 转换格式 2013-05-21
		String note = (String) request.getParameter("notes");
		String comments=(String) request.getParameter("comments");
		String product=(String) request.getParameter("product");
		String quantity=(String) request.getParameter("quantity");
		String componentId=(String)request.getParameter("component");
		String wbs=(String)request.getParameter("wbs");
		
		
		NonLaborCost nonlaborcost = new NonLaborCost();
		nonlaborcost.setNid(Integer.parseInt(nid));
		nonlaborcost.setNdate(date);
		nonlaborcost.setGroupNameID(Integer.parseInt(groupID));
		nonlaborcost.setPOID(Integer.parseInt(poid));
		nonlaborcost.setLocaleID(Integer.parseInt(locale));
		nonlaborcost.setNonLaborCost(Double.parseDouble(cost));
		nonlaborcost.setNotes(note);
		nonlaborcost.setComments(comments);
		nonlaborcost.setQuantity(quantity);
		nonlaborcost.setProductId(Integer.parseInt(product));
		nonlaborcost.setComponentId(Integer.parseInt(componentId));
		nonlaborcost.setWBS(wbs);
		Connection conn = null;
		boolean result = false;
		
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().editNonLabor(conn, nonlaborcost);
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		} finally{
			if (conn != null)
			{
				conn.close();
			}
		}
		System.out.println("add nonlaborcost result in action =" + result);
		request.setAttribute("result", String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	
	
	
	/*********************Test override name list*********************/
	/**
	 * @function ��set name list����� 
	 * @author Dancy
	 * @param mapping,form,request,response
	 * @return
	 * @throws Exception
	 * 2012-2-6 ��program Ϊprojectnames
	 */	
	@SuppressWarnings("unchecked")
	public Object searchData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Map> datalist = null;
		String dataName = (String) request.getParameter("dataName");
		System.out.println("dataName in action is:"+dataName);
		String sql="";
		String data = "";
		String id = "";
		if("Product".equals(dataName))
		{
			//sql="select * from products;";
			sql="select productid,product,hide,(select ProductYear from productyear py where py.ProductYearID=ps.ProductYearid) as 'pyear'," +
					"(select Platform from platform pf where pf.PlatformID=ps.platformid) as 'pform'," +
					"(select Business from business bs where bs.BusinessID=ps.businessid ) as 'bss'," +
					"(select FWQEOwner from fwqeowner fo where fo.FWQEOwnerID=ps.FWQEOwnerid ) as 'FWQEOwner'," +
					"groupnum from products ps;";
			data = "product";
			id = "productid";
		}
		
		if("SkillLevel".equals(dataName))
		{
			sql="select * from skilllevels;";
			data = "skillLevelName";
			id = "skillLevelID";
		}
		if("Location".equals(dataName))
		{
			sql="select * from locations;";
			data = "locationName";
			id = "locationId";
		}
		if("OTType".equals(dataName))
		{
			sql="select * from ottype;";
			data = "OTTypeName";
			id = "OTTypeId";
		}
		if("Description".equals(dataName))
		{
			sql="select * from descriptions;";
			data = "description";
			id = "descriptionid";
		}
		if("WorkType".equals(dataName))
		{
			sql="select * from worktype;";
			data = "worktype";
			id = "id";
		}
		if("Milestone".equals(dataName))
		{
			sql="select * from milestones;";
			data = "milestone";
			id = "milestoneid";
		}
		if("Component".equals(dataName))
		{
			sql="select * from components;";
			data = "componentName";
			id = "componentid";
		}
		
		if("Locationcode".equals(dataName))
		{
			sql="select * from locationcodes;";
			data = "code";
			id = "codeID";
		}
		if("POManager".equals(dataName))
		{
			sql="select * from pomanager;";
			data = "POManager";
			id = "POManagerID";
		}
		//拆分Business和BusinessSelect时候的查询条件 FWJ 2013-05-06
		if("Business".equals(dataName))
		{
			sql="select * from business;";
			data = "Business";
			id = "BusinessID";
		}
		if("BusinessSelect".equals(dataName))
		{
			sql="select * from business where hide=0;";
			data = "Business";
			id = "BusinessID";
		}
		//拆分Platform和PlatformSelect时候的查询条件 FWJ 2013-05-06
		if("Platform".equals(dataName))
		{
			sql="select * from platform;";
			data = "Platform";
			id = "PlatformID";
		}
		if("PlatformSelect".equals(dataName))
		{
			sql="select * from platform where hide=0;";
			data = "Platform";
			id = "PlatformID";
		}
		if("Program".equals(dataName))
		{
			sql="select * from projectnames;";
			data = "projectname";
			id = "projectnameid";
		}
		if("WBS".equals(dataName))
		{
			sql="select * from wbs;";
			data = "wbs";
			id = "wbsid";
		}
		//拆分FWQEOwner和FWQEOwnerSelect时候的查询条件 FWJ 2013-05-06
		if("FWQEOwner".equals(dataName))
		{
			sql="select * from fwqeowner;";
			data = "FWQEOwner";
			id = "FWQEOwnerID";
		}
		if("FWQEOwnerSelect".equals(dataName))
		{
			sql="select * from fwqeowner where hide=0;";
			data = "FWQEOwner";
			id = "FWQEOwnerID";
		}
		//拆分ProductYear和ProductYearSelect时候的查询条件 FWJ 2013-05-06
		if("ProductYear".equals(dataName))
		{
			sql="select * from productyear;";
			data = "ProductYear";
			id = "ProductYearID";
		}
		if("ProductYearSelect".equals(dataName))
		{
			sql="select * from productyear where hide=0;";
			data = "ProductYear";
			id = "ProductYearID";
		}
		if("Cycle".equals(dataName))
		{
			sql="select * from billcycles;";
			data = "BillCycles";
			id = "cycleID";
		}
		//hanxiaoyu01 2012-12-17 ����
		if("TestType".equals(dataName)){
			sql="select * from testtype";
			data="TestType";
			id="TestTypeID";
		}
		// Added by FWJ on 2013-03-06
		if("TargetLaunch".equals(dataName))
		{
			sql="select * from targetlaunch;";
			data="targetLaunch";
			id="targetLaunchid";
		}
		
		// Added by FWJ on 2013-03-06
		if("Category".equals(dataName))
		{
			sql="select * from category;";
			data="category";
			id="categoryid";
		}
		if("Budget".equals(dataName))
		{
			sql="select * from budgettracking;";
			data="budget";
			id="budgetid";
		}
		
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			datalist = PODao.getInstance().searchDataList(stmt,sql,id,data);
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(null!=stmt){
				stmt.close();
			}
			if(null!=conn){
				conn.close();
			}
		}

		JSONArray jsonArray =JSONArray.fromObject(datalist);
		PrintWriter out = response.getWriter();
		out.print(jsonArray.toString());
		out.close();
		
		return null;
	}

	
	/**
	 * set Name List �޸�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Dancy
	 */	
	public ActionForward modifyData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String id = (String) request.getParameter("id");
		String name = (String) request.getParameter("name");
		
		String dataName = (String) request.getParameter("dataName");
		//System.out.println("dataName in modify action is:"+dataName+" and modified obj is :"+name);
		String sql = "";
		if("Product".equals(dataName))
		{
			sql="update products set product=? where productid=?";
		}
		if("SkillLevel".equals(dataName))
		{
			sql="update skilllevels set skillLevelName=? where skillLevelID=?";
		}
		if("Location".equals(dataName))
		{
			sql="update locations set locationName=? where locationId=?";
		}
		if("OTType".equals(dataName))
		{
			sql="update ottype set OTTypeName=? where OTTypeId=?";
		}
		if("Description".equals(dataName))
		{
			sql="update descriptions set description=? where descriptionid=?";
		}
		if("WorkType".equals(dataName))
		{
			sql="update worktype set worktype=? where id=?";
		}
		if("Milestone".equals(dataName))
		{
			sql="update milestones set milestone=? where milestoneid=?";
		}
		if("Component".equals(dataName))
		{
			sql="update components set componentName=? where componentid=?";
		}
		
		if("Locationcode".equals(dataName))
		{
			sql="update locationcodes set code=? where codeID=?";
		}
		if("POManager".equals(dataName))
		{
			sql="update pomanager set POManager=? where POManagerID=?";

		}
		if("Business".equals(dataName))
		{
			sql="update business set Business=? where BusinessID=?";

		}
		if("Platform".equals(dataName))
		{
			sql="update platform set Platform=? where PlatformID=?";

		}
		if("Program".equals(dataName))
		{
			sql="update projectnames set projectname=? where projectnameid=?";

		}
		if("WBS".equals(dataName))
		{
			sql="update wbs set wbs=? where wbsid=?";
	
		}
		if("FWQEOwner".equals(dataName))
		{
			sql="update fwqeowner set FWQEOwner=? where FWQEOwnerID=?";

		}
		if("ProductYear".equals(dataName))
		{
			sql="update productyear set ProductYear=? where ProductYearID=?";
		}
		if("Cycle".equals(dataName))
		{
			sql="update billcycles set BillCycles=? where cycleID=?";
		}
		//hanxiaoyu01 2012-12-17
		if("TestType".equals(dataName)){
			sql="update testtype set testtype=? where TestTypeID=?";
		}
		//Added by FWJ on 2013-03-06
		if("TargetLaunch".equals(dataName))
		{
			sql="update targetlaunch set targetLaunch=? where targetLaunchid=?";
		}
		
	    //Added by FWJ on 2013-03-14
		if("Category".equals(dataName))
		{
			sql="update Category set category=? where categoryid=?";
		}
		
		//Added by FWJ on 2013-05-20
		if("Budget".equals(dataName))
		{
			sql="update budgettracking set budget=? where budgetid=?";
		}
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().modifyData(conn, name, Integer.parseInt(id),sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
		response.getWriter().write(String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}

	
	/**
	 * set name list�h��修改了sql语句，删除之后的字符都设置为hide为1 FWJ 2013-05-03
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author dancy
	 */	
	public ActionForward deleteData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String id = (String) request.getParameter("id");
	//	System.out.println("id="+id);
		String dataName = (String) request.getParameter("dataName");
		//System.out.println("dataName in delete action is:"+dataName+" and deleted obj id is:"+id);
		String sql = "";
		if("Product".equals(dataName))
		{
			sql="update products set hide=1 where productid=?";
		}
		if("SkillLevel".equals(dataName))
		{
			sql="update skilllevels set hide=1 where skillLevelID=?";
		}
		if("Location".equals(dataName))
		{
			sql="update locations set hide=1 where locationId=?";
		}
		if("OTType".equals(dataName))
		{
			sql="update ottype set hide=1 where OTTypeId=?";
		}
		if("Description".equals(dataName))
		{
			sql="delete from descriptions where descriptionid=?";
		}
		if("WorkType".equals(dataName))
		{
			sql="update worktype set hide=1 where id=?";
		}
		if("Milestone".equals(dataName))
		{
			sql="update milestones set hide=1 where milestoneid=?";
		}
		if("Component".equals(dataName))
		{
			sql="update components set hide=1 where componentid=?";
		}
		if("Locationcode".equals(dataName))
		{
			sql="delete from locationcodes where codeID=?";
		}
		if("POManager".equals(dataName))
		{
			sql="update pomanager set hide =1 where POManagerID=?";

		}
		if("Business".equals(dataName))
		{
			sql="update business set hide=1 where BusinessID=?";

		}
		if("Platform".equals(dataName))
		{
			sql="update platform set hide=1 where PlatformID=?";

		}
		if("Program".equals(dataName))
		{
			sql="update platform set hide=1 where projectnameid=?";

		}
		if("WBS".equals(dataName))
		{
			sql="update wbs set hide=1 where wbsid=?";
	
		}
		if("FWQEOwner".equals(dataName))
		{
			sql="update fwqeowner set hide=1 where FWQEOwnerID=?";

		}
		if("ProductYear".equals(dataName))
		{
			sql="update productyear set hide=1 where ProductYearID=?";
		}
		if("Cycle".equals(dataName))
		{
			sql="delete from billcycles where cycleID=?";
		}
		if("TestType".equals(dataName)){
			sql="update testtype set hide=1 where testtypeID=?";
		}
		//Added by FWJ on 2013-03-06
		if("TargetLaunch".equals(dataName))
		{
			sql="update TargetLaunch set hide=1 where TargetLaunchID=?";
		}
		//Added by FWJ on 2013-03-14
		if("Category".equals(dataName))
		{
		//	sql="delete from category where categoryID=?";
			sql="update category set hide=1 where categoryID=?";
		}
		//Added by FWJ on 2013-05-20
		if("Budget".equals(dataName))
		{
		//	sql="delete from category where categoryID=?";
			sql="update budgettracking set hide=1 where budgetid=?";
		}
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().deleteData(conn, Integer.parseInt(id),sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		response.getWriter().write(String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * set name list添加了activeData的方法 FWJ 2013-05-03
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author dancy
	 */	
	public ActionForward activeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String id = (String) request.getParameter("id");
	//	System.out.println("id="+id);
		String dataName = (String) request.getParameter("dataName");
		//System.out.println("dataName in delete action is:"+dataName+" and deleted obj id is:"+id);
		String sql = "";
		if("Product".equals(dataName))
		{
			sql="update products set hide=0 where productid=?";
		}
		if("SkillLevel".equals(dataName))
		{
			sql="update skilllevels set hide=0 where skillLevelID=?";
		}
		if("Location".equals(dataName))
		{
			sql="update locations set hide=0 where locationId=?";
		}
		if("OTType".equals(dataName))
		{
			sql="update ottype set hide=0 where OTTypeId=?";
		}
		if("Description".equals(dataName))
		{
			sql="delete from descriptions where descriptionid=?";
		}
		if("WorkType".equals(dataName))
		{
			sql="update worktype set hide=0 where id=?";
		}
		if("Milestone".equals(dataName))
		{
			sql="update milestones set hide=0 where milestoneid=?";
		}
		if("Component".equals(dataName))
		{
			sql="update components set hide=0 where componentid=?";
		}
		if("Locationcode".equals(dataName))
		{
			sql="delete from locationcodes where codeID=?";
		}
		if("POManager".equals(dataName))
		{
			sql="update pomanager set hide =0 where POManagerID=?";

		}
		if("Business".equals(dataName))
		{
			sql="update business set hide=0 where BusinessID=?";

		}
		if("Platform".equals(dataName))
		{
			sql="update platform set hide=0 where PlatformID=?";

		}
		if("Program".equals(dataName))
		{
			sql="delete from projectnames where projectnameid=?";

		}
		if("WBS".equals(dataName))
		{
			sql="update wbs set hide=0 where wbsid=?";
	
		}
		if("FWQEOwner".equals(dataName))
		{
			sql="update fwqeowner set hide=0 where FWQEOwnerID=?";

		}
		if("ProductYear".equals(dataName))
		{
			sql="update productyear set hide=0 where ProductYearID=?";
		}
		if("Cycle".equals(dataName))
		{
			sql="delete from billcycles where cycleID=?";
		}
		if("TestType".equals(dataName)){
			sql="update TestType set hide=0 where TestTypeID=?";
		}
		//Added by FWJ on 2013-03-06
		if("TargetLaunch".equals(dataName))
		{
		//	sql="delete from TargetLaunch where TargetLaunchID=?";
			sql="update TargetLaunch set hide=0 where TargetLaunchID=?";
		}
		//Added by FWJ on 2013-03-14
		if("Category".equals(dataName))
		{
		//	sql="delete from category where categoryID=?";
			sql="update category set hide=0 where categoryID=?";
		}
		//Added by FWJ on 2013-05-20
		if("Budget".equals(dataName))
		{
		//	sql="delete from category where categoryID=?";
			sql="update budgettracking set hide=0 where budgetid=?";
		}
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().deleteData(conn, Integer.parseInt(id),sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		response.getWriter().write(String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * set name list添加了checkDate的方法 防止被引用的字段被删除 FWJ 2013-05-03
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author dancy
	 */	
	/*
	public ActionForward checkData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String id = (String) request.getParameter("id");
	//	System.out.println("id="+id);
		String dataName = (String) request.getParameter("dataName");
		//System.out.println("dataName in delete action is:"+dataName+" and deleted obj id is:"+id);
		String sql = "";
		if("Product".equals(dataName))
		{
			sql="delete from products where productid=?";
		}
		if("SkillLevel".equals(dataName))
		{
			sql="delete from skilllevels where skillLevelID=?";
		}
		if("Location".equals(dataName))
		{
			sql="delete from locations where locationId=?";
		}
		if("OTType".equals(dataName))
		{
			sql="delete from ottype where OTTypeId=?";
		}
		if("Description".equals(dataName))
		{
			sql="delete from descriptions where descriptionid=?";
		}
		if("WorkType".equals(dataName))
		{
			sql="delete from worktype where id=?";
		}
		if("Milestone".equals(dataName))
		{
			sql="delete from milestones where milestoneid=?";
		}
		if("Component".equals(dataName))
		{
			sql="delete from components where componentid=?";
		}
		if("Locationcode".equals(dataName))
		{
			sql="delete from locationcodes where codeID=?";
		}
		if("POManager".equals(dataName))
		{
			sql="delete from pomanager where POManagerID=?";

		}
		if("Business".equals(dataName))
		{
			sql="select * from products where BusinessID=?";

		}
		if("Platform".equals(dataName))
		{
			sql="delete from platform where PlatformID=?";

		}
		if("Program".equals(dataName))
		{
			sql="delete from projectnames where projectnameid=?";

		}
		if("WBS".equals(dataName))
		{
			//仅仅monthlyexpense中的wbs是利用wbsid关联的，其他的两个地方均是字符串存储
			sql="select * from monthlyexpense where wbsid=?";
	
		}
		if("FWQEOwner".equals(dataName))
		{
	//		sql="select * from monthlyexpense where clientID=?";
			sql="SELECT Monthlyexpense.Clientid, Products.FWQEOwnerid FROM FWQEOwner "+
				"LEFT JOIN Monthlyexpense ON Monthlyexpense.Clientid=FWQEOwner.FWQEOwnerid "+
				"LEFT JOIN Products ON Products.FWQEOwnerid=FWQEOwner.FWQEOwnerid "+
				"where FWQEOwner.FWQEOwnerid=?";

		}
		if("ProductYear".equals(dataName))
		{
			sql="select * from monthlyexpense where yearID=?";
		}
		if("Cycle".equals(dataName))
		{
			sql="delete from billcycles where cycleID=?";
		}
		if("TestType".equals(dataName)){
		//	sql="delete from testtype where TestTypeID=?";
			sql="select * from expensedata_details where TestTypeID=?";
		}
		//Added by FWJ on 2013-03-06
		if("TargetLaunch".equals(dataName))
		{
		//	sql="delete from TargetLaunch where TargetLaunchID=?";
			sql="select * from expensedata_details where TargetLaunchID=?";
		}
		//Added by FWJ on 2013-03-14
		if("Category".equals(dataName))
		{
		//	sql="delete from category where categoryID=?";
			sql="select * from monthlyexpense where categoryID=?";
		}
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().checkData(conn, Integer.parseInt(id),sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		
//		PrintWriter out=response.getWriter();
//		return null;
		response.getWriter().write(String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
	*/
	/**
	 * set name list ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author Longzhe,dancy
	 */	
	public ActionForward addData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		POForm mainForm = (POForm) form;
		String name = (String) request.getParameter("name");
		String dataName = (String) request.getParameter("dataName");
		System.out.println("dataName in add action is:"+dataName+" and added obj is:"+name);
		String sql0 = "";
		String sql1 = "";
		if("Product".equals(dataName))
		{
			String pname = (String) request.getParameter("pname");
			String bsname = (String) request.getParameter("bsname");
			String pfname = (String) request.getParameter("pfname");
			String foname = (String) request.getParameter("foname");
			System.out.println("pname:"+pname+"//bsname:"+bsname+"//pfname:"+pfname+"//foname:"+foname);
			sql0 = "select * from products where product=?;";
			sql1 = "insert products (product,ProductYearid,platformid,businessid,FWQEOwnerid) values (?," +
					"(select ProductYearID from productyear where ProductYear='"+pname+"')," +					
					"(select PlatformID from platform where Platform='"+pfname+"')," +
					"(select BusinessID from business where Business='"+bsname+"')," +
					"(select FWQEOwnerID from fwqeowner where FWQEOwner='"+foname+"'));";
			//System.out.println(sql1);	
		}
		
		if("SkillLevel".equals(dataName))
		{
			sql0 = "select * from skilllevels where skillLevelName=?;";
			sql1 = "insert skilllevels (skillLevelName) values (?);";
		}
		if("Location".equals(dataName))
		{
			sql0 = "select * from locations where locationName=?;";
			sql1 = "insert locations (locationName) values (?);";
		}
		if("OTType".equals(dataName))
		{
			sql0 = "select * from ottype where OTTypeName=?;";
			sql1 = "insert ottype (OTTypeName) values (?);";
		}
		if("Description".equals(dataName))
		{
			sql0 = "select * from descriptions where description=?;";
			sql1 = "insert descriptions (description) values (?);";
		}
		if("WorkType".equals(dataName))
		{
			sql0 = "select * from worktype where worktype=?;";
			sql1 = "insert worktype (worktype) values (?);";
		}
		if("Milestone".equals(dataName))
		{
			sql0 = "select * from milestones where milestone=?;";
			sql1 = "insert milestones (milestone) values (?);";
		}
		if("Component".equals(dataName))
		{
			sql0 = "select * from components where componentName=?;";
			sql1 = "insert components (componentName) values (?);";
		}
		
		if("Locationcode".equals(dataName))
		{
			sql0 = "select * from locationcodes where code=?;";
			sql1 = "insert locationcodes (code) values (?);";
		}
		if("POManager".equals(dataName))
		{
			sql0 = "select * from pomanager where POManager=?;";
			sql1 = "insert pomanager (POManager) values (?);";

		}
		if("Business".equals(dataName))
		{
			sql0 = "select * from business where Business=?;";
			sql1 = "insert business (Business) values (?);";

		}
		if("Platform".equals(dataName))
		{
			sql0 = "select * from platform where Platform=?;";
			sql1 = "insert platform (Platform) values (?);";

		}
		if("Program".equals(dataName))
		{
			sql0 = "select * from projectnames where projectname=?;";
			sql1 = "insert projectnames (projectname) values (?);";

		}
		if("WBS".equals(dataName))
		{
			sql0 = "select * from wbs where wbs=?;";
			sql1 = "insert wbs (wbs) values (?);";
	
		}
		if("FWQEOwner".equals(dataName))
		{
			sql0 = "select * from fwqeowner where FWQEOwner=?;";
			sql1 = "insert fwqeowner (FWQEOwner) values (?);";

		}
		if("ProductYear".equals(dataName))
		{
			sql0 = "select * from productyear where ProductYear=?;";
			sql1 = "insert productyear (ProductYear) values (?);";
			
		}
		if("Cycle".equals(dataName))
		{
			sql0 = "select * from billcycles where BillCycles=?;";
			sql1 = "insert billcycles (BillCycles) values (?);";
			
		}
		//hanxiaoyu01 2012-12-17
		if("TestType".equals(dataName)){
			sql0="select * from testtype where testtype=?";
			sql1="insert testtype(testtype) values(?)";
		}
		
		// Added by FWJ on 2013-03-06
		if("TargetLaunch".equals(dataName))
		{
			sql0="select * from targetlaunch where targetlaunch=?";
			sql1="insert targetlaunch(targetlaunch) values(?)";
		}
		// Added by FWJ on 2013-03-14
		if("Category".equals(dataName))
		{
			sql0="select * from category where category=?";
			sql1="insert category(category) values(?)";
		}
		if("Budget".equals(dataName))
		{
			sql0="select * from budgettracking where budget=?";
			sql1="insert budgettracking(budget) values(?)";
		}
		
		Connection conn = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().addData(conn, name,sql0,sql1);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		response.getWriter().write(String.valueOf(result));
		return mapping.findForward(mainForm.getOperPage());
	}
/**
 *  updateBalance����
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 * @author yindandan
 */	
	public ActionForward updateBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String poid = (String) request.getParameter("poid");
		System.out.println("poid:"+poid);
		String flag = (String) request.getParameter("flag");
		String amount = (String) request.getParameter("amount");
		System.out.println("flag="+flag+"  amount="+amount);
		float projectTake = 0;
		String sql0 = "select projectId from projects where PONumberid="+poid;
		String sql = "select sum(NonLaborCosts) as 'nonLabor' from nonlaborcosts where poid="+poid;

		Connection conn = null;
		Statement stmt = null;
		List<Project> projectlist = new ArrayList<Project>();
		Project ptOTRate = null;
		Project ptRate = null;
		Project ptHours = null;
		Project ptNonLaborCost = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			//ȡ�ø�po��Ӧ������project
			projectlist = PODao.getInstance().getProjectsByPO(stmt,sql0);
			System.out.println("projectlist:"+projectlist.size());
			//ȡ�ø�po��Ӧ������NonLaborCost�ܻ���
			ptNonLaborCost = ptHours = PODao.getInstance().getProjectsReference(stmt,sql,"nonLabor");
			System.out.println("POID="+poid+",its total NonLaborCost is :"+ptNonLaborCost.getSumNonLaborCost());
			if(projectlist!=null)
			{
				for(int i=0;i<projectlist.size();i++)
				{
					String sql1 ="select OTRate from ottype where OTTypeName=(select OTType from projects where projectId="+projectlist.get(i).getProjectID()+");";
					String sql2 = "select Rate from ratematrix where SkillLevel=(select skillLevel from projects where projectId="+projectlist.get(i).getProjectID()+")" +
									" and CostRegion=(select location from projects where projectId="+projectlist.get(i).getProjectID()+");";
					String sql3 = "select sum(hours) as 'sum' from expensedata where projectId="+projectlist.get(i).getProjectID();
					ptOTRate = PODao.getInstance().getProjectsReference(stmt,sql1,"OTRate");//ÿ��project��OT rate
					ptRate = PODao.getInstance().getProjectsReference(stmt,sql2,"ptRate");//ÿ��project��rate(����skillLevel��location����)
					ptHours = PODao.getInstance().getProjectsReference(stmt,sql3,"sum");//ÿ��project���ܹ�ʱ
					projectTake += ptOTRate.getOTRate()*ptRate.getRateP()*ptHours.getSumHours();//�ۼ����е�project����
//					System.out.println("Project "+projectlist.get(i).getProjectID()+" total cost is:"+ptOTRate.getOTRate()*ptRate.getRateP()*ptHours.getSumHours());
				}
			
			}
			System.out.println("So its total cost is:"+(projectTake+ptNonLaborCost.getSumNonLaborCost()));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		Double poused = projectTake+ptNonLaborCost.getSumNonLaborCost();
		if(flag.equals("1"))
		{
			this.savePObyID(mapping, form, request, response, poid, String.valueOf(poused), String.valueOf(amount));
		}
		response.getWriter().write(String.valueOf(poused));
		
		return null;
	}
	public ActionForward savePObyID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String poid, String poused,String amount)
			throws Exception
	{
		Connection conn = null;
		boolean result = false;
		try
		{
			conn = DataTools.getConnection(request);
			result = PODao.getInstance().savePOBalance(conn, poid, poused, amount);
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		System.out.println("update balance result is:"+result);
		return null;
	}
/**
 * �Զ������ʼ�
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */	
	public ActionForward sendMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String id = (String) request.getParameter("id");
		String pno = (String) request.getParameter("pno");
		String lock = (String) request.getParameter("lock");
		String status = (String) request.getParameter("status");
		String sdate = (String) request.getParameter("sdate");
		String edate = (String) request.getParameter("edate");
		String desr = (String) request.getParameter("ds");
		String manager = (String) request.getParameter("manager");
		String code = (String) request.getParameter("code");
		Calendar cd = Calendar.getInstance();
		System.out.println(cd.get(Calendar.YEAR)+"/"+(cd.get(Calendar.MONTH)+1)+"/"+cd.get(Calendar.DATE));
		
		if("TRUE".equals(lock))
		{
			lock = "Locked";
		}
		else
			lock = "Unlocked";
		String from = "oip@beyondsoft.com";
		String subject = "[IBS OIP] Notification -- PO Balance Alert";
		String body = "<div style='font-family:Verdana; font-size:12px; line-height:24px;'><p><font color='green' size='3'>Balance value of [PO]"+pno
		+" is lower than its Alert value!</font></p>"+
		"<hr /><p><H4>DETAILS:</H4></p>"
		+"<ul><li>PO ID: "+id+"</li><li>Lock/Unlock: "+lock+"</li><li>Status: "+status+"</li><li>"
		+"Start Date: "+sdate+"</li><li>End Date: "+edate+"</li>"+
		"<li>Location Code: "+code+"</li><li>Manager: "+manager+"</li><li>Description: "+desr+"</li></ul>"
		+"<br /><hr /><p><font color='red'>This mail is sent by the system automatically, please don't reply it.<br />"
		+"2012-02-09</font></p></div>";
		//System.out.println(body);
		String host = "smtp.beyondsoft.com";
		String to2 = (String) request.getParameter("mailTo");
		System.out.println("mail to:" + to2);
		try{
			System.out.println("System is mailing to "+to2.substring(0,to2.indexOf("@"))+" ......");
			Properties props = System.getProperties();	    	 
	    	// Setup mail server
	    	props.put("mail.smtp.host", host);	    	 
	    	// Get session
//	    	Session session = Session.getDefaultInstance(props, null);	
	    	Session session = Session.getInstance(props, null);
	    	// Define message
	    	MimeMessage message = new MimeMessage(session);
	    	message.setFrom(new InternetAddress(from));
	    	message.addRecipient(Message.RecipientType.TO,new InternetAddress(to2));
	    	message.setSubject(subject);
//	    	message.setText(body);	    	
	    	MimeMultipart multipart = new MimeMultipart("related");	          
	        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
	  		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	  		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	  		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	  		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	  		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	  		CommandMap.setDefaultCommandMap(mc);
	        MimeBodyPart htmlBodyPart = new MimeBodyPart();          
	        htmlBodyPart.setContent(body,"text/html;charset=gb2312");
	        multipart.addBodyPart(htmlBodyPart); 

	        message.setContent(multipart);
	        Transport.send(message);
	        System.out.println("successfully!");
	    	  
		}catch(Exception e)
		{
			System.err.println( "failed�� "+e);   
		}
		return null;
	}
	
	
	
	/**
	 * @author hanxiaoyu01
	 * �ж�PO�Ƿ�����
	 * 2013-01-04
	 */
	public ActionForward checkPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		int poid = Integer.parseInt(request.getParameter("poid"));
		Connection conn=null;
		int count=0;
		int count_me=0;
	    try{
		   conn = DataTools.getConnection(request);
		   count=PODao.getInstance().checkPO(conn,poid);
		   //FWJ on 2013-04-27
		   count_me=PODao.getInstance().checkPOInMonthlyExpense(conn, poid);
	    }catch(Exception e){
		   e.printStackTrace();
	    }finally{
		   if(conn!=null){
			   conn.close();
		   }
	   }
		PrintWriter out=response.getWriter();
		if(count>0&&count_me>0){
			//在project中和monthlyexpense均被使用
			out.print("3");
		}else if(count>0&&count_me<=0){
			//仅在project中被使用
			out.print("2");
		}else if(count_me>0&&count<=0){
			//仅在monthlyexpense中被使用
			out.print("1");
		}else {
			//没有被使用
			out.print("0");
		}
		
		System.out.println("count="+count+"   count_me="+count_me);
		return null;
	}
	
	
	/**
	 * @author FWJ
	 * 检查PO是否已经被添加
	 * 2013-04-27
	 */
	public ActionForward checkPOadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		String pon = request.getParameter("pon");
		Connection conn=null;
		int count=0;
	    try{
		   conn = DataTools.getConnection(request);
		   count=PODao.getInstance().checkPOadd(conn,pon);
	    }catch(Exception e){
		   e.printStackTrace();
	    }finally{
		   if(conn!=null){
			   conn.close();
		   }
	   }
		PrintWriter out=response.getWriter();
		if(count>0){
			out.print("true");
		}else {
			out.print("false");
		}
		return null;
	}
	
	/**
	 * @author hanxiaoyu01
	 * remove POʱ�ж�PO��Products��Component�Ƿ�����
	 * 2013-01-06
	 */
	public ActionForward checkPO2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		int poid = Integer.parseInt(request.getParameter("poid"));
		String product=(String)request.getParameter("product");
		String component=(String)request.getParameter("component");
		Connection conn=null;
		int count=0;
	    try{
		   conn = DataTools.getConnection(request);
		   count=PODao.getInstance().checkPO2(conn,poid,product,component);
	    }catch(Exception e){
		   e.printStackTrace();
	    }finally{
		   if(conn!=null){
			   conn.close();
		   }
	   }
		PrintWriter out=response.getWriter();
		if(count>0){
			out.print("true");
		}else{
			out.print("false");
		}
		return null;
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2013-02-16
	 * ��Case and Defect����Ĭ��ֵ
	 * 
	 */
	public ActionForward addDefaultCaseAndDefect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		SysUser u=(SysUser) request.getSession().getAttribute("user");
		int dpid=Integer.parseInt(request.getParameter("dpid"));
		int dcid=Integer.parseInt(request.getParameter("dcid"));
		int dmid=Integer.parseInt(request.getParameter("dmid"));
		DefaultCaseDefect dcd=new DefaultCaseDefect(u.getUserId(),dpid,dcid,dmid);
		Connection conn=null;
		try{
			conn=DataTools.getConnection(request);
			PODao.getInstance().deleteDefaultCaseAndDefect(conn, u.getUserId());
			PODao.getInstance().addDefaultCaseAndDefect(conn, u.getUserId(), dcd);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		PrintWriter out=response.getWriter();
		out.print("success!");
		out.close();
		return null;
	}
	
	//添加了filter的方法，不直接访问数据库，对数据库的压力减小 FWJ on 2013-04-26
	@SuppressWarnings("unchecked")
	public ActionForward searchWithFilter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("enter");
		POForm mainForm = (POForm) form;
		
		List<ProjectOrder> list = (List<ProjectOrder>) request.getSession().getAttribute("list");
		List<ProjectOrder> templist = new ArrayList<ProjectOrder>();
		
		for (int i = 0; i < list.size(); i++)
			{
				templist.add(list.get(i));
			}
	
		String page = request.getParameter("currentpage");
		
		String pagefrom = request.getParameter("pagefrom");
		
		String pofilters2 = null;
		String statefilters = null;
		String lockstatusfilters = null;
		String managerfilters = null;
		
		if(pagefrom!=null&&pagefrom.equals("po_list"))
		{
			pofilters2 = request.getParameter("pofilters2");
			statefilters = request.getParameter("statefilters");
			lockstatusfilters = request.getParameter("lockstatusfilters");
			managerfilters = request.getParameter("managerfilters");
		}
		else
		{
			//如果不设置session中的pagex，从monthlyexpense返回会出错
			page=request.getSession().getAttribute("pagex").toString();
			pofilters2=(String)request.getSession().getAttribute("pofilters2");
			statefilters=(String)request.getSession().getAttribute("statefilters");
			lockstatusfilters=(String)request.getSession().getAttribute("lockstatusfilters");
			managerfilters=(String)request.getSession().getAttribute("managerfilters");
		}
		System.out.println("pofilters2 that got from the page="+pofilters2);
		
		if(pofilters2!=null&&pofilters2.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (pofilters2.indexOf(templist.get(i).getPONumber().trim())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("pofilters2", pofilters2);
		
		System.out.println("statefilters that got from the page="+statefilters);
		
		if(statefilters!=null&&statefilters.indexOf(",")>0)
		{
			for (int i = 0; i < templist.size(); i++) 
			{
				if (statefilters.indexOf(templist.get(i).getPOStatus())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("statefilters", statefilters);
		
		
		System.out.println("lockstatusfilters that got from the page="+lockstatusfilters);

		if(lockstatusfilters!=null&&lockstatusfilters.indexOf(",")>0)
		{
			
			for (int i = 0; i < templist.size(); i++) 
			{

				if (lockstatusfilters.indexOf(templist.get(i).getLock().trim())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}

		request.getSession().setAttribute("lockstatusfilters", lockstatusfilters);
		
		
		System.out.println("managerfilters that got from the page="+managerfilters);
		
		if(managerfilters!=null&&managerfilters.indexOf(",")>0)
		{
			
			for (int i = 0; i < templist.size(); i++) 
			{

				if (managerfilters.indexOf(templist.get(i).getPOManager())<0) 
				{
					templist.remove(i);
					i--;
				}
			}
		}
		request.getSession().setAttribute("managerfilters", managerfilters);

		int TotalPage = templist.size()/8;
		if(templist.size()%8>0)
		{
			TotalPage++;
		}
		request.getSession().setAttribute("TotalPage", TotalPage);
		request.getSession().setAttribute("list_po", templist);

		int p = 0;
		if(page!=null)
		{
			p = Integer.parseInt(page);
		}
		
		this.turnPOPage(mapping, form, request, response, 8, p);
		request.getSession().setAttribute("page", p);
		System.out.println("The page in the searchwithfilter="+p);
		return mapping.findForward(mainForm.getOperPage());
	}
}