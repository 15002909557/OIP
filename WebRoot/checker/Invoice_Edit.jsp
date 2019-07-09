<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")

	List<Map> polist = (List<Map>) request.getSession().getAttribute("polist");

	@SuppressWarnings("unchecked")
	List<Map> yearlist = (List<Map>) request.getSession().getAttribute("yearselect");//修改了yearlist为yearselect的获取，fwj 2013-05-08
	
	@SuppressWarnings("unchecked")
	List<Map> monthlist = (List<Map>) request.getSession().getAttribute("monthlist");
	
	@SuppressWarnings("unchecked")
	List<String> monthprojectlist = (List<String>) request.getSession().getAttribute("monthprojectlist");
	
	@SuppressWarnings("unchecked")
	List<Map> categorylist = (List<Map>) request.getSession().getAttribute("categorylist");

	@SuppressWarnings("unchecked")
	List<Map> clientlist = (List<Map>) request.getSession().getAttribute("clientlist");
	
	@SuppressWarnings("unchecked")
	List<Map> wbslist = (List<Map>) request.getSession().getAttribute("wbslist");
	
	@SuppressWarnings("unchecked")
	List<Map> paymentlist = (List<Map>) request.getSession().getAttribute("payment_select"); //FWJ 2013-0620
	
	String oper = (String) request.getAttribute("oper");
	//增加了对copy值的获取 FWJ 2013-04-18
	String oper_new = (String) request.getAttribute("oper_new");
	
	//添加了格式化方法 FWJ 2013-05-21
	//NumberFormat format = NumberFormat.getInstance();
	//format.setMinimumFractionDigits(2);
	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	Invoice invoice = null;

	if("edit".equals(oper))
	{
		invoice = (Invoice)request.getAttribute("invoice");
	}
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title> Expense <%if("edit".equals(oper)&&null==oper_new){%>Edit<%}else{%>Add<% } %> </title>	
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
	<link href="css/style_new.css" rel="stylesheet" type="text/css" />
	<script src="js/datapicker/jquery.ui.core.js"></script> 
	<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
	<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" /> 
	<script >
	$(function() 
	{	
		setAlertV2("#cost");
	});	
	</script>
	</head>
	<body>

		<div class="editPage" style="padding-top:53px; line-height:29px;">
		<form action="InvoiceAction.do?operate=addInvoice&operPage=Invoice_Eidt_submit" target="hide" method="post">
			<%if("edit".equals(oper)){%>
				<input type="hidden" id="invoiceid" name="invoiceid" value="<%=invoice.getMonthlyexpenseid() %>"/>
			<%	} %>
			Year:
			<select name="Year" id="year" style="width:120px" >	
			<option value="-1">Select a year</option>
			<%
			//取得yearlist的year name不可能为空
			for (int j=0; j<yearlist.size(); j++)
			{
			%>
				<option value=<%= yearlist.get(j).get("yearid")%> 
				<%if("edit".equals(oper) &&invoice.getYear().equals(yearlist.get(j).get("year")))
				{%>
					selected="selected" 
				<%} %>>
				<%=yearlist.get(j).get("year") %></option>
			<%
			}
			%>	
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			Month:
			<select name="Month" id="month" style="width:120px" >
			<option value="-1">Select a month</option>
			<%
			for (int k=0; k<monthlist.size(); k++){
			%>
				<option value=<%= monthlist.get(k).get("monthid")%> 
				<%if("edit".equals(oper) &&invoice.getMonth().equals(monthlist.get(k).get("month")))
				{%>
					selected="selected" 
				<%} 
				 %>>
				<%=monthlist.get(k).get("month") %>
				</option>
			<%}%>	
			</select>
			<br />
			Project:
			<br />
			<select name = "project_select" id="project_select" style="width:293px;height:21px; margin:-1px;" onchange="javascript:onchangeSeltoText(this,'project');" onpropertychange="javascript:onchangeSeltoText(this,'project');">
			
			<% if("edit".equals(oper))
			{ %>
				<option value="<%=invoice.getMonthproject() %>"><%=invoice.getMonthproject() %></option>
			<%} 
			else
			{%>
			<option value=""></option>
			<%
			}
			for (int k=0; k<monthprojectlist.size(); k++)
			{
				if("edit".equals(oper) &&!invoice.getMonthproject().equals(monthprojectlist.get(k))) 
				{
			%>
				<option value=<%= monthprojectlist.get(k)%>>
					<%=monthprojectlist.get(k)%>
				</option>
			<%
				}
				else if("new".equals(oper))
				{
			%>
				<option value=<%= monthprojectlist.get(k)%>>
					<%=monthprojectlist.get(k)%>
				</option>
			<%
				}
			}
			%>	
			</select>
			<span style="margin-left:-295px;">
			<input type="text" name="project_text" id="project_text" title="Enter or select a project" <%if("edit".equals(oper)){%> value = "<%=invoice.getMonthproject()%>"<%} else {%>value=""<%}%> onfocus="javascript:focusOn(this);" style="width:270px;border:0px; height:17px;" />
			</span>
			<br />
			Invoice/Downpayment:
			<br />
			<select name = "payment_select" id="payment_select" style="width:293px;height:21px; margin:-1px;" onchange="javascript:onchangeSeltoText(this,'payment');" onpropertychange="javascript:onchangeSeltoText(this,'payment');">
			<option value="-1"></option>
			<%for(int i=0;i<paymentlist.size();i++){
				if("new".equals(oper)){%>
			<option value=<%=paymentlist.get(i).get("payment") %><%if(paymentlist.get(i).get("payment")!=null&&paymentlist.get(i).get("payment").equals("Issue Invoice")){%>selected='selected'<%} %>><%if(paymentlist.get(i).get("payment")==null){%><%}else{%><%=paymentlist.get(i).get("payment")%> <%} %></option> 
			<%}else{%>
				<option value=<%= paymentlist.get(i).get("payment")%> <%if(invoice.getPayment()!=null&&paymentlist.get(i).get("payment").equals(invoice.getPayment().trim())){%>selected="selected" <%}%>>
						<%=paymentlist.get(i).get("payment")%>
				</option>		
				<%}
				}%>
			</select>
			<span style="margin-left:-295px;">
			<input type="text" name="payment_text" id="payment_text" title="Enter or select a payment" 
				<%if("edit".equals(oper)&&invoice.getPayment()!=null){%> 
				value = "<%=invoice.getPayment()%>"<%} 
				else {%>value=""<%}%> onfocus="javascript:focusOn(this);" style="width:270px;border:0px; height:17px;" />
			</span>
			
			<p>
			Category:
			<select name="category" id="category" style="width:110px">
			<option value="-1"></option>
			<%
			for (int k=0; k<categorylist.size(); k++)
			{
			%>
				<option value=<%= categorylist.get(k).get("categoryid")%> 
				<%if("edit".equals(oper) &&invoice.getCategory().equals(categorylist.get(k).get("category")))
				{%>
					selected="selected" 
				<%} %>>
					<%=categorylist.get(k).get("category")%>
				</option>
			<%}%>		
			</select>
			&nbsp;&nbsp;
			PO No:
			<span style="color:black">
			<select name="POID" id="PONumber" style="width:110px">
			<option value="-1"></option>
			<%
			for (int i=0; i<polist.size(); i++)
			{
				if(!polist.get(i).get("PONumber").equals(""))
				{
			%>
			<option value=<%= polist.get(i).get("POID")%> 
			<%if("edit".equals(oper) && invoice.getPONum()!=null&&invoice.getPONum().equals(polist.get(i).get("PONumber")))
			{%>
				selected="selected" 
			<%} %>>
				<%=polist.get(i).get("PONumber") %>
			</option>
			<%
				}
			}
			%>	
			</select>
			</span><br />
			</p>
			Client:
			<select name="client" id="client" style="width:125px">
			<option value="-1"></option>
			<%
			for (int i=0; i<clientlist.size(); i++)
			{
			%>
				<option value=<%= clientlist.get(i).get("clientid")%> 
				<%if("edit".equals(oper) && invoice.getClient().equals(clientlist.get(i).get("client")))
				{%>
					selected="selected" 
				<%}%>>
					<%=clientlist.get(i).get("client") %>
				</option>
			<%}%>	
			</select>
			&nbsp;&nbsp;
			WBS:
			<select name="wbs" id="wbs" style="width:123px; text-align:center">
			<option value="-1"></option>
			<%
			for (int i=0; i<wbslist.size(); i++){
			%>
				<option value=<%= wbslist.get(i).get("wbsid")%> 
				<%if("edit".equals(oper)&&invoice.getWBSNumber()!=null && invoice.getWBSNumber().equals(wbslist.get(i).get("wbs")))
				{%>
					selected="selected" 
				<%}%>>
					<%=wbslist.get(i).get("wbs")%>
				</option>
			<%}%>	
			</select><br />
			<p>
			Cost($):
			<input type="text" id="cost" name="cost" title="Please only enter the Arabic numeral!" 
			<% if("edit".equals(oper)) {%>value="<%=format.format(Double.parseDouble(invoice.getCost()))%>" <%}%> value="0"  
			<% if("new".equals(oper)) {%>onblur="if(this.value=='0') {this.value=defaultValue;}" 
			onfocus="if(this.value==defaultValue) {this.value='0';}"<%} %> 
			style="width:90px; text-align:center" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"/>
			&nbsp;
			Invoice No:
			<input type="text" id="Invoicenumber" name="Invoicenumber" <%if("edit".equals(oper)){%> 
			 value="<%=invoice.getInvoiceNumber() %>"<%} %> class="input_text4" style="width:114px;"/> <br />			
			</p> 
			Comment:
			<textarea name="comment" rows="3" cols="36" style="border:1px #ccc solid;margin-left:30px;width:207px;"><%if("edit".equals(oper)&&!(invoice.getComment()==null)){%><%=invoice.getComment().trim()%><%} %></textarea>
			<p style="margin-left:110px;">
			<input type="button" class="btnBack" onclick="javascript: back();" />
			<input type="button" class="btnSave2" <%if("edit".equals(oper)&&null==oper_new){%> onclick="javascript: modifyInvoice();"
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
	var txt = $(obj).find("option:selected").text().replace(/(^\s*)|(\s*$)/g, "");
	if(str=='project')
	{
		$("#project_text").val(txt);
	}
	if(str=='payment')
	{
		$("#payment_text").val(txt);
	}
}

