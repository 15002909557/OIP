<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file='../include/MyInforHead.jsp' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add a sub group</title>
	<link href="css/style_new.css" rel="stylesheet" type="text/css" />
</head>
	<body>
		<div class="editPage" style="line-height:36px; padding-top:90px;">
		<form action="DataCheckerAction.do?operate=addGroup&operPage=data_checker_04" method="post">
				<h3 >Add a new group</h3>
				Group name : <input type="text" name="gname" id="gname" style="border:1px #cccccc solid; width:218px;"/> <br />
				<div style="display:none;">		
				Group defaultUI : 
				<input type="radio" name="UIradio" value="1" id="FW" checked="checked"/>
					<label id="FWUI" for="FW">FirmWare UI</label>
					<input type="radio" name="UIradio" value="2" id="SW"/>
					<label id="SWUI" for="SW">SoftWare UI</label>
				</div>
				<p>
				Comments :  
				<textarea name="cms" id="cms" rows="4" style="border:1px #cccccc solid;" cols="30"></textarea>
				</p>
				<p style="margin-top:46px;">
				<input name="Submit2332" type="button" class="btnBack" onclick='javascript:backpage();' />
				<input name="Submit2332" type="button" class="btnReset" onclick='javascript:reset();' />
				<input name="Submit2332" type="button" class="btnSubmit2" onclick='javascript:addGroup();' />
				</p>
		</form>
	    
		</div>

<script type="text/javascript">
function addGroup()
{
	var radiobox = document.getElementsByName("UIradio");
	var gname = $("#gname").val();
	if(gname=='')
	{
		alert("GroupName should not be empty!");
		$("#gname").focus();
		return;
		
	}
//Add the group check by FWJ 2013-12-13	
	$.post("DataCheckerAction.do",{"operate":"checkNewAddGroup","gname":gname},function(data){
		if(data=="1")
		{
			alert("The Group have been existed! Please enter a new Name!")
			return false;
		}
		else
		{
			for(var i=0;i<radiobox.length;i++)
			{
				if(true == radiobox[i].checked)
				{
					var form = document.forms[0];
					form.submit();
					return;
				}
				else
				{
					alert("Please select a UI type!");
					return false;
				}
			}
		}
	}
	);
	/*
	for(var i=0;i<radiobox.length;i++)
	{
		if(true == radiobox[i].checked)
		{
			var form = document.forms[0];
			form.submit();
			return;
		}
		else
		{
			alert("Please select a UI type!");
			return false;
		}
	}
	*/
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