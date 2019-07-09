package com.beyondsoft.expensesystem.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter
{
	private FilterConfig config = null;
	
	public void destroy()
	{
		this.config = null;	
	}

	/* ���� Javadoc��
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException
	{
		try
		{
			String encoding = config.getInitParameter("encoding");
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
			chain.doFilter(request, response);
		}
		catch (IOException ex) 
		{
			System.out.println(request.getRemoteAddr() + " io error : " + ex);
		}
		catch (Throwable t) 
		{
			System.out.println("CommonFilter is: "+ t);
		}
	}

	public void init(FilterConfig config) throws ServletException
	{
		this.config = config;
	}
}