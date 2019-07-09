<%@ page contentType="text/html; charset=GBK"
	import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/header.jsp"%>
<html:html>
<HEAD>
	<html:base />
	<TITLE></TITLE>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
	<link href="css/css.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
	<iframe src="" width="0" height="0" name="hide"></iframe>
	<html:form
		action="/SysUserAction.do?operate=save&operPage=administrator_03_edit_submit"
		target="hide">
		<!--标题栏-->
		<%@ include file="../include/MyInforHead.jsp"%>
		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td align="right" class="font14">
					<input name="Submit233" type="button" class="button" value="Save"
						onclick='javascript:saveinfo();'>
					<input name="Submit2332" type="button" class="button" value="Back"
						onclick='javascript:returnback();'>
				</td>
			</tr>
		</table>
		<html:errors />
		<font color="red">&nbsp;&nbsp;Do not enter password if you don't want to
			update!</font>
		<TABLE cellSpacing=0 cellPadding=0 width="100%"
			background=../images/main_bg.jpg border=0>
			<tr>
				<td width="25%" height="32" align="center">
					UserName:
				</td>
				<td width="75%" colspan="2">
					<logic:equal name="SysUserForm" property="sysUser.userId"
						value="-1">
						<html:text name="SysUserForm" property="sysUser.userName"
							styleId="userName" styleClass="input" size="20" maxlength="20" />
						<span class="style1">*</span>
					</logic:equal>
					<logic:notEqual name="SysUserForm" property="sysUser.userId"
						value="-1">
						<bean:write name="SysUserForm" property="sysUser.userName"
							filter="true" />
					</logic:notEqual>
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
					PermissionLevel:
				</td>
				<td width="75%" colspan="2">
					<html:select property="sysUser.levelId" styleId="levelId"
						style="width:190px">
						<html:optionsCollection property="permissionLevel"
							label="levelName" value="levelId" />
					</html:select>
					<span class="style1">*</span>
				</td>
			</tr>
		</TABLE>
		<html:hidden property="recid" />
		<html:hidden property="sysUser.userId" styleId="userId" />
		<efan:returnbuttontag />
	</html:form>
	<!-- 用户注册页面 -->
</BODY>
<script>
function logout() {
	var form = document.forms[0];
	form.target = "";
	form.action = "../LogoutAction.do?operate=logout";
	form.submit();
}

function saveinfo() {
	var form = document.forms[0];

	if (form.userId.value == "-1") {
		if (isNull(form.userName, "UserName")) {
			return;
		}
		if (isNull(form.password, "Password")) {
			return;
		}
	}
	form.submit();
}

function returnback() {
	var form = document.forms[0];
	form.target = "";
	form.action = "../SysUserAction.do?operate=search&operPage=administrator_03";
	form.submit();
}
function selFieldChange() {
	var form = document.forms[0];
	var elem = form.leaderflag;
	var value;
	for ( var i = 0; i < 2; i++) {
		if (elem[i].checked == true) {
			value = elem[i].value;
			break;
		}
	}
	if (value == "0") {
		document.all["span_leaderflag"].style.display = "";
	} else {
		document.all["span_leaderflag"].style.display = "none";
	}
}
//-->
</SCRIPT>
</html:html>