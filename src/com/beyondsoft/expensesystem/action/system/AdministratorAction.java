package com.beyondsoft.expensesystem.action.system;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.dao.checker.ExpenseDataDao;
import com.beyondsoft.expensesystem.dao.system.AdministratorDao;
import com.beyondsoft.expensesystem.domain.system.Administrator;
import com.beyondsoft.expensesystem.form.system.AdministratorForm;
import com.beyondsoft.expensesystem.time.DESPlus;
import com.beyondsoft.expensesystem.util.AnnounceTool;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;
import com.beyondsoft.expensesystem.util.EmailTool;


@SuppressWarnings({ "unchecked", "deprecation" })
public class AdministratorAction extends BaseDispatchAction
{
	public ActionForward choose(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		return mapping.findForward(mainForm.getOperPage()); 
	} 
	
	
	public ActionForward searchPermissionLevels(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		List list = null;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			list = AdministratorDao.getInstance().searchPermissionLevels(conn,
					mainForm.getPageModel());
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
	
	public ActionForward searchPermissionNames(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		List list = null;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			list = AdministratorDao.getInstance().searchPermissionNames(conn,
					mainForm.getPageModel());
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
	
	public ActionForward searchSkillLevels(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		List list = null;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			list = AdministratorDao.getInstance().searchSkillLevels(conn,
					mainForm.getPageModel());
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
	
	public ActionForward searchLocations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		List list = null;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			list = AdministratorDao.getInstance().searchLocations(conn,
					mainForm.getPageModel());
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
	
	public ActionForward searchOTTypes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		List list = null;
		Connection conn = null;
		try
		{
			conn = DataTools.getConnection(request);
			list = AdministratorDao.getInstance().searchOTTypes(conn,
					mainForm.getPageModel());
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
	 * ��ѯSystem Announcement
	 * 
	 * @param stmt
	 * @param announce
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchSysAnnounce(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		String announce = "";
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			announce = AnnounceTool.getInstance().getSysAnnounce(stmt);
		}catch (Exception e)
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
		request.setAttribute("Announcement", announce);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ����System Announcement
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setAnnouncement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		String announce = (String)request.getParameter("Announce");
		//System.out.println("announcement is "+announce);
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			boolean result = AnnounceTool.getInstance().setSysAnnounce(stmt, announce);
			System.out.println("set Announcement "+result);
		}catch (Exception e)
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
		request.getSession().setAttribute("SystemAnnounce", announce);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ά��date������expensedata createtime, ά��date2��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author longzhe
	 */
	public ActionForward getAdministrator04(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		String from = "";
		String to = "";
		
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			List<String> dateList = ExpenseDataDao.getInstance().searchDate2(stmt);
			from = dateList.get(0);
			to = dateList.get(1);
		}catch (Exception e)
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
				conn.close();
			if (null != stmt)
				stmt.close();
		}
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		request.setAttribute("step", "1");
		System.out.println("date2 from "+from+" to "+to);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ά��date2��
	 * 
	 * @author longzhe
	 */
	public ActionForward updateDate2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
//		AdministratorForm mainForm = (AdministratorForm) form;
		
		Connection conn = null;
		Statement stmt = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			result = ExpenseDataDao.getInstance().updateDate2(stmt);
		}catch (Exception e)
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
				conn.close();
			if (null != stmt)
				stmt.close();
		}
		System.out.println("update date2 "+ result);
		request.getSession().setAttribute("result0", String.valueOf(result));
		if(result)
			return getExpensedateCreatetime( mapping,  form, request,  response);
		else
			return getAdministrator04( mapping,  form, request,  response);
	}
	/**
	 * expensedata createtime
	 */
	public ActionForward getExpensedateCreatetime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		String from = "";
		String to = "";
		
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			List<String> dateList = ExpenseDataDao.getInstance().searchExpensedataCreatetime(stmt);
			from = dateList.get(0);
			to = dateList.get(1);
		}catch (Exception e)
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
				conn.close();
			if (null != stmt)
				stmt.close();
		}
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		request.setAttribute("step", "2");
		System.out.println("expensedata createtime from "+from+" to "+to);
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * ά��ExpensedataCreatetime��
	 * 
	 * @author longzhe
	 */
	public ActionForward updateExpensedataCreatetime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
//		AdministratorForm mainForm = (AdministratorForm) form;
		
		Connection conn = null;
		Statement stmt = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			result = ExpenseDataDao.getInstance().updateExpensedataCreatetime(stmt);
		}catch (Exception e)
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
				conn.close();
			if (null != stmt)
				stmt.close();
		}
		System.out.println("update expensedate createtime "+ result);
		request.getSession().setAttribute("result1", String.valueOf(result));
		if(result)
			return getDate( mapping,  form, request,  response);
		else
			return getExpensedateCreatetime( mapping,  form, request,  response);
	}
	
	/**
	 * date
	 */
	public ActionForward getDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		String from = "";
		String to = "";
		
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			List<String> dateList = ExpenseDataDao.getInstance().searchdate(stmt);
			from = dateList.get(0);
			to = dateList.get(1);
		}catch (Exception e)
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
				conn.close();
			if (null != stmt)
				stmt.close();
		}
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		request.setAttribute("step", "3");
		System.out.println("date from "+from+" to "+to);
		return mapping.findForward(mainForm.getOperPage());
	}
	/**
	 * ά��date��
	 * 
	 * @author longzhe
	 */
	public ActionForward updateDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm = (AdministratorForm) form;
		
		Connection conn = null;
		Statement stmt = null;
		boolean result = false;
		try{
			conn = DataTools.getConnection(request);
			stmt = conn.createStatement();
			result = ExpenseDataDao.getInstance().updateDate(stmt);
		}catch (Exception e)
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
				conn.close();
			if (null != stmt)
				stmt.close();
		}
		System.out.println("update date "+ result);
		request.getSession().setAttribute("result2", String.valueOf(result));
		if(result)
			return mapping.findForward(mainForm.getOperPage());
		else
			return getDate( mapping,  form, request,  response);
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-10
	 * ����ϵͳ���ʼ�������
	 */
	public ActionForward searchEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		AdministratorForm mainForm = (AdministratorForm) form;
		Connection conn = null;
		Administrator adm=null;
		try{
			conn = DataTools.getConnection(request);
			adm = EmailTool.getInstance().getEmail(conn);
		}catch (Exception e)
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
		request.setAttribute("email", adm.getEmail());
		request.setAttribute("sender", adm.getSender());
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * @author hanxiaoyu01
	 * ������������  2012-12-11
	 * 
	 */
	public ActionForward setEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		AdministratorForm mainForm=(AdministratorForm)form;
		String psw=new DESPlus().encrypt(mainForm.getPsw());
		Connection conn = null;
		try{
			conn = DataTools.getConnection(request);
			EmailTool.getInstance().setEmail(conn, mainForm.getEmail(), mainForm.getSender(), psw);
		}catch (Exception e)
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
		return mapping.findForward(mainForm.getOperPage());
	}
	
	/**
	 * @author hanxiaoyu01
	 * 2012-12-12
	 * �鵽ϵͳ���ʼ�
	 */
	public Administrator getSysEmail(Connection conn)throws Exception{
		Administrator adm=null;
		try{
			adm = EmailTool.getInstance().getEmail(conn);
		}catch (Exception e)
		{
			// ���������Ϣ
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"errors.query"));
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		return adm;
	}
}
