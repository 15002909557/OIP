<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%
	//得到数据库里已经有的details
	@SuppressWarnings("unchecked")
	List<ExpenseDetail> list = (List<ExpenseDetail>) request.getAttribute("detaillist");
	//得到下拉单列表 worktypeList, layerList
	@SuppressWarnings("unchecked")
	List<String> worktypeList = (List<String>) request.getSession().getAttribute("worktypeList");
	@SuppressWarnings("unchecked")
	List<String> testtypeList = (List<String>) request.getSession().getAttribute("testtypeList");
	@SuppressWarnings("unchecked")
	List<String> milestoneList = (List<String>) request.getSession().getAttribute("milestoneList");
	System.out.println("milestone length="+milestoneList.size());

	String projectid = (String) request.getAttribute("projectid");
	String date = (String) request.getAttribute("date");
	String hour = (String) request.getAttribute("hour");

	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
	//copyUI 用于在paste时 判断UI是否一致
	String copyUI = (String) request.getSession().getAttribute("copyUI");

	String cmd = "";
	cmd = (String) request.getAttribute("cmd");
	String islock = (String) request.getAttribute("islock");
%>
<html>
	<head>
		<base target="_self">
		<title>Details_<%=date%></title>
		<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
		<link rel="stylesheet" href="css/style_new.css" type="text/css">
		<style type="text/css">
		
		.input
		{
			height:20px;
			line-height:20px;
			border: 1px solid #ccc;
			background:url(A3.jpg) no-repeat -150px 0px;
			font-family:'Arial';
			text-align:center;
		}
		.sub
		{
			position: absolute;
			top: 25px;
			left: 0;
			background:white;
			display: none;
			height:120px;
			overflow-y:scroll;
			overflow-x:hidden;
			scrollbar-face-color:#FFFFFF;
			scrollbar-highlight-color:#9fcde7;
			scrollbar-3dlight-color:#eef2fb;
			scrollbar-darkshadow-color:#eef2fb;
			scrollbar-shadow-color:#9fcde7;
			scrollbar-arrow-color:#9fcde7;
			scrollbar-track-color:#f9f9f9;
			z-index:20;
		}
		.sub ul
		{
			margin: 0;
			padding: 0;
			list-style: none;
		}
		.sub ul li
		{
			padding: 0 5px;
			line-height: 24px;
			background:#f9f9f9;
			border-bottom: 1px dotted #ccc;
			border-left: 1px dotted #ccc;
			/*border-right: 1px dotted #ccc;*/
			text-align:left;
		}
		input
		{
			border:1px #ccc solid;
			background:white;
			height:21px;
			line-height:21px;
		}
	</style>

