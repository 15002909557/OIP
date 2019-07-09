<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*, com.beyondsoft.expensesystem.util.*"%>
<%
	//得到数据库里已经有的details
	@SuppressWarnings("unchecked")
	List<ExpenseDetail> list = (List<ExpenseDetail>) request.getAttribute("detaillist");
	//得到下拉单列表 worktypeList, layerList
	@SuppressWarnings("unchecked")
	List<Map> worktypeList = (ArrayList<Map>) request.getAttribute("wlist");
	@SuppressWarnings("unchecked")
	List<String> testtypeList = (List<String>) request.getSession().getAttribute("testtypeList");
	@SuppressWarnings("unchecked")
	List<String> milestoneList = (List<String>) request.getSession().getAttribute("milestoneList");
	//Add the "Target Launch" by FWJ on 2013-03-06
	@SuppressWarnings("unchecked")
	List<String> targetlaunchList = (List<String>) request.getSession().getAttribute("targetlaunchList");
	//Add the "Budget" by FWJ on 2013-05-20
	@SuppressWarnings("unchecked")
	List<String> budgetList = (List<String>) request.getSession().getAttribute("budgetList");

	String projectid = (String) request.getAttribute("projectid");
	String date = (String) request.getAttribute("date");
	SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
	String islock = (String) request.getAttribute("islock");
	//hanxiaoyu01 2013-01-28
	String skilllevel = (String)request.getAttribute("skilllevel");
	String cmd = (String)request.getAttribute("cmd");
	
	System.out.println("islock in detail="+islock);
	System.out.println("cmd="+cmd);
	
	System.out.println("current get skill description is: "+DescriptionOfSkillLevel.descriptions.get(skilllevel));
