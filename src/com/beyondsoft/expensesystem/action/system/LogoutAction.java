package com.beyondsoft.expensesystem.action.system;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.beyondsoft.expensesystem.domain.system.SysUser;
import com.beyondsoft.expensesystem.util.BaseDispatchAction;

public class LogoutAction extends BaseDispatchAction
{
	/*
	 * 关闭浏览器时，launch的方法，用于删除session数据库里的用户记录和清除session
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//通过session得到用户id
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		int userid = user.getUserId();
		System.out.println("logout userid="+userid);

		//清除session
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		return mapping.findForward("index");
	}
}
