<%@ page contentType="text/html; charset=GBK" import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>

<%
	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
	int defaultUI = sysUser.getUI();
	System.out.println("default UI is: "+defaultUI);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Data Checker 2</title>
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<link href="css/style_new.css" id="style2" rel="stylesheet" type="text/css" />
<script src="js/datapicker/jquery.ui.core.js"></script> 
<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" /> 

<script type="text/javascript" >
$(document).ready(function(){
	var myDate = new Date(); 
	var year=myDate.getFullYear();  
	var month=(myDate.getMonth()+1)+'';
	if(month.length==1)
	{
		month = '0' + month;
	}  
	var day=myDate.getDate()+''; 
	if(day.length==1)
	{
		day = '0' + day;
	}
	var curdate = year+"/"+month+"/"+day;
	
	$("#today" ).datepicker({changeMonth: true, changeYear: true });
	$("#today" ).datepicker( "option", "dateFormat", "yy/mm/dd" );	
	$("#today").val(curdate);		
	$("#go").click(function (){go();});
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
	<body>
	<center>
	<div class="tanchuang_wrap" id="aaaa">
		<div class="lightbox"></div>
		<div class="tanchuang_neirong">
			<img src="images/loading.gif" />
			<br />
			Loading projects may takes a while, please wait, thanks!
		</div>
	</div>
	<form action="DataCheckerAction.do?operate=search&operPage=data_checker_03&maxItemPerPage=10&indexPage=0"
		method="post">		
		<div class="main_box2">		
		<!-- hanxiaoyu01 2012-12-19 隐藏UI 和headorcount两个单选框 -->	
			<!--<div class="modeTable">
				<table border="0" cellspacing="1" cellpadding="3" width="450" class="tabbg" align="center">
					<tr class="tr_content">
						<td class="tr_title">Read with:
						</td>
						<td><input type="radio" value="1" name="UIradio" class="rd" id="firmware" <%if(1 == defaultUI){%>checked="checked"<%}%> />
						<label for="firmware">FirmWare UI</label>
						</td>
						<td><input type="radio" id="software" class="rd" name="UIradio" value="2" <%if(2 == defaultUI){%> checked="checked"<%}%> />
						<label for="software">SoftWare UI</label>
						</td>
					</tr>
					
					<tr class="tr_content">
						<td class="tr_title">Fill with:
						</td>
						<td><input type="radio" id="headcount" name="headorhour" class="rd" value="0" <%if(0 == sysUser.getHeadorHour()){%> checked="checked" <%}%> />
						<label for="headcount">HeadCount</label>
						</td>
						<td><input type="radio" id="hour" name="headorhour" class="rd" value="1" <%if(1 == sysUser.getHeadorHour()){%> checked="checked"  <%}%> />
						<label for="hour">Hour</label>
						</td>
					</tr>				
				</table>
			</div>			
			-->
			<!-- 增加两个隐藏框 -->
			<input type="hidden" value="1" name="UIradio" class="rd" id="firmware" />
			<input type="hidden" id="headcount" name="headorhour" class="rd" value="1"/>
			<div class="btnsLine">
				Choose a date:
				<input type="text" id="today" name="today" class="today2"/>&nbsp;<span style="color:red;">*</span>
			</div>			
			<div class="btnsLine">
				<input type="button" id="back" class="btnBack" onclick='javascript:returnback();' />
				<input type="button" id="go" class="btnLoad" />
			</div>	
		</div>
		</form>		
		</center>	
<script type="text/javascript"><!--
function go()
{
	var defaultUI = '<%=defaultUI%>';
	var strdefaulUI = '';
	switch(defaultUI)
	{
		case '0': strdefaulUI='none';break;
		case '1': strdefaulUI='Firmware UI';break;
		case '2': strdefaulUI='Software UI';break;
	}
	var date = document.getElementById("today").value;
	if(''==date)
	{
		alert("Please select a date!");
		return false;
	}
	
	document.forms[0].submit();
	return true;
	//hanxiaoyu01 2012-12-19隐藏原来UI 和headorhour的单选提交判断
	/**
	var radiobox = document.getElementsByName("UIradio");
	for(var i=0;i<radiobox.length;i++) //when i=0,value=1,UI=FW; when i=1,value=2,UI=SW;
	{
		if(true == radiobox[i].checked)
		{
			if((i+1)!=defaultUI && '0'!=defaultUI)   //when i=0,value=1,UI=FW; when i=1,value=2,UI=SW;
			{
				switch(defaultUI)
				{
					case '1': document.getElementById("firmware").style.color="purple";break;
					case '2': document.getElementById("software").style.color="purple";break;
				}
				if(!confirm("Your group default UI is "+strdefaulUI+", ignore?"))
				{
					document.getElementById("firmware").style.color="";
					document.getElementById("software").style.color="";
					return false;
				}
			}
			displayDiv('aaaa');//显示覆盖层
			document.forms[0].submit();
			return true;
		}
	}
	alert("Please select a UI type!");
	return false;
	*/
}
function returnback()
{
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=selectDate&operPage=data_checker_01";
	form.submit();
}
//author=longzhe
//用于显示覆盖层
function closeDiv(divId){ 
document.getElementById(divId).style.display = 'none'; 
} 
function displayDiv(divId){ 
document.getElementById(divId).style.display = 'block'; 
} 
--></script>
</body>
</html>