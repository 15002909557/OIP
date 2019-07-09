<%@ page language="java" contentType="text/html; charset=GBK"
		import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/header.jsp"%>
<%	
	String announce = (String)  request.getAttribute("Announcement");
%>
<html>
	<head>
		<title>Administrator_08</title>
		<link href="css/css.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="../css/style.css" type="text/css">
	</head>
	<body>
		
		<!-- 标题栏 -->
		<%@ include file="../include/MyInforHead.jsp"%>
		<form action="AdministratorAction.do?operate=setAnnouncement&operPage=administrator_01" method="post">
		<!--列表栏-->
		<table align="center">
			<tr class="tr_title" align="center">
				<td>
					System Announcement
				</td>
			</tr>
			<tr class="tr2" align="center">
				<td>
					<textarea name="Announce" id="Announce" rows="8" cols="80"><%
						if(announce != "" && null != announce)
						{
							out.print(announce);
						}
					%></textarea>
				</td>
			</tr>
			<tr class="tr2">
			<td>
					<input name="Submit2332" type="button" class="button" value="Save"
							onclick='javascript: setAnnounce();'>
							&nbsp;&nbsp;&nbsp;
					<input name="Submit2332" type="button" class="button" 
							value="Back" onclick='javascript: backpage();'>
				</td>
			</tr>
		</table>
		</form>

<script type="text/javascript">
function setAnnounce() {
	var form = document.forms[0];
	form.submit();
}
function logout() {
	var form = document.forms[0];
	form.action = "LogoutAction.do?operate=logout";
	form.submit();
}
function backpage() 
{
	var form = document.forms[0];
	form.target = "";
	form.action = "AdministratorAction.do?operate=choose&operPage=administrator_01";
	form.submit();
}
</script>
	</body>
</html>