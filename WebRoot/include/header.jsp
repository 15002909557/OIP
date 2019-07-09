<%@ page language="java"%>

<%
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Cache-Control","Max-stale=0");
	response.setHeader("Pragma","no-cache");
	response.setContentType("text/html;charset=gb2312");
	
    String host=request.getContextPath();
    if (host == null)
    {
		host = "/WebRoot";
	}
%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-kingtake.tld" prefix="efan"%> 
<link href="<%=host%>/css/style_new.css" rel="stylesheet" type="text/css">
<link href="<%=host%>/css/calendar.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="<%=host%>/js/pub.js"></script>
<script language="JavaScript" src="<%=host%>/js/focus.js"></script>
<script language="JavaScript" src="js/jquery-1.5.1.js"></script>

