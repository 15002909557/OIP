<%@ page language="java" import="java.util.*" contentType="text/html; charset=GBK"%>
<%@ include file="../include/header.jsp"%>
<%
@SuppressWarnings("unchecked")
List<String> userListResult = (List<String>) request.getAttribute("userListResult");
System.out.println("userListResult in jsp = " + userListResult.get(0));
String uploadresult = (String) request.getAttribute("uploadresult");
String result="";
if (uploadresult=="true"){
	if (null!=userListResult){
		result="Successful! "+userListResult.get(0);
	}else{
		result="No user in the list!";
	}
}else{
	result=uploadresult;
}	

%>
<script>
var result='<%=result%>';
alert(result);
</script>
