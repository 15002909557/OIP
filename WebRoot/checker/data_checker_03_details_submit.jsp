<%@ page contentType="text/html; charset=GBK"%> 
<%
	String result = (String) request.getAttribute("saveresult");
	String detailsize = (String)request.getAttribute("detailsize");
	String comsize = (String)request.getAttribute("comsize");
	String sumofdetails = (String)request.getAttribute("sumofdetails");// by collie 0509 Details����ʱ�Զ�����Daily�����ݡ�
	System.out.println("sumofdetails in JSP= "+sumofdetails);
	System.out.println("detailsize:"+detailsize+"result:"+result+"comsize:"+comsize);
%>
<%@ page contentType="text/html; charset=GBK" language="java"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" href="./css/style.css" type="text/css">
	</head>
	<body onload="javascript: autoclose();" onunload="javascript: exit();">
		<table width="100%" border="0" cellpadding="3" class="bgcolor" cellspacing="0" >
			<tr class="tr_title">
				<td align="center" colspan='2'>
				<%if("true".equals(result)){%>
					Data saved successfully!
				<%}else{%>
					Sorry, data saved fail! Please save it again.
				<%} %>
				</td>
			</tr>
			<tr class="tr2">
				<td align="right">
				<%if("true".equals(result)){%>
					<img src="./images/saved_expense_detail.png" alt="Data saved successfully!">
				<%}else{%>
					<img src="./images/notsaved_expense_detail.png" alt="data saved fail!">
				<%} %>
				</td>
				<td align="right">
					<input name="Save" type="button" class="button" style="height: 40;"
						value=" Exit " onclick='javascript: myclose();'>
				</td>
			</tr>
		</table>
		
	</body>
<script language="javascript">
function exit()
{
	var result = '<%=result%>';
	var detailsize = '<%=detailsize%>';
	var comsize = '<%=comsize%>';
	var inputid = window.opener.document.getElementById("inputid").value;
	
	//var inputid = 1;
	//alert("inputid:"+inputid);

	if(result == 'true')//����ɹ�����ҳ��ʵʱ����
	{
		var inputid = window.opener.document.getElementById("inputid").value;
		//alert(inputid+":"+result+":"+detailsize+":"+comsize);
		
		//by collie 0510 Details��Ϣ ����ʱ ����Daily��Ϣ��
		var inputid_hours = inputid.replace("D","");
		//alert("inputid_hours:"+inputid_hours);
		var sumofdetails = '<%=sumofdetails%>';
		if (sumofdetails!='null'){
			window.opener.document.getElementById(inputid_hours).value=sumofdetails;
			window.opener.document.getElementById(inputid).value=sumofdetails;
		}
		
		if(detailsize != '0')//˵����details��D������
			window.opener.document.getElementById(inputid).style.color='';
		else
			window.opener.document.getElementById(inputid).style.color='red';
			
		if(comsize != '0')//˵����comments,�߿����
			window.opener.document.getElementById(inputid).style.backgroundColor='lightgreen';
		else
			window.opener.document.getElementById(inputid).style.backgroundColor='';
	}
	
	// by collie 0511 Details�ر�ʱ�ܸ���Daily��Page Sum�� 
	try{ 
	window.opener.getPageSum('s'); 
	}catch(exception){ 
	alert(exception.description); 
	// ����ִ�������ġ� 
	} 
	
}
function myclose()
{
	window.close(); 
}
function autoclose()
{
	var result = '<%=result%>';
	if(result == 'true')//����ɹ����Զ��ر�
	{
		setTimeout('myclose();','600');
		
	}
}
</script>
</html>
