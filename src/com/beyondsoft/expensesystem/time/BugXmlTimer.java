package com.beyondsoft.expensesystem.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class BugXmlTimer 
{

	public final static Timer timer = new Timer();

	public void timerStart() 
	{
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),8,30,0);
		 Date date = calendar.getTime();
		 System.out.println("date="+date);
	 	 timer.schedule(new BugXmlTimerTask(),date,24*60*60*1000);
	}
	public void timerStop() 
	{
		System.out.println("There were errors encountered!");
		if (timer != null)
		{
			timer.cancel();
		}
	}


}
