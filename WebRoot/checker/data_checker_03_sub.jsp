<%@ page contentType="text/html; charset=GBK" language="java"
	import="java.text.SimpleDateFormat,java.util.*,com.beyondsoft.expensesystem.domain.checker.*"
%>
<%
	// project List
	@SuppressWarnings("unchecked")
	List<ExpenseData> sessionlist = (List<ExpenseData>) request.getSession().getAttribute("list");
	@SuppressWarnings("unchecked")
	List<Groups> glist = (List<Groups>) request.getSession().getAttribute("glist");
	//总和
	List<ExpenseData> list = new ArrayList<ExpenseData>();

	java.text.NumberFormat formate = java.text.NumberFormat.getNumberInstance();
	formate.setMaximumFractionDigits(4);// 设定小数最大为数 ，那么显示的最后会四舍五入的
	
	for(int i=0;i<sessionlist.size();i++)//遍历总的项目列表
	{
		ExpenseData temped = new ExpenseData();
		int exsit = 0;
		temped.setGroupId(sessionlist.get(i).getGroupId());
		temped.setGroupName(sessionlist.get(i).getGroupName());
		temped.setHour1(sessionlist.get(i).getHour1());
		temped.setHour2(sessionlist.get(i).getHour2());
		temped.setHour3(sessionlist.get(i).getHour3());
		temped.setHour4(sessionlist.get(i).getHour4());
		temped.setHour5(sessionlist.get(i).getHour5());
		temped.setHour6(sessionlist.get(i).getHour6());
		temped.setHour7(sessionlist.get(i).getHour7());
		for(int j=0;j<list.size();j++)//遍历list列表
		{
			if(temped.getGroupId()==list.get(j).getGroupId())//如果group相同，hour相加
			{
				double summaryDay1=Double.parseDouble("0"+temped.getHour1().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour1().replaceAll(",", ""));
				double summaryDay2=Double.parseDouble("0"+temped.getHour2().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour2().replaceAll(",", ""));
				double summaryDay3=Double.parseDouble("0"+temped.getHour3().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour3().replaceAll(",", ""));
				double summaryDay4=Double.parseDouble("0"+temped.getHour4().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour4().replaceAll(",", ""));
				double summaryDay5=Double.parseDouble("0"+temped.getHour5().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour5().replaceAll(",", ""));
				double summaryDay6=Double.parseDouble("0"+temped.getHour6().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour6().replaceAll(",", ""));
				double summaryDay7=Double.parseDouble("0"+temped.getHour7().replaceAll(",", "")) 
									+ Double.parseDouble("0"+list.get(j).getHour7().replaceAll(",", ""));
				list.get(j).setHour1(formate.format(summaryDay1));
				list.get(j).setHour2(formate.format(summaryDay2));
				list.get(j).setHour3(formate.format(summaryDay3));
				list.get(j).setHour4(formate.format(summaryDay4));
				list.get(j).setHour5(formate.format(summaryDay5));
				list.get(j).setHour6(formate.format(summaryDay6));
				list.get(j).setHour7(formate.format(summaryDay7));
				exsit = 1;//标记已经操作
			}
		}
		if(exsit == 0)//如果没有操作当前project
			list.add(temped);
	}
	//总和完毕
	//参数说明：str1~7表示七天的日期，将显示在表头。
	String str1 = (String) request.getSession().getAttribute("str1");
	String str2 = (String) request.getSession().getAttribute("str2");
	String str3 = (String) request.getSession().getAttribute("str3");
	String str4 = (String) request.getSession().getAttribute("str4");
	String str5 = (String) request.getSession().getAttribute("str5");
	String str6 = (String) request.getSession().getAttribute("str6");
	String str7 = (String) request.getSession().getAttribute("str7");
	
	
	//第一天的日期
	String startDay = (String) request.getAttribute("startDay");
	System.out.println("startDay="+startDay);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date mon = df.parse(str1);
	Date tue = df.parse(str2);
	Date wed = df.parse(str3);
	Date thu = df.parse(str4);
	Date fri = df.parse(str5);
	Date sat = df.parse(str6);
	Date sun = df.parse(str7);
	int flag  = 0;
	System.out.println("moon = "+mon);	

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Check as group</title>
	<link rel="stylesheet" href="css/style_new.css" type="text/css" />
</head>
	<body>
	<center>
		<table width="80%" border="0" cellpadding="3" class="tabbg" cellspacing="1" >
		<tr class="tr_title2">
			<td align="right" colspan="10">single workload field format:&nbsp;&nbsp; regular time</td>
		</tr>
			<tr class="tr_title2">
				<td>Group</td>
				<td><%=str1.substring(5) %></td>
				<td><%=str2.substring(5)%></td>
				<td><%=str3.substring(5) %></td>
				<td><%=str4.substring(5) %></td>
				<td><%=str5.substring(5) %></td>
				<td><%=str6.substring(5) %></td>
				<td><%=str7.substring(5) %></td>
				<td>Summary</td>
			</tr>
			<% 
			for(int i=0;i<list.size();i++)
			{ 
				for (int m = 0; m < glist.size(); m++)
				{
					if (list.get(i).getGroupId() == glist.get(m).getGid()) 
					{
						if(null == glist.get(m).getLockday() || "null".equalsIgnoreCase(glist.get(m).getLockday()))
							System.out.println("Group "+list.get(m).getGroupName()+" lockday is null!");
						//格式化每个group的lockday为日期格式yyyy-MM-dd
						Date dd = df.parse(glist.get(m).getLockday().trim());
						flag = 0;
						if(dd.after(mon)||dd.equals(mon))//使用日期比较的布尔值判断
						{
							flag = 1;
						}
						 if(dd.after(tue)||dd.equals(tue))
						{
							flag = 2;
						}
						 if(dd.after(wed)||dd.equals(wed))
						{
							flag = 3;
						}
						 if(dd.after(thu)||dd.equals(thu))
						{
							flag = 4;
						}
						 if(dd.after(fri)||dd.equals(fri))
						{
							flag = 5;
						}
						 if(dd.after(sat)||dd.equals(sat))
						{
							flag = 6;
						}
						 if(dd.after(sun)||dd.equals(sun))
						{
							flag = 7;
						}
	
					}										
				}
			
			%>
			<tr class="tr_content" align="center">
				<td><%=list.get(i).getGroupName()%></td>
				<td <%if(flag>=1){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour1())%></td>
				<td <%if(flag>=2){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour2()) %></td>
				<td <%if(flag>=3){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour3())%></td>
				<td <%if(flag>=4){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour4())%></td>
				<td <%if(flag>=5){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour5()) %></td>
				<td <%if(flag>=6){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour6()) %></td>
				<td <%if(flag>=7){ %>style="color:red;"<% }%>><%=Float.parseFloat(list.get(i).getHour7()) %></td>
				<td>
				<%=formate.format(Float.parseFloat(list.get(i).getHour1())
								+Float.parseFloat(list.get(i).getHour2())
								+Float.parseFloat(list.get(i).getHour3())
								+Float.parseFloat(list.get(i).getHour4())
								+Float.parseFloat(list.get(i).getHour5())
								+Float.parseFloat(list.get(i).getHour6())
								+Float.parseFloat(list.get(i).getHour7()))
				%>
				</td>
			</tr>
			<%} %>
		</table>
	</center>
	</body>
</html>