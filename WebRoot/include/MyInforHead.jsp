<%@ page contentType="text/html; charset=GBK" import="com.beyondsoft.expensesystem.domain.system.*"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-kingtake.tld" prefix="efan"%> 

<%	
	SysUser sysUserHead = (SysUser) request.getSession().getAttribute("user");
	String SystemAnnounce = (String) request.getSession().getAttribute("SystemAnnounce");
	//String groupAnnounce = sysUserHead.getGroupAnnounce();
	if (null == SystemAnnounce || SystemAnnounce.equals("null"))
		SystemAnnounce = "No System Announcement now!";
	else
		SystemAnnounce = "<font color='red'>System Announcement: </font><br />" + SystemAnnounce;

		
	//if (null == groupAnnounce || groupAnnounce.equals("null")||groupAnnounce.equals(""))
	//	groupAnnounce = "";
	//else
	//	groupAnnounce= "<font color='red'>Group Announcement: </font><br /><font color='gray'>" + groupAnnounce +"</font>";

	if (null == sysUserHead.getAnnouncement() || sysUserHead.getAnnouncement().equals("null"))
		sysUserHead.setAnnouncement("");
	
	
	String isFirst = (String) request.getSession().getAttribute("isFirst");
	System.out.println("isFirst="+isFirst);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/jquery-1.5.1.js"></script>
<link href="css/style1024.css" rel="stylesheet" id="style" type="text/css" />
<script type="text/javascript">
function drag(obj)
{
 if (typeof obj == "string") {
  var obj = document.getElementById(obj);
  obj.orig_index=obj.style.zIndex;//设置当前对象永远显示在最上层
 }
 obj.onmousedown=function (a){//鼠标按下
  this.style.cursor="move";//设置鼠标样式
  this.style.zIndex=1000;
   var d=document;
  if(!a) a=window.event;//按下时创建一个事件
  var x=a.clientX-document.body.scrollLeft-obj.offsetLeft;//x=鼠标相对于网页的x坐标-网页被卷去的宽-待移动对象的左外边距
  var y=a.clientY-document.body.scrollTop-obj.offsetTop;//y=鼠标相对于网页的y左边-网页被卷去的高-待移动对象的左上边距
  d.onmousemove=function(a){//鼠标移动
   if(!a) a=window.event;//移动时创建一个事件
   obj.style.left=a.clientX+document.body.scrollLeft-x;
   obj.style.top=a.clientY+document.body.scrollTop-y;
  }
  d.onmouseup=function (){//鼠标放开
   document.onmousemove=null;
   document.onmouseup = null;
   obj.style.cursor="normal";//设置放开的样式
   obj.style.zIndex=obj.orig_index;
  }
 }  
}

$(function(){
	if('<%=isFirst%>'=='yes')
	{	
		$("#announce").show();
		drag("announce");
		<% request.getSession().setAttribute("isFirst", "no"); %>
	}
	else if('<%=isFirst%>'=='no')
	{
		$("#announce").hide();	
	}
	
	$("#close").click(function(event)
	{
		$("#announce").slideUp("slow");
	});
	
	$("#shows").click(function(event)
	{
		$("#announce").show();
		drag("announce");
	});
	
	if(screen.width==1360||screen.width==1366)
	{
		$("#style").attr("href","css/style1366.css");
	}
	if(screen.width==1280)
	{
		$("#style").attr("href","css/style1280.css");
	}
	if(screen.width==1440)
	{
		$("#style").attr("href","css/style_new.css");
	}

});

function changePWD()
{
	var gid = '<%=sysUserHead.getGroupID() %>';
	var gname = '<%=sysUserHead.getGroupName() %>';
	window.open('SysUserAction.do?operate=toChangePWD&operPage=changePWD&gid='+gid,null,'height=360,width=600,scrollbars=yes,top=260,left=300,resizable');
}
function downloadManual()
{
	window.location.href="SysUserAction.do?operate=downloadLocal";
}
function logout()
{
	window.location.href ="LogoutAction.do?operate=logout";
}
</script>
</head>
<body>
<div style="width:100%;">
	<div class="logo2"></div>
	<div class="banner"></div>		
	<div class="account">
		<div style="float:left;"><img src="images/user.png" /></div>
		<div style="line-height:37px; float:left; margin-left:10px;">
		Account: <% out.print(sysUserHead.getUserName()); %>
		</div>
	</div>
</div>
<div class="noteInfo">
	<div class="uInfo">
		PermissionLevel:  
		<% out.print(sysUserHead.getLevel()); %> &nbsp;&nbsp;&nbsp;&nbsp; 
		Group: <% out.print(sysUserHead.getGroupName()); %> 
	</div>
	<div class="manual">
		<a href="javascript:" id="shows">Announcement</a> | 
		<a href="javascript:downloadManual();" title="Download User's Manual">User's Manual</a> | 
		<a href="javascript:changePWD();">Change Password</a> | 
		<a href="javascript:logout();">Logout</a>
	</div>	
</div>
	<div class="announce" id="announce" style="display:none;">
		<div style="width:98%; display:inline;">
			<div style="float:left; width:89%; padding-left:2%; text-align:left; line-height:23px; height:23px; background-color:#ffc99b;">Announcements:</div>
			<div style="float:left; width:8%; padding-right:1%; height:23px; background-color:#ffc99b;"><input type="button" id="close" value="x" class="close"/></div>
		</div>
		<div class="inner-content">
		<%=SystemAnnounce%><br />
	    <%--<%=groupAnnounce%><br />--%>
		<%=sysUserHead.getAnnouncement() %>
	</div>
</div>
</body>
</html>