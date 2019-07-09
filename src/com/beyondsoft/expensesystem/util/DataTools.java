package com.beyondsoft.expensesystem.util;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

public class DataTools {
	/**
	 * ��ȡexpensesystem���ݿ�����
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
			throw new Exception("�޷���ȡexpensesystem���ݿ����ӣ�");
		} else {
			return conn;
		}
	}

	/**
	 * ��ȡStatement����
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static Statement getStatement(HttpServletRequest request) throws
	 * Exception { Connection conn = getConnection(request); Statement stmt =
	 * conn.createStatement(); if (stmt == null) { throw new
	 * Exception("�޷���ȡstatement����"); } else { return stmt; } }
	 */
}
