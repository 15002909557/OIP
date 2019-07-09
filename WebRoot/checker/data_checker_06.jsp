<%@ page language="java" import="java.util.*,com.beyondsoft.expensesystem.domain.system.*,com.beyondsoft.expensesystem.domain.checker.*"
 contentType="text/html; charset=GBK"%>
 <%@ include file="../include/MyInforHead.jsp"%>

 <% 
 	@SuppressWarnings("unchecked")
 	List<Map> Productlist = (ArrayList<Map>) request.getSession().getAttribute("Productlist");
 	@SuppressWarnings("unchecked")
	List<Map> Componentlist = (ArrayList<Map>) request.getSession().getAttribute("Componentlist");
	@SuppressWarnings("unchecked")
	List<Map> Milestonelist = (ArrayList<Map>) request.getSession().getAttribute("Milestonelist");
	String pid =(String) request.getSession().getAttribute("pid");
	String cid =(String) request.getSession().getAttribute("cid");
	String mid =(String) request.getSession().getAttribute("mid");
	String sdate =(String) request.getSession().getAttribute("sdate");
	String edate =(String) request.getSession().getAttribute("edate");
	//hanxiaoyu01 2013-02-16
	//DefaultCaseDefect dcd=(DefaultCaseDefect)request.getSession().getAttribute("dcd");
	@SuppressWarnings("unchecked")
	List<CaseDefect> list = (ArrayList<CaseDefect>) request
			.getSession().getAttribute("displaylist");
	//总页数
	int TotalPage = Integer.parseInt(request.getSession().getAttribute(
			"TotalPage").toString());
	//当前页码
	int	currentpage = Integer.parseInt(request.getSession()
				.getAttribute("page").toString());
	request.getSession().setAttribute("pagec",currentpage);
	//FWJ 2013-05-13
	String result=(String)request.getAttribute("result");
	if(result!=null)
	{
		System.out.println(result.equals("true"));
	}
 %>
<html>
  <head>
    <title>Case and Defect</title>
<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css">
<script src="js/datapicker/jquery-1.5.1.js"></script> 
<script src="js/datapicker/jquery.ui.core.js"></script> 
<script src="js/datapicker/jquery.ui.datepicker.js"></script> 
<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css"> 
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
		<%if(result!=null){%>
			if((<%=result%>+"")=="true")
			{
				alert("Save case and defect successful!");
			}
			else
				{
					alert("Save case and defect failed!");
				}		
		<%}%>
	});
	

