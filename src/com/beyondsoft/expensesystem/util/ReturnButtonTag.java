package com.beyondsoft.expensesystem.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


@SuppressWarnings("serial")
public final class ReturnButtonTag extends BodyTagSupport {
	
    protected StringBuffer sbPage = new StringBuffer();

    @SuppressWarnings({ "unchecked", "deprecation" })
	public int doStartTag() throws JspException {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();		
		boolean afreshflag = false;
		boolean nextPageflag = false;
		//newLine("<form name=\"returninfoform\" method=\"post\" action=\"\">");
		Enumeration enumeration = request.getParameterNames();
		while(enumeration.hasMoreElements())
		{
			String element = (String)enumeration.nextElement();

			if(element.startsWith("pageModel") || element.startsWith("query_")){
				afreshflag = true;
				String[] args = request.getParameterValues(element);
				
				for(int i = 0; i < args.length; i++)
				{
					newLine("<input type=\"hidden\" name=\""+element+"\" value=\""+args[i]+"\">");
				}
				if (element.equals("pageModel.nextPageNo")) {
					nextPageflag = true;
				}
			}
		}
		if (afreshflag && nextPageflag) {
			newLine("<input type=\"hidden\" name=\"pageModel.afreshQuery\" value=\"true\">");
		}
		//newLine("</form>");
		
        try {
            pageContext.getOut().write(sbPage.toString());
            sbPage.setLength(0);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return EVAL_BODY_TAG;
    }

    private void newLine(String str) {
        sbPage.append(str + "\n");
    }
}