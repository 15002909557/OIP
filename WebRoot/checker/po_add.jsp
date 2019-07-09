<%@ page language="java" import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*" contentType="text/html; charset=GBK"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<Map> managerList = (ArrayList<Map>) request.getAttribute("managerList");
	@SuppressWarnings("unchecked")
	List<Map> cycleList = (ArrayList<Map>) request.getAttribute("cycleList");
	String lastPath = (String)request.getSession().getAttribute("path");
	//System.out.println("Last Path is : "+lastPath);
	SysUser user = (SysUser) request.getSession().getAttribute("user");
	//添加对po和copy的取值,引进了com.beyondsoft.expensesystem.domain.checker.*包。 FWJ on 2013-04-16 
	ProjectOrder po = (ProjectOrder)request.getAttribute("po");
	//NumberFormat format = NumberFormat.getInstance();
	//format.setMinimumFractionDigits(2);
	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	String copy = (String) request.getAttribute("copy");
	
	if(null != copy)
		System.out.println("The step is copy PO!");
	else
		System.out.println("The step is add PO!");
	System.out.println("copy here is:"+copy);


 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>   
    <title>Add a new PO</title>
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
			getStatus();
		<% if(null != copy) {%>
			$("#date0").val('<%=po.getNotStartStatusDate()%>');
			$("#date1").val('<%=po.getOpenStatusDate()%>');
			$("#date2").val('<%=po.getCloseStatusDate()%>');
				for(var i=0;i<3;i++){
					var ss = document.getElementsByName("poStatus");
				 	tt = ss[i].value;
					getDateV_Copy($("#date"+i).val(),tt);
				}		
		<%}%>
		
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
			 });
	</script>
  </head>
  
  <body>
  <center>
  	<div class="editPage3">
  		<form action="" method="post"> 			   
			<h3>Add a new PO</h3>
			PO Number : <input type="text" id="pno" name="pno" class="input_text4" style="margin-right:100px;" <%if(null != copy) {%>value="<%= po.getPONumber()%>"<%}%>/>
			<input type="button" class="btnSave2" onclick="javascript:addPO();"/>
			<%if(null==copy) {%>
			<input type="reset" class="btnReset" value="" />
			<%} %>
			<div class="statusTable">
			<table border="0" cellpadding="0" cellspacing="0" width="600">
				<tr>
					<td align="right">Lock Status</td>
					<td>
						<input type="radio" value="Lock" name="lockStatus" id="lock"
						<%if(null != copy&&"Lock".equals(po.getLock())){
						 %>
						 checked="checked"
						 <%}else if(null==copy){%>checked="checked"<%} %>/>Lock
					</td>
					<td align="right">PO State</td>
					<td>
						<input type="radio" value="Not Start" name="poStatus" id="poStatus" onclick="getStatus();"
						<%if(null != copy&&"Not Start".equals(po.getPOStatus())){
						 %>
						 checked="checked"
						 <%}else if(null==copy){%>checked="checked"<%} %>/>Not Start
					</td>
					<td>
						<input type="text" id="date0" name="date0" class="date" style="border:0px; text-align:center;"  <%if(null== copy) {%><%} else if(null!= copy&&!"Not Start".equals(po.getPOStatus())){%>disabled="disabled"<%}%> onchange="getDateV(this.value);"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input type="radio" value="Unlock" name="lockStatus" id="unlock" 
						<%if(null != copy&&"Unlock".equals(po.getLock())){
						 %>
						 checked="checked"
						 <%} %>/>Unlock
					</td>
					<td></td>
					<td>
						<input type="radio" value="Open" name="poStatus" id="poStatus" onclick="getStatus();"
						<%if(null != copy&&"Open".equals(po.getPOStatus())){
						 %>
						 checked="checked"
						 <%} %>/>Open
					</td>
					<td>
						<input type="text" id="date1" name="date1" class="date"  style="border:0px;text-align:center;" <%if(null== copy) {%>disabled="disabled"<%} else if(null!= copy&&!"Open".equals(po.getPOStatus())){%>disabled="disabled"<%}%> onchange="getDateV(this.value);"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td>
						<input type="radio" value="Close" name="poStatus" id="poStatus" onclick="getStatus();"
						<%if(null != copy&&"Close".equals(po.getPOStatus())){
						 %>
						 checked="checked"
						 <%} %>/>Close
					</td>
					<td>
						<input type="text" id="date2" name="date2" class="date" style="border:0px;text-align:center;" <%if(null== copy) {%>disabled="disabled"<%} else if(null!= copy&&!"Close".equals(po.getPOStatus())){%>disabled="disabled"<%}%> onchange="getDateV(this.value);"/>
					</td>
				</tr>
			</table>
			</div>
			Budget($) : 
			<input type="text" class="input_text4" id="amount" name="amount" <%if(null != copy) {%>value="<%=format.format(po.getPOAmount())%>"<%} %>value="0" style="margin-left:61px;margin-right:134px;ime-mode:disabled" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" oninput="setAlertV(this);" onpropertychange="setAlertV(this);"/>
			Alert Balance($) : 
			<input type="text" class="input_text4" id="alertBalance" name="alertBalance" <%if(null != copy) {%>value="<%= format.format(po.getAlertBalance())%>"<%} %>value="0" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" style="margin-left:24px;" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"/>
			<br />
			Close Balance($) : 
			<input type="text" class="input_text4" id="closeBalance" name="closeBalance" <%if(null != copy) {%>value="<%= format.format(po.getCloseBalance())%>"<%} %>value="0" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" style="margin-left:18px;margin-right:134px;" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"/>		
			PO Manager : 
			<select id="manager" name="manager" style="width:154px; margin-left:44px; ">
				<option value="-1"></option>
				<%for(int m=0;m<managerList.size();m++){ %>
				<option value='<%=managerList.get(m).get("POManagerID") %>'
				<%if(null != copy&&managerList.get(m).get("POManagerID").equals(String.valueOf(po.getPOManagerid()))){%>
				selected="selected"
				<% }%>>
				<%=managerList.get(m).get("POManager") %>
				</option>
				<%} %>
			</select>
			
			<br/>
			PO Issued Date : 
			<input type="text" class="input_text4" name="startDate" id="sdate" <%if(null != copy) {%>value="<%= po.getPOStartDate()%>"<%} %> readonly="readonly"  style="margin-left:25px;margin-right:134px;"/>
			PO End Date : 
			<input type="text" class="input_text4" name="endDate" id="edate" <%if(null != copy) {%>value="<%= po.getPOEndDate()%>"<%} %> readonly="readonly"  style="margin-left:40px;"/>
			<br />	
			Email : <input type="text" id="email" name="email" class="input_text4"  style="margin-left:8px; width:649px;" <%if(null != copy&&po.getEmail()==null){%>value=""<%}else if(null != copy){ %>value='<%=po.getEmail() %>' <%} %> onchange="checkemail();"
			title="At most 5 e-mail addresses can be input! Each e-mail should be separated by a ';' character."/>
			<input type="text" id="mailinfo" style="color:red; border:0px; margin-left:60px;" readonly="readonly" />
			<br />
			Description : 
			<textarea rows="5" cols="25" name="description" id="description" style="border:1px #ccc solid;margin-left:10px;margin-right:134px;"><%if(null != copy&&(po.getDescription()==null||"null".equals(po.getDescription()))){out.print("");}else if(null != copy){%><%=po.getDescription()%><%}%></textarea>		
			Comment : 
			<textarea rows="5" cols="30" name="comment" id="comment" style="border:1px #ccc solid;"></textarea>
			<p style="width:80%;text-align:center;">				
			<input type="button" id="backbtn" name="backbtn" class="btnBack" onclick="javascript:returnback();"/>
			</p>
			<input type="hidden" id="uid" name="uid" value='<%=user.getUserId() %>'/>  
		</form>
	</div>
  </center> 
  
 <script type="text/javascript">
 
