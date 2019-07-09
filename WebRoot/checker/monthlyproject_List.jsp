<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<Map> polist = (List<Map>) request.getSession().getAttribute("polist");
	@SuppressWarnings("unchecked")
	List<Monthlyproject> showlist = (List<Monthlyproject>) request.getSession().getAttribute("showlist");
	@SuppressWarnings("unchecked")
	List<Monthlyproject> monthprojectlist = (List<Monthlyproject>) request.getSession().getAttribute("monthlyprojectlist");
	String monthprojectfilters = (String)request.getSession().getAttribute("monthprojectfilters");
	@SuppressWarnings("unchecked")
	List<Monthlyproject> locationlist = (List<Monthlyproject>) request.getSession().getAttribute("locationlist");
	String locationfilters = (String)request.getSession().getAttribute("locationfilters");
	@SuppressWarnings("unchecked")
	List<Monthlyproject> bcategorylist = (List<Monthlyproject>) request.getSession().getAttribute("businesscategorylist");
	String bcategoryfilters = (String)request.getSession().getAttribute("bcategoryfilters");

	DecimalFormat format = new DecimalFormat();
	format.applyPattern(",##0.00");
	
	//得到父页面名字，判断是由哪个页面过来，用来判断back时 返回哪个页面
	String parentpage = (String)request.getSession().getAttribute("parentpage");
	System.out.println("parentpage="+parentpage);
	
	//add turn page by dancy 2013/04/15
	//总页数
	int TotalPage = Integer.parseInt(request.getSession().getAttribute(
			"TotalPage").toString());

	//当前页码
	int	currentpage = Integer.parseInt(request.getSession().getAttribute("page").toString());
	System.out.println("currentpage="+currentpage);
	request.getSession().setAttribute("pagec",currentpage);
	System.out.println("TotalPage="+TotalPage);
	System.out.println("currentpage="+currentpage);
	if(currentpage>TotalPage){
		currentpage=TotalPage;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Monthly Project</title>
	<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
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
		if(str=="mp")
		{
			filter = "Project Filter";
			return "All Project";
		}
		else if(str=="lt")
		{
			filter = "Location Filter";
			return "All Location";
		}
		else if(str=="bc")
		{
			filter = "Business Category Filter";
			return "All Business Category";
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
		form.action = "InvoiceAction.do?operate=searchWithMpFilter&operPage=monthlyproject_List";
		form.submit();
	}
	
	$(document).mousemove(function(e)
			{
				if(e.pageX<x1||e.pageX>x1+170||e.pageY<y1||e.pageY>y1+165)
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
		if($("#allmp").attr("checked")==true)
		{
			$(".boxmp").attr("checked",true);
		}
		if($("#alllt").attr("checked")==true)
		{
			$(".boxlt").attr("checked",true);
		}
		if($("#allbc").attr("checked")==true)
		{
			$(".boxbc").attr("checked",true);
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
				<input type="text" name="monthprojectfilters" id="mp" class="ff" style="width:200px"
				<% if(monthprojectfilters!=null) {%> value="<%=monthprojectfilters%>" title="<%=monthprojectfilters %>" <%} else{ %>
				value="Project Filter" title="Project Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulmp" class="uu" style="display:none; width:200px" >
				<li><input type="checkbox" name="mp" id="allmp" class="allmp" onclick="javascript:checkBox(this,'mp');"
				<% if(monthprojectfilters!=null&&monthprojectfilters.indexOf("All Project")>=0) {%> checked="checked"<%} %> />
				<label id="allmpv">All Project</label></li>
				<%
					for (int i = 0; i < monthprojectlist.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cmp" id="mp<%=i %>" class="boxmp" onclick="javascript:checkBox(this,'mp');" 
				<% if(monthprojectfilters!=null&&monthprojectfilters.indexOf(monthprojectlist.get(i)+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="mp<%=i %>v"><%=monthprojectlist.get(i)%></label></li>
				<%
					}
				%>
				</ul>
	 	</div>
		<div class="drop" align="left" >
				<input type="text" name="locationfilters" id="lt" class="ff" style="width:160px"
				<% if(locationfilters!=null) {%> value="<%=locationfilters%>" title="<%=locationfilters %>" <%} else{ %>
				value="Location Filter" title="Location Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ullt" class="uu" style="display:none; width:160px" >
				<li><input type="checkbox" name="lt" id="alllt" class="alllt" onclick="javascript:checkBox(this,'lt');"
				<% if(locationfilters!=null&&locationfilters.indexOf("All Location")>=0) {%> checked="checked"<%} %> />
				<label id="allltv">All Location</label></li>
				<%
					for (int i = 0; i < locationlist.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="clt" id="lt<%=i %>" class="boxlt" onclick="javascript:checkBox(this,'lt');" 
				<% if(locationfilters!=null&&locationfilters.indexOf(locationlist.get(i)+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="lt<%=i %>v"><%=locationlist.get(i)%></label></li>
				<%
					}
				%>
				</ul>
	 	</div>
		<div class="drop" align="left" >
				<input type="text" name="bcategoryfilters" id="bc" class="ff" style="width:160px"
				<% if(bcategoryfilters!=null) {%> value="<%=bcategoryfilters%>" title="<%=bcategoryfilters %>" <%} else{ %>
				value="Business Category Filter" title="Business Category Filter"<%} %> onmouseover="javascript:getDropList(this);"/> 
				<ul id="ulbc" class="uu" style="display:none; width:160px" >
				<li><input type="checkbox" name="bc" id="allbc" class="allbc" onclick="javascript:checkBox(this,'bc');"
				<% if(bcategoryfilters!=null&&bcategoryfilters.indexOf("All Business Category")>=0) {%> checked="checked"<%} %> />
				<label id="alllbc">All Business Category</label></li>
				<%
					for (int i = 0; i < bcategorylist.size(); i++) 
					{
				%>
				<li><input type="checkbox" name="cbc" id="bc<%=i %>" class="boxbc" onclick="javascript:checkBox(this,'bc');" 
				<% if(bcategoryfilters!=null&&bcategoryfilters.indexOf(bcategorylist.get(i)+"")>=0) {
				%> checked="checked"<%} %>/>
				<label id="bc<%=i %>v"><%=bcategorylist.get(i)%></label></li>
				<%
					}
				%>
				</ul>
	 	</div>
		<div class="modeTable" align="center">
			<table border="0" cellpadding="3" cellspacing="1" style="clear:both;" class="tabbg5" align="center">
				<tr class="tr_title2">
					<td width="4%">ID</td>
					<td width="20%">Project</td>
					<td width="8%">Location</td>
					<td width="8%">Business Category</td>
					<td width="8%">Invoice/ Downpayment</td>
					<td width="8%">Initial Quotation/ Budget</td>
					<td width="8%">Used Amount</td>
					<td width="8%">Remaining Amount</td>
					<td width="8%">Cost in Latest Month</td>
					<td width="6%">	</td>
				</tr>
				<%
				if (showlist.size() == 0) {
				System.out.println("no list");
				%>
				<tr class="tr_content" align="center">
				<td colspan="9">No Records Found!</td>
				</tr>
				<%
				} else
				{
					for (int j = 0; j < showlist.size(); j++) 
					{
				%>

					<tr class="tr_content" align="center" onclick="javascript:getLaborID(this,'<%=showlist.get(j).getMonthprojectid() %>', '<%=j %>');">
					<td>
					<input type="radio" name="check" id="check<%=j%>" />
					<%=showlist.get(j).getMonthprojectid()%></td>
					<td align="left"><%=showlist.get(j).getMonthproject()%></td>
					<td><%if(showlist.get(j).getLocation()!=null&&showlist.get(j).getLocation()!="null"){out.print(showlist.get(j).getLocation());}
					else{out.print("");}%></td>
					<td><%if(showlist.get(j).getBusinesscategory()!=null&&showlist.get(j).getBusinesscategory()!="null"){out.print(showlist.get(j).getBusinesscategory());}
					else{out.print("");}%></td>
					<td><%if(showlist.get(j).getPayment()!=null&&showlist.get(j).getPayment()!="null"){out.print(showlist.get(j).getPayment());}
					else{out.print("");}%></td>
					<td align="left"><%out.print("$"+format.format((showlist.get(j).getBudget())+0.00));%></td>
					<td align="left"><%out.print("$"+format.format((showlist.get(j).getUsedBudegt())+0.00));%></td>
					<td align="left"><%if(showlist.get(j).getRemainBalance()<0) { out.print("-$"+(format.format(showlist.get(j).getRemainBalance())).toString().replace("-",""));}
  						else{out.print("$"+format.format(showlist.get(j).getRemainBalance()));}%></td>
					<td align="left"><%out.print("$"+format.format((showlist.get(j).getCostInLatestMonth())+0.00));%></td>
					<td>	
					<input type="button" name="<%=showlist.get(j).getMonthprojectid() %>" id="edit"
							class="btnEdit" onclick="javascript:editProject(this);" alt="Edit Project" style="margin-right:10px;"/>
					<input type="button" name="<%=showlist.get(j).getMonthprojectid()%>" id="remove"
							alt="remove Project" onclick="javascript:removeProject(this);" class="btnRemove" />
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
		else if(TotalPage>5&&m<5&&r==0){ p =j*5; n = m+1;}
		else if(TotalPage<=5) { p=1; n = TotalPage;}
			for(int i=0;i<n;i++)
			{ 
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
	<div>
	<input type="button" value="Add a New Project" class="btnStyle" onclick="javascript:addNewProject();" />
	<input type="button" value="Copy to a New Project" class="btnStyle" onclick="javascript:copyProject()"/>
	<input type="hidden" name="parentpage" value="<%=parentpage %>" />
	<input type="button" value="Project Report" <%if(showlist.size()==0){ %>disabled="disabled"<%} %>
				class="btnStyle1" style="margin-left:10px;" onclick="javascript:projectReport();" />
	</div>	 

	<hr  />
	<input name="submit1" type="button" id="return" class="btnBack" onclick='javascript:returnback();'/>
<script type="text/javascript">
var id ='';
var currentRow;
var index_copy=-1;

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
	index_copy=index;
	//alert(id);
	$("#check"+index).attr("checked","checked");
	
}

function projectReport()
{
	var form = document.forms[0];
	form.action = "ReportAction.do?operate=projectReport&curPage=monthlyproject_list";
	form.submit();
}

function returnback()
{
	var form = document.forms[0];
	form.action = "POAction.do?operate=showPOList&operPage=po_list&backpage=monthlyproject_list";
	form.submit();
}
function addNewProject()
{
    var form=document.forms[0];
    //通过post方式表单传参数，不是直接在url中传递，这是不安全的
	form.action = "InvoiceAction.do?operate=toInsertMonthlyproject&operPage=monthlyproject_Eidt";
    form.submit();
}
function copyProject()
{
	if(''==id)
	{
		alert("Please select a Project first!");
		return false;
	}
//	var num = index_copy;
	//alert(id);
	var form = document.forms[0];
	form.action= "InvoiceAction.do?operate=toEditProject&oper=copy&operPage=monthlyproject_Eidt&num="+id;
	form.submit();
}

function removeProject(obj)
{
	//remove一条记录使用局部刷新(AJAX)会比较高效一些
	var projectid = obj.name;
	if (!confirm("Sure to delete the Project?"))
	{
    	window.event.returnValue = false;
	}else
	{
		 var form = document.forms[0];
		 form.action="InvoiceAction.do?operate=deleteProject&operPage=monthlyproject_List&projectid="+projectid;
		 form.submit();    	    
	}
}
function editProject(obj)
{
	var pid=obj.name;
    var form=document.forms[0];
    form.action = "InvoiceAction.do?operate=toEditProject&operPage=monthlyproject_Eidt&num="+pid;
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
	form.action = "InvoiceAction.do?operate=turnMPPageJSP&operPage=monthlyproject_List&page="+page;	
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