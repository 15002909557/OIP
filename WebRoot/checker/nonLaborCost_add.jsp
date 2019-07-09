<%@ page language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*"
	pageEncoding="UTF-8"%>
<%@ include file='../include/MyInforHead.jsp' %>
<%
	@SuppressWarnings("unchecked")
	Vector<List> v = (Vector<List>) request.getAttribute("List");
	@SuppressWarnings("unchecked")
	List<Groups> glist = v.get(0);
	@SuppressWarnings("unchecked")
	List<Map> locationslist = v.get(1);
	@SuppressWarnings("unchecked")
	List<Map> productlist = v.get(2);
	@SuppressWarnings("unchecked")
	List<Map> componentlist = v.get(3);
	@SuppressWarnings("unchecked")
	List<Map> wbslist = v.get(4);
	//添加了格式化方法 FWJ 2013-05-21
	//NumberFormat format = NumberFormat.getInstance();
	//format.setMinimumFractionDigits(2);
	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	NonLaborCost nonlaborcost = null;
	nonlaborcost = (NonLaborCost) request.getAttribute("nonlaborcost");
	String copy = (String) request.getAttribute("copy");
	if(null != copy)
		System.out.println("if");
	else
		System.out.println("else");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>NonLaborCost Add</title>
		<link href="css/style1.css" rel="stylesheet" type="text/css" />
		<link href="css/style_new.css" rel="stylesheet" type="text/css" />
		<script src="js/datapicker/jquery-1.5.1.js"></script>
		<script src="js/datapicker/jquery.ui.core.js"></script>
		<script src="js/datapicker/jquery.ui.datepicker.js"></script>
		<link rel="stylesheet" href="js/datapicker/demos.css" />
		<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" />
	<script type="text/javascript">
	var defaultpo = "-1";
		$(function() {
			$( "#date" ).datepicker({
				changeMonth: true
				//changeYear: true
				});
			$( "#date" ).datepicker( "option", "dateFormat", "yy/mm/dd" );
			<%
				if(null != nonlaborcost)
				{
					System.out.println("nonlaborcost.getPOID()="+nonlaborcost.getPOID());
			%>
				var nonDate = '<%=nonlaborcost.getNdate().replaceAll("-","/")%>';
				$( "#date" ).val(nonDate);
				defaultpo = "<%=nonlaborcost.getPOID()%>";
				configpo();
			<%
				}
			%>
			setAlertV2("#cost");
		});
		
		
		function configpo(){
		 var obj = document.getElementById("product");
         var product = obj.options[obj.selectedIndex].text;
		 var component=document.getElementById("component").value;
		 if(product!=null&&product!=""&&component!=""&&component!=null){
		  //如果都不为空，则发送ajax请求
		 $.post("./POAction.do?operate=searchbyComponentAndProduct&operPage=data_checker_04_edit_Pro",
			{componentid:component, productname:product},
			function(result)
			{
				clearPOList();
		    	var poselect = document.getElementById("poid");
		    	poselect.options.length=0;
		    	if(defaultpo=='-1')
		    	{
		    		poselect.add(new Option("", defaultpo));
		    	}
		    	for(var i=0; i<result.length; i++)
				{
				    var value=result[i].poid;
					var text = result[i].poname;
					var option = new Option(text, value);
					poselect.add(option);
					if(defaultpo==value)
					{
						poselect.options[i].selected = 'selected';
					}
				}
		    	if(defaultpo!='-1')
		    	{
		    		poselect.add(new Option("", "-1"));
		    	}
		    	defaultpo = "-1";	    	
		  	},
		  	"json"
		  );
		  
		  
		 }
		}
		
 function clearPOList(){
   
    var courseid = document.getElementById("poid");
   
    while(courseid.childNodes.length > 0){
     courseid.removeChild(courseid.childNodes[0]);
    }
}
		
	</script>
	</head>

	<body>
	<div class="editPage" style="width:480px; padding-left:66px; line-height:30px;">
	<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
	<form action="POAction.do?operate=saveNonLabor&operPage=nonLaborCost_add" method="post" target="hide">			
			<h3 style="text-align:center; line-height:70px; width:80%;">NonLabor Cost Record</h3>
				<%
				if(null != nonlaborcost && null == copy)
				{%>
					NonLaborCost ID: 
					<label>
						<%=nonlaborcost.getNid() %>
						<input type="hidden" id="nid" name="nid" value="<%=nonlaborcost.getNid() %>" />
					</label>
					<br />
				<%}%>
					NonLaborCost($): 
					<input type="text" id="cost" name="cost" class="input_text2" style="width:90px;" onkeydown="myKeyDown();" onkeyup="myKeyUp(this);" oninput="setAlertV2(this);" onpropertychange="setAlertV2(this);"
						<%if(null != nonlaborcost){%>value = "<%=format.format(nonlaborcost.getNonLaborCost()) %>" onfocus="this.select();"<%}%>/>
					<br />
					Date: <input type="text" id="date" name="date" readonly="readonly" class="input_text2" style="width:123px;margin-left:40px; margin-right:5px;"/>
					WBS:
					<select id="wbs" name="wbs" style="width:125px; margin-left:22px;">
						<option value=""></option>
						 <%for(int i=0;i<wbslist.size();i++){ %>
						 <option value="<%=wbslist.get(i).get("wbs") %>" title="<%=wbslist.get(i).get("wbs") %>"
						  <%
						   if(null!=nonlaborcost&&wbslist.get(i).get("wbs").equals(nonlaborcost.getWBS())){
						   %>
						   selected="selected"
						   <%} %>
						 >
						 <%=wbslist.get(i).get("wbs") %>
						 </option>
						 <%} %>
					</select>
					<br />
					Product:
						<select id="product" name="product"
							style="width: 129px; margin-left:20px; margin-right:5px;" onchange="configpo()">
							<option value="-1"></option>
							<%
								for (int i = 0; i < productlist.size(); i++) {
							%><option value='<%=productlist.get(i).get("productid")%>'
								<%
								if(null != nonlaborcost && ((productlist.get(i).get("productid").toString()).equals((String.valueOf(nonlaborcost.getProductId())))))
								{
									%>selected="selected"<%
								}
								%>
								><%=productlist.get(i).get("product")%></option>
							<%
								}
							%>
						</select>
					
						Group: 
					<select id="group" name="group" style="width: 125px;margin-left:16px;">
					<option value="-1"></option>
						<%
							for (int i = 0; i < glist.size(); i++) {
							if(glist.get(i).getGid()!=-1){
						%><option value='<%=glist.get(i).getGid()%>'
						<%
							if(null != nonlaborcost && glist.get(i).getGid()==nonlaborcost.getGroupNameID())
							{
								%> selected="selected" <%
							}
						%>
							><%=glist.get(i).getGname()%></option>
						<%
							}
							}
						%>
					</select>					
					<br />					
					Component: 
					<select id="component" name="component"  onchange="configpo()" style="width: 129px;margin-right:5px;">
					 <option value=""></option>
					 <%for(int i=0;i<componentlist.size();i++){ %>
					 <option value="<%=componentlist.get(i).get("componentId") %>"
					  <%
					   if(null!=nonlaborcost&&componentlist.get(i).get("componentId").equals(nonlaborcost.getComponentId())){
					   %>
					   selected="selected"
					   <%} %>
					 >
					 <%=componentlist.get(i).get("component") %>
					 </option>
					 <%} %>
					</select>
					Location: 
						<select id="locale" name="locale"
							style="width: 125px; margin-left:2px;">
							<option value="-1"></option>
							<%
								for (int i = 0; i < locationslist.size(); i++) {
							%><option value='<%=locationslist.get(i).get("locationId")%>'
								<%
								if(null != nonlaborcost && locationslist.get(i).get("locationId").equals(
															String.valueOf(nonlaborcost.getLocaleID())))
								{
									%>selected="selected"<%
								}
								%>
								><%=locationslist.get(i).get("locationName")%></option>
							<%
								}
							%>
						</select>
						<br/>
						PO: 
					<select id="poid" name="poid" style="width:128px;margin-left:49px; margin-right:5px;">
						<option value=""></option>						
					</select>
					Quantity:
					<input type="text"  id="quantity" name="quantity" class="input_text2" style="width:123px;margin-left:2px; margin-right:5px;"
						<%if(null != nonlaborcost){%>value = "<%=nonlaborcost.getQuantity() %>"<%}%>/>
					<br />
					Notes/Product Item:<br/> 
					<textarea id="notes" name="notes" cols="45" rows="2" style="border:1px #cccccc solid;"><%
						if(null != nonlaborcost)
						{
							%><%=nonlaborcost.getNotes()%><%
						}
					%></textarea>
					<br/>
					Comments: <br/>
					<textarea id="comments" name="comments" cols="45" rows="2" style="border:1px #cccccc solid;"><%
						if(null != nonlaborcost)
						{
							%><%=nonlaborcost.getComments()%><%
						}
					%></textarea>
			<p style="text-align:center;">
		<input type="button" id="back" class="btnBack" onclick="returnback();"/>
		<input type="button" id="save" class="btnSave2"
				<%
					if(null == nonlaborcost && null == copy)
						{%>onclick="savenonLaborCost();"<%}
					else if(null != nonlaborcost && null == copy)
						{%>onclick="editnonLaborCost();"<%}
					else if("copy".equals(copy))
						{%>onclick="savenonLaborCost();"<%}
				%>/>
				</p>
	</form>
	</div>
	
