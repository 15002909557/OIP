<%@ page language="java" contentType="text/html; charset=GBK"
		import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/header.jsp"%>
<%
	String from = (String) request.getAttribute("from");
	String to = (String) request.getAttribute("to");
	if("null".equals(from) || null == from)
		from = "";
	if("null".equals(to) || null == to)
		to = "";
	
	String result0 = (String) request.getSession().getAttribute("result0");
	String result1 = (String) request.getSession().getAttribute("result1");
	String result2 = (String) request.getSession().getAttribute("result2");
	
	String step = (String) request.getAttribute("step");
	
	System.out.println("result0="+result0);
	System.out.println("result1="+result1);
	System.out.println("result2="+result2);
	System.out.println("step="+step);
%>
<html>
	<head>
	<title>administrator 04</title>
	<link rel="stylesheet" href="../css/style.css" type="text/css">

	</head>
<body>
	<form action="AdministratorAction.do?operate=choose&operPage=administrator_01" method="post">
	<!-- ±êÌâÀ¸ -->
	<%@ include file="../include/MyInforHead.jsp"%>
	<center>
	<table border="0">
		<tr>
			<td height="60"><font face=Symbol size="30">&#174;</font></td>
			<td>
			<input 
			<%if("1".equals(step)){ %>
			style="background-color: lightgreen;"
			<%}%>
			 name="Submit2332" type="button" class="button" value=" Update Date2 "
			 onclick="javascript: searchDate2();">
			 </td>
			 
			<td height="60"><font face=Symbol size="30">&#174;</font></td>
			<td>
			<input 
			<%if("2".equals(step)){ %>
			style="background-color: lightgreen;"
			<%}%>
			 name="Submit2332" type="button" class="button" value="Update Expensedate"
			 onclick="javascript: searchExpensedateCreatetime();">
			 </td>
			 
			<td height="60"><font face=Symbol size="30">&#174;</font></td>
			<td><input 
			<%if("3".equals(step)){ %>
			style="background-color: lightgreen;"
			<%}%>
			name="Submit2332" type="button" class="button" value=" Update Date "
			onclick="javascript: searchDate();">
			</td>
			
			<td height="60"><font face=Symbol size="30">&#174;</font></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<%if(null != result0 && "true".equals(result0)) {%>
				Success!
			<%}%>
			</td>
			<td colspan="2" align="center">
			<%if(null != result1 && "true".equals(result1)) {%>
				Success!
			<%}%>
			</td>
			<td colspan="2" align="center">
			<%if(null != result2 && "true".equals(result2)) {%>
				Success!
			<%}%>
			</td>
		</tr>
	</table>
	<p></p>
	<table border="0">
		<tr>
			<td height="120" align="center" colspan="3">
				<input name="Submit2332" type="button" 
					class="button" style="width: 100;height: 30" 
					value=" Execute " onclick="javascript: execute();"
					<%if("1".equals(step)){%>
					<%}else if("2".equals(step)){
						if(!"true".equals(result0)){%>
						disabled="disabled"
					<%}}else if("3".equals(step)){
						if(!"true".equals(result1)){%>
						disabled="disabled"
					<%}} %>
					>
			</td>
		</tr>
		<tr>
			<td>
				<font size="4">From</font> <label id="from"><%=from %></label>
				<input type="hidden" name="from" value="<%=from %>">
			</td>
			<td width="60">
				
			</td>
			<td>
				<font size="4">To</font> <label id="to"><%=to %></label>
				<input type="hidden" name="to" value="<%=from %>">
			</td>
		</tr>
	</table>
	</center>

	<hr>
	<center><input name="Submit2332" type="button" class="button" value="Back"
						onclick='javascript:backpage();'></center>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function execute()
{
	var step = '<%=step%>';
	var form = document.forms[0];
	if( '1' == step)
	{
		form.action = "AdministratorAction.do?operate=updateDate2&operPage=administrator_04";
	}
	else if( '2' == step)
	{
		form.action = "AdministratorAction.do?operate=updateExpensedataCreatetime&operPage=administrator_04";
	}
	else if( '3' == step)
	{
		form.action = "AdministratorAction.do?operate=updateDate&operPage=administrator_04";
	}
	
	form.submit();
}
function backpage()
{
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=choose&operPage=administrator_01";
	form.submit();
}
function searchDate2()
{
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=getAdministrator04&operPage=administrator_04";
	form.submit();
}
function searchExpensedateCreatetime()
{
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=getExpensedateCreatetime&operPage=administrator_04";
	form.submit();
}
function searchDate()
{
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=getDate&operPage=administrator_04";
	form.submit();
}
</SCRIPT>
</html>