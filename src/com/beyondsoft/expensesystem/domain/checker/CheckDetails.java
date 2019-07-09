package com.beyondsoft.expensesystem.domain.checker;

import java.util.List;

/*
 * 2011/5/5
 * Author=xiaofei
 * Description: 用来保存Details检查信息 				
 */
public class CheckDetails
{
	private int expensedataid = -1;
	private String groupname = "";
	private int projectid = -1;
	private String projectname = "";
	private String createtime = "";
	private double dailyworkload = 0;
	private double sumofdetails = 0;
	
	public boolean findExpensedata(int pid,String ctime,List<CheckDetails> details)
	{
		boolean result=false;
		if (details.size()!=0){
			for (int i=0;i<details.size();i++){
				if (details.get(i).getProjectid()==pid && details.get(i).getCreatetime().equals(ctime.trim())){
					result=true;
					i=details.size();
				}
				//System.out.println(details.get(i).getProjectid()+details.get(i).getCreatetime()+result+pid+ctime);
			}
		}
		return result;
	}
	public int getProjectid()
	{
		return projectid;
	}
	public void setProjectid(int projectid)
	{
		this.projectid = projectid;
	}
	public int getExpensedataid()
	{
		return expensedataid;
	}
	public void setExpensedataid(int expensedataid)
	{
		this.expensedataid = expensedataid;
	}
	public String getGroupname()
	{
		return groupname;
	}
	public void setGroupname(String groupname)
	{
		this.groupname = groupname;
	}
	public String getProjectname()
	{
		return projectname;
	}
	public void setProjectname(String projectname)
	{
		this.projectname = projectname;
	}
	public String getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(String createtime)
	{
		this.createtime = createtime;
	}
	public double getDailyworkload()
	{
		return dailyworkload;
	}
	public void setDailyworkload(double dailyworkload)
	{
		this.dailyworkload = dailyworkload;
	}
	public double getSumofdetails()
	{
		return sumofdetails;
	}
	public void setSumofdetails(double sumofdetails)
	{
		this.sumofdetails = sumofdetails;
	}
	
}
