<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"
	contentType="text/html; charset=GBK"%>
<%
	// User List
	SysUser user = (SysUser) request.getAttribute("user");
	//groupList
	@SuppressWarnings("unchecked")
	List<Groups> glist = (List)request.getAttribute("glist");
	//PermissionLevel List
	@SuppressWarnings("unchecked")
	List<PermissionLevel> pmlist =(List)request.getAttribute("pmlist");
	
	//GroupName
	String gname = (String) request.getAttribute("gname");
	//GroupId
	String gid = (String) request.getAttribute("gid");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>Edit User Info</title>
		<link href="css/style1024.css" id="style2" rel="stylesheet"type="text/css" />
		<base target="_self" />
		<script src="js/jquery-1.5.1.js"></script>
		<script type="text/javascript">
	
	    $(function()
	    {
	    	if(screen.width==1360||screen.width==1366)
			{
				$("#style2").attr("href","css/style1366.css");
			}
			if(screen.width==1280)
			{
				$("#style2").attr("href","css/style1280.css");
			}
			if(screen.width==1440)
			{
				$("#style2").attr("href","css/style_new.css");
			}
	    });
	    </script>
</head>
	<body id="editPage">
	<div class="editPage2">
		<h3>Edit User Info</h3>
		<form action="SysUserAction.do?operate=modifyUse&operPage=data_checker_04_edit_User&GID=<%=gid %>&GroupsName=<%=gname %>" method="post">		
		<%
		if(null != user)
		{
		%>
		<input type="hidden" name="UID" value="<%=user.getUserId() %>" />
		<input type="hidden" name="GID" value="<%=gid %>" />
		<div style="float:left; height:200px; width:200px;">
		<span style="margin-right:20px;">ID :<label style="color:black;"> <%=user.getUserId() %></label> </span>
		<p>User Name: <label style="color:black;"> <%=user.getUserName() %></label></p>
		</div>
		<div style="float:left; height:200px; width:700px;">										
		Employee ID: 
		<input name="EmployeeID" id="EmployeeID" type="text" class="input_text4" style="width:125px; margin-left:25px; margin-right:60px;" 
		<%if(null==user.getHPEmployeeNumber()||"null".equals(user.getHPEmployeeNumber())) {%> value="" <%}else{%>value="<%=user.getHPEmployeeNumber() %>"<% }%> />
		Email Address: <input name="Email" id="Email" type="text" class="input_text4" style="width:200px" value='<%=user.getEmail() %>' />
		<br />
		Permission Level:
		<select id="level" name="PermissionLevel" style="width:130px; margin-right:60px;" onchange="changeGroup()">
			<option title="<%=user.getLevel() %>" value="<%=user.getLevelID() %>"><%=user.getLevel() %></option>
			<%
					for(int i=0;i<pmlist.size();i++)
					{
						if(!pmlist.get(i).getLevelName().equals(user.getLevel()))
						%><option title="<%=pmlist.get(i).getLevelName() %>" value="<%=pmlist.get(i).getLevelId() %>"><%=pmlist.get(i).getLevelName() %></option><%
					}	
				%>
		</select> 
		Group : 
		<select id="groupId" name="move" style="width:205px; margin-left:46px;">
		   <%if(user.getLevelID()==1||user.getLevelID()==2||user.getLevelID()==3){ %>
		    <option value="-1">All Data</option>
		   <%}else{ 
		        for(int i=0;i<glist.size();i++){
		        if(glist.get(i).getGid()!=-1){
		   %>
		    <option title="<%=glist.get(i).getGname() %>" value="<%=glist.get(i).getGid() %>" 
		     <%if(glist.get(i).getGname().equals(user.getGroupName())){ %>
		     selected="selected"
		     <%} %>
		    ><%=glist.get(i).getGname() %></option>
		   <%   }
		       } 
		     }
		   %>
		</select>
		<p>
		Message:<textarea name="Massage" id="Massage" rows="2"  cols="200" 
		style="text-align:left; border:1px #ccc solid; height:36px; width:513px; margin-left:29px;"><%=user.getMassage() %></textarea>
		</p>
		<p style="text-align:center;">
		<input name="Submit2332" type="button" class="btnSave2" onclick='javascript:modify();' />
		<input name="Submit2332" type="button" class="btnBack" onclick='javascript:backpage();' />
		</p>
		</div>
		<%
		}
		else
		{
		String wait =  (String)request.getAttribute("wait");
			if(null == wait)//前一操作不为保存
			{
			%>
				There is something wrong.Please wait for a while and try again.
	
			<%
			}
			else//前一操作为保存
			{
			%>
				Save success!
	
			<%
			}
		}
		%>
		</form>
		<%
		if(null == user)
		{
		%>
		
		<input name="Submit2332" type="button" class="btnBack" onclick='javascript:backpage();' />

		<%
		}
		%>
		</div>

<script type="text/javascript">
$.ajaxSetup({  
   async : false  
});
function modify()
{
	if(checkEmail() && checkEmployeeID()&&checkUser())
	{
		var form = document.forms[0];
		form.submit();
	}
}
function backpage()
{
	window.location.href = "DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID=<%=gid %>&gname=<%=gname %>";
}
function checkEmail()
{
	var reg = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
	var mail = document.getElementById("Email").value;
	if(reg.test(mail) || ""==mail)
	{
		return true;
	}
	alert("Email address is invalid");
	document.getElementById("Email").focus();
	return false;
	
}
function checkEmployeeID()
{
	var reg=/^\d{0,6}$/;
	var reg1=/^[n][a]$/i;
	var employeeID=document.getElementById("EmployeeID").value;
	if(reg.test(employeeID) || null==employeeID || reg1.test(employeeID))
	{
		return true;
	}
	if(''==employeeID)
	{
		return true;
	}
	document.getElementById("EmployeeID").focus();
	alert("EmloyeeNumber is invalid!");
	return false;
}

function myKeyDown()
{
    var    k=window.event.keyCode;   
    if ((k==46)||(k==8)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) 
    {}
    else if(k==13){
         window.event.keyCode = 9;}
    else{
         window.event.returnValue = false;}
}
//hanxiaoyu01 2013-01-28
function changeGroup(){
 $("#groupId").empty();
 var level=$("#level").val();
 $.post("SysUserAction.do",{"operate":"getGroups","level":level},function(result){
		for(var i=0;i<result.length;i++){
		 $("#groupId").append("<option value='"+result[i].gid+"'>"+result[i].gname+"</option>");
		}
			
	},"json");
}

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