<script type="text/javascript"><!-- 
var islock = '<%=islock%>';
var x=0,y=0,w=0,h=0;
$(document).ready(function()
{
	prepareSelect();
	//add a row for table
	var ui = '<%=sysUser.getUI()%>';
	var i=0;
	//hanxiaoyu01 2012-12-19 增加test type下拉表
	$("#btnAdd").click(function()
	{
		i++;
		var row = $("<tr class='tr_content' align='center'></tr>");
		var row2 = $("<tr class='tr_content' align='center' onmouseover='javascript:MouseOver(this);' onmouseout='javascript:MouseOut(this);'></tr>");
		var cc = "<td><div class='selectbox'><input type='text' id='selaa"+i+"' class='input' name='txtActivityType' readonly='readonly'/>"
				+"<input type='hidden' id='selaa"+i+"hide' name='ActivityType'/>"
				+"<div id='selaa"+i+"list' class='sub'><ul>"
				<%
				for (int i = 0; i < worktypeList.size(); i += 3)
				{
				%>
					+"<li id='a<%=worktypeList.get(i)%>'><%=worktypeList.get(i + 1)%></li>"
				<%			
				}
				%>
				+"</ul></div></div></td>";	
		if(ui=='1')
		{		
		cc = cc+"<td><div class='selectbox'><input type='text' id='selab"+i+"' class='input' name='txtMilestone' readonly='readonly'/>"
			+"<input type='hidden' id='selab"+i+"hide' name='Milestone'/>"
			+"<div id='selab"+i+"list' class='sub'><ul>"
				<%
				for (int j = 0; j < milestoneList.size(); j += 3)
				{
				%>
					+"<li id='b<%=milestoneList.get(j)%>'><%=milestoneList.get(j + 1)%></li>"
				<%
				}
				%>
			+"</ul></div></div></td>"
			// hanxiaoyu01 2012-12-19 把Test Type 加到ui==1的里面去
			+"<td><div class='selectbox'><input type='text' id='selad"+i+"' class='input' name='txtTestType' readonly='readonly'/>"
			+"<input type='hidden' id='selad"+i+"hide' name='TestType'/>"
			+"<div id='selad"+i+"list' class='sub'><ul>"							
				<%
				for (int i = 0; i < testtypeList.size(); i += 2)
				{
				%>
				+"<li id='d<%=testtypeList.get(i)%>'><%=testtypeList.get(i + 1)%></li>"
				<%
				}
				%>
			+"</ul></div></div></td>"
			
			+"<td><input type='text' name='firmware' id='firmware"+i+"' title='It should be 4 single digits and 1 letter(e.g. 1123A). For non-printer devices, please simply add the last 5 characters.'"
			+"style='width:60px;' <%if("0".equals(islock)) {%> readonly='readOnly'<%} %> onKeyUp='javascript:getCom(this);'/></td>";				
	
		}
	    
		
		
		cc = cc + "<td><input type='text' name='hour' id='hour' style='width:60px;' onkeydown='myKeyDown()'" 
			+"onfocus='this.select();'<%if("0".equals(islock)) {%> readonly='readonly'<%} %> /></td>";
		var cc2 = "<td  <%if(sysUser.getUI()==1) {%>colspan='6' <%} else if(sysUser.getUI()==2){ %> colspan='3'<%} %>><input type='text' name='comm' id='comm' class='comStyle' maxlength='50'value='comments' />";		
		cc2 = cc2 + "<input type='button' class='btnDel' style='margin-right:6px;' value='Delete'"
			+" title='Delete'<%if("0".equals(islock)) {%>disabled='disabled' <%} %>/></td>";
		var cell=$(cc);		
		row.append(cell);
		$("#tblGrid").append(row);
		var cell2=$(cc2);		
		row2.append(cell2);
		$("#tblGrid").append(row2);	
		prepareSelect();
	});
	
	$("#btnCopy").click(function()
	{
		copy();
	});
});

function prepareSelect()
{
	$(".input").each(function(event)
	{
            $(this).click(function(e)
			{
				$(".sub").hide();
				var x1 = $(this).offset().left;
				var y1 = $(this).offset().top;
				var w1 = $(this).width();
				var tid = "#"+this.id;
				var sub = tid+"list";
				var hid = tid+"hide";
				var subl = sub+" li";
				var subc = tid+"CD";
				
				x=x1;
				y=y1;
				w=w1;
				h=$(this).height();
				
				var subc = "."+this.id+"CD";
				$(sub).css("width",w1+5);
				$(sub).css("left",x1);
				$(sub).css("top",y1+25);
				$(sub).show();
				$(subc).css('background-color','#ffe6d1');
				$(subl).mouseover(function()
				{
					$(this).css('background-color','gray');
				});
				$(subl).mouseout(function()
				{
					$(this).css('background-color','white');
					$(subc).css('background-color','#ffe6d1');
				});
				$(subl).click(function()
				{
					$(tid).val($(this).text());
					var gv = this.id;
					var fv= gv.substring(1,gv.length);
					$(hid).val(fv);					
					$(sub).hide();
				});
            });
        });

	//delete a row from table
	$(".btnDel").each(function(event){
			$(this).click(function()
			{
				var tr = $(this).parent().parent();
				var rw =tr.index();	
				if(rw>0)
				{			
					$("#tblGrid tr").eq(rw).remove();
					$("#tblGrid tr").eq(rw-1).remove();
				}
			});
		});	
		$(".comStyle").each(function(event)
		{
			$(this).mouseover(function()
			{
				if(this.value=='comments')
				{
					this.value='';
					this.focus();
				}
			});
			$(this).mouseout(function()
			{
				if(this.value=='')
				{
					this.value='comments';
					this.blur();
				}
			});
		});		
}
//实时取得鼠标点击位置，每次点击判断前一个操作过的下拉框时候为展开状态，若是展开则将其隐藏
$(document).click(function(e)
{
	if(e.pageX<x||e.pageX>x+w||e.pageY<y||e.pageY>y+h)
	{
		$(".sub").hide();
	}
});
function closeDiv(divId){ 
document.getElementById(divId).style.display = 'none'; 
} 
function displayDiv(divId){ 
document.getElementById(divId).style.display = 'block'; 
} 
--></script>
<script language="javascript">

   
     
   //deletes the specified row from the table  
   function removeRow(src)  
   {   
    var oRow = src.parentNode.parentNode;
    document.all("tblGrid").deleteRow(oRow.rowIndex-1); 
    document.all("tblGrid").deleteRow(oRow.rowIndex); 
   }  
   
