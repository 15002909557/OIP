<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	//polist
	@SuppressWarnings("unchecked")
	List<Map> polist = (List<Map>) request.getSession().getAttribute("polist");
//增加了在session中获取总的list，为WBS等筛选条件准备 FWJ 2013-05-06
	@SuppressWarnings("unchecked")
	List<Invoice> totallist = (List<Invoice>) request.getSession().getAttribute("melist");
	//expenselist
	@SuppressWarnings("unchecked")
	List<Invoice> expenselist = (List<Invoice>) request.getSession().getAttribute("expenselist");

	@SuppressWarnings("unchecked")
	List<Map> yearlist = (List<Map>) request.getSession().getAttribute("yearlist");
	
	@SuppressWarnings("unchecked")
	List<Map> monthlist = (List<Map>) request.getSession().getAttribute("monthlist");
	//新增了对Category、Client、WBS的值的获取 FWJ on 2013-04-19
//	@SuppressWarnings("unchecked")
//	List<Map> categorylist = (List<Map>) request.getSession().getAttribute("categorylist");
//	@SuppressWarnings("unchecked")
//	List<Map> clientlist = (List<Map>) request.getSession().getAttribute("clientlist");
//	@SuppressWarnings("unchecked")
//	List<Map> wbslist = (List<Map>) request.getSession().getAttribute("wbslist");

	//updated by dancy 20130529
	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	@SuppressWarnings("unchecked")
	List categoryselect = (List) request.getSession().getAttribute("categoryselect");
	@SuppressWarnings("unchecked")
	List clientselect = (List) request.getSession().getAttribute("clientselect");
	@SuppressWarnings("unchecked")
	List wbsselect = (List) request.getSession().getAttribute("wbsselect");
	
	//获取session里面的值
	String poid = (String) request.getSession().getAttribute("poid");
	String pofilters = (String)request.getSession().getAttribute("pofilters");
	System.out.println("pofilters in the invoice_list ="+pofilters);
	String yearfilters = (String)request.getSession().getAttribute("yearfilters");
	String monthfilters = (String)request.getSession().getAttribute("monthfilters");
	String categoryfilters = (String)request.getSession().getAttribute("categoryfilters");
	String clientfilters = (String)request.getSession().getAttribute("clientfilters");
	String wbsfilters = (String)request.getSession().getAttribute("wbsfilters");
	
	//得到父页面名字，判断是由哪个页面过来，用来判断back时 返回哪个页面
	String parentpage = (String)request.getSession().getAttribute("parentpage");
	System.out.println("parentpage="+parentpage);
	
	//add turn page by dancy 2013/04/15
	//总页数
	int TotalPage = Integer.parseInt(request.getSession().getAttribute(
			"TotalPage").toString());

	//当前页码
	int	currentpage = Integer.parseInt(request.getSession().getAttribute("page").toString());
	request.getSession().setAttribute("pagec",currentpage);
	
	if(currentpage>TotalPage){
		currentpage=TotalPage;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!-- Modified to Invoice List to Monthly Expense by FWJ on 2013-03-13-->
	<title>Monthly Expense</title>
	<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
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

	//添加的Filter的getDropList方法
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
		if(str=="po1")
		{
			filter = "PO Number Filter";
			return "All PO Number";
		}
		else if(str=="yr")
		{
			filter = "Year Filter";
			return "All Year";
		}
		else if(str=="mt")
		{
			filter = "Month Filter";
			return "All Month";
		}
		else if(str=="cy")
		{
			filter = "Category Filter";
			return "All Category";
		}
		else if(str=="ct")
		{
			filter = "Client Filter";
			return "All Client";
		}
		else if(str=="wbs")
		{
			filter = "WBS Filter";
			return "All WBS";
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
		var form = document.forms[0];
		form.target = "";
		form.action = "InvoiceAction.do?operate=searchWithFilter&operPage=Invoice_List";
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
		if($("#allpo1").attr("checked")==true)
		{
			$(".boxpo1").attr("checked",true);
		}
		if($("#allyr").attr("checked")==true)
		{
			$(".boxyr").attr("checked",true);
		}
		if($("#allmt").attr("checked")==true)
		{
			$(".boxmt").attr("checked",true);
		}
		if($("#allcy").attr("checked")==true)
		{
			$(".boxcy").attr("checked",true);
		}
		if($("#allct").attr("checked")==true)
		{
			$(".boxct").attr("checked",true);
		}
		if($("#allwbs").attr("checked")==true)
		{
			$(".boxwbs").attr("checked",true);
		}
    });

    </script>
