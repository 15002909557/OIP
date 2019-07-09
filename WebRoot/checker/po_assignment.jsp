<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*" contentType="text/html; charset=GBK"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	@SuppressWarnings("unchecked")
	List<Map> Productlist = (ArrayList<Map>) request.getSession().getAttribute("Productlist");
	@SuppressWarnings("unchecked")
	List<Map> Componentlist = (ArrayList<Map>) request.getSession().getAttribute("Componentlist");
	@SuppressWarnings("unchecked")
	List<ProjectOrder> POlist = (ArrayList<ProjectOrder>) request.getSession().getAttribute("polist");
	String currentPath = "po_assignment";
	request.getSession().setAttribute("path",currentPath);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
    <title>PO Management & Monthly Expense</title>
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
 
  <body>
  <center>
   <form action="" method="post">
   <div style="width:1100px; margin-top:40px;">
    <div class="u_left">
    	<div id="uu_left">
    	<div class="uu_t" style="margin-top:60px; margin-left:20px;">
    	<span style="font-size:12px; font-weight:bolder;">Product:&nbsp;</span>
    	<select class="dd_box" id="product" name="product" onchange="javascript:searchList();">
    	<option value="-1">ProductList</option>
    	<%for(int i=0;i<Productlist.size();i++){ %>
    	<option value='<%=Productlist.get(i).get("productid")%>'>
    	<%=Productlist.get(i).get("product") %>
    	</option>
    	<%} %>
    	</select>
    	</div>
    	<div class="uu_t" style="margin-top:6px;">
    	<span style="font-size:12px; font-weight:bolder;">Component:&nbsp;</span>
    	<select class="dd_box" id="component" name="component" onchange="javascript:searchList();">
    	<option value="-1">ComponentList</option>
    	<%for(int i=0;i<Componentlist.size();i++){ %>
    	<option value='<%=Componentlist.get(i).get("componentid") %>'>
    	<%=Componentlist.get(i).get("componentName") %>
    	</option>
    	<%} %>
    	</select>
    	</div>
    	</div>
    	<div id="uu_right">
    	<div style="font-size:13px; font-weight:bolder; margin-top:10px; margin-bottom:6px;">
    	Search Results
    	</div>
  		<div style="height:218px; overflow:auto; overflow-x:hidden;">
  		<table id="tabs" border="0" style="text-align:center;" width="140" cellpadding="0" cellspacing="0">
  		<tr class="list">
  		<td>
  		<div style="border:1px #ccc solid; padding:3px; background:#ffffff;">
  			Please select a Product and a Component!
  		</div>
  		</td>
  		</tr>
  		</table>
  		
    	</div>
    	</div>
    </div>
    <div class="u_center">
    <input type="button" value="Add" id="add" disabled="disabled" class="btnStyle1" style="margin-top:100px;width:60px;" onclick="javascript:addPO();"/>
    <input type="button" value="Remove" id="remove" class="btnStyle1" disabled="disabled" style="margin-top:20px;width:60px;" onclick="javascript:delectPN();"/>
    </div>
  <div class="u_right">
   	 <div id="tab" style="height:230px; margin-top:4px; overflow: auto;">  
   	 <table width="95%" border="0" cellpadding="3" cellspacing="1" class="tabbg">
    	<tr class="tr_title2">
    		<td align="center">PONumber</td>
    		<td align="center">PO Manager</td>
    		<td></td>
    	</tr>
    	<%for(int i=0;i<POlist.size();i++){ 
    	if(!POlist.get(i).getPONumber().equals("")){
    	%>
    	<tr id="pl<%=i %>" onclick="selectPO(this,'<%=POlist.get(i).getPOID() %>','<%=POlist.get(i).getPONumber() %>','<%=i %>');" class="tr_content">
    		<td>
    		<input type="radio" name="check" id="check<%=i %>"/>
    		<% out.print(POlist.get(i).getPONumber());%>
    		</td>
    		<td><% out.print(POlist.get(i).getPOManager());%></td>
    		<td>
    		<!-- Modified to Invoice List to Monthly Expense by FWJ on 2013-03-13-->
    			<input name="<%=i %>" type="button" value="Monthly Expense" class="btnInvoice" 
    				onclick="javascript: gotoInvoiceList('<%=POlist.get(i).getPOID() %>');"/>
    			<input type="hidden" id="poname<%=i %>" value="<%=POlist.get(i).getPONumber() %>" />
    			<input type="hidden" id="poid<%=i %>" value="<%=POlist.get(i).getPOID() %>" />
    		</td>
    	</tr>
    	<%} }%>
    	</table>
   	 </div>
    <div style="margin:5px;">
    	<input type="button" value="Add New PO" class="btnStyle" onclick="javascript:addNewPO();"/>
   		<input type="button" value="PO List" class="btnStyle" onclick="javascript:getPOList();"/>
    	<input type="button" value="Edit PO" class="btnStyle" onclick="javascript:editPO();"/>
    </div>
  </div>
