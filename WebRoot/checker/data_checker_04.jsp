<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"%>
<%@ include file="../include/MyInforHead.jsp"%>
<%
	//初始化Group和User列表
	@SuppressWarnings("unchecked")
	List<Groups> glist = (List<Groups>) request.getAttribute("glist");
	@SuppressWarnings("unchecked")
	List<String> alist = (List<String>) request.getAttribute("alist");
	@SuppressWarnings("unchecked")
	List<String> defaultuilist = (List<String>) request.getAttribute("UIlist");
	String sessionGID = (String) request.getSession().getAttribute("groupID");
	if(sessionGID==null)
	{
		sessionGID = "-1";
	}
	String adStr = "";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<link href="css/style1024.css" id="style2" rel="stylesheet" type="text/css" />
<title>Project and Account Assignment</title>
<script type="text/javascript">
		$(function() {
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
			$("#PandL").load(function()
			{          
         		$(this).height($(this).contents().find("body").height() + 40);   
     		});   
 
		 });
	</script>
</head>
	<body>
	<center>	
	<form action="DataCheckerAction.do?operate=saveAnnouncement&operPage=data_checker_04" method="post">
	<div class="main_box3" style="width:95%;padding-left:5%;">		
		<div id="conditions" style="display:inline-table;" align="center">
		<div style="float:left;padding-right:12px; line-height:25px;">Groups:</div>
		<div style="float:left; margin-right:100px;">
			<select name="GroupsId" id="GroupsId" onchange="javascript:GroupsChange();">
				<%
				for (int i = 0; i < glist.size(); i++) 
				{					
				%>
				<option value=<%=glist.get(i).getGid()%> 
				<% 
				if(glist.get(i).getGid()==Integer.parseInt(sessionGID)){
				adStr = glist.get(i).getAnnouncement();
				%>selected="selected"<%
				}%>>
					<%=glist.get(i).getGname()%>
				</option>
				<%
					}
				%>
				
			</select>
			<!--hanxiaoyu01 2012-12-24 给Group增加修改和删除功能  -->
			<input type="button" id="DeleteGroup" class="btnStyle" value="Delete" onclick="deleteGroup();" <%if(Integer.parseInt(sessionGID)==-1){ %>disabled="disabled" <%} %>/>
		    <input type="button" id="ModifyGroup" class="btnStyle" value="Modify" onclick="javascript:UpdateGroup();" <%if(Integer.parseInt(sessionGID)==-1){ %>disabled="disabled" <%} %>/>
		</div>
		<div id="inputs" style="float:left; margin-left:30px;">
			<div style="display:none;">
				<input type="radio" name="UIradio" value="1" checked="checked"/>
				<label id="FWUI" onclick="document.getElementsByName('UIradio')[0].checked='true';">FirmWare UI</label>
				<input type="radio" name="UIradio" value="2" />
				<label id="SWUI" onclick="document.getElementsByName('UIradio')[1].checked='true';">SoftWare UI</label>
			</div>	
			<input type="button" class="btnStyle" onclick="javascript:addGroup();" id="addG" value="Add New Group"/>
			<input type="button" class="btnStyle1" onclick="javascript:addUser();" id="addU" value="Add New User"/>
		</div>
		<p style="clear:both;float:left;">
		<!-- Change the value(Please select a Group!) to (Input Announcement) -->
		<textarea name="announcement" id="announcement" rows="4" class="input_text" 
		style="height:80px; text-align:left; padding:2px; width:850px;" cols="160"><%if (adStr.trim().equals("")){%>Input Announcement<%}else{ %><%=adStr%><%}%></textarea>
		</p>
		<input type="button" class="btnSubmit2" id="submitBtn" onclick="javascript:setAnnouncement();" style="clear:both;float:left;"/>
		<p style="float:left;clear:both;">
		<input name="showPL" id="showPL" type="checkbox" onclick="javascript:searchPandL(this);" />
		<label onclick="javascript:checkShow();" style="font-size:10px;">Show Projects and Users</label>
		</p>
		</div>
		</div>		
		<div class="btnsLine">
		<hr />
		<input type="button" class="btnBack" onclick='javascript:backpage();'/>
		</div>
		</form>
		<div align="center" style="clear:both; display:none;" id="bodyL">
		<iframe src="" scrolling="no" class="show_frame" height="auto" frameborder="0" name="PandL" id="PandL" width="95%"></iframe>
		</div>
		
	</center>
<script type="text/javascript">