%>
<html>
  <head>   
    <title>Details_<%=date%></title>
	<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
	<link rel="stylesheet" href="css/style_new.css" type="text/css">
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
	    	<td width="15%">Activity Type</td>
	    	<td width="8%">Target Milestone</td>
	    	<td width="8%">Target Launch</td>
	    	<td width="6%">Test Type</td>
	    	<td width="13%">Description of Skill Level</td>
	    	<td width="23%">Budget Tracking</td>
	    	<td width="5%">FW Version</td>
	    	<td width="4%">Workload</td>
	    	<td></td>
	    </tr>
	    <%
	    if(list!=null)
	    {
		    for(int i=0;i<list.size();i++)
		    {
	    %>
	    <tr class="tr_content" align='center' id="l<%=i %>">
	    	<td>
				<input name="worktypeName" type="text" id="worktypel<%=i %>" value="<%=list.get(i).getWorktype() %>" readonly='readonly' style="width:180px;" />
				<input type="hidden" name="ActivityType" id="acl<%=i %>" value="<%=list.get(i).getWorktypeid()%>" />
			</td>
	    	<td>
	    		<select id="milestonel<%=i %>" name="Milestone" style="width:80px;">
	    		
	    		<%if(list.get(i).getMilestoneName()!=null&&list.get(i).getMilestoneName()!="") {%>
	    		<!-- 防止数据在set name list中被删除之后，显示空白 -->
	    		<option value="<%= list.get(i).getMilestone() %>"><%= list.get(i).getMilestoneName() %></option>
	    		<%} %>
	    		<%
				for (int m = 0; m < milestoneList.size(); m += 3)
				{
					if(!milestoneList.get(m).equals(String.valueOf(list.get(i).getMilestone()))){
				%>
	    			<option value="<%=milestoneList.get(m) %>">
	    			<%=milestoneList.get(m+1) %>
	    			</option>
	    		<%
				}else if(list.get(i).getMilestoneName()==null||list.get(i).getMilestoneName()==""){
	    		%>
	    		<!-- 防止copy之后，页面空白 -->
	    		<option value="<%=milestoneList.get(m) %>"
	    		<%if(milestoneList.get(m).equals(String.valueOf(list.get(i).getMilestone()))) {%>selected='selected'<%} %>>
	    			<%=milestoneList.get(m+1) %>
	    		</option>
	    		<%} }%>
	    		</select>
	    	</td>
	    	<!-- Added by FWJ on 2013-03-07 -->
	    	<td>
	    		<select id="targetlaunchl<%=i %>" name="TargetLaunch" style="width:80px;">
	    		<%if(list.get(i).getTargetlaunch()!=null&&list.get(i).getTargetlaunch()!="") {%>
	    		<option value="<%= list.get(i).getTargetlaunchid() %>"><%= list.get(i).getTargetlaunch() %></option>
	    		<%} %>
	    		<%
				for (int k = 0; k < targetlaunchList.size(); k += 2)
				{
					if(!targetlaunchList.get(k).equals(String.valueOf(list.get(i).getTargetlaunchid()))){
				%>
	    			<option value="<%=targetlaunchList.get(k) %>">
	    			<%=targetlaunchList.get(k+1)%>
	    			</option>
	    		<%
				}else if(list.get(i).getTargetlaunch()==null||list.get(i).getTargetlaunch()==""){
	    		%>
	    		<option value="<%=targetlaunchList.get(k) %>"
	    		<%if(targetlaunchList.get(k).equals(String.valueOf(list.get(i).getTargetlaunchid()))) {%>selected='selected'<%} %>>
	    			<%=targetlaunchList.get(k+1)%>
	    		</option>
	    		<% }}%>
	    		</select>
	    	</td>
	    	<td>
	    		<select id="testtypel<%=i %>" name="TestType" style="width:60px;">
	    		<%if(list.get(i).getTesttypeName()!=null&&list.get(i).getTesttypeName()!="") {%>
		    	<option value='<%= list.get(i).getTesttype() %>'><%= list.get(i).getTesttypeName() %></option>
		    	<%} %>
		    	<%
				for (int t = 0; t < testtypeList.size(); t += 2)
				{
					if(!testtypeList.get(t).equals(String.valueOf(list.get(i).getTesttype()))){
				%>
		    		<option value="<%=testtypeList.get(t) %>">
		    		<%=testtypeList.get(t+1) %>
		    		</option>
				<%}else if(list.get(i).getTesttypeName()==null||list.get(i).getTesttypeName()==""){%>
					<option value="<%=testtypeList.get(t) %>"
		    		<%if(list.get(i).getTesttype()==Integer.parseInt(testtypeList.get(t))){ %>selected='selected'<%} %>>
		    		<%=testtypeList.get(t+1) %>
		    		</option>
				<%}}%>
				</select>
	    	
	    	</td>
	    	<!-- Description of Skill added by FWJ 2013-05-20 -->
	    	<td>
	    		<input type="text" name="descriptionofskill" id="descriptionofskilll<%=i %>" style="width:170px;text-align:center" 
	    			value = "<%=DescriptionOfSkillLevel.getDesc(list.get(i).getWorktype().toString(), skilllevel) %>"
	    			onfocus="this.select();" maxlength="50" <%if("0".equals(islock)) {%> readonly='readonly'<%} %>/>
	    	</td>
	    	<!-- Budget Tracking added by FWJ 2013-05-22 -->
	    	<td>
	    	<select id="budgetl<%=i %>" name="Budget" style="width:272px;">
	    	<!-- 防止以前的数据显示为空，设置判断为空的条件。其实可以设置sql的默认数据，但是尽量不要对数据库施加要求 -->
	    		
	    		<%if(list.get(i).getBudget()!=null&&list.get(i).getBudget()!="") {%>
		    	<option value='<%= list.get(i).getBudgetid() %>'><%= list.get(i).getBudget() %></option>
		    	<%}
	    		else
	    		{
	    		%>
	    		<option value="-1"></option>
	    		<%} %>
		    	<%
				for (int t = 0; t < budgetList.size(); t += 2)
				{
					if(!budgetList.get(t).equals(String.valueOf(list.get(i).getBudgetid()))){
					%>
		    		<option value="<%=budgetList.get(t) %>" 
		    		<%if((list.get(i).getBudget()==null||list.get(i).getBudget()=="")&&list.get(i).getBudgetid()==Integer.parseInt(budgetList.get(t))){ %>selected='selected'<%} %>>
		    		<%=budgetList.get(t+1) %>
		    		</option>
					<%}else if(list.get(i).getBudget()==null||list.get(i).getBudget()==""){%>
					<option value="<%=budgetList.get(t) %>" 
		    		<%
		    		if(list.get(i).getBudgetid()==Integer.parseInt(budgetList.get(t))){ %>selected='selected'<%} %>>
		    		<%=budgetList.get(t+1) %>
		    		</option>
				<%}}%>
				</select>
	    	</td>
	    	<td>
	    		<input type='text' name='firmware' id='firmwarel<%=i %>' value='<%=list.get(i).getFirmware()%>'
					title="It should be 4 single digits and 1 letter(e.g. 1123A). For non-printer devices, please simply add the last 5 characters."
					style='width:80px;' <%if("0".equals(islock)) {%> readonly='readOnly'<%} %> onkeyup="javascript:getCom(this);"/>
	    	</td>
	    	<td>
	    		<input type='text' name='hour' id='hourl<%=i %>' style='width:60px;' onkeydown="myKeyDown()" value='<%=list.get(i).getHour()%>'
					onfocus="this.select();" maxlength="10" <%if("0".equals(islock)) {%> readonly='readonly'<%} %> />
	    	</td>
	    	
	    	<td <% if("Tester".equals(skilllevel.trim())) {%>align="left"<%} %>>
	    	<input type='button' title="Delete" value='Reset' class="new_btn" style="margin-right:6px;" 
					<%if("0".equals(islock)) {%>disabled='disabled' <%} %> onclick="javascript:deleteRecord('l<%=i %>');" />
			<input cid="tr2<%=i %>" name="commsbutton" type="button" value="Comments" class="new_btn" style="margin-right:3px;" 
			       <%if("0".equals(islock)||!"".equals(list.get(i).getComm())) {%>disabled='disabled' <%} %> title="To add comments for the current row."/>	
			<label style="cursor:pointer;font-size:18px; color:red;" title="To add a new record row." onclick="addRow('l<%=i %>')">+</label>
			</td>
			
	       <!-- hanxiaoyu01 2013-01-29 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! !!!-->	
	       	
	    	</tr>
	        <tr class='tr_content' align='center'
	        <%if("".equals(list.get(i).getComm())){ %>
	         style="display: none;"
	        <%} %>
	        id="tr2<%=i %>">
	    	 <td colspan="9">
	    	 <input name='comms' class="tr2<%=i %>"  id="commsl<%=i %>" type='text' style='width:1200px; border:1px #e9e9e9 solid;' 
	    	  maxlength="200" title="Less than 200 char can be input here." value="<%=list.get(i).getComm()%>"/>
	    	 </td>
	    	 </tr>
	    <%
		    }
	    }
	    %>
	    <%
	if(worktypeList!=null)
	{
	    for(int j=0;j<worktypeList.size();j++)
	    {
	    %>
	     <tr class="tr_content" align='center' id="w<%=j %>">
	    	<td>
				<input  name="worktypeName" type="text" id="worktypew<%=j %>" value="<%=worktypeList.get(j).get("worktype") %>" readonly='readonly' style="width:180px;" />
				<input type="hidden" name="ActivityType" id="acw<%=j %>" value="<%=worktypeList.get(j).get("wid")%>" />
			</td>
	    	<td>
	    		<select id="milestonew<%=j %>" name="Milestone" style="width:80px;" class="ml" onchange="javaxcript:autoSelect(this);">
	    		<%
				for (int m = 0; m < milestoneList.size(); m += 3)
				{
				%>
	    			<option value="<%=milestoneList.get(m) %>"
	    			<% if(milestoneList.get(m+1).equals("N/A")) {%>
	    			selected="selected"
	    			<%} %>
	    			>
	    			<%=milestoneList.get(m+1) %>
	    			</option>
	    		<%
				}
	    		%>
	    		</select>
	    	</td>
	    	<!-- Add the Target Launch by FWJ on 2013-03-07 改变选项框的宽度-->
	    	<td>
	    		<select id="targetlaunchw<%=j %>" name="TargetLaunch" style="width:80px;" class="tl" onchange="javaxcript:autoSelect(this);">
	    		<%
	    		
				for (int k = 0; k < targetlaunchList.size(); k += 2)
				{
				%>
	    			<option value="<%=targetlaunchList.get(k) %>"
	    			<% 
	    			// change by dancy 2013/04/01
	    			if((skilllevel.indexOf("III")>=0)||(skilllevel.indexOf("Project Manager")>=0))
	    			{	    				
	    				if(targetlaunchList.get(k+1).equals("NA")) {
	    			%>
	    			selected="selected"
	    			<%
	    				}
	    			}
	    			else
	    			{
	    				if(targetlaunchList.get(k+1).equals("VR1"))
	    				{
	    			%>
	    			selected="selected"
	    			<%
	    				} 
	    			}
	    			%>>
	    			<%=targetlaunchList.get(k+1) %>
	    			</option>
	    		<%
				}
	    		%>
	    		</select>
	    	</td>
	    	<td>
	    	<select id="testtypew<%=j %>" name="TestType" style="width:60px;" class="tt" onchange="javaxcript:autoSelect(this);">
	    	<%
			for (int i = 0; i < testtypeList.size(); i += 2)
			{
			%>
	    		<option value="<%=testtypeList.get(i) %>">
	    		<%=testtypeList.get(i+1) %>
	    		</option>
			<%
			}
			%>
			</select>
	    	</td>
	    	<!-- Description of Skill added by FWJ 2013-05-20 -->
	    	<td>
	    		<input type="text" name="descriptionofskill" id="descriptionofskillw<%=j %>" style="width:170px; text-align:center" 
	    			value="<%=DescriptionOfSkillLevel.getDesc(worktypeList.get(j).get("worktype").toString(), skilllevel) %>"
	    			onfocus="this.select();" maxlength="50" title="At most 50 characters can be input." <%if("0".equals(islock)) {%> readonly='readonly'<%} %>
	    			onkeyup="javascript:unbindEvent(this,'w<%=j %>');"/>
	    	</td>
	    	<!-- Budget Tracking added by FWJ 2013-05-22 -->
	    	<td>
	    	<select id="budgetw<%=j %>" name="Budget" style="width:272px;" class="bb" onchange="javaxcript:autoSelect(this);">
	    	<%
			for (int i = 0; i < budgetList.size(); i += 2)
			{
			%>
	    		<option value="<%=budgetList.get(i) %>" <%if(budgetList.get(i+1).indexOf("Original Quotation")>=0){%>selected=selected<%} %>>
	    		<%=budgetList.get(i+1) %>
	    		</option>
			<%
			}
			%>
			</select>
	    	
	    	</td>
	    	<td>
	    		<input type='text' name='firmware' id='firmwarew<%=j %>' style='width:80px;' class="fw" 
	    		<%
	    		// change by dancy 2013/04/01
		    	if(skilllevel.indexOf("Tester")<0){
	    		//if(!"Tester".equals(skilllevel.trim())){ 
	    		%> value=""<%} %>
					<%if("0".equals(islock)) {%> readonly='readonly' <%} %> onkeyup="javascript:autoFill(this)"/>	    	
	    	</td>
	    	
	    	<td>
	    		<input type='text' name='hour' id='hourw<%=j %>' style='width:60px;' onkeydown="myKeyDown()" value="" 
					onfocus="this.select();" maxlength="10" <%if("0".equals(islock)) {%> readonly='readonly'<%} %> 
					onkeyup="javascript:unbindEvent(this,'w<%=j %>');"/>
	    	</td>
	    	
	    	<td <% if("Tester".equals(skilllevel.trim())) {%>align="left"<%} %>>
	    		<input type='button' title="Delete" value='Reset' class="new_btn" style="margin-right:6px;" 
					<%if("0".equals(islock)) {%>disabled='disabled' <%} %> onclick="javascript:deleteRecord('w<%=j %>');" />

	    	 <input cid="tr<%=j %>" name="commsbutton" type="button" value="Comments" class="new_btn" style="margin-right:3px;" 
			       <%if("0".equals(islock)) {%>disabled='disabled' <%} %>  title="To add comments for the current row."/>
			       
	    	<label style="cursor:pointer;font-size:18px; color:red;" title="To add a new record row." onclick="addRow('w<%=j %>')">+</label> 
	    	</td>
	    </tr>
	    	<tr class='tr_content' align='center' id="tr<%=j %>" style="display: none;">
	    	<td colspan="9">
	    	 <input name='comms'  class="tr<%=j %>" id="commsw<%=j %>" type='text' 
	    	 style='width:1200px; border:1px #e9e9e9 solid;' value="" maxlength="200"
	    	 title="Less than 200 char can be input here."/>
	    	</td>
	    	</tr>
	    <%
	    }
	}
	    %>

    </table>
              <%if (null != cmd) {
				%>
				<label><%=cmd%></label>
				<%
					}
				%>
    <div class="btnsLine" align="center">
     <%
  // change by dancy 2013/04/01
  // change by FWJ 2013/09/03
 //	 if(skilllevel.indexOf("Tester")>=0){
     //if("Tester".equals(skilllevel.trim())){ 
     %>
      <input name="copy" type="button" class="btnStyle" id="btnCopy" <%if("0".equals(islock)) {%> disabled='disabled' <%} %> value=" Copy  ctrl+c" style="margin-right:50px; width: 100px;"/>
	  <input name="Paste" type="button" class="btnStyle" <%if("0".equals(islock)) {%> disabled='disabled' <%} %> value=" Paste" onclick='javascript: paste();' style="margin-right:50px;width: 100px;"/>		
     <%//} %>
      <input name="Save" type="button" class="btnStyle1" <%if("0".equals(islock)) {%> disabled='disabled' <%} %> value=" Save ctrl+s" onclick="javascript:save();" />
    </div>
    <input type="hidden" name="d_skillLevel" value="<%=skilllevel %>"/>
    </form>
  </body>
  
