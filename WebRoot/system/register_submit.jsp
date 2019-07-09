<%@ page contentType="text/html;charset=GBK"%> 
<%@ include file="../include/header.jsp"%>
<script>
if('<bean:write name="SysUserForm" property="strErrors" filter="true"/>' == 'true')
{
	alert("Data saved successfully!");
	parent.returnback();
}
else
{
	alert('<bean:write name="SysUserForm" property="strErrors" filter="true"/>');
}

</script>