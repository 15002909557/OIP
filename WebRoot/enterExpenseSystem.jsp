<%@ page contentType="text/html; charset=GBK"%>

<%
	String uid = (String)request.getParameter("id");
	//System.out.println("uid in jsp is "+uid);
%>
<html:base />
<HTML>
	<HEAD>
		<TITLE>Login</TITLE>
		<style type="text/css">
<!--
td,br {
	font-family: "宋体";
	font-size: 9pt;
	color: #000000;
}

.text {
	font-family: "宋体";
	font-size: 9pt;
	color: #000000;
	margin: 0px;
	height: 20px;
	width: 150px;
	border: 1px solid #CCCCCC;
	padding-top: 0px;
	padding-right: 0px;
	padding-bottom: 0px;
	padding-left: 0px;
}

body {
	background-color: #FFFFFF;
	margin: 0px;
}
-->
</style>

<script type="text/JavaScript">
<!--
	function openWin() {
		window.open ("reg.html","","width=595;height=152") ;
	}
//-->
</script>
	</HEAD>
	<BODY>
		<!-- ImageReady Slices (管理系统-用户登录) -->
		<div align="center">
			<html:form action="/LoginAction.do?operate=login&operPage=index" method="post">
				<table height="20%" width="300" BORDER=0 CELLPADDING=0 CELLSPACING=0 align="center">
				<tr><td style="font-size: 21px;font-family: Arial">Workload Management login...</td></tr>
				</table>
				<div align="center" name="logintable" id="logintable" style="display:none;">
				<TABLE WIDTH=414 BORDER=0 CELLPADDING=0 CELLSPACING=0 align="center">
					<TR>
						<TD WIDTH=414 HEIGHT=81>
							<input type="text" name="userid" id="userid" value="<%=uid%>">
						</TD>
					</TR>
				</TABLE>
				</div>
				<!-- End ImageReady Slices -->
			</html:form>
		</div>
	</BODY>
<script>
setTimeout("login()",1600);
function login() {
	var infoForm = document.forms[0];

	trimAllText();
	
	infoForm.submit();
}
function enterKey() {
	if(event.keyCode ==13){   
  		login();
  		event.returnValue = false;  
  	}
}
</script>
</HTML>