function saveInvoice()
{	
	if($("#year").val()==-1)
	{
		alert("Year should not be blank!");
		$("#year").focus();
		return;
	}
	if($("#month").val()==-1)
	{
		alert("Month should not be blank!");
		$("#month").focus()
		return;
	}

	var text=$("#project_text").val();
	if(text==''|| text=='Enter or select the project')
	{
		alert("Project should not be blank!");
		$("#project_text").focus();
		return;
	}
	
	if($("#category").val()==-1)
	{
		alert("Category should not be blank!");
		$("#category").focus()
		return;
	}
	
	if($("#PONumber").val()==-1)
	{
		alert("PONumber should not be blank!");
		$("#PONumber").focus()
		return;
	}

	if($("#client").val()==-1)
	{
		alert("Client should not be blank!");
		$("#client").focus()
		return;
	}

	if($("#cost").val().replace(/[ ]/g,"")=="")
	{
		$("#cost").val("0");
	}
//	if($("#wbs").val()==-1)
//	{
//		alert("Wbs should not be blank!");
//		$("#wbs").focus()
//		return;
//	}

//	var Invoicenumber = $("#Invoicenumber").val();
//	if(Invoicenumber=='')
//	{
//		alert("Invoice Number should not be blank!");
//		$("#Invoicenumber").focus();
//		return;
//	}
	
	var form = document.forms[0];
	form.submit();
}
function modifyInvoice()
{
	if($("#year").val()==-1)
	{
		alert("Year should not be blank!");
		$("#year").focus();
		return;
	}
	if($("#month").val()==-1)
	{
		alert("Month should not be blank!");
		$("#month").focus()
		return;
	}

	var text=$("#project_text").val();
	if(text=='' || text=='Enter or select a project')
	{
		
		alert("Project should not be blank!");
		$("#project_text").focus();
		return;
	}

	if($("#category").val()==-1)
	{
		alert("Category should not be blank!");
		$("#category").focus()
		return;
	}
	
	if($("#PONumber").val()==-1)
	{
		alert("PONumber should not be blank!");
		$("#PONumber").focus()
		return;
	}

	if($("#client").val()==-1)
	{
		alert("Client should not be blank!");
		$("#client").focus()
		return;
	}
	if($("#cost").val().replace(/[ ]/g,"")=="")
	{
		$("#cost").val("0");
	}
//	if($("#wbs").val()==-1)
//	{
//		alert("Wbs should not be blank!");
//		$("#wbs").focus()
//		return;
//	}

	//var Invoicenumber = $("#Invoicenumber").val();
	//if(Invoicenumber=='')
	//{
	//	alert("Invoice Number should not be blank!");
	//	$("#Invoicenumber").focus();
	//	return;
	//}
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=editInvoice&operPage=Invoice_Eidt_submit";
	form.submit();
}
function back()
{
	var form = document.forms[0];
//	form.action = "InvoiceAction.do?operate=searchbyMonthlyEx&operPage=Invoice_List";
//修改了action语句 FWJ 2013-04-24
	form.action = "InvoiceAction.do?operate=searchWithFilter&operPage=Invoice_List&back=invoice_edit";
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