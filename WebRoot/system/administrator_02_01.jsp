<%@ page contentType="text/html; charset=GBK"
	import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/header.jsp"%>
<html>
	<head>
		<title>Administrator 2</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
		<link href="css/css.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<html:form
			action="/AdministratorAction.do?operate=searchPermissionLevels&operPage=administrator_02">
			<!--标题栏-->
			<%@ include file="../include/MyInforHead.jsp"%>
			<!--列表栏-->
			<font color="red"><html:errors /> </font>
			<table width="100%">
				<tr>
					<td WIDTH=40% valign="top">
						<table WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0>
							<tr>
								<td width="20%">
									&nbsp;
								</td>
								<td>
									Lists
								</td>
							</tr>
							<tr>
								<td width="20%">
									&nbsp;
								</td>
								<td>
									<select name="options" onchange="searchList();">
										<option value="PermissionLevels">
											PermissionLevels
										</option>
										<option value="PermissionNames" selected>
											PermissionNames
										</option>
										<option value="SkillLevels">
											SkillLevels
										</option>
										<option value="Locations">
											Locations
										</option>
										<option value="OTTypes">
											OTTypes
										</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
					<td width="60%" valign="top">
						<table width="80%" border="0">
							<tr>
								<td colspan="2">
									Details
								</td>
							</tr>
						</table>
						<table width="80%" border="0" cellpadding="3" cellspacing="1"
							class="bgcolor">
							<tr class="tr_title">
								<td align="center">
									PermissionID
								</td>
								<td align="center">
									PermissionName
								</td>
							</tr>
							<logic:present name="list">
								<logic:iterate id="listrow" name="list" scope="request">
									<tr class="tr2">
										<td align="center">
											<bean:write name="listrow" property="permissionid"
												filter="true" />
										</td>
										<td align="center">
											<bean:write name="listrow" property="permissionname"
												filter="true" />
										</td>
									</tr>
								</logic:iterate>
							</logic:present>
						</table>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
				<tr>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="center" class="font14">
						<input name="Submit2332" type="button" class="button" value="Back"
							onclick='javascript:returnback();'>
					</td>
				</tr>
			</table>
		</html:form>
	</body>
	<script type="text/javascript">
function logout() {
	var form = document.forms[0];
	form.action = "LogoutAction.do?operate=logout";
	form.submit();
}
function searchList() {
	var form = document.forms[0];
	var option = "";
	for ( var i = 0; i < form.options.length; i++) {
		if (form.options[i].selected == true) {
			var option = form.options[i].value;
		}
	}
	if (option == "PermissionLevels") {
		form.action = "AdministratorAction.do?operate=searchPermissionLevels&operPage=administrator_02";
		form.submit();
	} else if (option == "PermissionNames") {
		form.action = "AdministratorAction.do?operate=searchPermissionNames&operPage=administrator_02_01";
		form.submit();
	} else if (option == "SkillLevels") {
		form.action = "AdministratorAction.do?operate=searchSkillLevels&operPage=administrator_02_02";
		form.submit();
	} else if (option == "Locations") {
		form.action = "AdministratorAction.do?operate=searchLocations&operPage=administrator_02_03";
		form.submit();
	} else {
		form.action = "AdministratorAction.do?operate=searchOTTypes&operPage=administrator_02_04";
		form.submit();
	}
}
function returnback() 
{
	var form = document.forms[0];
	form.target = "";
	form.action = "AdministratorAction.do?operate=choose&operPage=administrator_01";
	form.submit();
}
</script>
</html>