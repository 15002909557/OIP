<%@ page language="java" contentType="text/html; charset=GBK"
		import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%SysUser sysUser = (SysUser) request.getSession().getAttribute("user"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Administrator 1</title>
		<link href="css/style1024.css" rel="stylesheet" type="text/css" id="style2" />
		<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
		<script type="text/javascript">
		$(function()
		{
			if(screen.width==1360||screen.width==1366)
			{
				$("#style2").attr("href","css/style1366.css");
			}
			if(screen.width==1280)
			{
				$("#style2").attr("href","css/style1280.css");
			}
			if(screen.width==1440)
			{
				$("#style2").attr("href","css/style_new.css");
			}
			
		});
	</script>
	<style type="text/css">
		a:link, a:visited
		{
			text-decoration:none;
		}
		a:hover, a:active
		{
			text-decoration:underline;
		}
		</style>
	</head>
	<body>
		<form action="AdministratorAction.do?operate=searchPermissionLevels&operPage=administrator_02" method="post">
			<table width="750" border="0" cellpadding="0" cellspacing="0" align="center">
				<tr align="left">
					<td height="80">
						<a href="javascript:administrator_02();">
							<font color="#1366A4">Set The System</font>
						</a>
					</td>
				</tr>
				<tr align="left">
					<td height="80">
						<a href="javascript:administrator_03();">
							<font color="#1366A4">Set The Users</font> 
						</a>
					</td>
				</tr>
				<tr align="left">
					<td height="80">
						<a href="javascript:administrator_08();">
							<font color="#1366A4">Set The System Annoucement</font> 
						</a>
					</td>
				</tr>
				<tr align="left">
					<td height="80">
						<a href="javascript:administrator_04();">
							<font color="#1366A4">Set The Date</font> 
						</a>
					</td>
				</tr>
				<!-- hanxiaoyu01 2012-12-10 增加set email 项 -->
				<tr align="left">
					<td height="80">
						<a href="javascript:administrator_05();">
							<font color="#1366A4">Set The Email</font> 
						</a>
					</td>
				</tr>
			</table>
</form>
<script type="text/javascript">
function logout() {
	var form = document.forms[0];
	form.action = "LogoutAction.do?operate=logout";
	form.submit();
}
function administrator_02() {
	var form = document.forms[0];
	form.submit();
}
function administrator_03() {
	var form = document.forms[0];
	form.action = "SysUserAction.do?operate=search&operPage=administrator_03";
	form.submit();
}
function administrator_08() {
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=searchSysAnnounce&operPage=administrator_08";
	form.submit();
}
function administrator_04()
{
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=getAdministrator04&operPage=administrator_04";
	form.submit();
}
//hanxiaoyu01 2012-12-10增加方法转发到邮箱设置页面
function administrator_05()
{
	var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=searchEmail&operPage=administrator_email";
	form.submit();
}
</script>
</body>
</html>