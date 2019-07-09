<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"
	contentType="text/html; charset=GBK"%>
<%
	// project List
	@SuppressWarnings("unchecked")
	List<ExpenseData> Plist = (List<ExpenseData>) request.getAttribute("projectlist");
	// User List
	@SuppressWarnings("unchecked")
	List<SysUser> Userlist = (List<SysUser>) request.getAttribute("Userlist");
	//GroupName
	String gname = (String) request.getSession().getAttribute("gname");
	//GroupId
	String gid = (String) request.getSession().getAttribute("groupID");
	if(gid.equals("-1"))
	{
		gname = "All Data";
		request.getSession().setAttribute("gname",gname);
	}
	System.out.println("jsp display gname:"+gname);
	System.out.println("user list :"+Userlist.size());
	
	//dancy 2012-12-20
	String re = (String) request.getSession().getAttribute("modifyresult");
	System.out.println("modify project result is: "+re);
	
	int plistSize = Plist.size();
	int currentpage=1;
	String totalSize = (String) request.getAttribute("totalSize");
	String curPage = (String) request.getAttribute("currentPage");
	if(totalSize!=null){
		plistSize = Integer.valueOf(totalSize);
	}
	if(curPage!=null){
		currentpage = Integer.valueOf(curPage);
	}
	int pageRecordsSize = 15;
	int totalPage = plistSize/pageRecordsSize + 1;
	if(plistSize%15==0){
		totalPage = plistSize/pageRecordsSize;
	}
	int currentPageRecords = Plist.size();
	
	System.out.println("currentpage="+currentpage);
	System.out.println("currentPageRecords="+currentPageRecords);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>		
	<title>Show Projects and Leaves</title>
	<base target="_self" />
	<link rel="stylesheet" href="js/datapicker/jquery.ui.all.css" type="text/css" />
	<script src="js/jquery-1.5.1.js"></script>
	<script src="js/datapicker/jquery.ui.core.js"></script>
	<script src="js/datapicker/jquery.ui.datepicker.js"></script>
	<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
	<script>
		$(function() {
			$(".test" ).datepicker({changeMonth: true, changeYear: true });
			$(".test" ).datepicker( "option", "dateFormat", "yy-mm-dd" );
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
			<% if(re!=null){%>
			if('<%=re%>'=='-1')
			{
				alert("Save fail! Same project alreadly exist.");
			}
			else if('<%=re%>'=='1')
			{
				alert("Save project successful!");
			}
			else if('<%=re%>'=='0')
			{
				alert("Save project fail!");
			}
			<%}%>
		 });
	</script>
