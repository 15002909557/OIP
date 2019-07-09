package com.beyondsoft.expensesystem.time;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class MyTimerListener implements ServletContextListener {

	private BugXmlTimer mytimer = new BugXmlTimer();

	public void contextInitialized(ServletContextEvent event) {
		mytimer.timerStart();
	}

	public void contextDestroyed(ServletContextEvent event) {
		mytimer.timerStop();
	}

}