function addPO()
{
	var pno = document.getElementById("pno").value
	if(!checkInput()){return false;}
	if(!checkemail())
	{
		alert("Please check the format of the e-mail!");
		return;
	}
	//添加了对重名的po的判断 FWJ on 2013-04-27
	$.post("POAction.do",{"operate":"checkPOadd","pon":pno},function(data)
	{
  	     if(data=="true")
  	   	 {
  	  	   	 alert("The PO Number has been existed!");
  	  	 }else
  	  	 {
				var form = document.forms[0];
				form.action = "POAction.do?operate=addPO&operPage=po_list";
				form.submit();
		}
 	});
}
	
	var myDate = new Date(); 
	var year = myDate.getFullYear();  
	var month = myDate.getMonth()+1;  
	var day = myDate.getDate();
	var hh = myDate.getHours();
	var mm = myDate.getMinutes();
	var curdate = year+"-"+month+"-"+day+" "+hh+":"+mm;
	
 	var comm = '';
 	var uname = '<%=user.getUserName()%>';
 
 	var st;
 	var ds;

 	
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
	}
	if('Open'==st)
	{
		document.getElementById("date1").disabled="";
		document.getElementById("date0").disabled="false";
		document.getElementById("date2").disabled="false";
	}
	if('Close'==st)
	{
		document.getElementById("date2").disabled="";
		document.getElementById("date0").disabled="false";
		document.getElementById("date1").disabled="false";
	}
	
	 comm = 'Created at '+curdate +' by '+uname+' and set the initial status to '+st;
	 document.getElementById("comment").value = comm;
	}
function getDateV(val){
	if(val!='')
	{
		comm += ', the '+st+' date is '+val;
    }
    document.getElementById("comment").value = comm;
}

function getDateV_Copy(val,aa){
	if(val!='')
	{
		comm += ', the '+aa+' date is '+val;
    }
    document.getElementById("comment").value = comm;
}

function setAlertV(obj){
	
	var val_n = $(obj).val();
	var val_a = val_n/10;


	if (/[\u4E00-\u9FA5a-zA-Z]/i.test(val_n)) {
    $(obj).val("");//禁止粘贴了非数字类
    return false;
	}
	if (window._inHandler) 
	{
		return false;
	}
	var sinput = clearData(val_n);

	window._inHandler = true;  //防止在ie下死循环 FWJ 2013-05-21
	
	$(obj).val((formatNum(sinput)));
	
	window._inHandler = false;

	$("#alertBalance").val(parseFloat((sinput/10).toFixed(4)));
	setAlertV2("#alertBalance");
//	alert(formatNum(val_n));
}
//用于加载的时候和除了Budget以外的涉及到金额的input使用 FWJ 2013-05-21
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

function checkInput(){
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
	
	if(pno.value.replace(/(^\s*)|(\s*$)/g,'')==''){
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
	if(edate.value==''){
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
function formatNum(strNum) {
    if (strNum.length <= 3) {
        return strNum;
    }
    if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)) {
        return strNum;
    }
    var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
    var re = new RegExp();
    re.compile("(\\d)(\\d{3})(,|$)");
    while (re.test(b)) {
        b = b.replace(re, "$1,$2$3");
    }
    return a + "" + b + "" + c;
}

function clearData(str) {
    var stemp = str.split(',');
    var newstr = "";
    for (i = 0; i <= stemp.length - 1; i++) {
        newstr += stemp[i];
    }
    return newstr;
}
 </script>
 </body>
</html>
