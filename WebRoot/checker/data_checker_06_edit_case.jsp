<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*"
	contentType="text/html; charset=GBK"%>
<%@ include file="../include/MyInforHead.jsp"%>
<% 
	@SuppressWarnings("unchecked")
 	List<Map> Productlist = (ArrayList<Map>) request.getSession().getAttribute("Productlist_hide");
 	@SuppressWarnings("unchecked")
	List<Map> Componentlist = (ArrayList<Map>) request.getSession().getAttribute("Componentlist_hide");
	@SuppressWarnings("unchecked")
	List<Map> Milestonelist = (ArrayList<Map>) request.getSession().getAttribute("Milestonelist_hide");
	DefaultCaseDefect cdc =(DefaultCaseDefect)request.getSession().getAttribute("dcd");
	CaseDefect cd = (CaseDefect) request.getAttribute("cd");
	//FWJ 2013-05-10
	String oper=(String)request.getAttribute("oper");

	System.out.println(oper);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base target="_self" />
    <title><%if(oper.equals("new")){%>Add a new Case Defect<%}else{%>Edit a Case Defect<%}%></title>
    <link href="css/style_new.css" rel="stylesheet" type="text/css" />
	<script src="js/datapicker/jquery-1.5.1.js"></script> 
	<script src="js/datapicker/jquery.ui.core.js"></script> 
	<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
	<link rel="stylesheet" href="js/datapicker/demos.css" /> 
	<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" /> 
	<script type="text/JavaScript">
	$(function() {
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
		});				
	</script>
  </head>
  
  <body>
  <div class="editPage">
  <form action="" method="post">
  <h3 style="color:gray;"><%if(oper.equals("new")){%>Add<%}else{%>Edit<%}%> Case and Defect        
  <%if(oper.equals("new")) {%><input type="button" value="Set Default" class="btnStyle" onclick="setdefault()"/><%} %></h3>
  <%if(oper.equals("edit")) {%>ID: <label id="id" for="id"><%=cd.getId() %> </label><%} %><br />
  	Product: <select id="productid" name="productid" style="width:136px; border:1px #ccc solid; margin-left:30px;">
  	<option value=-1></option>
 	
  	<% for(int i=0;i<Productlist.size();i++){ 
  		if(oper.equals("edit")){
  	%>
  	<option value='<%=Productlist.get(i).get("productid") %>' <%if(Productlist.get(i).get("product").equals(cd.getProduct())){ %>selected="selected"<%}%>><%=Productlist.get(i).get("product")%>
  	</option>
  	<% } else{%>
  	<option value='<%=Productlist.get(i).get("productid") %>' <%if(Integer.parseInt(Productlist.get(i).get("productid").toString())==cdc.getProductId()){ %>selected="selected"<%}%>><%=Productlist.get(i).get("product")%>
  	</option>	
  	<%}}%>
  	</select>
  	<br />
  	Component: <select id="componentid" name="componentid" style="width:136px; border:1px #ccc solid; margin-left:10px;">
  	<option value=-1></option>
  	<% for(int i=0;i<Componentlist.size();i++){ 
  		if(oper.equals("edit")){
  	%>
	  	<option value='<%=Componentlist.get(i).get("componentid") %>' <%if(Componentlist.get(i).get("componentName").equals(cd.getComponentName())){ %> selected="selected"<%}%>><%=Componentlist.get(i).get("componentName")%>
	  	</option>
	  	<% } else{%>
	  	<option value='<%=Componentlist.get(i).get("componentid") %>' <%if(Integer.parseInt(Componentlist.get(i).get("componentid").toString())==cdc.getComponentId()){ %>selected="selected"<%}%>><%=Componentlist.get(i).get("componentName")%>
	  	</option>	
	  	<%}}%>
  	</select>
  	<br />
  	 Milestone: <select id="milestoneid" name="milestoneid" style="width:136px; border:1px #ccc solid; margin-left:20px;">
  	<option value=-1></option>
  	<% for(int i=0;i<Milestonelist.size();i++){ 
  		if(oper.equals("edit")){
	  	%>
	  	<option value='<%=Milestonelist.get(i).get("milestoneid") %>' <%if(Milestonelist.get(i).get("milestone").equals(cd.getMilestone())){ %>selected="selected"<%}%>><%=Milestonelist.get(i).get("milestone")%>
	  	</option>
	  	<% } else{%>
	  	<option value='<%=Milestonelist.get(i).get("milestoneid") %>' <%if(Integer.parseInt(Milestonelist.get(i).get("milestoneid").toString())==cdc.getMilestoneId()){ %>selected="selected"<%}%>><%=Milestonelist.get(i).get("milestone")%>
	  	</option>	
	  	<%}}%>
  	</select>
  	<br />
  	StartDate: <input type="text" id="sdate" name="sdate" class="input_text2" <%if(oper.equals("edit")){ %>value= '<%=cd.getSDate() %>'<%} else{%>value=''<%} %> readonly="readonly"  style="margin-left:23px;"/><br />
  	EndDate: <input type="text" id="edate" name="edate" class="input_text2" <%if(oper.equals("edit")){ %>value= '<%=cd.getEDate() %>'<%} else{%>value=''<%} %> readonly="readonly"  style="margin-left:28px;"/><br />
  	

   No of TIs: <input type="text" id="cases" name="cases" <%if(oper.equals("edit")){ %>value='<%=cd.getCases() %>'<%} else{%>value=''<%} %> class="input_text2"  style="margin-left:28px;"
  	 maxlength="10" onfocus="this.select();" onkeydown="myKeyDown();" /><br />
  UrgentDefect: <input type="text" id="urgentdefect" name="urgentdefect" <%if(oper.equals("edit")){ %>value='<%=cd.getUrgentdefect() %>' <%} else{%>value=''<%} %> class="input_text2"
    maxlength="10" onfocus="this.select();" onkeydown="myKeyDown();" style="margin-left:3px;"/><br />
     
   HighDefect: <input type="text" id="highdefect" name="highdefect" <%if(oper.equals("edit")){ %>value='<%=cd.getHighdefect() %>'<%} else{%>value=''<%} %> class="input_text2"
    maxlength="10" onfocus="this.select();" onkeydown="myKeyDown();" style="margin-left:15px;" /><br />
     
   MediumDefect: <input type="text" id="normaldefect" name="normaldefect" <%if(oper.equals("edit")){ %>value='<%=cd.getNormaldefect() %>'<%} else{%>value=''<%} %> class="input_text2"
    maxlength="10" onfocus="this.select();" onkeydown="myKeyDown();" style="width:145px;" /><br />
     
   LowDefect: <input type="text" id="lowdefect" name="lowdefect" <%if(oper.equals("edit")){ %>value='<%=cd.getLowdefect() %>'<%} else{%>value=''<%} %> class="input_text2" 
   maxlength="10" onfocus="this.select();" onkeydown="myKeyDown();" style="margin-left:20px;"/><br />
  	<p>
  	<input type="button" class="btnBack" onclick="javascript:backpage();" style="margin-left:20px;"/>
  	<input type="button" class="btnSave2" onclick="javascript:saveCase();"/>
  	
  	</p>
   </form>
   </div>
  
  <script type="text/javascript">
