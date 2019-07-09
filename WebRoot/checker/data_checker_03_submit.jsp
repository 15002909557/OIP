<%@ page contentType="text/html;charset=GBK"%> 
<%@ include file="../include/header.jsp"%>
<%
	String result = (String) request.getAttribute("result");
	String curpage = (String) request.getAttribute("page");
%>

<script>
if('ok' == '<%=result%>')
{
	alert("Data saved successfully!");
	var curpage = '<%=curpage%>';
	parent.getPageSum();
	if('no'==curpage)
		parent.searchWithFilter();
	else
	{
		parent.turnpage('<%=curpage%>');
	}
}
else
{
	alert('Data does not saved!');
}

</script>