<!--
    <div class="show_box">
    <label id="show" for="show">
    <input type="checkbox" id="show" onclick="javascript:showBox();" />
     show search box
     </label> 
    </div>
    
     </div>
    <div id="search_box" style="display:none;">
    	<hr style="border:1px dashed #ccc;"/>
    	<div class="s_left">
    		<div class="s_dd">
   			 	<span style="font-size:13px; font-weight:bolder;">PO</span>&nbsp;&nbsp;
   			 	<select id="pcpn" name="pcpn" onchange="javascript:listChange(this.options[this.selectedIndex].value);">
   			 	<option value="-1">PONumberList</option>
   			 	<%for(int i=0;i<POlist.size();i++){ 
   			 	if(!POlist.get(i).getPONumber().equals("")){%>
   			 	<option value='<%=POlist.get(i).getPOID() %>'>
   			 	<%=POlist.get(i).getPONumber() %>
   			 	</option>
   			 	<%}} %>
   			 	</select>
    		</div>
    	
   		 </div>
    	<div class="s_right">
    		<div style="font-size:13px; font-weight:bolder; padding-left:40px; padding-bottom:5px; line-height:150%; text-align:left;">PO Assignment </div>
    		<div style="max-height:150px;">
    			<table width="90%" border="0" cellpadding="3" cellspacing="1" id="showdata" class="tabbg" align="center">
    				<tr class="tr_title2" id="tabHeader">
    					<td align="center" width="80">ID</td>
    					<td align="center" width="170">PO</td>
    					<td align="center" width="170">Product</td>
    					<td align="center" width="170">Component</td>
    					<td align="center" width="270">Recent NonLaborCost Date</td>
    				</tr>
    				<tr class="tr_content3" id="please"><td colspan="5" align="center"> Please select a PO! </td></tr>
    				<tr class="tr_content3" id="nodata" style="display:none;"><td colspan="5" align="center"> NO DATA !</td></tr>
   				</table>
   			</div>
    	</div>
    </div>
-->
    <hr style="clear:both;"/>
    <input type="button" class="btnBack" onclick="javascript:returnback();"/>
    </form>
    </center>
 
<script type="text/javascript">
//设置全局异步：hanxiaoyu01 2013-01-05
$.ajaxSetup({  
   async : false  
});

 var currentRow;
 var currentRow1;
 var rc=0;
 var pn;
 var no;

//查询左边对应的product与component的PONumberList  
function searchList()
{
	$("tr").remove(".list");//先清空表中原数据
  	$.post("POAction.do?operate=searchPCP&operPage=po_assignment",
  		{product:$("#product").val(),component:$("#component").val()},
  		function(result)
  		{
  			$("#tabs").append(
	  			function(n,html)
	  			{
	  				n = result.length;
	  				for(var i=0;i<result.length;i++)
	  				{
	  					html += "<tr class='list'><td><div onclick='selectPN(this,"+result[i].poid+")' class='ls'>"+result[i].pno+"</div></td></tr>";
	  				}
	  				return html;
	  			} 			
  			);
		},"json");
}

function selectPO(obj,pid,pnum,index)//选中右边POList对应行，取得该行po的POID、PONumber
{
 	if(currentRow)
 	{
 		currentRow.style.background='#ffffff';
 	}
 	document.getElementById("add").disabled="";
 	currentRow=obj;
 	currentRow.style.background='#ffe6d1';
 	rc = pid;//取得被选中po行中的poid
 	pn = pnum;//取得被选中po行中的ponumber
 	$("#check"+index).attr("checked","checked");
 	
 	//if(document.getElementById("show").checked)//若下面的po-pro-com查询显示栏为显示状态
 	//{
 	//	listChange(pid);
 	//	document.getElementById("pcpn").value = pid;//ponumber downlist中取得对应的ponumber
 	//}

}
 function addPO()//将右边POlist中的PO添加给左边对应的product与component
 {
	var productid = $("#product").val();
  	var componentid = $("#component").val();
  	if(productid==-1)
  	{
  		alert("Please select a product!");
  		$("#product").focus();
  		return false;
  	}
  	if(componentid==-1)
  	{
  		alert("Please select a component!");
  		$("#component").focus();
  		return false;
  	}

	var url = "POAction.do?operate=addPOlist&operPage=po_assignment";
	$.post(url,{poid:rc,productid:productid,componentid:componentid},
	function(result)
	{
		if(result=="true")
		{
  			$("#tabs").append(
	  			function(n,html)
	  			{
	  				html = "<tr class='list'><td><div onclick='selectPN(this,"+rc+")' class='ls'>"+pn+"</div></td></tr>";
	  				return html;
	  			}
	  		);	
		}
		else
		{
			alert("Already Exist!");
		}
	});
 } 


