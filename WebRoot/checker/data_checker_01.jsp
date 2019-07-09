<%@ page contentType="text/html; charset=GBK" import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>	
<%
	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Workload System Host</title>
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
	<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function(){
	$(".nav").each(function(event)
	{
		$(this).mouseover(function()
		{
			var imgId = "#"+this.id+"_img";
			var imgSrc = "images/"+this.id+"a.jpg";
			$(imgId).attr("src",imgSrc);
			$(this).css("color","black");
		});
		$(this).mouseout(function()
		{
			var imgId = "#"+this.id+"_img";
			var imgSrc = "images/"+this.id+".jpg";
			$(imgId).attr("src",imgSrc);
			$(this).css("color","#0592fc");
		});
	});
	
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
		<form action="DataCheckerAction.do?operate=selectDate&operPage=data_checker_02" method="post">		
			<div class="main_boxn">
				<% if(sysUser.getLevelID()!=6) {%>
				<div class="nav" title="Daily Records" id="icon1" onclick="javascript:checker_02();">
					<img src="images/icon1.jpg" id="icon1_img" /><h4>Daily Records</h4>
				</div>
				<div class="nav" title="Case & Defect" id="icon2" onclick="javascript:checker_06();"> 
					<img src="images/icon2.jpg" id="icon2_img"/> <h4>Case and Defect</h4>
				</div>
				<div class="nav" title="NonLabor Cost List" id="icon3" onclick="javascript:nonlaborCost_list();"> 
					<img src="images/icon3.jpg" id="icon3_img" /><h4>NonLabor Cost</h4>
				</div>
				<%} if(sysUser.getLevelID()==3||sysUser.getLevelID()==1) {%>
				<div class="nav" style="margin-right:0;px" title="Project & Account Assignment" id="icon4" onclick="javascript:checker_04();"> 
					<img src="images/icon4.jpg" id="icon4_img" /><br /><div style="font-size:13px; font-weight:bolder; margin-top:7px;">Project &amp; Account Assignment</div>
				</div>	
				<!-- 修改菜单名po_assignment为PO Management & Monthly Expense，FWJ 2013-04-05 -->			
				<div class="nav" title="Cost Management" id="icon5"  onclick="javascript:po_assignment();"> 
					<img src="images/icon5.jpg" id="icon5_img" /><h4> Cost Management </h4>
				</div>
				<%} if(sysUser.getLevelID()==3||sysUser.getLevelID()==1||sysUser.getLevelID()==4||sysUser.getLevelID()==6) {%>		
				<div class="nav" title="Set Name List" id="icon6"  onclick="javascript:checker_13();"> 
					<img src="images/icon6.jpg" id="icon6_img" /><h4> Set Name List</h4>
				</div>
				<div class="nav" title="Report Export" id="icon7"  onclick="javascript:checker_05();"> 
					<img src="images/icon7.jpg" id="icon7_img" /><h4> Report Export</h4>
				</div>
				<%} %>
			</div>
		</form>
		</center>	
<script type="text/javascript">
function checker_02() 
{
	var form = document.forms[0];
	form.submit();
}

function checker_04() 
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=searchGroups&operPage=data_checker_04";
	form.submit();
}
function checker_13()
{
		var form = document.forms[0];
		form.action = "ProjectAction.do?operate=configProjectAttribute&operPage=data_checker_13";
		form.submit();
}
function checker_05() 
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=selectReportDateAndGroup&operPage=data_checker_05";
	form.submit();
}


function checker_06() 
{
	var form = document.forms[0];
	form.action = "POAction.do?operate=searchCaseDefect&operPage=data_checker_06";
	form.submit();
}

function po_assignment() 
{
	var form = document.forms[0];
//	form.action = "POAction.do?operate=searchPO&operPage=po_assignment";
//抛弃PO Assignment界面，直接到PO List界面,FWJ on 2013-04-15
	form.action="POAction.do?operate=showPOList&operPage=po_list&pagefrom=checker01";
	form.submit();
}

function nonlaborCost_list()
{
	var form = document.forms[0];
	form.action = "POAction.do?operate=searchNonLabor&operPage=nonlaborCost_list";
	form.submit();
}
function downloadERC()
{
	//window.location.href="/BPD/documents/ExcelReportClient.zip";
}
</script>
</body>
</html>