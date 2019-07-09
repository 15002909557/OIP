<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"
	contentType="text/html; charset=GBK"%>
<%@ include file='../include/MyInforHead.jsp' %>
<%
	
	@SuppressWarnings("unchecked")
	List<Map> Permissionlist = (ArrayList<Map>) request.getAttribute("Permissionlist");
	@SuppressWarnings("unchecked")
	List<Map> Approvelist = (ArrayList<Map>) request.getAttribute("Approvelist");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add a new user</title>
	<link href="css/style_new.css" rel="stylesheet"type="text/css" />		
	<script src="js/jquery-1.5.1.js"></script> 
</head>
	<body>
	<div class="editPage" style="padding-top:80px; line-height:29px;">
	<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
		<form name="new" action="SysUserAction.do?operate=addUser&operPage=data_checker_04_newUser_submit" method="post">
		<h3 style="margin-left:60px;">Add a User</h3>
		User Name : <input type="text" name="username" id="username" maxlength="50" class="input_text4" /><span class="style1">*</span><br />
		Password : <input type="password" name="password" id="password" maxlength="50" class="input_text4" style="margin-left:8px;" /><span class="style1">*</span><br />
		<!-- hanxiaoyu01 2012-12-25 把groupId由hidden框改成select下拉框 -->
		<!-- hanxiaoyu01 2013-01-25 把Permission Level与Group之前改成联动限制 -->
		System Level :  
		<select name="level" id="level" style="width:141px; border:1px #cccccc solid;" onchange="changeGroup()">
			<option value="-1"></option>
			<%
				for(int i=0;i<Permissionlist.size();i++)
				{
			%>
				<option value='<%=Permissionlist.get(i).get("levelId") %>'>
					<%=Permissionlist.get(i).get("levelName") %>
				</option>
			<%

				}
			%>
		</select><span class="style1">*</span><br />
		Group:
		<select name="groupId" id="groupId" style="margin-left:48px;width:140px;"></select>
		<span class="style1">*</span><br/>
		Approve Level :  
		<select name="approvelevel" id="approvelevel" style="width:141px; border:1px #cccccc solid;">
			<option value="-1"></option>
			<%
			for(int i=0;i<Approvelist.size();i++)
			{
			%>
			<option value='<%=Approvelist.get(i).get("id") %>'>
				<%=Approvelist.get(i).get("discribe") %>
			</option>
			<%
			}
			%>
		</select><span class="style1">*</span><br />
		Employee ID : <input type="text" id="employeeID" name="employeeID" class="input_text4"  style="margin-left:3px; width:138px;"/>	<br />
		Email : <input type="text" name="email" id="email" maxlength="60" class="input_text4" style="width:182px;" /><br />	
		<p>
		<input name="Submit2332" type="button" class="btnBack" onclick='javascript:backpage();' />	
		<input name="Submit2332" type="button" class="btnReset" onclick='javascript:reset();' /> 
		<input name="Submit2332" type="button" class="btnSubmit2" onclick='javascript:addUser();' />	
		</p>
	    </form>								
		</div>
<script type="text/javascript">
$.ajaxSetup({  
   async : false  
});
function checkEmployeeID()
{
	var reg=/^\d{0,6}$/;//只能输入至多6个字符长度的数字
	var reg1=/^[n][a]$/i;//只能输入字母组合“na”，不区分大小写
	var employeeID=document.getElementById("employeeID").value;
	if(reg.test(employeeID) || null==employeeID || reg1.test(employeeID))
	{
		return true;
	}
	if(''==employeeID)
	{
		return true;
	}
	alert("EmloyeeID is invalid!");
	return false;
}
function addUser()
{
	if($("#username").val()=='')
	{
		alert("username should not be blank!")
		$("#username").focus();
		return;
	}
	if($("#level").val()=='-1')
	{
		alert("permission level should not be blank!")
		$("#level").focus();
		return;
	}
	if($("#approvelevel").val()=='-1')
	{
		alert("approve level should not be blank!")
		$("#approvelevel").focus();
		return;
	}
	if(checkEmail()&&checkPWD()&&checkEmployeeID()&&checkUser())
	{
		var form = document.forms[0];
		form.action = "SysUserAction.do?operate=addUser&operPage=data_checker_04_newUser_submit";
		form.target = "hide";
		form.submit();
	}
}

function backpage()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=searchGroups&operPage=data_checker_04";
	form.target = "";
	form.submit();
}

function checkPWD(){
		var psw = document.getElementById("password").value;
		if( '' == psw)
		{
			alert("Please enter the password!");
			document.getElementById("password").focus();
		 	return false;
		}
		else
		{
		 	return true;
		}

}
function checkEmail()
{
	var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	var mail = document.getElementById("email").value;
	if(reg.test(mail) || ""==mail)
	{
		return true;
	}
	alert("Email address is invalid");
	document.getElementById("Email").value='';
	document.getElementById("Email").focus();
	return false;
	
}

function changeGroup(){
 $("#groupId").empty();
 var level=$("#level").val();
 $.post("SysUserAction.do",{"operate":"getGroups","level":level},function(result){
		for(var i=0;i<result.length;i++){
		 $("#groupId").append("<option value='"+result[i].gid+"'>"+result[i].gname+"</option>");
		}
			
	},"json");
}

//hanxiaoyu01 2013-01-28
function checkUser(){
var gid=$("#groupId").val();
var level=$("#level").val();
var result="";
 //判断当前除了All Data的Group中是否已存在Data Checker用户
 if(gid!=-1&&level==4){
  $.post("SysUserAction.do",{"operate":"checkUser","gid":gid},function(data){
    result=data;
  });
 }
 if(result=="false"){
   alert("Data Checker exised!");
   return false;
 }else{
   return true;
 }

}

</script>
</body>
</html>