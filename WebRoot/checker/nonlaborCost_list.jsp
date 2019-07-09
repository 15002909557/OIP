<%@ page language="java"
	import="java.util.*,java.text.DecimalFormat,com.beyondsoft.expensesystem.domain.checker.*" contentType="text/html; charset=GBK"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	
	@SuppressWarnings("unchecked")
	List<ProjectOrder> plist = ( ArrayList<ProjectOrder>) request.getSession().getAttribute("plist");
	@SuppressWarnings("unchecked")
	List<Groups> glist = (ArrayList<Groups>) request.getSession().getAttribute("glist");
	@SuppressWarnings("unchecked")
	List<NonLaborCost> nlist = (ArrayList<NonLaborCost>) request.getSession().getAttribute("nlist");
	String data =(String) request.getAttribute("data");
	String gid = (String) request.getAttribute("groupId");
	String pno = (String) request.getAttribute("pno");
	
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
	int	currentpage = Integer.parseInt(request.getSession().getAttribute("page").toString());
	request.getSession().setAttribute("pagec",currentpage);

 %>
<html>
  <head>
    
    <title>NonLabor Cost List</title>
    <link href="css/style1024.css" rel="stylesheet" id="style2" type="text/css">
    <script src="js/jquery-1.5.1.js"></script>
	<script type="text/javascript">
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
    });
	</script>
  </head>
  
  <body style="min-width:1010px;">
  <center>
  <form action="" method="post">
  <div class="btnsLine">
    <input type="radio" name="data" id="twoMonth" 
    value="0" <%if("0".equals(data)){ %> checked="checked"<%} %>/><label for="twoMonth"><i>Data in the latest 2 months</i></label>
    <input type="radio" name="data" id="allData" style="margin-left:6px;"value="1" 
    <%if("1".equals(data)){ %> checked="checked" <%} %>/><label for="allData"><i>All data</i></label>

    <select id="group" name="group"  title="The subgroup will be included." style="margin-right:30px; margin-left:30px;">
    <%for(int i=0;i<glist.size();i++){ %>
    <option value='<%=glist.get(i).getGid() %>'
    <% if(gid!=null&&glist.get(i).getGid()==Integer.parseInt(gid)){ %>
     selected="selected"
    <%} %>>
    <%=glist.get(i).getGname() %>
    </option>
    <%} %>
    </select>
    <select name="pno" id="pno" style="margin-right:30px;">
    <option value="all">All PO</option>
    <%for(int j=0;j<plist.size();j++) { %>
    <option value='<%=plist.get(j).getPONumber() %>'
    <% if(pno!=null&&plist.get(j).getPONumber().equals(pno)){ %>
     selected="selected"
    <%} %>>
    <%=plist.get(j).getPONumber() %>
    </option>
    <%} %>
    </select>
    <input type="button" id="search" class="btnSearch" onClick="javascript:searchLabor();"/>
   </div>
    <div id="modeTable">
     <!-- 
		   hanxiaoyu01
	       2012-11-08
	                   去掉部Project,ActiveType,SkillLevelName,TestAsset,Description同时增加Comments，Quantity,Product,
	       Component
	 -->
    <table border="0" cellpadding="3" cellspacing="1" width="98%" id="tabs" class="tabbg4" align="center">
	    <tr class="tr_title2">
		    <td width="5%">ID</td>
		    <td width="6%">Date</td>
		    <td width="6%">Group</td>
		    <td width="8%">Product</td>
		    <td width="8%">Component</td>
		    <td width="7%">PO</td>
		    <td width="10%">WBS</td>
		    <td width="6%">Location</td>
		    <td width="8%">Quantity</td>
		    <td width="11%">Comments</td>
		    <td width="8%">NonLabor Cost</td>
		    <td>Notes/Product Item</td>
		    <td width="6%">Data Filler</td>
		    </tr>
	    <% for(int n=0;n<nlist.size();n++){ %>
	    <tr onClick="javascript:getLaborID(this,'<%=nlist.get(n).getNid() %>','<%=n %>');" 
	    class="tr_content4" align="center">
		    <td>
		    <input type="radio" name="check" id="check<%=n %>"/>
		    <% out.print(nlist.get(n).getNid()); %>
		    </td>
		    <td><% out.print(nlist.get(n).getNdate()); %></td>
		    <td><% out.print(nlist.get(n).getGroupName()); %></td>
		    <td>
		    <% if(nlist.get(n).getProduct()!=null){
		          out.print(nlist.get(n).getProduct());
		    } %>
		    </td>
		    <td>
		    <% if(nlist.get(n).getComponent()!=null){
		          out.print(nlist.get(n).getComponent());
		    }
		     %>
		     </td>
		    <td><% out.print(nlist.get(n).getPurchaseOrder()); %></td>
		    <td>
		    <%
		    	if(nlist.get(n).getWBS()!=null&&!"null".equals(nlist.get(n).getWBS()))
		    	{
		    		out.print(nlist.get(n).getWBS());
		    	}
		    %>
		    </td>
		    <td><% out.print(nlist.get(n).getLocale()); %></td>
		    <td>
		    <%if(nlist.get(n).getQuantity()!=null){
		         out.print(nlist.get(n).getQuantity());
		    }
		      %>
		      </td>
		    <td>
		    <label id="comment" title="<%=nlist.get(n).getComments() %>">
		    <% if(nlist.get(n).getComments()!=null)
		    {
		         if(nlist.get(n).getComments().length()>16)
		         {
		        	 out.print(nlist.get(n).getComments().substring(0,16)+"..."); 
		         }
		         else
		         {
		        	 out.print(nlist.get(n).getComments()); 
		         }
		    	
		    }
		    %>
		    </label>
		    </td>
		    <td align="left"><% out.print("$"+format.format(nlist.get(n).getNonLaborCost())); %></td>
		    <td>
		    <label id="note" title="<%=nlist.get(n).getNotes() %>">
		    <% 
		    if(nlist.get(n).getNotes().length()>16)
		    {
		    	out.print(nlist.get(n).getNotes().substring(0,16)+"...");
		    }
		    else
		    {
		   	 	out.print(nlist.get(n).getNotes());
		    } %>
		    </label>
		    </td>
		    <td>
		    <%
		    out.print(nlist.get(n).getCreator());
		    %>
		    </td> 
	    </tr>
	    <%}
	    if(nlist.size()==0){%>
	   <tr class="tr_content" align="center">
	   	<td colspan="13">No Records Found!</td>
	   </tr>
	    <%} %>
    </table>
    </div>
  
  <!-- 分页 -->
  <%
 	if(currentpage==0) currentpage=1;
   %>
  <div class="turnPage">
		<label <% if(currentpage==0||currentpage==1) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %> onclick="javascript: turnpage('1');">First</label>
		<label class="pageCell" onclick="javascript: turnpage('<%=currentpage-1 %>');">Prev</label>
		<% 
		int j = currentpage/5;
		int r = currentpage%5;
		int p =j*5;
		int n =0;
		int m = TotalPage -p;		
		if(TotalPage>5&&m>=5&&r>0){ p =j*5+1; n = 5;}
		else if(TotalPage>5&&m>=5&&r==0){ p =j*5-4; n = 5;}
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
	
    <div style="padding:13px;">
    <input id="newNon" type="button" value="Add New NonLaborCost" class="btnStyle" onclick="javascript: addNonLaborCost();"/>
    <input id="nonEdit" type="button" value="Edit NonLaborCost" class="btnStyle" onclick="javascript: editNonLaborCost();"/>
    <input id="nonDel" type="button" value="Remove NonLaborCost" class="btnStyle" onclick="javascript: deleteN();"/>
    <input id="nonCopy" type="button" value="Copy a New NonLaborCost" class="btnStyle" onclick="javascript: copyANewNonLaborCost();"/>
    </div>
    <hr />
    <div style="padding:5px;">    
    <input type="button" id="back" class="btnBack" onClick="javascript:returnback();" />
    </div>
    </form>
    </center>
  </body>
