<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../include/header.jsp"%>
<html:base />
<HTML>
	<HEAD>
		<TITLE>Register</TITLE>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
		<link href="css/css.css" rel="stylesheet" type="text/css">
	</HEAD>
	<BODY>
		<!-- 用户注册页面 -->
		<iframe src="" width="0" height="0" name="hide"></iframe>
		<html:form action="/SysUserAction.do?operate=save&operPage=register_submit" target="hide">
			<TABLE cellSpacing=0 cellPadding=0 width="100%"
				background=../images/main_bg.jpg border=0>
				<tr>
					<td width="25%" height="32" align="center">
						UserName:
					</td>
					<td width="75%" colspan="2">
						<html:text name="SysUserForm" property="sysUser.userName"
							styleId="userName" styleClass="input" size="20" maxlength="20" />
						<span class="style1">*</span>
					</td>
				</tr>
				<tr>
					<td height="32" align="center">
						Password:
					</td>
					<td width="75%" colspan="2">
						<html:password name="SysUserForm" property="sysUser.password"
							styleId="password" styleClass="input" size="8" maxlength="8" />
						<span class="style1">*</span>
					</td>
				</tr>
				<tr>
					<td height="32" align="center">
						Confirm Password:
					</td>
					<td width="75%" colspan="2">
						<html:password name="SysUserForm" property="pwd"
							styleId="pwd" styleClass="input" size="8" maxlength="8" />
						<span class="style1">*</span>
					</td>
				</tr>
				<tr>
					<td height="60" align="center">
						&nbsp;
					</td>
					<td colspan="3">
						<img src="../images/btnSure.gif" width="69" height="25"
							onclick='javascript:saveinfo();' style="cursor: hand">
						&nbsp;&nbsp;&nbsp;
						<img src="../images/btnReset.gif" width="69" height="26"
							onclick='javascript:returnback();' style="cursor: hand">
					</td>
				</tr>
			</TABLE>
		</html:form>
		<!-- 用户注册页面 -->
	</BODY>
	<script>
function saveinfo() {
	var form = document.forms[0];
	if (isNull(form.userName, "UserName")) {
		return;
	}
	if (isNull(form.password, "Password")) {
		return;
	}
	if (isNull(form.pwd, "Confirm Password")) {
		return;
	}
	if (form.password.value != form.pwd.value) {
		alert("Confirm Password should be the same with Password!");
		form.password.focus();
		return;
	}
	form.submit();
}
function returnback()
{
	window.location = "<%=host%>/system/login.jsp";
}
</script>
</HTML>