</script>
  </head>
  
  <body>
  <center>
  <form action="" method="post">
  <div style="width:990px; margin-top:20px; margin-bottom:10px; line-height:26px;">
  <div class="inputs" style="padding-top:10px;">
  
  	<select id="productid" name="productid" style="width:160px;">
  	<option value="-1">ProductList</option>
  	<% for(int i=0;i<Productlist.size();i++){ %>
  	<option value='<%=Productlist.get(i).get("productid") %>'
  	<% if(pid!=null&&pid.equals(Productlist.get(i).get("productid"))){ %>
  	selected="selected"
  	<%} %>
  	>
  	<%=Productlist.get(i).get("product") %>
  	</option>
  	<%} %>
  	</select>
  	
  	<select id="componentid" name="componentid" style="width:160px;">
  	<option value="-1">ComponentList</option>
  	<% for(int i=0;i<Componentlist.size();i++){ %>
  	<option value='<%=Componentlist.get(i).get("componentid") %>'
  	<% if(cid!=null&&cid.equals(Componentlist.get(i).get("componentid"))){ %>
  	selected="selected"
  	<%} %>
  	>
  	<%=Componentlist.get(i).get("componentName") %>
  	</option>
  	<%} %>
  	</select>
  	
  	 <select id="milestoneid" name="milestoneid" style="width:160px; margin-right:15px;">
  	<option value="-1">MilestoneList</option>
  	<% for(int i=0;i<Milestonelist.size();i++){ %>
  	<option value='<%=Milestonelist.get(i).get("milestoneid") %>'
  	<%if(mid!=null&&mid.equals(Milestonelist.get(i).get("milestoneid"))){ %>
  	selected="selected"
  	<%} %>
  	>
  	<%=Milestonelist.get(i).get("milestone") %>
  	</option>
  	<%} %>
  	</select>
  	<input type="button" class="btnSearch" onclick="showResult();"/>
  	<input type="button" value="Add a New Record" class="btnStyle1" onclick="javascript:addNewRecord();"/>
  	<br />
   	
   </div>   
   <div class="ss">
  	<br />
   </div>
   </div>  
   <div class="modeTable">
   <table cellpadding="3" cellspacing="1" width="90%" class="tabbg" border="0" align="center" style="min-width:1050px;">
	   <tr class="tr_title2">
		   <td align="center">ID
		   </td>
		   <td align="center">Product
		   </td>
		   <td align="center">Component
		   </td>
		   <td  align="center">Start Date
		   </td>
		   <td  align="center">End Date
		   </td>
		   <td align="center">No of TIs
		   </td>
		   <td align="center">Urgent Defect
		   </td>
		   <td align="center">High Defect
		   </td>
		   <td align="center">Medium Defect
		   </td>
		   <td align="center">Low Defect
		   </td>
		   <td align="center">Milestone
		   </td>
		   <td align="center">Data Filler
		   </td>
		   <td align="center">Create Time
		   </td>
		   <td align="center"></td>  
	   </tr>
	   <%
	   	for (int i = 0; i < list.size(); i++) {
	   %>
	   <tr class="tr_content">
		   <td align="center">
		   <%
		   	out.print(list.get(i).getId());
		   %>
		   </td>
		   <td align="center">
		   <%
		   	out.print(list.get(i).getProduct());
		   %>
		   </td>
		   <td align="center">
			<%
				out.print(list.get(i).getComponentName());
			%>
		   </td>
		   <td align="center">
			<%
				out.print(list.get(i).getSDate());
			%>
		   </td>
		   <td align="center">
			<%
				out.print(list.get(i).getEDate());
			%>
		   </td>
		   <td align="center">
		   	<%
		   		out.print(list.get(i).getCases());
		   	%>
		   </td>
		   <td align="center">
		    <%
		    	out.print(list.get(i).getUrgentdefect());
		    %>
		   </td>
		   <td align="center">
		    <%
		    	out.print(list.get(i).getHighdefect());
		    %>
		   </td>
		   <td align="center">
		    <%
		    	out.print(list.get(i).getNormaldefect());
		    %>
		   </td>
		   <td align="center">
		    <%
		    	out.print(list.get(i).getLowdefect());
		    %>
		   </td>
		   <td align="center">
		   <%
		   	out.print(list.get(i).getMilestone());
		   %>
		   </td>
		   <td align="center">
		   <%
		   	out.print(list.get(i).getCreator());
		   %>
		   </td>
		   <td align="center">
		   <%
		   if(list.get(i).getCreateTime()!=null)
		   {
		   	out.print(list.get(i).getCreateTime());
		   }
		   %>
		   </td>
		   <td align="center">
		   <input type="button" id="edit" name='<%=list.get(i).getId()%>'  
		   onclick="javascript:editPO(this);" class="btnEdit" title="Edit Record" style=" margin-right:10px;"/>
		   <input type="button" id="del" name='<%=list.get(i).getId()%>' 
		   onclick="javascript:deletePO(this);" class="btnRemove" title="Remove Record"/>		   
		   </td>  
	   </tr>
	   <%
	   	}
	   if(list.size()==0)
	   {
	   %>
	   <tr class="tr_content" align="center">
	   <td colspan="14">
	   No Records Found!
	   </td>
	   </tr>
	   <%
	   }
	   %>
  </table>
  </div>
  <!-- 分页 -->
  <%
  if(currentpage==0) currentpage=1;
   %>
  <div class="turnPage">
		<label <% if(currentpage==0||currentpage==1) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %> onclick="javascript: turnpage('1');">First</label>
		<label class="pageCell"  onclick="javascript: turnpage('<%=currentpage-1 %>');">Prev</label>
		<% 
		
		int j = currentpage/5;
		int r = currentpage%5;
		int p =j*5;
		int n =0;
		int m = TotalPage -p;		
		if(TotalPage>5&&m>=5&&r>0){ p =j*5+1; n = 5;}
		else if(TotalPage>5&&r==0){ p =j*5-4; n = 5;}
	//	else if(TotalPage>5&&m>=5&&r==0){ p =j*5-4; n = 5;}
		else if(TotalPage>5&&m<5&&r>0){ p =j*5+1; n = m;}
		else if(TotalPage<=5) { p=1; n = TotalPage;}
			for(int i=0;i<n;i++){ 
		%>
			<label onclick="javascript: turnpage('<%=(i+p) %>');" <% if(currentpage==(i+p)) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %>><%=(i+p)%></label>
		<%
			} 
		%>
		<label class="pageCell"  onclick="javascript: turnpage('<%=currentpage+1 %>');">Next</label>
		<label <% if(currentpage==TotalPage) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %> onclick="javascript: turnpage('<%=TotalPage %>');" title='total:<%=TotalPage %>'>Last</label>
		<input type="text" class="txtPage" id="txtPN" onkeydown="javascript:myKeyDown();"/><input type="button" class="go" value="Go" onclick="javacript:turnpage($('#txtPN').val());"/>
	</div>		
	
   <hr style="clear:both;"/>
   <input type="button" class="btnBack" onclick="returnback();">
  </form>
  </center>
  <script type="text/javascript">
  function addNewRecord()
  {
	  var form = document.forms[0];
		form.action = "POAction.do?operate=addNewRecord&operPage=data_checker_06_edit_case";
		form.submit();
  }
  
  
  function showResult()
  {
 	
  	var form = document.forms[0];
	form.action = "POAction.do?operate=searchCaseDefectCD&operPage=data_checker_06";
	form.submit();
  }
  
  function returnback()
  {
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=backPage&operPage=data_checker_01";
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

function turnpage(page)
{
	if(page=='')
	{
		alert("please input or select a Page Number!");
		$("#txtPage").focus();		
		return false;
	}
	var total = '<%=TotalPage%>';
	if(page<=0){return false;};
	if(page-total>0){return false;};
	var form = document.forms[0];
	form.action = "POAction.do?operate=turnPageJSP&operPage=data_checker_06&page="+page;	
	form.submit();
}

function deletePO(obj){
	var pid = obj.name; 
	var form = document.forms[0];
	if (!confirm("Sure to delete this record?"))
	{
    	window.event.returnValue = false;
    }
    else
    {
		form.action = "POAction.do?operate=deleteCase&operPage=data_checker_06&id="+pid;
		form.submit();
	}
}

function editPO(obj){
	var pid = obj.name; 
	var form = document.forms[0];
	form.action = "POAction.do?operate=editCase&operPage=data_checker_06_edit_case&id="+pid;
	form.submit();
}

  </script>
</body>
</html>
