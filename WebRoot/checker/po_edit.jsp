<%@ page language="java" import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*" contentType="text/html; charset=GBK"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	ProjectOrder po = (ProjectOrder) request.getAttribute("po");
	int poid = Integer.parseInt((String)request.getAttribute("poid"));
	@SuppressWarnings("unchecked")
	List<Map> managerList = (ArrayList<Map>) request.getSession().getAttribute("managerList");
	@SuppressWarnings("unchecked")
	List<Map> cycleList = (ArrayList<Map>) request.getAttribute("cycleList");
	
	String lastPath = (String)request.getSession().getAttribute("path");
	//System.out.println("Last Path is : "+lastPath);
	//2011-12-16
	SysUser user = (SysUser) request.getSession().getAttribute("user");
	@SuppressWarnings("unchecked")
	List<StatusChangeLog> loglist = (ArrayList<StatusChangeLog>) request.getAttribute("loglist");
	
	//添加了格式化方法 FWJ 2013-05-21
	//NumberFormat format = NumberFormat.getInstance();
	//format.setMinimumFractionDigits(2);
	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	//12-21 status change log显示
	String comm ="";
	System.out.println("size:"+loglist.size());
	if(loglist.size()>0){
		for(int i=0; i<loglist.size()-1;i++)
		{
			if(null!=loglist.get(i).getChangeDateTo())
			{
				comm +=(i+1)+". <font color='purple'>"+loglist.get(i).getTimeStamp().substring(0,16)+"</font> by <strong>"+loglist.get(i).getChangeUser()+
					"</strong> change the status from <font color='green'>"+loglist.get(i).getStatusFrom()+"</font> to <font color='green'>"+loglist.get(i).getStatusTo()+
					"</font> and set the "+loglist.get(i).getStatusTo()+" date to <font color='brown'>"+loglist.get(i).getChangeDateTo()+"</font>.<br />";
			}
			else
			{
				comm +=(i+1)+". <font color='purple'>"+loglist.get(i).getTimeStamp().substring(0,16)+"</font> by <strong>"+loglist.get(i).getChangeUser()+
					"</strong> change the status from <font color='green'>"+loglist.get(i).getStatusFrom()+"</font> to <font color='green'>"+loglist.get(i).getStatusTo()+
					"</font>.<br />";
			}
		}
		
		//if(loglist.size()!=1){
		int nn = loglist.size()-1;
		System.out.println("nn=="+nn);
		if(null!=loglist.get(nn).getChangeDateFrom())
		{
			comm += (nn+1)+". Created at <font color='purple'>"+loglist.get(nn).getTimeStamp().substring(0,16)+"</font> by <strong>"+loglist.get(nn).getChangeUser()+
					"</strong> and set the status to <font color='green'>"+loglist.get(nn).getStatusFrom()+"</font> date to <font color='brown'>"+loglist.get(nn).getChangeDateFrom()+"</font>.";
		}
		else
		{
			comm += (nn+1)+". Created at <font color='purple'>"+loglist.get(nn).getTimeStamp().substring(0,16)+"</font> by <strong>"+loglist.get(nn).getChangeUser()+
					"</strong> and set the status to <font color='green'>"+loglist.get(nn).getStatusFrom()+"</font>.";
		}
	//}
	}

	System.out.println("po alert: "+po.getAlertBalance());
	System.out.println("po balance: "+po.getPoBalance());
	//20130412 dancy 
	String result  = (String)request.getAttribute("result");
	System.out.println("result=="+result);

 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
    <title>Edit a PO</title>
	<link href="css/style_new.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" type="text/css" />
	<script src="js/datapicker/jquery-1.5.1.js"></script>
	<script src="js/datapicker/jquery.ui.core.js"></script>
	<script src="js/datapicker/jquery.ui.widget.js"></script>
	<script src="js/datapicker/jquery.ui.datepicker.js"></script>
	<link rel="stylesheet" href="js/datapicker/demos.css" type="text/css" />
	<link rel="stylesheet" href="js/datapicker/jquery.ui.datapicker.css" type="text/css" />
	<script>
		//取得datepicker.js
		$(function() {
		$(".date" ).datepicker({changeMonth: true, changeYear: true });
		$(".date" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
		$("#date0").val('<%=po.getNotStartStatusDate()%>');
		$("#date1").val('<%=po.getOpenStatusDate()%>');
		$("#date2").val('<%=po.getCloseStatusDate()%>');
			var dates = $( "#sdate, #edate" ).datepicker({
			changeMonth: true,
			numberOfMonths: 1,
			onSelect: function( selectedDate ) {
				var option = this.id == "sdate" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
				dates.datepicker( "option", "dateFormat", "yy-mm-dd" );
				
			}
		});
		setAlertV2("#amount");
		setAlertV2("#alertBalance");
		setAlertV2("#closeBalance");
		setAlertV2("#poUsed");
		setAlertV2("#poBalance");
	});
	</script>
  </head>  
  <body>
  <center>
  <div class="editPage3">
  <form action="" method="post">
  	<input type="hidden" id="uid" name="uid" value='<%=user.getUserId() %>' />
  	<input type="hidden" id="cf" name="cf" value='<%=po.getPOStatus()%>' />
  	<h3 style="margin-top:30px;">Edit PO</h3>
	PO Number : <label id="pno"><span style="color:red;"><% out.print(po.getPONumber()); %></span></label>
	<input type="button" class="btnSave2" onclick="javascript:save();" />
	<input type="button" value="Monthly Expense" class="btnStyle" onclick="javascript:gotoInvoiceList();"/>
	<div class="statusTable" style="width:760px;">
			<table border="0" cellpadding="0" cellspacing="0" width="750">
				<tr>
					<td align="right">Lock or Unlock : </td>
					<td>
						<input type="radio" value="Lock" name="lockStatus" id="lock" 
						<%if("Lock".equals(po.getLock())){
						 %>
						 checked="checked"
						 <%} %>/>Lock
					</td>
					<td align="right">PO State : </td>
					<td>
						<input type="radio" value="Not Start" name="poStatus" id="poStatus" onclick="getStatus();"
						<%if("Not Start".equals(po.getPOStatus())){
						 %>
						 checked="checked"
						 <%} %>/>Not Start
					</td>
					<td>
						<input type="text" id="date0" name="date0" class="date" style="border:0px; text-align:center;" 
						<%if(!"Not Start".equals(po.getPOStatus())) {%>disabled="disabled"<%} %> onchange="getDateV(this.value);"/>
					</td>
					<td colspan="2" align="center" class="ucell">
					<input type="hidden" class="btnStyle1" value="Update Balance" onclick="updateBalance();" title="Not Used!" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="radio" value="Unlock" name="lockStatus" id="unlock" 
						<%if("Unlock".equals(po.getLock())){
						 %>
						 checked="checked"
						 <%} %>/>Unlock
					</td>
					<td></td>
					<td>
						<input type="radio" value="Open" name="poStatus" id="poStatus" onclick="getStatus();"
						<%if("Open".equals(po.getPOStatus())){
						 %>
						 checked="checked"
						 <%} %>/>Open
					</td>
					<td>
						<input type="text" id="date1" name="date1" class="date" style="border:0px; text-align:center;" 
						<%if(!"Open".equals(po.getPOStatus())) {%>disabled="disabled"<%} %> onchange="getDateV(this.value);"/>
					</td>
					<td class="ucell" style="padding-left:10px;">PO Used($) : </td>
					<td class="ucell">
					<input type="text" class="input_text2" name="poUsed" id="poUsed" value='<%=format.format(po.getPoUsed()) %>' readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td>
						<input type="radio" value="Close" name="poStatus" id="poStatus" onclick="getStatus();"
						<%if("Close".equals(po.getPOStatus())){
						 %>
						 checked="checked"
						 <%} %>/>Close
					</td>
					<td>
						<input type="text" id="date2" name="date2" class="date" style="border:0px; text-align:center;" 
						<%if(!"Close".equals(po.getPOStatus())) {%>disabled="disabled"<%} %> onchange="getDateV(this.value);"/>
					</td>
					<td class="ucell" style="padding-left:10px;">Remaining Balance($) : </td>
					<td class="ucell">
					<input type="text" class="input_text2" name="poBalance" id="poBalance" value="<%=format.format(po.getPoBalance()) %>" readonly="readonly"/>
					</td>
				</tr>
			</table>
			</div>
			Budget($) : 
			<input type="text" class="input_text4" id="amount" name="amount" value='<%=format.format(po.getPOAmount())%>' style="margin-left:61px;margin-right:186px;" 
			onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" onchange="javascript:setAlertV(this);"  onpropertychange="setAlertV(this);"/>
			
			Alert Balance($) : 
			<input type="text" class="input_text4" id="alertBalance" name="alertBalance" value='<%=format.format(po.getAlertBalance()) %>' style="margin-left:8px;margin-right:20px;" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"/>
			<br />
			Close Balance($) : 
			<input type="text" class="input_text4" id="closeBalance" name="closeBalance" value='<%=format.format(po.getCloseBalance()) %>' style="margin-left:18px;margin-right:186px;" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"/>
			PO Manager : 
			<select id="manager" name="manager" style="width:154px; margin-left:28px;">
				<%for(int m=0;m<managerList.size();m++){ %>
				<option value='<%=managerList.get(m).get("POManagerID") %>'
				<%if(managerList.get(m).get("POManagerID").equals(String.valueOf(po.getPOManagerid()))){%>
				selected="selected"
				<% }%>>
				<%=managerList.get(m).get("POManager") %>
				</option>
				<%} %>
			</select>
			<br/>		
			PO Issued Date :
			<input type="text" class="input_text4" name="startDate" id="sdate" value='<%=po.getPOStartDate() %>' readonly="readonly"  style="margin-left:25px;margin-right:186px;"/>
			PO End Date : 
			<input type="text" class="input_text4" name="endDate" id="edate" value='<%=po.getPOEndDate() %>' readonly="readonly"  style="margin-left:23px;"/>
			<br />
			Email : <input type="text" id="email" name="email" class="input_text4" <%if(po.getEmail()==null){%>value=""<%}else{ %>value='<%=po.getEmail() %>' <%} %>  
			title="At most 5 e-mail addresses can be input! Each e-mail should be separated by a ';' character." onchange="checkemail(); " 
			style="width:684px; margin-left:8px; text-align:left;"/>
			<input type="text" id="mailinfo" style="color:red; margin-left:50px;border:0px;" readonly="readonly" />
			<br />
			Description : 
			<textarea rows="3" cols="25" name="description" id="description" style="border:1px #ccc solid;margin-left:10px;margin-right:214px"><%if(po.getDescription()==null||"null".equals(po.getDescription())){
							out.print("");}
							else {out.print(po.getDescription());}
							%></textarea>		
			Comment : 
			<textarea rows="3" cols="26" name="comment" id="comment" style="border:1px #ccc solid;"></textarea>
			<br />
			<div class="com_his">
						<%=comm %>
			</div>
			<p style="width:80%;text-align:center;">				
			<input type="button" id="backbtn" name="backbtn" class="btnBack" onclick="javascript:returnback();"/>
			</p>
							
			</form>
			</div>
    </center> 

 <script type="text/javascript">
<% if(result!=null){%>
//防止在保存数据成功时，金钱数字显示的仍然是没有逗号的情况。FWJ 2013-05-21
	setAlertV2("#amount");
	setAlertV2("#alertBalance");
	setAlertV2("#closeBalance");
	setAlertV2("#poUsed");
	setAlertV2("#poBalance");
	<%System.out.println("po.getPOAmount()="+po.getPOAmount());%>
	if('<%=result%>'=='true')
	{
		alert("Data save success!");
	}
	else if('<%=result%>'=='false')
	{
		alert("Data save fail!");
	}
<%}%>
var myDate = new Date(); 
var year = myDate.getFullYear();  
var month = myDate.getMonth()+1;  
var day = myDate.getDate();
var hh = myDate.getHours();
var mm = myDate.getMinutes();
var curdate = year+"-"+month+"-"+day+" "+hh+":"+mm;	
var dd='';
var uname = '<%=user.getUserName()%>';
var os = '<%=po.getPOStatus()%>'
var ot = '<%=po.getNotStartStatusDate()%>';
var oo = '<%=po.getOpenStatusDate()%>';
var oc = '<%=po.getCloseStatusDate()%>';
var comm = '<%=po.getComment()%>'
var st='';
var ds='';
var pid = <%=poid%>;



function save()
{
	var date0 = document.getElementById("date0").value;
	var date1 = document.getElementById("date1").value;
	var date2 = document.getElementById("date2").value;
	var pb = $("#poBalance").val();
	if(!checkInput())
	{
		return;
	}
	if(!checkemail())
	{
		alert("Please check the format of the e-mail!");
		return;
	}
	var form = document.forms[0];
	form.action = "POAction.do?operate=savePOInfo&operPage=po_list&poid="+pid+"&date0="
					+date0+"&date1="+date1+"&date2="+date2+'&dt='+dd+'&ds='+ds+'&pb='+pb;
	form.submit();

}

function setAlertV(obj)
{
	var val=$(obj).val();
	
	if (/[\u4E00-\u9FA5a-zA-Z]/i.test(val)) {
	    $(obj).val("");//禁止粘贴了非数字类
	    return false;
		}
		if (window._inHandler) 
		{
			return false;
		}
		var sinput = clearData(val);

		window._inHandler = true;  //防止在ie下死循环 FWJ 2013-05-21
		
		$(obj).val((formatNum(sinput)));
		
	//	$("#alertBalance").val(parseFloat((val/10).toFixed(4)));
		$("#alertBalance").val(parseFloat((sinput/10).toFixed(4)));
		var ps = $("#poUsed").val();
		ps=ps.replace(/,/g,"");
	//	$("#poBalance").val(parseFloat((val - ps).toFixed(4)));
		$("#poBalance").val(parseFloat((sinput - ps).toFixed(4)));
		window._inHandler = false;
		setAlertV2("#alertBalance");
		setAlertV2("#poBalance");
}

function setAlertV2(obj)
{
	var val_n = $(obj).val();
	if (window._inHandler) 
	{
		return false;
	}
	var sinput = clearData(val_n);

	window._inHandler = true;  //防止在ie下死循环 FWJ 2013-05-21
	
	$(obj).val((formatNum(sinput)));
	
	window._inHandler = false;
}

function getStatus(){

	var str = document.getElementsByName("poStatus");
 	for(var i=0;i<str.length;i++)
 	{
 		if(str[i].checked)
 		{
 			st = str[i].value;
 		}
	}
	if('Not Start'==st)
	{
		document.getElementById("date0").disabled="";
		document.getElementById("date1").disabled="false";
		document.getElementById("date2").disabled="false";
		dd = document.getElementById("date0").value;
		document.getElementById("date1").value = oo;
		document.getElementById("date2").value = oc;
	}
	if('Open'==st)
	{
		document.getElementById("date1").disabled="";
		document.getElementById("date0").disabled="false";
		document.getElementById("date2").disabled="false";
		dd = document.getElementById("date1").value;
		document.getElementById("date0").value = ot;
		document.getElementById("date2").value = oc;
	}
	if('Close'==st)
	{
		document.getElementById("date2").disabled="";
		document.getElementById("date0").disabled="false";
		document.getElementById("date1").disabled="false";
		dd = document.getElementById("date2").value;
		document.getElementById("date0").value = ot;
		document.getElementById("date1").value = oo;
	}
	if(st!=os)
	{
		document.getElementById("comment").value = curdate
		+' by '+uname+' change the status from '+os+' to '+st;		
	}
	if(st==os){
		document.getElementById("comment").value = comm;
	}
	ds = dd;
}
function getDateV(val)
{
	if(val!=dd)
	{
		document.getElementById("comment").value = curdate
		+' by '+uname+' change the status from '+os+' to '+st
		+' and set the '+st+' date to '+val;
		ds = val;
    }
}
function checkInput()
{
	var pno = document.getElementById("pno")
	var amount = document.getElementById("amount");
	var alertB = document.getElementById("alertBalance");
	var closeB = document.getElementById("closeBalance");
	var email = document.getElementById("email");
	var sdate = document.getElementById("sdate");
	var edate = document.getElementById("edate");
	var pm = document.getElementById("manager");
//	var quo = document.getElementById("quoNumber");
	var lockStatus = "";
	var str2 = document.getElementsByName("lockStatus");
 	for(var i=0;i<str2.length;i++)
 	{
 		if(str2[i].checked)
 		{
 			lockStatus = str2[i].value;
 		}
	}
 	if(lockStatus=="")
	{
		alert("please choose a Lock Status");
		return false;
	}
	
 	var poStatus = "";
	var str = document.getElementsByName("poStatus");
 	for(var i=0;i<str.length;i++)
 	{
 		if(str[i].checked)
 		{
 			poStatus = str[i].value;
 		}
	}
	if(poStatus=="")
	{
		alert("please choose a PO Status");
		return false;
	}
	if(pno.value==''){
		alert("Please enter a PO Number!");
		pno.focus();
		return false;		
	}
	if(amount.value==''){
		alert("Please enter a PO Budget!")
		amount.focus();
		return false;
	}
	if(alertB.value==''){
		alert("Please enter a Alert Balance!")
		alertB.focus();
		return false;
	}
	if(closeB.value==''){
		alert("Please enter a Close Balance!")
		closeB.focus();
		return false;
	}
	if(pm.options[pm.selectedIndex].value=='-1'){
		alert("Please select a PO Manager!")
		pm.focus();
		return false;
	}
	if(sdate.value==''){
		alert("Please select a PO Start Date!")
		sdate.focus();
		return false;
	}
	if(edate.value=='')
	{
		alert("Please select a PO End Date!")
		edate.focus();
		return false;
	}
	return true;
}
//email中是不允许有空格出现的,所以一开始就把所有的空格去掉
function checkemail()
{
	var email = document.getElementById("email").value;
	if(email!='')
	{
		//remove the all Spaces  use replace(/\s/ig,'')
		email = email. replace(/\s/ig,'');
		$("#email").val(email);
		//check the address if end with ;
		if(email.substring(email.length-1)==';')
		{
			email = email.substring(0,email.length-1);
		}
		var email_2= email.split(';');
		if(email_2.length>5)
		{
			$("#mailinfo").val("At most 5 email");
			$("#email").select();
			return false;
		}
		//regular expression for email address
		var CheckMail =/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		//var CheckMail = /^([a-zA-Z0-9_\s\.\-])+@{1}([a-zA-Z0-9_])+\.([a-zA-Z0-9_])+/;
		var str = "";
		for(i=0;i<email_2.length;i++)
		{
		 	str = email_2[i];
			if(!CheckMail.test(str))
			{
				$("#mailinfo").val("Wrong format of email ！");
				$("#email").select();
				return false;
			}
		}
	}
	$("#mailinfo").val("");	
	return true;
}


function returnback(){
	var pathName = '<%=lastPath%>';
	if(pathName=='po_assignment')
	{
		var form = document.forms[0];
		form.action = "POAction.do?operate=searchPO&operPage=po_assignment";
		form.submit();
	}
	else{
		var form = document.forms[0];
		form.action = "POAction.do?operate=showPOList&operPage=po_list";
		form.submit();
	}
}

function gotoInvoiceList(poid)
{
	var poname = '<%=po.getPONumber()%>';
	var poid = '<%=po.getPOID()%>';	
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=searchbyMonthlyEx&operPage=Invoice_List&parentpage=po_edit&POID="+poid+"&PONAME="+poname;
	form.submit();
}

function myKeyDown()
{
	var k=window.event.keyCode;   
    if ((k==46)||(k==8)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)||k==110||k==190||k==17||k==86||k==67||k==88) 
    {
   		window.event.returnValue = true;
    }
   	else if(k==13)
   	{
        window.event.keyCode = 9;
   	}
  	 else
   	{
      	window.event.returnValue = false;
   	}
}
function myKeyUp(obj)
{
	var vv = $(obj).val();
	vv = vv.replace(getStr(vv),"");
	if(vv.length>1&&vv.substring(0,1)=="0")
	{
		vv = vv.substring(1);
	}
	//如果是小数，小数点左边是0，被清掉后，这里补上
	if(vv.substring(0,1)=='.')
	{
		vv = '0' + vv;
	}
	$(obj).val(vv);
}
function getStr(vv)
{
	var arr = ["!","@","#","$","%","^","&","*","(",")",">"];
	var str = "";
	for(var i=0;i<arr.length;i++)
	{
		if(vv.indexOf(arr[i])>=0)
		{
			str = arr[i];
		}
	}
	return str;
}
function formatNum(strNum) 
{
    if (strNum.length <= 3) 
    {
        return strNum;
    }
    if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)) 
    {
        return strNum;
    }
    var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
    var re = new RegExp();
    re.compile("(\\d)(\\d{3})(,|$)");
    while (re.test(b)) 
    {
        b = b.replace(re, "$1,$2$3");
    }
    return a + "" + b + "" + c;
}

function clearData(str) 
{
    var stemp = str.split(',');
    var newstr = "";
    for (i = 0; i <= stemp.length - 1; i++) 
    {
        newstr += stemp[i];
    }
    return newstr;
}
 </script>
 </body>
</html>
