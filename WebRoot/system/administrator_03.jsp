<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="../include/header.jsp"%>

<html:html locale="true">
<head>
	<html:base />
	<title>administrator03</title>
	<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
	<html:form
		action="/SysUserAction.do?operate=search&operPage=administrator_03">
		<!--标题栏-->
		<%@ include file="../include/MyInforHead.jsp"%>
		<table align="center" width="60%" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<td align="center">
					<input type="button" class="button" value="Create An Account"
						onclick="javascript:addfile();" style="">
				</td>
				<td align="center">
					<input type="button" class="button" value="Disable Account(s)"
						onclick="javascript:deletefile();" style="">
				</td>
				<td align="center">
					<input type="button" class="button" value="Enable Account(s)"
						onclick="javascript:enablefile();" style="">
				</td>
			</tr>
		</table>
		<!--列表栏-->
		<font color="red"><html:errors /> </font>
		<table align="center" width="66%" border="0" cellpadding="3" cellspacing="1"
			class="bgcolor">
			<tr class="tr_title">
				<td width="3%">
					<img src="../images/hit.gif" width="20" height="20" id="idCheckAll"
						onclick="checkAll()">
				</td>
				<td align="center">
					UserName
				</td>
				<td align="center">
					Password
				</td>
				<td align="center">
					LevelID
				</td>
				<td align="center">
					Disabled
				</td>
				<td align="center">
					Approve level
				</td>
			</tr>
			<logic:present name="list">
				<logic:iterate id="listrow" name="list" scope="request">
					<tr class="tr2" height="30">
						<td align="center">
							<input type="checkbox" name="recid"
								value="<bean:write name='listrow' property='user_id' filter='true'/>" />
						</td>
						<td align="center">
							<bean:write name="listrow" property="username" filter="true" />
						</td>
						<td align="center">
							<bean:write name="listrow" property="password" filter="true" />
						</td>
						<td align="center">
							<bean:write name="listrow" property="levelid" filter="true" />
						</td>
						<td align="center">
							<bean:write name="listrow" property="account_disabled" filter="true" />
						</td>
						<td align="center">
							<LABEL id="<bean:write name='listrow' property='user_id'/>L" 
									onclick="toModifyApprove('<bean:write name='listrow' property='user_id'/>');">
									<bean:write name="listrow" property="approvelevel" filter="true" /></LABEL>
							<input name="<bean:write name='listrow' property='user_id'/>I" 
								   id="<bean:write name='listrow' property='user_id'/>I" style="display: none;" 
									maxlength="2" size="5" 
									value='<bean:write name="listrow" property="approvelevel" filter="true" />'
									onclick="toModifyApprove('<bean:write name='listrow' property='user_id'/>');">
							<label id="<bean:write name='listrow' property='user_id'/>X"
									onclick="closeModifyApprove('<bean:write name='listrow' property='user_id'/>');"
									title="close" title="close" 
									style="display:none;">X</label>
							&nbsp;
							<img id="<bean:write name='listrow' property='user_id'/>M"
								  src="../images/onebit21.png" width="18" height="18"
								  onclick="javascript: modifyApprove('<bean:write name='listrow' property='user_id'/>');" 
								  title="save" alt="save"
								  style="cursor: hand;display: none" />
						</td>
					</tr>
				</logic:iterate>
			</logic:present>
		</table>
		<!--翻页栏-->
		<table align="center" width="90%" border="0">
			<tr>
				<td>
					<efan:pagecontroltag name='SysUserForm' property="pageModel" />
				</td>
				<td width="20">
					&nbsp;
				</td>
			</tr>
		</table>
		<hr>
		<center>
		<input type="button" class="button" value="Back"
						onclick="javascript:backpage();">
		</center>
		<html:hidden name='SysUserForm' property="recid" />
	</html:form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
