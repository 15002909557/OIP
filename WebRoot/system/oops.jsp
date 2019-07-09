<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../include/header.jsp"%>
<%
	String announce = (String) request.getAttribute("announce");
%>
<html:base />
<HTML>
	<HEAD>
		<TITLE>Oops</TITLE>
	</HEAD>
	<BODY>
	<br>
	<br>
	<br>
		<div align="center">
			<table height="20%" width="400" BORDER=0 CELLPADDING=0 CELLSPACING=0 align="center">
				<tr>
					<td style="font-size: 46px;font-family: Arial; color='red'"><img src="../images/Oops.png" width="36" height="36">Oops</td>
				</tr>
				<tr>
					<td style="font-size: 21px;font-family: Arial">OIP shutdown!</td>
				</tr>
				<tr>
					<td style="font-size: 16px;font-family: Arial">System Announcement: <%=announce %></td>
				</tr>
				<tr>
					<td align="right">
					<a href="http://203.93.238.253:7001/workload"><font color="#1366A4">Please wait for a while and try again.</font> </a>
					</td>
				</tr>
			</table>
		</div>
	</BODY>
</HTML>