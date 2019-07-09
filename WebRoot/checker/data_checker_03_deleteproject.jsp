<%@ page contentType="text/html;charset=GBK"%> 
<%@ include file="../include/header.jsp"%>
<%
	String result = (String) request.getAttribute("result");
%>
<script>
if('<%=result%>' == 'true')
{
	alert("Delete successfully!");
	parent.removeRow();
	
}
else
{
	alert('Delete fail!');
}

</script>