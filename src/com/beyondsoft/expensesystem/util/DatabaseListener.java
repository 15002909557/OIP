package com.beyondsoft.expensesystem.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
/**
 * ����Դ������
 * ���ù���ֱ�Ӵ�ServletContext�в�����Ϊ��dataSource���Ķ�������Ϊ��DataSource��
 * @author zhangliyong
 *
 */
public class DatabaseListener implements ServletContextListener
{
	private DataSource dataSource = null;
	
	public void contextDestroyed(ServletContextEvent event)
	{
		// ��������Դ����
		if(dataSource != null)
			dataSource = null;
	}

	public void contextInitialized(ServletContextEvent event)
	{
		try
		{
			// �������Դ����
			Context context = new InitialContext();
			//expensesystem ����Դ
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql2");
			// ������Դ�������ȫ�ֱ�����
			event.getServletContext().setAttribute("dataSource", dataSource);
			
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
	}
}