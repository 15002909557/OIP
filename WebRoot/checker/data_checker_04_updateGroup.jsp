<%@ page language="java" contentType="text/html; charset=GBK" import="com.beyondsoft.expensesystem.domain.checker.*"%>
<%@ include file='../include/MyInforHead.jsp' %>
<%
Groups group=(Groups)request.getAttribute("group");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>To Edit <%=group.getGname() %></title>
	<link href="css/style_new.css" rel="stylesheet" type="text/css" />
</head>
	<body>
		<div class="editPage" style="line-height:36px; padding-top:90px;">
		<form action="DataCheckerAction.do?operate=doUpdateGroup&operPage=data_checker_04" method="post">
				<h3 >Modify a group</h3>
				<input type="hidden" id="gid" name="groupId" value="<%=group.getGid() %>"/>
				Group name : <input type="text" name="gname" id="gname" value="<%=group.getGname() %>" style="border:1px #cccccc solid; width:218px;"/> <br />
				<div style="display:none;">		
				Group defaultUI : 
				<input type="radio" name="UIradio" value="1" id="FW" checked="checked"/>
					<label id="FWUI" for="FW">FirmWare UI</label>
					<input type="radio" name="UIradio" value="2" id="SW"/>
					<label id="SWUI" for="SW">SoftWare UI</label>
				</div>
				<p>
				Comments :  
				<textarea name="cms" id="cms" rows="4"  style="border:1px #cccccc solid;" cols="30"><%if(group.getComments()!=null){%><%=group.getComments()%><%} %></textarea>
				</p>
				<p style="margin-top:46px;">
				<input name="Submit2332" type="button" class="btnBack" onclick='javascript:backpage();' />
				<input name="Submit2332" type="button" class="btnReset" onclick='javascript:reset();' />
				<input name="Submit2332" type="button" class="btnSubmit2" onclick='javascript:updateGroup();' />
				</p>
		</form>
	    
		</div>

<script type="text/javascript">
function updateGroup()
{
	var radiobox = document.getElementsByName("UIradio");
	var gname = $("#gname").val();
	var gid=$("#gid").val();
	if(gname=='')
	{
		alert("GroupName should not be empty!");
		$("#gname").focus();
		return;
	}
	var result;
	//判断除了它本身外有没有与其重名的group name
	$.post("DataCheckerAction.do",{"operate":"checkGroupName","gid":gid,"gname":gname},function(data){
      result=data;
      if(result=="false"){
        alert("This Group is Exist!");
      }else{
       var form = document.forms[0];
	   form.submit();
      }
    });
	
}
function backpage()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=searchGroups&operPage=data_checker_04";
	form.submit();
}
</script>
	</body>
</html>