<script language="javascript">
//hanxiaoyu01 2013-01-29 Ctrl+S 自动保存
document.onkeydown = fnPortfolioKeyCheck;
function fnPortfolioKeyCheck(evt)
{
   
    evt = (evt) ? evt : window.event;
	<% 
	//iflock is true then can not paste copy or save
	System.out.println("skilllevel="+skilllevel);
	//FWJ 2013-09-03
//	if(((islock!=null&&islock.equals("1"))||islock==null)&&skilllevel.indexOf("Tester")>=0)
	if((islock!=null&&islock.equals("1"))||islock==null)
	{
	%>
	    if (evt.ctrlKey)
	    {    
	        if (83 == evt.keyCode)
	        { 
	          save();
	        }
	        else if(67 == evt.keyCode)
	        {
	          copy();
	        }
	        else if(86 == evt.keyCode)
	        {
	          //paste();
	        }
	    }
	<%
	}
	%>
}

function autoSelect(obj)
{
	//自动填写其他hour为空的milestone和testtype
	var value = $(obj).val()
	var className = $(obj).attr("class");
	$("."+className+" option[value='"+value+"']").attr("selected","selected");
}
function autoFill(obj)
{
	getCom(obj);
	//自动填写其他hour为空的fw vision
	var value = $(obj).val()
	var className = $(obj).attr("class");
	$("."+className).val(value);
}

