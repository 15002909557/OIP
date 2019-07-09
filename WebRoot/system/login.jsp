<%@ page contentType="text/html; charset=GBK"%>
<%
	String name = "";
	Cookie cookies[] = request.getCookies();
	if(null != cookies)
	{
		for(int i=0; i<cookies.length; i++)
		{
			if("username".equals(cookies[i].getName()))
			{
				System.out.println("name="+cookies[i].getValue());
				name=cookies[i].getValue();
			}
		}
	}
	String msg =(String)request.getSession().getAttribute("msg");
	System.out.println("message is: "+msg);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Workload System Login</title>
<link href="../css/style1024.css"  id="style0" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.5.1.js"></script>
<script type="text/javascript">
$(function(){
	
	$(".btnLogin").each(function(event)
	{
		$(this).click(function()
		{
			login();
		});	
		$(this).mouseover(function()
		{
			$(this).css('background','url(../images/login_down.png) no-repeat');
		});
		$(this).mouseout(function()
		{
			$(this).css('background','url(../images/login.png) no-repeat');
		});
	});
	
	if(screen.width==1360||screen.width==1366)
	{
		$("#style0").attr("href","../css/style1366.css");
	}
	if(screen.width==1280)
	{
		$("#style0").attr("href","../css/style1280.css");
	}
	if(screen.width==1440)
	{
		$("#style0").attr("href","../css/style_new.css");
	}
	if($("#passwaord").val=='')
	{
		
	}
	
});
</script>
</head>
<body onload="javascript:clsPWD();">
	<div class="logo"></div>
	<div class="main_bg"></div>	
	<div class="login_content">
		<div class="login_box">
			<form action="../LoginAction.do?operate=login2&operPage=index" method="post">
			<div class="title">Workload System</div>
			<div style="clear:both;">
				<div class="content_left">
					UserName:<input type="text" name="userName" id="userName" class="inputbox" size="20" onkeydown="javascript:enterKey();" />
					<p>
					Password:<input type="password" name="password" id="password" class="inputbox" size="21" onkeydown="javascript:enterKey();" />
					</p>
					<p>
					<input type="checkbox" name="remember" id="remember" title="Do not choose this if using public computers" style="margin-left:50px; border:0px; outline:0;" id="ckd"/><label id='remeberSTR' title="Do not choose this if using public computers" for="remember"> Remember my name</label>
					</p>
					<p style="color: red;margin-left: 30px">
					 <%if(msg!=null){ %>
					 <%=msg %>
					 <%} %>
					 </p>
				</div>
				<div class="content_right">
					<input type="button"  class="btnLogin"/>
				</div>
			</div>
			</form>
		</div>
		<div class="ad"></div>
	</div>
	<div class="foot">Copyright &copy; 2012-2013 <span style="color:blue;"><i>Beyondsoft</i></span> All rights reserved.&nbsp;&nbsp;&nbsp;</div>


<script type="text/javascript">
function isRemembered()
{
	var	yourname = "<%=name%>";	
	if('' != yourname)
	{
		$("#userName").val(yourname);
		$("#password").focus();
		document.getElementById('remember').click();
	}
	if(''!=yourname&&$("#password").val()!='')
	{
		<%
			request.getSession().removeAttribute("msg");
		%>
	}
} 
function login() {
	var infoForm = document.forms[0];
	if ($("#userName").val() == "") 
	{
		alert("Please enter username!");
		$("#userName").focus();
		return;
	}
	if ($("#password").val() == "") 
	{
		alert("Please enter password!");
		$("#password").focus();
		return;
	}
	infoForm.submit();
}

function enterKey(e) 
{  
    var currKey=0;
     e=e||event;
	if(e.keyCode==13)
	{   
  		login();
  		event.returnValue = false;  
  	}  
}
document.onkeydown=enterKey;
function clsPWD()
{
	//$("#password").val("");
	document.getElementById("password").value="";
	$("#userName").focus();
	isRemembered();
}
</script>
</body>
</html>