//hanxiaoyu01 2013-02-16 设置默认值
  function setdefault(){
   var dpid=$("#productid").val();
   var dcid=$("#componentid").val();
   var dmid=$("#milestoneid").val();
   $.post("POAction.do",{"operate":"addDefaultCaseAndDefect","dpid":dpid,"dcid":dcid,"dmid":dmid},function(data){
     alert(data);
    });
	}
  //hanxiaoyu01 加入提交前日期不为空的限制 2012-11-14

  function saveCase()
  {
	//添加了三个不为空的判断 FWJ 2013-05-10
    if($("#productid").val()==-1)
	{
		alert("Product should not be blank!");
		$("#productid").focus();
		return;
	}
    if($("#componentid").val()==-1)
	{
		alert("Component should not be blank!");
		$("#componentid").focus();
		return;
	}
    if($("#milestoneid").val()==-1)
	{
		alert("Milestone should not be blank!");
		$("#milestoneid").focus();
		return;
	}
	if($("#cases").val()=='')
	{
		alert("Cases should not be blank!");
		$("#cases").focus();
		return;
	}
	if($("#urgentdefect").val()=='')
	{
		alert("Urgent Defect should not be blank!");
		$("#urgentdefect").focus();
		return;
	}
	if($("#highdefect").val()=='')
	{
		alert("High Defect should not be blank!");
		$("#highdefect").focus();
		return;
	}
	if($("#normaldefect").val()=='')
	{
		alert("Medium Defect should not be blank!");
		$("#normaldefect").focus();
		return;
	}
	if($("#lowdefect").val()=='')
	{
		alert("Low Defect should not be blank!");
		$("#lowdefect").focus();
		return;
	}
	var sDate=document.getElementById("sdate").value;
    var eDate=document.getElementById("edate").value;
    if(sDate==null||sDate=="")
    {
     alert("The Start Date should not be empty!");
     document.getElementById("sdate").focus();
     return;
    }
    else if(eDate==null||eDate==""){
     alert("The End Date should not be empty!");
     document.getElementById("edate").focus();
     return;
    }
    else
    {
     var form = document.forms[0];
     <%if(oper.equals("edit")){%>
     form.action= "POAction.do?operate=saveCaseDefect&operPage=data_checker_06&id="+<%=cd.getId()%>;
     <%}else{%>
     form.action= "POAction.do?operate=addCaseDefect&operPage=data_checker_06";
     <%}%>
	 form.submit();
    }	
  }
  
  function backpage()
  {
 	var form = document.forms[0];
	form.action = "POAction.do?operate=backPage&operPage=data_checker_06";	
	form.submit();
  }
  
 function myKeyDown()
 {
    var    k=window.event.keyCode;   
    if ((k==46)||(k==8)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) 
    {}
    else if(k==13){
         window.event.keyCode = 9;}
    else{
         window.event.returnValue = false;}
} 
  </script>
 </body>
</html>
