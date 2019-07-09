package com.beyondsoft.expensesystem.util;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;

@SuppressWarnings("serial")
public class CtrActionServlet extends ActionServlet 
{
	public static ServletContext context = null;
	
	public void init() throws ServletException
	{
		super.init();
		context = getServletContext(); 	
		InitConfig.init();
	}
}
