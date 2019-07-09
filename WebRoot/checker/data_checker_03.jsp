<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*,
	com.beyondsoft.expensesystem.util.*"
	%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	//检查Details数据和Daily数据是否一致
	@SuppressWarnings("unchecked")
	List<CheckDetails> checkdetails = (List<CheckDetails>) request.getSession().getAttribute("checkdetails");
	CheckDetails tempcheckdetail = new CheckDetails();//by collie 0505
	
	// project List
	@SuppressWarnings("unchecked")
	List<ExpenseData> list = (List<ExpenseData>) request.getSession().getAttribute("displaylist");
	System.out.println("list.size()="+list.size());
	// leaveType List
	@SuppressWarnings("unchecked")
	List<String> projectId = (List<String>) request.getSession().getAttribute("projectId");
	@SuppressWarnings("unchecked")
	List<String> projectId2 = (List<String>) request.getSession().getAttribute("projectId2");
			
	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
	
	String lockday = sysUser.getLockday();
	String approveday = sysUser.getApproveday();
	
	//参数说明：str1~7表示七天的日期，将显示在表头。
	String str1 = ((String) request.getSession().getAttribute("str1")).substring(5);
	String str2 = ((String) request.getSession().getAttribute("str2")).substring(5);
	String str3 = ((String) request.getSession().getAttribute("str3")).substring(5);
	String str4 = ((String) request.getSession().getAttribute("str4")).substring(5);
	String str5 = ((String) request.getSession().getAttribute("str5")).substring(5);
	String str6 = ((String) request.getSession().getAttribute("str6")).substring(5);
	String str7 = ((String) request.getSession().getAttribute("str7")).substring(5);
	
	String lock = (String) request.getSession().getAttribute("lock");
	String approvelock = (String) request.getSession().getAttribute("approvelock");
	System.out.println("lock in jsp="+lock);
	System.out.println("approvelock in jsp="+approvelock);
	//approvelock
	
	String summaryDay1 = (String) request.getSession().getAttribute("summaryDay1");
	String summaryDay2 = (String) request.getSession().getAttribute("summaryDay2");
	String summaryDay3 = (String) request.getSession().getAttribute("summaryDay3");
	String summaryDay4 = (String) request.getSession().getAttribute("summaryDay4");
	String summaryDay5 = (String) request.getSession().getAttribute("summaryDay5");
	String summaryDay6 = (String) request.getSession().getAttribute("summaryDay6");
	String summaryDay7 = (String) request.getSession().getAttribute("summaryDay7");
	
	String bordColor = "1 ";
	String firstDay = (String) request.getSession().getAttribute("firstDay");
	
	String endDay = (String) request.getSession().getAttribute("endDay");
	
	//Filter List
	@SuppressWarnings("unchecked")
	List<String> projectList = (List<String>) request.getSession().getAttribute("ProjectFilter");
	@SuppressWarnings("unchecked")
	List<String> skillList = (List<String>) request.getSession().getAttribute("SkillFilter");
	@SuppressWarnings("unchecked")
	List<String> ottypeList = (List<String>) request.getSession().getAttribute("OTTpeyFilter");
	@SuppressWarnings("unchecked")
	List<String> productList = (List<String>) request.getSession().getAttribute("ProductFilter");
	@SuppressWarnings("unchecked")
	List<String> groupList = (List<String>) request.getSession().getAttribute("GroupFilter");
	
	System.out.println("sysUser.getUI()="+sysUser.getUI());
	
	//总页数
	int TotalPage = Integer.parseInt(request.getSession().getAttribute("TotalPage").toString());
	//当前页码
	// by collie 0505 点 CheckDetails会导致500错误
	int currentpage = 1;
	if (!request.getAttribute("page").equals("no")){	
		currentpage = Integer.parseInt(request.getAttribute("page").toString());
		System.out.println("page="+request.getAttribute("page"));
	}
	//int currentpage = Integer.parseInt(request.getAttribute("page").toString());
	
	String turnpage = (String) request.getAttribute("turnpage");
	@SuppressWarnings("unchecked")
	List<Map> allskillLevel = (List<Map>) request.getSession().getAttribute("skillList");
	String iflock = "0";
	
	String result = (String) request.getAttribute("result");
	System.out.println("result="+result);
	
	//hanxiaoyu01 2012-01-21
	String minDate=(String) request.getSession().getAttribute("minDate");
	minDate = minDate.substring(5,7)+"/"+minDate.substring(8)+"/"+minDate.substring(0,4);
	System.out.println("minDate is : "+minDate);
	
	//current selected filters
	String componentfilters = (String)request.getAttribute("componentfilters");
//	System.out.println("componentfiltesr="+componentfilters);
	String productfilters=(String)request.getAttribute("productfilters");
	String skillfilters=(String)request.getAttribute("skillfilters");
	String ottypefilters=(String)request.getAttribute("ottypefilters");
	String groupfilters=(String)request.getAttribute("groupfilters");
	
	System.out.println("skillfilters="+skillfilters);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Data Checker 3</title>
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<script src="js/datapicker/jquery.ui.core.js"></script> 
<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" /> 
<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
<style>
ul
{
	width:167px;
	border:1px #666666 solid;
	margin:0; 
	padding:0; 
	position:absolute;
	top:20px;
	left:5px; 
	background-color:white;
	
}

li
{
	list-style:none;
	font-size:12px;
}

.drop
{
	float:left;
	position:relative;
	margin-left:5px;
	/*Added the margin-top and margin-bottom by FWJ 2013-12-19*/
	margin-top:10px;
	margin-bottom:5px;
}

