<%@ page contentType="text/html; charset=GBK" language="java"
	import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Set Name List</title>
	<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
	<link rel="stylesheet"  href="css/jquery.ui.all.css" />
	<script src="js/jquery-1.5.1.js"></script>
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
 	<script src="js/jquery.ui.tabs.js"></script>
	<script language="javascript" type="text/javascript">
	$(function() {
		$( "#tabs" ).tabs();			
		getItemList("ProductYearSelect");
		getItemList("PlatformSelect");
		getItemList("BusinessSelect");
		getItemList("FWQEOwnerSelect");
		loadData("Product");
		$( "#ProductButton" ).click(function ()
			{
				loadData('Product');
				getItemList("ProductYearSelect");
				getItemList("PlatformSelect");
				getItemList("BusinessSelect");
				getItemList("FWQEOwnerSelect");
			});
		$( "#SkillLevelButton" ).click(function ()
			{
				loadData('SkillLevel');
			});
		$( "#LocationButton" ).click(function ()
			{
				loadData('Location');
			});
		$( "#OTTypeButton" ).click(function ()
			{
				loadData('OTType');
			});
		/** dancy 2012-12-20
		$( "#DescriptionButton" ).click(function ()
			{
				loadData('Description');
			});
		**/
		$( "#WorkTypeButton" ).click(function ()
			{
				loadData('WorkType');
			});
		$( "#MilestoneButton" ).click(function ()
			{
				loadData('Milestone');
			});
		$( "#ComponentButton" ).click(function ()
			{
				loadData('Component');
			});
		/** hanxiaoyu01 2012-12-18隐藏location code
		$( "#LocationcodeButton" ).click(function ()
			{
				loadData('Locationcode');
			});
		*/
		$( "#POManagerButton" ).click(function ()
			{
				loadData('POManager');
			});
		$( "#PlatformButton" ).click(function ()
			{
				loadData('Platform');
			});
		$( "#BusinessButton" ).click(function ()
			{
				loadData('Business');
			});
		$( "#WBSButton" ).click(function ()
			{
				loadData('WBS');
			});
		$( "#FWQEOwnerButton" ).click(function ()
			{
				loadData('FWQEOwner');
			});
		$( "#ProductYearButton" ).click(function ()
			{
				loadData('ProductYear');
			});
		/**
		hanxiaoyu01 2013-01-05隐藏Bill Cycle
		 * $( "#CycleButton" ).click(function ()
					{
				loadData('Cycle');
			});
		 
		 */
		
	    //hanxiaoyu01  2012-12-17
	    $("#TestTypeButton").click(function(){
		        loadData('TestType');
		    });
		//Added by FWJ on 2013-03-06
		 $("#TargetLaunchButton").click(function(){
     		loadData('TargetLaunch');
 		});

		//Added by FWJ on 2013-03-14
		 $("#CategoryButton").click(function(){
     		loadData('Category');
 		});
	 		
		//Added by FWJ on 2013-05-20
		 $("#BudgetButton").click(function(){
     		loadData('Budget');
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
	});
		
        </script>
	</head>
	<body>
	<center>
	<form action="ProjectAction.do?operate=addProductName&operPage=data_checker_01" target="hide" method="post">
		<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
		<div class="btnsLine">
			<input name="Submit2332" type="button" class="btnBack" onclick='javascript:backpage();' />
		</div>
			<div id="tabs">
				<ul>
					<li><a href="#tabs-Product" id="ProductButton">Product</a></li>
					<li><a href="#tabs-SkillLevel" id="SkillLevelButton">SkillLevel</a></li>
					
					<li><a href="#tabs-Location" id="LocationButton">Location</a></li>
					<li><a href="#tabs-OTType" id="OTTypeButton">OTType</a></li>
					<!-- <li><a href="#tabs-Description" id="DescriptionButton">Description</a></li> -->
					<li><a href="#tabs-WorkType" id="WorkTypeButton">WorkType</a></li>
					<li><a href="#tabs-Milestone" id="MilestoneButton">Milestone</a></li>
					<li><a href="#tabs-Component" id="ComponentButton">Component</a></li>
					<%--hanxiaoyu01 2012-12-19去掉location code
					<li><a href="#tabs-Locationcode" id="LocationcodeButton">Locationcode</a></li>
					--%>
					<li><a href="#tabs-pomanager" id="POManagerButton">POManager</a></li>
					<li><a href="#tabs-platform" id="PlatformButton">Platform</a></li>
					<li><a href="#tabs-business" id="BusinessButton">Business</a></li>
					<li><a href="#tabs-wbs" id="WBSButton">WBS</a></li>
					<li><a href="#tabs-fwqeowner" id="FWQEOwnerButton">FWQEOwner</a></li>
					<li><a href="#tabs-productyear" id="ProductYearButton">ProductYear</a></li>
					<!-- hanxiaoyu01 2013-01-05 
					 <li><a href="#tabs-cycle" id="CycleButton">Bill Cycle</a></li>
					 -->
					<!-- hanxiaoyu01 2012-12-17 增加Test Type -->
					<li><a href="#tabs-testtype" id="TestTypeButton">Test Type</a></li>
					<!-- Add Target Launch by FWJ on 2013-03-06 -->
					<li><a href="#tabs-targetlaunch" id="TargetLaunchButton">Target Launch</a></li>
					<!-- Add Category by FWJ on 2013-03-14 -->
					<li><a href="#tabs-category" id="CategoryButton">Category</a></li>
					<!-- Add Budget by FWJ on 2013-05-20 -->
					<li><a href="#tabs-budget" id="BudgetButton">Budget Tracking</a></li>
				</ul>
					<!-- product name -->
						<div id="tabs-Product">
							<div class="tabbg2" align="center">								
								<input type="text" name="Product" id="Product" value="Product" class="input_text4" maxlength="60" onfocus="this.select()" />
								<select id="ProductYearSelect" style="width:150px; margin-left:16px;" title="Product Year">
									<option value=""></option>
								</select>
								<select id="PlatformSelect" style="width:150px; margin-left:16px;" title="Platform">
									<option value=""></option>
								</select>
								<select id="BusinessSelect" style="width:150px; margin-left:16px;" title="Business">
									<option value=""></option>
								</select>
								<select id="FWQEOwnerSelect" style="width:150px; margin-left:16px; margin-right:20px;" title="FWQEOwner">
									<option value=""></option>
								</select>						
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Product");' />							
							</div>
								<table id="ProductTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
									<tr class="tr_title2">
										<td width="8%">ID</td>
										<td width="26%">Product Name</td>
										<td width="14%">Product Year</td>
										<td width="14%">Business</td>
										<td width="14%">Platform</td>
										<td width="16%">FWQEOwner</td>
										<!-- <td align="center" width="8%"></td> -->
									</tr> 
								</table>
						</div>
					<!-- product name -->
						<!-- SkillLevel START-->
						<div id="tabs-SkillLevel">
							<div class="tabbg2">									
								<input type="text" name="SkillLevel" id="SkillLevel" value="SkillLevel" class="input_text4" style="width:200px; margin-right:16px;" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("SkillLevel");' />
							</div>
							<table id="SkillLevelTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td width="44%">SkillLevel Name</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- SkillLevel END -->
					<!-- Location START-->
						<div id="tabs-Location">
							<div class="tabbg2">
								<input type="text" name="Location" id="Location" class="input_text5" value="Location" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Location");' />
							</div>
								<table id="LocationTable"  border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
									<tr class="tr_title2">
										<td width="8%">ID</td>
										<td>Location</td>
										<!-- <td align="center" width="8%"></td> -->
									</tr> 
								</table>
						</div>
					<!-- Location END -->
					<!-- OTType START-->
						<div id="tabs-OTType">
							<div class="tabbg2">
								<input type="text" name="OTType" id="OTType" value="OTType" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("OTType");' />
							</div>
							<table id="OTTypeTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>OTType</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- OTType END -->
					<%-- dancy 2012-12-20
					<!-- Description START-->
						<div id="tabs-Description">
							<div class="tabbg2">
								<input type="text" name="Description" id="Description" value="Description" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Description");' />
							</div>
							<table id="DescriptionTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Description</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- Description END -->
					--%>
					<!-- WorkType START-->
						<div id="tabs-WorkType">
							<div class="tabbg2">
								<input type="text" name="WorkType" id="WorkType" value="WorkType" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("WorkType");' />
							</div>
							<table id="WorkTypeTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>WorkType</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- WorkType END -->
					<!-- Milestone START-->
						<div id="tabs-Milestone">
							<div class="tabbg2">
								<input type="text" name="Milestone" id="Milestone" value="Milestone" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Milestone");' />
							</div>
							<table id="MilestoneTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Milestone</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- Milestone END -->
					<!-- --------------- 20111125加入component    -->	
					<!-- Component START-->
						<div id="tabs-Component">
							<div class="tabbg2">
								<input type="text" name="Component" id="Component" value="Component" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Component");' />
							</div>
							<table id="ComponentTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Component</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- Component END -->
					<%-- hanxiaoyu01 2012-12-19 隐藏Location Code
						<div id="tabs-Locationcode">
							<div class="tabbg2">
								<input type="text" name="Locationcode" id="Locationcode" value="Locationcode" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Locationcode");' />
							</div>
							<table id="LocationcodeTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Locationcode</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					--%>
					<!-- POManager START-->
						<div id="tabs-pomanager">
							<div class="tabbg2">
								<input type="text" name="POManager" id="POManager" value="POManager" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("POManager");' />
							</div>
							<table id="POManagerTable" width="1010" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>POManager</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- POManager END -->
				<!-- Platform START-->
						<div id="tabs-platform">
							<div class="tabbg2">
								<input type="text" name="Platform" id="Platform" value="Platform" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Platform");' />
							</div>
							<table id="PlatformTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Platform</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- Platform END -->
					
					<!-- Business START-->
						<div id="tabs-business">
							<div class="tabbg2">
								<input type="text" name="Business" id="Business" value="Business" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Business");' />
							</div>
							<table id="BusinessTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Business</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- Business END -->
					
					<!-- WBS START-->
						<div id="tabs-wbs">
								<div class="tabbg2">
									<input type="text" name="WBS" id="WBS" value="WBS" class="input_text5" maxlength="50" onfocus="this.select()" />
									<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("WBS");' />
								</div>
								<table id="WBSTable" border="0" cellpadding="3" cellspacing="1"
										style="font-size: 12px;" align="center" class="tab_bg">
									<tr class="tr_title2">
										<td width="8%">ID</td>
										<td>WBS</td>
										<!-- <td align="center" width="8%"></td> -->
									</tr> 
								</table>
						</div>
					<!-- WBS END -->
					
					<!-- FWQEOwner START-->
						<div id="tabs-fwqeowner">
							<div class="tabbg2">
								<input type="text" name="FWQEOwner" id="FWQEOwner" value="FWQEOwner" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("FWQEOwner");' />
							</div>
							<table id="FWQEOwnerTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>FWQEOwner</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- FWQEOwner END -->
					
					<!-- ProductYear START-->
						<div id="tabs-productyear">
							<div class="tabbg2">
								<input type="text" name="ProductYear" id="ProductYear" value="ProductYear" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("ProductYear");' />
							</div>
							<table id="ProductYearTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>ProductYear</td>
									<!-- <td align="center" width="8%"></td> -->
								</tr> 
							</table>
						</div>
					<!-- ProductYear END -->
					<!-- hanxiaoyu01 2013-01-05
						<div id="tabs-cycle">
							<div class="tabbg2">
								<input type="text" name="Cycle" id="Cycle" value="Cycle" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("Cycle");' />
							</div>
							<table id="CycleTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>BillCycle</td>
									
								</tr> 
							</table>
						</div>
						-->
					
					<!-- Test Type Start hanxiaoyu01 2012-12-17-->
					<div id="tabs-testtype">
							<div class="tabbg2">
								<input type="text" name="TestType" id="TestType" value="TestType" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript: addData("TestType");' />
							</div>
							<table id="TestTypeTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Test Type</td>
								</tr> 
							</table>
						</div>
					<!-- Test Type end-->
					
					<!-- Added Target Launch by FWJ on 2013-03-06-->	
					<div id="tabs-targetlaunch">
							<div class="tabbg2">
								<input type="text" name="TargetLaunch" id="TargetLaunch" value="TargetLaunch" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript:addData("TargetLaunch");' />
							</div>
							<table id="TargetLaunchTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Target Launch</td>
								</tr> 
							</table>
						</div>	
						
					<!-- Target Launch end-->
					
					<!-- Added Category by FWJ on 2013-03-14-->	
					<div id="tabs-category">
							<div class="tabbg2">
								<input type="text" name="Category" id="Category" value="Category" onfocus="if (value==defaultValue){value=''}" onblur="if(value==''){value=defaultValue}" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript:addData("Category");' />
							</div>
							<table id="CategoryTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Category</td>
								</tr> 
							</table>
						</div>
						
					<!-- Category end-->
					<!-- Added Budget by FWJ on 2013-05-20-->	
					<div id="tabs-budget">
							<div class="tabbg2">
								<input type="text" name="Budget" id="Budget" value="Budget Tracking" onfocus="if (value==defaultValue){value=''}" onblur="if(value==''){value=defaultValue}" class="input_text5" maxlength="50" onfocus="this.select()" />
								<input name="Submit2332" type="button" class="btnStyle1" value=" Add " onclick='javascript:addData("Budget");' />
							</div>
							<table id="BudgetTable" border="0" cellpadding="3" cellspacing="1" align="center" class="tab_bg">
								<tr class="tr_title2">
									<td width="8%">ID</td>
									<td>Budget Tracking</td>
								</tr> 
							</table>
						</div>
						
					<!-- Budget end-->
					
			</div>
			</form>
		</center>
	
<script type="text/javascript">

function backpage()
{
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=backPage&operPage=data_checker_01";
	form.submit();
}

var main;
//test function override  by Dancy 2012-01-12
function loadData(obj)
{
	var url ="POAction.do?operate=searchData&operPage=data_checker_13&dataName="+obj;	
	var objTable = obj+"Table";
	var rowlength = document.getElementById(objTable).rows.length;
	while(rowlength>1)
	{
		//document.all(objTable).deleteRow(rowlength-1); 
		document.getElementById(objTable).deleteRow(rowlength-1);
		rowlength = document.getElementById(objTable).rows.length;
	}
	$.post(url,
			function(result)
			{
				for(var i=0;i<result.length;i++)
				{
					//var newRow = document.all(objTable).insertRow(-1);
					
					var newRow = document.getElementById(objTable).insertRow(-1);
					newRow.setAttribute("className","tr_content");
					newRow.setAttribute("class","tr_content");//兼容firefox
					var oCell = newRow.insertCell(-1);  
					oCell.innerHTML = "<label>"+result[i].id+"</label>";
					oCell = newRow.insertCell(-1);
					
					var str="&nbsp;<img name='m' id='"+obj+i+"M' src='images/save.png' width='20' height='20' style='display:none;cursor:pointer;'"
					+"onclick='javascript:modifyData("+result[i].id+","+i+");' alt='Save Modify' title='Save Modify' />"
					
					+"&nbsp;<img name='d' id='"+obj+i+"D' src='images/delete.png' style='display:none;cursor:pointer;'"
					+"onclick='deleteData("+result[i].id+","+i+")' alt='Hide' title='Hide' />" 
					
					+"&nbsp;<label id='"+obj+i+"A'></label>";//防止找不到id，页面出现黄色感叹号
					
					if(result[i].hide=="1")
					{
						newRow.style.background="#E0E0E0";
					//	oCell.disabled="disabled";
					//	oCell.innerHTML.disabled="disabled";
					//	oCell2 = newRow.insertCell(-1);
						str="&nbsp;<input type='button' name='a' id='"+obj+i+"A' value='Active' style='display:none;cursor:pointer;'"
							+"onclick='activeData("+result[i].id+","+i+")' alt='Active' title='This String has been hidden, click to Active it.' />"+"<label id='"+obj+i+"D'></label>"+"<label id='"+obj+i+"M'></label>";	
					}	
						
					oCell.innerHTML = "<label "
						+"onclick='toEditData("+i+");' "
						+"id='"+obj+i+"L' style='font-family: Arial;font-size: 10pt; font-weight:normal;'>"
						+result[i].data+"</label>"			
						+"<input type='text' id='"+obj+i+"I'"
						+"style='overflow:visible;display:none;font-family: Arial;font-size: 10pt; font-weight:normal;'>"
						+"<img name='x' src='images/cancel.png' id='"+obj+i+"X' onclick='notEditData("+i+");' style='display:none;cursor:pointer;'"
						+"alt='Cancel' title='Cancel' />"
			
						+str;
										
					if('Product'==obj)
					{
						if(result[i].short==null)
						{
							result[i].short='';
						}
						var oCell = newRow.insertCell(-1);  
						oCell.innerHTML = "<label onclick='toEditData("+i+");' id='P"+obj+i+"L' style='font-family: Arial;font-size: 10pt; font-weight:normal;'>"
										+result[i].productyear+"</label>"
										+"<input type='text' id='P"+obj+i+"I'"
										+"style='overflow:visible;display:none;font-family: Arial;font-size: 10pt; font-weight:normal;'>";
						
						oCell = newRow.insertCell(-1); 
						oCell.innerHTML = "<label onclick='toEditData("+i+");' id='B"+obj+i+"L' style='font-family: Arial;font-size: 10pt; font-weight:normal;'>"
										+result[i].bss+"</label>"
										+"<input type='text' id='B"+obj+i+"I'"
										+"style='overflow:visible;display:none;font-family: Arial;font-size: 10pt; font-weight:normal;'>";
						
						oCell = newRow.insertCell(-1); 
						oCell.innerHTML = "<label onclick='toEditData("+i+");' id='F"+obj+i+"L' style='font-family: Arial;font-size: 10pt; font-weight:normal;'>"
										+result[i].pform+"</label>"
										+"<input type='text' id='F"+obj+i+"I'"
										+"style='overflow:visible;display:none;font-family: Arial;font-size: 10pt; font-weight:normal;'>";
						
						oCell = newRow.insertCell(-1);  
						oCell.innerHTML = "<label onclick='toEditData("+i+");' id='S"+obj+i+"L' style='font-family: Arial;font-size: 10pt; font-weight:normal;'>"
										+result[i].short+"</label>"
										+"<input type='text' id='S"+obj+i+"I'"
										+"style='overflow:visible;display:none;font-family: Arial;font-size: 10pt; font-weight:normal;'>";
					}
				
					//oCell = newRow.insertCell(-1); 
					//oCell.setAttribute("align","center");
					//oCell.innerHTML = "<label>"+result[i].groupnum+"</label>";

				}
				
			},
			"json");
			main = obj;
}

//增加了activeDate的功能 FWJ 2013-05-03
function activeData(pyid,index)
{
	var objTable = main+"Button";
	$.post("POAction.do?operate=activeData&operPage=data_checker_13&dataName="+main,
			{id:pyid},
			function(result)
			{
				if("true"==result)
				{
		//个人觉得激活该字段的时候不用成功提醒 FWJ 2013-05-02
		//			alert("Successful!");
					document.getElementById(objTable).click();
				}
				else
				{
					alert("Fail!");
					notEditData(index);
				}
			});	
}

function toEditData(index)
{
	if(main!='SkillLevel')
	{
		document.getElementById(main+index+"L").style.display = "none";
		document.getElementById(main+index+"I").value =document.getElementById(main+index+"L").innerHTML;
		document.getElementById(main+index+"I").style.display = "";
		document.getElementById(main+index+"X").style.display = "";
		document.getElementById(main+index+"M").style.display = "";
		document.getElementById(main+index+"D").style.display = "";

		document.getElementById(main+index+"A").style.display = "";//For testing 2013-05-02
	}
}
function notEditData(index)
{
	document.getElementById(main+index+"L").style.display = "";
	document.getElementById(main+index+"I").style.display = "none";
	document.getElementById(main+index+"X").style.display = "none";
	document.getElementById(main+index+"M").style.display = "none";
	document.getElementById(main+index+"D").style.display = "none";

	document.getElementById(main+index+"A").style.display = "none";//For testing 2013-05-02
	
	document.getElementById(main+index+"I").value="";
}
function modifyData(pyid,index)
{
	var url = "POAction.do?operate=modifyData&operPage=data_checker_13&dataName="+main;
	var pyname = document.getElementById(main+index+"I").value;
	$.post(url,{id:pyid,name:pyname},
		function(result)
		{
			if("true"==result)
			{
				alert("Successful!");
				document.getElementById(main+index+"L").innerHTML=pyname;
				notEditData(index);
			}
			else
			{
				alert("Fail!");
				notEditData(index);
			}
		});
}

function deleteData(pyid,index)
{
	var objTable = main+"Button";
	$.post("POAction.do?operate=deleteData&operPage=data_checker_13&dataName="+main,
			{id:pyid},
			function(result)
			{
				if("true"==result)
				{
					alert("Successful!");
					document.getElementById(objTable).click();
				}
				else
				{
					alert("Fail!");
					notEditData(index);
				}
			});
}

function addData(obj)
{
	var url = "POAction.do?operate=addData&operPage=data_checker_13&dataName="+main;
	var pyname = document.getElementById(main).value;
	var objTable = main+"Button";
	if('Product'==obj)
	{
		var pn = document.getElementById("ProductYearSelect");
		var pname = pn.options[pn.selectedIndex].value;
		var bs = document.getElementById("BusinessSelect");
		var bsname = bs.options[bs.selectedIndex].value;
		var pf = document.getElementById("PlatformSelect");
		var pfname = pf.options[pf.selectedIndex].value;
		var fo = document.getElementById("FWQEOwnerSelect");
		var foname = fo.options[fo.selectedIndex].value;
		$.post(url,
			{name:pyname,pname:pname,bsname:bsname,pfname:pfname,foname:foname},
			function(result)
			{
				if("true"==result)
				{
					alert("Successful!");
					document.getElementById(main).value = obj;
					document.getElementById(objTable).click();
				}
				else
				{
					alert("Fail!");
				}
			});
	}
	else{
	$.post(url,
			{name:pyname},
			function(result)
			{
				if("true"==result)
				{
					alert("Successful!");
					document.getElementById(main).value = obj;					
					document.getElementById(objTable).click();
				}
				else
				{
					alert("Fail!");
				}
			});
	}
}

function getItemList(obj)
{
	var poselect = document.getElementById(obj);
	var url ="POAction.do?operate=searchData&operPage=data_checker_13&dataName="+obj;
		$.post(url,
			function(result)
			{
				poselect.options.length=0;
		    	for(var i=0; i<result.length; i++)
				{
					var value = result[i].data;
					var option = new Option(value, value);
					poselect.add(option);
				}		    	
		  	},
		  	"json"
		  );
}

</script>
</body>
</html>