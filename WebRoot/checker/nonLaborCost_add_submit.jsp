<%@ page contentType="text/html;charset=GBK"%> 
<%@ include file="../include/header.jsp"%>
<%
	String result = (String) request.getAttribute("result");
%>
<script>
var result = '<%=result%>';
if('true'==result)
{
	alert("Data saved successfully!");
	parent.returnback();
}
else
{
	alert("Data saved failed!");
}

</script>