<script type="text/javascript">
var currentRow;
var id;
 function getLaborID(obj,nid,index)
 {
  if(currentRow)
 	{
 		currentRow.style.background='#ffffff';
 	}
 	currentRow=obj;
 	currentRow.style.background='#ffe6d1';
 	id = nid;//取得被选中labor行中的nid
 	//alert(id);
 	$("#check"+index).attr("checked","checked");
 
 }
 function searchLabor()
 { 	
 	var form = document.forms[0];
 	form.action = "POAction.do?operate=searchNonLaborCost&operPage=nonlaborCost_list";
 	form.submit(); 	
 }
 
 function deleteN()
 {    
    if(null!=id)
	{
	    if (!confirm("Sure to delete this NonLabor Cost?"))
	 	{
	    	window.event.returnValue = false;
	    }
	   	else
	   	{
	    	
			var form = document.forms[0];
	 		form.action = "POAction.do?operate=deleteNonLaborCost&operPage=nonlaborCost_list&nid="+id;
	 		form.submit();	 		
	 	}
 	}
	else
		alert("Please select a NonLaborCost first!");
 }

 function addNonLaborCost()
{
    
	var form = document.forms[0];
	form.action = "POAction.do?operate=toinsertNonLabor&operPage=nonLaborCost_add";
	form.submit();
}
function editNonLaborCost()
{
	if(null!=id)
	{
	var form = document.forms[0];
	form.action = "POAction.do?operate=toinsertNonLabor&operPage=nonLaborCost_add&oper=modify&nonid="+id;
	form.submit();
	}
	else
		alert("Please select a NonLaborCost first!");
}
function copyANewNonLaborCost()
{
	if(null!=id)
	{
	var form = document.forms[0];
	form.action = "POAction.do?operate=toinsertNonLabor&operPage=nonLaborCost_add&oper=copy&nonid="+id;
	form.submit();
	}
	else
		alert("Please select a NonLaborCost first!");
}

 function returnback(){
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=backPage&operPage=data_checker_01";
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
	if(page>total){return false;};
	var form = document.forms[0];
	form.action = "POAction.do?operate=turnLPageJSP&operPage=nonlaborCost_list&page="+page;
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

  
</html>