//点击选中对应行内的input对象，选中项改变颜色
function selectPN(nbj,poid)
{
	if(currentRow1)
 	{
 		currentRow1.style.background='#ffffff';
 	}
 	document.getElementById("remove").disabled="";
	currentRow1 = nbj;
	nbj.style.background='gray';
	no = poid;
}
//删除选中的ponumber
function delectPN()
{	
	var productid = $("#product").val();
  	var componentid = $("#component").val();
  	//hanxiaoyu01 2013-01-06
  	var product=$("#product").find("option:selected").text();
  	var component=$("#component").find("option:selected").text();
  	product=$.trim(product);
  	component=$.trim(component);
  	
  	//hanxiaoyu01 2013-01-05
	//删除PO前先作判断，如果这个PO在Project中被引用，则不能删除 
	   var result="";
	   //hanxiaoyu01 2013-01-06
   	    $.post("POAction.do",{"operate":"checkPO2","poid":no,"product":product,"component":component},function(data){
   	     result=data;
   	    });
   	    
   	    if(result=="true"){
   	     alert("This PO  has been used,you can not delete it!");
   	     return false;
   	    }else{
   	     url = "POAction.do?operate=deletePONumberOfPCP&operPage=po_assignment";
	      $.post(url,{productid:productid,componentid:componentid,pno:no},
		    function(result)
		    {
			   if('true'==result)
			     {
				alert("successful!");
				searchList();
				document.getElementById("remove").disabled="disabled";
			    }
			   else
				alert("fail!");
		   }
	     );
   	    }
	
	
}
//返回主菜单页面backPage
function returnback()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=backPage&operPage=data_checker_01";
	form.submit();
 }
//检查复选框是否选中
function showBox()
{
	var box = document.getElementById("show");//取得复选框对象
	if(!box.checked)
	{
		box.checked = 'true';
	}
	else 
	{
		box.checked = '';
	}
	searchPODetails(box);
}
//点击复选框使下面的po-pro-com查询显示栏与否
function searchPODetails(box)
{
	if(!box.checked)
	{		
		$("#search_box").css("display","none");
		return false;	
	}
	$("#search_box").css("display","");

}

function listChange(poid)
{
	$("tr").remove(".tr_content3");//先清空原数据
	if(poid==-1)
	{
		$("#please").css("display","");
		$("#nodata").css("display","none");
		return false;
	}
	$.post("POAction.do?operate=searchPCPDetails&operPage=po_assignment",{poid:poid},
		function(result)
		{
			$("#please").css("display","none");
			$("#nodata").css("display","none");
			if(result.length>0)
			{
				$("#showdata").append(
		  			function(n,html)
		  			{
		  				n = result.length;
		  				html = "";
		  				for(var i=0;i<result.length;i++)
		  				{
		  					html += "<tr class='tr_content3'><td>"+(i+1)+"</td><td>"+result[i].pno+"</td><td>"
		  							+result[i].product+"</td><td>"+result[i].component+"</td><td>"
		  							+result[i].last+"</td></tr>";
		  				}
		  				return html;
		  			}
	  			);
			}
			else
			{
				$("#nodata").css("display","");
			}
		},"json");
}


function gotoInvoiceList(POID)
{
	var form = document.forms[0];
	form.action = "InvoiceAction.do?operate=searchbyMonthlyEx&operPage=Invoice_List&parentpage=poassignment&POID="+POID;
	form.submit();
}

//进入到po list
function getPOList()
{
	var form = document.forms[0];
	form.action="POAction.do?operate=showPOList&operPage=po_list";
	form.submit();

}
function addNewPO()
{
	var form = document.forms[0];
	form.action="POAction.do?operate=toAddPO&operPage=po_add";
 	form.submit();
}

//edit po
function editPO()
{
	if(rc==0)
	{
		alert("Please select a po first!");
		return false;
	}
	var form = document.forms[0];
 	form.action="POAction.do?operate=toEditPO&operPage=po_edit&poid="+rc;
 	form.submit();
}

  </script>
</body>
</html>