function checkShow()
{
	var show = document.getElementById('showPL');
	if(!show.checked) //取消选择
	{
		show.checked='true';
	}
	else
	{
		show.checked='';
	}
	
	searchPandL(show);
}
function searchPandL(show)
{
    //alert("searchPandL被调用了");
	if(!show.checked) //取消选择
	{
		document.getElementById("bodyL").style.display='none';
		return false;
	}
	else  //选中
	{
		document.getElementById("bodyL").style.display='';		
		var gname = $("#GroupsId option:selected").text();
		gname = $.trim(gname)
		var gid = $("#GroupsId").val();
		var alist='<%=alist%>';   //Name,Announcement,Id
		var temp = alist.substring(1,alist.length-1).split(", ");
		
		for(var i=0;i<temp.length-1;i=i+3)
		{
			if($.trim(gname) == temp[i])
			{
				window.frames["PandL"].location.href="DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID="+temp[i+2]+"&gname="+gname;
				return;
			}
		}
	}
}
function GroupsChange()
{
	var gname = $("#GroupsId option:selected").text();
	gname = $.trim(gname)
	if(gname=="All Data"){
	 $("#DeleteGroup").attr("disabled","disabled");
	 $("#ModifyGroup").attr("disabled","disabled");
	}else{
	 $("#DeleteGroup").removeAttr("disabled");
	 $("#ModifyGroup").removeAttr("disabled");
	}
	document.getElementById("submitBtn").disabled = "";
	document.getElementById("showPL").disabled = "";
	document.getElementById("addG").disabled = "";
	document.getElementById("addU").disabled = "";
	var alist='<%=alist%>';   //Name,Announcement,Id
	var UIlist = '<%=defaultuilist%>';   //Name, UIvalue, Id
	var temp = alist.substring(1,alist.length-1).split(", ");
	var tempui = UIlist.substring(1,UIlist.length-1).split(", ");
	
	//得到相应的公告信息
	for(var i=0;i<temp.length-1;i=i+3)
	{
		if($.trim(gname) == temp[i])
		{
			var tempannounce = temp[i+1].replace(new RegExp("<quote>","gm"),"'");
			document.getElementById("announcement").value=tempannounce;
			break;
		}
	}
	//得到相应的UI信息
	for(var i=0;i<tempui.length-1;i=i+3)
	{
		if(gname == tempui[i])
		{
			var ui = tempui[i+1].replace(new RegExp("<quote>","gm"),"'");
			switch(ui)
			{
				case '0': document.getElementsByName('UIradio')[0].checked='';
						  document.getElementsByName('UIradio')[1].checked='';
						  break;
				case '1': document.getElementsByName('UIradio')[0].checked='true';break;
				case '2': document.getElementsByName('UIradio')[1].checked='true';break;
			}
			break;
		}
	}
	//判断是否已经选择show
	if(document.getElementById("showPL").checked)
	{
		document.getElementById("bodyL").style.display='';
		var gname = $("#GroupsId option:selected").text();
		var gid = $("#GroupsId").val();
		var alist='<%=alist%>';   //Name,Announcement,Id
		alist = alist.replace(/\,/g, "×" );
		var temp = alist.substring(1,alist.length-1).split("× ");
		for(var i=0;i<temp.length-1;i=i+3)
		{
			if($.trim(gname) == temp[i])
			{
				window.frames["PandL"].location.href="DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID="+temp[i+2]+"&gname="+gname;
				return;
			}
		}
	}
}
function setAnnouncement()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=saveAnnouncementandUI&operPage=data_checker_04";
	form.submit();
}
function backpage()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=backPage&operPage=data_checker_01";
	form.submit();
}
function addGroup()
{
	var form = document.forms[0];
	form.action = "DataCheckerAction.do?operate=toAddGname&operPage=data_checker_04_newGroup";
	form.submit();
}
function addUser()
{
	var gname = $("#GroupsId option:selected").text();
	var gid = $("#GroupsId").val();
	var form = document.forms[0];
	form.action = "SysUserAction.do?operate=toinsert&operPage=data_checker_04_newUser&GroupName="+gname+"&gid="+gid;
	form.submit();
}

//hanxiaoyu01 2012-12-24 
$.ajaxSetup({  
   async : false  
});
function deleteGroup()
{
	var gid = $("#GroupsId").val();
	if(gid==-1)
	{
		  alert("please select a group!");
		  return false;
	 }
	var result="";
	$.post("DataCheckerAction.do",{"operate":"checkGroup","gid":gid},function(data)
	{
	  result=data;
	});
	if(result=="false")
	{
	  alert("It has Project List or User List in this group,you can not delete this Group!");
	  return false;
	}
	else
	{
	  	if(confirm("Delete this Group?"))
		{
	   		window.location="DataCheckerAction.do?operate=deleteGroup&gid="+gid+"&operPage=data_checker_04";
	  	}
	}
}

function UpdateGroup()
{
	var gid = $("#GroupsId").val();
	if(gid==-1)
	{
	  	alert("please select a group!");
	  	return false;
	}
	else
	{
	  	window.location="DataCheckerAction.do?operate=updateGroup&gid="+gid+"&operPage=data_checker_04_updateGroup";
	} 
}
</script>
</body>
</html>