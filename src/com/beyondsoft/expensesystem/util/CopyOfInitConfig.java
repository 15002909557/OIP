package com.beyondsoft.expensesystem.util;

import tools.xml.XMlParser;

public class CopyOfInitConfig
{
	public static void init()
	{
		String url = CtrActionServlet.context.getRealPath("/")
				+ "/WEB-INF/classes/tools-config.xml";
		try
		{
			XMlParser.readXmlFile(url);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
