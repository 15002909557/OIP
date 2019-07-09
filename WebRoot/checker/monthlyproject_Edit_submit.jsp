<%@ page contentType="text/html;charset=GBK"%> 
<%@ include file="../include/header.jsp"%>
<%
	String result = (String) request.getAttribute("result");
%>

<script>
var re = '<%=result%>';

if('1'==re)
{
	alert("Data saved successfully!");
	parent.back();
}
else if('0'==re)
{	alert("Data saved failed! Please check that whether the Monthly Project already exists!");
}
else if('-1'==re)
{	alert("Data saved failed!");
}

</script>