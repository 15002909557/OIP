package com.beyondsoft.expensesystem.util;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class DataTools {
	/**
	 * 获取expensesystem数据库连接
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection(HttpServletRequest request)
			throws Exception {
		DataSource source = (DataSource) request.getSession()
				.getServletContext().getAttribute("dataSource");
		Connection conn = source.getConnection();
		if (conn == null) {
			throw new Exception("无法获取expensesystem数据库连接！");
		} else {
			return conn;
		}
	}

	/**
	 * 获取Statement对象
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static Statement getStatement(HttpServletRequest request) throws
	 * Exception { Connection conn = getConnection(request); Statement stmt =
	 * conn.createStatement(); if (stmt == null) { throw new
	 * Exception("无法获取statement对象！"); } else { return stmt; } }
	 */
}