function unbindEvent(obj,tag)
{
	if($(obj).val()!='0')
	{
		//改变对应行的firmware的className，这样其他hour未填写的firmware改变不会影响已经填写完毕的firmware
		$("#firmware"+tag).attr("class","fw2");
		//去掉对应行的firmware的onkeyup事件，这样本行firmware的onkeyup不会影响其他的
		$("#firmware"+tag).attr("onkeyup","")

		$("#testtype"+tag).attr("class","tt2")
		$("#testtype"+tag).attr("onchange","")

		$("#milestone"+tag).attr("class","ml2")
		$("#milestone"+tag).attr("onchange","")
		
		$("#targetlaunch"+tag).attr("class","tl2");
		$("#targetlaunch"+tag).attr("onchange","");

		$("#budget"+tag).attr("class","bb2");
		$("#budget"+tag).attr("onchange","");
	}
}

function deleteRecord(tag)
{
	//清空hour
	$("#hour"+tag).val("");
	//初始化FW Vision = NA  change by dancy 2013/04/01
	 <%if(skilllevel.indexOf("Tester")<0){ %>
		$("#firmware"+tag).val("");
	<% } else {%>
		$("#firmware"+tag).val("");
	<% }%>
	//初始化targetlaunch Added by FWJ on 2013-03-07
	 <%if((skilllevel.indexOf("Project Manager")>=0)||(skilllevel.indexOf("III")>=0)){ %>
		$("#targetlaunch"+tag+" option[text='NA']").attr("selected","selected");
	<% } else {%>
		$("#targetlaunch"+tag+" option[text='VR1']").attr("selected","selected");
	<% }%>
	//清空test type
	$("#testtype"+tag+" option[value='-1']").attr("selected","selected");
	//清空budget tracking FWJ 2013-05-22
	$("#budget"+tag+" option[value='1']").attr("selected","selected");
	//初始化milestone
	$("#milestone"+tag+" option[text='NA']").attr("selected","selected");
	//清空评论comms
	$("#comms"+tag).val("");
	//初始化description of skill FWJ 2013-05-22
	<%if(skilllevel.indexOf("Tester")>=0){ %>
	$("#descriptionofskill"+tag).val("Tester");
	<% }else{%>
		if($("#worktype"+tag).val().indexOf("Test Lead")>=0)
			{
				$("#descriptionofskill"+tag).val("Test Lead");
			}else
				{
					$("#descriptionofskill"+tag).val("Test Developer");
				}
	<%}%>
	
}

