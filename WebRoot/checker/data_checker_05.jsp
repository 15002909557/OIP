<%@ page contentType="text/html; charset=GBK"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<Groups> glist = (List<Groups>) request.getSession().getAttribute("glist");
	//得到当前用户 主要用于判断approve功能
	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
	System.out.println("user approve level="+sysUser.getApproveLevel());
	String lockday = (String) request.getAttribute("lockday");
	String approveday = (String) request.getAttribute("approveday");

	String groupsId = (String) request.getAttribute("groupId2");
	int groupId = -1;
	if(groupsId!=null)
	{
		groupId = Integer.parseInt(groupsId);
	}

	System.out.println("groupId2="+groupId);
	//@Dancy 2011-11-17 添加取得lock和approve的session
	String testLock = (String) request.getSession().getAttribute("lockday");
	String testApprove= (String) request.getSession().getAttribute("approveday");
	System.out.println("Lock:"+lockday);
	System.out.println("Approve:"+approveday);
	System.out.println("testLock:"+testLock);
	System.out.println("testApprove:"+testApprove);
	if(null!=testLock)
	{
		lockday=testLock;
	}
	if(testApprove!=null)
	{
		approveday=testApprove;
	}
	
	System.out.println("lockday final jsp:"+lockday);
	System.out.println("approveday final jsp:"+approveday);
	
	//end
	
//hanxiaoyu01 2012-01-21 取得Lock的最小日期
   String minDate = (String) request.getSession().getAttribute("minDate");
	System.out.println("today3 minValue is: "+minDate);
	minDate = minDate.substring(5,7)+"/"+minDate.substring(8)+"/"+minDate.substring(0,4);
	String minDate2 =(String) request.getSession().getAttribute("minDate2");
	System.out.println("today4 minValue is: "+minDate2);
	minDate2 = minDate2.substring(5,7)+"/"+minDate2.substring(8)+"/"+minDate2.substring(0,4);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Data Checker 5</title>
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<link href="css/style_new.css" rel="stylesheet" type="text/css" />
		
<script src="js/datapicker/jquery-1.5.1.js"></script> 
<script src="js/datapicker/jquery.ui.core.js"></script> 
<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" /> 
<style>
ul
{
	width:313px;
	border:1px #666666 solid;
	margin:0; 
	padding:0; 
	position:absolute;
	top:26px;
	left:17px; 
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
	margin-left:17px;
	line-height:100%;
}
.ff, .fr
{
	width:311px;
	text-align:center;
	font-size:12px;
	border:1px #cccccc solid;
	height:21px;
	line-height:21px;
}
.fr
{
	width:150px;
}
.uu
{
	display:none;
	overflow:auto; 
	max-height:280px;
}

</style>	
<script type="text/JavaScript">

