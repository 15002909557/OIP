<%@ page language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*" contentType="text/html; charset=GBK"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<ProjectOrder> polist = (ArrayList<ProjectOrder>) request.getSession().getAttribute("displaylist");
	@SuppressWarnings("unchecked")
	List<Map> managerList = (ArrayList<Map>) request.getSession().getAttribute("managerList");
	
	String currentPath = "po_list";
	request.getSession().setAttribute("path",currentPath);
	//添加了session中获取filter FWJ 2013-04-26
	@SuppressWarnings("unchecked")
	List<ProjectOrder> ponumberlist = (ArrayList<ProjectOrder>) request.getSession().getAttribute("polist");
	//System.out.println("ponumberlist.size()="+ponumberlist.size());
	String statefilters = (String)request.getSession().getAttribute("statefilters");
	String pofilters2 = (String)request.getSession().getAttribute("pofilters2");
	String lockstatusfilters = (String)request.getSession().getAttribute("lockstatusfilters");
	String managerfilters = (String)request.getSession().getAttribute("managerfilters");
	//添加了格式化方法 FWJ 2013-05-21
	//NumberFormat format = NumberFormat.getInstance();
	//format.setMinimumFractionDigits(2);
	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	//总页数
	int TotalPage = Integer.parseInt(request.getSession().getAttribute(
			"TotalPage").toString());
	//当前页码
	int	currentpage = Integer.parseInt(request.getSession()
				.getAttribute("page").toString());
	request.getSession().setAttribute("pagec",currentpage);
	System.out.println("currentpage in the jsp="+currentpage);
	if(currentpage>TotalPage){
		currentpage=TotalPage;
	}
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>PO List</title>
    <link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
    <script src="js/datapicker/jquery-1.5.1.js"></script>
    <!-- FWJ on 2013-04-23 -->
	<style>
	ul
	{
		width:130px;
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
	}
	
	.ff
	{
		width:128px;
		text-align:center;
		font-size:12px;
	}
	.uu
	{
		display:none;
		overflow:auto; 
		max-height:140px;
	}
	#ulpo, #ulp
	{
		max-height:120px;
	}
	</style>
    <script type="text/javascript">
    
    var x1=0;
	var y1=0;
	var current ="";
	var filter= "";
	
    function getDropList(obj)
	{
		x1 = $(obj).offset().left;
		y1 = $(obj).offset().top;

		current = obj.id;
		$("#ul"+obj.id).show();
		if($("input[name='"+obj.id+"']").length==0)
		{
			$("#"+obj.id).val("");
		}
	}

	function getAll(str)
	{
		if(str=="st")
		{
			filter = "PO State Filter";
			return "All PO States";
		}
		else if(str=="po2")
		{
			filter = "PO Number Filter";
			return "All PO Numbers";
		}
		else if(str=="ls")
		{
			filter = "Lock/Unlock Filter";
			return "All Lock/Unlock";
		}
		else if(str=="mg")
		{
			filter = "PO Manager Filter";
			return "All PO Managers";
		}
	}
	function checkBox(obj,str)
	{
		var vv = $("#"+str).val();
		vv = vv.replace(getAll(str),"");
		var va = $("#"+obj.id+"v").text();	
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

	function searchWithFilter()
	{
		var currentpage=<%=currentpage%>;
		var form = document.forms[0];
		form.target = "";
		form.action = "POAction.do?operate=searchWithFilter&operPage=po_list&pagefrom=po_list&currentpage="+currentpage;
		form.submit();
	}

	$(document).mousemove(function(e)
			{
				if(e.pageX<x1||e.pageX>x1+135||e.pageY<y1||e.pageY>y1+165)
				{
					$("#ul"+current).hide();
				}
			});
	
    $(function()
    {
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
		if($("#allpo2").attr("checked")==true)
		{
			$(".boxpo2").attr("checked",true);
		}
		if($("#allst").attr("checked")==true)
		{
			$(".boxst").attr("checked",true);
		}
		if($("#allls").attr("checked")==true)
		{
			$(".boxls").attr("checked",true);
		}
		if($("#allmg").attr("checked")==true)
		{
			$(".boxmg").attr("checked",true);
		}
    }
    );
    </script>
  </head>
  
  <body>
  <center>
  <form action="" method="post">
  <input type="hidden" id="mlist" name="mlist" value='<%=managerList.size()%>'/>
  <div style="width:1010px; line-height:23px; margin-top:20px;">
  
  <div class="flt" >Filter:</div>
    <div class="drop" align="left" >
		<input type="text" name="pofilters2" id="po2" class="ff" 
		<% if(pofilters2!=null) {%> value="<%=pofilters2%>" title="<%=pofilters2 %>" <%} else{ %>
		value="PO Number Filter" title="PO Number Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
		<ul id="ulpo2" class="uu" style="display:none;" >
		<li><input type="checkbox" name="po2" id="allpo2" class="allpo2" onclick="javascript:checkBox(this,'po2');"
		<% if(pofilters2!=null&&pofilters2.indexOf("All PO Numbers")>=0){%> checked="checked"<%} %> />
		<label id="allpo2v">All PO Numbers</label></li>
		<%
			for (int i = 1; i < ponumberlist.size(); i++) 
			{
		%>
		<li><input type="checkbox" name="cpo2" id="po2<%=i %>" class="boxpo2" onclick="javascript:checkBox(this,'po2');" 
		<% if(pofilters2!=null&&pofilters2.indexOf(ponumberlist.get(i).getPONumber().trim())>=0) {%> checked="checked"<%} %>/>
		<label id="po2<%=i %>v"><%=ponumberlist.get(i).getPONumber()%></label></li>
		<%
			}
		%>
		</ul>
	 </div>
  
  	<div class="drop" align="left" >
		<input type="text" name="statefilters" id="st" class="ff" 
		<% if(statefilters!=null) {%> value="<%=statefilters%>" title="<%=statefilters %>" <%} else{ %>
		value="PO State Filter" title="PO State Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
		<ul id="ulst" class="uu" style="display:none;" >
		<li><input type="checkbox" name="st" id="allst" class="allst" onclick="javascript:checkBox(this,'st');"
		<% if(statefilters!=null&&statefilters.indexOf("All PO States")>=0) {%> checked="checked"<%} %> />
		<label id="allstv">All PO States</label></li>

		<li><input type="checkbox" name="cst" id="st0" class="boxst" onclick="javascript:checkBox(this,'st');" 
		<% if(statefilters!=null&&statefilters.indexOf("Not Start")>=0) {
		%> checked="checked"<%} %>/>
		<label id="st0v">Not Start</label></li>
		<li><input type="checkbox" name="cst" id="st1" class="boxst" onclick="javascript:checkBox(this,'st');" 
		<% if(statefilters!=null&&statefilters.indexOf("Open")>=0) {
		%> checked="checked"<%} %>/>
		<label id="st1v">Open</label></li>
		<li><input type="checkbox" name="cst" id="st2" class="boxst" onclick="javascript:checkBox(this,'st');" 
		<% if(statefilters!=null&&statefilters.indexOf("Close")>=0) {
		%> checked="checked"<%} %>/>
		<label id="st2v">Close</label></li>
		</ul>
	 </div>
	 
	 <div class="drop" align="left" >
		<input type="text" name="lockstatusfilters" id="ls" class="ff" 
		<% if(lockstatusfilters!=null) {%> value="<%=lockstatusfilters%>" title="<%=lockstatusfilters %>" <%} else{ %>
		value="Lock/Unlock Filter" title="Lock/Unlock Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
		<ul id="ulls" class="uu" style="display:none;" >
		<li><input type="checkbox" name="ls" id="allls" class="allls" onclick="javascript:checkBox(this,'ls');"
		<% if(lockstatusfilters!=null&&lockstatusfilters.indexOf("All Lock/Unlock")>=0) {%> checked="checked"<%} %> />
		<label id="alllsv">All Lock/Unlock</label></li>

		<li><input type="checkbox" name="cls" id="ls0" class="boxls" onclick="javascript:checkBox(this,'ls');" 
		<% if(lockstatusfilters!=null&&lockstatusfilters.indexOf("Lock,")>=0) {
		%> checked="checked"<%} %>/>
		<label id="ls0v">Lock</label></li>
		<li><input type="checkbox" name="cls" id="ls1" class="boxls" onclick="javascript:checkBox(this,'ls');" 
		<% if(lockstatusfilters!=null&&lockstatusfilters.indexOf("Unlock,")>=0) {
		%> checked="checked"<%} %>/>
		<label id="ls1v">Unlock</label></li>
		</ul>
	 </div>
	 
	 <div class="drop" align="left" >
		<input type="text" name="managerfilters" id="mg" class="ff" 
		<% if(managerfilters!=null) {%> value="<%=managerfilters%>" title="<%=managerfilters %>" <%} else{ %>
		value="PO Manager Filter" title="PO Manager Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
		<ul id="ulmg" class="uu" style="display:none;" >
		<li><input type="checkbox" name="mg" id="allmg" class="allmg" onclick="javascript:checkBox(this,'mg');"
		<% if(managerfilters!=null&&managerfilters.indexOf("All PO Managers")>=0){%> checked="checked"<%} %> />
		<label id="allmgv">All PO Managers</label></li>
		<%
			for (int i = 0; i < managerList.size(); i++) 
			{
		%>
		<li><input type="checkbox" name="cmg" id="mg<%=i %>" class="boxmg" onclick="javascript:checkBox(this,'mg');" 
		<% if(managerfilters!=null&&managerfilters.indexOf(managerList.get(i).get("POManager").toString().trim())>=0) {%> checked="checked"<%} %>/>
		<label id="mg<%=i %>v"><%=managerList.get(i).get("POManager")%></label></li>
		<%
			}
		%>
		</ul>
	 </div>
 </div>

  <!-- 将New PO 和 MonthlyExpense按钮移动到表格的下方。 FWJ on 2013-04-16 -->
	<br />
  <div>
  <table id="list" border="0" cellpadding="3" cellspacing="1" width="1010" class="tab_bg" style="clear:left; min-width:1000px;" align="center">
  	<tr class="tr_title2">
  		<td width="5%">ID</td>
  		<td width="10%">PO Number</td>
  		<td width="10%">PO Issued Date</td>
  		<td width="10%">PO Budget</td>
  		<td width="10%">PO Used</td>
  		<td width="10%">PO Remaining</td>
  		<td width="10%">PO State</td>
  		<td width="10%">PO End Date</td>
  		<td width="8%">Lock/Unlock</td>
  		<td width="8%">PO Manager</td>
  		<td width="5%"></td>
  	</tr>
  	<!-- 数据库中的state中第一项-1值的默认state为Not Start -->
  	<% if(polist.size()==0){%>
  	<tr class="tr_content">
  	<td colspan="11" align="center"> NOT FOUND!</td>
  	</tr>
  	<%} %>
  	<% 
  	for(int i=0;i<polist.size();i++){
  	if(!polist.get(i).getPONumber().equals("")){
  	%>
  	<tr align="center" onclick="javascript:getLaborID(this,'<%=polist.get(i).getPOID() %>', '<%=polist.get(i).getPONumber() %>','<%=i %>');" class="tr_content">
  		<td>
  		<input type="radio" name="check" id="check<%=i %>"/>
  		<%out.print(polist.get(i).getPOID()); %>
  		</td>
  		<td><% out.print(polist.get(i).getPONumber()); %></td>
  		<td><% out.print(polist.get(i).getPOStartDate());%></td>
  		<td align="left"><% out.print("$"+format.format(polist.get(i).getPOAmount()));%></td>
  		<td align="left"><% out.print("$"+format.format(polist.get(i).getPoUsed()));%></td>
  		<td align="left"><%if(polist.get(i).getPoBalance()<0) { out.print("-$"+(format.format(polist.get(i).getPoBalance())).toString().replace("-",""));}
  		else{out.print("$"+format.format(polist.get(i).getPoBalance()));}%></td>
  		<td><% out.print(polist.get(i).getPOStatus());%></td>
  		<td><% out.print(polist.get(i).getPOEndDate()); %></td>
  		<td><% out.print(polist.get(i).getLock()); %></td>
  		<td><% out.print(polist.get(i).getPOManager()); %></td>
  		<td>
  			<input type="button" class="btnEdit" onclick="javascript:editPO('<%=polist.get(i).getPOID() %>');" title="Edit PO" style=" margin-right:10px;"/>	
  			<input type="button" class="btnRemove" onclick="javascript:deletePO('<%=polist.get(i).getPOID() %>');" title="Remove PO" />

  		</td>
  	</tr>
  	<%} }%>
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
		else if(TotalPage>5&&m>=5&&r==0){ p =j*5-4; n = 5;}
		else if(TotalPage>5&&m<5&&r>0){ p =j*5+1; n = m;}
		else if(TotalPage>5&&m<5&&r==0){ p =j*5; n = m+1;}//添加了一项如果翻页为5时的判断。FWJ on 2013-05-02
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
	 
	  <br />
	   <!-- 将New PO 和 MonthlyExpense按钮移动到表格的下方,并添加了新的Copy to a New PO的按钮。 FWJ on 2013-04-16 -->
  <div>
 	<input type="button" value="Add a New PO" class="btnStyle" onclick="javascript:addNewPO();"/>
 	<input type="button" value="Copy to a New PO" class="btnStyle" onclick="javascript:copyPO()"/>
 	<input type="button" value="Monthly Expense" class="btnStyle" onclick="javascript: gotoInvoiceList();"/>
 	<input type="button" value="Project Management" class="btnStyle" onclick="javascript: gotoMonthlyprojectList();"/>
 	<input type="button" value="PO Report" <%if(polist.size()==0){ %>disabled="disabled"<%} %>
				class="btnStyle1" style="margin-left:10px;" onclick="javascript:POReport();" />
  </div>		
  <hr />
  <input type="button" class="btnBack" onclick="javascript:returnback();"/>
  </form>
  </center>
  
<script type="text/javascript">
var currentRow;
var id='';
var ponum = '';
 function getLaborID(obj,nid,num,index)
 {
  if(currentRow)
 	{
 		currentRow.style.background='#ffffff';
 	}
 	currentRow=obj;
 	currentRow.style.background='#ffe6d1';
 	//currentRow.className = "tr2";
 	id = nid;//取得被选中labor行中的nid
 	ponum = num;
 	//alert(id);
 	$("#check"+index).attr("checked","checked");
 
 }
function deletePO(pid)
{
	if (!confirm("Sure to delete this PO?"))
 	{
    	window.event.returnValue = false;
    }
   	else
   	{
   	    //删除PO前先作判断，如果这个PO在Project中被引用，则不能删除 hanxiaoyu01 2013-01-04
   	    //增加了对po在monthlyexpense中被使用的判断。
   	    $.post("POAction.do",{"operate":"checkPO","poid":pid},function(data){
   	     if(data=="3")
   	   	 {
   	       alert("This PO  has been used in the Project and Monthly Expense, you can not delete it!");
   	     }else if(data=="2")
   	   	     {
   	    	 	alert("This PO  has been used in the Project, you can not delete it!");
   	   	     }else if(data=="1")
   	   	   	     {
   	    			alert("This PO  has been used in the Monthly Expense, you can not delete it!");
	   	   	     }else{
			   	      var form = document.forms[0];
			 		  form.action="POAction.do?operate=deletePO&operPage=po_list&poid="+pid;
			 		  form.submit();  
		   	     }
   	    });
 	}
}

function editPO(pid)
{
	var form = document.forms[0];
 	form.action="POAction.do?operate=toEditPO&operPage=po_edit&poid="+pid;
 	form.submit();
}
//添加了导出po的功能
function POReport()
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=poReport&curPage=po_list";
	form.submit();
}

function addNewPO()
{
 	var form = document.forms[0];
 	form.action="POAction.do?operate=toAddPO&operPage=po_add";
 	form.submit();
}

//新添加的copyPO的功能 FWJ 2013-04-16
function copyPO()
{
	if(''==id)
	{
		alert("Please select a PO first!");
		return false;
	}
	var poid = id;
	//alert(id);
	var form = document.forms[0];
	form.action= "POAction.do?operate=toAddPO&oper=copy&operPage=po_add&poid="+poid;
	form.submit();
	
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
	form.action = "POAction.do?operate=turnPOPageJSP&operPage=po_list&page="+page;
	form.submit();

}

function returnback()
{
	var form = document.forms[0];
//	form.action = "POAction.do?operate=searchPO&operPage=po_assignment";
// 返回到data_checker_01，FWJ on 2013-04-15
	form.action = "DataCheckerAction.do?operate=backPage&operPage=data_checker_01";
	form.submit();
}

/*
function checkAll(a)
{	
	var obj = document.getElementsByName(a);
	var str = '';
	for(var i=0;i<obj.length;i++)
	{
		obj[i].checked=event.srcElement.checked;
	}
}


function getReq()
{	
	if(checkIteam()==false)
	{
		return false;
	}
	var form = document.forms[0];
	form.action = "POAction.do?operate=search&operPage=po_list";
	form.submit(); 


}
function checkB(obj,a)
{
	if(!obj.checked)
		document.getElementById(a).checked = false;
}

function checkIteam()
{
	var status = document.getElementsByName("status");
	var str = '';
	for(var i=0;i<status.length;i++)
	{
		if(status[i].checked)
		{
			str = status[i].value;
		}
	}
	if(str=='')
	{
		alert("please select a status!");
		document.getElementById("status").focus();
		return false;
	}
	
	var lock = document.getElementsByName("lock");
	var ltr = '';
	for(var i=0;i<lock.length;i++)
	{
		if(lock[i].checked)
		{
			ltr = lock[i].value;
		}
	}
	if(ltr=='')
	{
		alert("please select a lock status!");
		document.getElementById("lock").focus();
		return false;
	}
	
	var manager = document.getElementsByName("manager");
	var mtr = '';
	for(var i=0;i<manager.length;i++)
	{
		if(manager[i].checked){
			mtr= manager[i].value;
			}
	}
	if(mtr=='')
	{
		alert("please select a manager!");
		document.getElementById("manager").focus();
		return false;
	}
}
*/
function gotoInvoiceList()
{
//	if(''==id)
//	{
//		alert("Please select a PO first!");
//		return false;
//	}
	var poid = id;
	var poname = ponum;
	
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=searchbyMonthlyEx&operPage=Invoice_List&parentpage=polist&POID="+poid+"&PONAME="+poname;
	form.submit();
}
//FWJ 2013-06-13
function gotoMonthlyprojectList()
{
//	if(''==id)
//	{
//		alert("Please select a PO first!");
//		return false;
//	}
	var poid = id;
	var poname = ponum;
	
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=searchbyMonthlyproject&operPage=monthlyproject_List";
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