.ff
{
	width:163px;
	text-align:center;
	font-size:12px;
}
.uu
{
	display:none;
	overflow:auto; 
	max-height:140px;
}
#ulc, #ulp
{
	max-height:180px;
}
</style>
<script language="javascript">
$(function ()
{
	getPageSum('n');
	var dates = $( "#today2" ).datepicker(
	{
		changeMonth: true,
		changeYear: true,
		numberOfMonths: 1,
		minDate:'<%=minDate%>',
		onSelect: function( selectedDate )
		{
			var option = this.id == "today1" ? "minDate" : "maxDate",
				instance = $( this ).data( "datepicker" ),
				date = $.datepicker.parseDate(
					instance.settings.dateFormat ||
					$.datepicker._defaults.dateFormat,
					selectedDate, instance.settings );
			dates.not( this ).datepicker( "option", option, date );
			dates.datepicker( "option", "dateFormat", "yy-mm-dd" );
		}
	});
	//GroupName的onclick事件
	$("#groupbutton").click(function ()
	{
		var form = document.forms[0];
		subwindow=window.open("DataCheckerAction.do?operate=searchLeavePerWeek&operPage=data_checker_03_sub&startDay="+'<%=firstDay%>',null,'height=360,width=800,scrollbars=yes,top=330,left=300,resizable');
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
	// set filter content box to 'readonly'
	$(".ff").attr("readonly",true);	
	// if all box is checked other box shoulde be checked
	if($("#allc").attr("checked")==true)
	{
		$(".boxc").attr("checked",true);
	}
	if($("#allp").attr("checked")==true)
	{
		$(".boxp").attr("checked",true);
	}
	if($("#alls").attr("checked")==true)
	{
		$(".boxs").attr("checked",true);
	}
	if($("#allr").attr("checked")==true)
	{
		$(".boxr").attr("checked",true);
	}
	if($("#allg").attr("checked")==true)
	{
		$(".boxg").attr("checked",true);
	}
});

var x1 = 0;
var y1 = 0;
var current = "";
var filter = ""
function getAll(str)
{
	if(str=="c")
	{
		filter = "Component Filter";
		return "All Components";
	}
	else if(str=="p")
	{
		filter = "Product Filter";
		return "All Products";
	}
	else if(str=="s")
	{
		filter = "SkillLevel Filter";
		return "All SkillLevels";
	}
	else if(str=="r")
	{
		filter = "Rate Filter";
		return "All Rates";
	}
	else if(str=="g")
	{
		filter = "Group Filter";
		return "All Groups";
	}
}
function getDropList(obj)
{
	x1 = $(obj).offset().left;
	y1 = $(obj).offset().top;
	current = obj.id;
	$("#ul"+obj.id).show();
	if($("input[name='c"+obj.id+"']").length==0)
	{
		$("#"+obj.id).val("");
	}
}
function checkBox(obj,str)
{
	var vv = $("#"+str).val();
	vv = vv.replace(getAll(str),"");
	var va = $("#"+obj.id+"v").text().replace(",", ";");//fix the selected value contains ',' issue
	var aa = $("input[name='c"+str+"']");
	if($(obj).attr("checked")==true)
	{		
		if(obj.id=="all"+str)
		{
			$(".box"+str).attr("checked",true);
			vv = getAll(str);
		}
		else
		{
			$(".all"+str).attr("checked",false);
			vv = vv.replace(filter,"");
			vv = va + "," +vv;
		}
	}
	else
	{
		if(obj.id=="all"+str)
		{
			$(".box"+str).attr("checked",false);
			vv = vv.replace(va,"");
		}
		else
		{
			$(".all"+str).attr("checked",false);
			vv = "";
			for( var i=0; i<aa.length;i++)
			{
				if($(aa[i]).attr("checked")==true)
				{
					vv = $("#"+aa[i].id+"v").text() + "," +vv;
				}
			}
		}
	}
	$("#"+str).val(vv);
	$("#"+str).attr("title",vv);
	searchWithFilter();
}
$(document).mousemove(function(e)
{
	if(e.pageX<x1||e.pageX>x1+165||e.pageY<y1||e.pageY>y1+200)
	{
		$("#ul"+current).hide();
	}
});

</script>
</head>
<body onunload="javascript: destroy();">
<center>
	<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
	<form action="DataCheckerAction.do?operate=searchWithFilter&operPage=data_checker_03" target="hide" method="post">
		<input type="hidden" name="GroupsId" value="<%=sysUser.getGroupID() %>" />
		<input type="hidden" name="startDay" id="startDay" value="<%=firstDay %>" />
		<input type="hidden" name="endDay" id="endDay" value="<%=endDay %>" />
		<input type="hidden" name="approveOK" id="approveOK" value="NO" />
		<html:hidden name="DataCheckerForm" property="today" value='<%=firstDay.replaceAll("-", "/") %>' styleId="today"/>
	<div class="main_box3">
		<div class="nbox">
			<div class="bbox">
				<input name="back" type="button" class="btnBack" onclick='javascript:returnback();' />
			</div>
			<div class="hbox">
				<input type="button" value="QuickView" class="btnStyle1" id="groupbutton"/>
				<input type="button" value="Add New Project" class="btnStyle" onclick="javascript:addProject();"/>
				<input type="button" value="Excel Export" class="btnStyle" onclick="javascript:excelExport();" />
				<%if(5!=sysUser.getLevelID()) { //filler用户没有lock权限%>
				<input type="text" value="Select Date"  class="today2" id="today2" style="width:130px;" name="selectDate" readonly="readonly"/>
				<input type="button" value="Lock" class="btnLock" onclick="javascript:lock();" title="Locked at <%=lockday %>"/>
				<%} %>
				
				<%
					if (3 == sysUser.getLevelID()) // //approver有权限approve
					{
				%>
					<input type="button" value="Approve" class="btnApprove" onclick="javascript:approve();" title="Approved at <%=approveday%>" name="approvebutton" />
				<%	} %>
			</div>
		</div>
	<!--Hide the div.class and add the style for Filter by FWJ 2013-12-19  -->	
	<!-- 	<div class="mbox" > --> 
			<div class="flt" style='clear:left;margin-top:10px'>Filter:</div> 
			<div class="drop" align="left" >
				<input type="text" name="componentfilter" id="c" class="ff" 
				<% if(componentfilters!=null) {%> value="<%=componentfilters %>" title="<%=componentfilters %>" <%} else{ %>
				value="Component Filter" title="Component Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulc" class="uu" style="display:none;">
				<li><input type="checkbox" name="cc" id="allc" class="allc" onclick="javascript:checkBox(this,'c');"
				<% if(componentfilters!=null&&componentfilters.indexOf("All Components")>=0) {%> checked="checked"<%} %> />
				<label id="allcv">All Components</label></li>
				<%
					for (int i = 0; i < projectList.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cc" id="c<%=i %>" class="boxc" onclick="javascript:checkBox(this,'c');" 
				<% if(componentfilters!=null&&componentfilters.indexOf(projectList.get(i))>=0) {
				%> checked="checked"<%} %>/>
				<label id="c<%=i %>v"><%=projectList.get(i)%></label></li>
				<%
					}
				%>
				</ul>
		 	 </div>
		 	 <div class="drop" align="left" >
				<input type="text" name="productfilter" id="p" class="ff" 
				<% if(productfilters!=null) {%> value="<%=productfilters %>" title="<%=productfilters %>" <%} else{ %>
				value="Product Filter" title="Product Filter"<%} %>onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulp" class="uu" style="display:none;" >
				<li><input type="checkbox" name="pc" id="allp" class="allp" onclick="javascript:checkBox(this,'p');" 
				<% if(productfilters!=null&&productfilters.indexOf("All Products")>=0) {%> checked="checked"<%} %> />
				<label id="allpv">All Products</label></li>
				<%
					for (int i = 0; i < productList.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cp" id="p<%=i %>" class="boxp" onclick="javascript:checkBox(this,'p');"
				<% if(productfilters!=null&&productfilters.indexOf(productList.get(i))>=0) {
				%> checked="checked"<%} %>/>
				<label id="p<%=i %>v"><%=productList.get(i)%></label></li>
				<%
					}
				%>
				</ul>
		 	 </div>
		 	  <div class="drop" align="left" >
				<input type="text" name="skillfilter" id="s" class="ff" 
				<% if(skillfilters!=null) {%> value="<%=skillfilters %>" title="<%=skillfilters %>" <%} else{ %>
				value="SkillLevel Filter" title="SkillLevel Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="uls" class="uu" style="display:none;" >
				<li><input type="checkbox" name="sc" id="alls" class="alls" onclick="javascript:checkBox(this,'s');" 
				<% if(FilterTools.isElementContained(skillfilters, "All SkillLevels", ",")) {%> checked="checked"<%} %> />
				<label id="allsv">All SkillLevels</label></li>
				<%
					for (int i = 0; i < skillList.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cs" id="s<%=i %>" class="boxs" onclick="javascript:checkBox(this,'s');" 
				<% if(FilterTools.isElementContained(skillfilters, skillList.get(i), ",")) {%> checked="checked"<%} %>/>
				<label id="s<%=i %>v"><%=skillList.get(i)%></label></li>
				<%
					}
				%>
				</ul>
		 	 </div>
		 	 <div class="drop" align="left" >
				<input type="text" name="ottypefilter" id="r" class="ff" 
				<% if(ottypefilters!=null) {%> value="<%=ottypefilters %>" title="<%=ottypefilters %>" <%} else{ %>
				value="Rate Filter" title="Rate Filter" <%} %>onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulr" class="uu" style="display:none;" >
				<li><input type="checkbox" name="rc" id="allr" class="allr" onclick="javascript:checkBox(this,'r');" 
				<% if(ottypefilters!=null&&ottypefilters.indexOf("All Rates")>=0) {%> checked="checked"<%} %>/>
				<label id="allrv">All Rates</label></li>
				<%
					for (int i = 0; i < ottypeList.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cr" id="r<%=i %>" class="boxr" onclick="javascript:checkBox(this,'r');" 
				<% if(ottypefilters!=null&&ottypefilters.indexOf(ottypeList.get(i))>=0) {%> checked="checked"<%} %>/>
				<label id="r<%=i %>v"><%=ottypeList.get(i)%></label></li>
				<%
					}
				%>
				</ul>
		 	 </div>
		 	 <% if(sysUser.getLevelID()<4){ %>
		 	 <div class="drop" align="left" >
				<input type="text" name="groupfilter" id="g" class="ff" 
				<% if(groupfilters!=null) {%> value="<%=groupfilters %>" title="<%=groupfilters %>" <%} else{ %>
				value="Group Filter" title="Group Filter" <%} %>onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulg" class="uu" style="display:none;" >
				<li><input type="checkbox" name="gc" id="allg" class="allg" onclick="javascript:checkBox(this,'g');" 
				<% if(groupfilters!=null&&groupfilters.indexOf("All Groups")>=0) {%> checked="checked"<%} %>/>
				<label id="allgv">All Groups</label></li>
				<%
					for (int i = 0; i < groupList.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cg" id="g<%=i %>" class="boxg" onclick="javascript:checkBox(this,'g');" 
				<% if(groupfilters!=null&&groupfilters.indexOf(groupList.get(i))>=0) {%> checked="checked"<%} %>/>
				<label id="g<%=i %>v"><%=groupList.get(i)%></label></li>
				<%
					}
				%>
				</ul>
		 	 </div>
		  	 <%} %>
	<!-- </div> -->
		<div class="modeTable">
		<input type="hidden" id="firstDay" name="firstDay" value="<%=firstDay %>" />
		<table border="0" cellspacing="1" cellpadding="3" class="tabbg" align="center" width="98%" id="tblGrid" style="clear:both">
		  <tr class="tr_title2">
			<td width="9%">Component</td>
			<td width="10%">Product</td>
			<td width="9%">UserName</td>
			<td width="10%">Skill Level</td>
			<td width="6%">Location</td>
			<td width="9%">OTType</td>
			<td width="7%">Group</td>			
			<td width="1%"></td>
			<td width="4%"></td>
			<td class="str">
			<%out.print(str1);%><br />
			<%if(approvelock.substring(0,1).equals("0")){
			%>
				<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>	
			<br />
			Mon.<br />
			<%if(lock.substring(0,1).equals("0")){%>
				<img src="images/lock.png" width="15" height="15" alt="Locked" />
			<%}  %>
			 </td>
			<td class="str">
			<%out.print(str2);%><br />
			<%if(approvelock.substring(1,2).equals("0")){
			%>
				<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>	
			<br />
			Tue.<br />
			<%if(lock.substring(1,2).equals("0")){%>
				<img src="images/lock.png" width="15" height="15" alt="Locked" />
			<%}  %>
			</td>
			<td class="str">
			<%out.print(str3);%><br />
			<%if(approvelock.substring(2,3).equals("0")){
			%>
				<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>	
			<br />
			Wed.<br />
			<%if(lock.substring(2,3).equals("0")){%>
				<img src="images/lock.png" width="15" height="15" alt="Locked" />
			<%}  %>
			</td>
			<td class="str">
			<%out.print(str4);%><br />
			<%if(approvelock.substring(3,4).equals("0")){
			%>
				<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>	
			<br />
			Thu.<br />
			<%if(lock.substring(3,4).equals("0")){%>
				<img src="images/lock.png" width="15" height="15" alt="Locked" />
			<%}  %>
			</td>
			<td class="str">
			<%out.print(str5);%><br />
			<%if(approvelock.substring(4,5).equals("0")){
			%>
				<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>	
			<br />
			Fri.<br />
			<%if(lock.substring(4,5).equals("0")){%>
				<img src="images/lock.png" width="15" height="15" alt="Locked" />
			<%}  %>
			</td>
			<td class="str">
			<%out.print(str6);%><br />
			<%if(approvelock.substring(5,6).equals("0")){
			%>
				<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>	
			<br />
			Sat.<br />
			<%if(lock.substring(5,6).equals("0")){%>
				<img src="images/lock.png" width="15" height="15" alt="Locked" />
			<%}  %>
			</td>
			<td class="str">
			<%out.print(str7);%><br />
			<%if(approvelock.substring(6,7).equals("0")){%>
			<img src="images/approve.png" width="15" height="15" alt="Approved" />
			<%} %>
			<br />
			Sun.<br />
			<% if(lock.substring(6,7).equals("0")){ %>
			<img src="images/lock.png" width="15" height="15" alt="Locked" /><%} %>	
			</td>
		  </tr>		 
		  <tr class="tr_title3" style="color:green;">
		  	<td colspan="9">Total Summary</td>
		  	<td align="center" id="expenseDataSummary1">
					<%=summaryDay1%>
				</td>
				<td align="center" id="expenseDataSummary2">
					<%=summaryDay2%>
				</td>
				<td align="center" id="expenseDataSummary3">
					<%=summaryDay3%>
				</td>
				<td align="center" id="expenseDataSummary4">
					<%=summaryDay4%>
				</td>
				<td align="center" id="expenseDataSummary5">
					<%=summaryDay5%>
				</td>
				<td align="center" id="expenseDataSummary6">
					<%=summaryDay6%>
				</td>
				<td align="center" id="expenseDataSummary7">
					<%=summaryDay7%>
				</td>
		  </tr>
		  <tr class="tr_title3" style="color:green;">
		  	<td colspan="9">Page Summary</td>
		  	<td id="pageSummary1"><label id="pageSum1"></label></td>
			<td id="pageSummary2"><label id="pageSum2"></label></td>
			<td id="pageSummary3"><label id="pageSum3"></label></td>
			<td id="pageSummary4"><label id="pageSum4"></label></td>
			<td id="pageSummary5"><label id="pageSum5"></label></td>
			<td id="pageSummary6"><label id="pageSum6"></label></td>
			<td id="pageSummary7"><label id="pageSum7"></label></td>
		  </tr>
		  <%
				for (int i = 0; i < list.size(); i++) {
			%>			
			<tr <%if(i%2==1) {%>class="tr_content1"<%}else{ %> class="tr_content2"<%} %> align="center">			
				<td><%=list.get(i).getProjectName() %></td>
				<td><%=list.get(i).getProduct() %></td>				
				<td><%=list.get(i).getUserName() %></td>
				<td><%=list.get(i).getSkillLevel() %></td>				
				<td><%=list.get(i).getLocation() %></td>
				<td><%=list.get(i).getOTType() %></td>
				<td><%=list.get(i).getGroupName() %></td>
				<td>
				<%
					Integer intConfirm = list.get(i).getConfirm();
								if (intConfirm == 0)
									{%><label title='not confirm'> × </label><%}
								else if (intConfirm == 1)
									{%><label title='confirmed'> √ </label><%}
								else
									{out.print("Err!");}
						
					%>
				</td>
				<td align="center">
				<%
						if (intConfirm == 0){//如果没有confirm 就可以删除
				%>
					<input type="button" value="Delete" class="btnDel" 
					                                                        
							onclick="javascript:deleteTempProject('<%=list.get(i).getProjectId()%>',this,<%=i %>);" /> <!-- hanxiaoyu01  把i传过去  -->
				<%}%>
				</td>
				<td>
				<input id="<%=i * 7 + 1%>" type="text" maxlength="10" class="test"
						<%
						//通过lock和approvelock来判断是否可写
						if(lock.substring(0,1).equals("0") || approvelock.substring(0,1).equals("0")){%>
							readonly="readonly" alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour1()%>" size="4"
						style="display:none;ime-mode:disabled ; <%if (!(list.get(i).getHour1()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str1,checkdetails))%>background-color:red;
						"  name="<%=i * 7 + 1%>"  onkeydown="myKeyDown()" onkeyup="if(isNaN(value))execCommand('undo')"
						onchange="getPageSum('n');this.style.color='green';" onfocus="this.select()"/>
					<%
						if (!list.get(i).getHour1().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null
												&& !(list.get(i).getComment1().trim()
														.equals(""))) {
					%>
					<input id="D<%=i * 7 + 1%>"  type="button" value="<%=list.get(i).getHour1()%>" title="Details" class="detailStyle"
					<%
						if(lock.substring(0,1).equals("0") || approvelock.substring(0,1).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount1()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str1','<%=i * 7 + 1%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input  id="D<%=i * 7 + 1%>"  type="button" value="<%=list.get(i).getHour1()%>" title="Details" class="detailStyle" 
						<%
						if(lock.substring(0,1).equals("0") || approvelock.substring(0,1).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount1()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str1','<%=i * 7 + 1%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
					%>
					<%
						}
						//out.print(list.get(i).getDetailscount1()); // by collie 0428: 把details信息传到Daily Records页面
					%>
				</td>
				<td>
				<input id="<%=i * 7 + 2%>" type="text" maxlength="10" class="test"
						<%if(lock.substring(1,2).equals("0") || approvelock.substring(1,2).equals("0")){%>
							readonly="readonly" alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour2()%>" size="4"
						style="display:none;ime-mode:disabled ; <%if (!(list.get(i).getHour2()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str2,checkdetails))%>background-color:red;
						" name="<%=i * 7 + 2%>" onkeydown="myKeyDown()" onkeyup="if(isNaN(value))execCommand('undo')" 
						onchange="getPageSum('n');this.style.color='#007500';"/ onfocus="this.select()" />
					<%
						if (!list.get(i).getHour2().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null
												&& !(list.get(i).getComment2().trim()
														.equals(""))) {
					%>
					<input id="D<%=i * 7 + 2%>" type="button" value="<%=list.get(i).getHour2()%>" title="Details" class="detailStyle"
					<%if(lock.substring(1,2).equals("0") || approvelock.substring(1,2).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount2()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str2','<%=i * 7 + 2%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input id="D<%=i * 7 + 2%>" type="button" value="<%=list.get(i).getHour2()%>" title="Details" class="detailStyle" 
					<%if(lock.substring(1,2).equals("0") || approvelock.substring(1,2).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount2()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str2','<%=i * 7 + 2%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
					%>
					<%
						}
						//out.print(list.get(i).getDetailscount2()); // by collie 0428: 把details信息传到Daily Records页面
					%>
				</td>
				<td>
				<input id="<%=i * 7 + 3%>" type="text" maxlength="10" class="test"
						<%if(lock.substring(2,3).equals("0") || approvelock.substring(2,3).equals("0")){%>
							readonly="readonly" alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour3()%>" size="4"
						style="display:none;ime-mode:disabled ; <%if (!(list.get(i).getHour3()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str3,checkdetails))%>background-color:red;
						" name="<%=i * 7 + 3%>" onkeydown="myKeyDown()" onkeyup="if(isNaN(value))execCommand('undo')"
						onchange="getPageSum('n');this.style.color='#007500';" onfocus="this.select()"/>
					<%
						if (!list.get(i).getHour3().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null
												&& !(list.get(i).getComment3().trim()
														.equals(""))) {
					%>
					<input id="D<%=i * 7 + 3%>" type="button" value="<%=list.get(i).getHour3()%>" title="Details" class="detailStyle"
					<%if(lock.substring(2,3).equals("0") || approvelock.substring(2,3).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount3()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str3','<%=i * 7 + 3%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input id="D<%=i * 7 + 3%>" type="button" value="<%=list.get(i).getHour3()%>" title="Details" class="detailStyle"
						<%if(lock.substring(2,3).equals("0") || approvelock.substring(2,3).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount3()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str3','<%=i * 7 + 3%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
					%>
					<%
						}
						//out.print(list.get(i).getDetailscount3()); // by collie 0428: 把details信息传到Daily Records页面
					%>
				</td>
				<td>
				<input id="<%=i * 7 + 4%>" type="text" maxlength="10" class="test"
						<%if(lock.substring(3,4).equals("0") || approvelock.substring(3,4).equals("0")){%>
							readonly="readonly" alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour4()%>" size="4"
						style="display:none;ime-mode:disabled ; <%if (!(list.get(i).getHour4()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str4,checkdetails))%>background-color:red;
						" name="<%=i * 7 + 4%>" onkeydown="myKeyDown()" onkeyup="if(isNaN(value))execCommand('undo')"
						onchange="getPageSum('n');this.style.color='#007500'" onfocus="this.select()"/>
					<%
						if (!list.get(i).getHour4().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null
												&& !(list.get(i).getComment4().trim()
														.equals(""))) {
					%>
					<input id="D<%=i * 7 + 4%>" type="button" value="<%=list.get(i).getHour4()%>" title="Details" class="detailStyle"
					<%if(lock.substring(3,4).equals("0") || approvelock.substring(3,4).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount4()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str4','<%=i * 7 + 4%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input id="D<%=i * 7 + 4%>" type="button" value="<%=list.get(i).getHour4()%>" title="Details" class="detailStyle"
						<%if(lock.substring(3,4).equals("0") || approvelock.substring(3,4).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount4()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str4','<%=i * 7 + 4%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
					%>
					<%
						}
						//out.print(list.get(i).getDetailscount4()); // by collie 0428: 把details信息传到Daily Records页面
					%>
				</td>
				<td>
				<input id="<%=i * 7 + 5%>" type="text" maxlength="10" class="test"
						<%if(lock.substring(4,5).equals("0") || approvelock.substring(4,5).equals("0")){%>
							readonly="readonly" alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour5()%>" size="4"
						style="display:none;ime-mode:disabled ; <%if (!(list.get(i).getHour5()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str5,checkdetails))%>background-color:red;
						" name="<%=i * 7 + 5%>" onkeydown="myKeyDown()" onkeyup="if(isNaN(value))execCommand('undo')" 
						onchange="getPageSum('n');this.style.color='#007500'" onfocus="this.select()"/>
					<%
						if (!list.get(i).getHour5().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null
												&& !(list.get(i).getComment5().trim()
														.equals(""))) {
					%>
					<input id="D<%=i * 7 + 5%>" type="button" value="<%=list.get(i).getHour5()%>" title="Details" class="detailStyle"
					<%if(lock.substring(4,5).equals("0") || approvelock.substring(4,5).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount5()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str5','<%=i * 7 + 5%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input id="D<%=i * 7 + 5%>" type="button" value="<%=list.get(i).getHour5()%>" title="Details" class="detailStyle"
						<%if(lock.substring(4,5).equals("0") || approvelock.substring(4,5).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount5()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str5','<%=i * 7 + 5%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
					%>
					<%
						}
						//out.print(list.get(i).getDetailscount5()); // by collie 0428: 把details信息传到Daily Records页面
					%>
				</td>
				<td>
				<input id="<%=i * 7 + 6%>" type="text" maxlength="10" class="test"
						<%if(lock.substring(5,6).equals("0") || approvelock.substring(5,6).equals("0")){%>
							alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour6()%>" size="4"
						style="display:none;ime-mode:disabled ; <%if (!(list.get(i).getHour6()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str6,checkdetails))%>background-color:red;
						" name="<%=i * 7 + 6%>" onkeydown="myKeyDown()" onkeyup="if(isNaN(value))execCommand('undo')"
						onchange="getPageSum('n');this.style.color='#007500'" onfocus="this.select()"/>
					<%
						if (!list.get(i).getHour6().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null
												&& !(list.get(i).getComment6().trim()
														.equals(""))) {
					%>
					<input id="D<%=i * 7 + 6%>" type="button" value="<%=list.get(i).getHour6()%>" title="Details" class="detailStyle"
						<%if(lock.substring(5,6).equals("0") || approvelock.substring(5,6).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount6()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str6','<%=i * 7 + 6%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input id="D<%=i * 7 + 6%>" type="button" value="<%=list.get(i).getHour6()%>" title="Details" class="detailStyle"
						<%if(lock.substring(5,6).equals("0") || approvelock.substring(5,6).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount6()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str6','<%=i * 7 + 6%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
					%>
					<%
						}
						//out.print(list.get(i).getDetailscount6()); // by collie 0428: 把details信息传到Daily Records页面
					%>
				</td>
				<td>
				<input id="<%=i * 7 + 7%>" type="text" maxlength="10" class="test"
						<%if(lock.substring(6,7).equals("0") || approvelock.substring(6,7).equals("0")){%>
							readonly="readonly" alt="<%=list.get(i).getHour1()%>"
						<%}%>
						value="<%=list.get(i).getHour7()%>" size="4"
						style="display:none; ime-mode:disabled ; <%if (!(list.get(i).getHour7()).equals(" ")) out.print(bordColor);
						%> border: 0px red; text-align: center;
						<%// by collie 0505
						if (null!=checkdetails) if (tempcheckdetail.findExpensedata(list.get(i).getProjectId(),str7,checkdetails))%>background-color:red;
						" name="<%=i * 7 + 7%>" onkeydown="myKeyDown()"onkeyup="if(isNaN(value))execCommand('undo')"  
						onchange="getPageSum('n');this.style.color='#007500'" onfocus="this.select()"/>
					<%
						if (!list.get(i).getHour7().equals(" ")) {
					%>
					<%
						if (list.get(i).getComments() != null&& !(list.get(i).getComment7().trim().equals(""))) {
					%>
					<input id="D<%=i * 7 + 7%>" type="button" value="<%=list.get(i).getHour7()%>" title="Details" class="detailStyle"
						<%if(lock.substring(6,7).equals("0") || approvelock.substring(6,7).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount7()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str7','<%=i * 7 + 7%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						} else {
					%>
					<input id="D<%=i * 7 + 7%>" type="button" value="<%=list.get(i).getHour7()%>" title="Details" class="detailStyle"
						<%if(lock.substring(6,7).equals("0") || approvelock.substring(6,7).equals("0")){
							iflock = "0";
						}else{
							iflock = "1";
						} %>
						<%if(list.get(i).getDetailscount7()==0){ %>
							style="color:#7dabdf;"
						<%} %>
						onclick="javascript:addD('<%=list.get(i).getProjectId()%>','str7','<%=i * 7 + 7%>','<%=iflock %>','<%=list.get(i).getSkillLevel()%>');" />
					<%
						}
						}
					%>		
				</td>
				
		  </tr>
		  <%} %>
		</table>			
		</div>
		<div class="turnPage">
			<% //if(!"no".equals(turnpage)){ //判断是否显示分页
			System.out.println("currentpage2="+currentpage+" TotalPage="+TotalPage);
			%>
			<label <% if(currentpage==0||currentpage==1) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %> onclick="javascript: turnpage('1');">First</label>
			<label class="pageCell" <% if(currentpage>1) {%> onclick="javascript: turnpage('<%=currentpage-1 %>');"<%} %>>Prev</label>
			<% 
			if(currentpage==0) {currentpage=1;}
			int j = currentpage/5;
			int r = currentpage%5;
			int p =j*5;
			int n =0;
			int m = TotalPage -p;
			System.out.println("m="+m);		
			if(TotalPage>5&&m>=5&&r>0){ p =j*5+1; n = 5;}
			else if(r==0){ p =j*5-4; n = 5;}
			else if(TotalPage>5&&m<5&&r>0){ p =j*5+1; n = m;}
			else if(TotalPage<=5) { p=1; n = TotalPage;}
			for(int i=0;i<n;i++)
			{ 
			%>
				<label onclick="javascript: turnpage('<%=(i+p) %>');" <% if(currentpage==(i+p)) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %>><%=(i+p)%></label>
			<%
			} 
			%>
			<label class="pageCell"onclick="javascript: turnpage('<%=currentpage+1 %>');">Next</label>
			<label <% if(currentpage==TotalPage) {%> class="pageCell2"<%} else{%>class="pageCell"<%} %> onclick="javascript: turnpage('<%=TotalPage %>');" title='total:<%=TotalPage %>'>Last</label>	
			<input type="text" class="txtPage" id="txtPN" onkeydown="javascript:myKeyDown();"/><input type="button" class="go" value="Go" onclick="javacript:goPage($('#txtPN').val());"/>
			<%//} %>
		</div>
		
		<html:hidden name='DataCheckerForm' property="recid" />
		<html:hidden name='DataCheckerForm' property="projectId" />
		<html:hidden name='DataCheckerForm' property="today" />
		<efan:returnbuttontag />
	</div>
	</form>
	<input type="hidden" name="inputid" id='inputid' value=''/> 
	<input type="hidden" name="deleteprojectrow" id='deleteprojectrow' value=''/>
</center>
<div class="tanchuang_wrap" id="aaaa">
		<div class="lightbox"></div>
		<div class="tanchuang_neirong">
			<img src="images/loading.gif" />
			<br />Loading, please wait, thanks!
		</div>
</div>		
<script language="javascript">
//window.name = "win";
//w用来标记details窗口是否打开，避免打开多个details窗口
var w=null; 
//var currentId = document.getElementById("<%=(String) request.getAttribute("currentId")%>");
var scrollTop = <%=request.getAttribute("scrollTop")%>;
//if (null!=currentId)// by collie 0505
//{
//	currentId.focus();
//}
if (null!=scrollTop)// by collie 0505
{
	window.scrollTo(0,scrollTop);
}
if('<bean:write name="DataCheckerForm" property="strErrors" filter="true"/>' == 'true')
{
	alert("1 Data saved successfully!");
}
if('<bean:write name="DataCheckerForm" property="strErrors" filter="true"/>' == 'delete')
{
	alert("Data deleted successfully!");
}
//@定义一个迭代器对象
function Iterator(arr)
{
　 this.obj=arr;
　 this.length=this.obj.length;
　 this.index=0; //从前往后
}
　
Iterator.prototype=
{
　current:function()
　{
　　 return this.obj[this.index-1];
　},
　 first:function()
　{
　　　return this.obj[0];
　},
  last:function()
　{
　　　return this.obj[this.length-1];
　 },
　 hasNext:function()
　 {
　　　this.index=this.index+1;
　　　 if(this.index>this.length || null==this.obj[this.index-1])
　　　　　return false;
　　　return true;
　}
}
Array.prototype.createIterator=function()
{
　return new Iterator(this);
}


function addProject()
{
	var form = document.forms[0];
	form.target = "";
	var gid = '<%=sysUser.getGroupID()%>';
	var gname = '<%=sysUser.getGroupName()%>';
	form.action = "DataCheckerAction.do?operate=toinsert&operPage=new_project_edit&gid="+gid+"&gname="+gname;
	form.submit();
}

function addD(projectId,selectedDate,inputid,islock,sk)
{
	var form = document.forms[0];		
	if(w==undefined)
	{ 
	}
	else
	{
		w.close();
	}
	var hour = document.getElementById(inputid).value;
	//下面这句用来记录当前点击的button id，在details_submit.jsp里用来改变颜色，实现实时变色
	document.getElementById("inputid").value="D"+inputid;
	var hh = 160;//2013-1-29 by dancy设置弹框高度
	if(sk.indexOf('Tester')>=0)
	{
		hh = 450;
	}
	w = window.open("DataCheckerAction.do?operate=searchExpenseDetails&operPage=data_checker_03_details2&projectId=" + projectId + "&selectedDate=" + selectedDate+ "&hour=" + hour +"&islock="+islock+"&skillLevel="+sk,'','height='+hh+',width=1300,scrollbars=yes,top=200,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=yes');
}


function returnback() 
{
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=selectDate&operPage=data_checker_02";
	form.submit();
}

function searchWithFilter()
{
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=searchWithFilter&operPage=data_checker_03";
	form.submit();
}
function goPage(page)
{
	if(page=='')
	{
		alert("please input or select a Page Number!");
		$("#txtPage").focus();		
		return false;
	}
	else
	{
		turnpage(page);
	}
}
function turnpage(page)
{	
	var total = '<%=TotalPage%>';
	if(parseInt(page)-parseInt(total)>0)//加parseInt将page和total的string类型转为int类型再比较 by dancy 2013-1-24
	{
		alert("Total page is "+total+", please choose a correct page number!");
		return;
	}
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=turnPageJSP&operPage=data_checker_03&page="+page;
	form.submit();
}

function myKeyDown()
{
    var k=window.event.keyCode;   
    if ((k==46)||(k==8)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) 
    {}
    else if(event.ctrlKey&&event.keyCode==67)
    {}
    else if(event.ctrlKey&&event.keyCode==86)
    {}
    else if(k==13)
    {
         window.event.keyCode = 9;
    }
    else
    {
         window.event.returnValue = false;
    }
}
function deleteTempProject(projectid, row,i)
{

	if (!confirm("Sure to delete the project?"))
	{
    	window.event.returnValue = false;
    }
    else
	{
	    
   //TotalSummary没修改前的值：hanxiaoyu01 2012-12-27
	var t1=document.getElementById("expenseDataSummary1").innerHTML;
	var t2=document.getElementById("expenseDataSummary2").innerHTML;
	var t3=document.getElementById("expenseDataSummary3").innerHTML;
	var t4=document.getElementById("expenseDataSummary4").innerHTML;
	var t5=document.getElementById("expenseDataSummary5").innerHTML;
	var t6=document.getElementById("expenseDataSummary6").innerHTML;
	var t7=document.getElementById("expenseDataSummary7").innerHTML;

	//PageSummary没修改前的值：hanxiaoyu01 2012-12-27
	var y1=document.getElementById("pageSum1").innerHTML;
	var y2=document.getElementById("pageSum2").innerHTML;
	var y3=document.getElementById("pageSum3").innerHTML;
	var y4=document.getElementById("pageSum4").innerHTML;
	var y5=document.getElementById("pageSum5").innerHTML;
	var y6=document.getElementById("pageSum6").innerHTML;
	var y7=document.getElementById("pageSum7").innerHTML;
	
	//details里的值 hanxiaoyu01 2012-12-27
	var id1=i*7+1;
	var id2=i*7+2;
	var id3=i*7+3;
	var id4=i*7+4;
	var id5=i*7+5;
	var id6=i*7+6;
	var id7=i*7+7;
	
	var d1=document.getElementById("D"+id1).value;
	var d2=document.getElementById("D"+id2).value;
	var d3=document.getElementById("D"+id3).value;
	var d4=document.getElementById("D"+id4).value;
	var d5=document.getElementById("D"+id5).value;
	var d6=document.getElementById("D"+id6).value;
	var d7=document.getElementById("D"+id7).value;
	
	//修改TotalSummary hanxiaoyu01 2012-12-27
	document.getElementById("expenseDataSummary1").innerHTML=parseFloat((t1-d1).toFixed(4));
	document.getElementById("expenseDataSummary2").innerHTML=parseFloat((t2-d2).toFixed(4));
	document.getElementById("expenseDataSummary3").innerHTML=parseFloat((t3-d3).toFixed(4));
	document.getElementById("expenseDataSummary4").innerHTML=parseFloat((t4-d4).toFixed(4));
	document.getElementById("expenseDataSummary5").innerHTML=parseFloat((t5-d5).toFixed(4));
	document.getElementById("expenseDataSummary6").innerHTML=parseFloat((t6-d6).toFixed(4));
	document.getElementById("expenseDataSummary7").innerHTML=parseFloat((t7-d7).toFixed(4));
	
	
	//修改PageSummary hanxiaoyu01 2012-12-27
	document.getElementById("pageSum1").innerHTML=parseFloat((y1-d1).toFixed(4));
	document.getElementById("pageSum2").innerHTML=parseFloat((y2-d2).toFixed(4));
	document.getElementById("pageSum3").innerHTML=parseFloat((y3-d3).toFixed(4));
	document.getElementById("pageSum4").innerHTML=parseFloat((y4-d4).toFixed(4));
	document.getElementById("pageSum5").innerHTML=parseFloat((y5-d5).toFixed(4));
	document.getElementById("pageSum6").innerHTML=parseFloat((y6-d6).toFixed(4));
	document.getElementById("pageSum7").innerHTML=parseFloat((y7-d7).toFixed(4));
	
	
	//hanxiaoyu01 2013-01-04
	//再把修改后的值传到后台更改session里面的值（翻页时TotalSummary的值是从session里面取的，这里更改后必须把session里的值覆盖）
	t1=document.getElementById("expenseDataSummary1").innerHTML;
	t2=document.getElementById("expenseDataSummary2").innerHTML;
	t3=document.getElementById("expenseDataSummary3").innerHTML;
	t4=document.getElementById("expenseDataSummary4").innerHTML;
	t5=document.getElementById("expenseDataSummary5").innerHTML;
	t6=document.getElementById("expenseDataSummary6").innerHTML;
	t7=document.getElementById("expenseDataSummary7").innerHTML;
	
		var form = document.forms[0];
		
		form.action = "DataCheckerAction.do?operate=deleteTempProject&operPage=data_checker_03_deleteproject&projectid="+projectid
		+"&t1="+t1
		+"&t2="+t2
		+"&t3="+t3
		+"&t4="+t4
		+"&t5="+t5
		+"&t6="+t6
		+"&t7="+t7
		;
		form.submit();
	}

	//下面这句用来标记当前删除的行号,在deleteproject.jsp中，如果删除成功，会调用本页面的removwRow()函数
	document.getElementById("deleteprojectrow").value=row.parentElement.parentElement.rowIndex;
}
function removeRow()
{
	var row = document.getElementById("deleteprojectrow").value;
 	document.all("tblGrid").deleteRow(row);
} 
function trueRemaind()
{
		remaind = true;
}
function falseRemaind()
{
		remaind = false;
}
/**hanxiaoyu01 2012-12-013
   增加求PageSummary修改后和修改前的差值，然后把它与TotalSummary原来的值相加
  以便更新TotalSummary
*/
function getPageSum(obj)
{
    
	var projectlistsize = '<%=list.size()%>';
	var sum1 = 0;var sum2 = 0;var sum3 = 0;var sum4 = 0;var sum5 = 0;var sum6 = 0;var sum7 = 0;
	for(i=1;i<=projectlistsize*7;i+=7)
	{
		sum1 = sum1+parseFloat("0"+document.getElementById(i).value);
		sum2 = sum2+parseFloat("0"+document.getElementById(i+1).value);
		sum3 = sum3+parseFloat("0"+document.getElementById(i+2).value);
		sum4 = sum4+parseFloat("0"+document.getElementById(i+3).value);
		sum5 = sum5+parseFloat("0"+document.getElementById(i+4).value);
		sum6 = sum6+parseFloat("0"+document.getElementById(i+5).value);
		sum7 = sum7+parseFloat("0"+document.getElementById(i+6).value);
	}
	//PageSummary现在应该写的值：
	var s1 = parseFloat(sum1);
	var s2 = parseFloat(sum2);
	var s3 = parseFloat(sum3);
	var s4 = parseFloat(sum4);
	var s5 = parseFloat(sum5);
	var s6 = parseFloat(sum6);
	var s7 = parseFloat(sum7);
	
	//PageSummary没修改前的值：hanxiaoyu01 2012-12-13
	var y1=document.getElementById("pageSum1").innerHTML;
	var y2=document.getElementById("pageSum2").innerHTML;
	var y3=document.getElementById("pageSum3").innerHTML;
	var y4=document.getElementById("pageSum4").innerHTML;
	var y5=document.getElementById("pageSum5").innerHTML;
	var y6=document.getElementById("pageSum6").innerHTML;
	var y7=document.getElementById("pageSum7").innerHTML;
	
	//更新PageSummary数据：
	document.getElementById("pageSum1").innerHTML=parseFloat(s1.toFixed(4));
	document.getElementById("pageSum2").innerHTML=parseFloat(s2.toFixed(4));
	document.getElementById("pageSum3").innerHTML=parseFloat(s3.toFixed(4));
	document.getElementById("pageSum4").innerHTML=parseFloat(s4.toFixed(4));
	document.getElementById("pageSum5").innerHTML=parseFloat(s5.toFixed(4));
	document.getElementById("pageSum6").innerHTML=parseFloat(s6.toFixed(4));
	document.getElementById("pageSum7").innerHTML=parseFloat(s7.toFixed(4));
	
	//hanxiaoyu01 2012-12-07
	//只有在执行Details的save操作后才执行下面的步骤
	if('s'==obj){

    //PageSummary修改前后的差值:
	var c1=s1-y1;
	var c2=s2-y2;
	var c3=s3-y3;
	var c4=s4-y4;
	var c5=s5-y5;
	var c6=s6-y6;
	var c7=s7-y7;

	 //TotalSummary没修改前的值：
	var t1=document.getElementById("expenseDataSummary1").innerHTML;
	var t2=document.getElementById("expenseDataSummary2").innerHTML;
	var t3=document.getElementById("expenseDataSummary3").innerHTML;
	var t4=document.getElementById("expenseDataSummary4").innerHTML;
	var t5=document.getElementById("expenseDataSummary5").innerHTML;
	var t6=document.getElementById("expenseDataSummary6").innerHTML;
	var t7=document.getElementById("expenseDataSummary7").innerHTML;
	
	//更新TotalSummary数据：
	document.getElementById("expenseDataSummary1").innerHTML=parseFloat((t1*1+c1).toFixed(4));
	document.getElementById("expenseDataSummary2").innerHTML=parseFloat((t2*1+c2).toFixed(4));
	document.getElementById("expenseDataSummary3").innerHTML=parseFloat((t3*1+c3).toFixed(4));
	document.getElementById("expenseDataSummary4").innerHTML=parseFloat((t4*1+c4).toFixed(4));
	document.getElementById("expenseDataSummary5").innerHTML=parseFloat((t5*1+c5).toFixed(4));
	document.getElementById("expenseDataSummary6").innerHTML=parseFloat((t6*1+c6).toFixed(4));
	document.getElementById("expenseDataSummary7").innerHTML=parseFloat((t7*1+c7).toFixed(4));

	 //再把值传到后台更改session里面的值（翻页时TotalSummary的值是从session里面取的，这里更改后必须把session里的值覆盖）
	$.post("DataCheckerAction.do",{"operate":"saveSummaryDay",
		"t1":parseFloat((t1*1+c1).toFixed(4)),
		"t2":parseFloat((t2*1+c2).toFixed(4)),
		"t3":parseFloat((t3*1+c3).toFixed(4)),
		"t4":parseFloat((t4*1+c4).toFixed(4)),
		"t5":parseFloat((t5*1+c5).toFixed(4)),
		"t6":parseFloat((t6*1+c6).toFixed(4)),
		"t7":parseFloat((t7*1+c7).toFixed(4))
		});
	}
	
	if(obj!='s')
	{
		var re = '<%=result%>';
		if(''==re)
		{}
		else if('true'==re)
			alert("Successfully!");
		else if('false'==re)
			alert("Failed!");
	}
	
}
//离开页面 判断是否需要善后 关闭detials页面等
function destroy()
{
	if(w==undefined)
	{ 
		
	}
	else
	{
		w.close();
	}
}
function excelExport() 
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=excelExport&curPage=checker03&scrollTop=" + document.body.scrollTop;
	form.submit();

}

function lock()
{
	var day = document.getElementById("today2").value;
	
	var ui = '<%=sysUser.getUI()%>';
	var headORhour = '<%=sysUser.getHeadorHour()%>';
	
	if(day == "Select Date")
	{
		alert("Please select a date!");
		document.getElementById("today2").focus();
		return false;
	}
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=lockExpense&operPage=data_checker_03&day="+day+"&UIradio="+ui+"&headorhour="+headORhour;
	form.target="";
	displayDiv('aaaa');//显示覆盖层
	form.submit();
}
function approve()
{

		var day = document.getElementById("today2").value;		
		var ui = '<%=sysUser.getUI()%>';
		var headORhour = '<%=sysUser.getHeadorHour()%>';
		var lockday = '<%=lockday%>';
		var a = parseInt(lockday.replace('-','').replace('-',''));
		var b = parseInt(day.replace('-','').replace('-',''));
		if(day == "Select Date")
		{
			document.getElementById("today2").focus();
			return;
		}
		if(b-a>0)
		{
			alert("Aprroveday should not be later than Lockday!");
			document.getElementById("today2").focus();
			return;
		}
		
		var form = document.forms[0];
		form.action = "DataCheckerAction.do?operate=approveExpense&operPage=data_checker_03&day="+day+"&UIradio="+ui+"&headorhour="+headORhour;
		form.target="";
		displayDiv('aaaa');//显示覆盖层
		form.submit();
}
function closeDiv(divId)
{ 
	document.getElementById(divId).style.display = 'none'; 
} 
function displayDiv(divId)
{ 
	document.getElementById(divId).style.display = 'block'; 
} 

</script>
</body>
</html>