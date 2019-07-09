<%@ page language="java" contentType="text/html; charset=GBK"
		import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/header.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String email=(String)request.getAttribute("email");
String sender=(String)request.getAttribute("sender");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>administrator_email</title>
	<link href="css/css.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="../css/style.css" type="text/css">
	
  </head>
  
  <body>
  <!-- ±êÌâÀ¸ -->
	<%@ include file="../include/MyInforHead.jsp"%>
	
    <form action="" method="post">
     <table align="center">
			<tr class="tr_title" align="center">
			  <td colspan="2">System Email</td>
			</tr>
			<tr class="tr2">
			  <td align="right">Email:</td>
			  <td><input type="text" id="email" name="email" value="<%=email %>"/></td>
			</tr>
			<tr>
			   <td align="right">From:</td>
			   <td><input type="text" id="sender" name="sender" value="<%=sender %>"/></td>
			</tr>
			<tr>
			   <td align="right">Password:</td>
			   <td><input type="password" id="psw" name="psw"/></td>
			</tr>
			<tr>
			<td colspan="2" align="center">
					<input name="Submit2332" type="button" class="button" value="Save"
							onclick='javascript: setEmail();'>
							&nbsp;&nbsp;&nbsp;
					<input name="Submit2332" type="button" class="button" 
							value="Back" onclick='javascript: backpage();'>
			</td>
			</tr>
		</table>
    </form>
<script type="text/javascript">
function setEmail(){
    var email=document.getElementById("email");
    var sender=document.getElementById("sender");
    var psw=document.getElementById("psw");
    if(email.value==""||email.value==null){
     alert("email can not be null!");
     email.focus();
     return false;
    }
    if(sender.value==""||sender.value==null){
     alert("From can not be null!");
     sender.focus();
     return false;
    }
     if(psw.value==""||psw.value==null){
     alert("Password can not be null!");
     psw.focus();
     return false;
    }
    var form = document.forms[0];
	form.action = "AdministratorAction.do?operate=setEmail&operPage=administrator_01";
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