</head>
	<body id="editPage">
	<div class="show_list">
		<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
		<h3 style="text-align:left;width:98%;">Group: <%=gname%></h3>
		<hr style="clear:both; width:98%;"/>
		<div style="clear: both;width:90%; line-height:30px; display:inline;">
			<div style="float:left;text-align:left; font-size:16px; font-weight:bolder;margin-left:12px;">Project List</div>
			<div style="float:left;margin-left:150px; display:none;">
				<input type="radio" name="hide" value="1" style="cursor:pointer;"/>Hidden
				<input type="radio" name="hide" value="0" style="cursor:pointer;"/>Unhidden
			</div>
		</div>
		<form action="DataCheckerAction.do?operate=moveProject&operPage=data_checker_04_edit" method="post" enctype="MULTIPART/FORM-DATA">
		<div class="modeTable" style="clear:both;">
		<%
		if(plistSize>0){
		%>
		<table id="tb<%=currentpage%>" border="0" cellpadding="3" cellspacing="1" class="tab_bg2">
				<tr class="tr_title2">
					<td width="7%">ProjectID</td>
					<td width="9%">User</td>
					<td width="12%">Component</td>
					<td width="13%">Product</td>
					<td width="9%">SkillLevel</td>
					<td width="8%">Location</td>
					<td width="9%">OTType</td>
					<td width="12%">WBS</td>
					<td width="6%">IsConfirmed</td>
					<td width="">Comments</td>
					<td width="3%"></td>
				</tr>
		<%
		for(int i=0; i<currentPageRecords; i++) {%>
		<tr <%if(Plist.get(i).getHidden()>0){%>class="tr_content2"<%}else{ %>class="tr_content"<%} %> align="center">									
					<td>
						<input type="hidden" name="projectid" value='<%=Plist.get(i).getProjectId()%>' />
						<%
							out.print(Plist.get(i).getProjectId());
						%>
					</td>
					<td>
					<%
						out.print(Plist.get(i).getUserName());
					%>
					</td>
					<td>
						<%
							out.print(Plist.get(i).getComponentname());
						%>
					</td>
					<td>
						<%
							out.print(Plist.get(i).getProduct());
						%>
					</td>
					<td>
						<%
							out.print(Plist.get(i).getSkillLevel());
						%>
					</td>
					<td>
						<%
							out.print(Plist.get(i).getLocation());
						%>
					</td>
					<td>
						<%
							out.print(Plist.get(i).getOTType());
						%>
					</td>
					<td>
					<%
						if(!"null".equals(Plist.get(i).getWbs())&&Plist.get(i).getWbs()!=null)
						{
							out.print(Plist.get(i).getWbs());
						}
					%>
					</td>
					<td>
						<%
							int intConfirm = Plist.get(i).getConfirm();
									if (intConfirm == 0)out.print("×");
									else if (intConfirm == 1)out.print("√");
									else out.print("Err!");
						%>
					</td>
					<td>
					<label<%if (Plist.get(i).getComments() != null&& !(Plist.get(i).getComments().trim().equals(""))) {%>
					title='<%=Plist.get(i).getComments().trim() %>'>
					<%	if(Plist.get(i).getComments().trim().length()>12)
						{
							out.print(Plist.get(i).getComments().trim().substring(0,12)+"...");
						}
						else
						{
							out.print(Plist.get(i).getComments().trim());
						} 						
					}%>
					</label>			
					</td>
					<td>
						<input type="button" name='<%=Plist.get(i).getProjectId()%>' title="Modify Project"
							onclick="javascript: editP(this);" class="btnEdit" style=" margin-right:10px;" />
					</td>
				</tr>
		
		<%}
			}%>
				<%
				if (plistSize == 0) {
				%>
				<tr class="tr_content">
					<td colspan="11">
						No Project List in this group.
					</td>
				</tr>
				<%
					}
				%>

			</table>
			<!-- Add the Turn Page Labels by FWJ 2013-12-13 -->
			<%
			int startNum = 1;
			int endNum = 1;
			int turnStart = currentpage/5;
			int flag = currentpage%5;
			if(flag==0)
			{
				turnStart = turnStart<1?0:turnStart - 1;
			}
			startNum = turnStart*5 + 1;
			endNum = turnStart*5 + 5;
			if(totalPage<endNum)
			{
				endNum = turnStart*5 + totalPage%5;
			}
			
			%>
			<div class="turnPage">
				<label id ="first" <% if(currentpage==1) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %> onclick="javascript: turnpage('1');">First</label>
				<%if(currentpage-1>0){ %>
				<label id ="prev" class="pageCell" onclick="javascript: turnpage('<%=currentpage-1 %>');">Prev</label>
				<%} %>
				<%for(int i=startNum;i<=endNum;i++) {%>
				<label id="label<%=(i-1)%5%>" onclick="javascript: turnpage('<%=i %>');" <% if(currentpage==i) {%> class="pageCell2"<%} else{%> class="pageCell"<%} %>><%=i%></label>
				<%} %>
				<%if(totalPage>currentpage+1) {%>
				<label id="next" class="pageCell"  onclick="javascript: turnpage(<%=currentpage+1 %>);">Next</label>
				<%} %>
				<label id="last" <%if(currentpage==totalPage){ %>class="pageCell2"<%} else{ %>class="pageCell" <%} %>onclick="javascript: turnpage('<%=totalPage %>');" title='total:<%=totalPage %>'>Last</label>
				<input type="text" class="txtPage" id="txtPN" onkeydown="javascript:myKeyDown();"/>
				<input type="button" id="go" class="go" value="Go" onclick="javacript:turnpage($('#txtPN').val());"/>
			</div>
			</div>
			<div class="btnsLine" style="text-align:left;width:98%;">
			<input name="Submit233" type="button" class="btnStyle" value="Add New Project" onclick='javascript:addProject();'/>
			<input name="Submit235" type="button" class="btnStyle"  value="Download Project List" 
				onclick='javascript:DownloadProjectList();' title="To Check More Project Details."/>
			<input name="Submit234" type="button" class="btnStyle" value="Upload Updated Project List" onclick='javascript:UploadProjectList();'/>
			<input id="ptxt" type="file" name="files"/>
			</div>
		<hr />
		<h4 style="clear:both; text-align:left; width:98%; line-height:25px;">User List</h4>
		<div class="modeTable">
		<table border="0" cellpadding="3" cellspacing="1"class="tab_bg2" id="utab" style="width:98%;min-width:1030px;">
			<tr class="tr_title2">
				<td width="8%">ID</td>
				<td width="12%">Name</td>
				<td width="12%">System Level</td>
				<td width="12%">Approve Level</td>
				<td width="12%">Employee ID</td>
				<td width="12%">Leave Date</td>
				<td width="18%">Email Address</td>
				<td width="6%"></td>
			</tr>
			<%
				for (int i = 0; i < Userlist.size(); i++) {
			%>
			<tr class="tr_content" align="center" id="trs<%=i %>" >
				<td>
					<%
						out.print(Userlist.get(i).getUserId());
					%>
				</td>
				<td>
					<%
						out.print(Userlist.get(i).getUserName());
					%>
				</td>
				<td>
					<%
						out.print(Userlist.get(i).getLevel());
					%>
				</td>
				<td>
				<%
					out.print(Userlist.get(i).getApproveLevelName());
				%>
				</td>
				<td>
				<%
					if(Userlist.get(i).getHPEmployeeNumber()==null)
					{
						out.print("");
					}
					else
					{
						out.print(Userlist.get(i).getHPEmployeeNumber());
					}
				%>
				</td>
				<td>
				<input type="text" id="leaveTime<%=i %>" class="test" name="leaveTime" style="width:80px;border:1px #ccc solid;" value='<%=Userlist.get(i).getLeaveTime() %>'
				 readonly="readonly" />
				</td>
				<td>
					<%
					if(Userlist.get(i).getEmail()==null||Userlist.get(i).getEmail().equals("null"))
					{
						out.print("");
					}
					else
					{
						out.print(Userlist.get(i).getEmail());
					}
					%>
				</td>
				<td>
					<input type="button" name='<%=Userlist.get(i).getUserId()%>' title="Modify User"
						onclick="javascript: editU(this);" class="btnEdit" style="margin-right:10px;"/>
					<input type="button" name='<%=Userlist.get(i).getUserId()%>' title="Delete User"
						onclick="javascript: deleteU(this, '<%=i %>');" class="btnRemove" />
				</td>
			</tr>
			<%
			}
			if (Userlist.size() == 0) {
			 %>
			<tr id="noUser" class="tr_content">
				<td colspan="8">
					No UserList in this group.
				</td>
			</tr>
				<%} %>
			
		</table>
		</div>