function MouseOver(obj)
{
	$(obj).css('background-color','#ffe6d1');
}
function MouseOut(obj)
{
	$(obj).css('background-color','#ffffff');
}
    
  </script>
	</head>
	<body>
		<input type="text" style="display: none;" name="tempwidth" id="tempwidth" value="test">
		<div class="tanchuang_wrap" id="aaaa">
				<div class="lightbox"></div>
				<div class="tanchuang_neirong">
					<p>
						saving...
				</div>
		</div>

			<form action="DataCheckerAction.do?operate=saveExpenseDetails&operPage=data_checker_03_details_submit" method="post">
				<input type="hidden" name="projectId" id="projectId" value='<%=projectid%>' />
				<input type="hidden" name="date" id="date" value='<%=date%>' />
				<table border="0" cellpadding="3" cellspacing="1" class="tabbg" align="center" width="98%" id="tblGrid">
				<tr class="tr_title2">
				<%
				if (sysUser.getUI() == 1) 
				{
				%>
				<td>ActivityType</td>
				<td>Target Milestone</td>
				<td>Test Type</td>
				<td>FW Version</td>
				<%
				} 
				else if(sysUser.getUI() == 2)
				{
				%>
				<td>WorkType</td>
				<td>TestType</td>
				<%
				}
				 %>
				<td>Workload</td>
				</tr>
				<!--  如果details已经有数据 -->
				<%
				if (null != list && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
				%>
				
				<tr class="tr_content" align='center'>
				<td>
					<div class="selectbox">
						<input type="text" id="sela<%=i %>" class="input" name="txtActivityType" readonly="readonly" value='<%=list.get(i).getWorktype() %>'/>
						<input type="hidden" id="sela<%=i %>hide" name="ActivityType" value='<%=list.get(i).getWorktypeid() %>'/>
						<div id="sela<%=i %>list" class="sub">
							<ul>
							<%
							for (int j = 0; j < worktypeList.size(); j += 3) {
							%>
								<li id='a<%=worktypeList.get(j)%>' <% if(worktypeList.get(j).equals(String.valueOf(list.get(i).getWorktypeid()))){ %>
								 class="sela<%=i %>CD" style="background-color:#ffe6d1;"<%} %>>
								<%=worktypeList.get(j + 1)%>
								</li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>				
				<%
				if (sysUser.getUI() == 1) 
				{
				%>
				<td>
				<div class="selectbox">
						<input type="text" id="selb<%=i %>" class="input" name="txtMilestone" readonly="readonly" value='<%=list.get(i).getMilestoneName() %>'/>
						<input type="hidden" id="selb<%=i %>hide" name="Milestone" value='<%=list.get(i).getMilestone() %>'/>
						<div id="selb<%=i %>list" class="sub">
							<ul>
							<%
							for (int j = 0; j < milestoneList.size(); j += 3)
							{
							%>
								<li id='b<%=milestoneList.get(j)%>' <%if(milestoneList.get(j).equals(String.valueOf(list.get(i).getMilestone()))){ %> 
								class="selb<%=i %>CD" style="background-color:#ffe6d1;"<% }%>>
								<%=milestoneList.get(j + 1)%>
								</li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>
				<!-- hanxiaoyu01 2012-12-18 不管UI为多少，都显示Test Type-->
				<td>
				 <div class="selectbox">
						<input type="text" id="seld<%=i %>" class="input" name="txtTestType" readonly="readonly" value='<%=list.get(i).getTesttypeName() %>'/>
						<input type="hidden" id="seld<%=i %>hide" name="TestType" value='<%=list.get(i).getTesttype() %>'/>
						<div id="seld<%=i %>list" class="sub">
							<ul>
							<%
							for (int j = 0; j < testtypeList.size(); j += 2)
							{
							%>
							<li id='d<%=testtypeList.get(j)%>'<%if(testtypeList.get(j).equals(String.valueOf(list.get(i).getTesttype()))){ %>
							class="seld<%=i %>CD" style="background-color:#ffe6d1;" <%} %>>
							<%=testtypeList.get(j + 1)%>
							</li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>
				<td>
				<input type='text' name='firmware' id='firmware' value='<%=list.get(i).getFirmware()%>'
				title="It should be 4 single digits and 1 letter(e.g. 1123A). For non-printer devices, please simply add the last 5 characters."
				style='width:60px;' <%if("0".equals(islock)) {%> readonly='readOnly'<%} %> onkeyup="javascript:getCom(this);"/>
				</td>
				<%
				}
				else if(sysUser.getUI() == 2) 
				{
				%>
				<td>
				<div class="selectbox">
						<input type="text" id="seld<%=i %>" class="input" name="txtTestType" readonly="readonly" value='<%=list.get(i).getTesttypeName() %>'/>
						<input type="hidden" id="seld<%=i %>hide" name="TestType" value='<%=list.get(i).getTesttype() %>'/>
						<div id="seld<%=i %>list" class="sub">
							<ul>
							<%
							for (int j = 0; j < testtypeList.size(); j += 2)
							{
							%>
							<li id='d<%=testtypeList.get(j)%>'<%if(testtypeList.get(j).equals(String.valueOf(list.get(i).getTesttype()))){ %>
							class="seld<%=i %>CD" style="background-color:#ffe6d1;" <%} %>>
							<%=testtypeList.get(j + 1)%>
							</li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>
				<%
				} 
				%>
				<td>
					<input type='text' name='hour' id='hour' style='width:60px;' onkeydown="myKeyDown()" value='<%=list.get(i).getHour()%>'
					onfocus="this.select();" maxlength="10" <%if("0".equals(islock)) {%> readonly='readonly'<%} %> />
				</td>
				</tr>
				<tr class="tr_content" onmouseover="javascript:MouseOver(this);" onmouseout="javascript:MouseOut(this);">
				<td <%if(sysUser.getUI()==1) {%>colspan="6" <%} else if(sysUser.getUI()==2){ %> colspan="3"<%} %>>
					<input type='text' name='comm' id='comm' class='comStyle' maxlength='50'
					<%if("".equals(list.get(i).getComm())){%>value='comments' <% }else { %>value='<%=list.get(i).getComm() %>'<%} %> <%if("0".equals(islock)) {%> readonly='readonly'<%} %> />
					<input type='button' title="Delete" value='Delete' class="btnDel" style="margin-right:6px;" 
					<%if("0".equals(islock)) {%>disabled='disabled' <%} %> />
				</td>
				</tr>
				
				<%
					}
				}
				else { %>
				
				<tr class="tr_content" align='center'>
				<td>
					<div class="selectbox">
						<input type="text" id="sel" class="input" name="txtActivityType" readonly="readonly"/>
						<input type="hidden" id="selhide" name="ActivityType"/>
						<div id="sellist" class="sub">
							<ul>
							<%
							for (int i = 0; i < worktypeList.size(); i += 3) {
							%>
								<li id='a<%=worktypeList.get(i)%>'><%=worktypeList.get(i + 1)%></li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>				
				<%
				if (sysUser.getUI() == 1) 
				{
				%>
				<td>
				<div class="selectbox">
						<input type="text" id="sel2" class="input" name="txtMilestone" readonly="readonly"/>
						<input type="hidden" id="sel2hide" name="Milestone"/>
						<div id="sel2list" class="sub">
							<ul>
							<%
							for (int j = 0; j < milestoneList.size(); j += 3)
							{
							%>
								<li id='b<%=milestoneList.get(j)%>'><%=milestoneList.get(j + 1)%></li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>
				<!-- hanxiaoyu01 2012-12-18 增加test type属性 -->
				<td>
				<div class="selectbox">
						<input type="text" id="sel4" class="input" name="txtTestType"readonly="readonly"/>
						<input type="hidden" id="sel4hide" name="TestType"/>
						<div id="sel4list" class="sub">
							<ul>
							<%
							for (int i = 0; i < testtypeList.size(); i += 2)
							{
							%>
							<li id='d<%=testtypeList.get(i)%>'><%=testtypeList.get(i + 1)%></li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>
				<td>
				<input type='text' name='firmware' id='firmware' title="It should be 4 single digits and 1 letter(e.g. 1123A). For non-printer devices, please simply add the last 5 characters."
								 style='width:60px;' <%if("0".equals(islock)) {%> readonly='readonly' 
								<%} %> onkeyup="javascript:getCom(this);"/>
				</td>
				<%
				}
				else if(sysUser.getUI() == 2) 
				{
				%>
				<td>
				<div class="selectbox">
						<input type="text" id="sel4" class="input" name="txtTestType"readonly="readonly"/>
						<input type="hidden" id="sel4hide" name="TestType"/>
						<div id="sel4list" class="sub">
							<ul>
							<%
							for (int i = 0; i < testtypeList.size(); i += 3)
							{
							%>
							<li id='d<%=testtypeList.get(i)%>'>(<%=testtypeList.get(i + 1)%></li>
							<%
							}
							%>
							</ul>
						</div>
					</div>
				</td>
				<%
				} 
				%>
				<td>
					<input type='text' name='hour' id='hour' style='width:60px;' onkeydown="myKeyDown()" 
					onfocus="this.select();" maxlength="10" <%if("0".equals(islock)) {%> readonly='readonly'<%} %> />
				</td>
				</tr>
				<tr class="tr_content" onmouseover="javascript:MouseOver(this);" onmouseout="javascript:MouseOut(this);">
				<td <%if(sysUser.getUI()==1) {%>colspan="6" <%} else if(sysUser.getUI()==2){ %> colspan="3"<%} %>>
					<input type='text' name='comm' id='comm' class='comStyle' maxlength='50' value="comments" />
					<input type='button' title="Delete" value='Delete' class="btnDel" style="margin-right:6px;" 
					<%if("0".equals(islock)) {%>disabled='disabled' <%} %> />
				</td>
				</tr>
				<%} %>
				</table>
				<%
				if (null != cmd) {
				%>
				<label><%=cmd%></label>
				<%
					}
				%>
				<div class="btnsLine">
				<input name="add" type="button" value="Add Row" class="btnStyle" id="btnAdd" <%if("0".equals(islock)) {%> disabled="disabled" <%} %> style="margin-right:50px;"/>
				<input name="copy" type="button" class="btnStyle" id="btnCopy" <%if("0".equals(islock)) {%> disabled='disabled' <%} %> value=" Copy " style="margin-right:50px;"/>
				<input name="Save" type="button" class="btnStyle" <%if("0".equals(islock)) {%> disabled='disabled' <%} %> value=" Paste " onclick='javascript: paste();' style="margin-right:50px;"/>
				<input name="Save" type="button" class="btnStyle1" <%if("0".equals(islock)) {%> disabled='disabled' <%} %>	value=" Save " onclick='javascript: save();'/>
				</div>
			</form>

	
	</body>
<script language="javascript">
//@Dancy 2011-11-11 添加tr的onmouseover和onmouseout事件，改变tr背景颜色
	function mouseOver(obj)
	{
		obj.style.background="#9cc4e4"
	}
	
	function mouseOut(obj)
	{
		obj.style.background="white"
	}
//@end
	function scrollwidth(obj)
	{
	//判断ie版本
	  var X,V,N;
	  V=navigator.appVersion;
	  N=navigator.appName;
	  if(N=="Microsoft Internet Explorer")
	    X=parseFloat(V.substring(V.indexOf("MSIE")+5,V.lastIndexOf("Windows")));
	  else 
	  	X=parseFloat(V);
	  //alert(X);
	 	
		document.getElementById("tempwidth").value = obj.style.width;
		obj.style.width='';
		obj.style.overflow='visible';
		
		if(6==X)
	 	{
	 		obj.click();
	 	}
		
	}

function save()
{
	var ui = '<%=sysUser.getUI()%>';
	var verification = 1;
	//判断下拉单是否填写完整
	
		$(".selectitm").each(function(){
			if(""==$(this).val())
			{
				alert("Please select all the parameters!");
				verification = 0;
				return false;
			}
			});
		if(0==verification)
			return;
	
	var hours = new Array();
	hours = document.getElementsByName("hour");
	var totalhours = 0; //当前页面的总hour
	var hour = '<%=hour%>';
	//将所有时间相加，用于判断是否等于checker03页面上的时间
	for(var i=0;i<hours.length;i++)
	{
		totalhours=totalhours+parseFloat(hours[i].value);
	}
	//判断是否为数字类型
	if(isNaN(totalhours))
	{
		alert("Please enter all workload or delete the blank row");
		return false;
	}
	//判断父页面和当前页面的时间是否相同
	var overwrite = "true";
	displayDiv('aaaa');
	var inputid = window.opener.document.getElementById("inputid").value;
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=saveExpenseDetails&operPage=data_checker_03_details_submit&inputid="+inputid+"&overwrite="+overwrite;//by collie 0506 不一致的数据可以选择直接覆盖
	form.submit();
}
//用于限制用户输入 author=longzhe
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
function exit()
{
	window.close();
}
function copy()
{
	var ui = '<%=sysUser.getUI()%>';	
	var hours = new Array();
	hours = document.getElementsByName("hour");
	var totalhours = 0; //当前页面的总hour
	//将所有时间相加，用于判断是否等于checker03页面上的时间
	for(var i=0;i<hours.length;i++)
	{
		totalhours=totalhours+parseFloat(hours[i].value);
	}
	//判断是否为数字类型
	if(isNaN(totalhours))
	{
		alert("Please enter all workload or delete the blank row");
		return false;
	}
	
	displayDiv('aaaa');	
	var hour = '<%=hour%>'
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=copyDetails&operPage=data_checker_03_details&totalhour="+hour+"&ui="+ui;
	form.submit();
}
function paste()
{
	var hour = '<%=hour%>'
	var copyui = '<%=copyUI%>';
	var userui = '<%=sysUser.getUI()%>';
	
	if(copyui==userui)
	{
		var form = document.forms[0];
		form.action = "DataCheckerAction.do?operate=pasteDetails&operPage=data_checker_03_details&totalhour="+hour;
		form.submit();
	}
}

//@Dancy 2011-10-21 添加方法使Revision输入框中输入前面字母自动填入映射字符串
function getCom(x)
{
	var k = window.event.keyCode;
	var arr = new Array("EngFW");
	var i;
	if(k!=8)
	{
		var r;
		var y=x.value;
    	for(i=0;i<arr.length;i++){
			r=arr[i].search(y);
			if(r==0){
				x.value=arr[i];
				if(x.createTextRange){
					var rg=x.createTextRange();
					rg.moveEnd("character",arr[i].length);
					rg.moveStart("character",y.length);
					rg.select();
				}
			else {//非IE浏览器
    				obj.setSelectionRange(y.length, arr[i].length);
   					 obj.focus();
   				 }
			return;	
			}
   	 	 }
		return(r);
	}
}
</script>
</html>