</head>
	<body style="min-width:1010px">
	<center>
	<div class="invoice_main">	
		<form action="" method="post">
<!-- 对filter的强化 -->
		<div class="flt">Filter:</div> 
		
	 	<div class="drop" align="left" >
				<input type="text" name="yearfilters" id="yr" class="ff" 
				<% if(yearfilters!=null) {%> value="<%=yearfilters%>" title="<%=yearfilters %>" <%} else{ %>
				value="Year Filter" title="Year Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulyr" class="uu" style="display:none;" >
				<li><input type="checkbox" name="yr" id="allyr" class="allyr" onclick="javascript:checkBox(this,'yr');"
				<% if(yearfilters!=null&&yearfilters.indexOf("All Year")>=0) {%> checked="checked"<%} %> />
				<label id="allyrv">All Year</label></li>
				<%
					for (int i = 0; i < yearlist.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cyr" id="yr<%=i %>" class="boxyr" onclick="javascript:checkBox(this,'yr');" 
				<% if(yearfilters!=null&&yearfilters.indexOf(yearlist.get(i).get("year")+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="yr<%=i %>v"><%=yearlist.get(i).get("year")%></label></li>
				<%
					}
				%>
				</ul>
	 	</div>
		<div class="drop" align="left" >
				<input type="text" name="monthfilters" id="mt" class="ff" 
				<% if(monthfilters!=null) {%> value="<%=monthfilters%>" title="<%=monthfilters %>" <%} else{ %>
				value="Month Filter" title="Month Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulmt" class="uu" style="display:none;" >
				<li><input type="checkbox" name="mt" id="allmt" class="allmt" onclick="javascript:checkBox(this,'mt');"
				<% if(monthfilters!=null&&monthfilters.indexOf("All Month")>=0) {%> checked="checked"<%} %> />
				<label id="allmtv">All Month</label></li>
				<%
					for (int i = 0; i < monthlist.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cmt" id="mt<%=i %>" class="boxmt" onclick="javascript:checkBox(this,'mt');" 
				<% if(monthfilters!=null&&monthfilters.indexOf(monthlist.get(i).get("month")+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="mt<%=i %>v"><%=monthlist.get(i).get("month")%></label></li>
				<%
					}
				%>
				</ul>
	 	 </div>
	 	 <!-- 将所有id名，name名po改为po1，刷选所有All PO Number的时候不会出现异常，但是不知道为什么 -->
	 	 <div class="drop" align="left" >
				<input type="text" name="pofilters" id="po1" class="ff" 
				<% if(pofilters!=null) {%> value="<%=pofilters%>" title="<%=pofilters %>" <%} else{ %>
				value="PO Number Filter" title="PO Number Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulpo1" class="uu" style="display:none;" >
				<li><input type="checkbox" name="po1" id="allpo1" class="allpo1" onclick="javascript:checkBox(this,'po1');"
				<% if(pofilters!=null&&pofilters.indexOf("All PO Number")>=0){%> checked="checked"<%} %> />
				<label id="allpo1v">All PO Number</label></li>
				<%
					for (int i = 1; i < polist.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cpo1" id="po1<%=i %>" class="boxpo1" onclick="javascript:checkBox(this,'po1');" 
				<% if(pofilters!=null&&pofilters.indexOf(polist.get(i).get("PONumber").toString().trim())>=0) {%> checked="checked"<%} %>/>
				<label id="po1<%=i %>v"><%=polist.get(i).get("PONumber")%></label></li>
				<%
					}
				%>
				</ul>
	 	</div>
	 	
	 	 <div class="drop" align="left" >
				<input type="text" name="categoryfilters" id="cy" class="ff" 
				<% if(categoryfilters!=null) {%> value="<%=categoryfilters%>" title="<%=categoryfilters %>" <%} else{ %>
				value="Category Filter" title="Category Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulcy" class="uu" style="display:none;" >
				<li><input type="checkbox" name="cy" id="allcy" class="allcy" onclick="javascript:checkBox(this,'cy');"
				<% if(categoryfilters!=null&&categoryfilters.indexOf("All Category")>=0) {%> checked="checked"<%} %> />
				<label id="allcyv">All Category</label></li>
				<%
					for (int i = 0; i < categoryselect.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="ccy" id="cy<%=i %>" class="boxcy" onclick="javascript:checkBox(this,'cy');" 
				<% if(categoryfilters!=null&&categoryfilters.indexOf(categoryselect.get(i)+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="cy<%=i %>v"><%=categoryselect.get(i)%></label></li>
				<%
					}
				%>
				</ul>
	 	 </div>
	 	 
	 	 <div class="drop" align="left" >
				<input type="text" name="clientfilters" id="ct" class="ff" 
				<% if(clientfilters!=null) {%> value="<%=clientfilters%>" title="<%=clientfilters %>" <%} else{ %>
				value="Client Filter" title="Client Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulct" class="uu" style="display:none;" >
				<li><input type="checkbox" name="ct" id="allct" class="allct" onclick="javascript:checkBox(this,'ct');"
				<% if(clientfilters!=null&&clientfilters.indexOf("All Client")>=0) {%> checked="checked"<%} %> />
				<label id="allctv">All Client</label></li>
				<%
					for (int i = 0; i < clientselect.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cct" id="ct<%=i %>" class="boxct" onclick="javascript:checkBox(this,'ct');" 
				<% if(clientfilters!=null&&clientfilters.indexOf(clientselect.get(i)+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="ct<%=i %>v"><%=clientselect.get(i)%></label></li>
				<%
					}
				%>
				</ul>
	 	 </div>
	 	 
	 	 <div class="drop" align="left" >
				<input type="text" name="wbsfilters" id="wbs" class="ff" 
				<% if(wbsfilters!=null) {%> value="<%=wbsfilters%>" title="<%=wbsfilters %>" <%} else{ %>
				value="WBS Filter" title="WBS Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulwbs" class="uu" style="display:none;" >
				<li><input type="checkbox" name="wbs" id="allwbs" class="allwbs" onclick="javascript:checkBox(this,'wbs');"
				<% if(wbsfilters!=null&&wbsfilters.indexOf("All WBS")>=0) {%> checked="checked"<%} %> />
				<label id="allctv">All WBS</label></li>
				<%
					for (int i = 0; i < wbsselect.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cwbs" id="wbs<%=i %>" class="boxwbs" onclick="javascript:checkBox(this,'wbs');" 
				<% if(wbsfilters!=null&&wbsfilters.indexOf(wbsselect.get(i)+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="wbs<%=i %>v"><%=wbsselect.get(i)%></label></li>
				<%
					}
				%>
				</ul>
	 	 </div>
	 	
		<div class="modeTable" align="center">
			<table border="0" cellpadding="3" cellspacing="1" style="clear:both" class="tabbg5" align="center">
				<tr class="tr_title2">
				<!-- 修改了部分width参数 -->
					<td width="5%">ID</td>
					<td width="18%">Project</td>
					<td width="5%">Year</td>
					<td width="5%">Month</td>
					<td width="8%">Category</td>
					<td width="8%">PO Number</td>
					<td width="8%">Client</td>
					<td width="8%">Invoice/Downpayment</td>
					<td width="8%">Cost</td>
					<td width="10%">WBS</td>
					<td width="5%">	</td>
				</tr>
				<%
				if (expenselist.size() == 0) {
				System.out.println("no list");
				%>
				<tr class="tr_content" align="center">
				<td colspan="11">No Records Found!</td>
				</tr>
				<%
				} else
				{
					for (int j = 0; j < expenselist.size(); j++) 
					{
				%>

					<tr class="tr_content" align="center" onclick="javascript:getLaborID(this,'<%=expenselist.get(j).getMonthlyexpenseid() %>', '<%=j %>');">
					<td>
					<input type="radio" name="check" id="check<%=j%>" />
					<%=expenselist.get(j).getMonthlyexpenseid()%></td>
					<td align="left"><%=expenselist.get(j).getMonthproject()%></td>
					<td><%=expenselist.get(j).getYear()%></td>
					<td><%=expenselist.get(j).getMonth()%></td>
					<td><%=expenselist.get(j).getCategory()%></td>
					<td><%if(expenselist.get(j).getPONum()!=null&&expenselist.get(j).getPONum()!="null"){out.print(expenselist.get(j).getPONum());}
					else{out.print("");}%></td>
					<td><%=expenselist.get(j).getClient()%></td>
					<td><%if(expenselist.get(j).getPayment()!=null&&expenselist.get(j).getPayment()!="null"){out.print(expenselist.get(j).getPayment());}
					else{out.print("");}%></td>
					<td align="left"><%out.print("$"+format.format((Double.parseDouble(expenselist.get(j).getCost())+0.00)));%></td>
					<td><%if(expenselist.get(j).getWBSNumber()!=null&&expenselist.get(j).getWBSNumber()!="null"){out.print(expenselist.get(j).getWBSNumber());}
					else{out.print("");}%></td>

					<td>						
					<input type="button" name="<%=expenselist.get(j).getMonthlyexpenseid() %>" id="edit"
							class="btnEdit" onclick="javascript:editinvoice(this);" alt="Edit Expense" style="margin-right:10px;"/>
					<input type="button" name="<%=expenselist.get(j).getMonthlyexpenseid()%>" id="<%=expenselist.get(j).getPOid() %>"
							alt="remove Expense" onclick="javascript:removeinvoice(this);" class="btnRemove" />
					</td>
					</tr>
				<%
					}
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
		else if(TotalPage>5&&m>=5&&r==0){ p =j*5-4; n = 5;}
		else if(TotalPage>5&&m<5&&r>0){ p =j*5+1; n = m;}
		else if(TotalPage>5&&m<5&&r==0){ p =j*5; n = m+1;}//添加了一项如果翻页为5时的判断。FWJ on 2013-05-02
		else if(TotalPage<=5) { p=1; n = TotalPage;}
			for(int i=0;i<n;i++)
			{ 
	//		System.out.println("(currentpage,i,j,r,p,m,n)="+"("+currentpage+","+i+","+j+","+r+","+p+","+m+","+n+")");
		%>
			<label onclick="javascript: turnpage('<%=(i+p) %>');" <% if(currentpage==(i+p)) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %>><%=(i+p)%></label>
		<%
			} 
		%>
		<label class="pageCell"  onclick="javascript: turnpage('<%=currentpage+1 %>');">Next</label>
		<label <% if(currentpage==TotalPage) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %> onclick="javascript: turnpage('<%=TotalPage %>');" title='total:<%=TotalPage %>'>Last</label>
		<input type="text" class="txtPage" id="txtPN" onkeydown="javascript:myKeyDown();"/><input type="button" class="go" value="Go" onclick="javacript:turnpage($('#txtPN').val());"/>
	</div>		
		</form>
	
	</div>
	</center>
	<!-- 移动Add New Expense和Copy to a New Expense -->
	<div>
	<input type="button" value="Add a New Expense" class="btnStyle" onclick="javascript:addnewinvoice();" />
	<input type="button" value="Copy to a New Expense" class="btnStyle" onclick="javascript:copyExpense()"/>
	<input type="hidden" name="parentpage" value="<%=parentpage %>" />
	<input type="button" value="Expense Report" <%if(expenselist.size()==0){ %>disabled="disabled"<%} %>
				class="btnStyle1" style="margin-left:10px;" onclick="javascript:expenseReport();" />
	</div>	 

	<hr  />
	<input name="submit1" type="button" id="return" class="btnBack" onclick='javascript:returnback();'/>
<script type="text/javascript">
var id ='';
var currentRow;

function onchangePONumber()
{
	//onChangeYear和onChangeYear都直接调用这个即可，简化代码
	var form = document.forms[0];
	//参数传递尽量不要在表单的url中明码显示
	form.action = "InvoiceAction.do?operate=searchbyMonthlyEx&operPage=Invoice_List";
	form.submit();
}
//添加了table trID等信息的相关方法 FWJ on 2013-04-18
function getLaborID(obj,meid,index)
{
 if(currentRow)
	{
		currentRow.style.background='#ffffff';
	}
	currentRow=obj;
	currentRow.style.background='#ffe6d1';
	//currentRow.className = "tr2";
	id = meid;//取得被选中labor行中的meid
	//alert(id);
	$("#check"+index).attr("checked","checked");

}

function expenseReport()
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=expenseReport&curPage=Invoice_List";
	form.submit();
}

function returnback()
{
	
	var parentpage = '<%=parentpage%>';
	//修改了old poid to poid FWJ 2012-04-23
	var poid = '<%=poid%>'
	var form = document.forms[0];
	if('poassignment'==parentpage)
	{
		form.action = "POAction.do?operate=searchPO&operPage=po_assignment";
	}
	if('polist'==parentpage)
	{
		form.action = "POAction.do?operate=showPOList&operPage=po_list&backpage=Invoice_list";
//		form.action = "POAction.do?operate=searchPO&operPage=po_assignment";
	}
	if('po_edit'==parentpage)
	{
		form.action = "POAction.do?operate=toEditPO&operPage=po_edit&poid="+poid;
	}
	form.submit();
}
function addnewinvoice()
{
    var form=document.forms[0];
    //通过post方式表单传参数，不是直接在url中传递，这是不安全的
	form.action = "InvoiceAction.do?operate=toInsert&operPage=Invoice_Eidt";
    form.submit();
}
//Added on 2013-04-17
function copyExpense()
{
	if(''==id)
	{
		alert("Please select a Expense first!");
		return false;
	}
	var expenseid = id;
	//alert(id);
	var form = document.forms[0];
	form.action= "InvoiceAction.do?operate=toEdit&oper=copy&operPage=Invoice_Eidt&expenseid="+expenseid;
	form.submit();
}

function removeinvoice(obj)
{
	//remove一条记录使用局部刷新(AJAX)会比较高效一些
	var expenseId = obj.name;
	var poid = obj.id;
	if (!confirm("Sure to delete the expense?"))
	{
    	window.event.returnValue = false;
	}else
	{
	    var form=document.forms[0];
	    form.action="InvoiceAction.do?operate=DeleteInvoice&operPage=Invoice_List&Expenseid="+expenseId+"&POID="+poid;
	    form.submit();
	}
}
function editinvoice(expense)
{
	var expenseid = expense.name;

    var form=document.forms[0];
    form.action = "InvoiceAction.do?operate=toEdit&operPage=Invoice_Eidt&expenseid="+expenseid;
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
	form.action = "InvoiceAction.do?operate=turnPageJSP&operPage=Invoice_List&page="+page;	
	form.submit();
}

function myKeyDown()
{
    var    k=window.event.keyCode;   
    if ((k==46)||(k==8)||(k==190)||(k==110)|| (k>=48 && k<=57)||(k>=96 && k<=105)||(k>=37 && k<=40)) 
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
</script>
</body>
</html>