</form>
</div>
<script language="javascript">

function turnpage(currentPage)
{
	var total = '<%=totalPage%>';
	if(currentPage=='' || currentPage>total){
		alert("Invalid page number, please input correct one!");
		$("#txtPN").focus();
		return;
	}
	var gid = '<%=gid%>';
	var gname = '<%=gname%>';
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID="+gid+"&gname="+gname+"&currentPage="+currentPage;
	form.submit();
}
//Added by FWJ 2013-12-13
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

//@ Dancy modify
function deleteU(u, indexofleavDate)
{
	var trId = "#trs"+indexofleavDate;
	var uid = u.name;
	var tag = "leaveTime"+indexofleavDate;
	var leaveTime = document.getElementById(tag).value;
	if(leaveTime==""){
	alert("unLeaveUser can not be deleted!");
	return false;
	}
	else {
		if (!confirm("Sure to delete the user?"))
		{
    		window.event.returnValue = false;
    	}
   		else
   	 	{
		    var gid = '<%=gid%>';
			var gname = '<%=gname%>';					
			var form = document.forms[0];
			form.action = "DataCheckerAction.do?operate=deleteUser&operPage=data_checker_04_edit&UserId="+uid+"&GID="+gid+"&GroupsName="+gname+"&leaveTime="+leaveTime;
			form.submit();
		}
	}
}

function editU(u)
{
	var uid = u.name;
	var gid = '<%=gid%>';
	var gname = '<%=gname%>';
	var form = document.forms[0];
	form.target = "";
	form.action = "SysUserAction.do?operate=searchUserByID&operPage=data_checker_04_edit_User&userid="+uid+"&gid="+gid+"&gname="+gname;
	form.submit();
}
function editP(p)
{
	var pid = p.name;
	var gid = '<%=gid%>';
	var gname = '<%=gname%>';
	var form = document.forms[0];
	form.target = "";
	form.action = "DataCheckerAction.do?operate=searchProjectByID&operPage=data_checker_04_edit_Pro&projectid="+pid+"&gid="+gid+"&gname="+gname;
	form.submit();
}

function addProject()
{
	//获得和判断必填项project name
	var form = document.forms[0];
	form.target = "";
	var gid = '<%=gid%>';
	var gname = '<%=gname%>';
	form.action = "DataCheckerAction.do?operate=toinsert&operPage=new_project_edit&gid="+gid+"&gname="+gname;
	form.submit();
}

// by collie 0420
function DownloadProjectList() 
{
	var form = document.forms[0];
	var gname = '<%=gname %>';
	
	form.action = "ProjectAction.do?operate=downloadProjectList&operPage=data_checker_04_edit&groupName="+gname+"&scrollTop="
			+ document.body.scrollTop;
	form.submit();

}


function DownloadUserList() {
	var form = document.forms[0];
	var gname = '<%=gname %>';
	form.action = "ProjectAction.do?operate=downloadUserList&operPage=data_checker_04_edit&groupName="+gname+"&gid="+gid;
	form.submit();

}

// by collie 0427
function UploadProjectList() 
{	
	if (document.getElementById("ptxt").value=="")
	{
		alert("Please select an project list file firstly!")
	}
	else{
		var form = document.forms[0];	
		//hanxiaoyu01 2013-01-22用于上传后刷新数据
		var gid='<%=gid%>';
		var gname='<%=gname%>';
		
		form.action = "ProjectAction.do?operate=uploadProjectList&operPage=data_checker_04_edit_uploaded&scrollTop="
				+ document.body.scrollTop+"&gid="+gid+"&gname="+gname;
		form.submit();
	}
}

</script>
</body>
</html>