<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*"
	contentType="text/html; charset=GBK"%>
<%
	Project project = (Project) request.getAttribute("Project");
	//System.out.println("project.getid="+project.getProjectId());
	//GroupName
	String gname = (String) request.getAttribute("gname");
	//GroupId
	String gid = (String) request.getAttribute("gid");
	//skillLevellist,locationlist,OTTypelist,projectNames
	@SuppressWarnings("unchecked")
	List<String> skillLevellist = (List)request.getAttribute("skillLevellist");
	@SuppressWarnings("unchecked")
	List<String> locationlist = (List)request.getAttribute("locationlist");
	@SuppressWarnings("unchecked")
	List<String> OTTypelist = (List)request.getAttribute("OTTypelist");
	@SuppressWarnings("unchecked")
	List<String> userlist = (List)request.getAttribute("userlist");
	@SuppressWarnings("unchecked")
	List<String> componentlist = (List)request.getAttribute("componentlist");
	@SuppressWarnings("unchecked")
	List<String> productlist = (List)request.getAttribute("productlist");
	@SuppressWarnings("unchecked")
	List<String> wbslist = (List)request.getAttribute("wbslist");

	//groupList
	@SuppressWarnings("unchecked")
	List<Groups> glist = (List)request.getAttribute("glist");
	

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Edit Project Info</title>
	<link href="css/style1024.css" rel="stylesheet" id="style2" type="text/css" />
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
<div class="editPage2" style="padding-top:16px; height:235px;">
	<form action="DataCheckerAction.do?operate=modifyProject&operPage=data_checker_04_edit&GID=<%=gid %>&GroupsName=<%=gname %>&&ff=edit" method="post">
	<h3>Edit Project Info</h3> 
	<input type="hidden" id="PID" name="PID" value="<%=project.getProjectId() %>" />
	ID: <%=project.getProjectId() %><br />
	Component: 
	<select name="Component" id="Component" style="width:120px">
		<option value="<%=project.getComponentid() %>"><%=project.getComponent() %></option>
	<%
	for(int i=0;i<componentlist.size();i+=2)
	{
		if(!componentlist.get(i).equals(project.getComponent()))
		{%>
		<option title="<%=componentlist.get(i+1) %>" value="<%=componentlist.get(i+1) %>"><%=componentlist.get(i) %></option>
	<%
		} 
	}
	%>
	</select>
	Product: 
	<select name="Product" id="Product" style="width:120px; margin-left:4px;">
		<option value="<%=project.getProduct() %>"><%=project.getProduct() %></option>
		<%
		for(int i=0;i<productlist.size();i++)
		{
			if(!project.getProduct().equals(productlist.get(i)))
			{%>
				<option title="<%=productlist.get(i) %>" value="<%=productlist.get(i) %>"><%=productlist.get(i) %></option><%
			}
		}
		%>
	</select>
	WBS:
	<select name="wbs" id="wbs" style="width:120px; margin-left:34px;">
		<option value="<%=project.getWBS() %>"><%=project.getWBS() %></option>
		<%
		for(int i=0;i<wbslist.size();i++)
		{
			if(!wbslist.get(i).equals(project.getWBS()))
			{
				%><option title="<%=wbslist.get(i) %>" value="<%=wbslist.get(i) %>"><%=wbslist.get(i) %></option>
		<%
			}
		}

		%>
	</select>
	User: 
	<select name="user" style="width:120px;margin-left:8px;">
	<option value="<%=project.getUserId() %>"><%=project.getUsername() %></option>
	<%
		for(int i=0;i<userlist.size();i+=2)
		{
			if(!userlist.get(i+1).equals(project.getUsername()))
			{
	%>
	<option title="<%=userlist.get(i+1) %>" value="<%=userlist.get(i) %>" >
		<%=userlist.get(i+1) %>
	</option>
	<%
			}
		}
	%>
	</select>	
	Confirm: <input name="Confirm" type="checkbox" <%if(project.getConfirm()>0){%>checked="checked"<%}%> style="border:0px;"/>
	<br />
	Skill Level: 
	<select name="SkillLevel" id="skillLevel" style="width:120px; margin-left:8px;">
		<option title="<%=project.getSkillLevel() %>" value="<%=project.getSkillLevel() %>"><%=project.getSkillLevel() %></option>
		<%
			for(int i=0;i<skillLevellist.size();i++)
			{
				if(!skillLevellist.get(i).equals(project.getSkillLevel()))
				%><option title="<%=skillLevellist.get(i) %>" value="<%=skillLevellist.get(i) %>"><%=skillLevellist.get(i) %></option><%
			}

		%>
	</select>
	Location: 
	<select name="Location" id="location" style="width:120px">
		<option value="<%=project.getLocation() %>"><%=project.getLocation() %></option>
		<%
		for(int i=0;i<locationlist.size();i++)
		{
			if(!locationlist.get(i).equals(project.getLocation()))
			{
		%>
		<option title="<%=locationlist.get(i) %>" value="<%=locationlist.get(i) %>"><%=locationlist.get(i) %></option>
		<%
			}
		}
		%>
	</select>	
	OTType: 
	<select name="OTType" id="OTType" style="width:120px;margin-left:19px;">
		<option value="<%=project.getOTType() %>"><%=project.getOTType() %></option>
		<%
		for(int i=0;i<OTTypelist.size();i++)
		{
			if(!OTTypelist.get(i).equals(project.getOTType()))
			{
		%>
		<option title="<%=OTTypelist.get(i) %>" value="<%=OTTypelist.get(i) %>"><%=OTTypelist.get(i) %></option>
		<%
			}
		}	
		%>
	</select>	
	Group: 
	<select name="move" style="width:120px">
		<option title="<%=gname %>" value="<%=gid %>"><%=gname %></option>
		<%
			for(int i=0;i<glist.size();i++)
			{
				if(!glist.get(i).getGname().equals(gname))
				%><option title="<%=glist.get(i).getGname() %>" value="<%=glist.get(i).getGid() %>"><%=glist.get(i).getGname() %></option><%
			}

		%>
	</select>
	
	Hidden: <input name="Hidden" type="checkbox" <%if(project.getHidden()>0){%>checked="checked"<%}%> style="border:0px; margin-left:4px;"/>
	<p>
	Comments: 
	<textarea name="Comments" id="comments" rows="2" class="input_text4" cols="60" style="height:36px; width:500px; text-align:left; margin-right:20px;">
	<%=project.getComments() %>
	</textarea>
	<input name="Submit2332" type="button" class="btnSave2" onclick='javascript:modify();' />
	<input name="Submit2332" type="button" class="btnBack" onclick='javascript:returnback();' />
	</p>
	<input type="hidden" name="gname" value="<%=gname %>"/>
	</form>
	</div>

<script type="text/javascript">

function modify()
{
	if($("#Component").val()==-1)
	{
		alert("Component should not be blank!");
		$("#Component").focus();
		return;
	}
	if($("#Product").val()==-1)
	{
		alert("Product should not be blank!");
		$("#Product").focus();
		return;
	}
	if($("#location").val()==-1)
	{
		alert("Location should not be blank!");
		$("#location").focus();
		return;
	}
	if($("#OTType").val()==-1)
	{
		alert("OTType should not be blank!");
		$("#OTType").focus();
		return;
	}
	if($("#OTType").val()==-1)
	{
		alert("OTType should not be blank!");
		$("#OTType").focus();
		return;
	}
	if($("#wbs").val()==-1)
	{
		alert("WBS should not be blank!");
		$("#wbs").focus();
		return;
	}
	var form = document.forms[0];
	form.submit();
}
function returnback()
{
	window.location.href = "DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID=<%=gid %>&gname=<%=gname %>&ff=back";
}

</script>
</body>
</html>