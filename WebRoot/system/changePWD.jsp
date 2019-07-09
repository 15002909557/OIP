<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.text.SimpleDateFormat,java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"
%>
<%
	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
	String result = (String) request.getAttribute("result");
	System.out.println("change result in jsp: " + result);
	String username=request.getParameter("username");
	if(username==null)
	{
		username = sysUser.getUserName();
	}
	// User List
	@SuppressWarnings("unchecked")
	List<SysUser> Userlist = (List<SysUser>) request.getSession().getAttribute("userList_changePWD");
%>

<html>
	<head>
		<title>Change your password</title>
		<link rel="stylesheet" href="css/tipsy.css" type="text/css" />
		<link rel="stylesheet" href="css/tipsy-docs.css" type="text/css" />
		<script type="text/javascript" src="js/jquery.tipsy.js"></script>
		<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
		<link href="css/style_new.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function change()
	{	
	    		   
		var result = 1;
		//密码规则
		var reg1 = /^.{12,20}/;
		var reg2 = /[a-zA-Z]+/;
		var reg3 = /[0-9]+/;
		var reg4 = /[!@#=\-_]+/;
		//密码规则
		var pa = document.getElementById("newpassword").value;
		
		if(''==document.getElementById("username").value)
		{
			alert("Please enter your account name!");
			document.getElementById("username").focus();
			return;
		}
		if('none' !=document.getElementById("oldpassword").style.display)
		{
			if(''==document.getElementById("oldpassword").value)
			{
				alert("Please enter your old password!");
				document.getElementById("oldpassword").focus();
				return;
			}
		}
		if(''==document.getElementById("newpassword").value)
		{
			alert("Please enter your new password!");
			document.getElementById("newpassword").focus();
			return;
		}
		if(!reg1.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			alert("Length of newpassword should between 12 to 20!");
			return;
		}	
		if(!reg2.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			alert("The newpassword should contain letters!");
			return;
		}
		if(!reg3.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			alert("The newpassword should contain numbers!");
			return;
		}
		if(!reg4.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			alert("The newpassword should contain special characters(!@#=-_)!");
			return;
		}
		if(result == 1)
		{
			document.all.msgPWD.innerHTML='OK';
		}
		if(''==document.getElementById("renewpassword").value)
		{
			alert("Please enter the confirm new password!");
			document.getElementById("renewpassword").focus();
			return;
		}
		if(document.getElementById("renewpassword").value != document.getElementById("newpassword").value)
		{
			alert("Please confirm your new password!");
			document.getElementById("renewpassword").value = '';
			document.getElementById("newpassword").value = '';
			document.getElementById("newpassword").focus();
			return;
		}
		//document.all.msg.innerHTML="<font color='green'>Change Password Success!<br>Please use the new password at next logon.</font>";
		var form = document.forms[0];		
		    form.submit();		
		    
 }  
	function enterKey(e){
	 var currKey=0;
	  e=e||event;
		if(e.keyCode==13)
		{
		 //alert("可以按enter键可以提交了");
		 change();
		 event.returnValue=false;	 
		}
	}
	function userChange()
	{
		var selfname = '<%=sysUser.getUserName()%>';
		var selectname =document.getElementById("username").value;
		//alert(selfname+":"+selectname);
		
		document.getElementById("oldpassword").value = "";
		document.getElementById("newpassword").value = "";
		document.getElementById("renewpassword").value = "";
		
		 //clear the change PWD result message
		$("#changeResult").text("");
		
		if(selfname == selectname)
		{
			document.getElementById("oldpassword").style.display = "";
			document.all.msg.innerHTML='';
		}
		else
		{  
		    document.getElementById("oldpassword").style.display = "none"; 
            document.all.msg.innerHTML='Old password is unnecessary when change others password.';    
              		   	
		}	  		
	}
	function testPWD()
	{
	
		var result = 1;
		//密码规则
		var reg1 = /^.{12,20}/;
		var reg2 = /[a-zA-Z]+/;
		var reg3 = /[0-9]+/;
		var reg4 = /[!@#=\-_]+/;
		//密码规则		
		var pa = document.getElementById("newpassword").value;
		if(!reg1.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			document.all.msg.innerHTML='Length of newpassword should between 12 to 20!';
			return;
		}	
		if(!reg2.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			document.all.msg.innerHTML='The newpassword should contain letters!';
			return;
		}
		if(!reg3.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			alert("The newpassword should contain numbers!");
			return;
		}
		if(!reg4.test(pa))
		{
			result = 0;
			document.all.msgPWD.innerHTML='';
			document.all.msg.innerHTML='The newpassword should contain special characters(!@#=-_)!';
			return;
		}
		if(result == 1)
		{
			document.all.msg.innerHTML='';
			document.all.msgPWD.innerHTML='OK';
		}
		else
		{
			document.all.msgPWD.innerHTML='';
		}
	}
	$(function (){
		$('#oldpassword').tipsy({gravity: 'w'});
		$('#newpassword').tipsy({gravity: 'w'});
		$('#renewpassword').tipsy({gravity: 'w'});
	});
</script>
	</head>
	<body>
		<center>
		<div style="width:400px; line-height:30px; text-align:left; margin-top:50px;">		
			<form action="SysUserAction.do?operate=changePWD&operPage=changePWD" method="post">
						<label id="msg"></label>
						<label id="changeResult">
					<%
						if(null == result || "".equals(result))
						{
					%>
						&nbsp;
					<%
						}else
						{
					%>
						<%=result %>
					<%
						}
					%>
					</label>
					<br />
					User Name:
				<% 
					if(3==sysUser.getLevelID() || 4==sysUser.getLevelID())
					{
				%>
				
						<select name="username" id="username" onchange="javascript: userChange();" style="border:1px #ccc solid; margin-left:68px;">
							<%
								for (int i = 0; i < Userlist.size(); i++) 
								{
							%>
									<option value='<%=Userlist.get(i).getUserName() %>' title='<%=Userlist.get(i).getUserName() %>'
											<%
											if(Userlist.get(i).getUserName().equals(username))
											{
												%> selected="selected" <%
											}
											%>
											>
										<%=Userlist.get(i).getUserName()%>
									</option>
							<%
								}
							%>
						</select>
				<%
					}
					else
					{
				%>
						<input type="text" id="username" name="username" class="input_text4" style="margin-left:68px;" value="<%=sysUser.getUserName() %>">
				<%
					}
				%>
					<br />
					Old Password:
					<input type="password" id="oldpassword" name="oldpassword" class="input_text4" style="margin-left:52px;" title="Please enter your old password.">
					<br />
					New Password:
					<input type="password" id="newpassword" name="newpassword" class="input_text4" style="margin-left:48px;" onchange="javascript: testPWD();"
							title="Length should between 12 and 20,Contain letters, numbers, special characters(!@#=-_).">
					<label id="msgPWD" style="color: green;"></label>
					<br />
					Confirm New Password:
					<input type="password" id="renewpassword" class="input_text4"  name="renewpassword" 
							title="Please re-enter you new password." onkeydown="javascript:enterKey();">
					<br />
					<p style="text-align:center; margin-left:60px;">
					<input type="button" class="btnSave2" onkeydown="javascript:enterKey();" onclick="javascript:change();">
					</p>
		</form>
		</div>
		</center>
	</body>
</html>