<script type="text/javascript"><!--
//hanxiaoyu 2012-11-13 加入提交判断
function savenonLaborCost()
{
    if(pan()==true){
    var form = document.forms[0];
	form.action = "POAction.do?operate=saveNonLabor&operPage=nonLaborCost_add_submit";
	form.target = "hide";
	form.submit();
	}
}

function pan(){
    var date = document.getElementById("date").value;
    var cost=document.getElementById("cost").value;
    var product=document.getElementById("product").value;
    var group=document.getElementById("group").value;
    var component=document.getElementById("component").value;
    var locale=document.getElementById("locale").value;
    var quantity=document.getElementById("quantity").value;
    var comments=document.getElementById("comments").value;
    
    if(cost==null||cost=="")
    {
        alert("The NonLaborCost cannot be empty!");
        document.getElementById("cost").focus();
         return false;
    }
    else if(date==null||date=="")
    {
        alert("The Date cannot be empty!");
        document.getElementById("date").focus();
        return false;
    }
    else if(product=="-1")
    {
     alert("The Product cannot be empty!");
     document.getElementById("product").focus();
      return false;
    }else if(group=="-1"){
     alert("The GroupName cannot be empty!");
     document.getElementById("group").focus();
      return false;
    }else if(component==null||component==""){
     alert("The Component cannot be empty!");
     document.getElementById("component").focus();
      return false;
    }else if(locale=="-1"){
     alert("The Locale cannot be empty!");
     document.getElementById("locale").focus();
      return false;
    }else if(quantity==null||quantity==""){
     alert("The Quantity cannot be empty!");
     document.getElementById("quantity").focus();
      return false;
    }else if(comments==null||comments==""){
     alert("The Comments cannot be empty!");
     document.getElementById("comments").focus()
     return false;
    }else{
     return true;
    }
}
function editnonLaborCost()
{
	var nid = ''; 
	if(pan()==true){
	var form = document.forms[0];
	form.action = "POAction.do?operate=editNonLabor&operPage=nonLaborCost_add_submit";
	form.target = "hide";
	form.submit();
	}
}
function returnback()
{
	var form = document.forms[0];
	form.action = "POAction.do?operate=searchNonLabor&operPage=nonlaborCost_list";
	form.target = "";
	form.submit();
}
 function myKeyDown()
 {
    var    k=window.event.keyCode;   
    if ((k==46)||(k==8)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)||k==110||k==190) 
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
</script>
</body>
</html>
