package com.beyondsoft.expensesystem.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
	public static Connection conn=null;
	public static Connection getConn(){
		DataSource ds=null;
		try{
			// 获得数据源对象
			Context context=new InitialContext();
			//expensesystem 数据源
			ds=(DataSource)context.lookup("java:comp/env/jdbc/mysql2");
			try {
				conn=ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}catch(NamingException e){
			e.printStackTrace();
		}
		
		return conn;
	}
}