//window.name = "win";
if('<bean:write name="SysUserForm" property="strErrors" filter="true"/>' == 'true')
{
	alert("Data saved successfully!");
}
if('<bean:write name="SysUserForm" property="strErrors" filter="true"/>' == 'delete')
{
	alert("Data deleted successfully!");
}
if('<bean:write name="SysUserForm" property="strErrors" filter="true"/>' == 'enable')
{
	alert("Data enable successfully!");
}
if('<bean:write name="SysUserForm" property="strErrors" filter="true"/>' == 'modify')
{
	alert("Data modify successfully!");
}
function logout() {
	var form = document.forms[0];
	form.action = "../LogoutAction.do?operate=logout";
	form.submit();
}
function deletefile()
{
	var form = document.forms[0];
	var recid = updateRecord(form.recid);
	if (recid == null || '' == recid)  return;
	form.action = "../SysUserAction.do?operate=delete&operPage=administrator_03&recid=" + recid;
	if (!confirm("Are you sure to delete this account?"))
		return;
	form.submit(); 
}
function enablefile()
{
	var form = document.forms[0];
	var recid = updateRecord(form.recid);
	if (recid == null || '' == recid)  return;
	form.action = "../SysUserAction.do?operate=enable&operPage=administrator_03&recid=" + recid;
	if (!confirm("Are you sure to enable this account?"))
		return;
	form.submit(); 
}

function addfile() 
{
	var form = document.forms[0];
	form.action = "../SysUserAction.do?operate=toinsert&operPage=administrator_03_edit";
	form.submit();
}

function editfile(recid) 
{
	var form = document.forms[0];	
	form.action = "../SysUserAction.do?operate=toedit&operPage=administrator_03_edit&recid=" + recid;
	form.submit();
	//a=window.showModalDialog("../system/SysUserAction.do?operate=toedit&operPage=system_user_edit&recid="+recid,'','dialogWidth:400px;dialogHeight:300px;status:no');
	//if(a != null){
		//form.submit()
	//}
}
function updateRecord(elem)
{
	var lean = false;
	var returnValue = "";
	
	if(elem == undefined) {
		return;
	}
	if(isNaN(elem.length)){
		if(elem.checked == true)
		{
			returnValue += elem.value + ",";
			lean = true;
		}
	}else{
		for(var i = 0; i < elem.length; i++)
		{
			if(elem[i].checked == true)
			{
				returnValue += elem[i].value + ",";
				lean = true;
			}
		}
	}
	returnValue = returnValue.substr(0,returnValue.length - 1) + "";
	if(!lean){
		alert("Please select a record!"); 
		return null;
	}
	//if (returnValue.split(",").length > 1)
	//{
		//alert("只能选择一条记录！");
		//return null;
	//}
	return returnValue;
}
//-->
function backpage()
{
	var form = document.forms[0];	
	form.action = "../AdministratorAction.do?operate=choose&operPage=administrator_01";
	form.submit();
}
//////////////////////////////////////modify Approve
function toModifyApprove(id)
{
	//label 消失
	document.getElementById(id+'L').style.display="none";
	//input 显示 设置焦点
	document.getElementById(id+'I').style.display="";
	document.getElementById(id+"I").select();
	//X 显示
	document.getElementById(id+"X").style.display="";
	//modify 显示
	document.getElementById(id+"M").style.display="";
}
function closeModifyApprove(id)
{
	//label 显示
	document.getElementById(id+'L').style.display="";
	//input 消失 设置焦点
	document.getElementById(id+'I').style.display="none";
	//X 消失
	document.getElementById(id+'X').style.display="none";
	document.getElementById(id+"M").style.display="none";
	
	document.getElementById(id+'I').value = document.getElementById(id+'L').innerText;
}
function modifyApprove(id)
{
	var text = document.getElementById(id+'I').value;
	if(''==text)
	{
		alert("Can not be null!");
		return;
	}
	else
	{
		var form = document.forms[0];	
		form.action = "../SysUserAction.do?operate=mofifyApprove&operPage=administrator_03&id="+id;
		form.submit();
	}
}
</SCRIPT>
</html:html>