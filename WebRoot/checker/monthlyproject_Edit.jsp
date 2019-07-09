<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<Map> monthprojectlist = (List<Map>) request.getSession().getAttribute("monthlyproject_select");
	@SuppressWarnings("unchecked")
	List<Map> locationlist = (List<Map>) request.getSession().getAttribute("location_select");
	System.out.println("location.size="+locationlist.size());
	
	@SuppressWarnings("unchecked")
	List<Map> businesscategorylist = (List<Map>) request.getSession().getAttribute("businesscategory_select");
	@SuppressWarnings("unchecked")
	List<Map> paymentlist = (List<Map>) request.getSession().getAttribute("payment_select");
	
	String oper = (String) request.getAttribute("oper");
	System.out.println("oper="+oper);
	String oper_new = (String) request.getAttribute("oper_new");
	System.out.println("oper_new="+oper_new);
	
	//添加了格式化方法 FWJ 2013-05-21
	//NumberFormat format = NumberFormat.getInstance();
	//format.setMinimumFractionDigits(2);
	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	List<Monthlyproject> editMp =null;

	if("edit".equals(oper))
	{
		editMp = (List<Monthlyproject>)request.getAttribute("editMonthlyproject");
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title> Project <%if("edit".equals(oper)&&null==oper_new){%>Edit<%}else{%>Add<% } %> </title>	
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
	<link href="css/style_new.css" rel="stylesheet" type="text/css" />
	<script src="js/datapicker/jquery.ui.core.js"></script> 
	<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
	<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" /> 
	<script >
	$(function() 
	{	
		setAlertV2("#budget");

	});	
	</script>
	</head>
	<body>

		<div class="editPage" style="padding-top:70px; line-height:29px;">
		<form action="InvoiceAction.do?operate=addProject&operPage=monthlyproject_Eidt_submit&oper=addorcopy" target="hide" method="post">
			<%if("edit".equals(oper)){%>
				<input type="hidden" id="projectid" name="projectid" value="<%=editMp.get(0).getMonthprojectid() %>"/>
			<%	} %>
			Project:
			<br />
			<select name = "project_select" id="project_select" style="width:293px;height:21px; margin:-1px;" onchange="javascript:onchangeSeltoText(this,'project');" onpropertychange="javascript:onchangeSeltoText(this,'project');">
			
			<option value="-1"></option>
			<%
			for (int k=0; k<monthprojectlist.size(); k++)
			{
				 if("new".equals(oper))
				{
			%>
				<option value=<%= monthprojectlist.get(k).get("monthproject")%>>
					<%=monthprojectlist.get(k).get("monthproject")%>
				</option>
			<%}
				 else
					{
				%>
					<option value=<%= monthprojectlist.get(k).get("monthproject")%> <%if(monthprojectlist.get(k).get("monthproject").equals(editMp.get(0).getMonthproject().trim())){%>selected="selected" <%}%>>
						<%=monthprojectlist.get(k).get("monthproject")%>
					</option>
				<%}
			}
			%>	
			</select>
			
			<span style="margin-left:-295px;">
			<input type="text" name="project_text" id="project_text" title="Enter or select a project" <%if("edit".equals(oper)){%> value = "<%=editMp.get(0).getMonthproject()%>"<%} else {%>value=""<%}%> onfocus="javascript:focusOn(this);" style="width:270px;border:0px; height:17px;" />
			</span>
			<br />
				
			Business Category:
			<br />
			<select name = "category_select" id="category_select" style="width:293px;height:21px; margin:-1px;" onchange="javascript:onchangeSeltoText(this,'category');" onpropertychange="javascript:onchangeSeltoText(this,'category');">
			<option value="-1"></option>
			<%for(int i=0;i<businesscategorylist.size();i++){
				if("new".equals(oper)){
				%>
			<option value=<%=businesscategorylist.get(i).get("category") %>><%if(businesscategorylist.get(i).get("category")==null){%><%}else{%><%=businesscategorylist.get(i).get("category")%> <%} %></option> 
			<%}
				else{%>
					<option value=<%= businesscategorylist.get(i).get("category")%> <%if(editMp.get(0).getBusinesscategory()!=null&&businesscategorylist.get(i).get("category").equals(editMp.get(0).getBusinesscategory().trim())){%>selected="selected" <%}%>>
						<%=businesscategorylist.get(i).get("category")%>
					</option>
			<%}	}%>
			</select>
			
			<span style="margin-left:-295px;">
			<input type="text" name="category_text" id="category_text" title="Enter or select a business category" <%if("edit".equals(oper)&&editMp.get(0).getBusinesscategory()!=null){%> value = "<%=editMp.get(0).getBusinesscategory()%>"<%} else {%>value=""<%}%> onfocus="javascript:focusOn(this);" style="width:270px;border:0px; height:17px;" />
			</span>
			<br />
			Invoice/Downpayment:
			<br />
			<select name = "payment_select" id="payment_select" style="width:293px;height:21px; margin:-1px;" onchange="javascript:onchangeSeltoText(this,'payment');" onpropertychange="javascript:onchangeSeltoText(this,'payment');">
			<option value="-1"></option>
			<%for(int i=0;i<paymentlist.size();i++){
				if("new".equals(oper)){%>
			<option value=<%=paymentlist.get(i).get("payment") %>>
				<%if(paymentlist.get(i).get("payment")==null){%><%}
					else{%> <%=paymentlist.get(i).get("payment")%> <%} %>
			</option> 
			<%}else{%>
				<option value=<%= paymentlist.get(i).get("payment")%> <%if(editMp.get(0).getPayment()!=null&&paymentlist.get(i).get("payment").equals(editMp.get(0).getPayment().trim())){%>selected="selected" <%}%>>
						<%=paymentlist.get(i).get("payment")%>
				</option>		
				<%}
				}%>
			</select>
			<span style="margin-left:-295px;">
			<input type="text" name="payment_text" id="payment_text" title="Enter or select a payment" 
				<%if("edit".equals(oper)&&editMp.get(0).getPayment()!=null){%> 
				value = "<%=editMp.get(0).getPayment()%>"<%} 
				else {%>value=""<%}%> onfocus="javascript:focusOn(this);" style="width:270px;border:0px; height:17px;" />
			</span>
			<br />
			Location:
			<br />
			<select name="location" id="location" style="width:110px">
			<option value="-1"></option>
			<%for(int i=0;i<locationlist.size();i++){
				if("new".equals(oper)){%>
			<option value=<%=locationlist.get(i).get("locationid") %>><%=locationlist.get(i).get("location")%></option> 
			<%} else{%>
				<option value=<%= locationlist.get(i).get("locationid")%> <%if(editMp.get(0).getLocation()!=null&&locationlist.get(i).get("location").equals(editMp.get(0).getLocation().trim())){%>selected="selected" <%}%>>
						<%=locationlist.get(i).get("location")%>
				</option>
				
			<%}	}%>
			</select>
			<br />		
			Initial Quotation/Budget ($):
			<br />
			<input type="text" id="budget" name="budget" title="Please only enter the Arabic numeral!" 
			<% if("edit".equals(oper)) {%>value="<%=format.format(editMp.get(0).getBudget())%>" <%}%> value="0"  
			<% if("new".equals(oper)) {%>onblur="if(this.value=='0') {this.value=defaultValue;}" 
			onfocus="if(this.value==defaultValue) {this.value='0';}"<%} %> 
			style="width:120px; text-align:center" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"/>
			<br />
			
			<p style="margin-left:50px;">
			<input type="button" class="btnBack" onclick="javascript: back();" />
			<input type="button" class="btnSave2" <%if("edit".equals(oper)&&null==oper_new){%> onclick="javascript: modifyProject();"
			<%}else{ %>onclick="javascript: saveInvoice();" <%}%> />
			</p>
		</form>
		</div>

		<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
<script type="text/javascript">
function focusOn(obj)
{
	if($(obj).val()!='Enter or select a project')
	{
		$(obj).select();
	}
}

function onchangeSeltoText(obj,str)
{
//	var txt = $(obj).find("option:selected").text();
//remove the spaces by FWJ 2013-12-13
	var txt = $(obj).find("option:selected").text().replace(/(^\s*)|(\s*$)/g, "");
	if(str=='project'){
		$("#project_text").val(txt);
	}
	if(str=='category'){
		$("#category_text").val(txt);
	}
	if(str=='payment'){
		$("#payment_text").val(txt);
	}
}

function saveInvoice()
{	
//	if($("#wbs").val()==-1)
//	{
//		alert("Wbs should not be blank!");
//		$("#wbs").focus()
//		return;
//	}
	var text=$("#project_text").val();
	if(text==''|| text=='Enter or select the project')
	{
		alert("Project should not be blank!");
		$("#project_text").focus();
		return;
	}

	if($("#budget").val().replace(/[ ]/g,"")=="")
	{
		$("#budget").val("0");
	}
	
	var form = document.forms[0];
	form.submit();
}
function modifyProject()
{
	var text=$("#project_text").val();
	if(text==''|| text=='Enter or select the project')
	{
		alert("Project should not be blank!");
		$("#project_text").focus();
		return;
	}

	if($("#budget").val().replace(/[ ]/g,"")=="")
	{
		$("#budget").val("0");
	}
	
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=addProject&operPage=monthlyproject_Eidt_submit&oper=edit";
	form.submit();
}
function back()
{
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=searchWithMpFilter&operPage=monthlyproject_List&back=monthlyproject_edit";
	form.target = "";
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
	vv = vv.replace(getStr(vv),'');
	if(vv.length>1&&vv.substring(0,1)=='0')
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