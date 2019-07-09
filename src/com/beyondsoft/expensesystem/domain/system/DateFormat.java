package com.beyondsoft.expensesystem.domain.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormat
{
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-M-d");
	public List<String>doWeek(String currentDay) throws Exception
	{
		System.out.println("currentDay="+currentDay);
		Date today = df.parse(currentDay);
		List<String> list = new ArrayList<String>();
		Calendar ca = Calendar.getInstance();
		ca.setFirstDayOfWeek(Calendar.MONDAY);
		ca.setTime(today); 
		
		for (int a = 0; a < 7; a++) 
		{   
			ca.set(Calendar.DAY_OF_WEEK,ca.getFirstDayOfWeek() + a);
			list.add(a, df.format(ca.getTime()));  
		}
		System.out.println("first day of week="+list.get(0));
		return list;
	}
	
	public List<String>doWeek2(String currentDay) throws Exception
	{
		Date today = df2.parse(currentDay);
		List<String> list = new ArrayList<String>();
		Calendar ca = Calendar.getInstance();
		ca.setFirstDayOfWeek(Calendar.MONDAY);
		ca.setTime(today); 
		
		for (int a = 0; a < 7; a++) 
		{   
			ca.set(Calendar.DAY_OF_WEEK,ca.getFirstDayOfWeek() + a);
			list.add(a, df2.format(ca.getTime()));  
		}
		return list;
	}
	
}