$(function() {
		var dates = $( "#today1, #today2" ).datepicker({
			changeMonth: true,
			changeYear: true,
			numberOfMonths: 1,
			onSelect: function( selectedDate ) {
				var option = this.id == "today1" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, selectedDate );
				dates.datepicker( "option", "dateFormat", "yy/mm/dd" );
			}			
		});
       
     
        $("#today3" ).datepicker({changeMonth: true, changeYear: true, minDate:'<%=minDate%>' });
		$("#today3" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
	    $("#today4" ).datepicker({changeMonth: true, changeYear: true});
		$("#today4" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
		// set filter content box to 'readonly'
		$(".ff").attr("readonly",true);	
		// set filter content box to 'readonly'
		$(".fr").attr("readonly",true);	
	});

var x1 = 0;
var y1 = 0;
var current = "";

$(document).ready(function (){
$("#today4").datepicker({
   onSelect:function (dateText,inst){
   $("#today4").datepicker("option","minDate",dateText);  
   }
});
$("#today3").datepicker({
  onSelect:function (dateText,inst){
   $("#today3").datepicker("option","minDate",dateText);  
   }
});
});
function getDropList(obj)
{
	x1 = $(obj).offset().left;
	y1 = $(obj).offset().top;
	current = obj.id;
	$("#ul"+obj.id).show();
	if($("#"+obj.id).val()=="'All Data'")
	{
		$(".boxg").attr("checked",true);
		$(".allg").attr("checked",true);
	}
}
function checkBox(obj,str)
{
	var vv = $("#"+str).val();
	vv = vv.replace("'All Data'","");
	var va = $("#"+obj.id+"v").text();
	var aa = $("input[name='c"+str+"']");
	if($(obj).attr("checked")==true)
	{		
		if(obj.id=="all"+str)
		{
			$(".box"+str).attr("checked",true);
			vv = "'All Data'";
		}
		else
		{
			$(".all"+str).attr("checked",false);
			vv = vv.replace("'All Data'","");
			vv = "'"+va + "'," +vv;
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
			$("#all"+str).attr("checked",false);
			vv = "";
			for( var i=0; i<aa.length;i++)
			{
				if($(aa[i]).attr("checked")==true)
				{
					vv = "'"+$("#"+aa[i].id+"v").text() + "'," +vv;
				}
			}
		}
	}
	$("#"+str).val(vv);
	$("#"+str).attr("title",vv);
}
$(document).mousemove(function(e)
{
	if(e.pageX<x1||e.pageX>x1+315||e.pageY<y1||e.pageY>y1+300)
	{
		$("#ul"+current).hide();
	}
});

</script>

	</head>
	<body>
	<center>
			<form action="ReportAction.do?operate=selectReportDateAndGroup&operPage=data_checker_05" method="post">
			
			<div class="main_box3" style="width:1100px;line-height:32px;">
				<h4>Report Export</h4>	
				Choose a start date:
				<input type="text" name="firstDay" readonly="readonly" id="today1" class="input_text4" maxlength="15" /><span class="style1" style="margin-right:15px;">*</span>
				<label id="selectenddate">Choose an end date:</label>
				<input type="text" name="endDay" readonly="readonly" id="today2" class="input_text4" maxlength="15" /><span class="style1">*</span>
				<br />
				<div style="float:left;">Select Groups:</div>
				<% if(sysUser.getLevelID()<4){ %>
				<div class="drop" align="left" >
					<input type="text" name="groupExcel" id="g" class="ff" value="'All Data'" title="'All Data'" onmouseover="javascript:getDropList(this);"/> 
					<ul id="ulg" class="uu" style="display:none;" >
					<li><input type="checkbox" name="cg" id="allg" class="allg" onclick="javascript:checkBox(this,'g');"/>
						<label id="allgv"><%=glist.get(0).getGname()%></label></li>
						<%
							for (int i = 1; i < glist.size(); i++) 
							{
						%>
						<li><input type="checkbox" name="cg" id="g<%=i %>" class="boxg" onclick="javascript:checkBox(this,'g');"/>
						<label id="g<%=i %>v"><%=glist.get(i).getGname()%></label></li>
						<%
							}
						%>
					</ul>
		 	 	</div>
			    <%} else{ %>
			    <div class="drop" align="left" >
			    <input type="text" name="groupExcel" id="g" class="fr" value="<%=sysUser.getGroupName() %>" title="<%=sysUser.getGroupName() %>"/> 
			    </div>
			    <%} %>
		    	<div style="float:left; margin-left:10px; line-height:23px;" >
		    		<input name="Submit2431" type="button" class="btnStyle1"value="CheckReport" onclick="javascript:checkReport()" />
		    	</div>
				<div style="clear:left; margin-top:10px; float:left;">				
					<textarea readonly="readonly" id="checkResult" name="checkResult" cols="120" rows="5" style="border:1px #ccc solid; text-align:left; float:left;">Please select the date area and the group.</textarea>
				</div>
				<div style="clear:left; margin-top:10px; float:left; display:inline;">
					<input id="Download1"  name="fwReport" type="button" class="btnStyle" style="float:left;margin-left:0px; width:250px;"
						value="Download FW Excel Report" disabled="disabled" onclick="javascript:excelExport()" />
					<input id="Download2"  name="nlcReport" type="button" class="btnStyle"  style="float:left;width:250px;"
						value="Download Non-LaborCosts Report" disabled="disabled"onclick="javascript:excelExport2()" />
				</div>
				<div style="clear:left; margin-top:10px;  float:left;">
					<input id="Download3"  name="cdReport" type="button" class="btnStyle"  style="float:left;margin-left:0px; width:250px;"
						value="Download Case Defect Report" disabled="disabled" onclick="javascript:excelExport3()" />
	
				</div>
			</div>
			<br />
			<div class="main_box3" style="width:1100px;line-height:32px;">
				<h4>Data Lock <% if(sysUser.getLevelID()<4) {%>& Approve<%} %></h4>
				Select a Group:
				<select name="GroupsId2" id="GroupsId2" onchange="groupChange()" style="margin-right:8px;">
				<%
					for (int i = 0; i < glist.size(); i++) 
					{
				%>
					<option value=<%=glist.get(i).getGid()%> <% if (groupId==glist.get(i).getGid()){%>selected="selected"<%} %>>
						<%=glist.get(i).getGname()%>
					</option>
				<%						
					}
				%>
			</select>
			<img src="images/lock.png" alt=" Lock " width="20" height="20"/>at <i><%=lockday %></i>
			<%
			//判断当前用户是否具有lock权限
			if(sysUser.getLevelID()<4)//如果有,显示
			{ %>
				<img src="images/approve.png" alt=" Approve " width="20" height="20"/> at <i><%=approveday %></i>
			<%} %>
			<input type="text"  name="today3" readonly="readonly" id="today3" class="input_text4" 
			style="height:23px;line-height:23px; margin-left:20px;" title="Choose a lock date!"/>			
			<input name="lockbutton" type="button" class="btnLock" onclick="javascript: lock();" value="Lock" />
			<%
			//判断当前用户是否具有approve权限
			if(sysUser.getLevelID()<4)//如果有,显示
			{ %>
				<input type="text" name="today4" readonly="readonly" id="today4" class="input_text4" 
				style="height:23px;line-height:23px; margin-left:20px;" title="Choose an approve date!" />
				<input name="approvebutton" type="button" onclick="javascript: approve();" class="btnApprove" value="Approve"/>
			<%} %>
		</div>
		<div class="btnsLine">
				<hr />
				<input name="Submit2332" type="button" class="btnBack" onclick='javascript:returnback();' />
		</div>
	</form>
</center>
<script language="javascript">

var scrollTop = <%=request.getAttribute("scrollTop")%>;
if (null!=scrollTop)
{
	window.scrollTo(0,scrollTop);
}
function returnback() 
{
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=selectDate&operPage=data_checker_01";
	form.submit();
}
function checkReport() 
{
	var dd1 = $("#today1").val();
	var dd2 = $("#today2").val();
	var groups = $("#g").val();
	<% if(sysUser.getLevelID()==4){ %>
	groups = "'"+groups+"',";
	<%} %>
	if(dd1=='')
	{
		alert("please choose a start date!");
		$("#today1").focus();
		return;
	}
	if(dd2=='')
	{
		alert("please choose an end date!");
		$("#today2").focus();
		return;
	}
	var url = "ReportAction.do?operate=checkReportRequest&operPage=data_checker_05";
	$.post(url,{groupExcel:groups,startDay:dd1,endDay:dd2},function(result)
	{
		//alert(result);
		$("#checkResult").val(result);
		$(".btnStyle").attr("disabled","");
	});

}
function excelExport() 
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=excelExport&operPage=data_checker_05&scrollTop="+ document.body.scrollTop;
	form.submit();
}
function excelExport2() 
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=excelExport2&operPage=data_checker_05&scrollTop="+ document.body.scrollTop;
	form.submit();
}
function excelExport3() 
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=excelExport3&operPage=data_checker_05&scrollTop="+ document.body.scrollTop;
	form.submit();
}
function lock()
{
	var day = $("#today3").val();
	if(day == "")
	{   
		alert("Please select a lock date!");
		$("#today3").focus();
		return;
	}
	alert("Lock:"+day);
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=lockExpense&operPage=data_checker_05";
	form.submit();		
  }
function approve()
{
	var day = $("#today4").val();
	if(day == "")
	{
		alert("Please select an approve date!");
		$("#today4").focus();
		return;
	}
	alert("Approve:"+day);	
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=approveExpense&operPage=data_checker_05";
	form.submit();	
}
 
function groupChange()
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=changeGroup&operPage=data_checker_05";
	form.submit();
}
</script>
</body>
</html>