function closeDiv(divId)
{ 
	document.getElementById(divId).style.display = 'none'; 
} 
function displayDiv(divId)
{ 
	document.getElementById(divId).style.display = 'block'; 
}
function save()
{	
	//判断父页面和当前页面的时间是否相同
	/*var overwrite = "true";
	displayDiv('aaaa');
	var inputid = window.opener.document.getElementById("inputid").value;*/
	console.log("save");
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=saveExpenseDetails&operPage=data_checker_03_details_submit&inputid="+inputid+"&overwrite="+overwrite;//by collie 0506 不一致的数据可以选择直接覆盖
	form.submit();
}
//用于限制用户输入 author=longzhe
function myKeyDown()
{
    var k=window.event.keyCode;   
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
    	for(i=0;i<arr.length;i++)
        {
			r=arr[i].search(y);
			if(r==0)
			{
				x.value=arr[i];
				if(x.createTextRange)
				{
					var rg=x.createTextRange();
					rg.moveEnd("character",arr[i].length);
					rg.moveStart("character",y.length);
					rg.select();
				}
				else 
				{//非IE浏览器
	    			obj.setSelectionRange(y.length, arr[i].length);
	   				obj.focus();
	   			}
				return;	
			}
   	 	}
		return(r);
	}
}


//hanxiaoyu01 2013-01-28

