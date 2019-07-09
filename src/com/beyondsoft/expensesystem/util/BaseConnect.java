package com.beyondsoft.expensesystem.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseConnect
{
	private static DataSource dataSource = null;
	
	static
	{
		try
		{
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql2");
		}
		catch(NamingException e)
		{
			e.printStackTrace();
		}
	}
	
	public static DataSource newInstance()
	{
		return dataSource;
	}
}