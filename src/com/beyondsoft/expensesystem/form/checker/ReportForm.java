package com.beyondsoft.expensesystem.form.checker;

import com.beyondsoft.expensesystem.util.BaseActionForm;

public class ReportForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;

	private String startDay = null;
	private String endDay = null;

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

}