$("input[name='commsbutton']").live("click",function(){
 var cid=$(this).attr("cid");//造个对象存Id
 $("#"+cid).attr("class","tr_content");
 $("#"+cid).show();
 $("."+cid).focus();
});

function addRow(id)
{ 
	var vv = $("#worktype"+id).val();
	var vd = $("#ac"+id).val();
	var tr=$("#"+id);	 
	var table=$(tr).parents("table");
	var le=$(table).find("tr").length;
	var nid=id+le;
	var tr1=$("<tr class='tr_content' align='center' id='"+nid+"' style='background-color: #d6d6d6;'></tr>");
	var td1=$("<td><input name='worktypeName' id='worktypel"+nid+"' type='text' value='"+vv+"' readonly='readonly' style='width:180px;' /><input type='hidden' id='acl"+nid+"' name='ActivityType' value='"+vd+"'/></td>");
	var str="";
	 <%for (int m = 0; m < milestoneList.size(); m += 3){%>
	 str = str + "<option value='<%=milestoneList.get(m) %>'"
	<% if(milestoneList.get(m+1).equals("N/A")) {%>str = str +"selected='selected'"<%}%>
	 str = str+"><%=milestoneList.get(m+1) %></option>";
	 <%}%>
	 var td2=$("<td><select id='milestonel"+nid+"' name='Milestone' style='width:80px;'>"+str+"</select></td>");
	 var str1 = "";
	 <%for (int k = 0; k < targetlaunchList.size(); k += 2){%>
	 str1 = str1+"<option value='<%=targetlaunchList.get(k) %>' "
	 <% if(targetlaunchList.get(k+1).equals("N/A")) {%> str1 =str1 + "selected='selected'"; <%}%>
	  str1=str1 + "><%=targetlaunchList.get(k+1) %></option>";
	  <%}%>
	  var td3=$("<td><select id='targetlaunchl"+nid+"' name='TargetLaunch' style='width:80px;'>"+str1+"</select></td>");
	 
	 var str2="";
	 <%for (int t = 0; t < testtypeList.size(); t += 2){%>
	  str2=str2+"<option value='<%=testtypeList.get(t) %>'><%=testtypeList.get(t+1) %></option>"
	<%}%>
	 var td4=$("<td><select id='testtypel"+nid+"' name='TestType' style='width:80px;'>"+str2+"</select></td>");
	// var str5=$("#firmware"+id).val();
	 var td5=$("<td><input type='text' name='firmware' id='firmwarel"+nid+" title='It should be 4 single digits and 1 letter(e.g. 1123A). For non-printer devices, please simply add the last 5 characters.' style='width:80px;'/></td>");
	
	 var td6=$("<td><input type='text' name='hour' id='hourl"+nid+"' style='width:60px;' onkeydown='myKeyDown()' onfocus='this.select();' maxlength='10'  /></td>");
	 
	 var td7=$("<td align='left'></td>"); 

	 var str8=$("#descriptionofskill"+id).val();
 	 var td8=$("<td><input type='text' name='descriptionofskill' id='descriptionofskilll"+nid+"'style='width:100px;text-align:center' title='At most 50 characters can be input.' value='" +str8+"' onkeydown='myKeyDown()' onfocus='this.select();' maxlength='50'  /></td>");
		
	 var str9=""
		 
 	<%
		for (int i = 0; i < budgetList.size(); i += 2)
		{
		%>
 		str9=str9+"<option value='<%=budgetList.get(i) %>'" 
 		<%if(budgetList.get(i+1).indexOf("Original Quotation")>=0){%>
 		str9=str9+"selected='selected'"
 		<%} %>
 		str9=str9+"><%=budgetList.get(i+1) %></option>"
		<%
		}
		%>
	 var td9=$("<td><select id='budgetw+"+nid+"' name='Budget' style='width:272px;' class='bb' onchange='javaxcript:autoSelect(this);'>"+str9+"</select></td>");
	 
	 var input1=$("<input type='button' title='Delete' value='Reset' class='new_btn' style='margin-right:6px;' onclick='javascript:deleteRecord('l"+nid+"');' />");
	 var input2=$("<input cid='tr2"+nid+"' name='commsbutton' type='button' value='Comments' class='new_btn' style='margin-right:3px;' title='To add comments for the current row.' />	");
	 
	 $(td7).append(input1);
	 $(td7).append(input2);
	 $(tr1).append(td1);
	 $(tr1).append(td2);
	 $(tr1).append(td3);
	 $(tr1).append(td4);
	 $(tr1).append(td8);
	 $(tr1).append(td9);
	 $(tr1).append(td5);
	 $(tr1).append(td6);
	 $(tr1).append(td7);
	 $(tr).next().after(tr1);
	  
	 var tr2=$("<tr class='tr_content' align='center' id='tr2"+nid+"' style='display: none;' style='background-color: #d6d6d6;'></tr>");
	 var td7=$("<td colspan='9'></td>");
	 var input3=$("<input name='comms' class='tr2"+nid+"' id='commsl"+nid+"' type='text' style='width:1200px; border:1px #e9e9e9 solid;' maxlength='200' title='Less than 200 chars can be input here.' value=''/>");
	 $(td7).append(input3);
	 $(tr2).append(td7);
	 $(tr1).after(tr2);
}

$("#btnCopy").click(function()
{
	copy();
});
	
function copy()
{
	var ui = '<%=sysUser.getUI()%>';	
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=copyDetails&operPage=data_checker_03_details2&ui="+ui;
	form.submit();
}

function paste()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=pasteDetails&operPage=data_checker_03_details2";
	form.submit();

}

</script>

</html>
