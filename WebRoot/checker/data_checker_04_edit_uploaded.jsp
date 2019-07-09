<%@ page language="java"
	import="java.util.*,com.beyondsoft.expensesystem.domain.checker.*,com.beyondsoft.expensesystem.domain.system.*"
	contentType="text/html; charset=GBK"%>
<%@ include file="../include/header.jsp"%>
<%
@SuppressWarnings("unchecked")
List<String> projectListResult = (List<String>) request.getAttribute("projectListResult");
System.out.println("projectListResult in jsp = "+projectListResult.get(0));
String uploadresult = (String) request.getAttribute("uploadresult");
String result="";
if (uploadresult=="true"){
	if (null!=projectListResult){
		result="Successful! "+projectListResult.get(0);
	}else{
		result="No project in the list!";
	}
}else{
	result=uploadresult;
}	
String gname=(String)request.getParameter("gname");
String gid=(String)request.getParameter("gid");

System.out.println("gid in uploaded jsp is: "+gid);

%>
<script>
var result='<%=result%>';
alert(result);
//hanxiaoyu01 2013-01-22用于上传后刷新数据
var gname='<%=gname%>';
var gid ='<%=gid%>';
window.location="DataCheckerAction.do?operate=searchPandL&operPage=data_checker_04_edit&GID="+gid+"&gname="+gname;
</script>
