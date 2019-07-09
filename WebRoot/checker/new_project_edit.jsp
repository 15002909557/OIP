<%@ page contentType="text/html; charset=GBK" import="com.beyondsoft.expensesystem.domain.system.*,com.beyondsoft.expensesystem.domain.checker.*"%>
<%
	//GroupName
	String gname = (String) request.getAttribute("gname");
	String gid = (String)request.getAttribute("gid");
	//GroupId
	String sessionGID = (String) request.getSession().getAttribute("groupID");
	SysUser user = (SysUser) request.getSession().getAttribute("user");
	//hanxiaoyu01 2013-02-18 取当前用户project的默认值 
	Project dproject = (Project)request.getAttribute("dproject");
	
	System.out.println("gname is :"+gname+" gid is : "+gid+"   sessionGID="+sessionGID);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Add a new project</title>
	<link href="css/style_new.css" rel="stylesheet"type="text/css" />		
	<script src="js/jquery-1.5.1.js"></script> 
<script language="javascript"> 
function closeDiv(divId){ 
document.getElementById(divId).style.display = 'none'; 
} 
function displayDiv(divId){ 
document.getElementById(divId).style.display = 'block'; 
} 
</script>
</head>
<body>
		<%
			//如果是从data_checker_04_edit传过来，就无需显示inforhead.jsp
			if (null == sessionGID || "".equals(sessionGID) || "null".equals(sessionGID)) {
		%><%@ include file="../include/MyInforHead.jsp"%><%}%>
		<div class="editPage" style="padding-top:60px; height:460px;"  id="editPage">
		<h3> Add a new project</h3>
		<html:form styleId="form1" action="DataCheckerAction.do?operate=saveNewProject&operPage=new_project_edit_submit" target="hide" method="post">
				UserName:&nbsp;&nbsp;<% out.print(user.getUserName());%> 
				<input type="button" value="Set Default" class="btnStyle" onclick="setdefault()" style="margin-left: 30px;"/>
				<br />
					Component:
					<html:select property="project.componentid"  styleId="componentid" style="width:160px;">
						<html:optionsCollection property="componentNames_fw"
							label="componentName" value="componentid" />
					</html:select>
					<span class="style1">*</span><br />
					Product:
					<html:select property="project.product" styleId="productName" style="width:160px; margin-left:21px;">
						<html:optionsCollection property="productNames_fw"
							label="productName" value="productName" />
					</html:select>
					<span class="style1">*</span><br />
					WBS:
					<html:select property="project.WBS" styleId="wbs" style="width:160px; margin-left:35px;">
						<html:optionsCollection property="wbsNames"
							label="wbs" value="wbs" />
					</html:select>
					<span class="style1">*</span><br />
					Skill Level:
					<html:select property="project.skillLevel" styleId="skillLevel" style="width:160px; margin-left:8px;">
						<html:optionsCollection property="skillLevels_fw"
							label="skillLevelName" value="skillLevelName" />
					</html:select>
					<span class="style1">*</span><br />
					Location:
					<html:select property="project.location" styleId="location" style="width:160px; margin-left:17px;">
						<html:optionsCollection property="locations_fw"
							label="locationName" value="locationName"/>
					</html:select>
					<span class="style1">*</span><br/>
					OTType:
					<html:select property="project.OTType" styleId="OTType" style="width:160px; margin-left:21px;">
						<html:optionsCollection property="OTTypes_fw"
							label="OTTypeName" value="OTTypeName" />
					</html:select>
					<span class="style1">*</span><br />
					Group:<label style="margin-left:50px;">
					<%=gname %>
					</label>
					<br />
				Comments:
				<html:textarea name="DataCheckerForm" property="project.comments" styleId="comments" cols="25" rows="3" 
					onblur="if(this.value.length>100){this.value=this.value.substring(0,100);}" style="margin-left:2px; border:1px #ccc solid;"
					onkeydown="if(this.value.length>100){this.value=this.value.substring(0,100);}"
					onkeyup="if(this.value.length>100){this.value=this.value.substring(0,100);}" />
	            <br/>
				<p style="align:center; padding-left:60px;">	
				<input name="save" type="button" class="btnSave2" onclick='javascript: return saveinfofw();'/>
				<input name="back" type="button" class="btnBack" onclick='javascript:returnback();'/>
				</p>
				    <html:hidden property="recid" />
					<html:hidden property="expenseData.expenseDataId" styleId="expenseDataId" />
					<html:hidden property="expenseData.projectId" styleId="projectId" />
					<html:hidden property="today" styleId="today" />
					<html:hidden property="project.groupId" styleId="groupId" value="<%=gid %>"/>
				</html:form>
				</div>
		<div class="tanchuang_wrap" id="aaaa">
		<div class="lightbox"></div>
		<div class="tanchuang_neirong">
			<img src="images/loading.gif" />
			Loading projects may take a while, please wait, thanks!
		</div>
	</div>
		<iframe src="" width="0" height="0" name="hide" style="display:none;"></iframe>
<script>

var componentid=<%=dproject.getComponentid()%>;
var productName="<%=dproject.getProduct()%>";
var WBS="<%=dproject.getWBS()%>";
var skillLevel="<%=dproject.getSkillLevel()%>";
var locationName="<%=dproject.getLocation()%>";
var OTType="<%=dproject.getOTType()%>";
$("#componentid").val(componentid);
$("#productName").val(productName);
$("#wbs").val(WBS);
$("#skillLevel").val(skillLevel);
$("#location").val(locationName);
$("#OTType").val(OTType);

var changed = 0;
function saveinfofw() 
{
		var component = $("#componentid").val();
		var product = $("#productName").val();
		var wbs = $("#wbs").val();
		if(component<1)
		{
			alert("please select a component!");
			 $("#componentid").focus();
			return;
		}
		if(product=='')
		{
			alert("please select a product!");
			$("#productName").focus()
			return;
		}
		if(wbs==null)
		{
			alert("pelase select a WBS!");
		}
		var form = document.forms[0];
		form.submit();
}

function returnback() {
	var form = document.forms[0];
	form.target = "";
	var gid = '<%=gid%>';
	var gname = '<%=gname%>';
	if('null'=='<%=sessionGID%>')
	{
		displayDiv('aaaa');
		form.action = "DataCheckerAction.do?operate=search&operPage=data_checker_03";
	}
	else
	{
		form.action = "DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID="+gid+"&gname="+gname;
	}
	form.submit();
}

function clearPOList(){
   
    var courseid = document.getElementById("POName");
   
    while(courseid.childNodes.length > 0){
     courseid.removeChild(courseid.childNodes[0]);
    }
}
//hanxiaoyu01 2013-02-18
function setdefault(){

$.ajax({
  type:"post",
  url:"DataCheckerAction.do?operate=setDefaultProject",
  data:$("#form1").serialize(),
  success:function(msg){
   if($.trim(msg)!=""){
     alert(msg);
   }else{
     alert("fail!");
   }
  }
});
}
</script>
</body>
</html>