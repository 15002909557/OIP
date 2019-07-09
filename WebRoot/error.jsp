
<%
	String host=request.getContextPath();
    if (host == null)
    {
		host = "/WebRoot";
	}
	
%>
<link href="<%=host%>/css/style.css" rel="stylesheet" type="text/css">
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0" background="<%=host%>/images/main_bg.jpg">
  <tr>
    <td align="center" valign="middle"><img src="<%=host%>/images/error.gif" width="500" height="180" border="0" usemap="#Map"></td>
  </tr>
</table>
<map name="Map">
  <area shape="rect" coords="397,64,468,79" href="<%=host%>/system/login.jsp" target="_top">
</map>