<%@ page contentType="text/html; charset=GBK"%> 
<%@ include file="../include/header.jsp"%>
<%
	String result = (String) request.getAttribute("result");
	System.out.println("result="+result);
%>
<script>
var re = '<%=result%>';
if('0' == re)
{
	alert("Data saved successfully!");
	parent.backpage();
}
else if('-1'==re)
{
	alert("Data saved failed!Please retry.");
}
else if('1'==re)
{
	alert("This user is already exist!Please recheck.");
}

</script>