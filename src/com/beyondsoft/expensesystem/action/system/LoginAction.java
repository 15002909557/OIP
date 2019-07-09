package com.beyondsoft.expensesystem.action.system;

import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.dao.system.SysUserDao;
import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.AnnounceTool;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;
import com.beyondsoft.expensesystem.util.DataTools;
import com.beyondsoft.expensesystem.util.MD5;


public class LoginAction extends BaseDispatchAction
{

	/**
	 * �û���¼ from submit form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @flag
	 */
	public ActionForward login2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String remember = (String) request.getParameter("remember");
		String username = (String) request.getParameter("userName");
		String password = (String) request.getParameter("password");

		if("on".equals(remember))//��ס�˺�
		{
			Cookie cookie = new Cookie("username", username);
			cookie.setMaxAge(7*24*60*60);
			cookie.setPath("/");
			response.addCookie(cookie);
			System.out.println("add cookie!");
		}else
		{
			Cookie cookie = new Cookie("username", "");
			cookie.setMaxAge(7*24*60*60);
			cookie.setPath("/");
			response.addCookie(cookie);
			System.out.println("delete cookie!");
		}
		
		Connection conn = null;
		SysUser user = null;
		String SystemAnnounce = "";
		
		MD5 m = new MD5();		
		
		try
		{
			conn = DataTools.getConnection(request);
			if(!conn.isClosed())
			{
					Cookie cookies[] = request.getCookies();
					if(null != cookies)
					{
						for(int i=0; i<cookies.length; i++)
						{
							if("username".equals(cookies[i].getName()))
							{
								System.out.println("name="+cookies[i].getValue());
								cookies[i].setValue("");//����cookiesΪ��
							}
						}
					}		
	
				System.out.println("LoginAction line90: conn is not closed!");
				//ȡ���û���Ϣ
				user = SysUserDao.getInstance().findByName(conn,username);
				//用户不存在（用户名不存在，用户removed离职了）
				if (user == null)
				{
					System.out.println("no user");
					request.getSession().setAttribute("msg", "User does not exist!");
					return mapping.findForward("index");
				}
				//add by dancy 2013/05/06 密码过期
				if(m.getMD5ofStr(password).equals(user.getPassword().toUpperCase())&&user.getExpireDay()>90)
				{
					request.getSession().setAttribute("msg", "Your account is expired, Please contact your super Data Checker or Data Approver to reset it!");
					return mapping.findForward("index");
				}
				//update by dancy 2013/05/06 密码不正确
				if (!m.getMD5ofStr(password).equals(user.getPassword().toUpperCase()))
				{
					request.getSession().setAttribute("msg", "Your password is wrong!");
					
					return mapping.findForward("index");
				}
				if(user.getExpireDay()>=75&&user.getExpireDay()<=90)
				{
					user.setAnnouncement("Please change your password in "+(90-user.getExpireDay())+" days !");
				}
				//System Announcement
				Statement stmt1 = conn.createStatement();
				SystemAnnounce = AnnounceTool.getInstance().getSysAnnounce(stmt1);	
				request.getSession().invalidate();
				request.getSession().setAttribute("user", user);
				request.getSession().setAttribute("SystemAnnounce", SystemAnnounce);
				request.getSession().setAttribute("isFirst", "yes");
				request.getSession().removeAttribute("msg");

			}
			else
			{
				System.out.println("LoginAction line116 conn is closed!!!");

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			throw e;
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		
		if (user.getLevelID() == 1)
		{
			return mapping.findForward("developer_01");
		}
		else if (user.getLevelID() == 2)
		{
			return mapping.findForward("administrator_01");
		}
		else
		{
			return mapping.findForward("data_checker_01");
		}	
	}
}
