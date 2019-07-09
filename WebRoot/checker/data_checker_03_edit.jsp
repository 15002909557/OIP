<%@ page contentType="text/html; charset=GBK"
	import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/header.jsp"%>
<%
	//GroupName
	String gname = (String) request.getParameter("gname");
	//GroupId
	String gid = (String) request.getParameter("gid");
	
	//如果是从data_checerk_04_edit.jsp传过来 header="no" 不显示MyInforHead.jsp
	//如果不是从data_checerk_04_edit.jsp传过来 header="" 显示MyInforHead.jsp
	//header
	String header = (String) request.getAttribute("header");
%>
<html:html>
<HEAD>
	<html:base />
	<TITLE></TITLE>
<style type="text/css"> 
<!-- 
body,td,th { 
font-size: 12px; padding:0; margin:0; 
} 
.tanchuang_wrap{ width:100%; height:100%;position:absolute;left: 0px;top: 0px;z-index:100; display:none;} 
.lightbox{width:100%;z-index:101; height:100%;background-color:#ccc;filter:alpha(Opacity=80);-moz-opacity:0.5;opacity: 0.5; position:absolute; top:0px; left:0px;} 
.tanchuang_neirong{width:400px;height:160px;border:solid 0px #f7dd8c;background-color:#FFF;position:absolute;z-index:105;left: 33%;top: 3%;} 
--> 
</style> 
<script language="javascript"> 
function closeDiv(divId){ 
document.getElementById(divId).style.display = 'none'; 
} 
function displayDiv(divId){ 
document.getElementById(divId).style.display = 'block'; 
} 
</script> 
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
	<link href="css/css.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
	<div class="tanchuang_wrap" id="aaaa"> 
	<div class="lightbox"></div> 
	<div class="tanchuang_neirong">
	
	<embed src="../images/loading3.swf" width="400" height="160"></embed><br>
	Loading projects may takes about 1 minute, please wait, thanks!
	</div> 
	</div> 
	<iframe src="" width="0" height="0" name="hide"></iframe>
	
	<html:form
		action="/DataCheckerAction.do?operate=saveProject&operPage=data_checker_03_edit_submit"
		target="hide">
		<!--标题栏-->
		<%
			//如果是从data_checerk_04_edit.jsp传过来 header="no" 不显示MyInforHead.jsp
			if("no".equals(header))
			{
				%>
				<table width="100%">
					<tr>
						<td align="left" style="font-size: 13px; font-weight: bold;"">
							Group:&nbsp;<%=gname%>
						</td>
					</tr>
					<tr>
						<td>
							<hr>
						</td>
					</tr>
				</table>
				<%
			}
			//如果不是从data_checerk_04_edit.jsp传过来 header="" 显示MyInforHead.jsp
			else
			{
				%><%@ include file="../include/MyInforHead.jsp"%><%
			}
		%>
		<html:errors />
		<TABLE cellSpacing=0 cellPadding=0 width="100%"
			background=../images/main_bg.jpg border=0>
			<tr>
				<td width="25%" height="32" align="right">
					Comments:
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td width="75%" colspan="2" align="left">
					<html:text name="DataCheckerForm" property="project.comments"
						styleId="comments" styleClass="input" size="50" maxlength="100" />
				</td>
			</tr>
			<tr>
				<td height="32">
					&nbsp;
				</td>
				<td width="75%">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td height="32">
					&nbsp;
				</td>
				<td width="75%">
					<input name="Submit233" type="button" class="button" value="Save"
						onclick='javascript:saveinfo();'>&nbsp;&nbsp;
					<input name="Submit2332" type="button" class="button" value="Back"
						onclick='javascript:returnback();'>
				</td>
			</tr>
		</TABLE>
		<html:hidden property="recid" />
		<html:hidden property="project.projectId" styleId="projectId" />
		<html:hidden property="today" styleId="today" />
		<efan:returnbuttontag />
	</html:form>
	<!-- 用户注册页面 -->
</BODY>
<script>
function saveinfo() {
	var form = document.forms[0];
	form.submit();
}

function returnback() {
	var form = document.forms[0];
	form.target = "";
	var gid = '<%=gid %>';
	var gname = '<%=gname %>';
	if(gid != 'null') //判断如果是从data_checker_04_edit传来
		form.action = "../DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID="+gid+"&GroupsName="+gname;
	else    //从data_checker_03传来
	{
		displayDiv('aaaa');//显示覆盖层
		form.action = "../DataCheckerAction.do?operate=search&operPage=data_checker_03";
	}
	
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
function logout() {
	var form = document.forms[0];
	form.target = "";
	form.action = "../LogoutAction.do?operate=logout";
	form.submit();
}
//-->
</SCRIPT>
</html:html>